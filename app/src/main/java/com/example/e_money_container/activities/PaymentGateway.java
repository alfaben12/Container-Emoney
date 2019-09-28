package com.example.e_money_container.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_money_container.R;
import com.example.e_money_container.adapters.PaymentGatewayAdapter;
import com.example.e_money_container.helpers.PreferenceHelper;
import com.example.e_money_container.models.PaymentGateway.Datum;
import com.example.e_money_container.models.PaymentGateway.PaymentGatewayModel;
import com.example.e_money_container.request.PaymentGatewayRequest;
import com.example.e_money_container.retrofit.PhpApiClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentGateway extends AppCompatActivity {

    private PaymentGatewayAdapter adapter;
    private RecyclerView recyclerView;
    TextView txtFullName;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_gateway);

        txtFullName = findViewById(R.id.txtFullName);

        PreferenceHelper prefShared = new PreferenceHelper(this);
        String accountCode = prefShared.getStr("guestAccCode");
        String accountFullName = prefShared.getStr("guestAccFullName");
        String accountNominal = prefShared.getStr("guestAccNominal");

        txtFullName.setText(accountFullName);

        /*Create handle for the RetrofitInstance interface*/
        PaymentGatewayRequest service = PhpApiClient.getRetrofitInstance().create(PaymentGatewayRequest.class);
        Call<PaymentGatewayModel> call = service.getPaymentGateways();
        call.enqueue(new Callback<PaymentGatewayModel>() {
            @Override
            public void onResponse(Call<PaymentGatewayModel> call, Response<PaymentGatewayModel> response) {
                Toast.makeText(PaymentGateway.this, "Success...", Toast.LENGTH_SHORT).show();
                generateDataList(response.body().getData());
            }

            @Override
            public void onFailure(Call<PaymentGatewayModel> call, Throwable t) {
                Toast.makeText(PaymentGateway.this, ""+ t, Toast.LENGTH_SHORT).show();
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.guide:
                        Intent i = new Intent(PaymentGateway.this, GuideMe.class);
                        startActivity(i);
                        break;
                    case R.id.help:
                        Intent menuIntent = new Intent(PaymentGateway.this, Helps.class);
                        startActivity(menuIntent);
                        break;
                    case R.id.login:
                        Intent k = new Intent(PaymentGateway.this, Logins.class);
                        startActivity(k);
                        break;
                }
                return false;
            }
        });
    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataList(List<Datum> dataList) {
        recyclerView = (RecyclerView) findViewById(R.id.customRecyclerView);
        adapter = new PaymentGatewayAdapter(PaymentGateway.this, dataList);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}

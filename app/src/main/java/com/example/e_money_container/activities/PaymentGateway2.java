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
import com.example.e_money_container.adapters.PaymentGatewayAdapter2;
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

public class PaymentGateway2 extends AppCompatActivity {

    private PaymentGatewayAdapter2 adapter;
    private RecyclerView recyclerView;
    TextView txtFullName;
    ProgressDialog progressDoalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_gateway);

        /* INIT PROGRESS LOADER */
        progressDoalog = new ProgressDialog(this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();
        /* END PROGRESS LOADER */

        PreferenceHelper prefShared = new PreferenceHelper(this);
        String accountName = prefShared.getStr("accountName");

        txtFullName = findViewById(R.id.txtFullName);

        txtFullName.setText(accountName);

        /*Create handle for the RetrofitInstance interface*/
        PaymentGatewayRequest service = PhpApiClient.getRetrofitInstance().create(PaymentGatewayRequest.class);
        Call<PaymentGatewayModel> call = service.getPaymentGateways();
        call.enqueue(new Callback<PaymentGatewayModel>() {
            @Override
            public void onResponse(Call<PaymentGatewayModel> call, Response<PaymentGatewayModel> response) {
                generateDataList(response.body().getData());
                progressDoalog.dismiss();
            }

            @Override
            public void onFailure(Call<PaymentGatewayModel> call, Throwable t) {
                Toast.makeText(PaymentGateway2.this, ""+ t, Toast.LENGTH_SHORT).show();
                progressDoalog.dismiss();
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.guide:
                        Intent i = new Intent(PaymentGateway2.this, GuideMe.class);
                        startActivity(i);
                        break;
                    case R.id.help:
                        Intent menuIntent = new Intent(PaymentGateway2.this, Helps.class);
                        startActivity(menuIntent);
                        break;
                    case R.id.login:
                        Intent k = new Intent(PaymentGateway2.this, Logins.class);
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
        adapter = new PaymentGatewayAdapter2(PaymentGateway2.this, dataList);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}

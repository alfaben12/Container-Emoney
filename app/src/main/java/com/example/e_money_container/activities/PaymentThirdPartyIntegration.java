package com.example.e_money_container.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.example.e_money_container.adapters.PaymentGatewayApiAdapter;
import com.example.e_money_container.helpers.PreferenceHelper;
import com.example.e_money_container.models.PaymentGateway.PaymentGatewayModel;
import com.example.e_money_container.models.PaymentThirdParty.Datum;
import com.example.e_money_container.models.PaymentThirdParty.PaymentThirdPartyModel;
import com.example.e_money_container.request.PaymentGatewayRequest;
import com.example.e_money_container.retrofit.NodeApiClient;
import com.example.e_money_container.retrofit.PhpApiClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentThirdPartyIntegration extends AppCompatActivity {

    private PaymentGatewayApiAdapter adapter;
    private RecyclerView recyclerView;
    TextView txtFullName;
    ProgressDialog progressDoalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_third_party_integration);
        /* INIT PROGRESS LOADER */
        progressDoalog = new ProgressDialog(this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();
        /* END PROGRESS LOADER */
        PreferenceHelper prefShared = new PreferenceHelper(this);
        String accountName = prefShared.getStr("accountName");
        String accountJwtToken = prefShared.getStr("accountJwtToken");

        txtFullName = findViewById(R.id.txtFullName);

        txtFullName.setText(accountName);

        /*Create handle for the RetrofitInstance interface*/
        PaymentGatewayRequest service = NodeApiClient.getRetrofitInstance().create(PaymentGatewayRequest.class);
        Call<PaymentThirdPartyModel> call = service.paymentapi(accountJwtToken);
        call.enqueue(new Callback<PaymentThirdPartyModel>() {
            @Override
            public void onResponse(Call<PaymentThirdPartyModel> call, Response<PaymentThirdPartyModel> response) {
                Toast.makeText(PaymentThirdPartyIntegration.this, "Success...", Toast.LENGTH_SHORT).show();
                generateDataList(response.body().getData().getData());
                progressDoalog.dismiss();
            }

            @Override
            public void onFailure(Call<PaymentThirdPartyModel> call, Throwable t) {
                Toast.makeText(PaymentThirdPartyIntegration.this, ""+ t, Toast.LENGTH_SHORT).show();
                progressDoalog.dismiss();
            }
        });
    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataList(List<Datum> dataList) {
        recyclerView = (RecyclerView) findViewById(R.id.customRecyclerView);
        adapter = new PaymentGatewayApiAdapter(PaymentThirdPartyIntegration.this, dataList);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}

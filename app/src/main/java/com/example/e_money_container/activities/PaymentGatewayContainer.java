package com.example.e_money_container.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_money_container.R;
import com.example.e_money_container.adapters.PaymentGatewayContainerAdapter;
import com.example.e_money_container.helpers.PreferenceHelper;
import com.example.e_money_container.models.PaymentGatewayContainer.Datum;
import com.example.e_money_container.models.PaymentGatewayContainer.PaymentGatewayContainerModel;
import com.example.e_money_container.request.PaymentGatewayRequest;
import com.example.e_money_container.retrofit.NodeApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentGatewayContainer extends AppCompatActivity {

    private PaymentGatewayContainerAdapter adapter;
    private RecyclerView recyclerView;
    TextView txtFullName, txtWalletAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_container);

        PreferenceHelper prefShared = new PreferenceHelper(this);
        String accountName = prefShared.getStr("accountName");
        String accountBalance = prefShared.getStr("accountBalance");
        String accountRole = prefShared.getStr("accountRole");
        String accountContainerApiKey = prefShared.getStr("accountContainerApiKey");


        txtFullName = findViewById(R.id.txtFullName);
        txtWalletAccount = findViewById(R.id.txtWalletAccount);

        txtFullName.setText(accountName);
        txtWalletAccount.setText("Rp. " + accountBalance + " (" + accountRole + ")");

        /*Create handle for the RetrofitInstance interface*/
        PaymentGatewayRequest service = NodeApiClient.getRetrofitInstance().create(PaymentGatewayRequest.class);
        Call<PaymentGatewayContainerModel> call = service.getPaymentGatewaysContainer();
        call.enqueue(new Callback<PaymentGatewayContainerModel>() {
            @Override
            public void onResponse(Call<PaymentGatewayContainerModel> call, Response<PaymentGatewayContainerModel> response) {
                Toast.makeText(PaymentGatewayContainer.this, "Success...", Toast.LENGTH_SHORT).show();
                generateDataList(response.body().getData().getData());
            }

            @Override
            public void onFailure(Call<PaymentGatewayContainerModel> call, Throwable t) {
                Toast.makeText(PaymentGatewayContainer.this, ""+ t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataList(List<Datum> dataList) {
        recyclerView = (RecyclerView) findViewById(R.id.customRecyclerView);
        adapter = new PaymentGatewayContainerAdapter(PaymentGatewayContainer.this, dataList);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}

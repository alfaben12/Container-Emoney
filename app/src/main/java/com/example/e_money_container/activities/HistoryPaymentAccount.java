package com.example.e_money_container.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_money_container.R;
import com.example.e_money_container.adapters.HistoryPaymentAccountAdapter;
import com.example.e_money_container.adapters.PaymentGatewayAdapter;
import com.example.e_money_container.helpers.PreferenceHelper;
import com.example.e_money_container.models.AccountData.AccountDataModel;
import com.example.e_money_container.models.PaymentHistory.Datum;
import com.example.e_money_container.models.PaymentHistory.PaymentHistoryModel;
import com.example.e_money_container.request.AccountRequest;
import com.example.e_money_container.request.PaymentRequest;
import com.example.e_money_container.retrofit.NodeApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryPaymentAccount extends AppCompatActivity {

    private HistoryPaymentAccountAdapter adapter;
    private RecyclerView recyclerView;
    TextView txtFullName, txtWalletAccount;
    ProgressDialog progressDoalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_payment_account);

        /* INIT PROGRESS LOADER */
        progressDoalog = new ProgressDialog(this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();
        /* END PROGRESS LOADER */

        PreferenceHelper prefShared = new PreferenceHelper(this);
        String accountJwtToken = prefShared.getStr("accountJwtToken");
        String accountName = prefShared.getStr("accountName");
        String accountRole = prefShared.getStr("accountRole");
        String accountBalance = prefShared.getStr("accountBalance");

        txtFullName = findViewById(R.id.txtFullName);
        txtWalletAccount = findViewById(R.id.txtWalletAccount);

        txtFullName.setText(accountName);
        txtWalletAccount.setText("Rp. " + accountBalance + " (" + accountRole + ")");
        
        /*Create handle for the RetrofitInstance interface*/
        PaymentRequest service = NodeApiClient.getRetrofitInstance().create(PaymentRequest.class);
        Call<PaymentHistoryModel> call = service.paymenthistorys(accountJwtToken);
        call.enqueue(new Callback<PaymentHistoryModel>() {
            @Override
            public void onResponse(Call<PaymentHistoryModel> call, Response<PaymentHistoryModel> response) {
                if (response.isSuccessful()){
                    Toast.makeText(HistoryPaymentAccount.this, response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                    generateDataList(response.body().getData().getData());
                    progressDoalog.dismiss();
                }else{
                    Toast.makeText(HistoryPaymentAccount.this, "Account not found ...", Toast.LENGTH_SHORT).show();
                    progressDoalog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<PaymentHistoryModel> call, Throwable t) {
                Toast.makeText(HistoryPaymentAccount.this, "Failure connection..."+ t, Toast.LENGTH_SHORT).show();
                progressDoalog.dismiss();
            }
        });
    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataList(List<Datum> dataList) {
        recyclerView = (RecyclerView) findViewById(R.id.customRecyclerView);
        adapter = new HistoryPaymentAccountAdapter(HistoryPaymentAccount.this, dataList);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}

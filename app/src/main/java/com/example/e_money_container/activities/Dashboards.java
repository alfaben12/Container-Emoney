package com.example.e_money_container.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_money_container.R;
import com.example.e_money_container.helpers.PreferenceHelper;
import com.example.e_money_container.models.AccountData.AccountDataModel;
import com.example.e_money_container.models.Login.AccountData;
import com.example.e_money_container.models.Login.LoginModel;
import com.example.e_money_container.request.AccountRequest;
import com.example.e_money_container.request.LoginRegisterRequest;
import com.example.e_money_container.retrofit.NodeApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboards extends AppCompatActivity {

    TextView txtFullName, txtWalletAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboards);

        PreferenceHelper prefShared = new PreferenceHelper(this);
        String accountJwtToken = prefShared.getStr("accountJwtToken");

        txtFullName = findViewById(R.id.txtFullName);
        txtWalletAccount = findViewById(R.id.txtWalletAccount);

        /*Create handle for the RetrofitInstance interface*/
        AccountRequest service = NodeApiClient.getRetrofitInstance().create(AccountRequest.class);
        Call<AccountDataModel> call = service.account(accountJwtToken);
        call.enqueue(new Callback<AccountDataModel>() {
            @Override
            public void onResponse(Call<AccountDataModel> call, Response<AccountDataModel> response) {
                if (response.isSuccessful()){
                    Toast.makeText(Dashboards.this, response.body().getData().getDatas().getFullName(), Toast.LENGTH_SHORT).show();
                    txtFullName.setText(response.body().getData().getDatas().getFullName());
                    txtWalletAccount.setText("Rp. " +response.body().getData().getDatas().getAccountPaymentContainer().getBalance().toString() + " (" + response.body().getData().getDatas().getAccountRole().getName() + ")");

                }else{
                    Toast.makeText(Dashboards.this, "Account not found ...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AccountDataModel> call, Throwable t) {
                Toast.makeText(Dashboards.this, "Failure connection..."+ t, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

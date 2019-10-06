package com.example.e_money_container.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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
    ProgressDialog progressDoalog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboards);

        PreferenceHelper prefShared = new PreferenceHelper(this);
        String accountJwtToken = prefShared.getStr("accountJwtToken");
        String accountName = prefShared.getStr("accountName");
        String accountRole = prefShared.getStr("accountRole");

        txtFullName = findViewById(R.id.txtFullName);
        txtWalletAccount = findViewById(R.id.txtWalletAccount);
        /* INIT PROGRESS LOADER */
        progressDoalog = new ProgressDialog(Dashboards.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();
        /* END PROGRESS LOADER */

        /*Create handle for the RetrofitInstance interface*/
        AccountRequest service = NodeApiClient.getRetrofitInstance().create(AccountRequest.class);
        Call<AccountDataModel> call = service.account(accountJwtToken);
        call.enqueue(new Callback<AccountDataModel>() {
            @Override
            public void onResponse(Call<AccountDataModel> call, Response<AccountDataModel> response) {
                if (response.isSuccessful()){
                    PreferenceHelper prefShared = new PreferenceHelper(Dashboards.this);
                    Toast.makeText(Dashboards.this, response.body().getData().getDatas().getFullName(), Toast.LENGTH_SHORT).show();
                    prefShared.setStr("accountContainerApiKey", response.body().getData().getDatas().getAccountPaymentContainer().getPaymentGatewayAccountApikey());
                    prefShared.setStr("accountBalance", response.body().getData().getDatas().getAccountPaymentContainer().getBalance().toString());
                    txtFullName.setText(response.body().getData().getDatas().getFullName());
                    txtWalletAccount.setText("Rp. " +response.body().getData().getDatas().getAccountPaymentContainer().getBalance().toString() + " (" + response.body().getData().getDatas().getAccountRole().getName() + ")");
                    progressDoalog.dismiss();
                }else{
                    Toast.makeText(Dashboards.this, "Account not found ...", Toast.LENGTH_SHORT).show();
                    progressDoalog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<AccountDataModel> call, Throwable t) {
                Toast.makeText(Dashboards.this, "Failure connection..."+ t, Toast.LENGTH_SHORT).show();
                progressDoalog.dismiss();
            }
        });
    }

    public void clickHistoryPayment(View view) {
        Intent i = new Intent(Dashboards.this, HistoryPaymentAccount.class);
        startActivity(i);
    }

    public void clickWallet(View view) {
        Intent i = new Intent(Dashboards.this, SetupEmoneyContainer.class);
        startActivity(i);
    }

    public void clickSaving(View view) {
        Intent i = new Intent(Dashboards.this, AccountSaving.class);
        startActivity(i);
    }

    public void clickMyAccount(View view) {
        Intent i = new Intent(Dashboards.this, Profile.class);
        startActivity(i);
    }

    public void clickGuideMe(View view) {
        Intent i = new Intent(Dashboards.this, GuideMe.class);
        startActivity(i);
    }

    public void clickPayment(View view) {
        Intent i = new Intent(Dashboards.this, PaymentDashboard.class);
        startActivity(i);
    }

    public void clickRefresh(View view) {
        this.recreate();
    }

    public void clickLogout(View view) {
        SharedPreferences settings = getSharedPreferences("emoney_pref", Context.MODE_PRIVATE);
        settings.edit().clear().commit();
    }
}

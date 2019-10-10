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
import com.example.e_money_container.request.AccountRequest;
import com.example.e_money_container.retrofit.NodeApiClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends AppCompatActivity {
    ProgressDialog progressDoalog;
    TextView code, txtFullName, txtWalletAccount, username, name, savingBalance, savingTargetBalance, email, address, roleName, limit, containerBalance, accountCreated;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        /* INIT PROGRESS LOADER */
        progressDoalog = new ProgressDialog(this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();
        /* END PROGRESS LOADER */
        PreferenceHelper prefShared = new PreferenceHelper(this);
        String accountJwtToken = prefShared.getStr("accountJwtToken");
        String accountName = prefShared.getStr("accountName");
        String accountBalance = prefShared.getStr("accountBalance");
        String accountRole = prefShared.getStr("accountRole");

        txtFullName = findViewById(R.id.txtFullName);
        txtWalletAccount = findViewById(R.id.txtWalletAccount);

        txtFullName.setText(accountName);
        txtWalletAccount.setText("Rp. " + accountBalance + " (" + accountRole + ")");

        username = findViewById(R.id.username);
        name = findViewById(R.id.name);
        savingBalance = findViewById(R.id.savingBalance);
        savingTargetBalance = findViewById(R.id.savingTargetBalance);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address);
        roleName = findViewById(R.id.roleName);
        limit = findViewById(R.id.limit);
        containerBalance = findViewById(R.id.containerBalance);
        accountCreated = findViewById(R.id.accountCreated);
        code = findViewById(R.id.code);

        /*Create handle for the RetrofitInstance interface*/
        AccountRequest service = NodeApiClient.getRetrofitInstance().create(AccountRequest.class);
        Call<AccountDataModel> call = service.account(accountJwtToken);
        call.enqueue(new Callback<AccountDataModel>() {
            @Override
            public void onResponse(Call<AccountDataModel> call, Response<AccountDataModel> response) {
                if (response.isSuccessful()){
                    PreferenceHelper prefShared = new PreferenceHelper(Profile.this);
                    Toast.makeText(Profile.this, response.body().getData().getDatas().getFullName(), Toast.LENGTH_SHORT).show();
                    String apikey = "";
                    String balance = "";

                    if (response.body().getData().getDatas().getAccountPaymentContainer() != null){
                        apikey = response.body().getData().getDatas().getAccountPaymentContainer().getPaymentGatewayAccountApikey();
                        balance = response.body().getData().getDatas().getAccountPaymentContainer().getBalance().toString();

                    }else{
                        apikey = "";
                        balance = "0";
                    }

                    prefShared.setStr("accountContainerApiKey", apikey);
                    prefShared.setStr("accountBalance", balance);
                    username.setText(response.body().getData().getDatas().getUsername());
                    code.setText(response.body().getData().getDatas().getCode());
                    name.setText(response.body().getData().getDatas().getFullName());
                    savingBalance.setText("Rp. "+ response.body().getData().getDatas().getBalance().toString());
                    savingTargetBalance.setText("Rp. "+ response.body().getData().getDatas().getSavingBalance().toString());
                    email.setText(response.body().getData().getDatas().getEmail());
                    address.setText(response.body().getData().getDatas().getAddress());
                    roleName.setText(response.body().getData().getDatas().getAccountRole().getName());
                    limit.setText("Rp. "+ response.body().getData().getDatas().getAccountRole().getTransactionLimit() + "/" + response.body().getData().getDatas().getAccountRole().getTransactionLimitCount() + "x");
                    containerBalance.setText("Rp. "+ balance);

                    SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    SimpleDateFormat output = new SimpleDateFormat("yyyy/MM/dd");

                    Date d = null;
                    try
                    {
                        d = input.parse(response.body().getData().getDatas().getCreatedAt());
                    }
                    catch (ParseException e)
                    {
                        e.printStackTrace();
                    }
                    String formatted = output.format(d);

                    accountCreated.setText(formatted);
                    progressDoalog.dismiss();
                }else{
                    Toast.makeText(Profile.this, "Account not found ...", Toast.LENGTH_SHORT).show();
                    progressDoalog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<AccountDataModel> call, Throwable t) {
                Toast.makeText(Profile.this, "Failure connection..."+ t, Toast.LENGTH_SHORT).show();
                progressDoalog.dismiss();
            }
        });
    }

    public void clickAccountUpdate(View view) {
        Intent i = new Intent(Profile.this, AccountUpdate.class);
        startActivity(i);
    }

    public void clickLogout(View view) {
        SharedPreferences settings = getSharedPreferences("emoney_pref", Context.MODE_PRIVATE);
        settings.edit().clear().commit();

        Intent i = new Intent(Profile.this, Logins.class);
        startActivity(i);
    }
}

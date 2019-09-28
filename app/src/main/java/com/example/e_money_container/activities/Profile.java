package com.example.e_money_container.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.content.Intent;
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

    TextView username, name, savingBalance, savingTargetBalance, email, address, roleName, limit, containerBalance, accountCreated;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

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

        PreferenceHelper prefShared = new PreferenceHelper(this);
        String accountJwtToken = prefShared.getStr("accountJwtToken");

        /*Create handle for the RetrofitInstance interface*/
        AccountRequest service = NodeApiClient.getRetrofitInstance().create(AccountRequest.class);
        Call<AccountDataModel> call = service.account(accountJwtToken);
        call.enqueue(new Callback<AccountDataModel>() {
            @Override
            public void onResponse(Call<AccountDataModel> call, Response<AccountDataModel> response) {
                if (response.isSuccessful()){
                    PreferenceHelper prefShared = new PreferenceHelper(Profile.this);
                    Toast.makeText(Profile.this, response.body().getData().getDatas().getFullName(), Toast.LENGTH_SHORT).show();
                    prefShared.setStr("accountContainerApiKey", response.body().getData().getDatas().getAccountPaymentContainer().getPaymentGatewayAccountApikey());
                    prefShared.setStr("accountBalance", response.body().getData().getDatas().getAccountPaymentContainer().getBalance().toString());
                    username.setText(response.body().getData().getDatas().getUsername());
                    name.setText(response.body().getData().getDatas().getFullName());
                    savingBalance.setText("Rp. "+ response.body().getData().getDatas().getBalance().toString());
                    savingTargetBalance.setText("Rp. "+ response.body().getData().getDatas().getSavingBalance().toString());
                    email.setText(response.body().getData().getDatas().getEmail());
                    address.setText(response.body().getData().getDatas().getAddress());
                    roleName.setText(response.body().getData().getDatas().getAccountRole().getName());
                    limit.setText("Rp. "+ response.body().getData().getDatas().getAccountRole().getTransactionLimit() + "/" + response.body().getData().getDatas().getAccountRole().getTransactionLimitCount() + "x");
                    containerBalance.setText("Rp. "+ response.body().getData().getDatas().getAccountPaymentContainer().getBalance().toString());

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
                }else{
                    Toast.makeText(Profile.this, "Account not found ...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AccountDataModel> call, Throwable t) {
                Toast.makeText(Profile.this, "Failure connection..."+ t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void clickAccountUpdate(View view) {
        Intent i = new Intent(Profile.this, AccountUpdate.class);
        startActivity(i);
    }
}

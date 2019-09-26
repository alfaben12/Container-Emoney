package com.example.e_money_container.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_money_container.R;
import com.example.e_money_container.helpers.PreferenceHelper;
import com.example.e_money_container.models.Account.AccountModel;
import com.example.e_money_container.models.AccountData.AccountDataModel;
import com.example.e_money_container.models.Register.RegisterModel;
import com.example.e_money_container.request.AccountRequest;
import com.example.e_money_container.request.LoginRegisterRequest;
import com.example.e_money_container.retrofit.NodeApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountUpdate extends AppCompatActivity {

    EditText etName, etUsername, etAddress, etEmail, etPassword;
    TextView txtFullName, txtWalletAccount;
    Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_update);

        PreferenceHelper prefShared = new PreferenceHelper(this);
        final String accountJwtToken = prefShared.getStr("accountJwtToken");
        String accountName = prefShared.getStr("accountName");
        String accountBalance = prefShared.getStr("accountBalance");
        String accountRole = prefShared.getStr("accountRole");

        txtFullName = findViewById(R.id.txtFullName);
        txtWalletAccount = findViewById(R.id.txtWalletAccount);

        txtFullName.setText(accountName);
        txtWalletAccount.setText("Rp. " + accountBalance + " (" + accountRole + ")");

        etName = findViewById(R.id.etName);
        etUsername = findViewById(R.id.etUsername);
        etAddress = findViewById(R.id.etAddress);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnUpdate = findViewById(R.id.btnUpdate);

        /*Create handle for the RetrofitInstance interface*/
        AccountRequest service = NodeApiClient.getRetrofitInstance().create(AccountRequest.class);
        Call<AccountDataModel> call = service.account(accountJwtToken);
        call.enqueue(new Callback<AccountDataModel>() {
            @Override
            public void onResponse(Call<AccountDataModel> call, Response<AccountDataModel> response) {
                if (response.isSuccessful()){
                    etUsername.setText(response.body().getData().getDatas().getUsername());
                    etPassword.setText(response.body().getData().getDatas().getPassword());
                    etName.setText(response.body().getData().getDatas().getFullName());
                    etEmail.setText(response.body().getData().getDatas().getEmail());
                    etAddress.setText(response.body().getData().getDatas().getAddress());
                }else{
                    Toast.makeText(AccountUpdate.this, "Account not found ...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AccountDataModel> call, Throwable t) {
                Toast.makeText(AccountUpdate.this, "Failure connection..."+ t, Toast.LENGTH_SHORT).show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etName.getText().toString().trim().length() == 0){
                    Toast.makeText(AccountUpdate.this, "Name required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(etUsername.getText().toString().trim().length() == 0){
                    Toast.makeText(AccountUpdate.this, "Username required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(etAddress.getText().toString().trim().length() == 0){
                    Toast.makeText(AccountUpdate.this, "Address required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(etEmail.getText().toString().trim().length() == 0){
                    Toast.makeText(AccountUpdate.this, "Email required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(etPassword.getText().toString().trim().length() == 0){
                    Toast.makeText(AccountUpdate.this, "Password required", Toast.LENGTH_SHORT).show();
                    return;
                }

                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String full_name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String address = etAddress.getText().toString();

                /*Create handle for the RetrofitInstance interface*/
                AccountRequest service = NodeApiClient.getRetrofitInstance().create(AccountRequest.class);
                Call<AccountModel> call = service.updateAccount(accountJwtToken, username, password, full_name, email, address);
                call.enqueue(new Callback<AccountModel>() {
                    @Override
                    public void onResponse(Call<AccountModel> call, Response<AccountModel> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(AccountUpdate.this, response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                            Intent redirect = new Intent(getApplicationContext(), Dashboards.class);
                            startActivity(redirect);
                            finish();
                        }else{
                            Toast.makeText(AccountUpdate.this, response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AccountModel> call, Throwable t) {
                        Toast.makeText(AccountUpdate.this, "Failure connection..."+ t, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}

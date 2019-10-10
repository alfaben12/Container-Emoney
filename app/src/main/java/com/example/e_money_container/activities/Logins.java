package com.example.e_money_container.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.e_money_container.R;
import com.example.e_money_container.helpers.PreferenceHelper;
import com.example.e_money_container.models.Account.AccountModel;
import com.example.e_money_container.models.Login.LoginModel;
import com.example.e_money_container.request.AccountRequest;
import com.example.e_money_container.request.LoginRegisterRequest;
import com.example.e_money_container.retrofit.NodeApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Logins extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin;
    ProgressDialog progressDoalog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logins);

        PreferenceHelper prefShared = new PreferenceHelper(this);
        String accountJwtToken = prefShared.getStr("accountJwtToken");

        if (accountJwtToken != null){
            Intent redirect = new Intent(getApplicationContext(), Dashboards.class);
            startActivity(redirect);
            finish();
        }

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // inflate the layout

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* INIT PROGRESS LOADER */
                progressDoalog = new ProgressDialog(Logins.this);
                progressDoalog.setMessage("Loading....");
                progressDoalog.show();
                /* END PROGRESS LOADER */

                if(etEmail.getText().toString().trim().length() == 0){
                    Toast.makeText(Logins.this, "Email required", Toast.LENGTH_SHORT).show();
                    progressDoalog.dismiss();
                }

                if(etPassword.getText().toString().trim().length() == 0){
                    Toast.makeText(Logins.this, "Password required", Toast.LENGTH_SHORT).show();
                    progressDoalog.dismiss();
                }

                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                /*Create handle for the RetrofitInstance interface*/
                LoginRegisterRequest service = NodeApiClient.getRetrofitInstance().create(LoginRegisterRequest.class);
                Call<LoginModel> call = service.login(email, password);
                call.enqueue(new Callback<LoginModel>() {
                    @Override
                    public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(Logins.this, response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                            PreferenceHelper prefShared = new PreferenceHelper(Logins.this);
                            String balance = "";
                            if (response.body().getData().getDatas().getAccountData().getAccountPaymentContainer() != null){
                                balance = response.body().getData().getDatas().getAccountData().getAccountPaymentContainer().getBalance().toString();
                            }else{
                                balance = "";
                            }
                            prefShared.setStr("accountBalance", balance);
                            prefShared.setStr("accountJwtToken","Bearer "+ response.body().getData().getDatas().getJwtTokenData());
                            prefShared.setStr("accountName", response.body().getData().getDatas().getAccountData().getFullName());
                            prefShared.setStr("accountRole", response.body().getData().getDatas().getAccountData().getAccountRole().getName());
                            prefShared.setStr("accountId", response.body().getData().getDatas().getAccountData().getId().toString());
                            progressDoalog.dismiss();
                            Intent redirect = new Intent(getApplicationContext(), Dashboards.class);
                            startActivity(redirect);
                            finish();
                        }else{
                            Toast.makeText(Logins.this, "Account not found ...", Toast.LENGTH_SHORT).show();
                            progressDoalog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginModel> call, Throwable t) {
                        Toast.makeText(Logins.this, "Failure connection..."+ t, Toast.LENGTH_SHORT).show();
                        progressDoalog.dismiss();
                    }
                });
            }
        });
    }

    public void checkRegister(View view) {
        Intent i = new Intent(Logins.this, Registers.class);
        startActivity(i);
    }
}

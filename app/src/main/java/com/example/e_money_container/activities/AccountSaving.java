package com.example.e_money_container.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import com.example.e_money_container.request.AccountRequest;
import com.example.e_money_container.retrofit.NodeApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountSaving extends AppCompatActivity {

    EditText etNominal, etBalance;
    TextView txtFullName, txtWalletAccount;
    Button btnUpdate;
    ProgressDialog progressDoalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_saving);

        /* INIT PROGRESS LOADER */
        progressDoalog = new ProgressDialog(this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();
        /* END PROGRESS LOADER */

        PreferenceHelper prefShared = new PreferenceHelper(this);
        final String accountJwtToken = prefShared.getStr("accountJwtToken");
        String accountName = prefShared.getStr("accountName");
        String accountBalance = prefShared.getStr("accountBalance");
        String accountRole = prefShared.getStr("accountRole");

        txtFullName = findViewById(R.id.txtFullName);
        txtWalletAccount = findViewById(R.id.txtWalletAccount);

        txtFullName.setText(accountName);
        txtWalletAccount.setText("Rp. " + accountBalance + " (" + accountRole + ")");

        etBalance = findViewById(R.id.etBalance);
        etNominal = findViewById(R.id.etNominal);
        btnUpdate = findViewById(R.id.btnUpdate);

        etBalance.setEnabled(false);
        /*Create handle for the RetrofitInstance interface*/
        AccountRequest service = NodeApiClient.getRetrofitInstance().create(AccountRequest.class);
        Call<AccountDataModel> call = service.account(accountJwtToken);
        call.enqueue(new Callback<AccountDataModel>() {
            @Override
            public void onResponse(Call<AccountDataModel> call, Response<AccountDataModel> response) {
                if (response.isSuccessful()){
                    etBalance.setText(response.body().getData().getDatas().getBalance().toString());
                    etNominal.setText(response.body().getData().getDatas().getSavingBalance().toString());
                    progressDoalog.dismiss();
                }else{
                    Toast.makeText(AccountSaving.this, "Account not found ...", Toast.LENGTH_SHORT).show();
                    progressDoalog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<AccountDataModel> call, Throwable t) {
                Toast.makeText(AccountSaving.this, "Failure connection..."+ t, Toast.LENGTH_SHORT).show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* INIT PROGRESS LOADER */
                progressDoalog = new ProgressDialog(AccountSaving.this);
                progressDoalog.setMessage("Loading....");
                progressDoalog.show();
                /* END PROGRESS LOADER */

                if(etNominal.getText().toString().trim().length() == 0){
                    Toast.makeText(AccountSaving.this, "Nominal required", Toast.LENGTH_SHORT).show();
                    return;
                }

                String nominal = etNominal.getText().toString();

                /*Create handle for the RetrofitInstance interface*/
                AccountRequest service = NodeApiClient.getRetrofitInstance().create(AccountRequest.class);
                Call<AccountModel> call = service.updateAccountSaving(accountJwtToken, nominal);
                call.enqueue(new Callback<AccountModel>() {
                    @Override
                    public void onResponse(Call<AccountModel> call, Response<AccountModel> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(AccountSaving.this, response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                            progressDoalog.dismiss();
                            Intent redirect = new Intent(getApplicationContext(), Dashboards.class);
                            startActivity(redirect);
                            finish();
                        }else{
                            Toast.makeText(AccountSaving.this, "Saving balance should than current balance.", Toast.LENGTH_SHORT).show();
                            progressDoalog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<AccountModel> call, Throwable t) {
                        Toast.makeText(AccountSaving.this, "Failure connection..."+ t, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void clickContainer(View view) {
        Intent i = new Intent(AccountSaving.this, HistoryPaymentAccount.class);
        startActivity(i);
    }
}

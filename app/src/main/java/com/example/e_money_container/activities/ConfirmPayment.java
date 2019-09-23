package com.example.e_money_container.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_money_container.R;
import com.example.e_money_container.helpers.PreferenceHelper;
import com.example.e_money_container.models.Account.AccountModel;
import com.example.e_money_container.models.PaymentGateway.PaymentGatewayModel;
import com.example.e_money_container.request.AccountRequest;
import com.example.e_money_container.request.PaymentGatewayRequest;
import com.example.e_money_container.retrofit.NodeApiClient;
import com.example.e_money_container.retrofit.PhpApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmPayment extends AppCompatActivity {

    EditText etCode, etNominal;
    Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_payment);

        etCode = findViewById(R.id.etCode);
        etNominal = findViewById(R.id.etNominal);
        btnConfirm = findViewById(R.id.btnConfirm);

        // inflate the layout

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etCode.getText().toString().trim().length() == 0){
                    Toast.makeText(ConfirmPayment.this, "Code required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(etNominal.getText().toString().trim().length() == 0){
                    Toast.makeText(ConfirmPayment.this, "Nominal required", Toast.LENGTH_SHORT).show();
                    return;
                }

                String code = etCode.getText().toString();
                final String nominal = etNominal.getText().toString();

                /*Create handle for the RetrofitInstance interface*/
                AccountRequest service = NodeApiClient.getRetrofitInstance().create(AccountRequest.class);
                Call<AccountModel> call = service.getAccountByCode(code);
                call.enqueue(new Callback<AccountModel>() {
                    @Override
                    public void onResponse(Call<AccountModel> call, Response<AccountModel> response) {
                        Toast.makeText(ConfirmPayment.this, "Success...", Toast.LENGTH_SHORT).show();
                        if (response.body().getData().getData().size() > 0){
                            PreferenceHelper prefShared = new PreferenceHelper(ConfirmPayment.this);
                            prefShared.setStr("guestAccCode", response.body().getData().getData().get(0).getCode());
                            prefShared.setStr("guestAccFullName", response.body().getData().getData().get(0).getFullName());
                            prefShared.setStr("guestAccNominal", nominal);

                            Intent mainActivity = new Intent(getApplicationContext(), PaymentGateway.class);
                            startActivity(mainActivity);
                            finish();
                        }else{
                            Toast.makeText(ConfirmPayment.this, "Account not found ...", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AccountModel> call, Throwable t) {
                        Toast.makeText(ConfirmPayment.this, ""+ t, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}

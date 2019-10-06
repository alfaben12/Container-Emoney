package com.example.e_money_container.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.app.ProgressDialog;
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
import com.example.e_money_container.models.Payment.PaymentApiModel;
import com.example.e_money_container.models.PaymentDecreaseApi.PaymentDecreaseApiModel;
import com.example.e_money_container.models.PaymentGateway.PaymentGatewayModel;
import com.example.e_money_container.request.AccountRequest;
import com.example.e_money_container.request.PaymentGatewayRequest;
import com.example.e_money_container.request.PaymentRequest;
import com.example.e_money_container.retrofit.NodeApiClient;
import com.example.e_money_container.retrofit.PhpApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmPayment extends AppCompatActivity {

    EditText etCode, etNominal, etFromGatewayName, etToGatewayName;
    Button btnConfirm;
    ProgressDialog progressDoalog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_payment);

        PreferenceHelper prefShared = new PreferenceHelper(this);
        final String accountJwtToken = prefShared.getStr("accountJwtToken");
        String accountName = prefShared.getStr("accountName");

        etCode = findViewById(R.id.etCode);
        etNominal = findViewById(R.id.etNominal);
        btnConfirm = findViewById(R.id.btnConfirm);
        etFromGatewayName = findViewById(R.id.etFromGatewayName);
        etToGatewayName = findViewById(R.id.etToGatewayName);

        // inflate the layout

        final String accountToPaymentGateway = prefShared.getStr("accountToPaymentGateway");
        final String accountFromPaymentGateway = prefShared.getStr("accountFromPaymentGateway");
        final Integer accountId = Integer.parseInt(prefShared.getStr("accountId"));

        etFromGatewayName.setText(accountFromPaymentGateway);
        etToGatewayName.setText(accountToPaymentGateway);

        etFromGatewayName.setEnabled(false);
        etToGatewayName.setEnabled(false);
//        etCode.setText("ALFA");
//        etNominal.setText("100");
        btnConfirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /* INIT PROGRESS LOADER */
                progressDoalog = new ProgressDialog(ConfirmPayment.this);
                progressDoalog.setMessage("Loading....");
                progressDoalog.show();
                /* END PROGRESS LOADER */

                if(etCode.getText().toString().trim().length() == 0){
                    Toast.makeText(ConfirmPayment.this, "Code required", Toast.LENGTH_SHORT).show();
                    progressDoalog.dismiss();
                }

                if(etNominal.getText().toString().trim().length() == 0){
                    Toast.makeText(ConfirmPayment.this, "Nominal required", Toast.LENGTH_SHORT).show();
                    progressDoalog.dismiss();
                }

                String code = etCode.getText().toString();

                /*Create handle for the RetrofitInstance interface*/
                AccountRequest service = NodeApiClient.getRetrofitInstance().create(AccountRequest.class);
                Call<AccountModel> call = service.getAccountByCode(code);
                call.enqueue(new Callback<AccountModel>() {
                    @Override
                    public void onResponse(Call<AccountModel> call, final Response<AccountModel> response) {
//                        Toast.makeText(ConfirmPayment.this, "Success...", Toast.LENGTH_SHORT).show();
                        if (response.body().getData().getData().size() > 0){
//                            Toast.makeText(ConfirmPayment.this, ""+ response.body().getData().getData().get(0).getCode(), Toast.LENGTH_SHORT).show();

                            final String code = etCode.getText().toString();
                            final Integer nominal = Integer.parseInt(etNominal.getText().toString());

                            PaymentRequest service1 = PhpApiClient.getRetrofitInstance().create(PaymentRequest.class);
                            Call<PaymentApiModel> call1 = service1.pay(accountJwtToken, code, nominal, accountFromPaymentGateway, accountToPaymentGateway, accountId);
                            call1.enqueue(new Callback<PaymentApiModel>() {
                                @Override
                                public void onResponse(Call<PaymentApiModel> call1, Response<PaymentApiModel> response1) {
                                    if (response1.isSuccessful()){

                                        String payment_gateway_name = accountFromPaymentGateway;

                                        PaymentRequest service2 = NodeApiClient.getRetrofitInstance().create(PaymentRequest.class);
                                        Call<PaymentDecreaseApiModel> call2 = service2.pay_decrease_api(accountJwtToken, payment_gateway_name, nominal, accountId);
                                        call2.enqueue(new Callback<PaymentDecreaseApiModel>() {
                                            @Override
                                            public void onResponse(Call<PaymentDecreaseApiModel> call, Response<PaymentDecreaseApiModel> response) {
                                                if (response.isSuccessful()){
                                                    Toast.makeText(ConfirmPayment.this, "Success...", Toast.LENGTH_SHORT).show();
                                                    progressDoalog.dismiss();
                                                    Intent redirect = new Intent(getApplicationContext(), ThankYou.class);
                                                    startActivity(redirect);
                                                    finish();
                                                }else{
                                                    Toast.makeText(ConfirmPayment.this, "Payment decrease not fount {error}", Toast.LENGTH_SHORT).show();
                                                    progressDoalog.dismiss();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<PaymentDecreaseApiModel> call, Throwable t) {
                                                Toast.makeText(ConfirmPayment.this, "Payment not found..."+ t, Toast.LENGTH_SHORT).show();
                                                progressDoalog.dismiss();
                                            }
                                        });

                                    }else{
                                        Toast.makeText(ConfirmPayment.this, "Payment not found ", Toast.LENGTH_SHORT).show();
                                        progressDoalog.dismiss();
                                    }
                                }

                                @Override
                                public void onFailure(Call<PaymentApiModel> call, Throwable t) {
                                    Toast.makeText(ConfirmPayment.this, "Account not found..."+ t, Toast.LENGTH_SHORT).show();
                                    progressDoalog.dismiss();
                                }
                            });

                        }else{
                            Toast.makeText(ConfirmPayment.this, "Account not found {error}", Toast.LENGTH_SHORT).show();
                            progressDoalog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<AccountModel> call, Throwable t) {
                        Toast.makeText(ConfirmPayment.this, "Account not found ..."+ t, Toast.LENGTH_SHORT).show();
                        progressDoalog.dismiss();
                    }
                });
            }
        });
    }
}

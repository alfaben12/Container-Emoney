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
import com.example.e_money_container.models.PaymentThirdParty.PaymentThirdPartyModel;
import com.example.e_money_container.models.Register.RegisterModel;
import com.example.e_money_container.request.LoginRegisterRequest;
import com.example.e_money_container.request.PaymentGatewayRequest;
import com.example.e_money_container.request.PaymentRequest;
import com.example.e_money_container.retrofit.NodeApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentIntegration extends AppCompatActivity {

    TextView txtFullName, txtWalletAccount;
    EditText etName, etPaymentGatewayName, etApiKey, etBalance;
    Button btnAdd;
    ProgressDialog progressDoalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_integration);

        txtFullName = findViewById(R.id.txtFullName);
        txtWalletAccount = findViewById(R.id.txtWalletAccount);
        etName = findViewById(R.id.etName);
        etPaymentGatewayName = findViewById(R.id.etPaymentGatewayName);
        etApiKey = findViewById(R.id.etApiKey);
        etBalance = findViewById(R.id.etBalance);
        btnAdd = findViewById(R.id.btnAdd);

        PreferenceHelper prefShared = new PreferenceHelper(this);
        final String accountJwtToken = prefShared.getStr("accountJwtToken");
        String accountName = prefShared.getStr("accountName");
        String accountBalance = prefShared.getStr("accountBalance");
        String accountRole = prefShared.getStr("accountRole");
        String paymentGatewayContainerName = prefShared.getStr("paymentGatewayContainerName");

        txtFullName.setText(accountName);
        txtWalletAccount.setText("Rp. " + accountBalance + " (" + accountRole + ")");
        etPaymentGatewayName.setText(paymentGatewayContainerName);

        etPaymentGatewayName.setEnabled(false);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* INIT PROGRESS LOADER */
                progressDoalog = new ProgressDialog(PaymentIntegration.this);
                progressDoalog.setMessage("Loading....");
                progressDoalog.show();
                /* END PROGRESS LOADER */

                if(etName.getText().toString().trim().length() == 0){
                    Toast.makeText(PaymentIntegration.this, "Name required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(etPaymentGatewayName.getText().toString().trim().length() == 0){
                    Toast.makeText(PaymentIntegration.this, "E-Wallet Name required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(etApiKey.getText().toString().trim().length() == 0){
                    Toast.makeText(PaymentIntegration.this, "Api Key required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(etBalance.getText().toString().trim().length() == 0){
                    Toast.makeText(PaymentIntegration.this, "Balance required", Toast.LENGTH_SHORT).show();
                    return;
                }

                String name = etName.getText().toString();
                String paymentGatewayName = etPaymentGatewayName.getText().toString();
                String apiKey = etApiKey.getText().toString();
                Integer balance = Integer.parseInt(etBalance.getText().toString());

                /*Create handle for the RetrofitInstance interface*/
                PaymentGatewayRequest service = NodeApiClient.getRetrofitInstance().create(PaymentGatewayRequest.class);
                Call<PaymentThirdPartyModel> call = service.add_thirdparty(accountJwtToken ,name, paymentGatewayName, apiKey, balance);
                call.enqueue(new Callback<PaymentThirdPartyModel>() {
                    @Override
                    public void onResponse(Call<PaymentThirdPartyModel> call, Response<PaymentThirdPartyModel> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(PaymentIntegration.this, response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                            progressDoalog.dismiss();
                            Intent redirect = new Intent(getApplicationContext(), Dashboards.class);
                            startActivity(redirect);
                            finish();
                        }else{
                            Toast.makeText(PaymentIntegration.this, "Failerd added third-party..." + accountJwtToken, Toast.LENGTH_SHORT).show();
                            progressDoalog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<PaymentThirdPartyModel> call, Throwable t) {
                        Toast.makeText(PaymentIntegration.this, "Failure connection..."+ t, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}

package com.example.e_money_container.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_money_container.R;
import com.example.e_money_container.helpers.PreferenceHelper;
import com.example.e_money_container.models.AccountData.AccountDataModel;
import com.example.e_money_container.request.AccountRequest;
import com.example.e_money_container.retrofit.NodeApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetupEmoneyContainer extends AppCompatActivity {

    TextView txtFullName, txtWalletAccount;
    EditText etIdentity;
    Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_emoney_container);

        PreferenceHelper prefShared = new PreferenceHelper(this);
        final String accountJwtToken = prefShared.getStr("accountJwtToken");
        String accountName = prefShared.getStr("accountName");
        String accountBalance = prefShared.getStr("accountBalance");
        String accountRole = prefShared.getStr("accountRole");
        String accountContainerApiKey = prefShared.getStr("accountContainerApiKey");

        txtFullName = findViewById(R.id.txtFullName);
        txtWalletAccount = findViewById(R.id.txtWalletAccount);
        etIdentity = findViewById(R.id.etIdentity);
        btnUpdate = findViewById(R.id.btnUpdate);

        txtFullName.setText(accountName);
        txtWalletAccount.setText("Rp. " + accountBalance + " (" + accountRole + ")");
        etIdentity.setText(accountContainerApiKey);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etIdentity.getText().toString().trim().length() == 0) {
                    Toast.makeText(SetupEmoneyContainer.this, "Identity required", Toast.LENGTH_SHORT).show();
                    return;
                }
                PreferenceHelper prefShared = new PreferenceHelper(SetupEmoneyContainer.this);
                prefShared.setStr("accountContainerApiKey", etIdentity.getText().toString());

                Intent i = new Intent(SetupEmoneyContainer.this, PaymentGatewayContainer.class);
                startActivity(i);
            }
        });
    }
}

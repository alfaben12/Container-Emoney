package com.example.e_money_container.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.e_money_container.R;
import com.example.e_money_container.helpers.PreferenceHelper;

public class PaymentDashboard extends AppCompatActivity {

    TextView txtFullName, txtWalletAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_dashboard);

        PreferenceHelper prefShared = new PreferenceHelper(this);
        final String accountJwtToken = prefShared.getStr("accountJwtToken");
        String accountName = prefShared.getStr("accountName");
        String accountBalance = prefShared.getStr("accountBalance");
        String accountRole = prefShared.getStr("accountRole");

        txtFullName = findViewById(R.id.txtFullName);
        txtWalletAccount = findViewById(R.id.txtWalletAccount);

        txtFullName.setText(accountName);
        txtWalletAccount.setText("Rp. " + accountBalance + " (" + accountRole + ")");
    }

    public void clickPay(View view) {
        Intent i = new Intent(PaymentDashboard.this, PaymentGateway.class);
        startActivity(i);
    }

    public void clickThirdParty(View view) {
        Intent i = new Intent(PaymentDashboard.this, PaymentGateway2.class);
        startActivity(i);
    }

}

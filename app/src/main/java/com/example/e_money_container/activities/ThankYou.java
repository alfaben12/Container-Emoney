package com.example.e_money_container.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.e_money_container.R;
import com.example.e_money_container.helpers.PreferenceHelper;

public class ThankYou extends AppCompatActivity {

    TextView txtFullName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);

        PreferenceHelper prefShared = new PreferenceHelper(this);
        final String accountJwtToken = prefShared.getStr("accountJwtToken");
        String accountName = prefShared.getStr("accountName");

        txtFullName = findViewById(R.id.txtFullName);

        txtFullName.setText(accountName);
    }
}

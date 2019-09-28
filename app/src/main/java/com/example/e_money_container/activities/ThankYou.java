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

        txtFullName = findViewById(R.id.txtFullName);

        PreferenceHelper prefShared = new PreferenceHelper(this);
        String accountCode = prefShared.getStr("guestAccCode");
        String accountFullName = prefShared.getStr("guestAccFullName");
        String accountNominal = prefShared.getStr("guestAccNominal");

        txtFullName.setText(accountFullName);
    }
}

package com.example.e_money_container.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.e_money_container.R;

public class Helps extends AppCompatActivity {

    TextView txtFullName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helps);

        txtFullName = findViewById(R.id.txtFullName);
        txtFullName.setText("Administrator");
    }
}

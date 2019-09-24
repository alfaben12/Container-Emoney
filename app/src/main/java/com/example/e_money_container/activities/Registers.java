package com.example.e_money_container.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.e_money_container.R;
import com.example.e_money_container.helpers.PreferenceHelper;
import com.example.e_money_container.models.Login.LoginModel;
import com.example.e_money_container.models.Register.RegisterModel;
import com.example.e_money_container.request.LoginRegisterRequest;
import com.example.e_money_container.retrofit.NodeApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class Registers extends AppCompatActivity {

    EditText etName, etUsername, etAddress, etEmail, etPassword;
    Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registers);

        etName = findViewById(R.id.etName);
        etUsername = findViewById(R.id.etUsername);
        etAddress = findViewById(R.id.etAddress);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etName.getText().toString().trim().length() == 0){
                    Toast.makeText(Registers.this, "Name required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(etUsername.getText().toString().trim().length() == 0){
                    Toast.makeText(Registers.this, "Username required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(etAddress.getText().toString().trim().length() == 0){
                    Toast.makeText(Registers.this, "Address required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(etEmail.getText().toString().trim().length() == 0){
                    Toast.makeText(Registers.this, "Email required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(etPassword.getText().toString().trim().length() == 0){
                    Toast.makeText(Registers.this, "Password required", Toast.LENGTH_SHORT).show();
                    return;
                }

                Integer roleid = 2;
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                String full_name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String address = etAddress.getText().toString();

                /*Create handle for the RetrofitInstance interface*/
                LoginRegisterRequest service = NodeApiClient.getRetrofitInstance().create(LoginRegisterRequest.class);
                Call<RegisterModel> call = service.register(roleid, username, password, full_name, email, address);
                call.enqueue(new Callback<RegisterModel>() {
                    @Override
                    public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                        Toast.makeText(Registers.this, response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                        if (response.isSuccessful()){
                            Intent redirect = new Intent(getApplicationContext(), Logins.class);
                            startActivity(redirect);
                            finish();
                        }else{
                            Toast.makeText(Registers.this, "Account not found ...", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterModel> call, Throwable t) {
                        Toast.makeText(Registers.this, "Failure connection..."+ t, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void checkSignin(View view) {
        Intent i = new Intent(Registers.this, Logins.class);
        startActivity(i);
    }
}

package com.example.e_money_container.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.e_money_container.R;
import com.example.e_money_container.models.PaymentGateway.Datum;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Callback;

public class PaymentGateway extends AppCompatActivity {

    private ShopAdapter adapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDoalog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_gateway);

        /* INIT PROGRESS LOADER */
        progressDoalog = new ProgressDialog(this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();
        /* END PROGRESS LOADER */

        /*Create handle for the RetrofitInstance interface*/
        ShopRequest service = ApiClient.getRetrofitInstance().create(ShopRequest.class);
        Call<ProductModel> call = service.products();
        call.enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                Toast.makeText(Shops.this, "Success...", Toast.LENGTH_SHORT).show();
                generateDataList(response.body().getData().getDatas());
                progressDoalog.dismiss();
            }

            @Override
            public void onFailure(Call<ProductModel> call, Throwable t) {
                Toast.makeText(Shops.this, ""+ t, Toast.LENGTH_SHORT).show();
                progressDoalog.dismiss();
            }
        });
    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataList(List<Datum> dataList) {
        recyclerView = (RecyclerView) findViewById(R.id.customRecyclerView);
        adapter = new ShopAdapter(Shops.this, dataList);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}

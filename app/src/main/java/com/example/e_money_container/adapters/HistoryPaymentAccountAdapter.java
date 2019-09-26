package com.example.e_money_container.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.example.e_money_container.R;
import com.example.e_money_container.helpers.PreferenceHelper;
import com.example.e_money_container.models.Payment.MutationtModel;
import com.example.e_money_container.models.Payment.PaymentMoveModel;
import com.example.e_money_container.models.PaymentHistory.Datum;
import com.example.e_money_container.request.PaymentRequest;
import com.example.e_money_container.retrofit.NodeApiClient;
import com.example.e_money_container.retrofit.PhpApiClient;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.time.Instant;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryPaymentAccountAdapter extends RecyclerView.Adapter<HistoryPaymentAccountAdapter.CustomViewHolder> {

    private List<com.example.e_money_container.models.PaymentHistory.Datum> dataList;
    private Context context;

    public HistoryPaymentAccountAdapter(Context context, List<Datum> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        private TextView txt_createdAt, txt_from_payment_gateway_name, txt_nominal;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            txt_createdAt = mView.findViewById(R.id.txt_createdAt);
            txt_from_payment_gateway_name = mView.findViewById(R.id.txt_from_payment_gateway_name);
            txt_nominal = mView.findViewById(R.id.txt_nominal);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_payment_history, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat output = new SimpleDateFormat("yyyy/MM/dd");

        Date d = null;
        try
        {
            d = input.parse(dataList.get(position).getCreatedAt());
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        String formatted = output.format(d);

        holder.txt_createdAt.setText(formatted);
        holder.txt_from_payment_gateway_name.setText(dataList.get(position).getFromPaymentGatewayName());
        holder.txt_nominal.setText("Rp. "+ dataList.get(position).getNominal().toString());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}


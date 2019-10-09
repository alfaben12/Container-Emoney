package com.example.e_money_container.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.e_money_container.R;
import com.example.e_money_container.activities.ConfirmPayment;
import com.example.e_money_container.helpers.PreferenceHelper;
import com.example.e_money_container.models.PaymentThirdParty.Datum;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PaymentGatewayApiAdapter extends RecyclerView.Adapter<PaymentGatewayApiAdapter.CustomViewHolder> {

    private List<Datum> dataList;
    private Context context;

    public PaymentGatewayApiAdapter(Context context, List<Datum> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        private TextView txtName, txtBalance;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            txtName = mView.findViewById(R.id.txtName);
            txtBalance = mView.findViewById(R.id.txtBalance);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_gateways_api, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.txtName.setText(dataList.get(position).getPaymentGatewayName());
        holder.txtBalance.setText(dataList.get(position).getBalance().toString());
        final String payment_gateway = dataList.get(position).getPaymentGatewayName();

        holder.txtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferenceHelper prefShared = new PreferenceHelper(context);
                prefShared.setStr("accountFromPaymentGateway", payment_gateway);
                String accountToPaymentGateway = prefShared.getStr("accountToPaymentGateway");
                if (payment_gateway.equals(accountToPaymentGateway)){
                    Intent intent = new Intent(context, ConfirmPayment.class);
                    context.startActivity(intent);
                }else{
                    Toast.makeText(context, "Cant confirm from " + payment_gateway +" to "+ accountToPaymentGateway, Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}


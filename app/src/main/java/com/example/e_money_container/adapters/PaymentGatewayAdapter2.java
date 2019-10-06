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
import com.example.e_money_container.activities.Dashboards;
import com.example.e_money_container.activities.PaymentDashboard;
import com.example.e_money_container.activities.PaymentIntegration;
import com.example.e_money_container.activities.PaymentThirdPartyIntegration;
import com.example.e_money_container.activities.ThankYou;
import com.example.e_money_container.helpers.PreferenceHelper;
import com.example.e_money_container.models.Payment.MutationtModel;
import com.example.e_money_container.models.Payment.PaymentMoveModel;
import com.example.e_money_container.models.PaymentGateway.Datum;
import com.example.e_money_container.request.PaymentRequest;
import com.example.e_money_container.retrofit.NodeApiClient;
import com.example.e_money_container.retrofit.PhpApiClient;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentGatewayAdapter2 extends RecyclerView.Adapter<PaymentGatewayAdapter2.CustomViewHolder> {

    private List<Datum> dataList;
    private Context context;

    public PaymentGatewayAdapter2(Context context, List<Datum> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        private TextView txtName;
        private ImageView imgLogo;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            txtName = mView.findViewById(R.id.txtName);
            imgLogo = mView.findViewById(R.id.imgLogo);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_gateways, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.txtName.setText(dataList.get(position).getPaymentGatewayName());

        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(dataList.get(position).getImage())
                .placeholder((R.drawable.ic_card_loader))
                .error(R.drawable.ic_card_loader)
                .into(holder.imgLogo);

        final String payment_gateway = dataList.get(position).getPaymentGatewayName();
        holder.imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferenceHelper prefShared = new PreferenceHelper(context);
                prefShared.setStr("paymentGatewayContainerName", payment_gateway);

                Intent intent = new Intent(context, PaymentIntegration.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}


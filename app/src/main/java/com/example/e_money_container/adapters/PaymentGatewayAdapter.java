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

public class PaymentGatewayAdapter extends RecyclerView.Adapter<PaymentGatewayAdapter.CustomViewHolder> {

    private List<Datum> dataList;
    private Context context;

    public PaymentGatewayAdapter(Context context, List<Datum> dataList) {
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
                prefShared.setStr("accountToPaymentGateway", payment_gateway);

                Intent intent = new Intent(context, PaymentThirdPartyIntegration.class);
                context.startActivity(intent);
//                PreferenceHelper prefShared = new PreferenceHelper(context);
//                final String accountCode = prefShared.getStr("guestAccCode");
//                final Integer accountNominal = Integer.parseInt(prefShared.getStr("guestAccNominal"));
//
//                /*Create handle for the RetrofitInstance interface*/
//                PaymentRequest service = PhpApiClient.getRetrofitInstance().create(PaymentRequest.class);
//                Call<MutationtModel> call = service.mutation(accountNominal, payment_gateway);
//                call.enqueue(new Callback<MutationtModel>() {
//                    @Override
//                    public void onResponse(Call<MutationtModel> call, Response<MutationtModel> response) {
//                        if (response.isSuccessful()){
//                            final String accountid = response.body().getAccountid();
//                            final String uuid = response.body().getUuid();
//
//                            /*Create handle for the RetrofitInstance interface*/
//                            PaymentRequest service = NodeApiClient.getRetrofitInstance().create(PaymentRequest.class);
//                            Call<PaymentMoveModel> call1 = service.pay_move(payment_gateway, accountNominal, uuid, accountid);
//                            call1.enqueue(new Callback<PaymentMoveModel>() {
//
//                                @Override
//                                public void onResponse(Call<PaymentMoveModel> call1, Response<PaymentMoveModel> response1) {
//                                    if (response1.isSuccessful()){
//                                        Toast.makeText(context, response1.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
//
//                                        Intent i = new Intent(context, ThankYou.class);
//                                        context.startActivity(i);
//                                    }else{
//                                        Toast.makeText(context, "Your mutation exist, but payment failed.", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//
//                                @Override
//                                public void onFailure(Call<PaymentMoveModel> call, Throwable t) {
//                                    Toast.makeText(context, ""+ t, Toast.LENGTH_SHORT).show();
//                                }
//                            });
//
//                        }else{
//                            Toast.makeText(context, "Your mutation not found ", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<MutationtModel> call, Throwable t) {
//                        Toast.makeText(context, ""+ t, Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}


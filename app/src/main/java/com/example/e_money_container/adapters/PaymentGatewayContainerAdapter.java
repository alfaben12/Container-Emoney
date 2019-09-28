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
import com.example.e_money_container.activities.AccountSaving;
import com.example.e_money_container.activities.Dashboards;
import com.example.e_money_container.activities.PaymentGatewayContainer;
import com.example.e_money_container.activities.SetupEmoneyContainer;
import com.example.e_money_container.helpers.PreferenceHelper;
import com.example.e_money_container.models.Account.AccountModel;
import com.example.e_money_container.models.Payment.MutationtModel;
import com.example.e_money_container.models.Payment.PaymentMoveModel;
import com.example.e_money_container.models.PaymentGatewayContainer.Datum;
import com.example.e_money_container.request.AccountRequest;
import com.example.e_money_container.request.PaymentRequest;
import com.example.e_money_container.retrofit.NodeApiClient;
import com.example.e_money_container.retrofit.PhpApiClient;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentGatewayContainerAdapter extends RecyclerView.Adapter<PaymentGatewayContainerAdapter.CustomViewHolder> {

    private List<Datum> dataList;
    private Context context;

    public PaymentGatewayContainerAdapter(Context context, List<Datum> dataList) {
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
        View view = layoutInflater.inflate(R.layout.item_gateways_container, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.txtName.setText(dataList.get(position).getName());

        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(dataList.get(position).getImage())
                .placeholder((R.drawable.ic_card_loader))
                .error(R.drawable.ic_card_loader)
                .into(holder.imgLogo);

        final Integer paymentGatewayContainerID = dataList.get(position).getId();
        holder.imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferenceHelper prefShared = new PreferenceHelper(context);
                String accountContainerApiKey = prefShared.getStr("accountContainerApiKey");
                String accountJwtToken = prefShared.getStr("accountJwtToken");

                /*Create handle for the RetrofitInstance interface*/
                AccountRequest service = NodeApiClient.getRetrofitInstance().create(AccountRequest.class);
                Call<AccountModel> call = service.updatePaymentContainer(accountJwtToken, paymentGatewayContainerID, accountContainerApiKey);
                call.enqueue(new Callback<AccountModel>() {
                    @Override
                    public void onResponse(Call<AccountModel> call, Response<AccountModel> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(context, "Your third-party successfully integrated.", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(context, Dashboards.class);
                            context.startActivity(i);
                        }else{
                            Toast.makeText(context, "Failed account can't integrated or third-party not found.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AccountModel> call, Throwable t) {
                        Toast.makeText(context, ""+ t, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}


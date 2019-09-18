package com.example.e_money_container.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.e_money_container.R;
import com.example.e_money_container.models.PaymentGateway.Datum;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PaymentGatewayAdapter extends RecyclerView.Adapter<PaymentGatewayAdapter.CustomViewHolder> {

    private List<Datum> dataList;
    private Context context;

    public PaymentGatewayAdapter(Context context, List<Datum> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        private TextView txtPrice, txtName;
        private ImageView imageProduct;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            txtPrice = mView.findViewById(R.id.txtPrice);
            txtName = mView.findViewById(R.id.txtName);
            imageProduct = mView.findViewById(R.id.imageProduct);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_shop, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.txtPrice.setText("Rp. "+ dataList.get(position).getPrice());
        holder.txtName.setText(dataList.get(position).getName());

        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(dataList.get(position).getImage())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(holder.imageProduct);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


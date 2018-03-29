package com.twc.rca.product.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.twc.rca.R;
import com.twc.rca.product.activities.DVInputActivity;
import com.twc.rca.product.activities.DVProductActivity;
import com.twc.rca.product.model.DVProduct;

import java.util.List;

/**
 * Created by Sushil on 22-02-2018.
 */

public class DubaiVisaAdapter extends RecyclerView.Adapter<DubaiVisaAdapter.MyViewHolder>{

    Context context;

    private List<DVProduct> dvProductList;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tv_visa_period,tv_visa_lable,tv_visa_type,tv_visa_processing_time,tv_visa_validity,tv_visa_amount;

        public MyViewHolder(View view) {
            super(view);
            tv_visa_period = (TextView) view.findViewById(R.id.tv_product_period);
            tv_visa_lable = (TextView) view.findViewById(R.id.tv_product_lable);
            tv_visa_type = (TextView) view.findViewById(R.id.tv_visa_type);
            tv_visa_processing_time=(TextView)view.findViewById(R.id.tv_visa_processing_time);
            tv_visa_validity=(TextView)view.findViewById(R.id.tv_visa_validity);
            tv_visa_amount=(TextView)view.findViewById(R.id.tv_product_amt);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent=new Intent(context, DVProductActivity.class);
            view.getContext().startActivity(intent);
        }
    }

    public DubaiVisaAdapter(Context context,List<DVProduct> dvProductList) {
        this.context=context;
        this.dvProductList = dvProductList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dv_product_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DVProduct dvProduct = dvProductList.get(position);
        holder.tv_visa_period.setText(dvProduct.getVisa_period());
        holder.tv_visa_lable.setText(dvProduct.getVisa_lable());
        holder.tv_visa_type.setText(dvProduct.getVisa_type());
        holder.tv_visa_processing_time.setText(dvProduct.getVisa_processing_time());
        holder.tv_visa_validity.setText(dvProduct.getVisa_validity());
        holder.tv_visa_amount.setText(dvProduct.getVisa_amount());
    }

    @Override
    public int getItemCount() {
        return dvProductList.size();
    }
}

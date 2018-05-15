package com.twc.rca.product.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.twc.rca.R;
import com.twc.rca.product.activities.OrderApplicantListActivity;
import com.twc.rca.product.model.OrderModel;
import com.twc.rca.utils.ApiUtils;

import java.util.List;

/**
 * Created by Sushil on 15-05-2018.
 */

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.MyViewHolder> {

    Context context;

    private List<OrderModel> orderModelList;

    public OrderListAdapter(Context context, List<OrderModel> orderModelList) {
        this.context = context;
        this.orderModelList = orderModelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_order_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        OrderModel orderModel = orderModelList.get(position);
        holder.tv_account_order_pName.setText(orderModel.getProduct_name());
        String noTravellers = ApiUtils.getNoTravellers(Integer.parseInt(orderModel.getAdult_count()), Integer.parseInt(orderModel.getChild_count()), Integer.parseInt(orderModel.getInfant_count()));
        holder.tv_acoount_traveller_count.setText(noTravellers);
        if (orderModel.getProduct_id().equalsIgnoreCase("1"))
            holder.tv_account_travel_dt.setText(ApiUtils.getFormattedDate(ApiUtils.getDate(orderModel.getArrival_date(), "yyyy-MM-DD HH:MM:SS", "DD-MM-yyyy")));
        else
            holder.tv_account_travel_dt.setText(ApiUtils.getFormattedDate(ApiUtils.getDate(orderModel.getTravel_date(), "yyyy-MM-DD HH:MM:SS", "DD-MM-yyyy")));
    }

    @Override
    public int getItemCount() {
        return orderModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_account_order_pName, tv_acoount_traveller_count, tv_account_travel_dt;

        public MyViewHolder(View view) {
            super(view);

            tv_account_order_pName = (TextView) view.findViewById(R.id.tv_account_order_product_name);
            tv_acoount_traveller_count = (TextView) view.findViewById(R.id.tv_account_traveller_count);
            tv_account_travel_dt = (TextView) view.findViewById(R.id.tv_account_travel_dt);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, OrderApplicantListActivity.class);
                    intent.putExtra("orderId", orderModelList.get(getAdapterPosition()).getOrder_id());
                    view.getContext().startActivity(intent);
                }
            });
        }
    }

}

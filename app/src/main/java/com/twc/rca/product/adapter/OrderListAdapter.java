package com.twc.rca.product.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.twc.rca.R;
import com.twc.rca.activities.BaseActivity;
import com.twc.rca.product.activities.OrderApplicantListActivity;
import com.twc.rca.product.activities.PaymentActivity;
import com.twc.rca.product.model.OrderModel;
import com.twc.rca.product.task.RePaymentTask;
import com.twc.rca.utils.ApiUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.twc.rca.product.activities.OrderDetailActivity.PAYMENT_URL;
import static com.twc.rca.product.task.RePaymentTask.ORDER_ID;

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
        final OrderModel orderModel = orderModelList.get(position);
        holder.tv_account_order_pName.setText(orderModel.getProduct_name());
        String noTravellers = ApiUtils.getNoTravellers(Integer.parseInt(orderModel.getAdult_count()), Integer.parseInt(orderModel.getChild_count()), Integer.parseInt(orderModel.getInfant_count()));
        holder.tv_acoount_traveller_count.setText(noTravellers);
        if (orderModel.getProduct_id().equalsIgnoreCase("1"))
            holder.tv_account_travel_dt.setText(ApiUtils.getFormattedDate(ApiUtils.getDate(orderModel.getArrival_date(), "yyyy-MM-DD", "DD-MM-yyyy")));
        else
            holder.tv_account_travel_dt.setText(ApiUtils.getFormattedDate(ApiUtils.getDate(orderModel.getTravel_date(), "yyyy-MM-DD HH:MM:SS", "DD-MM-yyyy")));

        if (orderModel.getPayment_status().equalsIgnoreCase(ApiUtils.FAILURE))
            holder.btn_pay_now.setVisibility(View.VISIBLE);

        holder.btn_pay_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BaseActivity) context).showProgressDialog(context.getResources().getString(R.string.please_wait));
                new RePaymentTask(context, orderModel.getOrder_id()).doRePayment(rePaymentResponseCallback);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_account_order_pName, tv_acoount_traveller_count, tv_account_travel_dt;
        public AppCompatButton btn_pay_now;

        public MyViewHolder(View view) {
            super(view);

            tv_account_order_pName = (TextView) view.findViewById(R.id.tv_account_order_product_name);
            tv_acoount_traveller_count = (TextView) view.findViewById(R.id.tv_account_traveller_count);
            tv_account_travel_dt = (TextView) view.findViewById(R.id.tv_account_travel_dt);
            btn_pay_now = (AppCompatButton) view.findViewById(R.id.btn_re_pay_now);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!orderModelList.get(getAdapterPosition()).getPayment_status().equalsIgnoreCase(ApiUtils.FAILURE)) {
                        Intent intent = new Intent(context, OrderApplicantListActivity.class);
                        intent.putExtra("orderId", orderModelList.get(getAdapterPosition()).getOrder_id());
                        view.getContext().startActivity(intent);
                    }
                }
            });
        }
    }

    RePaymentTask.RePaymentResponseCallback rePaymentResponseCallback = new RePaymentTask.RePaymentResponseCallback() {
        @Override
        public void onSuccessRePaymentResponse(String response) {
            ((BaseActivity) context).dismissProgressDialog();
            try {
                JSONObject jsonObject = new JSONObject(response);

                JSONObject contentObject = (JSONObject) jsonObject.get(ApiUtils.CONTENT);

                JSONArray data = (JSONArray) contentObject.get(ApiUtils.RESULT_SET);

                JSONObject jsonobject = data.getJSONObject(0);

                String payment_url = jsonobject.getString(PAYMENT_URL);
                String order_id = jsonobject.getString(ORDER_ID);

                Intent intent = new Intent(context, PaymentActivity.class);
                intent.putExtra(PAYMENT_URL, payment_url);
                intent.putExtra(ORDER_ID, order_id);
                context.startActivity(intent);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailureRePaymentResponse(String response) {
            ((BaseActivity) context).dismissProgressDialog();
        }
    };
}

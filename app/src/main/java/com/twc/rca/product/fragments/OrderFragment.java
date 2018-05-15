package com.twc.rca.product.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twc.rca.R;
import com.twc.rca.activities.BaseFragment;
import com.twc.rca.product.adapter.OrderListAdapter;
import com.twc.rca.product.model.OrderModel;
import com.twc.rca.product.task.UserOrderListTask;
import com.twc.rca.utils.ApiUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sushil on 11-05-2018.
 */

public class OrderFragment extends BaseFragment {

    RecyclerView list_order;

    private List<OrderModel> userOrderList = new ArrayList<>();

    private OrderListAdapter orderListAdapter;

    public static OrderFragment getInstance() {
        OrderFragment orderFragment = new OrderFragment();
        return orderFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        list_order = (RecyclerView) view.findViewById(R.id.list_order);

        showProgressDialog();
        new UserOrderListTask(getContext()).getUserOrders(userOrderResponseCallback);
        return view;
    }

    UserOrderListTask.UserOrderResponseCallback userOrderResponseCallback = new UserOrderListTask.UserOrderResponseCallback() {
        @Override
        public void onSuccessUserOrderListResponse(String response) {
            dismissProgressDialog();
            try {
                JSONObject jsonObject = new JSONObject(response);

                JSONObject contentObject = (JSONObject) jsonObject.get(ApiUtils.CONTENT);

                OrderModel orderModel;

                JSONArray data = (JSONArray) contentObject.get(ApiUtils.RESULT_SET);

                if (data.length() > 0) {
                    for (int i = 0; i < data.length(); i++) {
                        orderModel = new OrderModel();
                        JSONObject jObject = data.getJSONObject(i);
                        orderModel.order_id = jObject.getString("order_id");
                        orderModel.user_id = jObject.getString("user_id");
                        orderModel.product_id = jObject.getString("product_id");

                        JSONObject productObject = jObject.getJSONObject("product_details_arr");
                        orderModel.product_name = productObject.getString("product_name");
                        orderModel.product_type = productObject.getString("product_type");
                        orderModel.product_validity = productObject.getString("product_validity");

                        orderModel.adult_count = jObject.getString("adult");
                        orderModel.child_count = jObject.getString("child");
                        orderModel.infant_count = jObject.getString("infant");
                        orderModel.nationality = jObject.getString("nationality");
                        orderModel.total_price = jObject.getString("total_price");
                        orderModel.travel_date = jObject.getString("travel_date");
                        orderModel.arrival_date = jObject.getString("arrival_date");
                        orderModel.arrival_time = jObject.getString("arrival_time");
                        orderModel.dept_date = jObject.getString("departure_date");
                        orderModel.dept_time = jObject.getString("departure_time");
                        orderModel.arriving_airport = jObject.getString("arriving_airport");
                        orderModel.departing_airport = jObject.getString("departing_airport");
                        orderModel.arrival_airline = jObject.getString("arrival_airline");
                        orderModel.departure_airline = jObject.getString("departure_airline");
                        orderModel.airport_coming_from = jObject.getString("airport_coming_from");
                        orderModel.airport_going_to = jObject.getString("airport_going_to");
                        orderModel.arrival_flight_no = jObject.getString("arrival_flight_no");
                        orderModel.departure_flight_no = jObject.getString("departure_flight_no");
                        orderModel.applicant_booking_status = jObject.getString("applicant_booking_status");
                        orderModel.payment_status = jObject.getString("payment_status");

                        userOrderList.add(orderModel);

                    }

                    orderListAdapter = new OrderListAdapter(getContext(), userOrderList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                    list_order.setLayoutManager(mLayoutManager);
                    list_order.setItemAnimator(new DefaultItemAnimator());
                    list_order.setAdapter(orderListAdapter);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailureUserOrderListResponse(String response) {
            dismissProgressDialog();
            try {
                JSONObject jsonObject = new JSONObject(response);

                JSONObject contentObject = (JSONObject) jsonObject.get(ApiUtils.CONTENT);

                showToast(contentObject.getString("message"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}

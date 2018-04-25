package com.twc.rca.product.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twc.rca.R;
import com.twc.rca.activities.BaseActivity;
import com.twc.rca.product.model.DVProduct;
import com.twc.rca.product.model.Transaction;
import com.twc.rca.product.task.OrderTask;
import com.twc.rca.utils.ApiUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by TWC on 28-02-2018.
 */

public class OrderDetailActivity extends BaseActivity {

    public static String ORDER_ID = "order_id", APPLICANT_PROFILE_IDS = "applicant_profile_ids";

    AppCompatButton btn_pay;

    DVProduct dvProduct;

    double total_price;

    String traveller_count, nationality, travel_dt, arrival_dt, arrival_tm, dept_dt, dept_tm, arriving_airport, dept_airport, airport_coming_from, airport_going_to;

    TextView tv_dv_amount, tv_dv_processing_time, tv_dv_visa_validity, tv_dv_traveller_count, tv_dv_travel_date, tv_dv_nationality;

    EditText et_dv_traveller_count, et_dv_travel_date, et_dv_nationality;

    EditText et_traveller_count, et_nationality, et_dt_arrival, et_tm_arrival, et_dt_dept, et_tm_dept, et_airport_arrival, et_airport_dept, et_coming_from, et_going_to;

    Button btn_book_now;

    LinearLayout ll_dv_product, ll_96hr_dv_product;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dv_product_input_form);
        initView();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();

            // clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            // finally change the color

            window.setStatusBarColor(this.getResources().getColor(R.color.text_color));
        }
    }

    void initView() {
        tv_dv_amount = (TextView) findViewById(R.id.tv_dv_amount);
        tv_dv_processing_time = (TextView) findViewById(R.id.tv_dv_processing_time);
        tv_dv_visa_validity = (TextView) findViewById(R.id.tv_dv_visa_validity);

        ll_dv_product = (LinearLayout) findViewById(R.id.layout_dv_product);
        ll_96hr_dv_product = (LinearLayout) findViewById(R.id.layout_96hr_dv_product);

        Bundle bundle = getIntent().getExtras();
        dvProduct = bundle.getParcelable("productDetails");

        total_price = getIntent().getDoubleExtra(DVProductActivity.TOTAL_PRICE, 0);
        if (dvProduct.getProduct_id().equalsIgnoreCase("1")) {
            traveller_count = getIntent().getStringExtra(DVProductActivity.TRAVELLER_COUNT);
            nationality = getIntent().getStringExtra(DVProductActivity.NATIONALITY);
            arrival_dt = getIntent().getStringExtra(DVProductActivity.ARRIVAL_DT);
            arrival_tm = getIntent().getStringExtra(DVProductActivity.ARRIVAL_TM);
            dept_dt = getIntent().getStringExtra(DVProductActivity.DEPT_DT);
            dept_tm = getIntent().getStringExtra(DVProductActivity.DEPT_TM);
            arriving_airport = getIntent().getStringExtra(DVProductActivity.ARRIVING_AIRPORT);
            dept_airport = getIntent().getStringExtra(DVProductActivity.DEPT_AIRPORT);
            airport_coming_from = getIntent().getStringExtra(DVProductActivity.AIRPORT_COMING_FROM);
            airport_going_to = getIntent().getStringExtra(DVProductActivity.AIRPORT_GOING_TO);
        } else {
            traveller_count = getIntent().getStringExtra(DVProductActivity.TRAVELLER_COUNT);
            nationality = getIntent().getStringExtra(DVProductActivity.NATIONALITY);
            travel_dt = getIntent().getStringExtra(DVProductActivity.TRAVEL_DT);
        }

        getSupportActionBar().setTitle(R.string.review_booking_details);
        tv_dv_amount.setText(String.valueOf(total_price));
        tv_dv_visa_validity.setText(dvProduct.getProduct_validity());

        if (Integer.parseInt(dvProduct.getProduct_id()) == 1) {
            ll_96hr_dv_product.setVisibility(View.VISIBLE);
            et_traveller_count = (EditText) findViewById(R.id.et_traveller_count);
            et_nationality = (EditText) findViewById(R.id.et_nationality);
            et_dt_arrival = (EditText) findViewById(R.id.et_dt_arrival);
            et_tm_arrival = (EditText) findViewById(R.id.et_tm_arrival);
            et_airport_arrival = (EditText) findViewById(R.id.et_arrival_airport);
            et_airport_dept = (EditText) findViewById(R.id.et_departure_airport);
            et_dt_dept = (EditText) findViewById(R.id.et_dt_departure);
            et_tm_dept = (EditText) findViewById(R.id.et_tm_departure);
            et_coming_from = (EditText) findViewById(R.id.et_coming_from);
            et_going_to = (EditText) findViewById(R.id.et_going_to);

            et_traveller_count.setEnabled(false);
            et_nationality.setEnabled(false);
            et_dt_arrival.setEnabled(false);
            et_tm_arrival.setEnabled(false);
            et_airport_arrival.setEnabled(false);
            et_airport_dept.setEnabled(false);
            et_dt_dept.setEnabled(false);
            et_tm_dept.setEnabled(false);
            et_coming_from.setEnabled(false);
            et_going_to.setEnabled(false);

            et_traveller_count.setText(traveller_count);
            et_nationality.setText(nationality);
            et_dt_arrival.setText(arrival_dt);
            et_tm_arrival.setText(arrival_tm);
            et_airport_arrival.setText(arriving_airport);
            et_airport_dept.setText(dept_airport);
            et_dt_dept.setText(dept_dt);
            et_tm_dept.setText(dept_tm);
            et_coming_from.setText(airport_coming_from);
            et_going_to.setText(airport_going_to);

        } else {
            ll_dv_product.setVisibility(View.VISIBLE);
            et_dv_traveller_count = (EditText) findViewById(R.id.et_dv_traveller_count);
            et_dv_travel_date = (EditText) findViewById(R.id.et_travel_dt);
            et_dv_nationality = (EditText) findViewById(R.id.et_dv_nationality);

            et_dv_travel_date.setEnabled(false);
            et_dv_traveller_count.setEnabled(false);
            et_dv_nationality.setEnabled(false);

            et_dv_travel_date.setText(travel_dt);
            et_dv_traveller_count.setText(traveller_count);
            et_dv_nationality.setText(nationality);
        }

        btn_book_now = (AppCompatButton) findViewById(R.id.btn_book_now);
        btn_book_now.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        btn_book_now.setEnabled(true);

        btn_book_now.setText(R.string.pay_now);

        btn_book_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressDialog(getString(R.string.please_wait));
                new OrderTask(getApplicationContext(), dvProduct.getProduct_id().toString(), Transaction.getmTransactionInstance().getNoOfAdults(), Transaction.getmTransactionInstance().getNoOfChildrens(), Transaction.getmTransactionInstance().getNoOfInfants(),
                        total_price, travel_dt, nationality, arrival_dt, arrival_tm, dept_dt, dept_tm, arriving_airport, dept_airport, "", "", 0, airport_coming_from, airport_going_to).createOrder(orderResponseCallback);
            }
        });
    }


   /* void initview() {
        btn_pay = (AppCompatButton) findViewById(R.id.btn_proceed_to_pay);

        total_price = getIntent().getDoubleExtra(DVProductActivity.TOTAL_PRICE, 0);
        if (dvProduct.getProduct_id().equalsIgnoreCase("1")) {
            arrival_dt = getIntent().getStringExtra(DVProductActivity.ARRIVAL_DT);
            arrival_tm = getIntent().getStringExtra(DVProductActivity.ARRIVAL_TM);
            dept_dt = getIntent().getStringExtra(DVProductActivity.DEPT_DT);
            dept_tm = getIntent().getStringExtra(DVProductActivity.DEPT_TM);
            arriving_airport = getIntent().getStringExtra(DVProductActivity.ARRIVING_AIRPORT);
            dept_airport = getIntent().getStringExtra(DVProductActivity.DEPT_AIRPORT);
            airport_coming_from = getIntent().getStringExtra(DVProductActivity.AIRPORT_COMING_FROM);
            airport_going_to = getIntent().getStringExtra(DVProductActivity.AIRPORT_GOING_TO);
        } else {
            travel_dt = getIntent().getStringExtra(DVProductActivity.TRAVEL_DT);
        }

        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressDialog();
                new OrderTask(getApplicationContext(), dvProduct.getProduct_id().toString(), Transaction.getmTransactionInstance().getNoOfAdults(), Transaction.getmTransactionInstance().getNoOfChildrens(), Transaction.getmTransactionInstance().getNoOfInfants(),
                        total_price, travel_dt, arrival_dt, arrival_tm, dept_dt, dept_tm, arriving_airport, dept_airport, "", "", 0, airport_coming_from, airport_going_to).createOrder(orderResponseCallback);
            }
        });
    }*/

    OrderTask.OrderResponseCallback orderResponseCallback = new OrderTask.OrderResponseCallback() {
        @Override
        public void onSuccessOrderResponse(String response) {
            dismissProgressDialog();
            try {
                JSONObject jsonObject = new JSONObject(response);

                JSONObject contentObject = (JSONObject) jsonObject.get(ApiUtils.CONTENT);

                JSONArray data = (JSONArray) contentObject.get(ApiUtils.RESULT_SET);

                JSONObject jsonobject = data.getJSONObject(0);

                String order_id = jsonobject.getString(ORDER_ID);
                String applicant_profile_ids = jsonobject.getString(APPLICANT_PROFILE_IDS);
                String[] applicant_ids = applicant_profile_ids.split(",");
                Intent intent = new Intent(OrderDetailActivity.this, PaymentActivity.class);
                intent.putExtra(ORDER_ID, order_id);
                intent.putExtra(APPLICANT_PROFILE_IDS, applicant_ids);
                startActivity(intent);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailureOrderResponse(String response) {
            dismissProgressDialog();
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}

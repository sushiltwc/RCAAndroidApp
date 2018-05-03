package com.twc.rca.product.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twc.rca.R;
import com.twc.rca.activities.BaseActivity;
import com.twc.rca.database.OrderHelper;
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

    TextView tv_actionbar_title;

    ImageButton img_button_back,img_button_edit;

    String traveller_count, nationality, travel_dt, arrival_dt, arrival_tm, dept_dt, dept_tm, arriving_airport, dept_airport, airport_coming_from, airport_going_to;

    TextView tv_dv_amount, tv_dv_processing_time, tv_dv_visa_validity, tv_dv_traveller_count, tv_dv_travel_date, tv_dv_nationality;

    EditText et_dv_traveller_count, et_dv_travel_date, et_dv_nationality;

    EditText et_traveller_count, et_nationality, et_dt_arrival, et_tm_arrival, et_dt_dept, et_tm_dept, et_airport_arrival, et_airport_dept, et_coming_from, et_going_to;

    TextView tv_od_traveller_count,tv_od_nationality,tv_od_travel_dt,tv_od__dt_arrival, tv_od_tm_arrival, tv_od_dt_dept, tv_od_tm_dept, tv_od_airport_arrival, tv_od_airport_dept, tv_od_coming_from, tv_od_going_to;

    AppCompatButton btn_pay_now;

    LinearLayout ll_dv_product, ll_96hr_dv_product;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_detail);
        initView();
        final ActionBar actionBar = getSupportActionBar();
        if(actionBar !=null)
        {
            View viewActionBar = getLayoutInflater().inflate(R.layout.layout_actionbar, null);
            ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT,
                    ActionBar.LayoutParams.WRAP_CONTENT,
                    Gravity.CENTER);
            tv_actionbar_title = (TextView) viewActionBar.findViewById(R.id.tv_actionbar_title);
            img_button_back=(ImageButton)viewActionBar.findViewById(R.id.img_btn_back);
            img_button_edit=(ImageButton)viewActionBar.findViewById(R.id.img_btn_edit);
            img_button_back.setVisibility(View.VISIBLE);
            img_button_edit.setVisibility(View.VISIBLE);

            tv_actionbar_title.setText(getString(R.string.order_review));
            actionBar.setCustomView(viewActionBar, params);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            Toolbar toolbar=(Toolbar)actionBar.getCustomView().getParent();
            toolbar.setContentInsetsAbsolute(0, 0);
            toolbar.getContentInsetEnd();
            toolbar.setPadding(0, 0, 0, 0);

            img_button_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

            img_button_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }


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

    @Override
    protected boolean isHomeAsUpEnabled() {
        return false;
    }

    void initView() {
        tv_dv_amount = (TextView) findViewById(R.id.tv_order_amount);
        tv_dv_processing_time = (TextView) findViewById(R.id.tv_od_processing_time);
        tv_dv_visa_validity = (TextView) findViewById(R.id.tv_od_visa_validity);

        ll_dv_product = (LinearLayout) findViewById(R.id.layout_od_product);
        ll_96hr_dv_product = (LinearLayout) findViewById(R.id.layout_od_96hr_dv_product);

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

        getSupportActionBar().setTitle(R.string.order_review);
        tv_dv_amount.setText(String.valueOf(total_price));
        tv_dv_visa_validity.setText(dvProduct.getProduct_validity());

        tv_od_traveller_count = (TextView) findViewById(R.id.tv_od_traveller_counts);
        tv_od_nationality = (TextView) findViewById(R.id.tv_od_nationalitys);

        if (Integer.parseInt(dvProduct.getProduct_id()) == 1) {
            ll_96hr_dv_product.setVisibility(View.VISIBLE);
            tv_od__dt_arrival = (TextView) findViewById(R.id.tv_od_arrival_dts);
            tv_od_tm_arrival = (TextView) findViewById(R.id.tv_od_arrival_tm);
            tv_od_airport_arrival = (TextView) findViewById(R.id.tv_od_arriving_airports);
            tv_od_airport_dept = (TextView) findViewById(R.id.tv_od_departure_airports);
            tv_od_dt_dept = (TextView) findViewById(R.id.tv_od_departure_dts);
            tv_od_tm_dept = (TextView) findViewById(R.id.tv_od_departure_tm);
            tv_od_coming_from = (TextView) findViewById(R.id.tv_od_airport_coming_froms);
            tv_od_going_to = (TextView) findViewById(R.id.tv_od_traveller_airport_going_tos);

            tv_od_traveller_count.setEnabled(false);
            tv_od_nationality.setEnabled(false);
            tv_od__dt_arrival.setEnabled(false);
            tv_od_tm_arrival.setEnabled(false);
            tv_od_airport_arrival.setEnabled(false);
            tv_od_airport_dept.setEnabled(false);
            tv_od_dt_dept.setEnabled(false);
            tv_od_tm_dept.setEnabled(false);
            tv_od_coming_from.setEnabled(false);
            tv_od_going_to.setEnabled(false);

            tv_od_traveller_count.setText(traveller_count);
            tv_od_nationality.setText(nationality);
            tv_od__dt_arrival.setText(arrival_dt);
            tv_od_tm_arrival.setText(arrival_tm);
            tv_od_airport_arrival.setText(arriving_airport);
            tv_od_airport_dept.setText(dept_airport);
            tv_od_dt_dept.setText(dept_dt);
            tv_od_tm_dept.setText(dept_tm);
            tv_od_coming_from.setText(airport_coming_from);
            tv_od_going_to.setText(airport_going_to);

        } else {
            ll_dv_product.setVisibility(View.VISIBLE);
            tv_od_travel_dt = (TextView) findViewById(R.id.tv_od_travel_date);

            tv_od_travel_dt.setEnabled(false);
            tv_od_traveller_count.setEnabled(false);
            tv_od_nationality.setEnabled(false);

            tv_od_travel_dt.setText(travel_dt);
            tv_od_traveller_count.setText(traveller_count);
            tv_od_nationality.setText(nationality);
        }

        btn_pay_now = (AppCompatButton) findViewById(R.id.btn_py_now);

        btn_pay_now.setOnClickListener(new View.OnClickListener() {
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
             /*   for (int i = 0; i < applicant_ids.length; i++) {
                    for (int j = 0; j < Transaction.getmTransactionInstance().getNoOfAdults(); j++)
                        OrderHelper.addOrderDetails(getApplicationContext(), order_id, applicant_ids[i], "adult", "applied");
                    i = i + Transaction.getmTransactionInstance().getNoOfAdults();
                    for (int k = 0; k < Transaction.getmTransactionInstance().getNoOfChildrens(); k++)
                        OrderHelper.addOrderDetails(getApplicationContext(), order_id, applicant_ids[i], "", "");
                    i = i + Transaction.getmTransactionInstance().getNoOfChildrens();
                    for (int l = 0; l < Transaction.getmTransactionInstance().getNoOfChildrens(); l++)
                        OrderHelper.addOrderDetails(getApplicationContext(), order_id, applicant_ids[i], "", "");
                }*/
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

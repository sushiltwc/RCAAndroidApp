package com.twc.rca.product.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.twc.rca.R;
import com.twc.rca.activities.BaseActivity;
import com.twc.rca.product.fragments.DatePickerDialogFragment;
import com.twc.rca.product.model.Transaction;
import com.twc.rca.utils.ApiUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by Sushil on 23-02-2018.
 */

public class DVInputActivity extends BaseActivity implements View.OnClickListener,DatePickerDialogFragment.DateDialogListener{

    TextView tv_dv_amount, tv_dv_processing_time, tv_dv_visa_validity;

    AutoCompleteTextView auto_dv_arriving_airport, auto_dv_departure_airport, auto_dv_airport_coming_from, auto_dv_airport_going_to;

    EditText et_dv_no_travellers, et_dv_nationality,et_dv_dt_arrival, et_dv_dt_departure;

    Button btn_book_now;

    String noOfPassengers, noOfAdults, noOfChildrens, noOfInfants;

    CharSequence list_airport[] = new CharSequence[]{"Dubai Airport", "Sharjah Airport", "Abu Dhabi Airport", "Al Maktoum Airport"};

    CharSequence list_nationality[]=new CharSequence[]{"India","Pakistan","Others"};

    private static final String DIALOG_DATE = "DVInputActivity.DatePickerDialogFragment";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dv_product_input);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();

            // clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            // finally change the color
            window.setStatusBarColor(this.getResources().getColor(R.color.text_color));
        }

        initView();
    }

    void initView() {
        tv_dv_amount = (TextView) findViewById(R.id.tv_dv_amount);
        tv_dv_processing_time = (TextView) findViewById(R.id.tv_dv_processing_time);
        tv_dv_visa_validity = (TextView) findViewById(R.id.tv_dv_visa_validity);

        et_dv_no_travellers = (EditText) findViewById(R.id.et_dv_traveller_count);
        et_dv_nationality=(EditText)findViewById(R.id.et_dv_nationality);
        et_dv_dt_arrival = (EditText) findViewById(R.id.et_dv_dt_arrival);
        et_dv_dt_departure = (EditText) findViewById(R.id.et_dv_dt_departure);

        auto_dv_arriving_airport = (AutoCompleteTextView) findViewById(R.id.auto_dv_arriving_airport);
        auto_dv_departure_airport = (AutoCompleteTextView) findViewById(R.id.auto_dv_departure_airport);
        auto_dv_airport_coming_from = (AutoCompleteTextView) findViewById(R.id.auto_dv_airport_coming_from);
        auto_dv_airport_going_to = (AutoCompleteTextView) findViewById(R.id.auto_dv_airport_going_to);

        btn_book_now = (AppCompatButton) findViewById(R.id.btn_book_now);

        et_dv_no_travellers.setOnClickListener(this);
        et_dv_nationality.setOnClickListener(this);
        et_dv_dt_arrival.setOnClickListener(this);
        et_dv_dt_departure.setOnClickListener(this);

        auto_dv_arriving_airport.setOnClickListener(this);
        auto_dv_departure_airport.setOnClickListener(this);
        auto_dv_airport_coming_from.setOnClickListener(this);
        auto_dv_airport_going_to.setOnClickListener(this);
        btn_book_now.setOnClickListener(this);

        Transaction.getmTransactionInstance().setNoOfAdults(1);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_dv_traveller_count:
                ArrayList<String> traveller_count = new ArrayList<>(Arrays.asList(et_dv_no_travellers.getText().toString().split(",")));
                if (traveller_count.size() == 3) {
                    Transaction.getmTransactionInstance().setNoOfAdults(Integer.parseInt(traveller_count.get(0).replaceAll("\\D+", "")));
                    Transaction.getmTransactionInstance().setNoOfChildrens(Integer.parseInt(traveller_count.get(1).replaceAll("\\D+", "")));
                    Transaction.getmTransactionInstance().setNoOfInfants(Integer.parseInt(traveller_count.get(2).replaceAll("\\D+", "")));
                }
                Intent intent_traveller_count = new Intent(this, AddTravellerActivity.class);
                startActivity(intent_traveller_count);
                break;

            case R.id.et_dv_nationality:
                popupDialog("Select Nationality",list_nationality, view.getId());
                break;

            case R.id.auto_dv_arriving_airport:
            case R.id.auto_dv_departure_airport:

                popupDialog("Select Aitport",list_airport,view.getId());
                break;

            case R.id.et_dv_dt_arrival:
            case R.id.et_dv_dt_departure:
                DatePickerDialogFragment dialog = new DatePickerDialogFragment();
                Bundle args = new Bundle();
                args.putInt("id", view.getId());
                dialog.setArguments(args);
                dialog.show(getSupportFragmentManager(), DIALOG_DATE);
                break;

            case R.id.btn_book_now:
                Intent intent_book_now = new Intent(this, OrderDetailActivity.class);
                startActivity(intent_book_now);
                finish();
                break;
        }
    }

    void popupDialog(String title, final CharSequence[] list, final int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setItems(list, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(id==R.id.auto_dv_arriving_airport)
                    auto_dv_arriving_airport.setText(list[which]);
                if(id==R.id.auto_dv_departure_airport)
                    auto_dv_departure_airport.setText(list[which]);
                if(id==R.id.et_dv_nationality)
                    et_dv_nationality.setText(list[which]);
            }
        });
        builder.show();
    }


    @Override
    public void onResume() {
        super.onResume();
        //If small device then request Portrait orientation
        try {
            // Hide SoftKeyBoard
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            if (Transaction.getmTransactionInstance().getNoOfPassengers() > 0) {
                if (Transaction.getmTransactionInstance().getNoOfAdults() > 1)
                    noOfAdults = Transaction.getmTransactionInstance().getNoOfAdults() + " Adults";
                else
                    noOfAdults = Transaction.getmTransactionInstance().getNoOfAdults() + " Adult";

                if (Transaction.getmTransactionInstance().getNoOfChildrens() == 1)
                    noOfChildrens = Transaction.getmTransactionInstance().getNoOfChildrens() + " Children";
                else if (Transaction.getmTransactionInstance().getNoOfChildrens() > 1)
                    noOfChildrens = Transaction.getmTransactionInstance().getNoOfChildrens() + " Childrens";
                else
                    noOfChildrens = "";

                if (Transaction.getmTransactionInstance().getNoOfInfants() == 1)
                    noOfInfants = Transaction.getmTransactionInstance().getNoOfInfants() + " Infant";
                else if (Transaction.getmTransactionInstance().getNoOfInfants() > 1)
                    noOfInfants = Transaction.getmTransactionInstance().getNoOfInfants() + " Infants";
                else
                    noOfInfants = "";

                noOfPassengers = noOfAdults;
                if (!ApiUtils.isValidStringValue(noOfChildrens))
                    noOfPassengers = noOfPassengers + "," + noOfChildrens;
                if (!ApiUtils.isValidStringValue(noOfInfants))
                    noOfPassengers = noOfPassengers + "," + noOfInfants;

                et_dv_no_travellers.setText(noOfPassengers);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFinishDialog(Date date,int id) {

        if(id==R.id.et_dv_dt_arrival)
            et_dv_dt_arrival.setText(ApiUtils.formatDate(date));
        if(id==R.id.et_dv_dt_departure)
            et_dv_dt_departure.setText(ApiUtils.formatDate(date));

    }
}

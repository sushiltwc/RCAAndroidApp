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
import android.widget.Button;
import android.widget.TextView;

import com.twc.rca.R;
import com.twc.rca.activities.BaseActivity;
import com.twc.rca.product.fragments.DatePickerDialogFragment;
import com.twc.rca.product.model.DVProduct;
import com.twc.rca.product.model.Transaction;
import com.twc.rca.utils.ApiUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by Sushil on 09-03-2018.
 */

public class DVProductActivity extends BaseActivity implements View.OnClickListener, DatePickerDialogFragment.DateDialogListener {

    TextView tv_dv_amount, tv_dv_processing_time, tv_dv_visa_validity, tv_dv_traveller_count, tv_dv_travel_date, tv_dv_nationality;

    Button btn_book_now;

    String noOfPassengers, noOfAdults, noOfChildrens, noOfInfants;

    CharSequence list_nationality[] = new CharSequence[]{"India", "Pakistan", "Others"};

    private static final String DIALOG_DATE = "DatePickerDialogFragment";

    DVProduct dvProduct;

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

        tv_dv_traveller_count = (TextView) findViewById(R.id.tv_dv_traveller_count);
        tv_dv_travel_date = (TextView) findViewById(R.id.tv_dv_travel_date);
        tv_dv_nationality = (TextView) findViewById(R.id.tv_dv_nationality);

        btn_book_now = (AppCompatButton) findViewById(R.id.btn_book_now);
        btn_book_now.setVisibility(View.VISIBLE);

        tv_dv_traveller_count.setOnClickListener(this);
        tv_dv_travel_date.setOnClickListener(this);
        tv_dv_nationality.setOnClickListener(this);
        btn_book_now.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        dvProduct = bundle.getParcelable("item");
        getSupportActionBar().setTitle(dvProduct.getProduct_name());
        tv_dv_amount.setText(dvProduct.getCurrency() + " " + dvProduct.getAdult_price());
        tv_dv_visa_validity.setText(dvProduct.getProduct_validity());

       /* if(Transaction.getmTransactionInstance().getNoOfAdults()==0) {
            Transaction.getmTransactionInstance().setNoOfAdults(1);
            Transaction.getmTransactionInstance().setNoOfPassengers(1);
        }*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_dv_traveller_count:
                ArrayList<String> traveller_count = new ArrayList<>(Arrays.asList(tv_dv_traveller_count.getText().toString().split(",")));
                if (traveller_count.size() == 3) {
                    Transaction.getmTransactionInstance().setNoOfAdults(Integer.parseInt(traveller_count.get(0).replaceAll("\\D+", "")));
                    Transaction.getmTransactionInstance().setNoOfChildrens(Integer.parseInt(traveller_count.get(1).replaceAll("\\D+", "")));
                    Transaction.getmTransactionInstance().setNoOfInfants(Integer.parseInt(traveller_count.get(2).replaceAll("\\D+", "")));
                }
                Intent intent_traveller_count = new Intent(this, AddTravellerActivity.class);
                startActivity(intent_traveller_count);
                break;

            case R.id.tv_dv_nationality:
                popupDialog("Select Nationality", list_nationality, view.getId());
                break;

            case R.id.tv_dv_travel_date:
                DatePickerDialogFragment dialog = new DatePickerDialogFragment();
                Bundle args = new Bundle();
                args.putInt("id", view.getId());
                dialog.setArguments(args);
                dialog.show(getSupportFragmentManager(), DIALOG_DATE);
                break;

            case R.id.btn_book_now:
                Intent intent_book_now = new Intent(this, OrderDetailActivity.class);
                startActivity(intent_book_now);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    void popupDialog(String title, final CharSequence[] list, final int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setItems(list, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (id == R.id.tv_dv_nationality)
                    tv_dv_nationality.setText(list[which]);
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
                    noOfChildrens = Transaction.getmTransactionInstance().getNoOfChildrens() + " Child";
                else if (Transaction.getmTransactionInstance().getNoOfChildrens() > 1)
                    noOfChildrens = Transaction.getmTransactionInstance().getNoOfChildrens() + " Childs";
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

                tv_dv_traveller_count.setText(noOfPassengers);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFinishDialog(Date date, int id) {

        if (id == R.id.tv_dv_travel_date)
            tv_dv_travel_date.setText(ApiUtils.formatDate(date));

    }
}

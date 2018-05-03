package com.twc.rca.product.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
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
import com.twc.rca.applicant.activities.SearchFieldActivity;
import com.twc.rca.database.CountryHelper;
import com.twc.rca.model.CountryModel;
import com.twc.rca.product.fragments.DatePickerDialogFragment;
import com.twc.rca.product.fragments.TimePickerDialogFragment;
import com.twc.rca.product.model.DVProduct;
import com.twc.rca.product.model.Transaction;
import com.twc.rca.utils.ApiUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by Sushil on 09-03-2018.
 */

public class DVProductActivity extends BaseActivity implements View.OnClickListener, DatePickerDialogFragment.DateDialogListener, TimePickerDialogFragment.TimeDialogListener {

    TextView tv_dv_product, tv_dv_amount, tv_dv_processing_time, tv_dv_visa_validity, tv_dv_traveller_count, tv_dv_travel_date, tv_dv_nationality;

    TextView tv_actionbar_title;

    ImageButton img_button_back;

    EditText et_dv_traveller_count, et_dv_travel_date, et_dv_nationality;

    EditText et_traveller_count, et_nationality, et_dt_arrival, et_tm_arrival, et_dt_dept, et_tm_dept, et_airport_arrival, et_airport_dept, et_coming_from, et_going_to;

    Button btn_book_now;

    LinearLayout ll_dv_product, ll_96hr_dv_product;

    String noOfPassengers, noOfAdults, noOfChildrens, noOfInfants;

    CharSequence list_nationality[] = new CharSequence[]{"India", "Pakistan", "Others"};

    CharSequence list_airport[] = new CharSequence[]{"Dubai Airport", "Sharjah Airport", "Abu Dhabi Airport", "Al Maktoum Airport"};

    private static final String DIALOG_DATE = "DatePickerDialogFragment", DIALOG_TIME = "TimePickerDialogFragment";

    public static String TOTAL_PRICE = "total_price", TRAVELLER_COUNT = "traveller_count", TRAVEL_DT = "travel_dt", NATIONALITY = "nationality", ARRIVAL_DT = "arrival_dt", ARRIVAL_TM = "arrival_tm", DEPT_DT = "dept_dt", DEPT_TM = "dept_tm", ARRIVING_AIRPORT = "arriving_airport", DEPT_AIRPORT = "dept_airport", AIRPORT_COMING_FROM = "airport_coming_from", AIRPORT_GOING_TO = "airport_going_to";

    ArrayList<CountryModel> countryModelArrayList;

    DVProduct dvProduct;

    Double total_amount;

    public int COMING_FROM_CODE = 1, GOING_TO_CODE = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_form);

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
            img_button_back.setVisibility(View.VISIBLE);
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
        initView();
    }

    @Override
    protected boolean isHomeAsUpEnabled() {
        return false;
    }

    void initView() {
        tv_dv_product = (TextView) findViewById(R.id.tv_product_name);
        tv_dv_amount = (TextView) findViewById(R.id.tv_amount);
        tv_dv_processing_time = (TextView) findViewById(R.id.tv_dv_processing_time);
        tv_dv_visa_validity = (TextView) findViewById(R.id.tv_dv_visa_validity);

        ll_dv_product = (LinearLayout) findViewById(R.id.layout_dv_product);
        ll_96hr_dv_product = (LinearLayout) findViewById(R.id.layout_96hr_dv_product);

        Bundle bundle = getIntent().getExtras();
        dvProduct = bundle.getParcelable("item");
        tv_actionbar_title.setText(dvProduct.getProduct_name());
        String str[] = dvProduct.getProduct_name().split(" ");
        SpannableString ss1 = new SpannableString(str[0]);
        ss1.setSpan(new RelativeSizeSpan(2.5f), 0, 1, 0);
        if (dvProduct.getProduct_id().equalsIgnoreCase("1"))
            tv_dv_product.setText("   " + ss1 + "\n" + str[1]);
        else
            tv_dv_product.setText("  " + ss1 + "\n" + str[1]);
        tv_dv_amount.setText(dvProduct.getCurrency() + " " + dvProduct.getAdult_price());
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

            et_traveller_count.setOnClickListener(this);
            et_nationality.setOnClickListener(this);
            et_dt_arrival.setOnClickListener(this);
            et_tm_arrival.setOnClickListener(this);
            et_airport_arrival.setOnClickListener(this);
            et_airport_dept.setOnClickListener(this);
            et_dt_dept.setOnClickListener(this);
            et_tm_dept.setOnClickListener(this);
            et_coming_from.setOnClickListener(this);
            et_going_to.setOnClickListener(this);

        } else {
            ll_dv_product.setVisibility(View.VISIBLE);
            et_dv_traveller_count = (EditText) findViewById(R.id.et_dv_traveller_count);
            et_dv_travel_date = (EditText) findViewById(R.id.et_travel_dt);
            et_dv_nationality = (EditText) findViewById(R.id.et_dv_nationality);
            et_dv_travel_date.setOnClickListener(this);
            et_dv_traveller_count.setOnClickListener(this);
            et_dv_nationality.setOnClickListener(this);
        }

        btn_book_now = (AppCompatButton) findViewById(R.id.btn_bk_now);

        btn_book_now.setOnClickListener(this);


       /* if(Transaction.getmTransactionInstance().getNoOfAdults()==0) {
            Transaction.getmTransactionInstance().setNoOfAdults(1);
            Transaction.getmTransactionInstance().setNoOfPassengers(1);
        }*/
    }

    @Override
    public void onClick(View view) {
        ArrayList<String> traveller_count;
        ArrayList<String> searchList;
        Intent searchIntent;
        DatePickerDialogFragment datePickerDialog;
        TimePickerDialogFragment timePickerDialog;
        Bundle args;

        switch (view.getId()) {
            case R.id.et_dv_traveller_count:
                traveller_count = new ArrayList<>(Arrays.asList(et_dv_traveller_count.getText().toString().split(",")));
                if (traveller_count.size() == 3) {
                    Transaction.getmTransactionInstance().setNoOfAdults(Integer.parseInt(traveller_count.get(0).replaceAll("\\D+", "")));
                    Transaction.getmTransactionInstance().setNoOfChildrens(Integer.parseInt(traveller_count.get(1).replaceAll("\\D+", "")));
                    Transaction.getmTransactionInstance().setNoOfInfants(Integer.parseInt(traveller_count.get(2).replaceAll("\\D+", "")));
                }
                Intent intent_traveller_count = new Intent(this, AddTravellerActivity.class);
                startActivity(intent_traveller_count);
                break;

            case R.id.et_traveller_count:
                traveller_count = new ArrayList<>(Arrays.asList(et_traveller_count.getText().toString().split(",")));
                if (traveller_count.size() == 3) {
                    Transaction.getmTransactionInstance().setNoOfAdults(Integer.parseInt(traveller_count.get(0).replaceAll("\\D+", "")));
                    Transaction.getmTransactionInstance().setNoOfChildrens(Integer.parseInt(traveller_count.get(1).replaceAll("\\D+", "")));
                    Transaction.getmTransactionInstance().setNoOfInfants(Integer.parseInt(traveller_count.get(2).replaceAll("\\D+", "")));
                }
                Intent intent_count = new Intent(this, AddTravellerActivity.class);
                startActivity(intent_count);
                break;

            case R.id.et_dv_nationality:
            case R.id.et_nationality:
                popupDialog("Select Nationality", list_nationality, view.getId());
                break;

            case R.id.et_travel_dt:
            case R.id.et_dt_arrival:
            case R.id.et_dt_departure:
                datePickerDialog = new DatePickerDialogFragment();
                args = new Bundle();
                args.putInt("id", view.getId());
                datePickerDialog.setArguments(args);
                datePickerDialog.show(getSupportFragmentManager(), DIALOG_DATE);
                break;

            case R.id.et_tm_arrival:
            case R.id.et_tm_departure:
                timePickerDialog = new TimePickerDialogFragment();
                args = new Bundle();
                args.putInt("id", view.getId());
                timePickerDialog.setArguments(args);
                timePickerDialog.show(getSupportFragmentManager(), DIALOG_TIME);
                break;

            case R.id.et_arrival_airport:
            case R.id.et_departure_airport:
                popupDialog("Select Aitport", list_airport, view.getId());
                break;

            case R.id.et_coming_from:
                Transaction.getmTransactionInstance().setTransaction_type(Transaction.COUNTRY);
                countryModelArrayList = new ArrayList<>();
                countryModelArrayList = CountryHelper.getInstance(getApplicationContext()).getCountryList();
                searchList = new ArrayList<>();
                for (int i = 0; i < countryModelArrayList.size(); i++) {
                    searchList.add(countryModelArrayList.get(i).getCountryName());
                }
                searchIntent = new Intent(getApplicationContext(), SearchFieldActivity.class);
                searchIntent.putExtra(SearchFieldActivity.TITLE, getApplicationContext().getString(R.string.select_country_coming_from));
                searchIntent.putStringArrayListExtra(SearchFieldActivity.SEARCHLIST, searchList);
                startActivityForResult(searchIntent, COMING_FROM_CODE);
                break;

            case R.id.et_going_to:
                Transaction.getmTransactionInstance().setTransaction_type(Transaction.COUNTRY);
                countryModelArrayList = new ArrayList<>();
                countryModelArrayList = CountryHelper.getInstance(getApplicationContext()).getCountryList();
                searchList = new ArrayList<>();
                for (int i = 0; i < countryModelArrayList.size(); i++) {
                    searchList.add(countryModelArrayList.get(i).getCountryName());
                }
                searchIntent = new Intent(getApplicationContext(), SearchFieldActivity.class);
                searchIntent.putExtra(SearchFieldActivity.TITLE, getApplicationContext().getString(R.string.select_country_going_to));
                searchIntent.putStringArrayListExtra(SearchFieldActivity.SEARCHLIST, searchList);
                startActivityForResult(searchIntent, GOING_TO_CODE);
                break;


            case R.id.btn_bk_now:
                if (btn_book_now.isEnabled()) {
                    Intent intent_book_now = new Intent(this, OrderDetailActivity.class);
                    intent_book_now.putExtra("productDetails", dvProduct);
                    intent_book_now.putExtra(TOTAL_PRICE, total_amount);
                    if (dvProduct.getProduct_id().equalsIgnoreCase("1")) {
                        intent_book_now.putExtra(TRAVELLER_COUNT, noOfPassengers);
                        intent_book_now.putExtra(NATIONALITY, et_nationality.getText().toString());
                        intent_book_now.putExtra(ARRIVAL_DT, et_dt_arrival.getText().toString());
                        intent_book_now.putExtra(ARRIVAL_TM, et_tm_arrival.getText().toString());
                        intent_book_now.putExtra(DEPT_DT, et_dt_dept.getText().toString());
                        intent_book_now.putExtra(DEPT_TM, et_tm_dept.getText().toString());
                        intent_book_now.putExtra(ARRIVING_AIRPORT, et_airport_arrival.getText().toString());
                        intent_book_now.putExtra(DEPT_AIRPORT, et_airport_dept.getText().toString());
                        intent_book_now.putExtra(AIRPORT_COMING_FROM, et_coming_from.getText().toString());
                        intent_book_now.putExtra(AIRPORT_GOING_TO, et_going_to.getText().toString());
                    } else {
                        intent_book_now.putExtra(TRAVEL_DT, et_dv_travel_date.getText().toString());
                        intent_book_now.putExtra(TRAVELLER_COUNT, noOfPassengers);
                        intent_book_now.putExtra(NATIONALITY, et_dv_nationality.getText().toString());
                    }
                    startActivity(intent_book_now);
                }
                break;
        }
    }

    void isAllFormFilled() {
        if (ll_dv_product.getVisibility() == View.VISIBLE) {
            if (!ApiUtils.isValidStringValue(et_dv_traveller_count.getText().toString()) && !ApiUtils.isValidStringValue(et_dv_nationality.getText().toString()) && !ApiUtils.isValidStringValue(et_dv_travel_date.getText().toString())) {
                btn_book_now.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                btn_book_now.setEnabled(true);
            } else {
                btn_book_now.setBackgroundColor(getResources().getColor(R.color.colorGray));
                btn_book_now.setEnabled(false);
            }
        } else {
            if (!ApiUtils.isValidStringValue(et_traveller_count.getText().toString()) && !ApiUtils.isValidStringValue(et_nationality.getText().toString()) && !ApiUtils.isValidStringValue(et_dt_arrival.getText().toString())
                    && !ApiUtils.isValidStringValue(et_tm_arrival.getText().toString()) && !ApiUtils.isValidStringValue(et_airport_arrival.getText().toString()) && !ApiUtils.isValidStringValue(et_airport_dept.getText().toString())
                    && !ApiUtils.isValidStringValue(et_dt_dept.getText().toString()) && !ApiUtils.isValidStringValue(et_tm_dept.getText().toString())
                    && !ApiUtils.isValidStringValue(et_coming_from.getText().toString()) && !ApiUtils.isValidStringValue(et_going_to.getText().toString())) {
                btn_book_now.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                btn_book_now.setEnabled(true);
            } else {
                btn_book_now.setBackgroundColor(getResources().getColor(R.color.colorGray));
                btn_book_now.setEnabled(false);
            }
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
                if (id == R.id.et_arrival_airport)
                    et_airport_arrival.setText(list[which]);
                if (id == R.id.et_departure_airport)
                    et_airport_dept.setText(list[which]);
                if (id == R.id.et_dv_nationality)
                    et_dv_nationality.setText(list[which]);
                if (id == R.id.et_nationality)
                    et_nationality.setText(list[which]);
                isAllFormFilled();
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

                if (et_dv_traveller_count != null)
                    et_dv_traveller_count.setText(noOfPassengers);
                else
                    et_traveller_count.setText(noOfPassengers);
                total_amount = Transaction.getmTransactionInstance().getNoOfAdults() * Double.parseDouble(dvProduct.getAdult_price()) + Transaction.getmTransactionInstance().getNoOfChildrens() * Double.parseDouble(dvProduct.getChild_price()) + Transaction.getmTransactionInstance().getNoOfInfants() * Double.parseDouble(dvProduct.getInfant_price());

                tv_dv_amount.setText(total_amount.toString());
                isAllFormFilled();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFinishDialog(Date date, int id) {

        if (id == R.id.et_travel_dt)
            et_dv_travel_date.setText(ApiUtils.formatDate(date));

        else if (id == R.id.et_dt_arrival)
            et_dt_arrival.setText(ApiUtils.formatDate(date));

        else if (id == R.id.et_dt_departure)
            et_dt_dept.setText(ApiUtils.formatDate(date));

        isAllFormFilled();

    }

    @Override
    public void onFinishDialog(int hour, int min, String period, int id) {

        if (id == R.id.et_tm_arrival)
            et_tm_arrival.setText(hour + ":" + min + " " + period);

        else if (id == R.id.et_tm_departure)
            et_tm_dept.setText(hour + ":" + min + " " + period);

        isAllFormFilled();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == COMING_FROM_CODE) {
            et_coming_from.setText(Transaction.getmTransactionInstance().getCountry());
        } else if (requestCode == GOING_TO_CODE) {
            et_going_to.setText(Transaction.getmTransactionInstance().getCountry());
        }
        isAllFormFilled();
    }
}

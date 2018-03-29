package com.twc.rca.applicant.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.twc.rca.R;
import com.twc.rca.activities.BaseActivity;
import com.twc.rca.applicant.adapter.SearchViewAdapter;
import com.twc.rca.product.adapter.ApplicationAdapter;
import com.twc.rca.product.model.Transaction;

import java.util.ArrayList;

/**
 * Created by Sushil on 16-03-2018.
 */

public class SearchFieldActivity extends BaseActivity implements SearchView.OnQueryTextListener {

    SearchView search_app_field;
    ListView list_search;

    public static ArrayList<String> list_item;
    private SearchViewAdapter searchViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_application_fields);

        search_app_field = (SearchView) findViewById(R.id.application_search_view);
        list_search = (ListView) findViewById(R.id.list_search);

        list_item = new ArrayList<>();
        list_item = getIntent().getStringArrayListExtra(ApplicationAdapter.SEARCHLIST);

        searchViewAdapter = new SearchViewAdapter(this);
        list_search.setAdapter(searchViewAdapter);
        search_app_field.setOnQueryTextListener(this);

        list_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int transasction_type = Transaction.getmTransactionInstance().getTransaction_type();

                String strName = parent.getItemAtPosition(position).toString();

                switch (transasction_type) {
                    case 1:
                        Transaction.getmTransactionInstance().setCountry(strName);
                        break;

                    case 2:
                        Transaction.getmTransactionInstance().setLanguage(strName);
                        break;

                    case 3:
                        Transaction.getmTransactionInstance().setMaritalStatus(strName);
                        break;

                    case 4:
                        Transaction.getmTransactionInstance().setPassportType(strName);
                        break;

                    case 5:
                        Transaction.getmTransactionInstance().setProfession(strName);
                        break;

                    case 6:
                        Transaction.getmTransactionInstance().setReligion(strName);
                        break;
                }
                InputMethodManager inputMethodManager = (InputMethodManager)SearchFieldActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(SearchFieldActivity.this.getCurrentFocus().getWindowToken(), 0);
                finish();
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        searchViewAdapter.filter(text);
        return false;
    }
}

package com.twc.rca.product.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.twc.rca.R;
import com.twc.rca.activities.BaseActivity;
import com.twc.rca.product.model.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TWC on 26-02-2018.
 */

public class AddTravellerActivity extends BaseActivity {

    ListView list_add_travellers;

    Button btn_apply;

    ImageButton btn_close;

    String[] list_traveller_lable = new String[]{"Adults", "Children", "Infant"};

    String[] list_age_group = new String[]{"16+", "2-11", "0-2"};

    final ArrayList<String> ls_traveller = new ArrayList<>();

    final ArrayList<String> ls_age_group = new ArrayList<>();

    Integer[] list_count = new Integer[3];

    int sum = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dv_add_traveller);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            View viewActionBar = getLayoutInflater().inflate(R.layout.layout_traveller_actionbar, null);
            ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                    ActionBar.LayoutParams.MATCH_PARENT,
                    ActionBar.LayoutParams.MATCH_PARENT,
                    Gravity.CENTER);
            actionBar.setCustomView(viewActionBar, params);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            Toolbar toolbar = (Toolbar) actionBar.getCustomView().getParent();
            toolbar.setContentInsetsAbsolute(0, 0);
            toolbar.getContentInsetEnd();
            toolbar.setPadding(0, 0, 0, 0);

            btn_close = (ImageButton) viewActionBar.findViewById(R.id.btn_close);
            btn_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }

        list_add_travellers = (ListView) findViewById(R.id.list_add_travellers);
        btn_apply = (Button) findViewById(R.id.btn_apply);

        for (int i = 0; i < list_traveller_lable.length; i++) {
            ls_traveller.add(list_traveller_lable[i]);
            ls_age_group.add(list_age_group[i]);
        }

        final TravellerAdapter adapter = new TravellerAdapter(this, ls_traveller, ls_age_group);
        list_add_travellers.setItemsCanFocus(true);
        list_add_travellers.setFocusable(false);
        list_add_travellers.setFocusableInTouchMode(false);
        list_add_travellers.setClickable(false);
        list_add_travellers.setAdapter(adapter);

        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < list_add_travellers.getCount(); i++) {
                    View view = list_add_travellers.getChildAt(i);
                    TextView tv_count = (TextView) view.findViewById(R.id.tv_traveller_count);
                    list_count[i] = Integer.parseInt((String) tv_count.getText());
                    sum = list_count[i] + sum;
                }
                Transaction.getmTransactionInstance().setNoOfAdults(list_count[0]);
                Transaction.getmTransactionInstance().setNoOfChildrens(list_count[1]);
                Transaction.getmTransactionInstance().setNoOfInfants(list_count[2]);
                Transaction.getmTransactionInstance().setNoOfPassengers(sum);
                finish();
            }
        });

    }

    @Override
    protected boolean isHomeAsUpEnabled() {
        return false;
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

    public class TravellerAdapter extends BaseAdapter {

        Context context;
        List<String> list_traveller = new ArrayList<>();
        List<String> list_age_group = new ArrayList<>();
        int count;

        public TravellerAdapter(Context context, List<String> list_traveller, List<String> list_age_group) {
            this.context = context;
            this.list_traveller = list_traveller;
            this.list_age_group = list_age_group;
        }

        @Override
        public int getCount() {
            return list_traveller.size();
        }

        @Override
        public Object getItem(int i) {
            return list_traveller.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int position, View view, ViewGroup viewGroup) {
            final ViewHolder viewHolder;

            if (view == null) {
                viewHolder = new ViewHolder();

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.traveller_list_item, null);

                viewHolder.tv_traveller_lable = (TextView) view.findViewById(R.id.tv_traveller_lable);
                viewHolder.tv_age_group = (TextView) view.findViewById(R.id.tv_age_group);
                viewHolder.tv_subtract = (TextView) view.findViewById(R.id.tv_subtract);
                viewHolder.tv_add = (TextView) view.findViewById(R.id.tv_add);
                viewHolder.tv_count = (TextView) view.findViewById(R.id.tv_traveller_count);

                viewHolder.tv_traveller_lable.setText(list_traveller.get(position));
                viewHolder.tv_age_group.setText(list_age_group.get(position));

                if (position == 0)
                    viewHolder.tv_count.setText(String.valueOf(Transaction.getmTransactionInstance().getNoOfAdults()));
                else if (position == 1)
                    viewHolder.tv_count.setText(String.valueOf(Transaction.getmTransactionInstance().getNoOfChildrens()));
                else
                    viewHolder.tv_count.setText(String.valueOf(Transaction.getmTransactionInstance().getNoOfInfants()));

                viewHolder.tv_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        count = Integer.parseInt((String) viewHolder.tv_count.getText());
                        count++;
                        viewHolder.tv_count.setText(String.valueOf(count));
                    }
                });

                viewHolder.tv_subtract.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        count = Integer.parseInt((String) viewHolder.tv_count.getText());
                       /* if (position == 0) {
                            if (count != 1)
                                count--;
                        }
                        if (position == 1 || position == 2) {*/
                            if (!(count <= 0))
                                count--;
                        //}
                        viewHolder.tv_count.setText(String.valueOf(count));
                    }
                });
                view.setTag(viewHolder);
            }
            return view;
        }
    }

    public class ViewHolder {
        TextView tv_traveller_lable;
        TextView tv_age_group;
        TextView tv_subtract;
        TextView tv_add;
        TextView tv_count;
    }
}

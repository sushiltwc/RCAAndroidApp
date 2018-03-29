package com.twc.rca.applicant.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.twc.rca.R;
import com.twc.rca.applicant.activities.SearchFieldActivity;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Sushil on 16-03-2018.
 */

public class SearchViewAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    private ArrayList<String> list_item;

    public SearchViewAdapter(Context context){
        this.context=context;
        inflater = LayoutInflater.from(context);
        this.list_item=new ArrayList<>();
        this.list_item.addAll(SearchFieldActivity.list_item);
    }

    public class ViewHolder {
        AppCompatTextView tv_search_item;
    }

    @Override
    public int getCount() {
        return SearchFieldActivity.list_item.size();
    }

    @Override
    public String getItem(int position) {
        return SearchFieldActivity.list_item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.search_list_item, null);
            // Locate the TextViews in listview_item.xml
            holder.tv_search_item = (AppCompatTextView) view.findViewById(R.id.tv_search_item);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.tv_search_item.setText(SearchFieldActivity.list_item.get(position));
        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        SearchFieldActivity.list_item.clear();
        if (charText.length() == 0) {
            SearchFieldActivity.list_item.addAll(list_item);
        } else {
            for (String wp : list_item) {
                if (wp.toLowerCase(Locale.getDefault()).contains(charText)) {
                    SearchFieldActivity.list_item.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}

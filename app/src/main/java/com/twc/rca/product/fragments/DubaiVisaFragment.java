package com.twc.rca.product.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twc.rca.R;
import com.twc.rca.product.adapter.DubaiVisaAdapter;
import com.twc.rca.product.model.DVProduct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TWC on 21-02-2018.
 */

public class DubaiVisaFragment extends Fragment {

    RecyclerView list_dv_product;

    private List<DVProduct> dvProductList = new ArrayList<>();

    private DubaiVisaAdapter dubaiVisaAdapter;

    public static DubaiVisaFragment getInstance() {
        DubaiVisaFragment dubaiVisaFragment = new DubaiVisaFragment();
        return dubaiVisaFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dubai_visa, container, false);

        list_dv_product=(RecyclerView)view.findViewById(R.id.list_dv_product);

        dubaiVisaAdapter = new DubaiVisaAdapter(this.getActivity(),dvProductList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        list_dv_product.setLayoutManager(mLayoutManager);
        list_dv_product.setItemAnimator(new DefaultItemAnimator());
        list_dv_product.setAdapter(dubaiVisaAdapter);

        prepareDVData();

        return view;
    }

    private void prepareDVData() {
        DVProduct dvProduct = new DVProduct("30","DAYS","Dubai Tourist Visa","VISA IN 2-3 DAYS","58 DAYS VALIDITY","330");
        dvProductList.add(dvProduct);

        dvProduct = new DVProduct("14","DAYS","Dubai Tourist Visa","VISA IN 2-3 DAYS","14 DAYS VALIDITY","330");
        dvProductList.add(dvProduct);

        dvProduct = new DVProduct("96","HOURS","Dubai Transit Visa","VISA IN 2-3 DAYS","30 DAYS VALIDITY","210");
        dvProductList.add(dvProduct);

        dvProduct = new DVProduct("90","DAYS","Dubai Tourist Visa","VISA IN 2-3 DAYS","58 DAYS VALIDITY","1030");
        dvProductList.add(dvProduct);

        dubaiVisaAdapter.notifyDataSetChanged();
    }
}

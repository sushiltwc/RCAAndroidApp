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
import com.twc.rca.product.adapter.DubaiVisaAdapter;
import com.twc.rca.product.model.DVProduct;
import com.twc.rca.product.task.DubaiVisaProductTask;
import com.twc.rca.utils.ApiUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sushil on 21-02-2018.
 */

public class DubaiVisaFragment extends BaseFragment {

    public static String PRODUCT_ID = "product_id", PRODUCT_NAME = "product_name", PRODUCT_VALIDITY = "product_validity", PRODUCT_TYPE = "product_type",PRODUCT_INFO_URL="product_info_url", CURRENCY = "currency", ADULT_PRICE = "adult_price", CHILD_PRICE = "child_price", INFANT_PRICE = "infant_price";

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

        list_dv_product = (RecyclerView) view.findViewById(R.id.list_dv_product);

        dubaiVisaAdapter = new DubaiVisaAdapter(this.getActivity(), dvProductList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        list_dv_product.setLayoutManager(mLayoutManager);
        list_dv_product.setItemAnimator(new DefaultItemAnimator());
        list_dv_product.setAdapter(dubaiVisaAdapter);

        showProgressDialog(getString(R.string.please_wait));
        new DubaiVisaProductTask(getContext()).getDubaiVisaProduct(dubaiVisaProductResponseCallback);

        ApiUtils.getNextDay();

        return view;
    }

    DubaiVisaProductTask.DubaiVisaProductResponseCallback dubaiVisaProductResponseCallback = new DubaiVisaProductTask.DubaiVisaProductResponseCallback() {
        @Override
        public void onSuccessDubaiVisaProductResponse(String response) {
            dismissProgressDialog();
            try {
                JSONObject jsonObject = new JSONObject(response);

                JSONObject contentObject = (JSONObject) jsonObject.get(ApiUtils.CONTENT);

                JSONArray data = (JSONArray) contentObject.get(ApiUtils.RESULT_SET);

                DVProduct dvProduct;
                if (data.length() > 0) {
                    JSONObject dataObject;
                    for (int i = 0; i < data.length(); i++) {
                        dataObject = data.getJSONObject(i);
                        String product_id = dataObject.getString(PRODUCT_ID);
                        String product_name = dataObject.getString(PRODUCT_NAME);
                        String product_validity = dataObject.getString(PRODUCT_VALIDITY);
                        String product_type = dataObject.getString(PRODUCT_TYPE);
                        String product_info=dataObject.getString(PRODUCT_INFO_URL);
                        String currency = dataObject.getString(CURRENCY);
                        String productCurrency = currency.substring(0);
                        String adult_price = dataObject.getString(ADULT_PRICE);
                        String child_price = dataObject.getString(CHILD_PRICE);
                        String infant_price = dataObject.getString(INFANT_PRICE);
                        dvProduct = new DVProduct(product_id, product_name, product_validity, product_type,product_info, currency, adult_price, child_price, infant_price);
                        dvProduct.setProduct_id(product_id);
                        dvProduct.setProduct_name(product_name);
                        dvProduct.setProduct_validity(product_validity);
                        dvProduct.setProduct_type(product_type);
                        dvProduct.setCurrency(productCurrency);
                        dvProduct.setAdult_price(adult_price);
                        dvProduct.setChild_price(child_price);
                        dvProduct.setInfant_price(infant_price);
                        dvProductList.add(dvProduct);
                    }
                    dubaiVisaAdapter.notifyDataSetChanged();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailureDubaiVisaProductResponse(String response) {
            dismissProgressDialog();
        }
    };
}

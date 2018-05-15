package com.twc.rca.product.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twc.rca.R;
import com.twc.rca.activities.BaseFragment;
import com.twc.rca.applicant.adapter.ApplicantListAdapter;
import com.twc.rca.applicant.model.ApplicantModel;
import com.twc.rca.applicant.task.OrderApplicantTask;
import com.twc.rca.product.model.Transaction;
import com.twc.rca.utils.ApiUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.drawable.ClipDrawable.HORIZONTAL;

/**
 * Created by Sushil on 15-03-2018.
 */

public class CurrentApplicantFragment extends BaseFragment {

    RecyclerView list_current_applicant;

    ApplicantModel applicantModel;

    private List<ApplicantModel> applicantList = new ArrayList<>();

    private ApplicantListAdapter applicantListAdapter;

    String orderId;

    public static CurrentApplicantFragment getInstance() {
        CurrentApplicantFragment currentApplicantFragment = new CurrentApplicantFragment();
        return currentApplicantFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_applicant, container, false);

        list_current_applicant = (RecyclerView) view.findViewById(R.id.list_applicant);

        DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(), HORIZONTAL);
        list_current_applicant.addItemDecoration(itemDecor);

        orderId = getArguments().getString("orderId");

        showProgressDialog();
        new OrderApplicantTask(getContext(), orderId).getOrderApplicantList(orderApplicantListResposeCallback);
        return view;
    }

    OrderApplicantTask.OrderApplicantListResposeCallback orderApplicantListResposeCallback = new OrderApplicantTask.OrderApplicantListResposeCallback() {
        @Override
        public void onSuccessOrderApplicantListResponse(String response) {

            dismissProgressDialog();
            try {
                JSONObject jsonObject = new JSONObject(response);

                JSONObject contentObject = (JSONObject) jsonObject.get(ApiUtils.CONTENT);

                ApplicantModel applicantModel;

                JSONArray data = (JSONArray) contentObject.get(ApiUtils.RESULT_SET);

                if (data.length() > 0) {
                    for (int i = 0; i < data.length(); i++) {
                        applicantModel = new ApplicantModel();
                        JSONObject jObject = data.getJSONObject(i);
                        applicantModel.applicantId = jObject.getString("profile_id");
                        applicantModel.applicantGivenName = jObject.getString("username");
                        applicantModel.applicantSurname = jObject.getString("surname");
                        applicantModel.applicantNationality = jObject.getString("nationality");
                        applicantModel.applicantGender = jObject.getString("gender");
                        applicantModel.applicantDOB = jObject.getString("dob");
                        applicantModel.applicantPOB = jObject.getString("place_of_birth");
                        applicantModel.is_Submitted = jObject.getString("is_submitted");
                        if (jObject.get("applicant_type_arr") != null) {
                            JSONObject jsonApplicantTypeArr = (JSONObject) jObject.get("applicant_type_arr");
                            applicantModel.applicantType = jsonApplicantTypeArr.getString("applicant_type_desc");
                        }
                        applicantList.add(applicantModel);
                    }
                }

                applicantListAdapter = new ApplicantListAdapter(getContext(), applicantList);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                list_current_applicant.setLayoutManager(mLayoutManager);
                list_current_applicant.setItemAnimator(new DefaultItemAnimator());
                list_current_applicant.setAdapter(applicantListAdapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailureOrderApplicantListResponse(String response) {

            dismissProgressDialog();
            try {
                JSONObject jsonObject = new JSONObject(response);

                JSONObject contentObject = (JSONObject) jsonObject.get(ApiUtils.CONTENT);

                showToast(contentObject.getString("message"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    void prepareApplicantList() {

        for (int i = 0; i < Transaction.getmTransactionInstance().getNoOfPassengers(); i++) {

            for (int j = 0; j < Transaction.getmTransactionInstance().getNoOfAdults(); j++) {
                applicantModel = new ApplicantModel();
                applicantModel.setApplicantName("Applicant " + (j + 1));
                applicantModel.setApplicantType("Adult " + (j + 1));
                applicantList.add(applicantModel);
            }
            i = i + Transaction.getmTransactionInstance().getNoOfAdults();
            for (int k = 0; k < Transaction.getmTransactionInstance().getNoOfChildrens(); k++) {
                applicantModel = new ApplicantModel();
                applicantModel.setApplicantName("Applicant " + (i + k + 1));
                applicantModel.setApplicantType("Child " + (k + 1));
                applicantList.add(applicantModel);
            }
            i = i + Transaction.getmTransactionInstance().getNoOfChildrens();
            for (int l = 0; l < Transaction.getmTransactionInstance().getNoOfChildrens(); l++) {
                applicantModel = new ApplicantModel();
                applicantModel.setApplicantName("Applicant " + (i + l + 1));
                applicantModel.setApplicantType("Infant " + (l + 1));
                applicantList.add(applicantModel);
            }
            i = i + Transaction.getmTransactionInstance().getNoOfInfants();
        }
    }
}

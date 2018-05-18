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
                        applicantModel.applicantSubmited = jObject.getString("is_submitted");

                        if (!ApiUtils.isValidStringValue(jObject.getString("country_of_birth_arr"))) {
                            JSONObject jsonApplicantCOBArr = (JSONObject) jObject.get("country_of_birth_arr");
                            applicantModel.applicantCOB = jsonApplicantCOBArr.getString("country_name");
                        }

                        applicantModel.applicantAddressLine1 = jObject.getString("address1");
                        applicantModel.applicantSurname = jObject.getString("surname");
                        applicantModel.applicantAddressLine2 = jObject.getString("address2");
                        applicantModel.applicantAddressLine3 = jObject.getString("address3");

                        if (!ApiUtils.isValidStringValue(jObject.getString("country_arr"))) {
                            JSONObject jsonApplicantCountryArr = (JSONObject) jObject.get("country_arr");
                            applicantModel.applicantCountry = jsonApplicantCountryArr.getString("country_name");
                        }

                        applicantModel.applicantOrderId = jObject.getString("order_id");
                        applicantModel.applicantFName = jObject.getString("father_name");

                        if (!ApiUtils.isValidStringValue(jObject.getString("applicant_type_arr"))) {
                            JSONObject jsonApplicantTypeArr = (JSONObject) jObject.get("applicant_type_arr");
                            applicantModel.applicantType = jsonApplicantTypeArr.getString("applicant_type_desc");
                        }

                        applicantModel.applicantCity = jObject.getString("city");

                        if (!ApiUtils.isValidStringValue(jObject.getString("language_arr"))) {
                            JSONObject jsonApplicantLanguageArr = (JSONObject) jObject.get("language_arr");
                            applicantModel.applicantLangSpoken = jsonApplicantLanguageArr.getString("lang_name");
                        }
                        applicantModel.applicantIsChild = jObject.getString("is_child");
                        applicantModel.applicantGivenName = jObject.getString("username");
                        applicantModel.applicantHName = jObject.getString("husband_name");

                        if (!ApiUtils.isValidStringValue(jObject.getString("nationality_arr"))) {
                            JSONObject jsonNationalityArr = (JSONObject) jObject.get("nationality_arr");
                            applicantModel.applicantNationality = jsonNationalityArr.getString("country_name");
                        }

                        applicantModel.applicantGender = jObject.getString("gender");

                        if (!ApiUtils.isValidStringValue(jObject.getString("profession_arr"))) {
                            JSONObject jsonApplicantProfArr = (JSONObject) jObject.get("profession_arr");
                            applicantModel.applicantProfession = jsonApplicantProfArr.getString("profession_name");
                        }

                        if (!ApiUtils.isValidStringValue(jObject.getString("passport_details_arr"))) {
                            JSONObject jsonApplicantPassportArr = (JSONObject) jObject.get("passport_details_arr");
                            applicantModel.applicantPPNo = jsonApplicantPassportArr.getString("pp_no");
                            applicantModel.applicantPPIssuePlace = jsonApplicantPassportArr.getString("pp_place_of_issue");
                            applicantModel.applicantPPDOI = jsonApplicantPassportArr.getString("pp_issue_date");
                            applicantModel.applicantPPDOE = jsonApplicantPassportArr.getString("pp_expiry_date");

                            if (!ApiUtils.isValidStringValue(jsonApplicantPassportArr.getString("pp_type_arr"))) {
                                JSONObject jsonPPType = (JSONObject) jsonApplicantPassportArr.get("pp_type_arr");
                                applicantModel.applicantPPType = jsonPPType.getString("passport_type_name");
                            }

                            if (!ApiUtils.isValidStringValue(jsonApplicantPassportArr.getString("pp_issuing_govt_arr"))) {
                                JSONObject jsonPPIssueGovt = (JSONObject) jsonApplicantPassportArr.get("pp_issuing_govt_arr");
                                applicantModel.applicantPPIssueGovt = jsonPPIssueGovt.getString("country_name");
                            }
                        }

                        if (!ApiUtils.isValidStringValue(jObject.getString("religion_arr"))) {
                            JSONObject jsonApplicantReligionArr = (JSONObject) jObject.get("religion_arr");
                            applicantModel.applicantReligion = jsonApplicantReligionArr.getString("religion_name");
                        }

                        applicantModel.applicantTelephone = jObject.getString("mobile_number");

                        if (!ApiUtils.isValidStringValue(jObject.getString("marital_status_arr"))) {
                            JSONObject jsonApplicantMaritalStatusArr = (JSONObject) jObject.get("marital_status_arr");
                            applicantModel.applicantMaritalStatus = jsonApplicantMaritalStatusArr.getString("marital_status_name");
                        }

                        applicantModel.applicantDOB = jObject.getString("dob");
                        applicantModel.applicantMName = jObject.getString("mother_name");
                        applicantModel.applicantPOB = jObject.getString("place_of_birth");

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

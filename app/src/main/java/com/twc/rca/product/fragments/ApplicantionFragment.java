package com.twc.rca.product.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.twc.rca.R;
import com.twc.rca.activities.BaseFragment;
import com.twc.rca.applicant.model.ApplicantModel;
import com.twc.rca.applicant.model.PassportBackModel;
import com.twc.rca.applicant.model.PassportFrontModel;
import com.twc.rca.applicant.model.ReceiveDocumentModel;
import com.twc.rca.product.adapter.ApplicationAdapter;
import com.twc.rca.utils.ApiUtils;
import com.twc.rca.utils.GVPassportUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sushil on 06-03-2018.
 */

public class ApplicantionFragment extends BaseFragment {

    ExpandableListView ex_application;
    AppCompatButton btn_submit_application;
    ArrayList<String> listDataHeader_title;
    ArrayList<Integer> listDataHeader_icon;
    ArrayList<Integer> list_child;
    Map<String, ArrayList<Integer>> formCollection;
    PassportFrontModel passportFrontModel;
    PassportBackModel passportBackModel;
    ApplicantModel applicantModel;

    public ApplicationAdapter applicationAdapter;

    static ApplicantionFragment applicantionFragment;

    public static ApplicantionFragment getInstance() {
        //ApplicantionFragment applicantFragment = new ApplicantionFragment();
        return applicantionFragment;
    }

    public static ApplicantionFragment newInstance() {
        applicantionFragment = new ApplicantionFragment();
        return applicantionFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_application, container, false);

        ex_application = (ExpandableListView) view.findViewById(R.id.expand_list_applicant_form);
        btn_submit_application = (AppCompatButton) view.findViewById(R.id.btn_submit_application);

        Bundle bundle = getArguments();
        applicantModel = bundle.getParcelable("applicant");
       /* if(applicantModel.getIs_Submitted().equalsIgnoreCase("Y"))
            applicationAdapter.setCustomerData(applicantModel);
*/
        prepareListData();
        prepareFormCollection();

        applicationAdapter = new ApplicationAdapter(getActivity(), this, listDataHeader_icon, listDataHeader_title, formCollection, processForm);
        ex_application.setAdapter(applicationAdapter);

        return view;
    }

    public void putData(Bundle args) {
        passportFrontModel = (PassportFrontModel) args.getSerializable("pf");
        passportBackModel = (PassportBackModel) args.getSerializable("pb");
        applicationAdapter.setCustomerData(passportFrontModel, passportBackModel);
    }

    public void putData(List<ReceiveDocumentModel> receiveDocumentModelArrayList) {
        String base64ImageData;
        for (int i = 0; i < receiveDocumentModelArrayList.size(); i++) {
            if (receiveDocumentModelArrayList.get(i).getDoc_type().equalsIgnoreCase("PASSPORT_FRONT")) {
                base64ImageData = GVPassportUtils.getByteArrayFromImageURL(receiveDocumentModelArrayList.get(i).getDoc_url().toString());
                passportFrontModel = new GVPassportUtils(getContext()).processPassportFront(ApiUtils.StringToBitMap(base64ImageData));
            } else if (receiveDocumentModelArrayList.get(i).getDoc_type().equalsIgnoreCase("PASSPORT_BACK")) {
                base64ImageData = GVPassportUtils.getByteArrayFromImageURL(receiveDocumentModelArrayList.get(i).getDoc_url().toString());
                passportBackModel = new GVPassportUtils(getContext()).processPassportBack(ApiUtils.StringToBitMap(base64ImageData));
            }
        }
        applicationAdapter.setCustomerData(passportFrontModel, passportBackModel);
    }

    void prepareListData() {
        listDataHeader_title = new ArrayList<>();
        listDataHeader_title.add(getString(R.string.your_personal_details));
        listDataHeader_title.add(getString(R.string.your_passport_details));
        listDataHeader_title.add(getString(R.string.your_contact_details));
        listDataHeader_title.add(getString(R.string.your_travel_details));

        listDataHeader_icon = new ArrayList<>();
        listDataHeader_icon.add(R.drawable.personal_details_selector);
        listDataHeader_icon.add(R.drawable.passport_details_selector);
        listDataHeader_icon.add(R.drawable.contact_details_selector);
        listDataHeader_icon.add(R.drawable.travel_details_selector);

        list_child = new ArrayList<>();
        list_child.add(R.layout.layout_personal_details_form);
        list_child.add(R.layout.layout_passport_details_form);
        list_child.add(R.layout.layout_contact_details_form);
        list_child.add(R.layout.layout_travel_details_form);
    }

    private void prepareFormCollection() {

        // preparing laptops collection(child)
        Integer[] personalModels = {R.layout.layout_personal_details_form};
        Integer[] passportModels = {R.layout.layout_passport_details_form};
        Integer[] contactModels = {R.layout.layout_contact_details_form};
        Integer[] travelModels = {R.layout.layout_travel_details_form};

        formCollection = new LinkedHashMap<String, ArrayList<Integer>>();

        for (String laptop : listDataHeader_title) {
            if (laptop.equals(getString(R.string.your_personal_details))) {
                loadChild(personalModels);
            } else if (laptop.equals(getString(R.string.your_passport_details)))
                loadChild(passportModels);
            else if (laptop.equals(getString(R.string.your_contact_details)))
                loadChild(contactModels);
            else if (laptop.equals(getString(R.string.your_travel_details)))
                loadChild(travelModels);

            formCollection.put(laptop, list_child);
        }
    }

    private void loadChild(Integer[] laptopModels) {
        list_child = new ArrayList<Integer>();
        for (Integer model : laptopModels)
            list_child.add(model);
    }

    ApplicationAdapter.ProcessForm processForm = new ApplicationAdapter.ProcessForm() {
        @Override
        public void enableSubmit(boolean status) {
            btn_submit_application.setEnabled(status);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        applicationAdapter.onActivityResult(requestCode, resultCode, data);
    }
}

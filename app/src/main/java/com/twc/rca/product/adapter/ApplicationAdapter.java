package com.twc.rca.product.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.twc.rca.R;
import com.twc.rca.applicant.activities.SearchFieldActivity;
import com.twc.rca.database.CountryHelper;
import com.twc.rca.database.LanguageHelper;
import com.twc.rca.database.MaritalHelper;
import com.twc.rca.database.ProfessionHelper;
import com.twc.rca.database.ReligionHelper;
import com.twc.rca.model.CountryModel;
import com.twc.rca.model.LanguageModel;
import com.twc.rca.model.MaritalModel;
import com.twc.rca.model.PassportTypeModel;
import com.twc.rca.model.ProfessionModel;
import com.twc.rca.model.ReligionModel;
import com.twc.rca.product.fragments.ApplicantionFragment;
import com.twc.rca.product.fragments.DatePickerDialogFragment;
import com.twc.rca.product.model.Transaction;
import com.twc.rca.utils.ApiUtils;
import com.twc.rca.utils.FormValidator;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * Created by Sushil on 07-03-2018.
 */

public class ApplicationAdapter extends BaseExpandableListAdapter implements View.OnClickListener, DatePickerDialogFragment.DateDialogListener {

    Context context;

    public int REQUEST_FOR_ACTIVITY_CODE = 100;

    public static String SEARCHLIST = "searchList",TITLE="title";

    Fragment applicantionFragment;

    AppCompatTextView tv_header_title;

    AppCompatImageView img_header_icon, img_application_groupIndicator;

    AppCompatEditText et_surname, et_given_name, et_nationality, et_gender, et_dob, et_pob, et_cob, et_marital_status, et_religion, et_language, et_profession, et_father_name, et_mother_name, et_husband_name;

    AppCompatEditText et_pp_no, et_pp_type, et_pp_issue_govt, et_pp_place_issue, et_pp_doi, et_pp_doe;

    AppCompatEditText et_address_line1, et_address_line2, et_address_line3, et_city, et_country, et_telephone_no;

    AppCompatEditText et_arrival_airlines, et_arrival_flight_no, et_arrival_coming_from, et_doa, et_arrival_time_hr, et_arrival_time_min, et_dept_airline, et_dept_flight_no, et_dept_leaving_to, et_dod, et_dept_time_hr, et_dept_time_min;

    String str_surname, str_given_name, str_nationality, str_gender, str_dob, str_pob, str_cob, str_marital_status, str_religion, str_language, str_father_name, str_mother_name, str_husband_name;

    CharSequence list_nationality[] = new CharSequence[]{"India", "Pakistan", "Others"};

    CharSequence list_gender[] = new CharSequence[]{"Male", "Female"};

    private static final String DIALOG_DATE = "DatePickerDialogFragment";

    ArrayList<CountryModel> countryModelArrayList;
    ArrayList<LanguageModel> languageModelArrayList;
    ArrayList<MaritalModel> maritalModelArrayList;
    ArrayList<ProfessionModel> professionModelArrayList;
    ArrayList<ReligionModel> religionModelArrayList;
    ArrayList<PassportTypeModel> passportTypeModelArrayList;
    ArrayList<String> searchNameList;

    ArrayList<String> list_header_title;
    ArrayList<Integer> list_header_icon;
    Map<String, ArrayList<Integer>> formCollection;

    ProcessForm processForm;

    // 4 Child types
    private static final int CHILD_TYPE_1 = 0;
    private static final int CHILD_TYPE_2 = 1;
    private static final int CHILD_TYPE_3 = 2;
    private static final int CHILD_TYPE_4 = 3;

    // 3 Group types
    private static final int GROUP_TYPE_1 = 0;
    private static final int GROUP_TYPE_2 = 1;
    private static final int GROUP_TYPE_3 = 2;
    private static final int GROUP_TYPE_4 = 3;


    public interface ProcessForm {
        void enableSubmit(boolean status);
    }

    public ApplicationAdapter(Context context, ApplicantionFragment fragment, ArrayList<Integer> list_header_icon, ArrayList<String> list_header_title, Map<String, ArrayList<Integer>> formCollection, ProcessForm processForm) {
        this.context = context;
        applicantionFragment = fragment;
        this.list_header_icon = list_header_icon;
        this.list_header_title = list_header_title;
        this.formCollection = formCollection;
        this.processForm = processForm;
    }

    @Override
    public int getGroupCount() {
        return list_header_title.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return formCollection.get(list_header_title.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list_header_title.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return formCollection.get(list_header_title.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup viewGroup) {

        int groupType = getGroupType(groupPosition);

        if (convertView == null || Integer.parseInt(convertView.getTag().toString()) != groupType) {
            switch (groupType) {
                case GROUP_TYPE_1:
                    convertView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_applicant_parent, null);
                    convertView.setTag(groupType);
                    break;

                case GROUP_TYPE_2:
                    convertView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_applicant_parent, null);
                    convertView.setTag(groupType);
                    break;

                case GROUP_TYPE_3:
                    convertView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_applicant_parent, null);
                    convertView.setTag(groupType);
                    break;

                case GROUP_TYPE_4:
                    convertView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_applicant_parent, null);
                    convertView.setTag(groupType);
                    break;
            }

        } else {

        }

        switch (groupType) {
            case GROUP_TYPE_1:
                tv_header_title = (AppCompatTextView) convertView.findViewById(R.id.tv_applicant_form_header);
                img_header_icon = (AppCompatImageView) convertView.findViewById(R.id.img_icon);
                img_application_groupIndicator = (AppCompatImageView) convertView.findViewById(R.id.img_application_groupIndicator);

                tv_header_title.setText(list_header_title.get(groupPosition));
                img_header_icon.setImageResource(isExpanded ? R.drawable.ic_personaldetails_selected : R.drawable.ic_personaldestails_unselected);
                img_application_groupIndicator.setImageResource(isExpanded ? R.drawable.ic_expanddetails_selected : R.drawable.ic_expanddetails_unselected);
                break;

            case GROUP_TYPE_2:
                tv_header_title = (AppCompatTextView) convertView.findViewById(R.id.tv_applicant_form_header);
                img_header_icon = (AppCompatImageView) convertView.findViewById(R.id.img_icon);
                img_application_groupIndicator = (AppCompatImageView) convertView.findViewById(R.id.img_application_groupIndicator);

                tv_header_title.setText(list_header_title.get(groupPosition));
                img_header_icon.setImageResource(isExpanded ? R.drawable.ic_passportdetails_selected : R.drawable.ic_passportdetails_unselected);
                img_application_groupIndicator.setImageResource(isExpanded ? R.drawable.ic_expanddetails_selected : R.drawable.ic_expanddetails_unselected);
                break;

            case GROUP_TYPE_3:
                tv_header_title = (AppCompatTextView) convertView.findViewById(R.id.tv_applicant_form_header);
                img_header_icon = (AppCompatImageView) convertView.findViewById(R.id.img_icon);
                img_application_groupIndicator = (AppCompatImageView) convertView.findViewById(R.id.img_application_groupIndicator);

                tv_header_title.setText(list_header_title.get(groupPosition));
                img_header_icon.setImageResource(isExpanded ? R.drawable.ic_contactdetails_selected : R.drawable.ic_contactdetails_unselected);
                img_application_groupIndicator.setImageResource(isExpanded ? R.drawable.ic_expanddetails_selected : R.drawable.ic_expanddetails_unselected);
                break;

            case GROUP_TYPE_4:
                tv_header_title = (AppCompatTextView) convertView.findViewById(R.id.tv_applicant_form_header);
                img_header_icon = (AppCompatImageView) convertView.findViewById(R.id.img_icon);
                img_application_groupIndicator = (AppCompatImageView) convertView.findViewById(R.id.img_application_groupIndicator);

                tv_header_title.setText(list_header_title.get(groupPosition));
                img_header_icon.setImageResource(isExpanded ? R.drawable.ic_traveldetails_selected : R.drawable.ic_traveldetails_unselected);
                img_application_groupIndicator.setImageResource(isExpanded ? R.drawable.ic_expanddetails_selected : R.drawable.ic_expanddetails_unselected);
                break;


        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        int childType = getChildType(groupPosition, childPosition);

        // We need to create a new "cell container"
        if (convertView == null || Integer.parseInt(convertView.getTag().toString()) != childType) {
            switch (childType) {
                case CHILD_TYPE_1:
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_personal_details_form, null);
                    convertView.setTag(childType);
                    break;
                case CHILD_TYPE_2:
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_passport_details_form, null);
                    convertView.setTag(childType);
                    break;
                case CHILD_TYPE_3:
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_contact_details_form, null);
                    convertView.setTag(childType);
                    break;
                case CHILD_TYPE_4:
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_travel_details_form, null);
                    convertView.setTag(childType);
                    break;
                default:
                    // Maybe we should implement a default behaviour but it should be ok we know there are 4 child types right?
                    break;
            }
        }

        // We'll reuse the existing one
        else {
            // There is nothing to do here really we just need to set the content of view which we do in both cases
        }

        switch (childType) {
            case CHILD_TYPE_1:
                initPersonalDetails(convertView);
                break;

            case CHILD_TYPE_2:
                initPassportDetails(convertView);
                break;

            case CHILD_TYPE_3:
                initContactDetails(convertView);
                break;

            case CHILD_TYPE_4:
                initTravelDetails(convertView);
                break;
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    @Override
    public int getChildTypeCount() {
        return 4; // I defined 4 child types (CHILD_TYPE_1, CHILD_TYPE_2, CHILD_TYPE_3, CHILD_TYPE_UNDEFINED)
    }

    @Override
    public int getGroupTypeCount() {
        return 4; // I defined 3 groups types (GROUP_TYPE_1, GROUP_TYPE_2, GROUP_TYPE_3)
    }

    @Override
    public int getGroupType(int groupPosition) {
        switch (groupPosition) {
            case 0:
                return GROUP_TYPE_1;
            case 1:
                return GROUP_TYPE_2;
            case 2:
                return GROUP_TYPE_3;
            default:
                return GROUP_TYPE_4;
        }
    }

    @Override
    public int getChildType(int groupPosition, int childPosition) {
        switch (groupPosition) {
            case 0:
                return CHILD_TYPE_1;
            case 1:
                return CHILD_TYPE_2;
            case 2:
                return CHILD_TYPE_3;
            case 3:
                return CHILD_TYPE_4;
        }
        return CHILD_TYPE_1;
    }

    void initPersonalDetails(View convertView) {
        et_surname = (AppCompatEditText) convertView.findViewById(R.id.et_pd_surname);
        et_given_name = (AppCompatEditText) convertView.findViewById(R.id.et_pd_given_names);
        et_nationality = (AppCompatEditText) convertView.findViewById(R.id.et_pd_nationality);
        et_gender = (AppCompatEditText) convertView.findViewById(R.id.et_pd_gender);
        et_dob = (AppCompatEditText) convertView.findViewById(R.id.et_pd_dob);
        et_pob = (AppCompatEditText) convertView.findViewById(R.id.et_pd_pob);
        et_cob = (AppCompatEditText) convertView.findViewById(R.id.et_pd_cob);
        et_marital_status = (AppCompatEditText) convertView.findViewById(R.id.et_pd_marital_status);
        et_religion = (AppCompatEditText) convertView.findViewById(R.id.et_pd_religion);
        et_language = (AppCompatEditText) convertView.findViewById(R.id.et_pd_language_spoken);
        et_profession = (AppCompatEditText) convertView.findViewById(R.id.et_pd_profession);
        et_father_name = (AppCompatEditText) convertView.findViewById(R.id.et_pd_father_name);
        et_mother_name = (AppCompatEditText) convertView.findViewById(R.id.et_pd_mother_name);
        et_husband_name = (AppCompatEditText) convertView.findViewById(R.id.et_pd_husband_name);

        et_nationality.setOnClickListener(this);
        et_gender.setOnClickListener(this);
        et_dob.setOnClickListener(this);
        et_cob.setOnClickListener(this);
        et_marital_status.setOnClickListener(this);
        et_religion.setOnClickListener(this);
        et_language.setOnClickListener(this);
        et_profession.setOnClickListener(this);

        et_cob.setInputType(InputType.TYPE_NULL);
        et_marital_status.setInputType(InputType.TYPE_NULL);

        countryModelArrayList = new ArrayList<>();
        languageModelArrayList = new ArrayList<>();
        maritalModelArrayList = new ArrayList<>();
        professionModelArrayList = new ArrayList<>();
        religionModelArrayList = new ArrayList<>();

        countryModelArrayList = CountryHelper.getInstance(context).getCountryList();
        languageModelArrayList = LanguageHelper.getInstance(context).getLanguageList();
        maritalModelArrayList = MaritalHelper.getInstance(context).getMatitalStatusList();
        professionModelArrayList = ProfessionHelper.getInstance(context).getProfessionList();
        religionModelArrayList = ReligionHelper.getInstance(context).getReligionList();

        et_surname.addTextChangedListener(new FormValidator(et_surname) {
            @Override
            public void validate(TextView textView, String text) {
                if (!ApiUtils.isValidateSurName(et_surname.getText().toString()))
                    et_surname.setError(context.getResources().getString(R.string.invalid_surname));
                checkRequiredFields(processForm);
            }
        });

        et_given_name.addTextChangedListener(new FormValidator(et_given_name) {
            @Override
            public void validate(TextView textView, String text) {
                if (!ApiUtils.isValidateGivenName(et_given_name.getText().toString()))
                    et_given_name.setError(context.getResources().getString(R.string.invalid_name));
            }
        });

        et_pob.addTextChangedListener(new FormValidator(et_pob) {
            @Override
            public void validate(TextView textView, String text) {
                if (ApiUtils.isValidStringValue(et_pob.getText().toString()))
                    et_pob.setError(context.getResources().getString(R.string.invalid_pob));
            }
        });

        et_father_name.addTextChangedListener(new FormValidator(et_father_name) {
            @Override
            public void validate(TextView textView, String text) {
                if (!ApiUtils.isValidateGivenName(et_father_name.getText().toString()))
                    et_father_name.setError(context.getResources().getString(R.string.invalid_father_name));
            }
        });

        et_mother_name.addTextChangedListener(new FormValidator(et_mother_name) {
            @Override
            public void validate(TextView textView, String text) {
                if (!ApiUtils.isValidateGivenName(et_mother_name.getText().toString()))
                    et_mother_name.setError(context.getResources().getString(R.string.invalid_mother_name));
            }
        });

        et_husband_name.addTextChangedListener(new FormValidator(et_husband_name) {
            @Override
            public void validate(TextView textView, String text) {
                if (!ApiUtils.isValidateGivenName(et_husband_name.getText().toString()))
                    et_husband_name.setError(context.getResources().getString(R.string.invalid_husband_name));
            }
        });
    }

    boolean validatePersonalDetails() {
        if (!ApiUtils.isValidateSurName(et_surname.getText().toString())) {
            et_surname.setError(context.getResources().getString(R.string.invalid_surname));
            return false;
        }
        if (!ApiUtils.isValidateGivenName(et_given_name.getText().toString())) {
            et_given_name.setError(context.getResources().getString(R.string.invalid_name));
            return false;
        }
        if (!ApiUtils.isValidStringValue(et_nationality.getText().toString())) {
            et_nationality.setError(context.getResources().getString(R.string.invalid_nationality));
            return false;
        }
        if (!ApiUtils.isValidStringValue(et_gender.getText().toString())) {
            et_gender.setError(context.getResources().getString(R.string.invalid_gender));
            return false;
        }
        if (!ApiUtils.isValidStringValue(et_dob.getText().toString())) {
            et_dob.setError(context.getResources().getString(R.string.invalid_dob));
            return false;
        }
        if (!ApiUtils.isValidStringValue(et_pob.getText().toString())) {
            et_pob.setError(context.getResources().getString(R.string.invalid_pob));
            return false;
        }
        if (!ApiUtils.isValidStringValue(et_cob.getText().toString())) {
            et_cob.setError(context.getResources().getString(R.string.invalid_cob));
            return false;
        }
        if (!ApiUtils.isValidStringValue(et_marital_status.getText().toString())) {
            et_marital_status.setError(context.getResources().getString(R.string.invalid_marital_status));
            return false;
        }
        if (!ApiUtils.isValidStringValue(et_religion.getText().toString())) {
            et_religion.setError(context.getResources().getString(R.string.invalid_religion));
            return false;
        }
        if (!ApiUtils.isValidStringValue(et_language.getText().toString())) {
            et_language.setError(context.getResources().getString(R.string.invalid_lang_spoken));
            return false;
        }
        if (!ApiUtils.isValidStringValue(et_profession.getText().toString())) {
            et_profession.setError(context.getResources().getString(R.string.invalid_profession));
            return false;
        }
        if (!ApiUtils.isValidStringValue(et_father_name.getText().toString())) {
            et_father_name.setError(context.getResources().getString(R.string.invalid_father_name));
            return false;
        }
        if (!ApiUtils.isValidStringValue(et_husband_name.getText().toString())) {
            et_husband_name.setError(context.getResources().getString(R.string.invalid_husband_name));
            return false;
        }
        if (!ApiUtils.isValidStringValue(et_mother_name.getText().toString())) {
            et_mother_name.setError(context.getResources().getString(R.string.invalid_mother_name));
            return false;
        }
        return true;
    }

    void initPassportDetails(View convertView) {
        et_pp_no = (AppCompatEditText) convertView.findViewById(R.id.et_ppd_passport_no);
        et_pp_type = (AppCompatEditText) convertView.findViewById(R.id.et_ppd_passport_type);
        et_pp_issue_govt = (AppCompatEditText) convertView.findViewById(R.id.et_ppd_passport_type);
        et_pp_place_issue = (AppCompatEditText) convertView.findViewById(R.id.et_ppd_place_issue);
        et_pp_doi = (AppCompatEditText) convertView.findViewById(R.id.et_ppd_dt_issue);
        et_pp_doe = (AppCompatEditText) convertView.findViewById(R.id.et_ppd_dt_expiry);

        et_pp_doi.setOnClickListener(this);
        et_pp_doe.setOnClickListener(this);

        et_pp_no.addTextChangedListener(new FormValidator(et_pp_no) {
            @Override
            public void validate(TextView textView, String text) {
                if (!ApiUtils.isValidStringValue(et_pp_no.getText().toString()))
                    et_pp_no.setError(context.getResources().getString(R.string.invalid_pp_no));
            }
        });

        et_pp_type.addTextChangedListener(new FormValidator(et_pp_type) {
            @Override
            public void validate(TextView textView, String text) {
                if (!ApiUtils.isValidStringValue(et_pp_type.getText().toString()))
                    et_pp_type.setError(context.getResources().getString(R.string.invalid_pp_type));
            }
        });

        et_pp_issue_govt.addTextChangedListener(new FormValidator(et_pp_issue_govt) {
            @Override
            public void validate(TextView textView, String text) {
                if (!ApiUtils.isValidStringValue(et_pp_issue_govt.getText().toString()))
                    et_pp_issue_govt.setError(context.getResources().getString(R.string.invalid_pp_issue_govt));
            }
        });

        et_pp_place_issue.addTextChangedListener(new FormValidator(et_pp_place_issue) {
            @Override
            public void validate(TextView textView, String text) {
                if (!ApiUtils.isValidStringValue(et_pp_place_issue.getText().toString()))
                    et_pp_place_issue.setError(context.getResources().getString(R.string.invalid_pp_poi));
            }
        });

        et_pp_doi.addTextChangedListener(new FormValidator(et_pp_doi) {
            @Override
            public void validate(TextView textView, String text) {
                if (!ApiUtils.isValidStringValue(et_pp_place_issue.getText().toString()))
                    et_pp_place_issue.setError(context.getResources().getString(R.string.invalid_pp_poi));
            }
        });
    }

    boolean validatePassportDetails() {
        if (!ApiUtils.isValidateSurName(et_pp_no.getText().toString())) {
            et_pp_no.setError(context.getResources().getString(R.string.invalid_pp_no));
            return false;
        }
        if (!ApiUtils.isValidateSurName(et_pp_type.getText().toString())) {
            et_pp_type.setError(context.getResources().getString(R.string.invalid_pp_type));
            return false;
        }
        if (!ApiUtils.isValidateSurName(et_pp_issue_govt.getText().toString())) {
            et_pp_issue_govt.setError(context.getResources().getString(R.string.invalid_pp_issue_govt));
            return false;
        }
        if (!ApiUtils.isValidateSurName(et_pp_doi.getText().toString())) {
            et_pp_doi.setError(context.getResources().getString(R.string.invalid_pp_doi));
            return false;
        }
        if (!ApiUtils.isValidateSurName(et_pp_doe.getText().toString())) {
            et_pp_doe.setError(context.getResources().getString(R.string.invalid_pp_doe));
            return false;
        }
        return true;
    }

    void initContactDetails(final View convertView) {
        et_address_line1 = (AppCompatEditText) convertView.findViewById(R.id.et_cd_address_line_1);
        et_address_line2 = (AppCompatEditText) convertView.findViewById(R.id.et_cd_address_line_2);
        et_address_line3 = (AppCompatEditText) convertView.findViewById(R.id.et_cd_address_line_3);
        et_city = (AppCompatEditText) convertView.findViewById(R.id.et_cd_city);
        et_country = (AppCompatEditText) convertView.findViewById(R.id.et_cd_country);
        et_telephone_no = (AppCompatEditText) convertView.findViewById(R.id.et_cd_telephone);

        et_country.setOnClickListener(this);

        et_address_line1.addTextChangedListener(new FormValidator(et_address_line1) {
            @Override
            public void validate(TextView textView, String text) {
                if (!ApiUtils.isValidStringValue(et_address_line1.getText().toString()))
                    et_address_line1.setError(context.getResources().getString(R.string.invalid_address_line1));
            }
        });

        et_telephone_no.addTextChangedListener(new FormValidator(et_telephone_no) {
            @Override
            public void validate(TextView textView, String text) {
                if (!ApiUtils.isValidMobileNumber(et_telephone_no.getText().toString()))
                    et_telephone_no.setError(context.getResources().getString(R.string.invalid_mob_no));
            }
        });
    }

    boolean validateContactDetails() {
        if (!ApiUtils.isValidStringValue(et_address_line1.getText().toString())) {
            et_address_line1.setError(context.getResources().getString(R.string.invalid_address_line1));
            return false;
        }

        if (!ApiUtils.isValidStringValue(et_city.getText().toString())) {
            et_city.setError(context.getResources().getString(R.string.invalid_city));
            return false;
        }

        if (!ApiUtils.isValidStringValue(et_country.getText().toString())) {
            et_country.setError(context.getResources().getString(R.string.invalid_country));
            return false;
        }

        if (!ApiUtils.isValidMobileNumber(et_telephone_no.getText().toString())) {
            et_telephone_no.setError(context.getResources().getString(R.string.invalid_mob_no));
            return false;
        }
        return true;
    }

    void initTravelDetails(View convertView) {
        et_arrival_airlines = (AppCompatEditText) convertView.findViewById(R.id.et_td_arrival_airlines);
        et_arrival_flight_no = (AppCompatEditText) convertView.findViewById(R.id.et_td_arrival_flight_no);
        et_arrival_coming_from = (AppCompatEditText) convertView.findViewById(R.id.et_td_arrival_coming_from);
        et_doa = (AppCompatEditText) convertView.findViewById(R.id.et_td_arrival_date);
        et_arrival_time_hr = (AppCompatEditText) convertView.findViewById(R.id.et_td_arrival_time_hr);
        et_arrival_time_min = (AppCompatEditText) convertView.findViewById(R.id.et_td_arrival_time_hr);
        et_arrival_time_min = (AppCompatEditText) convertView.findViewById(R.id.et_td_arrival_time_min);
        et_dept_airline = (AppCompatEditText) convertView.findViewById(R.id.et_td_departure_airlines);
        et_dept_flight_no = (AppCompatEditText) convertView.findViewById(R.id.et_td_departure_flight_no);
        et_dept_leaving_to = (AppCompatEditText) convertView.findViewById(R.id.et_td_departure_leaving_to);
        et_dod = (AppCompatEditText) convertView.findViewById(R.id.et_td_departure_date);
        et_dept_time_hr = (AppCompatEditText) convertView.findViewById(R.id.et_td_departure_time_hr);
        et_dept_time_min = (AppCompatEditText) convertView.findViewById(R.id.et_td_departure_time_min);
    }

    void checkRequiredFields(ProcessForm process) {
        if (!et_surname.getText().toString().isEmpty() && !et_given_name.getText().toString().isEmpty() && !et_nationality.getText().toString().isEmpty()
                && !et_gender.getText().toString().isEmpty() && !et_dob.getText().toString().isEmpty() && !et_pob.getText().toString().isEmpty()
                && !et_cob.getText().toString().isEmpty() && !et_marital_status.getText().toString().isEmpty() && !et_religion.getText().toString().isEmpty()
                && !et_language.getText().toString().isEmpty() && !et_profession.getText().toString().isEmpty() && !et_father_name.getText().toString().isEmpty()
                && !et_mother_name.getText().toString().isEmpty() && !et_husband_name.getText().toString().isEmpty()
                && !et_pp_no.getText().toString().isEmpty() && !et_pp_type.getText().toString().isEmpty() && !et_pp_issue_govt.getText().toString().isEmpty()
                && !et_pp_place_issue.getText().toString().isEmpty() && !et_pp_doi.getText().toString().isEmpty() && !et_pp_doe.getText().toString().isEmpty()
                && !et_address_line1.getText().toString().isEmpty() && !et_city.getText().toString().isEmpty() && !et_country.getText().toString().isEmpty()
                && !et_telephone_no.getText().toString().isEmpty())
            process.enableSubmit(true);
        else
            process.enableSubmit(false);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.et_pd_cob:
                Transaction.getmTransactionInstance().setTransaction_type(Transaction.COUNTRY);
                searchNameList = new ArrayList<>();
                for (int i = 0; i < countryModelArrayList.size(); i++) {
                    searchNameList.add(countryModelArrayList.get(i).getCountryName());
                }
                intent = new Intent(context, SearchFieldActivity.class);
                intent.putExtra(TITLE,context.getString(R.string.select_country_birth));
                intent.putStringArrayListExtra(SEARCHLIST, searchNameList);
                applicantionFragment.startActivityForResult(intent, REQUEST_FOR_ACTIVITY_CODE);
                break;

            case R.id.et_pd_marital_status:
                Transaction.getmTransactionInstance().setTransaction_type(Transaction.MARITALSTATUS);
                searchNameList = new ArrayList<>();
                for (int i = 0; i < maritalModelArrayList.size(); i++) {
                    searchNameList.add(maritalModelArrayList.get(i).getMaritalName());
                }
                intent = new Intent(context, SearchFieldActivity.class);
                intent.putExtra(TITLE,context.getString(R.string.select_marital_status));
                intent.putStringArrayListExtra(SEARCHLIST, searchNameList);
                applicantionFragment.startActivityForResult(intent, REQUEST_FOR_ACTIVITY_CODE);
                break;

            case R.id.et_pd_religion:
                Transaction.getmTransactionInstance().setTransaction_type(Transaction.RELIGION);
                searchNameList = new ArrayList<>();
                for (int i = 0; i < religionModelArrayList.size(); i++) {
                    searchNameList.add(religionModelArrayList.get(i).getReligionName());
                }
                intent = new Intent(context, SearchFieldActivity.class);
                intent.putExtra(TITLE,context.getString(R.string.select_religion));
                intent.putStringArrayListExtra(SEARCHLIST, searchNameList);
                applicantionFragment.startActivityForResult(intent, REQUEST_FOR_ACTIVITY_CODE);
                break;

            case R.id.et_pd_language_spoken:
                Transaction.getmTransactionInstance().setTransaction_type(Transaction.LANGUAGE);
                searchNameList = new ArrayList<>();
                for (int i = 0; i < languageModelArrayList.size(); i++) {
                    searchNameList.add(languageModelArrayList.get(i).getLanguageName());
                }
                intent = new Intent(context, SearchFieldActivity.class);
                intent.putExtra(TITLE,context.getString(R.string.select_language));
                intent.putStringArrayListExtra(SEARCHLIST, searchNameList);
                applicantionFragment.startActivityForResult(intent, REQUEST_FOR_ACTIVITY_CODE);
                break;

            case R.id.et_pd_profession:
                Transaction.getmTransactionInstance().setTransaction_type(Transaction.PROFESSION);
                searchNameList = new ArrayList<>();
                for (int i = 0; i < professionModelArrayList.size(); i++) {
                    searchNameList.add(professionModelArrayList.get(i).getProfessionName());
                }
                intent = new Intent(context, SearchFieldActivity.class);
                intent.putExtra(TITLE,context.getString(R.string.select_profession));
                intent.putStringArrayListExtra(SEARCHLIST, searchNameList);
                applicantionFragment.startActivityForResult(intent, REQUEST_FOR_ACTIVITY_CODE);
                break;

            case R.id.et_pd_nationality:
                popupDialog(context.getString(R.string.select_nationality), list_nationality, view.getId());
                break;

            case R.id.et_pd_gender:
                popupDialog(context.getString(R.string.select_gender), list_gender, view.getId());
                break;

            case R.id.et_pd_dob:
                DatePickerDialogFragment dialog = new DatePickerDialogFragment();
                Bundle args = new Bundle();
                args.putInt("id", view.getId());
                dialog.setArguments(args);
                dialog.show(applicantionFragment.getActivity().getSupportFragmentManager(), DIALOG_DATE);
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (requestCode == REQUEST_FOR_ACTIVITY_CODE) {
            if (Transaction.getmTransactionInstance().getTransaction_type() == 1) {
                inputMethodManager.hideSoftInputFromWindow(et_cob.getWindowToken(), 0);
                et_cob.setText(Transaction.getmTransactionInstance().getCountry());
            } else if (Transaction.getmTransactionInstance().getTransaction_type() == 3) {
                inputMethodManager.hideSoftInputFromWindow(et_marital_status.getWindowToken(), 0);
                et_marital_status.setText(Transaction.getmTransactionInstance().getMaritalStatus());
            } else if (Transaction.getmTransactionInstance().getTransaction_type() == 6) {
                inputMethodManager.hideSoftInputFromWindow(et_religion.getWindowToken(), 0);
                et_religion.setText(Transaction.getmTransactionInstance().getReligion());
            } else if (Transaction.getmTransactionInstance().getTransaction_type() == 2) {
                inputMethodManager.hideSoftInputFromWindow(et_language.getWindowToken(), 0);
                et_language.setText(Transaction.getmTransactionInstance().getLanguage());
            } else if (Transaction.getmTransactionInstance().getTransaction_type() == 5) {
                inputMethodManager.hideSoftInputFromWindow(et_profession.getWindowToken(), 0);
                et_profession.setText(Transaction.getmTransactionInstance().getProfession());
            }
        }
    }

    void popupDialog(String title, final CharSequence[] list, final int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setItems(list, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (id == R.id.et_pd_nationality)
                    et_nationality.setText(list[which]);
                if (id == R.id.et_pd_gender)
                    et_gender.setText(list[which]);
            }
        });
        builder.show();
    }

    @Override
    public void onFinishDialog(Date date, int id) {

        if (id == R.id.et_pd_dob)
            et_dob.setText(ApiUtils.formatDate(date));

    }
}

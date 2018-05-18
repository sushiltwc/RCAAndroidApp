package com.twc.rca.product.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.twc.rca.R;
import com.twc.rca.activities.BaseFragment;
import com.twc.rca.applicant.activities.SearchFieldActivity;
import com.twc.rca.applicant.model.ApplicantModel;
import com.twc.rca.applicant.model.PassportBackModel;
import com.twc.rca.applicant.model.PassportFrontModel;
import com.twc.rca.applicant.model.ReceiveDocumentModel;
import com.twc.rca.applicant.task.AddApplicantTask;
import com.twc.rca.database.CountryHelper;
import com.twc.rca.database.LanguageHelper;
import com.twc.rca.database.MaritalHelper;
import com.twc.rca.database.PassportTypeHelper;
import com.twc.rca.database.ProfessionHelper;
import com.twc.rca.database.ReligionHelper;
import com.twc.rca.model.CountryModel;
import com.twc.rca.model.LanguageModel;
import com.twc.rca.model.MaritalModel;
import com.twc.rca.model.PassportTypeModel;
import com.twc.rca.model.ProfessionModel;
import com.twc.rca.model.ReligionModel;
import com.twc.rca.product.model.Transaction;
import com.twc.rca.utils.ApiUtils;
import com.twc.rca.utils.FormValidator;
import com.twc.rca.utils.GVPassportUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Sushil on 16-05-2018.
 */

public class ApplicationFormFragment extends BaseFragment implements View.OnClickListener, DatePickerDialogFragment.DateDialogListener {

    public int REQUEST_FOR_ACTIVITY_CODE = 100;

    AppCompatEditText et_surname, et_given_name, et_nationality, et_gender, et_dob, et_pob, et_cob, et_marital_status, et_religion, et_language, et_profession, et_father_name, et_mother_name, et_husband_name;

    AppCompatEditText et_pp_no, et_pp_type, et_pp_issue_govt, et_pp_place_issue, et_pp_doi, et_pp_doe;

    AppCompatEditText et_address_line1, et_address_line2, et_address_line3, et_city, et_country, et_telephone_no;

    AppCompatButton btn_submit;

    CharSequence list_nationality[] = new CharSequence[]{"India", "Pakistan", "Others"};

    CharSequence list_gender[] = new CharSequence[]{"Male", "Female"};

    String strSurname, strGivenName, strNationality, strGender, strDob, strPob, strCob, strMaritalStatus, strReligion, strLanguage, strProfession, strFName, strMName, strHName, strPPNo, strPPType, strPPIssueGovt, strPIssue, strPPDoi, strPPDoe, strAddress1, strAddress2, strAddress3, strCity, strCountry, strTelNo;

    private static final String DIALOG_DATE = "DatePickerDialogFragment";

    ArrayList<CountryModel> countryModelArrayList;
    ArrayList<LanguageModel> languageModelArrayList;
    ArrayList<MaritalModel> maritalModelArrayList;
    ArrayList<ProfessionModel> professionModelArrayList;
    ArrayList<ReligionModel> religionModelArrayList;
    ArrayList<PassportTypeModel> passportTypeModelArrayList;
    ArrayList<String> searchNameList;

    PassportFrontModel passportFrontModel;
    PassportBackModel passportBackModel;
    ApplicantModel applicantModel;

    static ApplicationFormFragment applicationFormFragment;

    public static ApplicationFormFragment getInstance() {
        return applicationFormFragment;
    }

    public static ApplicationFormFragment newInstance() {
        applicationFormFragment = new ApplicationFormFragment();
        return applicationFormFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_application_form, container, false);

        applicantModel = getArguments().getParcelable("applicant");

        btn_submit = (AppCompatButton) view.findViewById(R.id.btn_submit_application_form);

        if (applicantModel.getApplicantSubmited().equalsIgnoreCase("Y"))
            btn_submit.setVisibility(View.GONE);

        btn_submit.setOnClickListener(this);

        btn_submit.setEnabled(true);

        initPersonalDetails(view);

        initPassportDetails(view);

        initContactDetails(view);

        return view;
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

        countryModelArrayList = CountryHelper.getInstance(getContext()).getCountryList();
        languageModelArrayList = LanguageHelper.getInstance(getContext()).getLanguageList();
        maritalModelArrayList = MaritalHelper.getInstance(getContext()).getMatitalStatusList();
        professionModelArrayList = ProfessionHelper.getInstance(getContext()).getProfessionList();
        religionModelArrayList = ReligionHelper.getInstance(getContext()).getReligionList();

        et_surname.addTextChangedListener(new FormValidator(et_surname) {
            @Override
            public void validate(TextView textView, String text) {
                strSurname = et_surname.getText().toString();
                if (!ApiUtils.isValidateSurName(strSurname))
                    et_surname.setError(getContext().getResources().getString(R.string.invalid_surname));
                checkRequiredFields();
            }
        });

        et_given_name.addTextChangedListener(new FormValidator(et_given_name) {
            @Override
            public void validate(TextView textView, String text) {
                strGivenName = et_given_name.getText().toString();
                if (!ApiUtils.isValidateGivenName(strGivenName))
                    et_given_name.setError(getContext().getResources().getString(R.string.invalid_name));
                checkRequiredFields();
            }
        });

        et_pob.addTextChangedListener(new FormValidator(et_pob) {
            @Override
            public void validate(TextView textView, String text) {
                strPob = et_pob.getText().toString();
                if (ApiUtils.isValidStringValue(strPob))
                    et_pob.setError(getContext().getResources().getString(R.string.invalid_pob));
                checkRequiredFields();
            }
        });

        et_father_name.addTextChangedListener(new FormValidator(et_father_name) {
            @Override
            public void validate(TextView textView, String text) {
                strFName = et_father_name.getText().toString();
                if (!ApiUtils.isValidateGivenName(strFName))
                    et_father_name.setError(getContext().getResources().getString(R.string.invalid_father_name));
                checkRequiredFields();
            }
        });

        et_mother_name.addTextChangedListener(new FormValidator(et_mother_name) {
            @Override
            public void validate(TextView textView, String text) {
                strMName = et_mother_name.getText().toString();
                if (!ApiUtils.isValidateGivenName(strMName))
                    et_mother_name.setError(getContext().getResources().getString(R.string.invalid_mother_name));
                checkRequiredFields();
            }
        });

        et_husband_name.addTextChangedListener(new FormValidator(et_husband_name) {
            @Override
            public void validate(TextView textView, String text) {
                strHName = et_husband_name.getText().toString();
                if (!ApiUtils.isValidateGivenName(strHName))
                    et_husband_name.setError(getContext().getResources().getString(R.string.invalid_husband_name));
                checkRequiredFields();
            }
        });
    }

    void initPassportDetails(View convertView) {
        et_pp_no = (AppCompatEditText) convertView.findViewById(R.id.et_ppd_passport_no);
        et_pp_type = (AppCompatEditText) convertView.findViewById(R.id.et_ppd_passport_type);
        et_pp_issue_govt = (AppCompatEditText) convertView.findViewById(R.id.et_ppd_passport_issuing_govt);
        et_pp_place_issue = (AppCompatEditText) convertView.findViewById(R.id.et_ppd_place_issue);
        et_pp_doi = (AppCompatEditText) convertView.findViewById(R.id.et_ppd_dt_issue);
        et_pp_doe = (AppCompatEditText) convertView.findViewById(R.id.et_ppd_dt_expiry);

        et_pp_type.setOnClickListener(this);
        et_pp_issue_govt.setOnClickListener(this);
        et_pp_doi.setOnClickListener(this);
        et_pp_doe.setOnClickListener(this);

        passportTypeModelArrayList = PassportTypeHelper.getInstance(getContext()).getPassportTypeList();

        et_pp_no.addTextChangedListener(new FormValidator(et_pp_no) {
            @Override
            public void validate(TextView textView, String text) {
                strPPNo = et_pp_no.getText().toString();
                if (ApiUtils.isValidStringValue(strPPNo))
                    et_pp_no.setError(getContext().getResources().getString(R.string.invalid_pp_no));
                checkRequiredFields();
            }
        });

        et_pp_place_issue.addTextChangedListener(new FormValidator(et_pp_place_issue) {
            @Override
            public void validate(TextView textView, String text) {
                strPIssue = et_pp_place_issue.getText().toString();
                if (ApiUtils.isValidStringValue(et_pp_place_issue.getText().toString()))
                    et_pp_place_issue.setError(getContext().getResources().getString(R.string.invalid_pp_poi));
                checkRequiredFields();
            }
        });
    }

    void initContactDetails(final View convertView) {
        et_address_line1 = (AppCompatEditText) convertView.findViewById(R.id.et_cd_address_line_1);
        et_address_line2 = (AppCompatEditText) convertView.findViewById(R.id.et_cd_address_line_2);
        et_address_line3 = (AppCompatEditText) convertView.findViewById(R.id.et_cd_address_line_3);
        et_city = (AppCompatEditText) convertView.findViewById(R.id.et_cd_city);
        et_country = (AppCompatEditText) convertView.findViewById(R.id.et_cd_country);
        et_telephone_no = (AppCompatEditText) convertView.findViewById(R.id.et_cd_telephone);

        et_country.setOnClickListener(this);

        countryModelArrayList = new ArrayList<>();
        countryModelArrayList = CountryHelper.getInstance(getContext()).getCountryList();

        et_address_line1.addTextChangedListener(new FormValidator(et_address_line1) {
            @Override
            public void validate(TextView textView, String text) {
                strAddress1 = et_address_line1.getText().toString();
                if (ApiUtils.isValidStringValue(strAddress1))
                    et_address_line1.setError(getContext().getResources().getString(R.string.invalid_address_line1));
                checkRequiredFields();
            }
        });

        et_address_line2.addTextChangedListener(new FormValidator(et_address_line2) {
            @Override
            public void validate(TextView textView, String text) {
                strAddress2 = et_address_line2.getText().toString();
                if (ApiUtils.isValidStringValue(strAddress2))
                    et_address_line2.setError(getContext().getResources().getString(R.string.invalid_address_line2));
            }
        });

        et_address_line3.addTextChangedListener(new FormValidator(et_address_line3) {
            @Override
            public void validate(TextView textView, String text) {
                strAddress3 = et_address_line3.getText().toString();
                if (ApiUtils.isValidStringValue(strAddress3))
                    et_address_line3.setError(getContext().getResources().getString(R.string.invalid_address_line3));
            }
        });

        et_city.addTextChangedListener(new FormValidator(et_city) {
            @Override
            public void validate(TextView textView, String text) {
                strCity = et_city.getText().toString();
                if (ApiUtils.isValidStringValue(et_city.getText().toString()))
                    et_city.setError(getContext().getResources().getString(R.string.invalid_city));
                checkRequiredFields();
            }
        });

        et_telephone_no.addTextChangedListener(new FormValidator(et_telephone_no) {
            @Override
            public void validate(TextView textView, String text) {
                strTelNo = et_telephone_no.getText().toString();
                if (!ApiUtils.isValidMobileNumber(et_telephone_no.getText().toString()))
                    et_telephone_no.setError(getContext().getResources().getString(R.string.invalid_mob_no));
                checkRequiredFields();
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.et_pd_cob:
                Transaction.getmTransactionInstance().setTransaction_type(Transaction.COUNTRY_BIRTH);
                searchNameList = new ArrayList<>();
                for (int i = 0; i < countryModelArrayList.size(); i++) {
                    searchNameList.add(countryModelArrayList.get(i).getCountryName());
                }
                intent = new Intent(getContext(), SearchFieldActivity.class);
                intent.putExtra(SearchFieldActivity.TITLE, getContext().getString(R.string.select_country_birth));
                intent.putStringArrayListExtra(SearchFieldActivity.SEARCHLIST, searchNameList);
                applicationFormFragment.startActivityForResult(intent, REQUEST_FOR_ACTIVITY_CODE);
                break;

            case R.id.et_pd_marital_status:
                Transaction.getmTransactionInstance().setTransaction_type(Transaction.MARITALSTATUS);
                searchNameList = new ArrayList<>();
                for (int i = 0; i < maritalModelArrayList.size(); i++) {
                    searchNameList.add(maritalModelArrayList.get(i).getMaritalName());
                }
                intent = new Intent(getContext(), SearchFieldActivity.class);
                intent.putExtra(SearchFieldActivity.TITLE, getContext().getString(R.string.select_marital_status));
                intent.putStringArrayListExtra(SearchFieldActivity.SEARCHLIST, searchNameList);
                applicationFormFragment.startActivityForResult(intent, REQUEST_FOR_ACTIVITY_CODE);
                break;

            case R.id.et_pd_religion:
                Transaction.getmTransactionInstance().setTransaction_type(Transaction.RELIGION);
                searchNameList = new ArrayList<>();
                for (int i = 0; i < religionModelArrayList.size(); i++) {
                    searchNameList.add(religionModelArrayList.get(i).getReligionName());
                }
                intent = new Intent(getContext(), SearchFieldActivity.class);
                intent.putExtra(SearchFieldActivity.TITLE, getContext().getString(R.string.select_religion));
                intent.putStringArrayListExtra(SearchFieldActivity.SEARCHLIST, searchNameList);
                applicationFormFragment.startActivityForResult(intent, REQUEST_FOR_ACTIVITY_CODE);
                break;

            case R.id.et_pd_language_spoken:
                Transaction.getmTransactionInstance().setTransaction_type(Transaction.LANGUAGE);
                searchNameList = new ArrayList<>();
                for (int i = 0; i < languageModelArrayList.size(); i++) {
                    searchNameList.add(languageModelArrayList.get(i).getLanguageName());
                }
                intent = new Intent(getContext(), SearchFieldActivity.class);
                intent.putExtra(SearchFieldActivity.TITLE, getContext().getString(R.string.select_language));
                intent.putStringArrayListExtra(SearchFieldActivity.SEARCHLIST, searchNameList);
                applicationFormFragment.startActivityForResult(intent, REQUEST_FOR_ACTIVITY_CODE);
                break;

            case R.id.et_pd_profession:
                Transaction.getmTransactionInstance().setTransaction_type(Transaction.PROFESSION);
                searchNameList = new ArrayList<>();
                for (int i = 0; i < professionModelArrayList.size(); i++) {
                    searchNameList.add(professionModelArrayList.get(i).getProfessionName());
                }
                intent = new Intent(getContext(), SearchFieldActivity.class);
                intent.putExtra(SearchFieldActivity.TITLE, getContext().getString(R.string.select_profession));
                intent.putStringArrayListExtra(SearchFieldActivity.SEARCHLIST, searchNameList);
                applicationFormFragment.startActivityForResult(intent, REQUEST_FOR_ACTIVITY_CODE);
                break;

            case R.id.et_pd_nationality:
                popupDialog(getContext().getString(R.string.select_nationality), list_nationality, view.getId());
                break;

            case R.id.et_pd_gender:
                popupDialog(getContext().getString(R.string.select_gender), list_gender, view.getId());
                break;

            case R.id.et_pd_dob:
            case R.id.et_ppd_dt_issue:
            case R.id.et_ppd_dt_expiry:
                DatePickerDialogFragment dialog = new DatePickerDialogFragment();
                Bundle args = new Bundle();
                args.putInt("id", view.getId());
                dialog.setArguments(args);
                dialog.show(applicationFormFragment.getActivity().getSupportFragmentManager(), DIALOG_DATE);
                break;

            case R.id.et_cd_country:
                Transaction.getmTransactionInstance().setTransaction_type(Transaction.COUNTRY);
                searchNameList = new ArrayList<>();
                for (int i = 0; i < countryModelArrayList.size(); i++) {
                    searchNameList.add(countryModelArrayList.get(i).getCountryName());
                }
                intent = new Intent(getContext(), SearchFieldActivity.class);
                intent.putExtra(SearchFieldActivity.TITLE, getContext().getString(R.string.select_country));
                intent.putStringArrayListExtra(SearchFieldActivity.SEARCHLIST, searchNameList);
                applicationFormFragment.startActivityForResult(intent, REQUEST_FOR_ACTIVITY_CODE);
                break;

            case R.id.et_ppd_passport_type:
                Transaction.getmTransactionInstance().setTransaction_type(Transaction.PASSPORTTYPE);
                searchNameList = new ArrayList<>();
                for (int i = 0; i < passportTypeModelArrayList.size(); i++) {
                    searchNameList.add(passportTypeModelArrayList.get(i).getPassportTypeName());
                }
                intent = new Intent(getContext(), SearchFieldActivity.class);
                intent.putExtra(SearchFieldActivity.TITLE, getContext().getString(R.string.select_passport_type));
                intent.putStringArrayListExtra(SearchFieldActivity.SEARCHLIST, searchNameList);
                applicationFormFragment.startActivityForResult(intent, REQUEST_FOR_ACTIVITY_CODE);
                break;

            case R.id.btn_submit_application_form:
                showProgressDialog(getString(R.string.please_wait));
                AddApplicantTask addApplicantTask = new AddApplicantTask(getContext(), applicantModel.getApplicantId(), strGivenName, strSurname, strDob, strGender, strMaritalStatus, strNationality, strPob, strCob, strReligion, strTelNo, strLanguage, strProfession, strMName, strFName, strHName, strCity, strAddress1, strAddress2, strAddress3, strCountry, strPPNo, strPPType, strPPDoi, strPPDoe, strPPIssueGovt, strPIssue);
                addApplicantTask.addApplicant(addApplicantResposeCallback);
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (requestCode == REQUEST_FOR_ACTIVITY_CODE) {
            if (Transaction.getmTransactionInstance().getTransaction_type() == 1) {
                inputMethodManager.hideSoftInputFromWindow(et_country.getWindowToken(), 0);
                strCountry = Transaction.getmTransactionInstance().getCountry();
                et_country.setText(strCountry);
            } else if (Transaction.getmTransactionInstance().getTransaction_type() == 2) {
                inputMethodManager.hideSoftInputFromWindow(et_language.getWindowToken(), 0);
                strLanguage = Transaction.getmTransactionInstance().getLanguage();
                et_language.setText(strLanguage);
            } else if (Transaction.getmTransactionInstance().getTransaction_type() == 3) {
                inputMethodManager.hideSoftInputFromWindow(et_marital_status.getWindowToken(), 0);
                strMaritalStatus = Transaction.getmTransactionInstance().getMaritalStatus();
                et_marital_status.setText(strMaritalStatus);
            } else if (Transaction.getmTransactionInstance().getTransaction_type() == 4) {
                inputMethodManager.hideSoftInputFromWindow(et_pp_type.getWindowToken(), 0);
                strPPType = Transaction.getmTransactionInstance().getPassportType();
                et_pp_type.setText(strPPType);
            } else if (Transaction.getmTransactionInstance().getTransaction_type() == 5) {
                inputMethodManager.hideSoftInputFromWindow(et_profession.getWindowToken(), 0);
                strProfession = Transaction.getmTransactionInstance().getProfession();
                et_profession.setText(strProfession);
            } else if (Transaction.getmTransactionInstance().getTransaction_type() == 6) {
                inputMethodManager.hideSoftInputFromWindow(et_religion.getWindowToken(), 0);
                strReligion = Transaction.getmTransactionInstance().getReligion();
                et_religion.setText(strReligion);
            } else if (Transaction.getmTransactionInstance().getTransaction_type() == 7) {
                inputMethodManager.hideSoftInputFromWindow(et_cob.getWindowToken(), 0);
                strCob = Transaction.getmTransactionInstance().getCountry_birth();
                et_cob.setText(strCob);
            }
        }
        checkRequiredFields();
    }

    void popupDialog(String title, final CharSequence[] list, final int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
        checkRequiredFields();
    }

    @Override
    public void onFinishDialog(Date date, int id) {

        if (id == R.id.et_pd_dob) {
            strDob = ApiUtils.formatDate(date);
            et_dob.setText(strDob);
        } else if (id == R.id.et_ppd_dt_issue) {
            strPPDoi = ApiUtils.formatDate(date);
            et_pp_doi.setText(strPPDoi);
        } else if (id == R.id.et_ppd_dt_expiry) {
            strPPDoe = ApiUtils.formatDate(date);
            et_pp_doe.setText(strPPDoe);
        }
        checkRequiredFields();
    }

    void checkRequiredFields() {
        if (!et_surname.getText().toString().isEmpty() && !et_given_name.getText().toString().isEmpty() && !et_nationality.getText().toString().isEmpty()
                && !et_gender.getText().toString().isEmpty() && !et_dob.getText().toString().isEmpty() && !et_pob.getText().toString().isEmpty()
                && !et_cob.getText().toString().isEmpty() && !et_marital_status.getText().toString().isEmpty() && !et_religion.getText().toString().isEmpty()
                && !et_language.getText().toString().isEmpty() && !et_profession.getText().toString().isEmpty() && !et_father_name.getText().toString().isEmpty()
                && !et_mother_name.getText().toString().isEmpty() && !et_husband_name.getText().toString().isEmpty()
                && !et_pp_no.getText().toString().isEmpty() && !et_pp_type.getText().toString().isEmpty() && !et_pp_issue_govt.getText().toString().isEmpty()
                && !et_pp_place_issue.getText().toString().isEmpty() && !et_pp_doi.getText().toString().isEmpty() && !et_pp_doe.getText().toString().isEmpty()
                && !et_address_line1.getText().toString().isEmpty() && !et_city.getText().toString().isEmpty() && !et_country.getText().toString().isEmpty()
                && !et_telephone_no.getText().toString().isEmpty()) {
            btn_submit.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            btn_submit.setEnabled(true);
        } else {
            btn_submit.setBackgroundColor(getResources().getColor(R.color.colorGray));
            btn_submit.setEnabled(false);
        }
    }

    public void putData(Bundle args) {
        passportFrontModel = (PassportFrontModel) args.getSerializable("pf");
        passportBackModel = (PassportBackModel) args.getSerializable("pb");
        setCustomerData(passportFrontModel, passportBackModel);
    }

    public void putData(List<ReceiveDocumentModel> receiveDocumentModelArrayList) {
        showProgressDialog(getString(R.string.please_wait));
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
        dismissProgressDialog();
        setCustomerData(passportFrontModel, passportBackModel);
    }

    public void setCustomerData(PassportFrontModel pfModel, PassportBackModel pbModel) {
        passportFrontModel = pfModel;
        passportBackModel = pbModel;
        setPersonalData();
        setPassportData();
        setContactData();
    }

    public void setCustomerData(ApplicantModel appModel) {
        applicantModel = appModel;
        setPersonalData();
        setPassportData();
        setContactData();
    }

    public void setPersonalData() {
        if (passportFrontModel != null && passportBackModel != null) {
            strSurname = passportFrontModel.getSurname();
            strGivenName = passportFrontModel.getName();
            strNationality = passportFrontModel.getNationality();
            strGender = passportFrontModel.getGender();
            strDob = passportFrontModel.getDob();
            strFName = passportBackModel.getfName();
            strMName = passportBackModel.getmName();
            strHName = passportBackModel.gethName();
        } else if (applicantModel != null) {
            strSurname = applicantModel.getApplicantSurname();
            strGivenName = applicantModel.getApplicantGivenName();
            strNationality = applicantModel.getApplicantNationality();
            strGender = applicantModel.getApplicantGender();
            strDob = applicantModel.getApplicantDOB();
            strPob = applicantModel.getApplicantPOB();
            strCob = applicantModel.getApplicantCOB();
            strMaritalStatus = applicantModel.getApplicantMaritalStatus();
            strReligion = applicantModel.getApplicantReligion();
            strLanguage = applicantModel.getApplicantLangSpoken();
            strProfession = applicantModel.getApplicantProfession();
            strFName = applicantModel.getApplicantFName();
            strMName = applicantModel.getApplicantMName();
            strHName = applicantModel.getApplicantHName();
        }
        et_surname.setText(strSurname);
        et_given_name.setText(strGivenName);
        et_nationality.setText(strNationality);
        et_gender.setText(strGender);
        et_dob.setText(strDob);
        et_pob.setText(strPob);
        et_cob.setText(strCob);
        et_marital_status.setText(strMaritalStatus);
        et_religion.setText(strReligion);
        et_language.setText(strLanguage);
        et_profession.setText(strProfession);
        et_father_name.setText(strFName);
        et_mother_name.setText(strMName);
        et_husband_name.setText(strHName);
    }

    public void setPassportData() {
        if (passportFrontModel != null && passportBackModel != null) {
            strPPNo = passportFrontModel.getPassportNo();
            strPPIssueGovt = passportFrontModel.getIssueCountry();
            strPPDoi = passportFrontModel.getDoi();
            strPPDoe = passportFrontModel.getDoe();

        } else if (applicantModel != null) {

            strPPNo = applicantModel.getApplicantPPNo();
            strPPType = applicantModel.getApplicantPPType();
            strPPIssueGovt = applicantModel.getApplicantPPIssueGovt();
            strPIssue = applicantModel.getApplicantPPIssuePlace();
            strPPDoi = applicantModel.getApplicantPPDOI();
            strPPDoe = applicantModel.getApplicantPPDOE();
        }
        et_pp_no.setText(strPPNo);
        et_pp_type.setText(strPPType);
        et_pp_issue_govt.setText(strPPIssueGovt);
        et_pp_place_issue.setText(strPIssue);
        et_pp_doi.setText(strPPDoi);
        et_pp_doe.setText(strPPDoe);
    }

    public void setContactData() {
        if (passportFrontModel != null && passportBackModel != null) {
            strAddress1 = passportBackModel.getAddLine1();
            strAddress2 = passportBackModel.getAddLine2();
        } else if (applicantModel != null) {
            strAddress1 = applicantModel.getApplicantAddressLine1();
            strAddress2 = applicantModel.getApplicantAddressLine2();
            strAddress3 = applicantModel.getApplicantAddressLine3();
            strCity = applicantModel.getApplicantCity();
            strCountry = applicantModel.getApplicantCountry();
            strTelNo = applicantModel.getApplicantTelephone();
        }
        et_address_line1.setText(strAddress1);
        et_address_line2.setText(strAddress2);
        et_address_line3.setText(strAddress3);
        et_city.setText(strCity);
        et_country.setText(strCountry);
        et_telephone_no.setText(strTelNo);
    }

    AddApplicantTask.AddApplicantResposeCallback addApplicantResposeCallback = new AddApplicantTask.AddApplicantResposeCallback() {
        @Override
        public void onSuccessAddApplicantResponse(String response) {
            dismissProgressDialog();
            getActivity().finish();
        }

        @Override
        public void onFailureAddApplicantResponse(String response) {
            dismissProgressDialog();
            try {
                org.json.JSONObject jsonObject = new org.json.JSONObject(response);

                org.json.JSONObject contentObject = (org.json.JSONObject) jsonObject.get(ApiUtils.CONTENT);

                String message = (String) contentObject.get(ApiUtils.MESSAGE);

                showToast(message);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

}

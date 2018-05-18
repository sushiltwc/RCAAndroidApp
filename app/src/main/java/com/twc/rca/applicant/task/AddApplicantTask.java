package com.twc.rca.applicant.task;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.twc.rca.database.CountryHelper;
import com.twc.rca.database.LanguageHelper;
import com.twc.rca.database.MaritalHelper;
import com.twc.rca.database.PassportTypeHelper;
import com.twc.rca.database.ProfessionHelper;
import com.twc.rca.database.ReligionHelper;
import com.twc.rca.utils.ApiUtils;
import com.twc.rca.utils.ILog;
import com.twc.rca.utils.PreferenceUtils;
import com.twc.rca.volley.utils.NetworkErrorHelper;
import com.twc.rca.volley.utils.VolleySingleTon;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Sushil on 15-05-2018.
 */

public class AddApplicantTask extends ApiUtils {

    protected static final String TAG = AddApplicantTask.class.getSimpleName();

    Context context;

    public static String METHOD_NAME = "add_applicant";

    public static String USER_ID = "user_id", USERNAME = "username", SURNAME = "surname", SALUTATION = "salutation", DOB = "dob", GENDER = "gender", MARITAL_STATUS = "marital_status_id", NATIONALITY = "nationality", PLACE_OF_BIRTH = "place_of_birth",
            COUNTRY_OF_BIRTH = "country_of_birth", RELIGION = "religion", MOBILE_NUMBER = "mobile_number", LANGUAGE = "language", PROFESSION = "profession", MOTHER_NAME = "mother_name", FATHER_NAME = "father_name", HUSBAND_NAME = "husband_name", CITY = "city",
            ADDRESS1 = "address1", ADDRESS2 = "address2", ADDRESS3 = "address3", COUNTRY = "country", FLIGHT_NO = "flight_no", ORIGIN_CITY = "origin_city", IS_CHILD = "is_child", PP_TYPE = "pp_type", PP_NO = "pp_no", PP_ISSUE_DATE = "pp_issue_date", PP_EXPIRY_DATE = "pp_expiry_date",
            PP_PLACE_OF_ISSUE = "pp_place_of_issue", PP_ISSUE_GOVT = "pp_issuing_govt", PP_ISACTIVE = "pp_isactive";

    String applicantId, userName, surName, dob, gender, marital_status, nationality, pob, cob, religion, mobNo, language, profession, mother_name, father_name, husband_name, city, address1, address2, address3, country, pp_no, pp_type, pp_issue_date, pp_expiry_date, pp_issuing_govt, pp_place_of_issue;

    public AddApplicantTask(Context context, String applicantId, String userName, String surName, String dob, String gender, String marital_status, String nationality, String pob, String cob, String religion, String mobNo, String language, String profession,
                            String mother_name, String father_name, String husband_name, String city, String address1, String address2, String address3, String country, String pp_no, String pp_type, String pp_issue_date, String pp_expiry_date, String pp_issuing_govt, String pp_place_of_issue) {
        this.context = context;
        this.applicantId = applicantId;
        this.userName = userName;
        this.surName = surName;
        this.dob = dob;
        this.gender = gender;
        this.marital_status = marital_status;
        this.nationality = nationality;
        this.pob = pob;
        this.cob = cob;
        this.religion = religion;
        this.mobNo = mobNo;
        this.language = language;
        this.profession = profession;
        this.mother_name = mother_name;
        this.father_name = father_name;
        this.husband_name = husband_name;
        this.city = city;
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
        this.country = country;
        this.pp_no = pp_no;
        this.pp_type = pp_type;
        this.pp_issue_date = pp_issue_date;
        this.pp_expiry_date = pp_expiry_date;
        this.pp_issuing_govt = pp_issuing_govt;
        this.pp_place_of_issue = pp_place_of_issue;
    }

    public AddApplicantTask(Context context) {
        this.context = context;
    }

    public void addApplicant(final AddApplicantTask.AddApplicantResposeCallback addApplicantResposeCallback) {
        JsonObjectRequest applicantRequest = new JsonObjectRequest(Request.Method.POST, BASE_APP_URL, new JSONObject(getParams(context)), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                ILog.d(TAG, response.toString());
                if (isValidResponse(response.toString()))
                    addApplicantResposeCallback.onSuccessAddApplicantResponse(response.toString());
                else
                    addApplicantResposeCallback.onFailureAddApplicantResponse(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ILog.d(TAG, error.toString());
                addApplicantResposeCallback.onFailureAddApplicantResponse(NetworkErrorHelper.getErrorStatus(error).getErrorMessage());
            }
        });
        VolleySingleTon.getInstance(context).addToRequestQueue(applicantRequest);
    }

    protected Map<String, Object> getParams(Context context) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(ApiUtils.ACCESS_TOKEN, PreferenceUtils.getAccessToken(context));
        map.put("applicant_id", applicantId);
        map.put(USER_ID, PreferenceUtils.getUserid(context));
        map.put(USERNAME, userName);
        map.put(SURNAME, surName);
        map.put(DOB, dob);
        map.put(GENDER, gender);
        map.put(MARITAL_STATUS, MaritalHelper.getInstance(context).getMaritalId(marital_status));
        map.put(NATIONALITY, CountryHelper.getInstance(context).getCountryId(nationality));
        map.put(PLACE_OF_BIRTH, pob);
        map.put(RELIGION, ReligionHelper.getInstance(context).getReligionId(religion));
        map.put(MOBILE_NUMBER, mobNo);
        map.put(LANGUAGE, LanguageHelper.getInstance(context).getLanguageId(language));
        map.put(PROFESSION, ProfessionHelper.getInstance(context).getProfessionId(profession));
        map.put(MOTHER_NAME, mother_name);
        map.put(FATHER_NAME, father_name);
        map.put(HUSBAND_NAME, husband_name);
        map.put(CITY, city);
        map.put(ADDRESS1, address1);
        map.put(ADDRESS2, address2);
        map.put(ADDRESS3, address3);
        map.put(COUNTRY, CountryHelper.getInstance(context).getCountryId(country));
        map.put(COUNTRY_OF_BIRTH, CountryHelper.getInstance(context).getCountryId(cob));
        map.put(IS_CHILD, getAge(dob) > 18 ? "Y" : "N");
        map.put(PP_NO, pp_no);
        map.put(PP_TYPE, PassportTypeHelper.getInstance(context).getPassportTypeId(pp_type));
        map.put(PP_ISSUE_DATE, pp_issue_date);
        map.put(PP_EXPIRY_DATE, pp_expiry_date);
        map.put(PP_ISSUE_GOVT, CountryHelper.getInstance(context).getCountryId(pp_issuing_govt));
        map.put(PP_PLACE_OF_ISSUE, pp_place_of_issue);
        String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        map.put(PP_ISACTIVE, compareDates(currentDate, pp_expiry_date) ? "N" : "Y");
        map.put(ApiUtils.METHOD, METHOD_NAME);
        return map;
    }

    public interface AddApplicantResposeCallback {
        void onSuccessAddApplicantResponse(String response);

        void onFailureAddApplicantResponse(String response);
    }
}


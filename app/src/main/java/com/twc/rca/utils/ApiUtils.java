package com.twc.rca.utils;

import android.util.Log;
import android.util.Patterns;

import com.twc.rca.BuildConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sushil on 22-02-2018.
 */

public class ApiUtils {

    protected static final String TAG = ApiUtils.class.getSimpleName();

    public static final String STATUS = "status", SUCCESS = "success", MESSAGE = "message", CONTENT = "content", DATA = "data", RESULT_SET = "result_set";

    public static final String CONTENT_TYPE = "Content-Type";

    public static final String ACCESS_TOKEN = "access_token";

    public static final String METHOD = "method";

    protected static final String BASE_APP_DEBUG_URL = "http://dev.redcarpetassist.com/rca_website/api/api.php";

    protected static final String BASE_APP_RELEASE_URL = "http://35.154.41.252:4004/votw/";

    protected static final String BASE_APP_URL = BuildConfig.DEBUG ? BASE_APP_DEBUG_URL : BASE_APP_RELEASE_URL;

    public static boolean isValidMail(String mailAddress) {
        return Patterns.EMAIL_ADDRESS.matcher(mailAddress).matches();
    }

    public static boolean isValidMobileNumber(String number) {
        return isValidMobileNumber(number, false);
    }

    public static boolean isValidMobileNumber(String number, boolean incomplete) {
        if (number.length() > 10 || number.length() <= 0)
            return false;
        if (!incomplete && number.length() < 10)
            return false;
        return number.startsWith("7") || number.startsWith("8") || number.startsWith("9");
    }

    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }

    public static boolean isValidStringValue(String value) {
        if (value == null || value.equals("") || value.equalsIgnoreCase("null"))
            return true;
        else
            return false;
    }

    // validate first name
    public static boolean isValidateGivenName( String firstName )
    {
        return firstName.matches("^\\p{L}+[\\p{L}\\p{Z}\\p{P}]{0,}");//( "[A-Z][a-zA-Z]*" );
    }

    // validate last name
    public static boolean isValidateSurName( String lastName )
    {
        return lastName.matches( "[a-zA-z]+([ '-][a-zA-Z]+)*" );
    }

    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String hireDate = sdf.format(date);
        return hireDate;
    }

    public static boolean isValidResponse(String response) {
        Log.d(TAG, "Response " + response);

        try {
            JSONObject jsonObject = new JSONObject(response);
            return jsonObject.getString(STATUS).equalsIgnoreCase(SUCCESS);
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }
}

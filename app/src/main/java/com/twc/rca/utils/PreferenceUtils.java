package com.twc.rca.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Sushil on 06-06-2017.
 * This will be shared preference class.
 */

public class PreferenceUtils {

    private static final String IS_APP_FIRST_LAUNCH_DATE = "firstDayLaunch";
    private static final String APP_FIRST_LAUNCH_DATE = "cardFirstLaunchDate";
    private static final String IS_REGISTERED = "isRegistered";
    private static final String IS_PAYMENT_DONE="isPaymentDone";

    public static final String NA = "NA";

    public static final String USERID = "user_id", EMAILID = "email_id", MOBILE_NO = "mobile_no", DEVICE_ID = "device_id";

    public static final String USEREXIST_MAILID="userExistMailIds",OTP_NO="otp_number";

    // Preferences for DB_VERSION - Club with DB name
    public static final String DB_VERSION = "db_version";


    public static void setIsRegistered(Context context){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(IS_REGISTERED, "true").apply();
    }

    public static boolean isRegistered(Context context) {
        if (context == null) {
            return false;
        }
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(PreferenceUtils.IS_REGISTERED, "false").equalsIgnoreCase("true");
    }

    public static void setIsPaymentDone(Context context,boolean isPaymentDone){
        SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean(IS_PAYMENT_DONE,isPaymentDone).apply();
    }

    public static boolean isPaymentDone(Context context){
        if (context == null) {
            return false;
        }
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(PreferenceUtils.IS_PAYMENT_DONE,false);
    }
}

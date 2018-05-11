package com.twc.rca.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;

import com.twc.rca.BuildConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
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

    public static final String USER_ID = "userId", APPLICANT_ID = "applicantId", APPLICANT_TYPE = "applicant_type";

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
    public static boolean isValidateGivenName(String firstName) {
        return firstName.matches("^\\p{L}+[\\p{L}\\p{Z}\\p{P}]{0,}");//( "[A-Z][a-zA-Z]*" );
    }

    // validate last name
    public static boolean isValidateSurName(String lastName) {
        return lastName.matches("[a-zA-z]+([ '-][a-zA-Z]+)*");
    }

    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String hireDate = sdf.format(date);
        return hireDate;
    }

    public static String formatDate(String dt) {
        String outputDateStr = null;
        try {
            if (dt != null) {
                DateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
                DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = inputFormat.parse(dt);
                outputDateStr = outputFormat.format(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputDateStr;
    }

    public static String getDate(String dt) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd/MM/yy");
        Date myDate = null;
        try {
            myDate = dateFormat.parse(dt);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat timeFormat = new SimpleDateFormat("dd/MM/yyyy");
        String finalDate = timeFormat.format(myDate);
        return finalDate;
    }

    public static String getIssueDate(String indate, String custType) {
        String yesterdayAsString = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date date = dateFormat.parse(indate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, 1);
            if (custType.equalsIgnoreCase("C"))
                calendar.add(Calendar.YEAR, -5);
            else
                calendar.add(Calendar.YEAR, -10);
            yesterdayAsString = dateFormat.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return yesterdayAsString;
    }

    public static Date getDate(String dt, String tm) {
        dt = dt + "T" + tm + "Z";
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy'T'HH:mm:ss'Z'");
        try {
            date = format.parse(dt);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static int getAge(String dobString) {

        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = sdf.parse(dobString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date == null) return 0;

        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.setTime(date);

        int year = dob.get(Calendar.YEAR);
        int month = dob.get(Calendar.MONTH);
        int day = dob.get(Calendar.DAY_OF_MONTH);

        dob.set(year, month + 1, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        return age;
    }

    public static Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
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

    public static void timeDifference(Date startDate, Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : " + endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);
    }

    public static void getNextDay() {
        Calendar cal = new GregorianCalendar();
        // cal now contains current date
        System.out.println(cal.getTime());

        // add the working days
        int workingDaysToAdd = 5;
        for (int i = 0; i < workingDaysToAdd; i++)
            do {
                cal.add(Calendar.DAY_OF_MONTH, 1);
            } while (!isWorkingDay(cal));
        System.out.println(cal.getTime());
    }

    public static boolean isWorkingDay(Calendar cal) {
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY)
            return false;
        return true;
    }
}

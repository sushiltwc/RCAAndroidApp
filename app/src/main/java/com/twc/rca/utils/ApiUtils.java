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

    public static final String STATUS = "status", SUCCESS = "success", FAILURE = "failure", MESSAGE = "message", CONTENT = "content", DATA = "data", RESULT_SET = "result_set";

    public static final String CONTENT_TYPE = "Content-Type";

    public static final String ACCESS_TOKEN = "access_token";

    public static final String USER_ID = "user_id", APPLICANT_ID = "applicant_id", EMAIL_ID = "email_id", PASSWORD = "password", PHONE_NO = "phone_no", USER_NAME = "user_name", APPLICANT_TYPE = "applicant_type";

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

    public static String getFormattedString(String str) {
        String[] strArray = str.split(" ");
        StringBuilder builder = new StringBuilder();
        for (String s : strArray) {
            String cap = s.substring(0, 1).toUpperCase() + s.substring(1, s.length()).toLowerCase();
            builder.append(cap + " ");
        }
        return builder.toString();
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

    public static Date getDate(String dt, String inputFormat, String outputFormat) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(inputFormat);
        Date myDate = null;
        try {
            myDate = dateFormat.parse(dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat timeFormat = new SimpleDateFormat(outputFormat);
        String finalDate = timeFormat.format(myDate);
        try {
            myDate = timeFormat.parse(finalDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return myDate;
    }

    public static String getFormattedDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //2nd of march 2015
        int day = cal.get(Calendar.DATE);

        if (!((day > 10) && (day < 19)))
            switch (day % 10) {
                case 1:
                    return new SimpleDateFormat("d'st' MMM yyyy").format(date);
                case 2:
                    return new SimpleDateFormat("d'nd' MMM yyyy").format(date);
                case 3:
                    return new SimpleDateFormat("d'rd' MMM yyyy").format(date);
                default:
                    return new SimpleDateFormat("d'th' MMM yyyy").format(date);
            }
        return new SimpleDateFormat("d'th' MMM yyyy").format(date);
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
        dt = dt + " " + tm.replace(" ", "") + ":00";
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
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

    public static String getNoTravellers(int adultCount, int childCount, int infantCount) {
        String noOfPassengers, noOfAdults, noOfChildrens, noOfInfants;

        if (adultCount > 1)
            noOfAdults = adultCount + " Adults";
        else
            noOfAdults = adultCount + " Adult";

        if (childCount == 1)
            noOfChildrens = childCount + " Child";
        else if (childCount > 1)
            noOfChildrens = childCount + " Childs";
        else
            noOfChildrens = "";

        if (infantCount == 1)
            noOfInfants = infantCount + " Infant";
        else if (infantCount > 1)
            noOfInfants = infantCount + " Infants";
        else
            noOfInfants = "";

        noOfPassengers = noOfAdults;
        if (!ApiUtils.isValidStringValue(noOfChildrens))
            noOfPassengers = noOfPassengers + "," + noOfChildrens;
        if (!ApiUtils.isValidStringValue(noOfInfants))
            noOfPassengers = noOfPassengers + "," + noOfInfants;

        return noOfPassengers;
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

    public static long timeDifference(Date startDate, Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        long hours = convertMillis(different);

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

        return hours;
    }

    public static long convertMillis(long milliseconds) {
        long seconds, minutes = 0, hours;
        seconds = milliseconds / 1000;
        hours = seconds / 3600;
        seconds = seconds % 3600;
        seconds = seconds / 60;
        minutes = minutes % 60;
        return hours;
    }

    public static boolean isTravelDateValid(String travelDt, int visaValidity) {

        Date currentDate = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        String formattedDate = df.format(currentDate);

        try {
            currentDate = df.parse(formattedDate);

            Date travelDate = df.parse(travelDt);

            long difference = travelDate.getTime() - currentDate.getTime();

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            long elapsedDays = difference / daysInMilli;

            if (elapsedDays > visaValidity)
                return false;
            else
                return true;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean compareDates(String d1, String d2) {
        try {
            //1
            // Create 2 dates starts
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = sdf.parse(d1);
            Date date2 = sdf.parse(d2);

            System.out.println("Date1" + sdf.format(date1));
            System.out.println("Date2" + sdf.format(date2));
            System.out.println();

            // Date object is having 3 methods namely after,before and equals for comparing
            // after() will return true if and only if date1 is after date 2
            if (date1.after(date2)) {
                System.out.println("Date1 is after Date2");
                return false;
            }
            // before() will return true if and only if date1 is before date2
            if (date1.before(date2)) {
                System.out.println("Date1 is before Date2");
                return true;
            }
            if (date1.equals(date2)) {
                return true;
            }
            System.out.println();
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static boolean isDateEquals(String d1, String d2) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = sdf.parse(d1);
            Date date2 = sdf.parse(d2);
            if (date1.equals(date2)) {
                return true;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return false;
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

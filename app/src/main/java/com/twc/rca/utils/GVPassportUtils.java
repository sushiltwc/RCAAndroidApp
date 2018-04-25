package com.twc.rca.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.SparseArray;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.twc.rca.R;
import com.twc.rca.applicant.model.PassportBackModel;
import com.twc.rca.applicant.model.PassportFrontModel;
import com.twc.rca.database.CountryHelper;
import com.twc.rca.mrz.MrzDate;
import com.twc.rca.mrz.MrzParser;
import com.twc.rca.mrz.MrzRange;
import com.twc.rca.mrz.MrzSex;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sushil on 10-04-2018.
 */

public class GVPassportUtils {

    String TAG = this.getClass().getName();
    Context context;
    ArrayList<String> ocr;
    ArrayList<String> names;
    ArrayList<String> address;
    PassportFrontModel passportFrontModel;
    PassportBackModel passportBackModel;
    static String str_country, str_name, str_surname, str_passportNo, str_nationality, str_dob, str_doi, str_doe, str_sex;
    String str_FName, str_MName, str_HName, str_Address_line1, str_Address_line2;

    public GVPassportUtils(Context context) {
        this.context = context;
        passportFrontModel = new PassportFrontModel();
        passportBackModel = new PassportBackModel();
    }

    public PassportFrontModel processPassportFront(Bitmap bitmap) {
        getCustomerName(bitmap);

        bitmap = PassportUtils.cutTop(bitmap);
        bitmap = PassportUtils.cutTop(bitmap);

        ocr = new ArrayList<>();
        TextRecognizer txtRecognizer = new TextRecognizer.Builder(context).build();
        if (!txtRecognizer.isOperational()) {
            ILog.d(TAG, String.valueOf(R.string.passport_scan_error));
        } else {
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray items = txtRecognizer.detect(frame);
            StringBuilder strBuilder = new StringBuilder();
            for (int i = 0; i < items.size(); i++) {
                TextBlock item = (TextBlock) items.valueAt(i);
                strBuilder.append(item.getValue());
                strBuilder.append("/");
                for (Text line : item.getComponents()) {
                    Log.v("lines", line.getValue());
                    ocr.add(line.getValue());
                }
            }

            String str1 = ocr.get(0).replace(" ", "").replace("-", "").replace("(", "").replace(")", "").replace("¢", "");
            String str2 = ocr.get(1).replace(" ", "").replace("-", "").replace("(", "").replace(")", "").replace(".", "");

            if (str1.length() != 44) {
                int strLength = 44 - str1.length();
                for (int i = 0; i < strLength; i++)
                    str1 += "<";
            }
            if (str2.length() != 44) {
                int strLength = 44 - str2.length();
                for (int i = 0; i < strLength; i++)
                    str2 += "<";
            }
            Log.d("Str1 : ", str1);
            Log.d("Str2 : ", str2);

            getPersonalDetails(str1);
            getPPDetails(str2);

            str_dob = ApiUtils.getDate(str_dob);
            str_doe = ApiUtils.getDate(str_doe);

            if (ApiUtils.getAge(str_dob) < 18)
                str_doi = ApiUtils.getIssueDate(str_doe, "C");
            else
                str_doi = ApiUtils.getIssueDate(str_doe, "A");

            passportFrontModel.setName(str_name);
            passportFrontModel.setSurname(str_surname);
            passportFrontModel.setNationality(str_nationality);
            passportFrontModel.setPassportNo(str_passportNo);
            passportFrontModel.setIssueCountry(str_country);
            passportFrontModel.setDob(str_dob);
            passportFrontModel.setGender(str_sex);
            passportFrontModel.setDoe(str_doe);
            passportFrontModel.setDoi(str_doi);

          /*  if (ApplicantHelper.getInstance(context).isApplicantExist(context, "29"))
                ApplicantHelper.insertOrUpdatePF(context, passportFrontModel, "29", true);
            else
                ApplicantHelper.insertOrUpdatePF(context, passportFrontModel, "29", false);*/
        }
        return passportFrontModel;
    }

    public PassportBackModel processPassportBack(Bitmap bitmap) {
        getFamilyNames(bitmap);
        getAddress(bitmap);
        passportBackModel.setfName(str_FName);
        passportBackModel.setmName(str_MName);
        passportBackModel.sethName(str_HName);
        passportBackModel.setAddLine1(str_Address_line1);
        passportBackModel.setAddLine2(str_Address_line2);
       /* if (ApplicantHelper.getInstance(context).isApplicantExist(context, "29"))
            ApplicantHelper.insertOrUpdatePB(context, passportBackModel, "29", true);
        else
            ApplicantHelper.insertOrUpdatePB(context, passportBackModel, "29", false);*/
        return passportBackModel;
    }

    void getCustomerName(Bitmap bitmap) {
        bitmap = PassportUtils.getCustomerNameBitmap(bitmap);
        ocr = new ArrayList<>();
        names = new ArrayList<>();
        TextRecognizer txtRecognizer = new TextRecognizer.Builder(context).build();
        if (!txtRecognizer.isOperational()) {
            ILog.d(TAG, String.valueOf(R.string.passport_scan_error));
        } else {
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray items = txtRecognizer.detect(frame);
            StringBuilder strBuilder = new StringBuilder();
            for (int i = 0; i < items.size(); i++) {
                TextBlock item = (TextBlock) items.valueAt(i);
                strBuilder.append(item.getValue());
                strBuilder.append("/");
                for (Text line : item.getComponents()) {
                    //extract scanned text lines here
                    Log.v("lines", line.getValue());
                    ocr.add(line.getValue());
                }
            }
            for (int i = 0; i < ocr.size(); i++) {
                if (isAlpha(ocr.get(i))) {
                    names.add(ocr.get(i));
                }
            }
            if (names.size() > 0) {
                if (names.size() == 2) {
                    str_surname = names.get(0);
                    str_name = names.get(1);
                } else
                    str_name = names.get(0);
            }
        }
    }

    void getPersonalDetails(String str) {
        String nationality = null, surname = null, name = null;
        String[] userName;
        //Checking first character is P or not at position 0
        if (str.charAt(0) == 'P') {
            //Checking second
            if (str.charAt(1) == '<' || str.charAt(1) == 'K') {
                nationality = new MrzParser(str).parseString(new MrzRange(2, 5, 0)).replace('1', 'I').replace('0', 'D');
                str = str.replace('5', 'S').replace('1', 'I');
                str = str.replaceAll("[^A-Za-z<]+", "");
                userName = new MrzParser(str).parseName(new MrzRange(5, str.length(), 0));
                surname = userName[0].toUpperCase().replaceAll("[^A-Za-z]+", "");
                name = userName[1].toUpperCase().replaceAll("[^A-Za-z]+", "");
            } else {
                nationality = new MrzParser(str).parseString(new MrzRange(1, 4, 0)).replace('1', 'I').replace('0', 'D');
                String str1 = str.replaceAll("[^A-Za-z<]+", "");
                userName = new MrzParser(str1).parseName(new MrzRange(4, str1.length(), 0));
                surname = userName[0];
                name = userName[1];
            }

            if (ApiUtils.isValidStringValue(str_name))
                str_name = name.replace("KK", " ");

            if (ApiUtils.isValidStringValue(str_surname))
                str_surname = surname.replace("KK", " ");

            str_country = CountryHelper.getInstance(context).getCountryName(nationality);
            Log.d("Issuing Country :", nationality);
            Log.d("Surname :", str_surname);
            Log.d("Name :", str_name);
        }
    }

    void getPPDetails(String str) {
        MrzDate dob = null, doe = null;
        MrzSex sex = null;
        String nationality;
        String[] pp = new String[2];

        if (str.charAt(8) == '<' && Character.isDigit(str.charAt(9))) {
            //pp = str.split("<");
            if (str.charAt(10) == '1') {
                StringBuilder stringBuilder = new StringBuilder(str);
                stringBuilder.setCharAt(10, 'I');
                str = stringBuilder.toString();
            }
            pp[0] = str.substring(0, 8);
            pp[1] = str.substring(10);
        } else if (str.charAt(8) == 'K' && Character.isDigit(str.charAt(9))) {
            //pp=str.split("K");
            if (str.charAt(10) == '1') {
                StringBuilder stringBuilder = new StringBuilder(str);
                stringBuilder.setCharAt(10, 'I');
                str = stringBuilder.toString();
            }
            pp[0] = str.substring(0, 8);
            pp[1] = str.substring(10);
        } else if (Character.isDigit(str.charAt(8)) && Character.isDigit(str.charAt(9)) && str.charAt(9) == '1' && str.charAt(10) == 'I') {
            pp[0] = str.substring(0, 8);
            pp[1] = str.substring(10);
        } else if (Character.isDigit(str.charAt(8)) && Character.isDigit(str.charAt(9)) && str.charAt(9) == '1') {
            if (str.charAt(9) == '1') {
                StringBuilder stringBuilder = new StringBuilder(str);
                stringBuilder.setCharAt(9, 'I');
                str = stringBuilder.toString();
            }
            pp[0] = str.substring(0, 8);
            pp[1] = str.substring(9);
        } else if (Character.isDigit(str.charAt(8)) && Character.isDigit(str.charAt(9))) {
            if (str.charAt(10) == '1') {
                StringBuilder stringBuilder = new StringBuilder(str);
                stringBuilder.setCharAt(10, 'I');
                str = stringBuilder.toString();
            }
            pp[0] = str.substring(0, 8);
            pp[1] = str.substring(10);
        } else if (Character.isDigit(str.charAt(8))) {
            if (str.charAt(9) == '1') {
                StringBuilder stringBuilder = new StringBuilder(str);
                stringBuilder.setCharAt(9, 'I');
                str = stringBuilder.toString();
            }
            pp[0] = str.substring(0, 8);
            pp[1] = str.substring(9);
        } else {
            pp[0] = str.substring(0, 8);
            pp[1] = str.substring(8);
        }

        nationality = new MrzParser(pp[1]).parseString(new MrzRange(0, 3, 0)).replace('1', 'I').replace('0', 'D');
        dob = new MrzParser(pp[1]).parseDate(new MrzRange(3, 9, 0));
        try {
            str_dob = (dob.toMrz1().toString()).replace("B", "8").replace("o", "0").replace("O", "0");
            SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
            Date d1 = sdf.parse(str_dob);
            sdf.applyPattern("dd/MM/yy");
            str_dob = sdf.format(d1);
            Log.d("Date of Birth", sdf.format(d1));
        } catch (Exception e) {

        }

        if (Character.isLetter(pp[1].charAt(10)) && (pp[1].charAt(10) == 'M' || pp[1].charAt(10) == 'F'))
            sex = new MrzParser(pp[1]).parseSex(10, 0);
        doe = new MrzParser(pp[1]).parseDate(new MrzRange(11, 17, 0));

        str_passportNo = pp[0];
        str_nationality = CountryHelper.getInstance(context).getCountryName(nationality);
        str_doe = doe.toString().replace("{", "").replace("}", "");
        str_sex = String.valueOf(sex);
        Log.d("PassportNo :", pp[0]);
        Log.d("Nationality :", nationality);
        Log.d("DOB :", dob.toString());
        Log.d("DOE :", doe.toString());
        Log.d("Sex :", String.valueOf(sex));
    }

    void getFamilyNames(Bitmap bitmap) {
        bitmap = PassportUtils.getFamilyNameBitmap(bitmap);
        ocr = new ArrayList<>();
        names = new ArrayList<>();
        TextRecognizer txtRecognizer = new TextRecognizer.Builder(context).build();
        if (!txtRecognizer.isOperational()) {

        } else {
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray items = txtRecognizer.detect(frame);
            StringBuilder strBuilder = new StringBuilder();
            for (int i = 0; i < items.size(); i++) {
                TextBlock item = (TextBlock) items.valueAt(i);
                strBuilder.append(item.getValue());
                strBuilder.append("/");
                for (Text line : item.getComponents()) {
                    //extract scanned text lines here
                    Log.v("lines", line.getValue());
                    ocr.add(line.getValue());
                }
            }
            for (int i = 0; i < ocr.size(); i++) {
                if (isAlpha(ocr.get(i))) {
                    if (ocr.get(i).split(" ").length > 1)
                        names.add(ocr.get(i));
                }
            }

            if (names.size() == 2) {
                str_MName = names.get(0);
                str_FName = names.get(1);
            }
            if (names.size() == 3) {
                str_HName = names.get(0);
                str_FName = names.get(1);
                str_MName = names.get(2);
            }
        }
    }

    void getAddress(Bitmap bitmap) {
        bitmap = PassportUtils.getAddressBitmap(bitmap);
        bitmap = PassportUtils.cutBottom(bitmap);
        ocr = new ArrayList<>();
        address = new ArrayList<>();
        TextRecognizer txtRecognizer = new TextRecognizer.Builder(context).build();
        if (!txtRecognizer.isOperational()) {

        } else {
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray items = txtRecognizer.detect(frame);
            StringBuilder strBuilder = new StringBuilder();
            for (int i = 0; i < items.size(); i++) {
                TextBlock item = (TextBlock) items.valueAt(i);
                strBuilder.append(item.getValue());
                strBuilder.append("/");
                for (Text line : item.getComponents()) {
                    //extract scanned text lines here
                    Log.v("lines", line.getValue());
                    ocr.add(line.getValue());
                }
            }
            for (int i = 0; i < ocr.size(); i++) {
                if (!getPinFromAddress(ocr.get(i)))
                    address.add(ocr.get(i));

            }

            StringBuilder builder = new StringBuilder();
            for (String add : address) {
                builder.append(add + "\n");
            }
            str_Address_line1 = builder.toString();
        }
    }

    boolean getPinFromAddress(String str) {
        Pattern pattern = Pattern.compile("(\\d{6})");

        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            str_Address_line2 = str; // 6 digit number
            return true;
        }
        return false;
    }

    public boolean isAlpha(String name) {
        return name.matches("[ A-Z]+");
    }
}

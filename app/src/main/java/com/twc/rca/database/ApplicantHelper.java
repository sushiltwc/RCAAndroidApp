package com.twc.rca.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.twc.rca.applicant.model.PassportBackModel;
import com.twc.rca.applicant.model.PassportFrontModel;
import com.twc.rca.utils.ILog;

/**
 * Created by Sushil on 10-04-2018.
 */

public class ApplicantHelper {

    public static boolean isApplicantExist(Context context, String appId) {

        String selection = ApplicantProvider
                .ApplicantList.KEY_AID + " = " + appId;

        Cursor cursor = context.getContentResolver().query(ApplicantProvider.ApplicantList
                .CONTENT_URI, null, selection, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public static int insertOrUpdatePF(Context context, PassportFrontModel passportFrontModel, String applicantId) {
        int rowID = -1;
        String selection = ApplicantProvider.ApplicantList.KEY_AID + " =?";
        String[] selectionArgs = {applicantId};
        ContentValues values = new ContentValues();

        values.put(ApplicantProvider.ApplicantList.KEY_AID, applicantId);
        values.put(ApplicantProvider.ApplicantList.KEY_SNAME, passportFrontModel.getSurname());
        values.put(ApplicantProvider.ApplicantList.KEY_GNAME, passportFrontModel.getName());
        values.put(ApplicantProvider.ApplicantList.KEY_NATIONALITY, passportFrontModel.getNationality());
        values.put(ApplicantProvider.ApplicantList.KEY_GENDER, passportFrontModel.getGender());
        values.put(ApplicantProvider.ApplicantList.KEY_DOB, passportFrontModel.getDob());
        values.put(ApplicantProvider.ApplicantList.KEY_PP_ISSUE_GOVT, passportFrontModel.getIssueCountry());
        values.put(ApplicantProvider.ApplicantList.KEY_DOI, passportFrontModel.getDoi());
        values.put(ApplicantProvider.ApplicantList.KEY_DOE, passportFrontModel.getDoe());

        if (isApplicantExist(context, applicantId)) {
            context.getContentResolver().update(ApplicantProvider.ApplicantList.CONTENT_URI, values, selection, selectionArgs);
        } else {
            context.getContentResolver().insert(ApplicantProvider.ApplicantList.CONTENT_URI, values);
        }
        return rowID;
    }

    public static int insertOrUpdatePB(Context context, PassportBackModel passportBackModel, String applicantId) {
        int rowID = -1;
        String selection = ApplicantProvider.ApplicantList.KEY_AID + " =?";
        String[] selectionArgs = {applicantId};
        ContentValues values = new ContentValues();

        values.put(ApplicantProvider.ApplicantList.KEY_AID, applicantId);
        values.put(ApplicantProvider.ApplicantList.KEY_FNAME, passportBackModel.getfName());
        values.put(ApplicantProvider.ApplicantList.KEY_MNAME, passportBackModel.getmName());
        values.put(ApplicantProvider.ApplicantList.KEY_HNAME, passportBackModel.gethName());
        values.put(ApplicantProvider.ApplicantList.KEY_ADD1, passportBackModel.getAddLine1());
        values.put(ApplicantProvider.ApplicantList.KEY_ADD2, passportBackModel.getAddLine2());

        if (isApplicantExist(context, applicantId)) {
            context.getContentResolver().update(ApplicantProvider.ApplicantList.CONTENT_URI, values, selection, selectionArgs);
        } else {
            context.getContentResolver().insert(ApplicantProvider.ApplicantList.CONTENT_URI, values);
        }
        return rowID;
    }

    public static int createOrUpdateApplicant(Context context, PassportFrontModel passportFrontModel, boolean isUpdate) {
        int rowID = -1;
        ContentValues values = new ContentValues();
        // values.put(ApplicantProvider.ApplicantList.KEY_AID, passportFrontModel.get);
        //  values.put(ApplicantProvider.ApplicantList.KEY_APP_TYPE, passportFrontModel.getDataInDay());
        values.put(ApplicantProvider.ApplicantList.KEY_SNAME, passportFrontModel.getSurname());
        values.put(ApplicantProvider.ApplicantList.KEY_GNAME, passportFrontModel.getName());
        values.put(ApplicantProvider.ApplicantList.KEY_NATIONALITY, passportFrontModel.getNationality());
        values.put(ApplicantProvider.ApplicantList.KEY_GENDER, passportFrontModel.getGender());
        values.put(ApplicantProvider.ApplicantList.KEY_DOB, passportFrontModel.getDob());
     /*   values.put(ApplicantProvider.ApplicantList.KEY_POB, passportFrontModel.getP());
        values.put(ApplicantProvider.ApplicantList.KEY_COB, passportFrontModel.getRecordedDate());
        values.put(ApplicantProvider.ApplicantList.KEY_MARITAL, passportFrontModel.get());
        values.put(ApplicantProvider.ApplicantList.KEY_RELIGION, passportFrontModel.getRecordedDate());
        values.put(ApplicantProvider.ApplicantList.KEY_LANG, passportFrontModel.getRecordedDate());
        values.put(ApplicantProvider.ApplicantList.KEY_PROF, passportFrontModel.getRecordedDate());
        values.put(ApplicantProvider.ApplicantList.KEY_FNAME, passportFrontModel.get());
        values.put(ApplicantProvider.ApplicantList.KEY_MNAME, passportFrontModel.getRecordedDate());
        values.put(ApplicantProvider.ApplicantList.KEY_HNAME, passportFrontModel.getRecordedDate());
        values.put(ApplicantProvider.ApplicantList.KEY_PP_NO, passportFrontModel.getRecordedDate());
        values.put(ApplicantProvider.ApplicantList.KEY_PP_TYPE, passportFrontModel.getRecordedDate());*/
        values.put(ApplicantProvider.ApplicantList.KEY_PP_ISSUE_GOVT, passportFrontModel.getIssueCountry());
        //   values.put(ApplicantProvider.ApplicantList.KEY_POI, passportFrontModel.getRecordedDate());
        values.put(ApplicantProvider.ApplicantList.KEY_DOI, passportFrontModel.getDoi());
        values.put(ApplicantProvider.ApplicantList.KEY_DOE, passportFrontModel.getDoe());
    /*    values.put(ApplicantProvider.ApplicantList.KEY_ADD1, passportFrontModel.getRecordedDate());
        values.put(ApplicantProvider.ApplicantList.KEY_ADD2, passportFrontModel.getRecordedDate());
        values.put(ApplicantProvider.ApplicantList.KEY_ADD3, passportFrontModel.getRecordedDate());
        values.put(ApplicantProvider.ApplicantList.KEY_CITY, passportFrontModel.getRecordedDate());
        values.put(ApplicantProvider.ApplicantList.KEY_COUNTRY, passportFrontModel.getRecordedDate());
        values.put(ApplicantProvider.ApplicantList.KEY_MOB_NO, passportFrontModel.getRecordedDate());*/


        if (!isUpdate) {
            // rowID = context.getContentResolver().update(ApplicantProvider.ApplicantList.CONTENT_URI, values, ApplicantProvider.ApplicantList.KEY_AID + "= ?", new String[]{passportFrontModel.getAid()});
        } else {
            Uri uri = context.getContentResolver().insert(ApplicantProvider.ApplicantList.CONTENT_URI, values);
            if (uri != null) {
                rowID = 1;
                ILog.d("Applicant Created Successfully", String.valueOf(rowID));
            }
        }
        return rowID;
    }
}

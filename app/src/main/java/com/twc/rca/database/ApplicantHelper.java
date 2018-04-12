package com.twc.rca.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.twc.rca.applicant.model.PassportFrontModel;
import com.twc.rca.utils.ILog;

/**
 * Created by Sushil on 10-04-2018.
 */

public class ApplicantHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "ApplicantDB.db";

    public static final int DB_VERSION = 1;

    private static ApplicantHelper sInstance;

    private SQLiteDatabase myDataBase;

    public static ApplicantHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ApplicantHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context
     */
    ApplicantHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void openDataBase() {
        String myPath = DatabaseUtils.DB_PATH + DB_NAME;
        try {
            myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

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

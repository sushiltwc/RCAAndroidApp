package com.twc.rca.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.twc.rca.utils.ILog;

/**
 * Created by Sushil on 21-03-2018.
 */

public class ApplicantProvider extends ContentProvider {

    public static final String TAG = ApplicantProvider.class.getSimpleName();

    static final String DB_NAME = "ApplicantList.db";

    static int DB_VERSION = 1;

    DatabaseHelper mDBHelper;

    SQLiteDatabase mDatabase;

    private ApplicantSQLiteHelper mApplicantSQLiteHelper;

    public static final String AUTHORITY = "com.twc.rca.database.applicant";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final int APPLICANT_LIST = 1;

    private static final UriMatcher URI_MATCHER;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(AUTHORITY, ApplicantProvider.ApplicantList.TABLE_NAME, APPLICANT_LIST);
    }

    @Override
    public boolean onCreate() {
        mApplicantSQLiteHelper = new ApplicantSQLiteHelper(getContext(), DB_NAME, null, DB_VERSION);
        try {
            mDatabase = mApplicantSQLiteHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            mDatabase = mApplicantSQLiteHelper.getReadableDatabase();
        }
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        switch (URI_MATCHER.match(uri)) {
            case APPLICANT_LIST:
                mDatabase = mApplicantSQLiteHelper.getReadableDatabase();
                cursor = mDatabase.query(ApplicantProvider.ApplicantList.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
        }
        if (cursor != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri insertUri = null;
        long rowCount = -1;
        switch (URI_MATCHER.match(uri)) {
            case APPLICANT_LIST:
                rowCount = mDatabase.insert(ApplicantProvider.ApplicantList.TABLE_NAME, null, values);
                insertUri = Uri.withAppendedPath(DocumentProvider.DocList.CONTENT_URI, Long.toString(rowCount));
                break;
        }
        if (rowCount != -1) {
            ILog.d(TAG, "Row inserted " + rowCount);
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return insertUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = -1;
        switch (URI_MATCHER.match(uri)) {
            case APPLICANT_LIST:
                count = mDatabase.delete(ApplicantList.TABLE_NAME, selection, selectionArgs);
                break;
        }
        ILog.d(TAG, "Deleted row : " + count);
        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = -1;
        switch (URI_MATCHER.match(uri)) {
            case APPLICANT_LIST:
                count = mDatabase.update(ApplicantList.TABLE_NAME, values, selection, selectionArgs);
                break;
        }
        ILog.d(TAG, "Updated row : " + count);
        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    static class ApplicantSQLiteHelper extends SQLiteOpenHelper {

        static ApplicantSQLiteHelper sApplicantSQLiteHelper;

        public static ApplicantSQLiteHelper getInstance(Context context) {
            if (sApplicantSQLiteHelper == null) {
                sApplicantSQLiteHelper = new ApplicantSQLiteHelper(context.getApplicationContext(), DB_NAME, null, DB_VERSION);
            }
            return sApplicantSQLiteHelper;
        }

        public ApplicantSQLiteHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        public ApplicantSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            createDocTable(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(ApplicantList.DROP_TABLE);
            createDocTable(db);
        }

        void createDocTable(SQLiteDatabase db) {
            db.execSQL(ApplicantList.CREATE_TABLE);
        }
    }

    public static class ApplicantList {
        public static final String TABLE_NAME = "ApplicantList";

        public static final String KEY_AID = "App_id", KEY_APP_TYPE = "App_Type", KEY_SNAME = "App_SName", KEY_GNAME = "App_GName", KEY_NATIONALITY = "App_Nationality", KEY_GENDER = "App_Gender", KEY_DOB = "App_Dob", KEY_POB = "APP_Pob", KEY_COB = "App_COB", KEY_MARITAL = "App_Marital_Status", KEY_RELIGION = "App_Religion", KEY_LANG = "App_Lang", KEY_PROF = "App_Prof", KEY_FNAME = "App_FName", KEY_MNAME = "App_MName", KEY_HNAME = "App_HName";

        public static final String KEY_PP_NO = "App_PP_No", KEY_PP_TYPE = "App_PP_Type", KEY_PP_ISSUE_GOVT = "App_PP_Issue_Govt", KEY_POI = "App_PP_Poi", KEY_DOI = "App_PP_Doi", KEY_DOE = "App_PP_Doe";

        public static final String KEY_ADD1 = "App_Add1", KEY_ADD2 = "App_Add2", KEY_ADD3 = "App_Add3", KEY_CITY = "App_City", KEY_COUNTRY = "App_Country", KEY_MOB_NO = "App_Mob_No";

        public static final String KEY_ARV_AIR = "App_Arv_Air", KEY_ARV_FLIGHT_NO = "App_Arv_Flight_No", KEY_ARV_COMING_FR = "App_Arv_Coming_Fr", KEY_ARV_DT = "App_Arv_Dt", KEY_ARV_TM_HR = "App_Arv_Tm_Hr", KEY_ARV_TM_MIN = "App_Arv_Tm_Min", KEY_DEPT_AIR = "App_Dept_Air", KEY_DEPT_FLIGHT_NO = "App_Dept_Flight_no", KEY_DEPT_LEAVING_TO = "App_Dept_Leaving_To", KEY_DEPT_DT = "App_Dept_Dt", KEY_DEPT_TM_HR = "App_Dept_Tm_Hr", KEY_DEPT_TM_MIN = "App_Dept_Tm_Min";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(
                ApplicantProvider.CONTENT_URI, TABLE_NAME);

        public static final String CREATE_TABLE =
                " CREATE TABLE IF NOT EXISTS  " + TABLE_NAME +
                        "(" + KEY_AID + " TEXT  , " + KEY_APP_TYPE + " TEXT ,"
                        + KEY_SNAME + " TEXT  , " + KEY_GNAME + " TEXT, " + KEY_NATIONALITY + " TEXT, "
                        + KEY_GENDER + " TEXT , " + KEY_DOB + " TEXT , " + KEY_POB + " TEXT ," + KEY_COB + " TEXT ,"
                        + KEY_MARITAL + " TEXT ," + KEY_RELIGION + " TEXT ," + KEY_LANG + " TEXT ," + KEY_PROF + " TEXT ,"
                        + KEY_FNAME + " TEXT ," + KEY_MNAME + " TEXT ," + KEY_HNAME + " TEXT ,"
                        + KEY_PP_NO + " TEXT ," + KEY_PP_TYPE + " TEXT ," + KEY_PP_ISSUE_GOVT + " TEXT ,"
                        + KEY_POI + " TEXT ," + KEY_DOI + " TEXT ," + KEY_DOE + " TEXT ,"
                        + KEY_ADD1 + " TEXT ," + KEY_ADD2 + " TEXT ," + KEY_ADD3 + " TEXT ,"
                        + KEY_CITY + " TEXT ," + KEY_COUNTRY + " TEXT ," + KEY_MOB_NO + " TEXT ,"
                        + KEY_ARV_AIR + " TEXT ," + KEY_ARV_FLIGHT_NO + " TEXT ," + KEY_ARV_COMING_FR + " TEXT ,"
                        + KEY_ARV_DT + " TEXT ," + KEY_ARV_TM_HR + " TEXT ," + KEY_ARV_TM_MIN + " TEXT ,"
                        + KEY_DEPT_AIR + " TEXT ," + KEY_DEPT_FLIGHT_NO + " TEXT ," + KEY_DEPT_LEAVING_TO + " TEXT ,"
                        + KEY_DEPT_DT + " TEXT ," + KEY_DEPT_TM_HR + " TEXT ," + KEY_DEPT_TM_MIN + " TEXT " +
                        ")";

        public static final String DROP_TABLE = "drop table if exists " + ApplicantList.TABLE_NAME;
    }
}

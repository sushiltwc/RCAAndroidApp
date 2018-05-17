package com.twc.rca.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.twc.rca.model.PassportTypeModel;

import java.util.ArrayList;

/**
 * Created by Sushil on 15-03-2018.
 */

public class PassportTypeHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "PassportTypeDB.db";

    public static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "PassportType";
    private static final String PP_TYPE_ID = "passport_type_id";
    private static final String PP_TYPE_CODE = "passport_type_code";
    private static final String PP_TYPE_NAME = "passport_type_name";

    private SQLiteDatabase myDataBase;

    private static PassportTypeHelper sInstance;

    public static PassportTypeHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PassportTypeHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context
     */
    PassportTypeHelper(Context context) {
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

    public ArrayList<PassportTypeModel> getPassportTypeList() {

        Cursor cursor = null;
        PassportTypeModel passportTypeModel;
        ArrayList<PassportTypeModel> passportTypeModelArrayList=new ArrayList<>();
        try {
            cursor = myDataBase.query(TABLE_NAME, new String[]{PP_TYPE_ID,PP_TYPE_CODE,PP_TYPE_NAME}, null,
                    null, null, null, null, null);
        } catch (SQLiteException ex) {
            ex.printStackTrace();
        }
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    passportTypeModel = new PassportTypeModel();
                    passportTypeModel.setPassportTypeId(cursor.getString(cursor.getColumnIndex(PP_TYPE_ID)));
                    passportTypeModel.setPassportTypeCode(cursor.getString(cursor.getColumnIndex(PP_TYPE_CODE)));
                    passportTypeModel.setPassportTypeName(cursor.getString(cursor.getColumnIndex(PP_TYPE_NAME)));
                    passportTypeModelArrayList.add(passportTypeModel);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        return passportTypeModelArrayList;
    }

    public String getPassportTypeId(String passporttypeName) {
        String passportTypeId = null;

        String sql = "";
        sql += "SELECT " + PP_TYPE_ID + " FROM " + TABLE_NAME;
        sql += " WHERE " + PP_TYPE_NAME + "='" + passporttypeName + "'";

        Cursor cur = myDataBase.rawQuery(sql, null);

        if (cur != null) {
            if (cur.getCount() > 0) {
                cur.moveToFirst();
                passportTypeId = cur.getString(cur.getColumnIndex(PP_TYPE_ID));
            }
        }
        cur.close();
        return passportTypeId;
    }
}


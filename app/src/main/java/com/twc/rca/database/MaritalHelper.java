package com.twc.rca.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.twc.rca.model.MaritalModel;

import java.util.ArrayList;

/**
 * Created by Sushil on 15-03-2018.
 */

public class MaritalHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "MaritalDB.db";

    public static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "Marital";
    private static final String MARITAL_ID = "marital_status_id";
    private static final String MARITAL_CODE = "marital_status_code";
    private static final String MARITAL_NAME = "marital_status_name";

    private SQLiteDatabase myDataBase;

    private static MaritalHelper sInstance;

    public static MaritalHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new MaritalHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context
     */
    MaritalHelper(Context context) {
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

    public ArrayList<MaritalModel> getMatitalStatusList() {

        Cursor cursor = null;
        MaritalModel maritalModel;
        ArrayList<MaritalModel> maritalModelArrayList = new ArrayList<>();
        try {
            cursor = myDataBase.query(TABLE_NAME, new String[]{MARITAL_ID, MARITAL_CODE, MARITAL_NAME}, null,
                    null, null, null, null, null);
        } catch (SQLiteException ex) {
            ex.printStackTrace();
        }
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    maritalModel = new MaritalModel();
                    maritalModel.setMaritalId(cursor.getString(cursor.getColumnIndex(MARITAL_ID)));
                    maritalModel.setMaritalCode(cursor.getString(cursor.getColumnIndex(MARITAL_CODE)));
                    maritalModel.setMaritalName(cursor.getString(cursor.getColumnIndex(MARITAL_NAME)));
                    maritalModelArrayList.add(maritalModel);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return maritalModelArrayList;
    }

    public String getMaritalId(String maritalName) {
        String maritalId = null;

        String sql = "";
        sql += "SELECT " + MARITAL_ID + " FROM " + TABLE_NAME;
        sql += " WHERE " + MARITAL_NAME + "='" + maritalName + "'";

        Cursor cur = myDataBase.rawQuery(sql, null);

        if (cur != null) {
            if (cur.getCount() > 0) {
                cur.moveToFirst();
                maritalId = cur.getString(cur.getColumnIndex(MARITAL_ID));
            }
        }
        cur.close();
        return maritalId;
    }

    public String getMaritalName(String maritalId) {
        String maritalName = null;

        String sql = "";
        sql += "SELECT " + MARITAL_NAME + " FROM " + TABLE_NAME;
        sql += " WHERE " + MARITAL_ID + "='" + maritalId + "'";

        Cursor cur = myDataBase.rawQuery(sql, null);

        if (cur != null) {
            if (cur.getCount() > 0) {
                cur.moveToFirst();
                maritalName = cur.getString(cur.getColumnIndex(MARITAL_NAME));
            }
        }
        cur.close();
        return maritalName;
    }
}


package com.twc.rca.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.twc.rca.model.CountryModel;

import java.util.ArrayList;

/**
 * Created by Sushil on 15-03-2018.
 */

public class CountryHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "CountryDB.db";

    public static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "Country";
    private static final String COUNTRY_ID = "country_id";
    private static final String COUNTRY_CODE = "country_code";
    private static final String COUNTRY_NAME = "country_name";

    private SQLiteDatabase myDataBase;

    private static CountryHelper sInstance;

    public static CountryHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new CountryHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context
     */
    CountryHelper(Context context) {
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

    public ArrayList<CountryModel> getCountryList() {

        Cursor cursor = null;
        CountryModel countryModel;
        ArrayList<CountryModel> countryModelArrayList=new ArrayList<>();
        try {
            cursor = myDataBase.query(TABLE_NAME, new String[]{COUNTRY_ID,COUNTRY_CODE,COUNTRY_NAME}, null,
                    null, null, null, null, null);
        } catch (SQLiteException ex) {
            ex.printStackTrace();
        }
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    countryModel = new CountryModel();
                    countryModel.setCountryId(cursor.getString(cursor.getColumnIndex(COUNTRY_ID)));
                    countryModel.setCountryCode(cursor.getString(cursor.getColumnIndex(COUNTRY_CODE)));
                    countryModel.setCountryName(cursor.getString(cursor.getColumnIndex(COUNTRY_NAME)));
                    countryModelArrayList.add(countryModel);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        return countryModelArrayList;
    }
}

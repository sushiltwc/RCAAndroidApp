package com.twc.rca.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.twc.rca.model.ProfessionModel;

import java.util.ArrayList;

/**
 * Created by Sushil on 15-03-2018.
 */

public class ProfessionHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "ProfessionDB.db";

    public static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "Profession";
    private static final String PROFESSION_ID = "profession_id";
    private static final String PROFESSION_CODE = "profession_code";
    private static final String PROFESSION_NAME = "profession_name";

    private SQLiteDatabase myDataBase;

    private static ProfessionHelper sInstance;

    public static ProfessionHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ProfessionHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context
     */
    ProfessionHelper(Context context) {
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

    public ArrayList<ProfessionModel> getProfessionList() {

        Cursor cursor = null;
        ProfessionModel professionModel;
        ArrayList<ProfessionModel> countryModelArrayList = new ArrayList<>();
        try {
            cursor = myDataBase.query(TABLE_NAME, new String[]{PROFESSION_ID, PROFESSION_CODE, PROFESSION_NAME}, null,
                    null, null, null, null, null);
        } catch (SQLiteException ex) {
            ex.printStackTrace();
        }
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    professionModel = new ProfessionModel();
                    professionModel.setProfessionId(cursor.getString(cursor.getColumnIndex(PROFESSION_ID)));
                    professionModel.setProfessionCode(cursor.getString(cursor.getColumnIndex(PROFESSION_CODE)));
                    professionModel.setProfessionName(cursor.getString(cursor.getColumnIndex(PROFESSION_NAME)));
                    countryModelArrayList.add(professionModel);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return countryModelArrayList;
    }

    public String getProfessionId(String professionName) {
        String professionId = null;

        String sql = "";
        sql += "SELECT " + PROFESSION_ID + " FROM " + TABLE_NAME;
        sql += " WHERE " + PROFESSION_NAME + "='" + professionName + "'";

        Cursor cur = myDataBase.rawQuery(sql, null);

        if (cur != null) {
            if (cur.getCount() > 0) {
                cur.moveToFirst();
                professionId = cur.getString(cur.getColumnIndex(PROFESSION_ID));
            }
        }
        cur.close();
        return professionId;
    }
}


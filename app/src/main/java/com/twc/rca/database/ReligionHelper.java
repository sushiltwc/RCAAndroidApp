package com.twc.rca.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.twc.rca.model.ReligionModel;

import java.util.ArrayList;

/**
 * Created by TWC on 15-03-2018.
 */

public class ReligionHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "ReligionDB.db";

    public static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "Religion";
    private static final String RELIGION_ID = "religion_id";
    private static final String RELIGION_CODE = "religion_code";
    private static final String RELIGION_NAME = "religion_name";

    private SQLiteDatabase myDataBase;

    private static ReligionHelper sInstance;

    public static ReligionHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ReligionHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context
     */
    ReligionHelper(Context context) {
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

    public ArrayList<ReligionModel> getReligionList() {

        Cursor cursor = null;
        ReligionModel religionModel;
        ArrayList<ReligionModel> religionModelArrayList=new ArrayList<>();
        try {
            cursor = myDataBase.query(TABLE_NAME, new String[]{RELIGION_ID,RELIGION_CODE,RELIGION_NAME}, null,
                    null, null, null, null, null);
        } catch (SQLiteException ex) {
            ex.printStackTrace();
        }
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    religionModel = new ReligionModel();
                    religionModel.setReligionId(cursor.getString(cursor.getColumnIndex(RELIGION_ID)));
                    religionModel.setReligionCode(cursor.getString(cursor.getColumnIndex(RELIGION_CODE)));
                    religionModel.setReligionName(cursor.getString(cursor.getColumnIndex(RELIGION_NAME)));
                    religionModelArrayList.add(religionModel);
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        return religionModelArrayList;
    }

    public String getReligionId(String religionName) {
        String religionId = null;

        String sql = "";
        sql += "SELECT " + RELIGION_ID + " FROM " + TABLE_NAME;
        sql += " WHERE " + RELIGION_NAME + "='" + religionName + "'";

        Cursor cur = myDataBase.rawQuery(sql, null);

        if (cur != null) {
            if (cur.getCount() > 0) {
                cur.moveToFirst();
                religionId = cur.getString(cur.getColumnIndex(RELIGION_ID));
            }
        }
        cur.close();
        return religionId;
    }
}


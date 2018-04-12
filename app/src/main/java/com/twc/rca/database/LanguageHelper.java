package com.twc.rca.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.twc.rca.model.LanguageModel;

import java.util.ArrayList;

/**
 * Created by Sushil on 15-03-2018.
 */

public class LanguageHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "LanguageDB.db";

    public static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "Language";
    private static final String LANGUAGE_ID = "lang_id";
    private static final String LANGUAGE_CODE = "lang_code";
    private static final String LANGUAGE_NAME = "lang_name";

    private SQLiteDatabase myDataBase;

    private static LanguageHelper sInstance;

    public static LanguageHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new LanguageHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context
     */
    LanguageHelper(Context context) {
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

    public ArrayList<LanguageModel> getLanguageList() {

        Cursor cursor = null;
        LanguageModel languageModel;
        ArrayList<LanguageModel> languageModelArrayList = new ArrayList<>();
        try {
            cursor = myDataBase.query(TABLE_NAME, new String[]{LANGUAGE_ID, LANGUAGE_CODE, LANGUAGE_NAME}, null,
                    null, null, null, null, null);
        } catch (SQLiteException ex) {
            ex.printStackTrace();
        }
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    languageModel = new LanguageModel();
                    languageModel.setLanguageId(cursor.getString(cursor.getColumnIndex(LANGUAGE_ID)));
                    languageModel.setLanguageCode(cursor.getString(cursor.getColumnIndex(LANGUAGE_CODE)));
                    languageModel.setLanguageName(cursor.getString(cursor.getColumnIndex(LANGUAGE_NAME)));
                    languageModelArrayList.add(languageModel);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return languageModelArrayList;
    }

    public String getLanguageId(String languageName) {
        String languageId = null;

        String sql = "";
        sql += "SELECT " + LANGUAGE_ID + " FROM " + TABLE_NAME;
        sql += " WHERE " + LANGUAGE_NAME + "='" + languageName + "'";

        Cursor cur = myDataBase.rawQuery(sql, null);

        if (cur != null) {
            if (cur.getCount() > 0) {
                cur.moveToFirst();
                languageId = cur.getString(cur.getColumnIndex(LANGUAGE_ID));
            }
        }
        cur.close();
        return languageId;
    }
}



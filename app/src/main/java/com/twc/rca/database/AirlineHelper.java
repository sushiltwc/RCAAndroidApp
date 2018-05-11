package com.twc.rca.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.twc.rca.model.AirlineModel;

import java.util.ArrayList;

/**
 * Created by Sushil on 11-05-2018.
 */

public class AirlineHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "AirlineDB.db";

    public static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "Airline";
    private static final String AIRLINE_ID = "airline_id";
    private static final String AIRLINE_NAME = "airline_name";
    private static final String TERMINAL = "terminal";

    private SQLiteDatabase myDataBase;

    private static AirlineHelper sInstance;

    public static AirlineHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new AirlineHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context
     */
    AirlineHelper(Context context) {
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

    public ArrayList<AirlineModel> getAirlineList() {

        Cursor cursor = null;
        AirlineModel airlineModel;
        ArrayList<AirlineModel> airlineModelArrayList = new ArrayList<>();
        try {
            cursor = myDataBase.query(TABLE_NAME, new String[]{AIRLINE_ID, AIRLINE_NAME, TERMINAL}, null,
                    null, null, null, null, null);
        } catch (SQLiteException ex) {
            ex.printStackTrace();
        }
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    airlineModel = new AirlineModel();
                    airlineModel.setAirlineId(cursor.getString(cursor.getColumnIndex(AIRLINE_ID)));
                    airlineModel.setAirlineName(cursor.getString(cursor.getColumnIndex(AIRLINE_NAME)));
                    airlineModel.setTerminal(cursor.getString(cursor.getColumnIndex(TERMINAL)));
                    airlineModelArrayList.add(airlineModel);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return airlineModelArrayList;
    }
}

package com.twc.rca.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

/**
 * Created by Sushil on 25-04-2018.
 */

public class OrderHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "OrderDB.db";

    public static final int DB_VERSION = 1;

    private static OrderHelper sInstance;

    private SQLiteDatabase myDataBase;

    public static OrderHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new OrderHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context
     */
    OrderHelper(Context context) {
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

    public static boolean addOrderDetails(Context context,String orderId,String appId,String appType,String orderStatus){
        ContentValues values = getValuesFromOrderResponse(orderId,appId,appType,orderStatus);
        Uri uri = context.getContentResolver().insert(OrderProvider.OrderList.CONTENT_URI, values);
        return uri != null;
    }

    private static ContentValues getValuesFromOrderResponse(String orderId,String appId,String appType,String orderStatus) {
        ContentValues values = new ContentValues();
        values.put(OrderProvider.OrderList.KEY_ORDER_ID, orderId);
        values.put(OrderProvider.OrderList.KEY_APP_ID, appId);
        values.put(OrderProvider.OrderList.KEY_APP_TYPE,appType);
        values.put(OrderProvider.OrderList.KEY_ORDER_STATUS, orderStatus);
        return values;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

package com.twc.rca.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.twc.rca.utils.ILog;

/**
 * Created by Sushil on 25-04-2018.
 */

public class OrderProvider extends ContentProvider {

    public static final String TAG = OrderProvider.class.getSimpleName();

    DatabaseHelper mDBHelper;

    SQLiteDatabase mDatabase;

    public static final String AUTHORITY = "com.twc.rca.database.order";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final int ORDER_LIST = 1;

    private static final UriMatcher URI_MATCHER;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(AUTHORITY, ApplicantProvider.ApplicantList.TABLE_NAME, ORDER_LIST);
    }

    @Override
    public boolean onCreate() {
        mDBHelper = DatabaseHelper.getInstance(getContext());
        try {
            mDatabase = mDBHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            mDatabase = mDBHelper.getReadableDatabase();
        }
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        switch (URI_MATCHER.match(uri)) {
            case ORDER_LIST:
                mDatabase = mDBHelper.getReadableDatabase();
                cursor = mDatabase.query(OrderList.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
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
            case ORDER_LIST:
                rowCount = mDatabase.insert(OrderList.TABLE_NAME, null, values);
                insertUri = Uri.withAppendedPath(OrderList.CONTENT_URI, Long.toString(rowCount));
                break;
        }
        if (rowCount > 0) {
            getContext().getContentResolver().notifyChange(insertUri, null);
        }
        return insertUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = -1;
        switch (URI_MATCHER.match(uri)) {
            case ORDER_LIST:
                count = mDatabase.delete(OrderList.TABLE_NAME, selection, selectionArgs);
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
            case ORDER_LIST:
                count = mDatabase.update(OrderList.TABLE_NAME, values, selection, selectionArgs);
                break;
        }
        ILog.d(TAG, "Updated row : " + count);
        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    public static class OrderList {
        public static final String TABLE_NAME = "OrderList";

        public static final String KEY_ORDER_ID = "orderId", KEY_APP_ID = "appId", KEY_APP_TYPE = "appType", KEY_ORDER_STATUS = "orderStatus";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(
                OrderProvider.CONTENT_URI, TABLE_NAME);

        public static final String CREATE_TABLE = " CREATE TABLE IF NOT EXISTS  " + TABLE_NAME +
                "(" + KEY_ORDER_ID + " TEXT  , " + KEY_APP_ID + " TEXT ," + KEY_APP_TYPE + " TEXT ,"
                + KEY_ORDER_STATUS + " TEXT" + ")";

    }
}

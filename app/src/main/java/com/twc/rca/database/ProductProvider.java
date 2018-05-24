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
 * Created by  on 14-03-2018.
 */

public class ProductProvider extends ContentProvider {

    public static final String TAG = ProductProvider.class.getSimpleName();

    DatabaseHelper mDBHelper;

    SQLiteDatabase mDatabase;

    public static final String AUTHORITY = "com.twc.rca.database.product";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final int PRODUCT_LIST = 1;

    private static final UriMatcher URI_MATCHER;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(AUTHORITY, ProductProvider.ProductList.TABLE_NAME, PRODUCT_LIST);
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
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)  {
        Cursor cursor = null;
        switch (URI_MATCHER.match(uri)) {
            case PRODUCT_LIST:
                mDatabase = mDBHelper.getReadableDatabase();
                cursor = mDatabase.query(ProductList.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
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
            case PRODUCT_LIST:
                rowCount = mDatabase.insert(ProductList.TABLE_NAME, null, values);
                insertUri = Uri.withAppendedPath(ProductList.CONTENT_URI, Long.toString(rowCount));
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
            case PRODUCT_LIST:
                count = mDatabase.delete(ProductList.TABLE_NAME, selection, selectionArgs);
                break;
        }
        ILog.d(TAG, "Deleted row : " + count);
        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = -1;
        switch (URI_MATCHER.match(uri)) {
            case PRODUCT_LIST:
                count = mDatabase.update(ProductList.TABLE_NAME, values, selection, selectionArgs);
                break;
        }
        ILog.d(TAG, "Updated row : " + count);
        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    public static class ProductList {
        public static final String TABLE_NAME = "ProductList";

        public static final String KEY_PID = "Product_id", KEY_PNAME = "Product_Name", KEY_PRODUCT_TYPE="Product_Type", KEY_PRODUCT_VALIDITY="Product_Validity", KEY_PRODUCT_CURRENCY="Currency", KEY_ADULT_PRICE = "Adult_Price",KEY_CHILD_PRICE = "Child_Price",KEY_INFANT_PRICE = "Infant_Price";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(
                ProductProvider.CONTENT_URI, ProductList.TABLE_NAME);

        public static final String CREATE_TABLE =
                " CREATE TABLE IF NOT EXISTS  " + TABLE_NAME + "(" + KEY_PID + " TEXT  , " + KEY_PNAME + " TEXT  , "
                        + KEY_PRODUCT_TYPE + " TEXT, " + KEY_PRODUCT_VALIDITY + " TEXT, " + KEY_PRODUCT_CURRENCY + " TEXT , " + KEY_ADULT_PRICE + " TEXT , " +
                        KEY_CHILD_PRICE + " TEXT ," + KEY_INFANT_PRICE + " TEXT " + ")";
    }
}

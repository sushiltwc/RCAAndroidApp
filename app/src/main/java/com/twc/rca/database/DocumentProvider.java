package com.twc.rca.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.twc.rca.utils.ILog;

/**
 * Created by Sushil on 22-05-2018.
 */

public class DocumentProvider extends ContentProvider {

    public static final String TAG = DocumentProvider.class.getSimpleName();

    static final String DB_NAME = "DocumentList.db";

    static int DB_VERSION = 1;

    SQLiteDatabase mDatabase;

    private DocSQLiteHelper mDocSQLiteHelper;

    public static final String AUTHORITY = "com.twc.rca.database.doc", RAW_QUERY_PATH = "/rawQuery", GROUP_BY_QUERY = "/groupBy";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final int DOC_LIST = 1;

    private static final UriMatcher URI_MATCHER;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(AUTHORITY, DocList.TABLE_NAME, DOC_LIST);
    }

    @Override
    public boolean onCreate() {
        mDocSQLiteHelper = new DocSQLiteHelper(getContext(), DB_NAME, null, DB_VERSION);
        try {
            mDatabase = mDocSQLiteHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            mDatabase = mDocSQLiteHelper.getReadableDatabase();
        }
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        switch (URI_MATCHER.match(uri)) {
            case DOC_LIST:
                mDatabase = mDocSQLiteHelper.getReadableDatabase();
                cursor = mDatabase.query(DocumentProvider.DocList.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
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
            case DOC_LIST:
                rowCount = mDatabase.insert(DocumentProvider.DocList.TABLE_NAME, null, values);
                insertUri = Uri.withAppendedPath(DocumentProvider.DocList.CONTENT_URI, Long.toString(rowCount));
                break;
        }
        if (rowCount != -1) {
            ILog.d(TAG, "Row inserted " + rowCount);
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return insertUri;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        int count = 0;
        switch (URI_MATCHER.match(uri)) {
            case DOC_LIST:
                for (ContentValues value : values) {
                    if (mDatabase.insert(DocList.TABLE_NAME, null, value) != -1) {
                        count++;
                    }
                }
                break;
        }
        ILog.d(TAG, "Inserted rows: " + count);
        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (URI_MATCHER.match(uri)) {
            case DOC_LIST:
                count = mDatabase.delete(DocList.TABLE_NAME, selection, selectionArgs);
                break;
        }
        ILog.d(TAG, "Deleted rows: " + count + " Uri " + uri);
        if (count != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = -1;
        switch (URI_MATCHER.match(uri)) {
            case DOC_LIST:
                count = mDatabase.update(DocList.TABLE_NAME, values, selection, selectionArgs);
                break;
        }
        ILog.d(TAG, "Rows updated " + count);
        if (count != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    static class DocSQLiteHelper extends SQLiteOpenHelper {

        static DocSQLiteHelper sDocSQLiteHelper;

        public static DocSQLiteHelper getInstance(Context context) {
            if (sDocSQLiteHelper == null) {
                sDocSQLiteHelper = new DocSQLiteHelper(context.getApplicationContext(), DB_NAME, null, DB_VERSION);
            }
            return sDocSQLiteHelper;
        }

        public DocSQLiteHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        public DocSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            createDocTable(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DocList.DROP_TABLE);
            createDocTable(db);
        }

        void createDocTable(SQLiteDatabase db) {
            db.execSQL(DocList.CREATE_TABLE);
        }
    }


    public static class DocList {
        public static final String TABLE_NAME = "DocList";

        public static final String KEY_USER_ID = "userId", KEY_APP_ID = "appId", KEY_ORDER_ID = "orderId", KEY_DOC_ID = "docId", KEY_DOC_TYPE = "docType", KEY_DOC_STATUS = "docStatus";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(
                DocumentProvider.CONTENT_URI, TABLE_NAME);

        public static final String CREATE_TABLE = " CREATE TABLE IF NOT EXISTS  " + TABLE_NAME +
                "(" + KEY_USER_ID + " TEXT  , " + KEY_ORDER_ID + " TEXT , " + KEY_APP_ID + " TEXT ,"
                + KEY_DOC_ID + " TEXT , " + KEY_DOC_TYPE + " TEXT , " + KEY_DOC_STATUS + " TEXT" + ")";

        public static final String DROP_TABLE = "drop table if exists " + DocList.TABLE_NAME;

    }
}

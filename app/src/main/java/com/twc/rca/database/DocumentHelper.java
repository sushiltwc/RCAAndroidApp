package com.twc.rca.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.twc.rca.applicant.model.DocumentModel;
import com.twc.rca.utils.ILog;
import com.twc.rca.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sushil on 22-05-2018.
 */

public class DocumentHelper {

    public static void insertDocList(Context context, String orderId, String appId, List<DocumentModel> documentModelArrayList) {
        for (int i = 0; i < documentModelArrayList.size(); i++) {
            ContentValues cv = new ContentValues();
            cv.put(DocumentProvider.DocList.KEY_USER_ID, PreferenceUtils.getUserid(context));
            cv.put(DocumentProvider.DocList.KEY_ORDER_ID, orderId);
            cv.put(DocumentProvider.DocList.KEY_APP_ID, appId);
            cv.put(DocumentProvider.DocList.KEY_DOC_ID, documentModelArrayList.get(i).getDoc_type_id());
            cv.put(DocumentProvider.DocList.KEY_DOC_TYPE, documentModelArrayList.get(i).getDoc_type_name());
            cv.put(DocumentProvider.DocList.KEY_DOC_STATUS, documentModelArrayList.get(i).getDoc_submitted());

            if (!isDataExist(context, orderId, appId, documentModelArrayList.get(i).getDoc_type_id()))
                context.getContentResolver().insert(DocumentProvider.DocList.CONTENT_URI, cv);
            else {
                context.getContentResolver().update(DocumentProvider.DocList.CONTENT_URI, cv, DocumentProvider.DocList.KEY_ORDER_ID + "=" + orderId + " AND " + DocumentProvider.DocList.KEY_APP_ID + "=" + appId + " AND " + DocumentProvider.DocList.KEY_DOC_ID + "=" + documentModelArrayList.get(i).getDoc_type_id(), null);
            }
        }
    }

    public static boolean isDataExist(Context context, String orderId, String appId, String docId) {

        String selection = DocumentProvider
                .DocList.KEY_ORDER_ID + " = " + orderId + " AND " + DocumentProvider.DocList.KEY_APP_ID + " = " + appId + " AND " + DocumentProvider.DocList.KEY_DOC_ID + " = " + docId;

        Cursor cursor = context.getContentResolver().query(DocumentProvider.DocList
                .CONTENT_URI, null, selection, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public static void updateDocList(Context context, String orderId, String applicantId, String docId, String docType, String docStatus) {
        ContentValues cv = new ContentValues();
        cv.put(DocumentProvider.DocList.KEY_USER_ID, PreferenceUtils.getUserid(context));
        cv.put(DocumentProvider.DocList.KEY_ORDER_ID, orderId);
        cv.put(DocumentProvider.DocList.KEY_APP_ID, applicantId);
        cv.put(DocumentProvider.DocList.KEY_DOC_ID, docId);
        cv.put(DocumentProvider.DocList.KEY_DOC_TYPE, docType);
        cv.put(DocumentProvider.DocList.KEY_DOC_STATUS, docStatus);
        context.getContentResolver().update(DocumentProvider.DocList.CONTENT_URI, cv, DocumentProvider.DocList.KEY_ORDER_ID + "=" + orderId + " AND " + DocumentProvider.DocList.KEY_APP_ID + "=" + applicantId + " AND " + DocumentProvider.DocList.KEY_DOC_ID + "=" + docId, null);
    }

    public static boolean isDocListUploaded(Context context, String orderId, String applicantId, ArrayList<DocumentModel> documentModelArrayList) {

        int i = 0;

        String selection = DocumentProvider.DocList.KEY_ORDER_ID + " = " + orderId + " AND " + DocumentProvider.DocList.KEY_APP_ID + " = " + applicantId;

        Cursor cursor = context.getContentResolver().query(DocumentProvider.DocList.CONTENT_URI, null, selection, null, null);

        if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {
            do {
                if (cursor.getString(cursor.getColumnIndex(DocumentProvider.DocList.KEY_DOC_STATUS)).equalsIgnoreCase("true"))
                    i++;
            } while (cursor.moveToNext());
        }
        ILog.d("DocUploaded",String.valueOf(i));
        if (documentModelArrayList.size() == i)
            return true;
        else
            return false;
    }
}

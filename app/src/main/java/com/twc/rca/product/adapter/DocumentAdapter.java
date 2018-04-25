package com.twc.rca.product.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.twc.rca.R;
import com.twc.rca.applicant.model.DocumentModel;
import com.twc.rca.applicant.model.PassportBackModel;
import com.twc.rca.applicant.model.PassportFrontModel;
import com.twc.rca.applicant.task.DocumentUploadTask;
import com.twc.rca.product.fragments.DocumentFragment;
import com.twc.rca.utils.ApiUtils;
import com.twc.rca.utils.GVPassportUtils;
import com.twc.rca.utils.ImageFilePath;
import com.twc.rca.utils.PreferenceUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by Sushil on 07-03-2018.
 */

public class DocumentAdapter extends BaseAdapter {

    private Context context;

    final int THUMBNAIL_HEIGHT_SIZE = 90, THUMBNAIL_WIDTH_SIZE = 90;

    Fragment documentFragment;

    ImageView img_doc;

    TextView tv_doc_name;

    List<DocumentModel> docList;

    ProgressDialog progressDialog;

    DocumentUploadCallback documentUploadCallback;

    public interface DocumentUploadCallback {
        void pfCallback(PassportFrontModel passportFrontModel);

        void pbCalback(PassportBackModel passportBackModel);
    }

    public DocumentAdapter(Context context, DocumentFragment fragment, List<DocumentModel> docList, DocumentUploadCallback documentUploadCallback) {
        this.context = context;
        documentFragment = fragment;
        this.docList = docList;
        this.documentUploadCallback = documentUploadCallback;
        progressDialog = new ProgressDialog(context);
    }

    @Override
    public int getCount() {
        return docList.size();
    }

    @Override
    public Object getItem(int i) {
        return docList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.document_list_item, null);

            tv_doc_name = (TextView) view.findViewById(R.id.tv_doc_name);
            img_doc = (ImageView) view.findViewById(R.id.img_doc);
            img_doc.setTag(new Integer(position));
            tv_doc_name.setText(docList.get(position).getDoc_type_name().replace("_", " "));
        }
        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data, View view, int position) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == DocumentFragment.PICK_IMAGE)
                onSelectFromGalleryResult(data, view, position);

            else if (requestCode == DocumentFragment.PICK_CAMERA)
                onCaptureImageResult(data, view, position);
        }
    }

    private void onSelectFromGalleryResult(Intent data, View view, int position) {

        if (data != null) {
            try {
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(context.getApplicationContext().getContentResolver(), data.getData());

                String realPath = ImageFilePath.getPath(context, data.getData());
                String filename = realPath.substring(realPath.lastIndexOf("/") + 1);

                String filenameArray[] = filename.split("\\.");
                String fileFormat = filenameArray[filenameArray.length - 1];

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();

                String base64ImageData = Base64.encodeToString(byteArray, Base64.DEFAULT);

                progressDialog.setMessage(context.getString(R.string.please_wait));
                progressDialog.show();
                new DocumentUploadTask(context, "", docList.get(position).doc_type_name, filename, fileFormat, base64ImageData).documentUpload(documentUploadResposeCallback, view, position);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    DocumentUploadTask.DocumentUploadResposeCallback documentUploadResposeCallback = new DocumentUploadTask.DocumentUploadResposeCallback() {
        @Override
        public void onSuccessDocumentUploadResponse(String response, View view, int position, String base64ImageData) {
            progressDialog.dismiss();
            Bitmap imageBitmap = null;
            imageBitmap = ApiUtils.StringToBitMap(base64ImageData);

            if (docList.get(position).doc_type_id.equalsIgnoreCase("1")) {
                PreferenceUtils.setIsPFUploaded(context, true);
                PassportFrontModel passportFrontModel = new GVPassportUtils(context).processPassportFront(ApiUtils.StringToBitMap(base64ImageData));
                documentUploadCallback.pfCallback(passportFrontModel);
            } else if (docList.get(position).doc_type_id.equalsIgnoreCase("2")) {
                PreferenceUtils.setIsPBUploaded(context, true);
                PassportBackModel passportBackModel = new GVPassportUtils(context).processPassportBack(ApiUtils.StringToBitMap(base64ImageData));
                documentUploadCallback.pbCalback(passportBackModel);
            }
            Bitmap thumbnailImageBitmap = Bitmap.createScaledBitmap(imageBitmap, THUMBNAIL_WIDTH_SIZE, THUMBNAIL_HEIGHT_SIZE, false);

            img_doc = (ImageView) view.findViewWithTag(position);

            img_doc.setImageBitmap(imageBitmap);

            //DocumentFragment.getInstance().uploadDocPrefs();

            Toast.makeText(context, docList.get(position).getDoc_type_name().toString().replace("_", " ") + " " + "Uploaded Successfully!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailureDocumentUploadResponse(String response, View view, int position) {
            progressDialog.dismiss();
            Toast.makeText(context, docList.get(position).getDoc_type_name().toString() + " Not Uploaded Successfully. Please try again!", Toast.LENGTH_SHORT).show();
        }
    };

    private void onCaptureImageResult(Intent data, View view, int position) {

        if (data != null) {
            try {
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(context.getApplicationContext().getContentResolver(), data.getData());
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                imageBitmap = Bitmap.createScaledBitmap(imageBitmap, THUMBNAIL_WIDTH_SIZE, THUMBNAIL_HEIGHT_SIZE, false);
                File rootDirectory = new File(Environment.getExternalStorageDirectory(), String.valueOf(R.string.app_name));
                if (rootDirectory.exists() == false)
                    rootDirectory.mkdirs();
                File destination = new File(rootDirectory,
                        System.currentTimeMillis() + ".jpg");
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                img_doc = (ImageView) view.findViewWithTag(position);

                img_doc.setImageBitmap(imageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

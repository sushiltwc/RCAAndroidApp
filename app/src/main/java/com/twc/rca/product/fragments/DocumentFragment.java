package com.twc.rca.product.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.twc.rca.R;
import com.twc.rca.activities.BaseFragment;
import com.twc.rca.applicant.model.ApplicantModel;
import com.twc.rca.applicant.model.DocumentModel;
import com.twc.rca.applicant.model.PassportBackModel;
import com.twc.rca.applicant.model.PassportFrontModel;
import com.twc.rca.applicant.model.ReceiveDocumentModel;
import com.twc.rca.applicant.task.DocumentListTask;
import com.twc.rca.applicant.task.DocumentReceiveTask;
import com.twc.rca.database.DocumentHelper;
import com.twc.rca.permissions.PermissionDialogUtil;
import com.twc.rca.permissions.RunTimePermissionWrapper;
import com.twc.rca.product.adapter.DocumentAdapter;
import com.twc.rca.utils.ApiUtils;
import com.twc.rca.utils.GVPassportUtils;
import com.twc.rca.utils.PreferenceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sushil on 06-03-2018.
 */

public class DocumentFragment extends BaseFragment {

    public static final int PICK_IMAGE = 1;

    public static final int PICK_CAMERA = 2;

    public static String DOC_TYPE_ID = "doc_type_id", DOC_TYPE_NAME = "doc_type_name";

    ViewPager viewPager;

    GridView document_grid;

    AppCompatButton btn_next;

    ArrayList<DocumentModel> doc_list = new ArrayList<>();

    List<ReceiveDocumentModel> receive_doc_list = new ArrayList<>();

    public DocumentAdapter documentAdapter;

    PassportFrontModel passportFrontModel;

    PassportBackModel passportBackModel;

    View docView;

    int docPosition;

    ApplicantModel applicantModel;

    String applicant_type;

    public static DocumentFragment getInstance() {
        DocumentFragment documentFragment = new DocumentFragment();
        return documentFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_documents, container, false);

        viewPager = (ViewPager) getActivity().findViewById(R.id.applicant_pager);
        document_grid = view.findViewById(R.id.document_grid);
        btn_next = view.findViewById(R.id.btn_doc_next);

        applicantModel = getArguments().getParcelable("applicant");

        if (applicantModel.getApplicantSubmited().equalsIgnoreCase("Y"))
            btn_next.setVisibility(View.GONE);

        applicant_type = applicantModel.getApplicantType().replaceAll("[0-9]", "").replace(" ", "");
        if (applicant_type.equalsIgnoreCase("Infant"))
            applicant_type = "Child";

        showProgressDialog(getString(R.string.please_wait));
        new DocumentReceiveTask(getContext(), applicantModel.getApplicantId()).documentReceive(documentReceiveResposeCallback);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (applicantModel.getApplicantSubmited().equalsIgnoreCase("Y")) {
                    ApplicationFormFragment.getInstance().setCustomerData(applicantModel);
                } else {
                    Bundle b = new Bundle();
                    if (passportFrontModel != null && passportBackModel != null) {
                        b.putSerializable("pf", passportFrontModel);
                        b.putSerializable("pb", passportBackModel);
                        b.putParcelableArrayList("docList", doc_list);
                        ApplicationFormFragment.getInstance().putData(b);
                    } else {
                        String base64ImageData;
                        showProgressDialog(getString(R.string.please_wait));
                        for (int i = 0; i < receive_doc_list.size(); i++) {
                            if (receive_doc_list.get(i).getDoc_type().equalsIgnoreCase("PASSPORT_FRONT")) {
                                base64ImageData = GVPassportUtils.getByteArrayFromImageURL(receive_doc_list.get(i).getDoc_url().toString());
                                passportFrontModel = new GVPassportUtils(getContext()).processPassportFront(ApiUtils.StringToBitMap(base64ImageData));
                            } else if (receive_doc_list.get(i).getDoc_type().equalsIgnoreCase("PASSPORT_BACK")) {
                                base64ImageData = GVPassportUtils.getByteArrayFromImageURL(receive_doc_list.get(i).getDoc_url().toString());
                                passportBackModel = new GVPassportUtils(getContext()).processPassportBack(ApiUtils.StringToBitMap(base64ImageData));
                            }
                        }
                        b.putSerializable("pf", passportFrontModel);
                        b.putSerializable("pb", passportBackModel);
                        b.putParcelableArrayList("docList", doc_list);
                        ApplicationFormFragment.getInstance().putData(b);
                        dismissProgressDialog();
                    }
                }
                viewPager.setCurrentItem(1);
            }
        });

        return view;
    }

    DocumentReceiveTask.DocumentReceiveResposeCallback documentReceiveResposeCallback = new DocumentReceiveTask.DocumentReceiveResposeCallback() {
        @Override
        public void onSuccessDocumentReceeiveResponse(String response) {
            dismissProgressDialog();
            try {
                ReceiveDocumentModel receiveDocumentModel;

                JSONObject jsonObject = new JSONObject(response);

                JSONObject contentObject = (JSONObject) jsonObject.get(ApiUtils.CONTENT);

                JSONArray data = (JSONArray) contentObject.get(ApiUtils.RESULT_SET);

                if (data.length() > 0) {
                    for (int i = 0; i < data.length(); i++) {
                        receiveDocumentModel = new ReceiveDocumentModel();
                        JSONObject jsonobject = data.getJSONObject(i);
                        receiveDocumentModel.doc_id = jsonobject.getString("doc_id");
                        receiveDocumentModel.doc_type = jsonobject.getString("doc_type");
                        receiveDocumentModel.doc_mime_type = jsonobject.getString("doc_mime_type");
                        receiveDocumentModel.doc_url = jsonobject.getString("doc_url");

                        if (receiveDocumentModel.getDoc_type().equalsIgnoreCase("PASSPORT_FRONT")) {
                            PreferenceUtils.setIsPFUploaded(getContext(), true);
                            changeStatus();
                        }
                        if (receiveDocumentModel.getDoc_type().equalsIgnoreCase("PASSPORT_BACK")) {
                            PreferenceUtils.setIsPBUploaded(getContext(), true);
                            changeStatus();
                        }
                        receive_doc_list.add(receiveDocumentModel);
                    }
                }

                showProgressDialog(getString(R.string.please_wait));
                new DocumentListTask(getContext(), applicant_type).getDocList(documentListResposeCallback);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailureDocumentReceiveResponse(String response) {
            dismissProgressDialog();
            showProgressDialog(getString(R.string.please_wait));
            new DocumentListTask(getContext(), applicant_type).getDocList(documentListResposeCallback);
        }
    };

    DocumentListTask.DocumentListResposeCallback documentListResposeCallback = new DocumentListTask.DocumentListResposeCallback() {
        @Override
        public void onSuccessDocumentListResponse(String response) {
            dismissProgressDialog();
            try {
                JSONObject jsonObject = new JSONObject(response);

                JSONObject contentObject = (JSONObject) jsonObject.get(ApiUtils.CONTENT);
                DocumentModel documentModel;

                JSONArray data = (JSONArray) contentObject.get(ApiUtils.RESULT_SET);
                if (data.length() > 0) {
                    for (int i = 0; i < data.length(); i++) {
                        documentModel = new DocumentModel();
                        JSONObject jsonobject = data.getJSONObject(i);
                        documentModel.doc_type_id = jsonobject.getString(DOC_TYPE_ID);
                        documentModel.doc_type_name = jsonobject.getString(DOC_TYPE_NAME);
                        documentModel.doc_submitted = "false";
                        doc_list.add(documentModel);
                    }
                }

                DocumentHelper.insertDocList(getContext(), applicantModel.getApplicantOrderId(), applicantModel.getApplicantId(), doc_list);
                if (receive_doc_list.size() > 0) {
                    for (int i = 0; i < receive_doc_list.size(); i++)
                        DocumentHelper.updateDocList(getContext(), applicantModel.getApplicantOrderId(), applicantModel.getApplicantId(), doc_list.get(i).getDoc_type_id(), receive_doc_list.get(i).getDoc_type(), "true");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            documentAdapter = new DocumentAdapter(getContext(), DocumentFragment.this, applicantModel.getApplicantOrderId(), applicantModel.getApplicantId(), receive_doc_list, doc_list, documentUploadCallback);
            document_grid.setAdapter(documentAdapter);

            document_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    if (applicantModel.getApplicantSubmited().equalsIgnoreCase("N")) {
                        checkForPermissions(getContext(), PICK_IMAGE);
                        docView = view;
                        docPosition = position;
                    }
                }
            });
        }

        @Override
        public void onFailureDocumentListResponse(String response) {
            dismissProgressDialog();
        }
    };

    private void selectImage() {
        final CharSequence[] items = {"TakePhoto", "Choose from Gallery", "Cancel"};

        TextView title = new TextView(getContext());
        title.setText("Add Photo!");
        title.setBackgroundColor(Color.BLACK);
        title.setPadding(10, 15, 15, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(22);


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCustomTitle(title);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("TakePhoto")) {
                    checkForPermissions(getContext(), PICK_CAMERA);
                } else if (items[item].equals("Choose from Gallery")) {
                    checkForPermissions(getContext(), PICK_IMAGE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void checkForPermissions(Context context, int permissioncode) {

        switch (permissioncode) {
            case 1:
                if (RunTimePermissionWrapper.isAllPermissionGranted((Activity) context, RunTimePermissionWrapper.PERMISSION_LIST.GALLERY_PERMISSION)) {
                    galleryIntent();
                } else {
                    PermissionDialogUtil.handlePermissionRequest((Activity) context, RunTimePermissionWrapper.REQUEST_CODE.GALLERY_PERMISSION, RunTimePermissionWrapper.PERMISSION_LIST.GALLERY_PERMISSION);

                }
                break;
            case 2:

                if (RunTimePermissionWrapper.isAllPermissionGranted((Activity) context, RunTimePermissionWrapper.PERMISSION_LIST.CAMERA_PERMISSION)) {
                    cameraIntent();
                } else {
                    PermissionDialogUtil.handlePermissionRequest((Activity) context, RunTimePermissionWrapper.REQUEST_CODE.CAMERA_PERMISSION, RunTimePermissionWrapper.PERMISSION_LIST.CAMERA_PERMISSION);
                }
                break;
            default:
        }
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        this.startActivityForResult(intent, PICK_IMAGE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, PICK_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        documentAdapter.onActivityResult(requestCode, resultCode, data, docView, docPosition);
    }

    public void uploadDocPrefs() {
        PreferenceManager.getDefaultSharedPreferences(getContext())
                .registerOnSharedPreferenceChangeListener(mPreferenceChangeListener);
    }

    void changeStatus() {
        if (PreferenceUtils.isPFUploaded(getContext()) && PreferenceUtils.isPBUploaded(getContext())) {
            btn_next.setEnabled(true);
            btn_next.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
        }
    }

    SharedPreferences.OnSharedPreferenceChangeListener mPreferenceChangeListener = new
            SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String
                        key) {
                    if (key.equals(PreferenceUtils.IS_PF_UPLOADED) || key.equals(PreferenceUtils.IS_PB_UPLOADED)) {
                        if (PreferenceUtils.isPFUploaded(getContext()) && PreferenceUtils.isPBUploaded(getContext()))
                            btn_next.setEnabled(true);
                        btn_next.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));

                    }
                }
            };

    DocumentAdapter.DocumentUploadCallback documentUploadCallback = new DocumentAdapter.DocumentUploadCallback() {
        @Override
        public void pfCallback(PassportFrontModel pfModel) {
            passportFrontModel = pfModel;
            changeStatus();
        }

        @Override
        public void pbCalback(PassportBackModel pbModel) {
            passportBackModel = pbModel;
            changeStatus();
        }
    };
}

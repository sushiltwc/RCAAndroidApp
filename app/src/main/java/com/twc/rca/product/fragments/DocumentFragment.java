package com.twc.rca.product.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.twc.rca.permissions.PermissionDialogUtil;
import com.twc.rca.permissions.RunTimePermissionWrapper;
import com.twc.rca.product.adapter.DocumentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TWC on 06-03-2018.
 */

public class DocumentFragment extends Fragment {

    public static final int PICK_IMAGE = 1;

    public static final int PICK_CAMERA = 2;

    ViewPager viewPager;

    GridView document_grid;

    AppCompatButton btn_next;

    List<String> doc_list = new ArrayList<>();

    public DocumentAdapter documentAdapter;

    View docView;
    int docPosition;

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
        btn_next=view.findViewById(R.id.btn_doc_next);
        doc_list.add("Passport Front");
        doc_list.add("Passport Back");
        doc_list.add("Photo");

        documentAdapter = new DocumentAdapter(getActivity(), this, doc_list);
        document_grid.setAdapter(documentAdapter);

        document_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectImage();
                docView = view;
                docPosition = position;
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });

        return view;
    }

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
}

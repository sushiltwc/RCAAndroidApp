package com.twc.rca.applicant.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sushil on 17-04-2018.
 */

public class DocumentModel implements Parcelable{

    @SerializedName("doc_type_id")
    public String doc_type_id;

    @SerializedName("doc_type_name")
    public String doc_type_name;

    @SerializedName("doc_submitted")
    public String doc_submitted;

    public DocumentModel(Parcel in){
        doc_type_id=in.readString();
        doc_type_name=in.readString();
        doc_submitted=in.readString();
    }

    public DocumentModel() {

    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(doc_type_id);
        parcel.writeString(doc_type_name);
        parcel.writeString(doc_submitted);
    }

    public static final Creator<DocumentModel> CREATOR = new Creator<DocumentModel>() {
        @Override
        public DocumentModel createFromParcel(Parcel in) {
            return new DocumentModel(in);
        }

        @Override
        public DocumentModel[] newArray(int size) {
            return new DocumentModel[size];
        }
    };

    public String getDoc_type_id() {
        return doc_type_id;
    }

    public void setDoc_type_id(String doc_type_id) {
        this.doc_type_id = doc_type_id;
    }

    public String getDoc_type_name() {
        return doc_type_name;
    }

    public void setDoc_type_name(String doc_type_name) {
        this.doc_type_name = doc_type_name;
    }


    public String getDoc_submitted() {
        return doc_submitted;
    }

    public void setDoc_submitted(String doc_submitted) {
        this.doc_submitted = doc_submitted;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}

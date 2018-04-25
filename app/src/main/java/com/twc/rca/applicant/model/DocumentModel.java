package com.twc.rca.applicant.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sushil on 17-04-2018.
 */

public class DocumentModel {

    @SerializedName("doc_type_id")
    public String doc_type_id;

    @SerializedName("doc_type_name")
    public String doc_type_name;

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
}

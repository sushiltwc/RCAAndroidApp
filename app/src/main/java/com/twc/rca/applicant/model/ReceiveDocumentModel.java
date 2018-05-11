package com.twc.rca.applicant.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sushil on 04-05-2018.
 */
public class ReceiveDocumentModel {

    @SerializedName("doc_id")
    public String doc_id;

    @SerializedName("doc_type")
    public String doc_type;

    @SerializedName("doc_url")
    public String doc_url;

    @SerializedName("doc_mime_type")
    public String doc_mime_type;

    public String getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(String doc_id) {
        this.doc_id = doc_id;
    }

    public String getDoc_type() {
        return doc_type;
    }

    public void setDoc_type(String doc_type) {
        this.doc_type = doc_type;
    }

    public String getDoc_url() {
        return doc_url;
    }

    public void setDoc_url(String doc_url) {
        this.doc_url = doc_url;
    }

    public String getDoc_mime_type() {
        return doc_mime_type;
    }

    public void setDoc_mime_type(String doc_mime_type) {
        this.doc_mime_type = doc_mime_type;
    }
}

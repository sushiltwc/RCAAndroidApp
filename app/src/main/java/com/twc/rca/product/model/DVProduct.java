package com.twc.rca.product.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sushil on 22-02-2018.
 */

public class DVProduct implements Parcelable {

    private String product_id;
    private String product_name;
    private String product_validity;
    private String product_type;
    private String product_info_url;
    private String currency;
    private String adult_price;
    private String child_price;
    private String infant_price;

    public DVProduct(Parcel in){
        product_id=in.readString();
        product_name=in.readString();
        product_validity=in.readString();
        product_type=in.readString();
        product_info_url=in.readString();
        currency=in.readString();
        adult_price=in.readString();
        child_price=in.readString();
        infant_price=in.readString();
    }

    public DVProduct(String product_id,String product_name,String product_validity,String product_type,String product_info_url,String currency,String adult_price,String child_price,String infant_price){
        this.product_id=product_id;
        this.product_name=product_name;
        this.product_validity=product_validity;
        this.product_type=product_type;
        this.product_info_url=product_info_url;
        this.currency=currency;
        this.adult_price=adult_price;
        this.child_price=child_price;
        this.infant_price=infant_price;
    }
    public static final Creator<DVProduct> CREATOR = new Creator<DVProduct>() {
        @Override
        public DVProduct createFromParcel(Parcel in) {
            return new DVProduct(in);
        }

        @Override
        public DVProduct[] newArray(int size) {
            return new DVProduct[size];
        }
    };

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_validity() {
        return product_validity;
    }

    public void setProduct_validity(String product_validity) {
        this.product_validity = product_validity;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getProduct_info_url() {
        return product_info_url;
    }

    public void setProduct_info_url(String product_info_url) {
        this.product_info_url = product_info_url;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAdult_price() {
        return adult_price;
    }

    public void setAdult_price(String adult_price) {
        this.adult_price = adult_price;
    }

    public String getChild_price() {
        return child_price;
    }

    public void setChild_price(String child_price) {
        this.child_price = child_price;
    }

    public String getInfant_price() {
        return infant_price;
    }

    public void setInfant_price(String infant_price) {
        this.infant_price = infant_price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(product_id);
        parcel.writeString(product_name);
        parcel.writeString(product_validity);
        parcel.writeString(product_type);
        parcel.writeString(product_info_url);
        parcel.writeString(currency);
        parcel.writeString(adult_price);
        parcel.writeString(child_price);
        parcel.writeString(infant_price);
    }
}

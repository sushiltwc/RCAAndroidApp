package com.twc.rca.product.model;

/**
 * Created by Sushil on 22-02-2018.
 */

public class DVProduct {

    private String visa_period;
    private String visa_lable;
    private String visa_type;
    private String visa_processing_time;
    private String visa_validity;
    private String visa_amount;

    public DVProduct(String visa_period,String visa_lable,String visa_type,String visa_processing_time,String visa_validity,String visa_amount){
        this.visa_period=visa_period;
        this.visa_lable=visa_lable;
        this.visa_type=visa_type;
        this.visa_processing_time=visa_processing_time;
        this.visa_validity=visa_validity;
        this.visa_amount=visa_amount;
    }

    public DVProduct() {
    }

    public String getVisa_period() {
        return visa_period;
    }

    public void setVisa_period(String visa_period) {
        this.visa_period = visa_period;
    }

    public String getVisa_lable() {
        return visa_lable;
    }

    public void setVisa_lable(String visa_lable) {
        this.visa_lable = visa_lable;
    }

    public String getVisa_type() {
        return visa_type;
    }

    public void setVisa_type(String visa_type) {
        this.visa_type = visa_type;
    }

    public String getVisa_processing_time() {
        return visa_processing_time;
    }

    public void setVisa_processing_time(String visa_processing_time) {
        this.visa_processing_time = visa_processing_time;
    }

    public String getVisa_validity() {
        return visa_validity;
    }

    public void setVisa_validity(String visa_validity) {
        this.visa_validity = visa_validity;
    }

    public String getVisa_amount() {
        return visa_amount;
    }

    public void setVisa_amount(String visa_amount) {
        this.visa_amount = visa_amount;
    }

}

package com.twc.rca.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 3LV on 07-08-2017.
 */

public class UserExist {

    @SerializedName("user_id")
    public String userId;

    @SerializedName("email_id")
    public String email_id;

    @SerializedName("mobile_no")
    public String mobile_no;

    @SerializedName("device_id")
    public String device_id;
}

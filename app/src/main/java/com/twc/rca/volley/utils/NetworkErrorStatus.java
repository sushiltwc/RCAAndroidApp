package com.twc.rca.volley.utils;

/**
 * Get Network errorMessage and errorCode
 * Created by Sushil
 */
public class NetworkErrorStatus {

    private String errorMessage = null;

    private int errorCode = 0;


    public String getErrorMessage() {
        return errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

}

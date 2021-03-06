package com.twc.rca.product.model;

/**
 * Created by Sushil on 27-02-2018.
 */

public class Transaction {

    public static final int COUNTRY = 1;
    public static final int LANGUAGE = 2;
    public static final int MARITALSTATUS = 3;
    public static final int PASSPORTTYPE = 4;
    public static final int PROFESSION = 5;
    public static final int RELIGION = 6;
    public static final int COUNTRY_BIRTH = 7;
    public static final int ARRIVAL_AIRLINE = 8;
    public static final int DEPT_AIRLINE = 9;
    public static final int PASSPORT_ISSUE_GOVT=10;

    private int noOfPassengers;
    private int noOfAdults;
    private int noOfChildrens;
    private int noOfInfants;

    private String visaType;
    private String country;
    private String country_birth;
    private String language;
    private String maritalStatus;
    private String passportType;
    private String passportIssueGovt;
    private String profession;
    private String religion;
    private String arrival_airline;
    private String dept_airline;

    private int transaction_type;  //stores the current transaction type

    private static Transaction mTransactionInstance;

    private Transaction() {
    }

    public static Transaction getmTransactionInstance() {
        if (mTransactionInstance == null) {
            mTransactionInstance = new Transaction();
        }
        return mTransactionInstance;
    }

    public int getNoOfPassengers() {
        return noOfPassengers;
    }

    public void setNoOfPassengers(int noOfPassengers) {
        this.noOfPassengers = noOfPassengers;
    }

    public int getNoOfChildrens() {
        return noOfChildrens;
    }

    public void setNoOfChildrens(int noOfChildrens) {
        this.noOfChildrens = noOfChildrens;
    }

    public int getNoOfAdults() {
        return noOfAdults;
    }

    public void setNoOfAdults(int noOfAdults) {
        this.noOfAdults = noOfAdults;
    }

    public int getNoOfInfants() {
        return noOfInfants;
    }

    public void setNoOfInfants(int noOfInfants) {
        this.noOfInfants = noOfInfants;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getPassportType() {
        return passportType;
    }

    public void setPassportType(String passportType) {
        this.passportType = passportType;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getVisaType() {
        return visaType;
    }

    public void setVisaType(String visaType) {
        this.visaType = visaType;
    }

    public int getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(int transaction_type) {
        this.transaction_type = transaction_type;
    }

    public String getCountry_birth() {
        return country_birth;
    }

    public void setCountry_birth(String country_birth) {
        this.country_birth = country_birth;
    }

    public String getArrival_airline() {
        return arrival_airline;
    }

    public void setArrival_airline(String arrival_airline) {
        this.arrival_airline = arrival_airline;
    }

    public String getDept_airline() {
        return dept_airline;
    }

    public void setDept_airline(String dept_airline) {
        this.dept_airline = dept_airline;
    }

    public String getPassportIssueGovt() {
        return passportIssueGovt;
    }

    public void setPassportIssueGovt(String passportIssueGovt) {
        this.passportIssueGovt = passportIssueGovt;
    }

}

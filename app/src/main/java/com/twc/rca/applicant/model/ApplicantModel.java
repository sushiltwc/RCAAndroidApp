package com.twc.rca.applicant.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sushil on 15-03-2018.
 */

public class ApplicantModel implements Parcelable {

    @SerializedName("applicantId")
    public String applicantId;

    @SerializedName("applicantSubmited")
    public String applicantSubmited;

    @SerializedName("applicantName")
    public String applicantName;

    @SerializedName("applicantType")
    public String applicantType;

    @SerializedName("applicantSurname")
    public String applicantSurname;

    @SerializedName("applicantGivenName")
    public String applicantGivenName;

    @SerializedName("applicantNationality")
    public String applicantNationality;

    @SerializedName("applicantGender")
    public String applicantGender;

    @SerializedName("applicantDOB")
    public String applicantDOB;

    @SerializedName("applicantPOB")
    public String applicantPOB;

    @SerializedName("applicantCOB")
    public String applicantCOB;

    @SerializedName("applicantMaritalStatus")
    public String applicantMaritalStatus;

    @SerializedName("applicantReligion")
    public String applicantReligion;

    @SerializedName("applicantLangSpoken")
    public String applicantLangSpoken;

    @SerializedName("applicantProfession")
    public String applicantProfession;

    @SerializedName("applicantFName")
    public String applicantFName;

    @SerializedName("applicantMName")
    public String applicantMName;

    @SerializedName("applicantHName")
    public String applicantHName;

    @SerializedName("applicantPPNo")
    public String applicantPPNo;

    @SerializedName("applicantPPType")
    public String applicantPPType;

    @SerializedName("applicantPPIssueGovt")
    public String applicantPPIssueGovt;

    @SerializedName("applicantPPIssuePlace")
    public String applicantPPIssuePlace;

    @SerializedName("applicantPPDOI")
    public String applicantPPDOI;

    @SerializedName("applicantPPDOE")
    public String applicantPPDOE;

    @SerializedName("applicantAddressLine1")
    public String applicantAddressLine1;

    @SerializedName("applicantAddressLine2")
    public String applicantAddressLine2;

    @SerializedName("applicantAddressLine3")
    public String applicantAddressLine3;

    @SerializedName("applicantCity")
    public String applicantCity;

    @SerializedName("applicantCountry")
    public String applicantCountry;

    @SerializedName("applicantTelephone")
    public String applicantTelephone;

    @SerializedName("applicantArrivalAirline")
    public String applicantArrivalAirline;

    @SerializedName("applicantArrivalFlightNo")
    public String applicantArrivalFlightNo;

    @SerializedName("applicantArrivalComingFrom")
    public String applicantArrivalComingFrom;

    @SerializedName("applicantDtOfArrival")
    public String applicantDtOfArrival;

    @SerializedName("applicantArrivalTimeHr")
    public String applicantArrivalTimeHr;

    @SerializedName("applicantArribalTimeMin")
    public String applicantArribalTimeMin;

    @SerializedName("applicantDeptAirline")
    public String applicantDeptAirline;

    @SerializedName("applicantDeptFlightNo")
    public String applicantDeptFlightNo;

    @SerializedName("applicantDeptLeavingTo")
    public String applicantDeptLeavingTo;

    @SerializedName("applicantDtOfDept")
    public String applicantDtOfDept;

    @SerializedName("applicantDeptTimeHr")
    public String applicantDeptTimeHr;

    @SerializedName("applicantDeptTimeMin")
    public String applicantDeptTimeMin;

    @SerializedName("applicantOderId")
    public String applicantOrderId;

    @SerializedName("applicantIsChild")
    public String applicantIsChild;

    public ApplicantModel(Parcel in) {
        applicantId = in.readString();
        applicantSubmited = in.readString();
        applicantName = in.readString();
        applicantType = in.readString();
        applicantGivenName = in.readString();
        applicantSurname = in.readString();
        applicantNationality = in.readString();
        applicantGender = in.readString();
        applicantPOB = in.readString();
        applicantCOB = in.readString();
        applicantDOB = in.readString();
        applicantMaritalStatus = in.readString();
        applicantReligion = in.readString();
        applicantLangSpoken = in.readString();
        applicantProfession = in.readString();
        applicantFName = in.readString();
        applicantMName = in.readString();
        applicantPPNo = in.readString();
        applicantPPType = in.readString();
        applicantPPIssueGovt = in.readString();
        applicantPPIssuePlace = in.readString();
        applicantPPDOI = in.readString();
        applicantPPDOE = in.readString();
        applicantAddressLine1 = in.readString();
        applicantAddressLine2 = in.readString();
        applicantAddressLine3 = in.readString();
        applicantCity = in.readString();
        applicantCountry = in.readString();
        applicantTelephone = in.readString();
        applicantArrivalAirline = in.readString();
        applicantArrivalFlightNo = in.readString();
        applicantArrivalComingFrom = in.readString();
        applicantDtOfArrival = in.readString();
        applicantArrivalTimeHr = in.readString();
        applicantArribalTimeMin = in.readString();
        applicantDeptAirline = in.readString();
        applicantDeptFlightNo = in.readString();
        applicantDeptLeavingTo = in.readString();
        applicantDtOfDept = in.readString();
        applicantDeptTimeHr = in.readString();
        applicantDeptTimeMin = in.readString();
        applicantHName = in.readString();
        applicantOrderId=in.readString();
        applicantIsChild=in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(applicantId);
        parcel.writeString(applicantSubmited);
        parcel.writeString(applicantName);
        parcel.writeString(applicantType);
        parcel.writeString(applicantGivenName);
        parcel.writeString(applicantSurname);
        parcel.writeString(applicantNationality);
        parcel.writeString(applicantGender);
        parcel.writeString(applicantPOB);
        parcel.writeString(applicantCOB);
        parcel.writeString(applicantDOB);
        parcel.writeString(applicantMaritalStatus);
        parcel.writeString(applicantReligion);
        parcel.writeString(applicantLangSpoken);
        parcel.writeString(applicantProfession);
        parcel.writeString(applicantFName);
        parcel.writeString(applicantMName);
        parcel.writeString(applicantPPNo);
        parcel.writeString(applicantPPType);
        parcel.writeString(applicantPPIssueGovt);
        parcel.writeString(applicantPPIssuePlace);
        parcel.writeString(applicantPPDOI);
        parcel.writeString(applicantPPDOE);
        parcel.writeString(applicantAddressLine1);
        parcel.writeString(applicantAddressLine2);
        parcel.writeString(applicantAddressLine3);
        parcel.writeString(applicantCity);
        parcel.writeString(applicantCountry);
        parcel.writeString(applicantTelephone);
        parcel.writeString(applicantArrivalAirline);
        parcel.writeString(applicantArrivalFlightNo);
        parcel.writeString(applicantArrivalComingFrom);
        parcel.writeString(applicantDtOfArrival);
        parcel.writeString(applicantArrivalTimeHr);
        parcel.writeString(applicantArribalTimeMin);
        parcel.writeString(applicantDeptAirline);
        parcel.writeString(applicantDeptFlightNo);
        parcel.writeString(applicantDeptLeavingTo);
        parcel.writeString(applicantDtOfDept);
        parcel.writeString(applicantDeptTimeHr);
        parcel.writeString(applicantDeptTimeMin);
        parcel.writeString(applicantHName);
        parcel.writeString(applicantOrderId);
        parcel.writeString(applicantIsChild);
    }

    public static final Creator<ApplicantModel> CREATOR = new Creator<ApplicantModel>() {
        @Override
        public ApplicantModel createFromParcel(Parcel in) {
            return new ApplicantModel(in);
        }

        @Override
        public ApplicantModel[] newArray(int size) {
            return new ApplicantModel[size];
        }
    };

    public ApplicantModel() {

    }


    //Personal Fields
    public String getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }

    public String getApplicantSurname() {
        return applicantSurname;
    }

    public void setApplicantSurname(String applicantSurname) {
        this.applicantSurname = applicantSurname;
    }

    public String getApplicantGivenName() {
        return applicantGivenName;
    }

    public void setApplicantGivenName(String applicantGivenName) {
        this.applicantGivenName = applicantGivenName;
    }

    public String getApplicantNationality() {
        return applicantNationality;
    }

    public void setApplicantNationality(String applicantNationality) {
        this.applicantNationality = applicantNationality;
    }

    public String getApplicantGender() {
        return applicantGender;
    }

    public void setApplicantGender(String applicantGender) {
        this.applicantGender = applicantGender;
    }

    public String getApplicantDOB() {
        return applicantDOB;
    }

    public void setApplicantDOB(String applicantDOB) {
        this.applicantDOB = applicantDOB;
    }

    public String getApplicantPOB() {
        return applicantPOB;
    }

    public void setApplicantPOB(String applicantPOB) {
        this.applicantPOB = applicantPOB;
    }

    public String getApplicantCOB() {
        return applicantCOB;
    }

    public void setApplicantCOB(String applicantCOB) {
        this.applicantCOB = applicantCOB;
    }

    public String getApplicantMaritalStatus() {
        return applicantMaritalStatus;
    }

    public void setApplicantMaritalStatus(String applicantMaritalStatus) {
        this.applicantMaritalStatus = applicantMaritalStatus;
    }

    public String getApplicantReligion() {
        return applicantReligion;
    }

    public void setApplicantReligion(String applicantReligion) {
        this.applicantReligion = applicantReligion;
    }

    public String getApplicantLangSpoken() {
        return applicantLangSpoken;
    }

    public void setApplicantLangSpoken(String applicantLangSpoken) {
        this.applicantLangSpoken = applicantLangSpoken;
    }

    public String getApplicantProfession() {
        return applicantProfession;
    }

    public void setApplicantProfession(String applicantProfession) {
        this.applicantProfession = applicantProfession;
    }

    public String getApplicantFName() {
        return applicantFName;
    }

    public void setApplicantFName(String applicantFName) {
        this.applicantFName = applicantFName;
    }

    public String getApplicantMName() {
        return applicantMName;
    }

    public void setApplicantMName(String applicantMName) {
        this.applicantMName = applicantMName;
    }

    public String getApplicantHName() {
        return applicantHName;
    }

    public void setApplicantHName(String applicantHName) {
        this.applicantHName = applicantHName;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getApplicantType() {
        return applicantType;
    }

    public void setApplicantType(String applicantType) {
        this.applicantType = applicantType;
    }

    //Passport Fields
    public String getApplicantPPNo() {
        return applicantPPNo;
    }

    public void setApplicantPPNo(String applicantPPNo) {
        this.applicantPPNo = applicantPPNo;
    }

    public String getApplicantPPType() {
        return applicantPPType;
    }

    public void setApplicantPPType(String applicantPPType) {
        this.applicantPPType = applicantPPType;
    }

    public String getApplicantPPIssueGovt() {
        return applicantPPIssueGovt;
    }

    public void setApplicantPPIssueGovt(String applicantPPIssueGovt) {
        this.applicantPPIssueGovt = applicantPPIssueGovt;
    }

    public String getApplicantPPIssuePlace() {
        return applicantPPIssuePlace;
    }

    public void setApplicantPPIssuePlace(String applicantPPIssuePlace) {
        this.applicantPPIssuePlace = applicantPPIssuePlace;
    }

    public String getApplicantPPDOI() {
        return applicantPPDOI;
    }

    public void setApplicantPPDOI(String applicantPPDOI) {
        this.applicantPPDOI = applicantPPDOI;
    }

    public String getApplicantPPDOE() {
        return applicantPPDOE;
    }

    public void setApplicantPPDOE(String applicantPPDOE) {
        this.applicantPPDOE = applicantPPDOE;
    }

    //Applicant Contact Fields
    public String getApplicantAddressLine1() {
        return applicantAddressLine1;
    }

    public void setApplicantAddressLine1(String applicantAddressLine1) {
        this.applicantAddressLine1 = applicantAddressLine1;
    }

    public String getApplicantAddressLine2() {
        return applicantAddressLine2;
    }

    public void setApplicantAddressLine2(String applicantAddressLine2) {
        this.applicantAddressLine2 = applicantAddressLine2;
    }

    public String getApplicantAddressLine3() {
        return applicantAddressLine3;
    }

    public void setApplicantAddressLine3(String applicantAddressLine3) {
        this.applicantAddressLine3 = applicantAddressLine3;
    }

    public String getApplicantCity() {
        return applicantCity;
    }

    public void setApplicantCity(String applicantCity) {
        this.applicantCity = applicantCity;
    }

    public String getApplicantCountry() {
        return applicantCountry;
    }

    public void setApplicantCountry(String applicantCountry) {
        this.applicantCountry = applicantCountry;
    }

    public String getApplicantTelephone() {
        return applicantTelephone;
    }

    public void setApplicantTelephone(String applicantTelephone) {
        this.applicantTelephone = applicantTelephone;
    }


    //Applicant Travel Fields;

    public String getApplicantArrivalAirline() {
        return applicantArrivalAirline;
    }

    public void setApplicantArrivalAirline(String applicantArrivalAirline) {
        this.applicantArrivalAirline = applicantArrivalAirline;
    }

    public String getApplicantArrivalFlightNo() {
        return applicantArrivalFlightNo;
    }

    public void setApplicantArrivalFlightNo(String applicantArrivalFlightNo) {
        this.applicantArrivalFlightNo = applicantArrivalFlightNo;
    }

    public String getApplicantArrivalComingFrom() {
        return applicantArrivalComingFrom;
    }

    public void setApplicantArrivalComingFrom(String applicantArrivalComingFrom) {
        this.applicantArrivalComingFrom = applicantArrivalComingFrom;
    }

    public String getApplicantDtOfArrival() {
        return applicantDtOfArrival;
    }

    public void setApplicantDtOfArrival(String applicantDtOfArrival) {
        this.applicantDtOfArrival = applicantDtOfArrival;
    }

    public String getApplicantArrivalTimeHr() {
        return applicantArrivalTimeHr;
    }

    public void setApplicantArrivalTimeHr(String applicantArrivalTimeHr) {
        this.applicantArrivalTimeHr = applicantArrivalTimeHr;
    }

    public String getApplicantArribalTimeMin() {
        return applicantArribalTimeMin;
    }

    public void setApplicantArribalTimeMin(String applicantArribalTimeMin) {
        this.applicantArribalTimeMin = applicantArribalTimeMin;
    }

    public String getApplicantDeptAirline() {
        return applicantDeptAirline;
    }

    public void setApplicantDeptAirline(String applicantDeptAirline) {
        this.applicantDeptAirline = applicantDeptAirline;
    }

    public String getApplicantDeptFlightNo() {
        return applicantDeptFlightNo;
    }

    public void setApplicantDeptFlightNo(String applicantDeptFlightNo) {
        this.applicantDeptFlightNo = applicantDeptFlightNo;
    }

    public String getApplicantDeptLeavingTo() {
        return applicantDeptLeavingTo;
    }

    public void setApplicantDeptLeavingTo(String applicantDeptLeavingTo) {
        this.applicantDeptLeavingTo = applicantDeptLeavingTo;
    }

    public String getApplicantDtOfDept() {
        return applicantDtOfDept;
    }

    public void setApplicantDtOfDept(String applicantDtOfDept) {
        this.applicantDtOfDept = applicantDtOfDept;
    }

    public String getApplicantDeptTimeHr() {
        return applicantDeptTimeHr;
    }

    public void setApplicantDeptTimeHr(String applicantDeptTimeHr) {
        this.applicantDeptTimeHr = applicantDeptTimeHr;
    }

    public String getApplicantDeptTimeMin() {
        return applicantDeptTimeMin;
    }

    public void setApplicantDeptTimeMin(String applicantDeptTimeMin) {
        this.applicantDeptTimeMin = applicantDeptTimeMin;
    }

    public String getApplicantSubmited() {
        return applicantSubmited;
    }

    public void setApplicantSubmited(String applicantSubmited) {
        this.applicantSubmited = applicantSubmited;
    }

    public String getApplicantOrderId() {
        return applicantOrderId;
    }

    public void setApplicantOrderId(String applicantOrderId) {
        this.applicantOrderId = applicantOrderId;
    }

    public String getApplicantIsChild() {
        return applicantIsChild;
    }

    public void setApplicantIsChild(String applicantIsChild) {
        this.applicantIsChild = applicantIsChild;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}

package com.twc.rca.product.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sushil on 14-05-2018.
 */

public class OrderModel implements Parcelable {

    @SerializedName("order_id")
    public String order_id;

    @SerializedName("user_id")
    public String user_id;

    @SerializedName("product_id")
    public String product_id;

    @SerializedName("product_type")
    public String product_type;

    @SerializedName("product_validity")
    public String product_validity;

    @SerializedName("product_name")
    public String product_name;

    @SerializedName("adult")
    public String adult_count;

    @SerializedName("child")
    public String child_count;

    @SerializedName("infant")
    public String infant_count;

    @SerializedName("nationality")
    public String nationality;

    @SerializedName("total_price")
    public String total_price;

    @SerializedName("travel_date")
    public String travel_date;

    @SerializedName("arrival_date")
    public String arrival_date;

    @SerializedName("arrival_time")
    public String arrival_time;

    @SerializedName("departure_date")
    public String dept_date;

    @SerializedName("departure_time")
    public String dept_time;

    @SerializedName("arriving_airport")
    public String arriving_airport;

    @SerializedName("departing_airport")
    public String departing_airport;

    @SerializedName("arrival_airline")
    public String arrival_airline;

    @SerializedName("departure_airline")
    public String departure_airline;

    @SerializedName("airport_coming_from")
    public String airport_coming_from;

    @SerializedName("airport_going_to")
    public String airport_going_to;

    @SerializedName("arrival_flight_no")
    public String arrival_flight_no;

    @SerializedName("departure_flight_no")
    public String departure_flight_no;

    @SerializedName("applicant_booking_status")
    public String applicant_booking_status;

    @SerializedName("payment_status")
    public String payment_status;

    public static final Creator<OrderModel> CREATOR = new Creator<OrderModel>() {
        @Override
        public OrderModel createFromParcel(Parcel in) {
            return new OrderModel(in);
        }

        @Override
        public OrderModel[] newArray(int size) {
            return new OrderModel[size];
        }
    };

    public OrderModel(Parcel in) {
        order_id = in.readString();
        user_id = in.readString();
        product_id = in.readString();
        product_name = in.readString();
        product_type = in.readString();
        product_validity = in.readString();
        adult_count = in.readString();
        child_count = in.readString();
        infant_count = in.readString();
        total_price = in.readString();
        travel_date = in.readString();
        arrival_date = in.readString();
        arrival_time = in.readString();
        dept_date = in.readString();
        dept_time = in.readString();
        arriving_airport = in.readString();
        departing_airport = in.readString();
        arrival_airline = in.readString();
        departure_airline = in.readString();
        airport_coming_from = in.readString();
        airport_going_to = in.readString();
        arrival_flight_no = in.readString();
        departure_flight_no = in.readString();
        applicant_booking_status = in.readString();
        payment_status = in.readString();
    }

    public OrderModel() {
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getProduct_validity() {
        return product_validity;
    }

    public void setProduct_validity(String product_validity) {
        this.product_validity = product_validity;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getAdult_count() {
        return adult_count;
    }

    public void setAdult_count(String adult_count) {
        this.adult_count = adult_count;
    }

    public String getChild_count() {
        return child_count;
    }

    public void setChild_count(String child_count) {
        this.child_count = child_count;
    }

    public String getInfant_count() {
        return infant_count;
    }

    public void setInfant_count(String infant_count) {
        this.infant_count = infant_count;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getTravel_date() {
        return travel_date;
    }

    public void setTravel_date(String travel_date) {
        this.travel_date = travel_date;
    }

    public String getArrival_date() {
        return arrival_date;
    }

    public void setArrival_date(String arrival_date) {
        this.arrival_date = arrival_date;
    }

    public String getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(String arrival_time) {
        this.arrival_time = arrival_time;
    }

    public String getDept_date() {
        return dept_date;
    }

    public void setDept_date(String dept_date) {
        this.dept_date = dept_date;
    }

    public String getDept_time() {
        return dept_time;
    }

    public void setDept_time(String dept_time) {
        this.dept_time = dept_time;
    }

    public String getArriving_airport() {
        return arriving_airport;
    }

    public void setArriving_airport(String arriving_airport) {
        this.arriving_airport = arriving_airport;
    }

    public String getDeparting_airport() {
        return departing_airport;
    }

    public void setDeparting_airport(String departing_airport) {
        this.departing_airport = departing_airport;
    }

    public String getArrival_airline() {
        return arrival_airline;
    }

    public void setArrival_airline(String arrival_airline) {
        this.arrival_airline = arrival_airline;
    }

    public String getDeparture_airline() {
        return departure_airline;
    }

    public void setDeparture_airline(String departure_airline) {
        this.departure_airline = departure_airline;
    }

    public String getAirport_coming_from() {
        return airport_coming_from;
    }

    public void setAirport_coming_from(String airport_coming_from) {
        this.airport_coming_from = airport_coming_from;
    }

    public String getAirport_going_to() {
        return airport_going_to;
    }

    public void setAirport_going_to(String airport_going_to) {
        this.airport_going_to = airport_going_to;
    }

    public String getArrival_flight_no() {
        return arrival_flight_no;
    }

    public void setArrival_flight_no(String arrival_flight_no) {
        this.arrival_flight_no = arrival_flight_no;
    }

    public String getDeparture_flight_no() {
        return departure_flight_no;
    }

    public void setDeparture_flight_no(String departure_flight_no) {
        this.departure_flight_no = departure_flight_no;
    }

    public String getApplicant_booking_status() {
        return applicant_booking_status;
    }

    public void setApplicant_booking_status(String applicant_booking_status) {
        this.applicant_booking_status = applicant_booking_status;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(order_id);
        parcel.writeString(user_id);
        parcel.writeString(product_id);
        parcel.writeString(product_name);
        parcel.writeString(product_type);
        parcel.writeString(product_validity);
        parcel.writeString(adult_count);
        parcel.writeString(child_count);
        parcel.writeString(infant_count);
        parcel.writeString(total_price);
        parcel.writeString(travel_date);
        parcel.writeString(arrival_date);
        parcel.writeString(arrival_time);
        parcel.writeString(dept_date);
        parcel.writeString(dept_time);
        parcel.writeString(arriving_airport);
        parcel.writeString(departing_airport);
        parcel.writeString(arrival_airline);
        parcel.writeString(departure_airline);
        parcel.writeString(airport_coming_from);
        parcel.writeString(airport_going_to);
        parcel.writeString(arrival_flight_no);
        parcel.writeString(departure_flight_no);
        parcel.writeString(applicant_booking_status);
        parcel.writeString(payment_status);
    }
}

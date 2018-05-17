package com.twc.rca.product.task;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.twc.rca.database.CountryHelper;
import com.twc.rca.utils.ApiUtils;
import com.twc.rca.utils.ILog;
import com.twc.rca.utils.PreferenceUtils;
import com.twc.rca.volley.utils.NetworkErrorHelper;
import com.twc.rca.volley.utils.VolleySingleTon;

import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Sushil on 20-04-2018.
 */

public class OrderTask extends ApiUtils {

    protected static final String TAG = OrderTask.class.getSimpleName();

    Context context;

    public static String METHOD_NAME = "order_creation";

    public static String PRODUCT_ID = "product_id", USER_ID = "user_id", ADULT_COUNT = "adult", CHILD_COUNT = "child", INFANT_COUNT = "infant", TOTAL_PRICE = "total_price", NATIONALITY = "nationality", TRAVEL_DATE = "travel_date", ARRIVAL_DATE = "arrival_date", ARRIVAL_TIME = "arrival_time",
            DEPARTURE_DATE = "departure_date", DEPARTURE_TIME = "departure_time", ARRIVING_AIRPORT = "arriving_airport", DEPARTING_AIRPORT = "departing_airport", AIRLINE = "airline", ADD_ON_SERVICE = "addon_service",
            HOURS = "hours",ARRIVAL_AIRLINE="arrival_airline",ARRIVAL_FLIGHT_NO="arrival_flight_no",DEPARTURE_AIRLINE="departure_airline",DEPARTURE_FLIGHT_NO="departure_flight_no", AIRPORT_COMING_FROM = "airport_coming_from", AIRPORT_GOING_TO = "airport_going_to";

    String productId, nationality, travel_date, arrival_date, arrival_time, departure_date, departure_time, arriving_airport, departure_airport, arriving_airline,arriving_flight_no,departure_airline,departure_flight_no, add_on_service, airport_coming_from, airport_going_to;

    int adult_count, child_count, infant_count, hours;

    double total_price;

    public OrderTask(Context context, String productId, int adult_count, int child_count, int infant_count, double total_price, String travel_date, String nationality,
                     String arrival_date, String arrival_time, String departure_date, String departure_time, String arriving_airport, String departure_airport,
                     String arriving_airline,String arriving_flight_no,String dept_airline,String dept_flight_no, String add_on_service, int hours, String airport_coming_from, String airport_going_to) {
        this.context = context;
        this.productId = productId;
        this.adult_count = adult_count;
        this.child_count = child_count;
        this.infant_count = infant_count;
        this.total_price = total_price;
        this.nationality = nationality;
        this.travel_date = travel_date;
        this.arrival_date = arrival_date;
        this.arrival_time = arrival_time;
        this.departure_date = departure_date;
        this.departure_time = departure_time;
        this.arriving_airport = arriving_airport;
        this.departure_airport = departure_airport;
        this.arriving_airline = arriving_airline;
        this.arriving_flight_no=arriving_flight_no;
        this.departure_airline=dept_airline;
        this.departure_flight_no=dept_flight_no;
        this.add_on_service = add_on_service;
        this.hours = hours;
        this.airport_coming_from = airport_coming_from;
        this.airport_going_to = airport_going_to;
    }

    public void createOrder(final OrderTask.OrderResponseCallback orderResponseCallback) {
        JsonObjectRequest orderRequest = new JsonObjectRequest(Request.Method.POST, BASE_APP_URL, new JSONObject(getParams(context)), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                ILog.d(TAG, response.toString());
                if (isValidResponse(response.toString()))
                    orderResponseCallback.onSuccessOrderResponse(response.toString());
                else
                    orderResponseCallback.onFailureOrderResponse(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ILog.d(TAG, error.toString());
                orderResponseCallback.onFailureOrderResponse(NetworkErrorHelper.getErrorStatus(error).getErrorMessage());
            }
        });
        VolleySingleTon.getInstance(context).addToRequestQueue(orderRequest);
        orderRequest.setRetryPolicy(new DefaultRetryPolicy(25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    protected Map<String, Object> getParams(Context context) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put(ACCESS_TOKEN, PreferenceUtils.getAccessToken(context));
        map.put(USER_ID, PreferenceUtils.getUserid(context));
        map.put(PRODUCT_ID, productId);
        map.put(ADULT_COUNT, adult_count);
        map.put(CHILD_COUNT, child_count);
        map.put(INFANT_COUNT, infant_count);
        map.put(NATIONALITY, CountryHelper.getInstance(context).getCountryId(nationality.toUpperCase()));
        map.put(TOTAL_PRICE, "1.0");
        map.put(TRAVEL_DATE, ApiUtils.formatDate(travel_date));
        map.put(ARRIVAL_DATE, ApiUtils.formatDate(arrival_date));
        map.put(ARRIVAL_TIME, arrival_time);
        map.put(DEPARTURE_DATE, ApiUtils.formatDate(departure_date));
        map.put(DEPARTURE_TIME, departure_time);
        map.put(ARRIVING_AIRPORT, arriving_airport);
        map.put(DEPARTING_AIRPORT, departure_airport);
        map.put(ARRIVAL_AIRLINE, arriving_airline);
        map.put(ARRIVAL_FLIGHT_NO,arriving_flight_no);
        map.put(DEPARTURE_AIRLINE,departure_airline);
        map.put(DEPARTURE_FLIGHT_NO,departure_flight_no);
        map.put(ADD_ON_SERVICE, add_on_service);
        map.put(HOURS, hours);
        map.put(AIRPORT_COMING_FROM, airport_coming_from);
        map.put(AIRPORT_GOING_TO, airport_going_to);
        map.put(ApiUtils.METHOD, METHOD_NAME);
        return map;
    }

    public interface OrderResponseCallback {
        void onSuccessOrderResponse(String response);

        void onFailureOrderResponse(String response);
    }

}

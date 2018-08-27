package com.example.hung.fparkingowner.config;

public class Constants {
//    public static final String API_URL = "http://192.168.1.62:9000/api/";
public static final String API_URL = "http://52.148.83.12:8080/api/";
    public static final String PUSHER_KEY = "d8e15d0b0ecad0c93a5e";
    public static final String PUSHER_CHANNEL = "Fparking";
    public static final String PUSHER_ORDER_FROM_DRIVER = "order";
    public static final String PUSHER_CHECKIN_FROM_DRIVER = "checkin";
    public static final String PUSHER_CHECKOUT_FROM_DRIVER = "checkout";
    public static final String PUSHER_CANCEL_FROM_DRIVER = "cancel";

    //Phone Validation pattern
    public static final String regEx = "(09|01[2|6|8|9])+([0-9]{8})\\b";
    public static final int PICK_CONTACT_REQUEST = 1;
}

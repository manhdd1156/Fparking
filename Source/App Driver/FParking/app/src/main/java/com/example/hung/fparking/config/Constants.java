package com.example.hung.fparking.config;

public class Constants {
    public static final String API_URL = "http://192.168.119.230:9000/tat/";
    public static final String PUSHER_KEY = "d8e15d0b0ecad0c93a5e";
    public static final String PUSHER_CHANNEL = "Fparking";
    public static final String PUSHER_ORDER_FOR_BOOKING = "ORDER_FOR_BOOKING";
    public static final String PUSHER_CHECKIN_FOR_BOOKING = "CHECKIN_FOR_BOOKING";
    public static final String PUSHER_CHECKOUT_FOR_BOOKING = "CHECKOUT_FOR_BOOKING";
    public static final String PUSHER_CANCEL_FROM_DRIVER = "CANCEL_FROM_DRIVER";

    //Phone Validation pattern
    public static final String regEx = "(09|01[2|6|8|9])+([0-9]{8})\\b";
    //Fragments Tags
    public static final String Login_Fragment = "Login_Fragment";
    public static final String SignUp_Fragment = "SignUp_Fragment";
    public static final String ForgotPassword_Fragment = "ForgotPassword_Fragment";
    public static final String ForgotPasswordOtp_Fragment = "ForgotPasswordOtp_Fragment";
    public static final String ConfirmRegister_Fragment = "ConfirmRegister_Fragment";
}

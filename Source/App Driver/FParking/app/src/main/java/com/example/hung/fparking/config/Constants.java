package com.example.hung.fparking.config;

public class Constants {
    public static final String API_URL = "http://52.148.83.12:8080/api/";
    public static final String PUSHER_KEY = "d8e15d0b0ecad0c93a5e";
    public static final String PUSHER_CHANNEL = "Fparking";
    public static final String PUSHER_ORDER_FOR_BOOKING = "order";
    public static final String PUSHER_CHECKIN_FOR_BOOKING = "checkin";
    public static final String PUSHER_CHECKOUT_FOR_BOOKING = "checkout";
    public static final String PUSHER_CANCEL_FROM_STAFF = "cancel";

    //Phone Validation pattern
    public static final String regEx = "^(09|01[2|6|8|9])+([0-9]{8})$";
    public static final String regBs = "([0-9]{2})+([a-zA-Z]{1})+([0-9]{1})\\b";
    public static final String regName = "^[a-z A-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ]{2,50}$";

}

package com.example.hung.fparking.config;

import android.app.Activity;
import android.content.SharedPreferences;

import com.example.hung.fparking.asynctask.IAsyncTaskHandler;
import com.example.hung.fparking.dto.BookingDTO;
import com.example.hung.fparking.dto.DriverDTO;
import com.example.hung.fparking.dto.ParkingDTO;
import com.example.hung.fparking.dto.StaffDTO;
import com.pusher.client.Pusher;
import com.pusher.client.channel.Channel;

public class Session {
    public static StaffDTO currentStaff;
    public static ParkingDTO currentParking;
    public static Pusher pusher;
    public static Channel channel;
    public static SharedPreferences spref;
    public static Activity homeActivity;
    public static BookingDTO bookingTemp;
    public static IAsyncTaskHandler container;
}

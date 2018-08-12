package com.example.hung.fparkingowner.config;

import android.app.Activity;
import android.content.SharedPreferences;

import com.example.hung.fparkingowner.asynctask.IAsyncTaskHandler;
import com.example.hung.fparkingowner.dto.ParkingDTO;
import com.example.hung.fparkingowner.dto.OwnerDTO;
import com.pusher.client.Pusher;
import com.pusher.client.channel.Channel;

public class Session {
    public static OwnerDTO currentOwner;
    public static ParkingDTO currentParking;
    public static Pusher pusher;
    public static Channel channel;
    public static SharedPreferences spref;
    public static Activity homeActivity;
    public static IAsyncTaskHandler container;
}

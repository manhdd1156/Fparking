package com.example.hung.myapplication.config;

import com.example.hung.myapplication.dto.StaffDTO;
import com.pusher.client.Pusher;
import com.pusher.client.channel.Channel;


/**
 * Created by DungNA on 10/20/17.
 */

public class Session {
    public static StaffDTO currentStaff;
    public static Pusher pusher;
    public static Channel channel;
}

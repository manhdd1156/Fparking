package com.example.hung.fparking.config;

import com.example.hung.fparking.dto.DriverDTO;
import com.pusher.client.Pusher;
import com.pusher.client.channel.Channel;

public class Session {
    public static DriverDTO currentDriver;
    public static Pusher pusher;
    public static Channel channel;
}

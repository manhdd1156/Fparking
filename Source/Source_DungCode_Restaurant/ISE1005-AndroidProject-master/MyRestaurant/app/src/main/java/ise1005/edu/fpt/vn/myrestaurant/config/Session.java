package ise1005.edu.fpt.vn.myrestaurant.config;

import com.pusher.client.Pusher;
import com.pusher.client.channel.Channel;

import ise1005.edu.fpt.vn.myrestaurant.dto.UserDTO;

/**
 * Created by DungNA on 10/20/17.
 */

public class Session {
    public static UserDTO currentUser;
    public static Pusher pusher;
    public static Channel channel;
}

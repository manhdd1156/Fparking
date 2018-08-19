package com.example.hung.fparking.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootCompleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
            Intent pushIntent = new Intent(context, Notification.class);
            context.startService(pushIntent);
    }
}

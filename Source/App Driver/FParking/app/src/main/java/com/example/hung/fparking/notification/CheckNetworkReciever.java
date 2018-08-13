package com.example.hung.fparking.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.hung.fparking.asynctask.BookingTask;
import com.example.hung.fparking.asynctask.DriverLoginTask;
import com.example.hung.fparking.asynctask.IAsyncTaskHandler;
import com.example.hung.fparking.config.Constants;
import com.example.hung.fparking.config.Session;
import com.example.hung.fparking.dto.BookingDTO;
import com.pusher.client.Pusher;

import java.util.ArrayList;

public class CheckNetworkReciever extends BroadcastReceiver implements IAsyncTaskHandler {
    ArrayList<BookingDTO> booking;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mPreferencesEditor;

    @Override
    public void onReceive(Context context, Intent intent) {
        mPreferences = context.getSharedPreferences("driver", 0);
        mPreferencesEditor = mPreferences.edit();
        Session.spref = context.getSharedPreferences("intro", 0);
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetInfo != null && activeNetInfo.isConnectedOrConnecting();
        if (isConnected) {
            if (!Session.spref.getString("driverid", "").equals("")) {
                new BookingTask("newbooking", Session.spref.getString("driverid", "") + "", "", "newbooking", this);
                new DriverLoginTask("second_time", null, "", new IAsyncTaskHandler() {
                    @Override
                    public void onPostExecute(Object o, String action) {
                    }
                });
            }
        }
    }

    @Override
    public void onPostExecute(Object o, String action) {
        if (Boolean.TRUE.equals(o)) {
            booking = (ArrayList<BookingDTO>) o;
            BookingDTO bookingDTO = booking.get(0);
            if (bookingDTO.getStatus() != 0 && bookingDTO.getStatus() != 3) {
                mPreferencesEditor.putString("drivervehicleID", bookingDTO.getDriverVehicleID() + "");
                mPreferencesEditor.putString("vehicleID", bookingDTO.getVehicleID() + "");
                mPreferencesEditor.putString("parkingID", bookingDTO.getParkingID() + "");
                mPreferencesEditor.putInt("status", bookingDTO.getStatus());
                mPreferencesEditor.putString("bookingid", bookingDTO.getBookingID() + "").commit();
            } else {
                mPreferencesEditor.clear().commit();
            }
        }
    }
}

package com.example.hung.fparking.asynctask;

import android.content.SharedPreferences;
import android.media.AudioManager;

import com.example.hung.fparking.BuildConfig;
import com.example.hung.fparking.HomeActivity;
import com.example.hung.fparking.OrderParking;
import com.example.hung.fparking.ParkingHistory;
import com.example.hung.fparking.config.Session;
import com.example.hung.fparking.dto.BookingDTO;
import com.example.hung.fparking.dto.DriverDTO;
import com.example.hung.fparking.login.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class HomeActivityTest {
    private ParkingHistory mActivity;
    SharedPreferences mPreferences;

    @Before
    public void setUp() throws Exception {
//        mActivity = Robolectric.buildActivity(ParkingHistory.class).create().resume().get();
//        Session.spref = mActivity.getSharedPreferences("intro", 0);
////        mPreferences = mActivity.getSharedPreferences("driver", 0);
////        mPreferences.edit().putString("parkingID", "1").commit();
//
//
//        Session.spref.edit().putString("token", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ7XCJ0eXBlXCI6XCJEUklWRVJcIixcImlkXCI6M30iLCJpYXQiOjE1MzQ0Mjc2NDQsImV4cCI6MTUzNDgxNDA0NH0.6BP6hd4TuaUGek6ThKI0walbxy5Xii6rKkVLN_zORzYleJbgVmvePKIBbxvshrnaScRWcoRN-oT5Y8egXHpMXw").commit();
    }

    @Test(expected = RuntimeException.class)
    public void checkdActivityData() {
        new BookingTask("phone", 31000 + "", "", "", new IAsyncTaskHandler() {
            @Override
            public void onPostExecute(Object o, String action) {
                ArrayList<BookingDTO> bookingDTOS = new ArrayList<>();
                assertEquals(bookingDTOS.size() + "", "", "d ");
            }
        });

    }
}
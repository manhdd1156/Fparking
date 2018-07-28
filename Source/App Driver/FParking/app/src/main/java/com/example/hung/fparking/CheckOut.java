package com.example.hung.fparking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hung.fparking.asynctask.BookingTask;
import com.example.hung.fparking.asynctask.IAsyncTaskHandler;
import com.example.hung.fparking.asynctask.NotificationTask;
import com.example.hung.fparking.dto.BookingDTO;
import com.example.hung.fparking.notification.Notification;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CheckOut extends AppCompatActivity implements IAsyncTaskHandler {

    TextView textViewAddress, textViewCheckIn, textViewPrice, textViewLicensePlate, textViewTotalPrice;
    Button buttonCheckOut;

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mPreferencesEditor;

    ArrayList<BookingDTO> booking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        // khởi tạo shareRePreference
        mPreferences = getSharedPreferences("driver", 0);
        mPreferencesEditor = mPreferences.edit();

        // ánh xạ
        textViewAddress = findViewById(R.id.textViewAddress);
        textViewCheckIn = findViewById(R.id.textViewCheckinTime);
        textViewPrice = findViewById(R.id.textViewPrice);
        textViewLicensePlate = findViewById(R.id.textViewLicensePlate);
        textViewTotalPrice = findViewById(R.id.textViewTotalPrice);
        buttonCheckOut = findViewById(R.id.buttonCheckout);

        // set text cho button theo data
        int status = mPreferences.getInt("status", 8);
        if (status == 3) {
            buttonCheckOut.setText("QUAY VỀ");
        }

        buttonCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonCheckOut.getText().equals("QUAY VỀ")) {
                    mPreferencesEditor.clear().commit();
                    Intent intentHome = new Intent(CheckOut.this, HomeActivity.class);
                    startActivity(intentHome);
                } else {
                    new NotificationTask("checkout", mPreferences.getString("vehicleID", ""), mPreferences.getString("parkingID", ""), "", CheckOut.this);
                }
            }
        });

        new BookingTask("bookingID", mPreferences.getString("bookingid", ""), "", "", this);
    }

    @Override
    public void onPostExecute(Object o, String action) {
        booking = (ArrayList<BookingDTO>) o;
        if (booking.size() > 0) {
            BookingDTO myBookingDTO = booking.get(0);

            final DateFormat df = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
            try {
                Date date1 = df.parse(myBookingDTO.getTimeIn());
                Date date2 = df.parse(myBookingDTO.getTimeOut());
                long diff = date2.getTime() - date1.getTime();
                textViewCheckIn.setText(myBookingDTO.getTimeIn() + " - " + myBookingDTO.getTimeOut());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            textViewAddress.setText(myBookingDTO.getAddress());
            textViewTotalPrice.setText(myBookingDTO.getAmount() + "");
            textViewPrice.setText(myBookingDTO.getPrice() + "");
            textViewLicensePlate.setText(myBookingDTO.getLicenseplate());
        }
    }
}

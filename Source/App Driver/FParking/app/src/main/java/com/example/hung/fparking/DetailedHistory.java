package com.example.hung.fparking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hung.fparking.asynctask.BookingTask;
import com.example.hung.fparking.asynctask.IAsyncTaskHandler;
import com.example.hung.fparking.dto.BookingDTO;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DetailedHistory extends AppCompatActivity implements IAsyncTaskHandler {

    TextView textViewAddress, textViewTime, textViewPrice, textViewLicensePlate, textViewTotalPrice, textViewType;
    ArrayList<BookingDTO> booking;
    ImageView backDetailedHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_history);

        // ánh xạ
        textViewLicensePlate = findViewById(R.id.textBS);
        textViewType = findViewById(R.id.textViewLS);
        textViewTotalPrice = findViewById(R.id.textViewAM);
        textViewAddress = findViewById(R.id.textViewAD);
        textViewTime = findViewById(R.id.textViewTM);
        backDetailedHistory = findViewById(R.id.imageViewBackDetailedHistory);

        backDetailedHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHistory = new Intent(DetailedHistory.this, ParkingHistory.class);
                startActivity(intentHistory);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        // get data intent
        Intent intent = getIntent();
        int bookingid = intent.getIntExtra("bookingid", 0);

        new BookingTask("bookingID", bookingid + "", "", "", this);

    }

    @Override
    public void onPostExecute(Object o, String action) {
        booking = (ArrayList<BookingDTO>) o;
        if (booking.size() > 0) {
            BookingDTO myBookingDTO = booking.get(0);

            textViewLicensePlate.setText(myBookingDTO.getLicenseplate());
            textViewType.setText(myBookingDTO.getType());
            textViewTotalPrice.setText(myBookingDTO.getAmount() + "");
            textViewAddress.setText(myBookingDTO.getAddress());
            textViewTime.setText(myBookingDTO.getTimeIn() + " - " + myBookingDTO.getTimeOut());
        }
    }
}

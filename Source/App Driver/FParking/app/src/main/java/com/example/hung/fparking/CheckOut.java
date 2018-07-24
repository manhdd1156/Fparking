package com.example.hung.fparking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.hung.fparking.asynctask.BookingTask;
import com.example.hung.fparking.asynctask.IAsyncTaskHandler;
import com.example.hung.fparking.dto.BookingDTO;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CheckOut extends AppCompatActivity implements IAsyncTaskHandler {

    TextView textViewAddress, textViewCheckIn, textViewPrice, textViewLicensePlate, textViewTotalPrice;
    Button buttonCheckOut;

    ArrayList<BookingDTO> booking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        // ánh xạ
        textViewAddress = findViewById(R.id.textViewAddress);
        textViewCheckIn = findViewById(R.id.textViewCheckinTime);
        textViewPrice = findViewById(R.id.textViewPrice);
        textViewLicensePlate = findViewById(R.id.textViewLicensePlate);
        textViewTotalPrice = findViewById(R.id.textViewTotalPrice);
        buttonCheckOut = findViewById(R.id.buttonCheckout);

        new BookingTask("bookingID", "2", "", this);
    }

    @Override
    public void onPostExecute(Object o, String action) {
        booking = (ArrayList<BookingDTO>) o;
        if (booking.size() > 0) {
            BookingDTO myBookingDTO = booking.get(0);

            final DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSZ");
            try {
                Date date1 = df.parse(myBookingDTO.getTimeIn());
                Date date2 = df.parse(myBookingDTO.getTimeOut());
                long diff = date2.getTime() - date1.getTime();
                textViewCheckIn.setText(date2 + "");
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

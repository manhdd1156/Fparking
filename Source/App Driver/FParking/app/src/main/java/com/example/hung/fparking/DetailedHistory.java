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
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DetailedHistory extends AppCompatActivity implements IAsyncTaskHandler {

    TextView textViewAddress, textViewTime, textViewPrice, textViewLicensePlate, textViewTotalPrice, textViewType, textViewTimeCheckinPH, textViewTimeCheckoutPH;
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
        textViewTime = findViewById(R.id.textViewTotalTimePH);
        backDetailedHistory = findViewById(R.id.imageViewBackDetailedHistory);
        textViewTimeCheckinPH = findViewById(R.id.textViewTimeCheckinPH);
        textViewTimeCheckoutPH = findViewById(R.id.textViewTimeCheckoutPH);

        backDetailedHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHistory = new Intent(DetailedHistory.this, ParkingHistory.class);
                startActivity(intentHistory);
                finish();
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
            NumberFormat currencyVN = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            textViewTotalPrice.setText( currencyVN.format(myBookingDTO.getAmount()).toString());
            textViewAddress.setText(myBookingDTO.getAddress());
            textViewTimeCheckinPH.setText(myBookingDTO.getTimeIn());
            textViewTimeCheckoutPH.setText(myBookingDTO.getTimeOut());

            final DateFormat df = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
            try {
                Date date1 = df.parse(myBookingDTO.getTimeIn());
                Date date2 = df.parse(myBookingDTO.getTimeOut());
                long diff = date2.getTime() - date1.getTime();
                textViewTime.setText(diff/(60*60*1000)+" Giờ");
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, ParkingHistory.class));
        finish();
    }
}

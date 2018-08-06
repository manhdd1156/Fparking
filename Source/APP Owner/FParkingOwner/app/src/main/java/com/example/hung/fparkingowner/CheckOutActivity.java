package com.example.hung.fparkingowner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hung.fparking.R;
import com.example.hung.fparkingowner.asynctask.GetVehicleTask;
import com.example.hung.fparkingowner.asynctask.IAsyncTaskHandler;
import com.example.hung.fparkingowner.asynctask.ManagerBookingTask;
import com.example.hung.fparkingowner.config.Session;
import com.example.hung.fparkingowner.dto.BookingDTO;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CheckOutActivity extends Activity implements OnClickListener,IAsyncTaskHandler {

    Button ok_btn, cancel_btn;
    TextView tvLisencePlate,tvTimeInOut,tvTotalTime,tvPrice,tvTotalPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_check_out);
        tvLisencePlate = (TextView) this.findViewById(R.id.tvLicensePlate);
        tvTimeInOut = (TextView) this.findViewById(R.id.tvTimeInout);
        tvPrice = (TextView) this.findViewById(R.id.tvPrice);
        tvTotalPrice = (TextView) this.findViewById(R.id.tvTotalPrice);
        ok_btn = (Button) findViewById(R.id.btnCheckOut);

        ok_btn.setOnClickListener(this);
//        cancel_btn.setOnClickListener(this);
//        Intent intent = getIntent();
//        String eventName = intent.getStringExtra("eventName");
//        String data = intent.getStringExtra("data");
//        handleMessage(eventName,data);
//        this.setFinishOnTouchOutside(false);

        BookingDTO b = new BookingDTO();
        b.setParkingID(Session.currentParking.getId());
        b.setStatus(3);
//        new ManagerBookingTask("updatebystatus",b, null);
        new ManagerBookingTask("getbystatus",b, CheckOutActivity.this);
    }

    @Override
    public void onClick(View v) {
        BookingDTO b = new BookingDTO();
        b.setParkingID(Session.currentParking.getId());
        b.setStatus(3);
        new ManagerBookingTask("updatebystatus",b, this);
//        new ManagerBookingTask("getbystatus",b, null);


    }


    @Override
    public void onPostExecute(Object o) {
        try {
            System.out.println("bố ở checkoutactivity rồi");
            JSONObject jsonObj = new JSONObject(o.toString());
            tvPrice.setText(jsonObj.getString("price"));
            tvTimeInOut.setText(jsonObj.getString("timein") + " - " + jsonObj.getString("timeout"));
            tvPrice.setText(jsonObj.getString("price"));
            final DateFormat df = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
            Date date1 = df.parse(jsonObj.getString("timein"));
            Date date2 = df.parse(jsonObj.getString("timeout"));
//            String dateformat1 = dataModel.getTimein();
//            String dateformat2 = dataModel.getTimeout();
//            long diff = datetimeout.getTime() - date1.getTime();
//            double diffInHours = diff / ((double) 1000 * 60 * 60);
//            NumberFormat formatter = new DecimalFormat("###,###");
//            NumberFormat formatterHour = new DecimalFormat("0.00");
//            String totalPrice = formatter.format(diffInHours * dataModel.getPrice());
//            if (diffInHours < 1) {
//                totalPrice = dataModel.getPrice() + "";
//            }
//            builder.setMessage("\tHóa đơn checkout \n"
//                    + "Biển số :          " + dataModel.getLicensePlate() + "\n"
//                    + "loại xe :           " + dataModel.getTypeCar() + "\n"
//                    + "thời gian vào : " + dateformat1 + "\n"
//                    + "thời gian ra :   " + dateformat2 + "\n"
//                    + "giá đỗ :           " + formatter.format(dataModel.getPrice()) + "vnđ\n"
//                    + "thời gian đỗ :  " + formatterHour.format(diffInHours) + " giờ \n"
//                    + "tổng giá :        " + totalPrice + "vnđ")
//                    .setPositiveButton("Yes", dialogClickListener)
//                    .setNegativeButton("No", dialogClickListener).setCancelable(false).show();

        }catch(Exception e) {
            System.out.println(e);
        }
    }
}
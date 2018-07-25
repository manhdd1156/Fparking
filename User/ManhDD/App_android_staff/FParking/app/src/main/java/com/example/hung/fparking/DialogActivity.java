package com.example.hung.fparking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.hung.fparking.asynctask.GetVehicleTask;
import com.example.hung.fparking.asynctask.ManagerBookingTask;
import com.example.hung.fparking.config.Session;
import com.example.hung.fparking.dto.BookingDTO;
import com.example.hung.fparking.dto.vehicleDTO;
import com.example.hung.fparking.login.MainActivity;

import org.json.JSONObject;

public class DialogActivity extends Activity implements OnClickListener {

    Button ok_btn, cancel_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog);

        ok_btn = (Button) findViewById(R.id.ok_btn_id);
        cancel_btn = (Button) findViewById(R.id.cancel_btn_id);

//        ok_btn.setOnClickListener(this);
        cancel_btn.setOnClickListener(this);
        Intent intent = getIntent();
        String eventName = intent.getStringExtra("eventName");
        String data = intent.getStringExtra("data");
        handleMessage(eventName,data);
//        this.setFinishOnTouchOutside(false);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ok_btn_id:

                showToastMessage("Ok Button Clicked");
                this.finish();

                break;

            case R.id.cancel_btn_id:

                showToastMessage("Cancel Button Clicked");
                this.finish();

                break;
        }

    }

    void showToastMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
                .show();
    }
    public void handleMessage(String eventName, String data) {
        try {
//            JSONObject json = new JSONObject(data);
            System.out.println(data);
//            final BookingDTO b = new BookingDTO();
//                b.setParkingID(Session.currentParking.getId());
            if (eventName.toLowerCase().contains("order")) {
//                b.setDrivervehicleID(json.getInt("drivervehicleID"));
                new GetVehicleTask(Session.currentParking.getId(),"order",this);

                ok_btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        BookingDTO b = new BookingDTO();
                        b.setParkingID(Session.currentParking.getId());
                        b.setStatus(1);
                        new ManagerBookingTask("updatebystatus",b, null);
                        showToastMessage("Ok Button Clicked");
                        Intent homeIntent = new Intent(DialogActivity.this, HomeActivity.class);
                        startActivity(homeIntent);
                        finish();

//                startActivity(intent);
                        // TODO Auto-generated method stub
                    }
                });
                cancel_btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        BookingDTO b = new BookingDTO();
                        b.setParkingID(Session.currentParking.getId());
                        b.setStatus(1);
                        new ManagerBookingTask("cancel",b, null);
                        showToastMessage("Cancel Button Clicked");
                        Intent homeIntent = new Intent(DialogActivity.this, HomeActivity.class);
                        startActivity(homeIntent);
                        finish();

//                startActivity(intent);
                        // TODO Auto-generated method stub
                    }
                });
            } else if (eventName.toLowerCase().contains("checkin")) {
                new GetVehicleTask(Session.currentParking.getId(),"checkin",this);

                ok_btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        BookingDTO b = new BookingDTO();
                        b.setParkingID(Session.currentParking.getId());
                        b.setStatus(2);
                        new ManagerBookingTask("updatebystatus",b, null);
                        showToastMessage("Ok Button Clicked");
                        Intent homeIntent = new Intent(DialogActivity.this, HomeActivity.class);
                        startActivity(homeIntent);
                        finish();

//                startActivity(intent);
                        // TODO Auto-generated method stub
                    }
                });
                cancel_btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        BookingDTO b = new BookingDTO();
                        b.setParkingID(Session.currentParking.getId());
                        b.setStatus(2);
                        new ManagerBookingTask("cancel",b, null);
                        showToastMessage("Cancel Button Clicked");
                        Intent homeIntent = new Intent(DialogActivity.this, HomeActivity.class);
                        startActivity(homeIntent);
                        finish();

//                startActivity(intent);
                        // TODO Auto-generated method stub
                    }
                });

            } else if (eventName.toLowerCase().contains("checkout")) {
                ok_btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        BookingDTO b = new BookingDTO();
                        b.setParkingID(Session.currentParking.getId());
                        b.setStatus(3);
                        new ManagerBookingTask("updatebystatus",b, null);
                        showToastMessage("Ok Button Clicked");
                        Intent homeIntent = new Intent(DialogActivity.this, HomeActivity.class);
                        startActivity(homeIntent);
                        finish();

//                startActivity(intent);
                        // TODO Auto-generated method stub
                    }
                });
                cancel_btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        BookingDTO b = new BookingDTO();
                        b.setParkingID(Session.currentParking.getId());
                        b.setStatus(3);
                        new ManagerBookingTask("cancel",b, null);
                        showToastMessage("Cancel Button Clicked");
                        Intent homeIntent = new Intent(DialogActivity.this, HomeActivity.class);
                        startActivity(homeIntent);
                        finish();

//                startActivity(intent);
                        // TODO Auto-generated method stub
                    }
                });
            }
        }catch(Exception e) {
            System.out.println("lỗi dialogactivity : " + e);
        }
    }
}
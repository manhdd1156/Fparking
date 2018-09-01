package com.example.hung.fparking.dialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hung.fparking.HomeActivity;
import com.example.hung.fparking.R;
import com.example.hung.fparking.asynctask.GetVehicleTask;
import com.example.hung.fparking.asynctask.IAsyncTaskHandler;
import com.example.hung.fparking.asynctask.ManagerBookingTask;
import com.example.hung.fparking.config.Session;
import com.example.hung.fparking.dto.BookingDTO;

public class DialogActivity extends Activity  implements OnClickListener,IAsyncTaskHandler {

    Button ok_btn, cancel_btn;
    TextView tvLicensePlate,tvType,tvColor,tvTitle,tvDriverid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

       tvLicensePlate = (TextView) findViewById(R.id.tvLP);
        tvType = (TextView) findViewById(R.id.tvType);
      tvColor = (TextView) findViewById(R.id.tvColor);
        tvTitle = (TextView) findViewById(R.id.title);
        tvDriverid  = (TextView) findViewById(R.id.lbdriverid);
        ok_btn = (Button) findViewById(R.id.ok_btn_id);
        cancel_btn = (Button) findViewById(R.id.cancel_btn_id);

        Intent intent = getIntent();
        String eventName = intent.getStringExtra("eventName");
        String data = intent.getStringExtra("data");
        handleMessage(eventName, data);
        this.setFinishOnTouchOutside(false);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ok_btn_id:
                this.finish();
                break;

            case R.id.cancel_btn_id:
                this.finish();
                break;
        }

    }

    public void handleMessage(String eventName, String data) {
        try {
            System.out.println("DialogActivity/handleMessage : data = " + data);

            if (eventName.toLowerCase().contains("order")) {
                new GetVehicleTask(Session.currentParking.getId(), "order", this).execute((Void) null);

                ok_btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        BookingDTO b = new BookingDTO();
                        b.setParkingID(Session.currentParking.getId());
                        b.setDriverID(Integer.parseInt(tvDriverid.getText().toString()));
                        b.setStatus(1);
                        new ManagerBookingTask("updatebystatus", b, new IAsyncTaskHandler() {
                            @Override
                            public void onPostExecute(Object o) {
                                Session.homeActivity.recreate();
                                finish();
                            }
                        });
                    }
                });
                cancel_btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        BookingDTO b = new BookingDTO();
                        b.setParkingID(Session.currentParking.getId());
                        b.setDriverID(Integer.parseInt(tvDriverid.getText().toString()));
                        b.setStatus(1);
                        new ManagerBookingTask("cancel", b, new IAsyncTaskHandler() {
                            @Override
                            public void onPostExecute(Object o) {

                            }
                        });
                        finish();
                        // TODO Auto-generated method stub
                    }
                });
            } else if (eventName.toLowerCase().contains("checkin")) {
                new GetVehicleTask(Session.currentParking.getId(), "checkin", this).execute((Void) null);

                ok_btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        BookingDTO b = new BookingDTO();
                        b.setParkingID(Session.currentParking.getId());
                        b.setDriverID(Integer.parseInt(tvDriverid.getText().toString()));
                        b.setStatus(2);
                        new ManagerBookingTask("updatebystatus", b, DialogActivity.this);
                        Session.homeActivity.recreate();
                        finish();

                    }
                });
                cancel_btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        BookingDTO b = new BookingDTO();
                        b.setParkingID(Session.currentParking.getId());
                        b.setDriverID(Integer.parseInt(tvDriverid.getText().toString()));
                        b.setStatus(2);
                        new ManagerBookingTask("cancel", b, DialogActivity.this);
                        finish();
                    }
                });

            } else if (eventName.toLowerCase().contains("checkout")) {
                new GetVehicleTask(Session.currentParking.getId(), "checkout", this).execute((Void) null);
               Session.homeActivity.getLocalClassName();
                ok_btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        BookingDTO b = new BookingDTO();
                        b.setParkingID(Session.currentParking.getId());
                        b.setDriverID(Integer.parseInt(tvDriverid.getText().toString()));
                        b.setStatus(3);
                        new ManagerBookingTask("getInfoCheckout", b, DialogActivity.this);

                       finish();
                    }
                });
                cancel_btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        BookingDTO b = new BookingDTO();
                        b.setParkingID(Session.currentParking.getId());
                        b.setDriverID(Integer.parseInt(tvDriverid.getText().toString()));
                        b.setStatus(3);
                        new ManagerBookingTask("cancel", b, DialogActivity.this);
                        finish();
                    }
                });
            } else if(eventName.toLowerCase().contains("cancel")) {
                cancel_btn.setVisibility(View.INVISIBLE);
                ok_btn.setText("Xác nhận");
                new GetVehicleTask(Session.currentParking.getId(), "cancel", this).execute((Void) null);
                ok_btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Session.homeActivity.recreate();
                        finish();
                    }
                });
            }
        } catch (Exception e) {
            System.out.println("lỗi dialogactivity : " + e);
        }
    }

    @Override
    public void onPostExecute(Object o) {
        System.out.println("onPost");
    }
}
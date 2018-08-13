package com.example.hung.fparking.dialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        getActionBar().hide(); //hide the title bar
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialog);
//Remove title bar
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        ok_btn = (Button) findViewById(R.id.ok_btn_id);
        cancel_btn = (Button) findViewById(R.id.cancel_btn_id);

//        ok_btn.setOnClickListener(this);
//        cancel_btn.setOnClickListener(this);
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

//                showToastMessage("Ok Button Clicked");
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
            System.out.println("DialogActivity/handleMessage : data = " + data);

            if (eventName.toLowerCase().contains("order")) {
                new GetVehicleTask(Session.currentParking.getId(), "order", this).execute((Void) null);

                ok_btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        BookingDTO b = new BookingDTO();
                        b.setParkingID(Session.currentParking.getId());
                        b.setStatus(1);
                        new ManagerBookingTask("updatebystatus", b, null);
                        finish();
                        Session.homeActivity.recreate();

//                        Intent myIntent = new Intent(DialogActivity.this, HomeActivity.class);
//                        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        finish();
//                        overridePendingTransition(0, 0);
//                        startActivity(myIntent);
//                        overridePendingTransition(0, 0);
////

//                startActivity(intent);
                        // TODO Auto-generated method stub
                    }
                });
                cancel_btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        BookingDTO b = new BookingDTO();
                        b.setParkingID(Session.currentParking.getId());
                        b.setStatus(1);
                        new ManagerBookingTask("cancel", b, new IAsyncTaskHandler() {
                            @Override
                            public void onPostExecute(Object o) {

                            }
                        });
                        finish();
//                        showToastMessage("Cancel Button Clicked");
//                        Intent myIntent = new Intent(DialogActivity.this, HomeActivity.class);
//                        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

//                        overridePendingTransition(0, 0);
//                        startActivity(myIntent);
//                        overridePendingTransition(0, 0);
//                        Session.homeActivity.recreate();
//                        finish();

//                startActivity(intent);
                        // TODO Auto-generated method stub
                    }
                });
            } else if (eventName.toLowerCase().contains("checkin")) {
                new GetVehicleTask(Session.currentParking.getId(), "checkin", this).execute((Void) null);

                ok_btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        BookingDTO b = new BookingDTO();
                        b.setParkingID(Session.currentParking.getId());
                        b.setStatus(2);
                        new ManagerBookingTask("updatebystatus", b, DialogActivity.this);
                        Session.homeActivity.recreate();
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
                        new ManagerBookingTask("cancel", b, DialogActivity.this);
//                        showToastMessage("Cancel Button Clicked");
//                        Intent myIntent = new Intent(DialogActivity.this, HomeActivity.class);
//                        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        finish();
//                        overridePendingTransition(0, 0);
//                        startActivity(myIntent);
//                        overridePendingTransition(0, 0);
//                        Session.homeActivity.recreate();
//                        finish();
                    }
                });

            } else if (eventName.toLowerCase().contains("checkout")) {
                new GetVehicleTask(Session.currentParking.getId(), "checkout", this).execute((Void) null);
               Session.homeActivity.getLocalClassName();
                ok_btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        BookingDTO b = new BookingDTO();
                        b.setParkingID(Session.currentParking.getId());
                        b.setStatus(3);
                        new ManagerBookingTask("getInfoCheckout", b, DialogActivity.this);

                       finish();
                    }
                });
                cancel_btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        BookingDTO b = new BookingDTO();
                        b.setParkingID(Session.currentParking.getId());
                        b.setStatus(3);
                        new ManagerBookingTask("cancel", b, DialogActivity.this);
                        showToastMessage("Cancel Button Clicked");
//                        Intent myIntent = new Intent(DialogActivity.this, HomeActivity.class);
//                        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        finish();
//                        startActivity(myIntent);
//                        Session.homeActivity.recreate();
//                        finish();

                        // TODO Auto-generated method stub
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
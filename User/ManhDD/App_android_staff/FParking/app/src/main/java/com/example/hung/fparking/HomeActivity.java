package com.example.hung.fparking;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hung.fparking.adapter.ListBookingAdapter;
import com.example.hung.fparking.asynctask.GetRateTask;
import com.example.hung.fparking.asynctask.IAsyncTaskHandler;
import com.example.hung.fparking.asynctask.ManagerBookingTask;
import com.example.hung.fparking.asynctask.ManagerParkingTask;
import com.example.hung.fparking.change_space.NumberPickerActivity;
import com.example.hung.fparking.config.Session;
import com.example.hung.fparking.dto.BookingDTO;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IAsyncTaskHandler, AdapterView.OnItemSelectedListener {
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        lv = (ListView) findViewById(R.id.cars_list);
        if (Session.currentStaff != null) {
            if (Session.currentParking == null) {
                new ManagerParkingTask("get", Session.currentStaff.getParking_id() + "", HomeActivity.this, lv);
            } else {
                TextView tvSpace = (TextView) findViewById(R.id.tvSpace);
                TextView tvAddress = (TextView) findViewById(R.id.tvAddress);
                setText(tvAddress, Session.currentParking.getAddress());
                setText(tvSpace, Session.currentParking.getCurrentspace() + "/" + Session.currentParking.getTotalspace());

            }
            BookingDTO b = new BookingDTO();
            b.setParkingID(Session.currentStaff.getParking_id());
            new GetRateTask(Session.currentParking.getId(), this).execute((Void) null);
            new ManagerBookingTask("get", b, this);
        }

        Button btnChangeSpace = (Button) findViewById(R.id.btnChange);
        btnChangeSpace.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, NumberPickerActivity.class);
                startActivity(intent);
                // TODO Auto-generated method stub
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void setText(final TextView text, final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(value);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_history) {
            // Handle the camera action
        } else if (id == R.id.nav_mAccount) {

        } else if (id == R.id.nav_mCar) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_view) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPostExecute(Object o) {
        List<BookingDTO> lstBooking = (List<BookingDTO>) o;
        Log.d("HomeActivity_onPost: ", lstBooking.toString());
//        TableDTO[] tables = new TableDTO[lstTable.size()];
//        Log.d("----", tables.toString());
//        for(int i=0;i<lstTable.size();i++) {
//            tables[i] = lstTable.get(i);
//            Log.d("3333", tables[i].toString());
//        }
//        ArrayAdapter<TableDTO> arrayAdapter = new ArrayAdapter<TableDTO>(this, R.layout.table_list_row, tables);
        ListBookingAdapter arrayAdapter = new ListBookingAdapter(this,lstBooking, this);
        lv.setAdapter(arrayAdapter);
    }

    @Override
    public void onItemSelected(final AdapterView<?> parent, View view, final int position, long id) {
        final BookingDTO dataModel = (BookingDTO) parent.getItemAtPosition(position);
        final Button btnAcept = (Button) view.findViewById(R.id.btnAccept);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);
        System.out.println("onItemSelected");
        if (dataModel.getStatus() == 1) {
//            btnAcept.setText("VÀO BÃI");
            btnAcept.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    Calendar calendar = Calendar.getInstance();
                    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String timeIn = dateFormatter.format(calendar.getTime().getTime());
//                    BookingDTO b = dataModel;
//                    b.setStatus(2);
//                    b.setTimein(timeIn.toString());
                    dataModel.setStatus(2);
                    dataModel.setTimein(timeIn.toString());
                    new ManagerBookingTask("update", dataModel, HomeActivity.this);
                    btnAcept.setText("THANH TOÁN");
                    onResume();
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    BookingDTO b = dataModel;
                    b.setStatus(0);
                    new ManagerBookingTask("update", b, HomeActivity.this);
//                    parent.removeViewAt(position);
//                    btnAcept.setText("CHeckOut");
                    onResume();
                }
            });
        } else if (dataModel.getStatus() == 2) {
            btnCancel.setVisibility(parent.INVISIBLE);
            btnAcept.setText("THANH TOÁN");
            btnAcept.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    Calendar calendar = Calendar.getInstance();
                    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    final String timeout = dateFormatter.format(calendar.getTime().getTime());
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int choice) {
                            switch (choice) {
                                case DialogInterface.BUTTON_POSITIVE:
//                                        dialog.dismiss();
//                                    BookingDTO b = dataModel;
//                                    b.setStatus(3);
//                                    b.setTimeout();
                                    dataModel.setStatus(3);
                                    dataModel.setTimeout(timeout.toString());
                                    new ManagerBookingTask("update", dataModel, HomeActivity.this);
                                    onResume();
                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:

                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                    try {
                        Date date1 = dateFormatter.parse(dataModel.getTimein());
                        Date date2 = dateFormatter.parse(dataModel.getTimeout());
                        long diff = date2.getTime() - date1.getTime();
                        double diffInHours = diff / ((double) 1000 * 60 * 60);
                        NumberFormat formatter = new DecimalFormat("###,###");
                        NumberFormat formatterHour = new DecimalFormat("0.00");
                        String totalPrice = formatter.format(diffInHours * dataModel.getPrice());
                        if (diffInHours < 1) {
                            totalPrice = dataModel.getPrice() + "";
                        }
                        builder.setMessage("\tHóa đơn checkout \n"
                                + "Biển số :          " + dataModel.getLicensePlate() + "\n"
                                + "loại xe :           " + dataModel.getTypeCar() + "\n"
                                + "thời gian vào : " + dataModel.getTimein() + "\n"
                                + "thời gian ra :   " + dataModel.getTimeout() + "\n"
                                + "giá đỗ :           " + formatter.format(dataModel.getPrice()) + "vnđ\n"
                                + "thời gian đỗ :  " + formatterHour.format(diffInHours) + " giờ \n"
                                + "tổng giá :        " + totalPrice + "vnđ")
                                .setPositiveButton("Yes", dialogClickListener)
                                .setNegativeButton("No", dialogClickListener).setCancelable(false).show();

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}

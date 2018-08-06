package com.example.hung.fparkingowner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hung.fparking.R;
import com.example.hung.fparkingowner.adapter.ListBookingHomeAdapter;
import com.example.hung.fparkingowner.asynctask.GetRateTask;
import com.example.hung.fparkingowner.asynctask.IAsyncTaskHandler;
import com.example.hung.fparkingowner.asynctask.ManagerBookingTask;
import com.example.hung.fparkingowner.asynctask.ManagerParkingTask;
import com.example.hung.fparkingowner.change_space.NumberPickerActivity;
import com.example.hung.fparkingowner.config.Session;
import com.example.hung.fparkingowner.dto.BookingDTO;
import com.example.hung.fparkingowner.model.CheckNetwork;
import com.example.hung.fparkingowner.notification.CheckNetworkReciever;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.example.hung.fparkingowner.config.Constants.PICK_CONTACT_REQUEST;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IAsyncTaskHandler {
    ListView lv;
    TextView tvSpace;
    TextView tvAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        CheckNetworkReciever.thisregisterReceiver(CheckNetworkReciever, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
        registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Session.homeActivity = HomeActivity.this;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        Session.container = this;
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        lv = (ListView) findViewById(R.id.cars_list);
        if (Session.currentOwner != null) {
            if (Session.currentParking == null) {
                new ManagerParkingTask("get", Session.currentParking, HomeActivity.this);
            } else {
                tvSpace = (TextView) findViewById(R.id.tvSpace);
                tvAddress = (TextView) findViewById(R.id.tvAddress);
                setText(tvAddress, Session.currentParking.getAddress());
                setText(tvSpace, Session.currentParking.getCurrentspace() + "/" + Session.currentParking.getTotalspace());

            }

            BookingDTO b = new BookingDTO();
            b.setParkingID(Session.currentOwner.getParking_id());
            new GetRateTask(Session.currentParking.getId(), this).execute((Void) null);
            new ManagerBookingTask("homeget", b, this);
        }

        Button btnChangeSpace = (Button) findViewById(R.id.btnChange);
        btnChangeSpace.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, NumberPickerActivity.class);
                startActivityForResult(intent, PICK_CONTACT_REQUEST);
//                startActivity(intent);
                // TODO Auto-generated method stub
            }
        });
    }
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            CheckNetwork checkNetwork = new CheckNetwork(HomeActivity.this, getApplicationContext());
            if (!checkNetwork.isNetworkConnected()) {
                checkNetwork.createDialog();
            } else {
                System.out.println("đã có mạng");
//                recreate();
            }
        }
    };

    @Override
    public void recreate() {
//       super.recreate();
//        lv = (ListView) findViewById(R.id.cars_list);
//            if (Session.currentParking == null) {
//                new ManagerParkingTask("get", Session.currentParking, HomeActivity.this);
//            } else {
//                tvSpace = (TextView) findViewById(R.id.tvSpace);
//                tvAddress = (TextView) findViewById(R.id.tvAddress);
//                setText(tvAddress, Session.currentParking.getAddress());
//                setText(tvSpace, Session.currentParking.getCurrentspace() + "/" + Session.currentParking.getTotalspace());
//
//            }
        new ManagerParkingTask("get", Session.currentParking, HomeActivity.this);
            BookingDTO b = new BookingDTO();
            b.setParkingID(Session.currentOwner.getParking_id());
            new GetRateTask(Session.currentParking.getId(), this).execute((Void) null);
        new ManagerBookingTask("homeget", b, this);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == PICK_CONTACT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                System.out.println("ở trong activityressult");
                setText(tvSpace, Session.currentParking.getCurrentspace() + "/" + Session.currentParking.getTotalspace());

                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.

                // Do something with the contact here (bigger example below)
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

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
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
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

        if (id == R.id.nav_statistical) {
            // Handle the camera action
            Intent intent = new Intent(HomeActivity.this, StatisticalActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_mAccount) {
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_view) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPostExecute(Object o) {
        System.out.println("O = " + o);
        try {
            if (o instanceof List) {
                List<BookingDTO> lstBooking = (List<BookingDTO>) o;
                Log.d("HomeActivity_onPost: ", lstBooking.toString());
                ListBookingHomeAdapter arrayAdapter = new ListBookingHomeAdapter(HomeActivity.this, lstBooking, this);
                lv.setAdapter(arrayAdapter);
            } else if(o instanceof String) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int choice) {
                        switch (choice) {
                            case DialogInterface.BUTTON_POSITIVE:
//                                BookingDTO b = new BookingDTO();
//                                b.setParkingID(Session.currentParking.getId());
//                                b.setStatus(3);
//                                new ManagerBookingTask("updatebystatus", b, null);
                                recreate();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                try {
                    final DateFormat df = new SimpleDateFormat("hh:mm:ss dd-MM-yyyy");
                    Date datein = df.parse(Session.bookingTemp.getTimein());
                    Date dateout = df.parse(Session.bookingTemp.getTimeout());


                    long diff = dateout.getTime() - datein.getTime();
                    double diffInHours = diff / ((double) 1000 * 60 * 60);
                    NumberFormat formatter = new DecimalFormat("###,###");
                    NumberFormat formatterHour = new DecimalFormat("0.00");
                    builder.setMessage("\t\t\t\t\t\t\t\t\t\tHÓA ĐƠN THANH TOÁN \n\n"
                            + "            Biển số : " + Session.bookingTemp.getLicensePlate() + "\n"
                            + "            Loại xe : " + Session.bookingTemp.getTypeCar() + "\n"
                            + " Thời gian vào : " + Session.bookingTemp.getTimein() + "\n"
                            + "    Thời gian ra : " + Session.bookingTemp.getTimeout() + "\n"
                            + "             Giá đỗ : " + formatter.format(Session.bookingTemp.getPrice()) + " vnđ\n"
                            + "   Thời gian đỗ : " + formatterHour.format(diffInHours) + " giờ \n"
                            + "Số tiền bị phạt : " + formatter.format(Session.bookingTemp.getTotalfine()) + " vnđ\n"
                            + "          Tổng giá : " + formatter.format(Session.bookingTemp.getAmount()) + " vnđ")
                            .setPositiveButton("Yes", dialogClickListener).setCancelable(false).show();

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }else if(o instanceof Boolean) {

            }
        } catch (Exception e) {
            System.out.println("lỗi onPost ở HomeActivity : " + e);
        }
    }


}

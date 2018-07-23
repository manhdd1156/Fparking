package com.example.hung.fparking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
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

import com.example.hung.fparking.adapter.ListBookingAdapter;
import com.example.hung.fparking.asynctask.GetRateTask;
import com.example.hung.fparking.asynctask.IAsyncTaskHandler;
import com.example.hung.fparking.asynctask.ManagerBookingTask;
import com.example.hung.fparking.asynctask.ManagerParkingTask;
import com.example.hung.fparking.change_space.NumberPickerActivity;
import com.example.hung.fparking.config.Session;
import com.example.hung.fparking.dto.BookingDTO;
import com.example.hung.fparking.notification.Notification;

import java.util.List;

import static com.example.hung.fparking.config.Constants.PICK_CONTACT_REQUEST;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IAsyncTaskHandler {
    ListView lv;
    TextView tvSpace;
    TextView tvAddress;
    FragmentManager fm;
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
                new ManagerParkingTask("get", Session.currentParking, HomeActivity.this);
            } else {
                tvSpace = (TextView) findViewById(R.id.tvSpace);
                tvAddress = (TextView) findViewById(R.id.tvAddress);
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
                startActivityForResult(intent,PICK_CONTACT_REQUEST);
//                startActivity(intent);
                // TODO Auto-generated method stub
            }
        });
//        DialogActivity myDialogFragment = new DialogActivity();
//        myDialogFragment.show
//        fm = getSupportFragmentManager();
//        Notification n = new Notification();
//startActivity(new Intent(this,Notification.class));
//        myDialogFragment.show(fm,"dsadasdas");

//        Intent intent = new Intent(this, n.getClass());
//        this.startService(intent);
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
        ListBookingAdapter arrayAdapter = new ListBookingAdapter(this,lstBooking, this);
        lv.setAdapter(arrayAdapter);
    }


}

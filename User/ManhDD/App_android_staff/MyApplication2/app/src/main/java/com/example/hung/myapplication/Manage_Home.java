package com.example.hung.myapplication;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hung.myapplication.R;
import com.example.hung.myapplication.asyntask.GetRateTask;
import com.example.hung.myapplication.asyntask.IAsyncTaskHandler;
import com.example.hung.myapplication.asyntask.ManagerParkingTask;
import com.example.hung.myapplication.change_space.NumberPickerActivity;
import com.example.hung.myapplication.config.Session;

public class Manage_Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IAsyncTaskHandler {
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage__home);
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
        if(Session.currentStaff!=null) {
            if (Session.currentParking == null) {
                new ManagerParkingTask("get", Session.currentStaff.getParking_id() + "", Manage_Home.this, lv);
            } else {
                TextView tvSpace = (TextView) findViewById(R.id.tvSpace);
                TextView tvAddress = (TextView) findViewById(R.id.tvAddress);
                setText(tvAddress, Session.currentParking.getAddress());
                setText(tvAddress, Session.currentParking.getCurrentspace() + "/" + Session.currentParking.getTotalspace());
            }
        }
        new GetRateTask(this).execute((Void) null);
        Button btnChangeSpace = (Button) findViewById(R.id.btnChange);
        btnChangeSpace.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Manage_Home.this, NumberPickerActivity.class);
                startActivity(intent);
                // TODO Auto-generated method stub
            }
        });

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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPostExecute(Object o) {

    }
}

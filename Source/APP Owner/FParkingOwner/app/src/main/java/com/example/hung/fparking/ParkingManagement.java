package com.example.hung.fparking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.hung.fparking.asynctask.IAsyncTaskHandler;
import com.example.hung.fparking.asynctask.ManagerParkingTask;
import com.example.hung.fparking.dto.BookingDTO;

import java.util.ArrayList;
import java.util.Collections;

public class ParkingManagement extends AppCompatActivity implements IAsyncTaskHandler {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_management);
//set recyleview
        mRecyclerView = (RecyclerView) findViewById(R.id.parking_list_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
//        mAdapter = new MyRecyclerViewAdapter(getDataSet());
//        mRecyclerView.setAdapter(mAdapter);

        new ManagerParkingTask("phone", Session.currentDriver.getId()+"", "", "", this);
    }

    @Override
    public void onPostExecute(Object o, String action) {
        booking = (ArrayList<BookingDTO>) o;
        Collections.sort(booking, BookingDTO.bookingDTOComparator);
        mAdapter = new MyRecyclerViewAdapter(booking);
        mRecyclerView.setAdapter(mAdapter);

        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Intent intentDetail = new Intent(ParkingHistory.this, DetailedHistory.class);
                intentDetail.putExtra("bookingid", booking.get(position).getBookingID());
                startActivity(intentDetail);
            }
        });
    }
}

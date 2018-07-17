package com.example.hung.fparking.asynctask;

import android.os.AsyncTask;

import com.example.hung.fparking.dto.ParkingTariffDTO;

import java.util.ArrayList;

public class ParkingTariffTask extends AsyncTask<Void, Void, Boolean> {

    private String parkingID, action;
    private ArrayList<ParkingTariffDTO> vehicle;
    private IAsyncTaskHandler container;

    @Override
    protected Boolean doInBackground(Void... voids) {
        return null;
    }
}

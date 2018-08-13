package com.example.hung.fparkingowner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;

import com.example.hung.fparkingowner.R;
import com.example.hung.fparkingowner.asynctask.GetCityTask;
import com.example.hung.fparkingowner.asynctask.IAsyncTaskHandler;
import com.example.hung.fparkingowner.config.Session;
import com.example.hung.fparkingowner.dto.CityDTO;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

public class AddParkingInformation extends AppCompatActivity implements IAsyncTaskHandler {

    String[] PARKINGLIST = {};
    EditText tbAddressAddParking, tbOpenTimeAddParking, tbSpace, tbPrice9AddParking, tbPrice16to29AddParking, tbPrice34to45AddParking;
    ArrayList<CityDTO> cityDTOS;
    private int cityID;

    MaterialBetterSpinner betterSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_add_parking);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, PARKINGLIST);
        betterSpinner = (MaterialBetterSpinner) findViewById(R.id.dropdownCity);

        betterSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < cityDTOS.size(); i++) {
                    if (PARKINGLIST[position].equals(cityDTOS.get(i).getCityName())) {
                        cityID = cityDTOS.get(i).getCityID();
                    }
                }
                Log.e("CityID: ", cityID + "");
            }
        });

        GetCityTask getCityTask = new GetCityTask(AddParkingInformation.this);
        getCityTask.execute();

        setProperties();
    }

    private void setProperties() {
        tbAddressAddParking = findViewById(R.id.tbAddressAddParking);
        tbOpenTimeAddParking = findViewById(R.id.tbOpenTimeAddParking);
        tbSpace = findViewById(R.id.tbSpace);
        tbPrice9AddParking = findViewById(R.id.tbPrice9AddParking);
        tbPrice16to29AddParking = findViewById(R.id.tbPrice16to29AddParking);
        tbPrice34to45AddParking = findViewById(R.id.tbPrice34to45AddParking);
    }

    @Override
    public void onPostExecute(Object o) {
        cityDTOS = (ArrayList<CityDTO>) o;
        if (cityDTOS.size() > 0) {
            PARKINGLIST = new String[cityDTOS.size()];
            for (int i = 0; i < cityDTOS.size(); i++) {
                PARKINGLIST[i] = cityDTOS.get(i).getCityName();
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, PARKINGLIST);
            betterSpinner.setAdapter(arrayAdapter);
        }
    }
}

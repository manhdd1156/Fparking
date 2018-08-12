package com.example.hung.fparkingowner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.example.hung.fparkingowner.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class AddParkingInformation extends AppCompatActivity {

    String[] PARKINGLIST = {"Bãi xe Trần ", "Bãi xe", "aaaaaaaaaaaaa"};
    EditText tbAddressAddParking, tbOpenTimeAddParking, tbSpace, tbPrice9AddParking, tbPrice16to29AddParking, tbPrice34to45AddParking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_add_parking);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, PARKINGLIST);
        MaterialBetterSpinner betterSpinner = (MaterialBetterSpinner) findViewById(R.id.dropdownCity);
        betterSpinner.setAdapter(arrayAdapter);

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
}

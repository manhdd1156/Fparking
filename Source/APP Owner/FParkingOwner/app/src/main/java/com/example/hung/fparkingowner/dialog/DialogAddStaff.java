package com.example.hung.fparkingowner.dialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.hung.fparkingowner.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class DialogAddStaff extends AppCompatActivity {
//    String [] PARKINGLIST ={"Bãi xe Trần ","Bãi xe","aaaaaaaaaaaaa"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_add_staff);

//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, PARKINGLIST);
//        MaterialBetterSpinner betterSpinner = (MaterialBetterSpinner)findViewById(R.id.dropdownParking);
//        betterSpinner.setAdapter(arrayAdapter);
    }
}

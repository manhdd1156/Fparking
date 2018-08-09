package com.example.hung.fparking;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class StaffManagement extends AppCompatActivity {
   ImageView backStaff, addStaff;
   AlertDialog dialog;
    String [] PARKINGLIST ={"Bãi xe Trần ","Bãi xe","aaaaaaaaaaaaa","aaaaaaaa","aaaaaaaaa","aaaaaaaaaaassssssssssssssssssssssssssssssssssssssssssssssssaaa"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_management);

        backStaff = findViewById(R.id.imageViewBackStaff);
        addStaff = findViewById(R.id.imageViewAddStaff);
        addStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v7.app.AlertDialog.Builder mBuilder = new AlertDialog.Builder(StaffManagement.this);
                View mView = getLayoutInflater().inflate(R.layout.activity_dialog_add_staff, null);
                mBuilder.setView(mView);
                dialog = mBuilder.create();
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(StaffManagement.this, android.R.layout.simple_dropdown_item_1line, PARKINGLIST);
                MaterialBetterSpinner betterSpinner = (MaterialBetterSpinner)mView.findViewById(R.id.dropdownParking);
                betterSpinner.setAdapter(arrayAdapter);
                dialog.show();
            }
        });
        backStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBackStaffManagement = new Intent(StaffManagement.this, HomeActivity.class);
                startActivity(intentBackStaffManagement);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });



    }
}

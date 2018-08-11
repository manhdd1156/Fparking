package com.example.hung.fparking;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class DetailedParking extends AppCompatActivity {
    Button btnUpdate, btnClose, btnDelete;
    AlertDialog dialog;
    ImageView backDP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_parking);

        btnUpdate = findViewById(R.id.btnUpdateDP);
        btnClose = findViewById(R.id.btnCloseDP);
        btnDelete = findViewById(R.id.btnDeleteDP);
        backDP = findViewById(R.id.imageViewBackDP);
        backDP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backDP = new Intent(DetailedParking.this, HomeActivity.class);
                startActivity(backDP);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                android.support.v7.app.AlertDialog.Builder mBuilder = new AlertDialog.Builder(DetailedParking.this);
                View mView = getLayoutInflater().inflate(R.layout.activity_cf_pass_dialog, null);
                mBuilder.setView(mView);
                dialog = mBuilder.create();
                dialog.show();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                android.support.v7.app.AlertDialog.Builder mBuilder = new AlertDialog.Builder(DetailedParking.this);
                View mView = getLayoutInflater().inflate(R.layout.activity_cf_pass_dialog, null);
                mBuilder.setView(mView);
                dialog = mBuilder.create();
                dialog.show();
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                android.support.v7.app.AlertDialog.Builder mBuilder = new AlertDialog.Builder(DetailedParking.this);
                View mView = getLayoutInflater().inflate(R.layout.activity_cf_pass_dialog, null);
                mBuilder.setView(mView);
                dialog = mBuilder.create();
                dialog.show();
            }
        });
    }
}

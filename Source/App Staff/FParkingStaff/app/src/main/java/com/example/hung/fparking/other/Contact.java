package com.example.hung.fparking.other;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.hung.fparking.HomeActivity;
import com.example.hung.fparking.R;

public class Contact extends AppCompatActivity {
    ImageView backContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);


        backContact = findViewById(R.id.imageViewBackContact);
        backContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
    }
}

package com.example.hung.fparkingowner.other;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.hung.fparkingowner.HomeActivity;
import com.example.hung.fparkingowner.R;

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
//                Intent intentBackContact = new Intent(Contact.this, HomeActivity.class);
//                startActivity(intentBackContact);
//                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }
}

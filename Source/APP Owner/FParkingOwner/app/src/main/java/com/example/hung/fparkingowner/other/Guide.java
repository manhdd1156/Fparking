package com.example.hung.fparkingowner.other;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.hung.fparkingowner.HomeActivity;
import com.example.hung.fparkingowner.R;

public class Guide extends AppCompatActivity {
ImageView backGuide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        backGuide = findViewById(R.id.imageViewBackGuide);
        backGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

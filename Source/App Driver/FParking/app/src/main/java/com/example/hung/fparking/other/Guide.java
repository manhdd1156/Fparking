package com.example.hung.fparking.other;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.hung.fparking.HomeActivity;
import com.example.hung.fparking.R;

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
                Intent intentBackGuide = new Intent(Guide.this, HomeActivity.class);
                startActivity(intentBackGuide);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, Guide.class));
        finish();
    }
}

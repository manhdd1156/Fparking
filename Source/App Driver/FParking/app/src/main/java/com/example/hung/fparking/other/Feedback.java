package com.example.hung.fparking.other;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.hung.fparking.HomeActivity;
import com.example.hung.fparking.R;

public class Feedback extends AppCompatActivity {
   ImageView backFeedback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        backFeedback = findViewById(R.id.imageViewBackFeedback);
        backFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFeedback = new Intent(Feedback.this, HomeActivity.class);
                startActivity(intentFeedback);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }
}

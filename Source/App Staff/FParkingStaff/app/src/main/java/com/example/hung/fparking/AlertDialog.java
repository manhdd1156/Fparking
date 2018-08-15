package com.example.hung.fparking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hung.fparking.config.Session;

public class AlertDialog extends AppCompatActivity {
Button btnOK;
TextView tvAleart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_dialog);
        btnOK = (Button) findViewById(R.id.btnOK);
        tvAleart = (TextView) findViewById(R.id.tvAlert);
        System.out.println("=-====");
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Session.homeActivity.recreate();
            }
        });
        Intent intent = getIntent();
        String aleart = intent.getStringExtra("StringAlert");
        tvAleart.setText(aleart);
        this.setFinishOnTouchOutside(false);
    }
}

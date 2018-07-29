package com.example.hung.fparking;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class DriverInformation extends AppCompatActivity {

    ImageView backInformation;
    Button mUpdate, confirm;
    EditText password, tbName, tbPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_information);

        // tạo dialog
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(DriverInformation.this);
        View mView = getLayoutInflater().inflate(R.layout.activity_dialog_confirm_pass, null);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();

        // ánh xạ
        backInformation = findViewById(R.id.imageViewBackInformation);
        mUpdate = (Button) findViewById(R.id.buttonUpdate);
        tbName = findViewById(R.id.tbName);
        tbPhone = findViewById(R.id.tbPhone);
        confirm = mView.findViewById(R.id.btnOK);
        password = mView.findViewById(R.id.tbPass);

        // listener
        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tbPhone.setText(password.getText());
                dialog.cancel();
            }
        });

        backInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentInformation = new Intent(DriverInformation.this, HomeActivity.class);
                startActivity(intentInformation);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}

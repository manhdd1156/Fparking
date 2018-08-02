package com.example.hung.fparking;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.hung.fparking.asynctask.DriverLoginTask;
import com.example.hung.fparking.asynctask.IAsyncTaskHandler;
import com.example.hung.fparking.config.Session;
import com.example.hung.fparking.dto.DriverDTO;

public class DriverInformation extends AppCompatActivity implements IAsyncTaskHandler {

    ImageView backInformation;
    Button mUpdate, confirm;
    EditText password, tbName, tbPhone, tbPassword;

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
        password = (EditText) mView.findViewById(R.id.tbPassWord);
        tbPassword = (EditText) findViewById(R.id.tbPass);
        tbPassword.setFocusable(false);
        tbPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentChange = new Intent(DriverInformation.this,ChangePassword.class);
                startActivity(intentChange);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

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
                DriverDTO driverDTO = new DriverDTO();
                driverDTO.setPhone(tbPhone.getText().toString());
                driverDTO.setName(tbName.getText().toString() );
                String pass = password.getText().toString();
                new DriverLoginTask("update", driverDTO, pass, DriverInformation.this);
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

        tbPhone.setText(Session.currentDriver.getPhone());
        tbName.setText(Session.currentDriver.getName());
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onPostExecute(Object o, String action) {
        password.setText("");
        tbName.setText(Session.currentDriver.getName());
        tbPhone.setText(Session.currentDriver.getPhone());
    }
}

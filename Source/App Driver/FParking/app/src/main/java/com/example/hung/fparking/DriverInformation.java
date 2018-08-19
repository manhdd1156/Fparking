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
import android.widget.TextView;

import com.example.hung.fparking.asynctask.DriverLoginTask;
import com.example.hung.fparking.asynctask.FineTask;
import com.example.hung.fparking.asynctask.IAsyncTaskHandler;
import com.example.hung.fparking.config.Constants;
import com.example.hung.fparking.config.Session;
import com.example.hung.fparking.dto.DriverDTO;
import com.example.hung.fparking.dto.FineDTO;
import com.example.hung.fparking.login.CustomToast;
import com.example.hung.fparking.model.CheckNetwork;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DriverInformation extends AppCompatActivity implements IAsyncTaskHandler {

    ImageView backInformation;
    Button mUpdate, confirm, btnOK;
    EditText password, tbName, tbPhone, tbPassword;
    TextView textViewAlert;

    AlertDialog alertDialog, dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_information);

        // tạo dialog
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(DriverInformation.this);
        View mView = getLayoutInflater().inflate(R.layout.activity_dialog_confirm_pass, null);
        mBuilder.setView(mView);
        dialog = mBuilder.create();

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
                Intent intentChange = new Intent(DriverInformation.this, ChangePassword.class);
                startActivity(intentChange);
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        // listener
        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pattern p = Pattern.compile(Constants.regEx);
                Pattern name = Pattern.compile(Constants.regName);

                Matcher m = p.matcher(tbPhone.getText().toString());
                Matcher n = name.matcher(tbName.getText().toString());
                if (!m.find()) {
                    new CustomToast().Show_Toast(getApplicationContext(), findViewById(R.id.profile_layout),
                            "Số điện thoại không đúng");
                } else if (!n.find()) {
                    new CustomToast().Show_Toast(getApplicationContext(), findViewById(R.id.profile_layout),
                            "Tên từ 2 đến 50 ký tự không bao gồm ký tự đặc biệt");
                } else {
                    new FineTask("getall", null, "fine", DriverInformation.this);
                }

            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DriverDTO driverDTO = new DriverDTO();
                driverDTO.setPhone(tbPhone.getText().toString());
                driverDTO.setName(tbName.getText().toString());
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
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        tbPhone.setText(Session.currentDriver.getPhone());
        tbName.setText(Session.currentDriver.getName());

        //tạo dialog
        AlertDialog.Builder mAlertBuilder = new AlertDialog.Builder(DriverInformation.this);
        View mAlertView = getLayoutInflater().inflate(R.layout.alert_dialog, null);
        mAlertBuilder.setView(mAlertView);
        alertDialog = mAlertBuilder.create();
        textViewAlert = mAlertView.findViewById(R.id.textViewAlert);
        btnOK = mAlertView.findViewById(R.id.btnOK);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onPostExecute(Object o, String action) {
        if (action.equals("fine")) {
            ArrayList<FineDTO> fineDTOS = (ArrayList<FineDTO>) o;
            CheckNetwork checkNetwork = new CheckNetwork(DriverInformation.this, getApplicationContext());
            if (fineDTOS.size() > 0 || !checkNetwork.isNetworkConnected() || Session.currentDriver.getStatus().equals("0")) {
                if (Session.currentDriver.getPhone().equals(tbPhone.getText().toString().trim())) {
                    textViewAlert.setText("Hiện tại không thế đổi số điện thoại");
                    alertDialog.show();
                }
            } else {
                dialog.show();
            }
        } else if (Boolean.TRUE.equals(o)) {
            textViewAlert.setText("Đổi thông tin thành công!");
            alertDialog.show();
            password.setText("");
            tbName.setText(Session.currentDriver.getName());
            tbPhone.setText(Session.currentDriver.getPhone());
        } else {
            textViewAlert.setText("Đổi thông tin thất bại!");
            alertDialog.show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}

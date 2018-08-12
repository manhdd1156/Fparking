package com.example.hung.fparking;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hung.fparking.asynctask.BookingTask;
import com.example.hung.fparking.asynctask.DriverLoginTask;
import com.example.hung.fparking.asynctask.IAsyncTaskHandler;
import com.example.hung.fparking.asynctask.ParkingTask;
import com.example.hung.fparking.config.Constants;
import com.example.hung.fparking.dto.DriverDTO;
import com.example.hung.fparking.login.CustomToast;
import com.example.hung.fparking.login.MainActivity;
import com.example.hung.fparking.login.SignUp_Fragment;
import com.example.hung.fparking.notification.Notification;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangePassword extends AppCompatActivity implements IAsyncTaskHandler {
    ImageView backChangePass;
    TextView editTextOldPass, editTextNewPass, editTextConfirmNewPass, textViewAlert;
    Button buttonUpdate, btnOK;
    AlertDialog.Builder builder;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        backChangePass = findViewById(R.id.imageViewBackChangePass);
        backChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBackChangePass = new Intent(ChangePassword.this, DriverInformation.class);
                startActivity(intentBackChangePass);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        setProperties();

    }

    private void setProperties() {
        //tạo dialog
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ChangePassword.this);
        View mView = getLayoutInflater().inflate(R.layout.alert_dialog, null);
        mBuilder.setView(mView);
        dialog = mBuilder.create();
        textViewAlert = mView.findViewById(R.id.textViewAlert);
        btnOK = mView.findViewById(R.id.btnOK);

        editTextOldPass = findViewById(R.id.editTextOldPass);
        editTextNewPass = findViewById(R.id.editTextNewPass);
        editTextConfirmNewPass = findViewById(R.id.editTextConfirmNewPass);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        builder = new AlertDialog.Builder(ChangePassword.this);

        // sự kiện ấn nút đổi pass
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });

        // sự kiện ấn nút đóng dialog
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    private void checkValidation() {
        // Get phone and password
        String oldPass = editTextOldPass.getText().toString();
        String newPass = editTextNewPass.getText().toString();
        String cfPassword = editTextConfirmNewPass.getText().toString();

        // Check for both field is empty or not
        if (oldPass.isEmpty() || newPass.isEmpty() || cfPassword.isEmpty()) {
            new CustomToast().Show_Toast(getApplicationContext(), findViewById(R.id.change_pass_layout),
                    "Hãy nhập đủ thông tin");
        }
        // Check lengh of pass
        else if(newPass.length()<6 ||newPass.length() >24){
            new CustomToast().Show_Toast(getApplicationContext(), findViewById(R.id.change_pass_layout),
                    "Mật khẩu mới phải từ 6 đến 24 ký tự");
        }
        // Check if old pass and new pass not match
        else if (!newPass.equals(cfPassword))
            new CustomToast().Show_Toast(getApplicationContext(), findViewById(R.id.change_pass_layout),
                    "Mật khẩu xác nhận không đúng, vui lòng nhập lại");
            // Else do login and do your stuff
        else {
            comfirmDialog(oldPass, cfPassword);
        }
    }

    private void comfirmDialog(final String oldp, final String newp) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int choice) {
                switch (choice) {
                    case DialogInterface.BUTTON_POSITIVE:
                        DriverDTO driverDTO = new DriverDTO();
                        driverDTO.setPassword(oldp);
                        new DriverLoginTask("newpass", driverDTO, newp, ChangePassword.this);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:

                        break;
                }
            }
        };
        try {
            builder.setMessage("Bạn có muốn đổi mật khẩu?")
                    .setPositiveButton("Đồng ý", dialogClickListener)
                    .setNegativeButton("Hủy", dialogClickListener)
                    .setCancelable(false).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPostExecute(Object o, String action) {
        if (Boolean.TRUE.equals(o)) {
            textViewAlert.setText("Mật khẩu đã được đổi thành công!");
            dialog.show();
        } else {
            textViewAlert.setText("Mật khẩu cũ không đúng!");
            dialog.show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, DriverInformation.class));
        finish();
    }
}

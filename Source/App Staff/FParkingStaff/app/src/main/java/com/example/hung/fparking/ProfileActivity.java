package com.example.hung.fparking;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hung.fparking.asynctask.IAsyncTaskHandler;
import com.example.hung.fparking.asynctask.ManagerLoginTask;
import com.example.hung.fparking.config.Constants;
import com.example.hung.fparking.config.Session;
import com.example.hung.fparking.model.CheckNetwork;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileActivity extends AppCompatActivity implements IAsyncTaskHandler {
    EditText name;
    EditText phone;
    EditText address;
    EditText password;
    EditText changePassStatistic;
    TextView tvError;
    TextView tvSuccess, error;
    Button btnConfirm, btnOK;
    AlertDialog dialog;
    ImageView backProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Button mUpdate = (Button) findViewById(R.id.btnUpdate);
        name = (EditText) findViewById(R.id.tbName);
        phone = (EditText) findViewById(R.id.tbPhone);
        address = (EditText) findViewById(R.id.tbAddress);
        tvSuccess = (TextView) findViewById(R.id.tvSuccess);
        changePassStatistic = findViewById(R.id.tbPassStatistic);
        changePassStatistic.setFocusable(false);
        registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        changePassStatistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentChangePass = new Intent(ProfileActivity.this, ChangePassword.class);
                startActivity(intentChangePass);
            }
        });
        backProfile = findViewById(R.id.imageViewBackProfile);
        backProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Session.homeActivity.recreate();
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        setText();
        mUpdate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                Pattern p = Pattern.compile(Constants.regEx);
                String mathPhone = phone.getText().toString();
                if (phone.getText().toString().contains("+84")) {
                    mathPhone = phone.getText().toString().replace("+84", "0");
                }
                Matcher m = p.matcher(mathPhone);
                if(name.getText().toString().isEmpty() || name.getText().toString().equals("")) {
                    showDialog("Hãy nhập tên");
                }else if(address.getText().toString().isEmpty() || address.getText().toString().equals("")) {
                    showDialog("Hãy nhập địa chỉ");
                }else if(phone.getText().toString().isEmpty() || phone.getText().toString().equals("")) {
                    showDialog("Hãy nhập số điện thoại");
                }else if(name.getText().toString().length()<2 || name.getText().toString().length()>50) {
                    showDialog("Tên phải lớn hơn 2 và nhỏ hơn 50 kí tự");
                }else if(address.getText().toString().length()<3 || address.getText().toString().length()>100) {
                    showDialog("Địa chỉ phải lớn hơn 3 và nhỏ hơn 100 ký tự");
                }
                else if (!phone.getText().toString().isEmpty() && !m.find()) {
                    showDialog("Số điện thoại không đúng định dạng");
//                    tvSuccess.setText("Số điện thoại không đúng định dạng");
//                    tvSuccess.setTextColor(Color.RED);
//                    tvSuccess.setVisibility(View.VISIBLE);
                } else {

                    tvSuccess.setVisibility(View.INVISIBLE);
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(ProfileActivity.this);
                    View mView = getLayoutInflater().inflate(R.layout.activity_cf_pass_dialog, null);
                    mBuilder.setView(mView);
                    dialog = mBuilder.create();
                    dialog.show();
                    tvError = (TextView) mView.findViewById(R.id.tvError);
                    password = (EditText) mView.findViewById(R.id.tbPassword);
                    btnConfirm = (Button) mView.findViewById(R.id.btnConfirm);

                    btnConfirm.setOnClickListener(
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {
                                        String passMD5 = getMD5Hex(password.getText().toString());
                                        if (password.getText().toString().isEmpty() || password.getText().toString().equals("")) {
                                            tvError.setText("Hãy nhập mật khẩu");
                                            tvError.setVisibility(View.VISIBLE);
                                        } else if (!passMD5.equals(Session.currentStaff.getPass())) {
                                            tvError.setText("Mật khẩu không đúng, vui lòng nhập lại");
                                            tvError.setVisibility(View.VISIBLE);
                                        } else {
                                            if (name.getText().toString().isEmpty() || name.getText().toString().equals("")) {
                                                Session.currentStaff.setName(name.getHint().toString());
                                            } else {
                                                Session.currentStaff.setName(name.getText().toString());
                                            }
                                            if (address.getText().toString().isEmpty() || address.getText().toString().equals("")) {
                                                Session.currentStaff.setAddress(address.getHint().toString());
                                            } else {
                                                Session.currentStaff.setAddress(address.getText().toString());
                                            }
                                            if (phone.getText().toString().isEmpty() || phone.getText().toString().equals("")) {
                                                Session.currentStaff.setPhone(phone.getHint().toString());
                                            } else {
                                                String tempPhone = phone.getText().toString();
                                                if(tempPhone.contains("+84")) {
                                                    tempPhone = phone.getText().toString().replace("+84","0");
                                                }
                                                Session.currentStaff.setPhone(tempPhone);
                                            }
                                            new ManagerLoginTask("updateProfile", "", "", new IAsyncTaskHandler() {
                                                @Override
                                                public void onPostExecute(Object o) {
                                                    if((boolean) o ) {
                                                        tvSuccess.setText("Cập nhật thông tin thành công");
                                                        tvSuccess.setTextColor(Color.GREEN);
                                                        tvSuccess.setVisibility(View.VISIBLE);
                                                    }else {
                                                        showDialog("Số điện thoại đã tồn tại, cập nhật không thành công");
                                                    }
//                                                    tvSuccess.setText("Cập nhật thông tin thành công");
//                                                    tvSuccess.setVisibility(View.VISIBLE);
                                                    dialog.cancel();
                                                }
                                            });
                                        }

                                    } catch (Exception e) {
                                        System.out.println("Lỗi ProfileActivity/checkValidation : " + e);
                                    }
                                }
                            });
                }
            }

        });
    }

    public void setText() {
        name.setText(Session.currentStaff.getName());
        phone.setText(Session.currentStaff.getPhone());
        address.setText(Session.currentStaff.getAddress());
    }
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            CheckNetwork checkNetwork = new CheckNetwork(ProfileActivity.this, getApplicationContext());
            if (!checkNetwork.isNetworkConnected()) {
                checkNetwork.createDialog();
            } else {
//                recreate();
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
    public void showDialog(String text) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ProfileActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.activity_alert_dialog, null);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        error = (TextView) mView.findViewById(R.id.tvAlert);
        btnOK = (Button) mView.findViewById(R.id.btnOK);
        error.setText(text);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    public static String getMD5Hex(final String inputString) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(inputString.getBytes());

        byte[] digest = md.digest();

        return convertByteToHex(digest);
    }

    private static String convertByteToHex(byte[] byteData) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onPostExecute(Object o) {
        setText();
        tvSuccess.setText("Cập nhật thông tin thành công");
        tvSuccess.setTextColor(android.R.color.holo_green_dark);
        tvSuccess.setVisibility(View.VISIBLE);
        dialog.cancel();
    }
}

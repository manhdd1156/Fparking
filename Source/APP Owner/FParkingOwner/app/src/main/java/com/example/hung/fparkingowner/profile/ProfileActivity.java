package com.example.hung.fparkingowner.profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hung.fparkingowner.DetailStaffActivity;
import com.example.hung.fparkingowner.R;
import com.example.hung.fparkingowner.asynctask.IAsyncTaskHandler;
import com.example.hung.fparkingowner.asynctask.ManagerLoginTask;
import com.example.hung.fparkingowner.asynctask.ManagerStaffTask;
import com.example.hung.fparkingowner.config.Constants;
import com.example.hung.fparkingowner.config.Session;
import com.example.hung.fparkingowner.dto.OwnerDTO;
import com.example.hung.fparkingowner.dto.StaffDTO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileActivity extends AppCompatActivity implements IAsyncTaskHandler {
    ListView lv;
    TextView tvSpace, textViewMPhone;
    TextView tvAddress;
    NavigationView navigationView;
    View headerView;
    ImageView imageViewFParking;
    EditText tbPass;
    Button update;
    EditText name;
    EditText phone;
    EditText address;
    EditText password;
    EditText changePass;
    TextView tvError;
    TextView tvSuccess, error;
    Button btnConfirm, btnOK;
    AlertDialog dialog;
    ImageView backProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        tbPass = findViewById(R.id.tbPassProfileOwner);
        tbPass.setFocusable(false);
        update = findViewById(R.id.btnUpdateProfileOwner);
        phone = (EditText) findViewById(R.id.tbPhoneSProfileOwner);
        name = (EditText) findViewById(R.id.tbNameProfileOwner);
        address = (EditText) findViewById(R.id.tbAddressProfileOwner);
        tvSuccess = (TextView) findViewById(R.id.tvSuccess);
        tbPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentChangePass = new Intent(ProfileActivity.this, ChangePassword.class);
                startActivity(intentChangePass);
            }
        });
        //Gọi alertDialog
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
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ProfileActivity.this);
//                View mView = getLayoutInflater().inflate(R.layout.activity_cf_pass_dialog, null);
//                mBuilder.setView(mView);
//                dialog = mBuilder.create();
//                dialog.show();
//                btnConfirm = (Button) mView.findViewById(R.id.btnConfirm);
//                btnConfirm.setOnClickListener(
//                        new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                checkValidation();
//                            }
//                        });
//                tvError = (TextView) mView.findViewById(R.id.tvError);
//                password = (EditText) mView.findViewById(R.id.tbPassword);
//                final AlertDialog dialog = mBuilder.create();
//                dialog.show();

                Pattern p = Pattern.compile(Constants.regEx);
                String mathPhone = phone.getText().toString();
                if (phone.getText().toString().contains("+84")) {
                    mathPhone = phone.getText().toString().replace("+84", "0");
                }
                Matcher m = p.matcher(mathPhone);

                if (!phone.getText().toString().isEmpty() && !m.find()) {
                    tvSuccess.setText("Số điện thoại không đúng định dạng");
                    tvSuccess.setTextColor(Color.RED);
                    tvSuccess.setVisibility(View.VISIBLE);
//                } else if(changePass.getText().toString().equals("") || changePass.getText().toString().isEmpty()) {
//                    tvSuccess.setText("Hãy nhập mật khẩu");
//                    tvSuccess.setTextColor(Color.RED);
//                    tvSuccess.setVisibility(View.VISIBLE);
//                } else if(changePass.getText().toString().length()<6 || changePass.getText().toString().length()>24) {
//                    tvSuccess.setText("Mật khẩu phải lớn hơn 6 và nhỏ hơn 24 kí tự");
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
                                        } else if (!passMD5.equals(Session.currentOwner.getPass())) {
                                            tvError.setText("Mật khẩu không đúng, vui lòng nhập lại");
                                            tvError.setVisibility(View.VISIBLE);
                                        } else {
                                            if (name.getText().toString().isEmpty() || name.getText().toString().equals("")) {
                                                Session.currentOwner.setName(name.getHint().toString());
                                            } else {
                                                Session.currentOwner.setName(name.getText().toString());
                                            }
                                            if (address.getText().toString().isEmpty() || address.getText().toString().equals("")) {
                                                Session.currentOwner.setAddress(address.getHint().toString());
                                            } else {
                                                Session.currentOwner.setAddress(address.getText().toString());
                                            }
                                            if (phone.getText().toString().isEmpty() || phone.getText().toString().equals("")) {
                                                Session.currentOwner.setPhone(phone.getHint().toString());
                                            } else {
                                                String tempPhone = phone.getText().toString();
                                                if (tempPhone.contains("+84")) {
                                                    tempPhone = phone.getText().toString().replace("+84", "0");
                                                }
                                                Session.currentOwner.setPhone(tempPhone);
                                            }
                                            new ManagerLoginTask("updateProfile", "", "", new IAsyncTaskHandler() {
                                                @Override
                                                public void onPostExecute(Object o) {
                                                    if ((boolean) o) {
                                                        tvSuccess.setText("Cập nhật thông tin thành công");
                                                        tvSuccess.setTextColor(Color.GREEN);
                                                        tvSuccess.setVisibility(View.VISIBLE);
                                                        dialog.cancel();
                                                    } else {
                                                        dialog.cancel();
                                                        showDialog("Số điện thoại đã tồn tại, cập nhật không thành công");
                                                    }

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
        name.setText("");
        phone.setText("");
        address.setText("");
        name.setHint(Session.currentOwner.getName());
        phone.setHint(Session.currentOwner.getPhone());
        address.setHint(Session.currentOwner.getAddress());
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

    public void checkValidation() {
        try {
            String passMD5 = getMD5Hex(password.getText().toString());
            if (passMD5.equals(Session.currentOwner.getPass())) {
                if (name.getText().toString().isEmpty() || name.getText().toString().equals("")) {
                    Session.currentOwner.setName(name.getHint().toString());
                } else {
                    Session.currentOwner.setName(name.getText().toString());
                }
                if (address.getText().toString().isEmpty() || address.getText().toString().equals("")) {
                    Session.currentOwner.setAddress(address.getHint().toString());
                } else {
                    Session.currentOwner.setAddress(address.getText().toString());
                }
                if (phone.getText().toString().isEmpty() || phone.getText().toString().equals("")) {
                    Session.currentOwner.setPhone(phone.getHint().toString());
                } else {
                    Session.currentOwner.setPhone(phone.getText().toString());
                }
                new ManagerLoginTask("updateProfile", "", "", new IAsyncTaskHandler() {
                    @Override
                    public void onPostExecute(Object o) {
                        if ((boolean) o) {
                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int choice) {
                                    switch (choice) {
                                        case DialogInterface.BUTTON_POSITIVE:
                                            break;
                                    }
                                }
                            };
                            AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                            try {

                                builder.setMessage("Số điện thoại đã tồn tại")
                                        .setPositiveButton("Yes", dialogClickListener).setCancelable(false).show();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            } else {
                showDialog("Mật khẩu không đúng");
            }

        } catch (Exception e) {
            System.out.println("Lỗi ProfileActivity/checkValidation : " + e);
        }
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


    @Override
    public void onPostExecute(Object o) {

    }
}

package com.example.hung.fparkingowner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hung.fparkingowner.asynctask.IAsyncTaskHandler;
import com.example.hung.fparkingowner.asynctask.ManagerLoginTask;
import com.example.hung.fparkingowner.asynctask.ManagerParkingTask;
import com.example.hung.fparkingowner.asynctask.ManagerStaffTask;
import com.example.hung.fparkingowner.config.Constants;
import com.example.hung.fparkingowner.config.Session;
import com.example.hung.fparkingowner.dto.ParkingDTO;
import com.example.hung.fparkingowner.dto.StaffDTO;
import com.example.hung.fparkingowner.profile.ChangePassword;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DetailStaffActivity extends AppCompatActivity implements IAsyncTaskHandler {
    EditText name;
    EditText phone;
    EditText address;
    EditText password;
    EditText changePass;
    TextView tvError;
    TextView tvSuccess;
    Button btnConfirm;
    AlertDialog dialog;
    Spinner sprinerParking;
    String staffid;
    ImageView backProfile;
    ArrayList<Integer> idParkinglist = new ArrayList<>();
    ArrayList<String> dropdownList = new ArrayList<>();
    int parkingidSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_detail);
        Button mUpdate = (Button) findViewById(R.id.btnUpdate);
        name = (EditText) findViewById(R.id.tbNameSD);
        phone = (EditText) findViewById(R.id.tbPhoneSD);
        address = (EditText) findViewById(R.id.tbAddressSD);
        tvSuccess = (TextView) findViewById(R.id.tvSuccess);
        changePass = findViewById(R.id.tbPassSD);
        changePass.setFocusable(false);
        sprinerParking = (Spinner) findViewById(R.id.spinner2);
        Intent ii = getIntent();
        staffid = ii.getStringExtra("staffid");

        backProfile = findViewById(R.id.imageViewBackProfile);
        backProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        new ManagerParkingTask("getbyowner", null, new IAsyncTaskHandler() {
            @Override
            public void onPostExecute(Object o) {
                final ArrayList<ParkingDTO> plist;
                if (o instanceof List) {
                    plist = (ArrayList<ParkingDTO>) o;

                    for(int i = 0; i<plist.size();i++) {
                        idParkinglist.add(plist.get(i).getId());
                        dropdownList.add(plist.get(i).getAddress());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter(DetailStaffActivity.this, android.R.layout.simple_spinner_item, dropdownList);
                    adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                    sprinerParking.setAdapter(adapter);
                    sprinerParking.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            parkingidSelected = idParkinglist.get(sprinerParking.getSelectedItemPosition());

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }

            }
        });

        new ManagerStaffTask("getbyowner", new StaffDTO(), this);
        mUpdate.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                Pattern p = Pattern.compile(Constants.regEx);
                String mathPhone =  phone.getText().toString();
                if (phone.getText().toString().contains("+84")) {
                    mathPhone = phone.getText().toString().replace("+84", "0");
                }
                Matcher m = p.matcher(mathPhone);
                if (!phone.getText().toString().isEmpty() && !m.find()) {
                    tvSuccess.setText("Số điện thoại không đúng định dạng");
                    tvSuccess.setTextColor(Color.RED);
                    tvSuccess.setVisibility(View.VISIBLE);
                } else if(changePass.getText().toString().equals("") || changePass.getText().toString().isEmpty()) {
                    tvSuccess.setText("Hãy nhập mật khẩu");
                    tvSuccess.setTextColor(Color.RED);
                    tvSuccess.setVisibility(View.VISIBLE);
                } else if(changePass.getText().toString().length()<6 || changePass.getText().toString().length()>24) {
                    tvSuccess.setText("Mật khẩu phải lớn hơn 6 và nhỏ hơn 24 kí tự");
                    tvSuccess.setTextColor(Color.RED);
                    tvSuccess.setVisibility(View.VISIBLE);
                }else {
                    tvSuccess.setVisibility(View.INVISIBLE);
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(DetailStaffActivity.this);
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
                                            StaffDTO staffDTO = new StaffDTO();
                                            staffDTO.setId(Integer.parseInt(staffid));
                                            if (name.getText().toString().isEmpty() || name.getText().toString().equals("")) {
                                                staffDTO.setName(name.getHint().toString());
                                            } else {
                                                staffDTO.setName(name.getText().toString());
                                            }
                                            if (address.getText().toString().isEmpty() || address.getText().toString().equals("")) {
                                                staffDTO.setAddress(address.getHint().toString());
                                            } else {
                                                staffDTO.setAddress(address.getText().toString());
                                            }
                                            if (phone.getText().toString().isEmpty() || phone.getText().toString().equals("")) {
                                                staffDTO.setPhone(phone.getHint().toString());
                                            } else {
                                                staffDTO.setPhone(phone.getText().toString());
                                            }
                                            if(staffDTO.getPhone().contains("+84")) {
                                                staffDTO.setPhone(staffDTO.getPhone().replace("+84","0"));
                                            }
                                                staffDTO.setPass(getMD5Hex(changePass.getText().toString()));
                                            staffDTO.setParking_id(parkingidSelected);
                                            new ManagerStaffTask("update", staffDTO, new IAsyncTaskHandler() {
                                                @Override
                                                public void onPostExecute(Object o) {
                                                    tvSuccess.setText("Cập nhật thông tin thành công");
                                                    tvSuccess.setTextColor(Color.GREEN);
                                                    tvSuccess.setVisibility(View.VISIBLE);
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
//        setText();
//        tvSuccess.setText("Cập nhật thông tin thành công");
//        tvSuccess.setTextColor(android.R.color.holo_green_light);
//        tvSuccess.setVisibility(View.VISIBLE);
//        dialog.cancel();
        List<StaffDTO> slist = (ArrayList<StaffDTO>) o;
        int temp = 0;

        for (int i = 0; i < slist.size(); i++) {
            if (slist.get(i).getId() == Integer.parseInt(staffid)) {
//                System.out.println("staff = " + slist.get(i).getParking_address());
                name.setText("");
                phone.setText("");
                address.setText("");
                changePass.setText("");
                name.setHint(slist.get(i).getName());
                phone.setHint(slist.get(i).getPhone());
                address.setHint(slist.get(i).getAddress());
                changePass.setText(slist.get(i).getPass());
                for (int j = 0; j < dropdownList.size(); j++) {
                    if (dropdownList.get(j).equals(slist.get(i).getParking_address())) {
                        sprinerParking.setSelection(j);
                    }
                }
                break;

            }
        }


    }
}

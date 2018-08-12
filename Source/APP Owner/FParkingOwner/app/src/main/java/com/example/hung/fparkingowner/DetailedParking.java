package com.example.hung.fparkingowner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hung.fparkingowner.asynctask.GetCityTask;
import com.example.hung.fparkingowner.asynctask.IAsyncTaskHandler;
import com.example.hung.fparkingowner.asynctask.ManagerParkingTask;
import com.example.hung.fparkingowner.config.Session;
import com.example.hung.fparkingowner.dto.ParkingDTO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


public class DetailedParking extends AppCompatActivity implements IAsyncTaskHandler {
    Button btnUpdate, btnClose, btnDelete;
    AlertDialog dialog;
    EditText address, openHour, openMin, closeHour, closeMin, totalSpace, confirmPass, currentSpace;
    TextView desposits, process, errorConfirmPass;
    Button btnConfimPass;
    ImageView backDP;
    Spinner sprinerCity;
    String parkingid, city;
    List<String> listCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_parking);
        Intent intent = getIntent();
        parkingid = intent.getStringExtra("parkingid");
        address = (EditText) findViewById(R.id.tbAddressDP);
        openHour = (EditText) findViewById(R.id.tbOpenTimeHour);
        openMin = (EditText) findViewById(R.id.tbOpenTimeMin);
        closeHour = (EditText) findViewById(R.id.tbCloseTimeHour);
        closeMin = (EditText) findViewById(R.id.tbCloseTimeMin);
        totalSpace = (EditText) findViewById(R.id.tbTotalSpace);
        currentSpace = (EditText) findViewById(R.id.tbCurrentSpace);
        desposits = (TextView) findViewById(R.id.tvMoneyDP);
        process = (TextView) findViewById(R.id.tvProcess);
        sprinerCity = (Spinner) findViewById(R.id.spinner);
        listCity = new ArrayList<>();


        btnUpdate = findViewById(R.id.btnUpdateDP);
        btnClose = findViewById(R.id.btnCloseDP);
        btnDelete = findViewById(R.id.btnDeleteDP);
        backDP = findViewById(R.id.imageViewBackDP);

        backDP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (address.getText().toString().isEmpty() || address.getText().toString().equals("") ||
                        openHour.getText().toString().isEmpty() || openHour.getText().toString().equals("") ||
                        openMin.getText().toString().isEmpty() || openMin.getText().toString().equals("") ||
                        closeHour.getText().toString().isEmpty() || closeHour.getText().toString().equals("") ||
                        closeMin.getText().toString().isEmpty() || closeMin.getText().toString().equals("") ||
                        totalSpace.getText().toString().isEmpty() || totalSpace.getText().toString().equals("")
                        ) {
                    process.setText("Hãy nhập các trường cần thay đổi");
                    process.setVisibility(View.VISIBLE);
                }  else if(Integer.parseInt(openHour.getText().toString()) >23 || Integer.parseInt(openMin.getText().toString()) > 60 ||
                        Integer.parseInt(closeHour.getText().toString())>24 || Integer.parseInt(closeMin.getText().toString())>60){
                    process.setText("Sai định dạng thời gian, vui lòng nhập lại");
                }else if(Integer.parseInt(openHour.getText().toString())>Integer.parseInt(closeHour.getText().toString()) ||
                        (Integer.parseInt(openHour.getText().toString())==Integer.parseInt(closeHour.getText().toString()) && Integer.parseInt(openMin.getText().toString())>Integer.parseInt(closeMin.getText().toString()))) {
                    process.setText("Giờ đóng cửa phải muộn hơn giờ mở cửa, vui lòng nhập lại");
                    onClickBtn("update");
                }
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btnClose.getText().equals("TẠM ĐÓNG")) {
                    onClickBtn("close");
                } else if (btnClose.getText().equals("HỦY ĐÓNG")) {
                    onClickBtn("cancelclose");
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btnDelete.getText().equals("XÓA")) {
                    onClickBtn("delete");
                } else if (btnDelete.getText().equals("HỦY XÓA")) {
                    onClickBtn("canceldelete");
                }
            }
        });

        new GetCityTask(new IAsyncTaskHandler() {
            @Override
            public void onPostExecute(Object o) {
                if (o instanceof List) {
                    listCity = (ArrayList<String>) o;
                    ArrayAdapter<String> adapter = new ArrayAdapter(DetailedParking.this, android.R.layout.simple_spinner_item, listCity);
                    adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                    sprinerCity.setAdapter(adapter);
                    sprinerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            city = sprinerCity.getSelectedItem().toString();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    new ManagerParkingTask("getbyowner", null, DetailedParking.this);
                }
            }
        }).execute((Void) null);
    }

    public void onClickBtn(final String type) {
        android.support.v7.app.AlertDialog.Builder mBuilder = new AlertDialog.Builder(DetailedParking.this);
        View mView = getLayoutInflater().inflate(R.layout.activity_cf_pass_dialog, null);
        mBuilder.setView(mView);
        confirmPass = (EditText) mView.findViewById(R.id.tbPassword);
        errorConfirmPass = (TextView) mView.findViewById(R.id.tvError);
        btnConfimPass = (Button) mView.findViewById(R.id.btnConfirm);
        btnConfimPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    if (confirmPass.getText().toString().isEmpty() || confirmPass.getText().toString().equals("")) {  // when is Empty
                        errorConfirmPass.setText("Hãy nhập mật khẩu");
                        errorConfirmPass.setVisibility(View.VISIBLE);
                    } else if (!getMD5Hex(confirmPass.getText().toString()).equals(Session.currentOwner.getPass())) { // when confirm pass is wrong
                        errorConfirmPass.setText("Mật khẩu không đúng, vui lòng nhập lại");
                        errorConfirmPass.setVisibility(View.VISIBLE);
                    } else {
                        ParkingDTO p = new ParkingDTO();
                        p.setId(Integer.parseInt(parkingid));
                        if (type.equals("update")) {   // button is "cập nhật"

                            p.setAddress(address.getText().toString());
                            p.setTotalspace(Integer.parseInt(totalSpace.getText().toString()));
                            String openhour = openHour.getText().toString();
                            if (openhour.length() < 2) {
                                openhour = "0" + openhour;
                            }
                            String openmin = openMin.getText().toString();
                            if (openmin.length() < 2) {
                                openmin = "0" + openhour;
                            }
                            String closehour = closeHour.getText().toString();
                            if (closehour.length() < 2) {
                                closehour = "0" + closehour;
                            }
                            String closemin = closeMin.getText().toString();
                            if (closemin.length() < 2) {
                                closemin = "0" + closemin;
                            }
                            System.out.println(openHour.getText().toString() + " ===========================");
                            p.setTimeoc(openHour.getText().toString() + ":" + openMin.getText().toString() + "-" + closeHour.getText().toString() + ":" + closeMin.getText().toString() + "h");
                            p.setStatus(4);
                        } else if (type.equals("close")) { // button is "tạm đóng"
                            p.setStatus(5);
                        } else if (type.equals("delete")) { // button is "Xóa"
                            p.setStatus(6);
                        } else if (type.equals("cancelclose") || type.equals("canceldelete")) {// button is "Hủy đóng" or "Hủy Xóa"
                            p.setStatus(1);
                        }

                        new ManagerParkingTask("update", p, new IAsyncTaskHandler() {
                            @Override
                            public void onPostExecute(Object o) {
                                dialog.cancel();
                                finish();
                            }
                        });

                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });
        dialog = mBuilder.create();
        dialog.show();
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
        System.out.println("O = " + o);
        final ArrayList<ParkingDTO> plist;
        try {
            if (o instanceof List) {
                plist = (ArrayList<ParkingDTO>) o;

                for (int i = 0; i < plist.size(); i++) {
                    if (plist.get(i).getId() == Integer.parseInt(parkingid)) {
                        address.setText(plist.get(i).getAddress());
                        openHour.setText(plist.get(i).getTimeoc().substring(0, 2) + "");
                        openMin.setText(plist.get(i).getTimeoc().substring(3, 5) + "");
                        closeHour.setText(plist.get(i).getTimeoc().substring(6, 8) + "");
                        closeMin.setText(plist.get(i).getTimeoc().substring(9, 11) + "");
                        totalSpace.setText(plist.get(i).getTotalspace() + "");
                        currentSpace.setText(plist.get(i).getCurrentspace() + "");
                        desposits.setText(plist.get(i).getDeposits() + "");
                        sprinerCity.setSelection(plist.get(i).getCity_id());
                        // disable button when parkring is block, .....
                        if (plist.get(i).getStatus() == 2) {
                            btnUpdate.setEnabled(false);
                            btnClose.setEnabled(false);
                            btnDelete.setEnabled(false);
                            btnUpdate.setBackgroundResource(R.drawable.button_selector2);
                            btnClose.setBackgroundResource(R.drawable.button_selector2);
                            btnDelete.setBackgroundResource(R.drawable.button_selector2);
                            process.setText("Bãi xe đã bị khóa, hãy liên hệ admin");
                            process.setVisibility(View.VISIBLE);
                        } else if (plist.get(i).getStatus() == 3) {
                            btnUpdate.setEnabled(false);
                            btnClose.setEnabled(false);
                            btnDelete.setEnabled(false);
                            btnUpdate.setBackgroundResource(R.drawable.button_selector2);
                            btnClose.setBackgroundResource(R.drawable.button_selector2);
                            btnDelete.setBackgroundResource(R.drawable.button_selector2);
                            process.setText("Yêu cầu tạo mới của bạn sẽ được xử lý trong 24 giờ");
                            process.setVisibility(View.VISIBLE);

                        } else if (plist.get(i).getStatus() == 4) {
                            process.setText("Yêu cầu cập nhật của bạn sẽ được xử lý trong 24 giờ");
                            process.setVisibility(View.VISIBLE);

                        } else if (plist.get(i).getStatus() == 5) {
                            btnUpdate.setEnabled(false);
                            btnDelete.setEnabled(false);
                            btnUpdate.setBackgroundResource(R.drawable.button_selector2);
                            btnDelete.setBackgroundResource(R.drawable.button_selector2);
                            btnClose.setText("HỦY ĐÓNG");
                            process.setText("Yêu cầu tạm đóng bãi của bạn sẽ được xử lý trong 24 giờ");
                            process.setVisibility(View.VISIBLE);
                        } else if (plist.get(i).getStatus() == 6) {
                            btnUpdate.setEnabled(false);
                            btnClose.setEnabled(false);
                            btnUpdate.setBackgroundResource(R.drawable.button_selector2);
                            btnClose.setBackgroundResource(R.drawable.button_selector2);
                            btnDelete.setText("HỦY XÓA");
                            process.setText("Yêu cầu xóa bãi của bạn sẽ được xử lý trong 24 giờ");
                            process.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("lỗi onPost ở DetailParkingActivity : " + e);
        }
    }
}

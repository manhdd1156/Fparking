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
import com.example.hung.fparkingowner.asynctask.GetTariffTask;
import com.example.hung.fparkingowner.asynctask.IAsyncTaskHandler;
import com.example.hung.fparkingowner.asynctask.ManagerParkingTask;
import com.example.hung.fparkingowner.config.Session;
import com.example.hung.fparkingowner.dto.CityDTO;
import com.example.hung.fparkingowner.dto.ParkingDTO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


public class DetailParkingActivity extends AppCompatActivity implements IAsyncTaskHandler {
    Button btnUpdate, btnClose, btnDelete;
    AlertDialog dialog;
    EditText address, openHour, openMin, closeHour, closeMin, totalSpace, confirmPass, currentSpace, price9, price916, price1648;
    TextView desposits, process, error, errorConfirmPass;
    Button btnConfimPass, btnOK;
    ImageView backDP;
    Spinner sprinerCity;
    String parkingid;
    int cityid;
    List<String> listCityString;
    List<CityDTO> listCityDTO;

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
        price9 = (EditText) findViewById(R.id.tbPrice9DetailParking);
        price916 = (EditText) findViewById(R.id.tbPrice16to29DetailParking);
        price1648 = (EditText) findViewById(R.id.tbPrice34to45DetailParking);
        desposits = (TextView) findViewById(R.id.tvMoneyDP);
        process = (TextView) findViewById(R.id.tvProcess);
        sprinerCity = (Spinner) findViewById(R.id.spinner);
        listCityString = new ArrayList<>();


        btnUpdate = findViewById(R.id.btnUpdateDP);
        btnClose = findViewById(R.id.btnCloseDP);
        btnDelete = findViewById(R.id.btnDeleteDP);
        backDP = findViewById(R.id.imageViewBackDP);

        backDP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(DetailParkingActivity.this, HomeActivity.class);
//                finish();
//                startActivity(i);
                Session.homeActivity.recreate();
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (address.getText().toString().isEmpty() || address.getText().toString().equals("")) {
                    showDialog("Hãy nhập địa chỉ", 0);
                } else if (openHour.getText().toString().isEmpty() || openHour.getText().toString().equals("")
                        || openMin.getText().toString().isEmpty() || openMin.getText().toString().equals("")
                        || closeHour.getText().toString().isEmpty() || closeHour.getText().toString().equals("")
                        || closeMin.getText().toString().isEmpty() || closeMin.getText().toString().equals("")) {
                    showDialog("Hãy nhập giờ mở - đóng cửa", 0);
                } else if (totalSpace.getText().toString().isEmpty() || totalSpace.getText().toString().equals("")) {
                    showDialog("Hãy nhập số chỗ", 0);
                } else if ((price9.getText().toString().isEmpty() || price9.getText().toString().equals("")) && (price916.getText().toString().isEmpty() || price916.getText().toString().equals("")) && (price1648.getText().toString().isEmpty() || price1648.getText().toString().equals(""))) {
                    showDialog("Hãy nhập giá xe ", 0);
                } else if (Integer.parseInt(openHour.getText().toString()) > 23 || Integer.parseInt(openMin.getText().toString()) > 59
                        || Integer.parseInt(closeHour.getText().toString()) > 24 || Integer.parseInt(closeMin.getText().toString()) > 59
                        || (Integer.parseInt(closeHour.getText().toString()) == 24 && Integer.parseInt(closeMin.getText().toString()) > 0)) {
                    showDialog("Thời gian không hợp lệ", 0);
                } else if (Integer.parseInt(openHour.getText().toString()) > Integer.parseInt(closeHour.getText().toString()) ||
                        (Integer.parseInt(openHour.getText().toString()) == Integer.parseInt(closeHour.getText().toString()) && Integer.parseInt(openMin.getText().toString()) > Integer.parseInt(closeMin.getText().toString()))) {
                    showDialog("Giờ đóng cửa phải muộn hơn giờ mở cửa, vui lòng nhập lại", 0);
//                    process.setText("Giờ đóng cửa phải muộn hơn giờ mở cửa, vui lòng nhập lại");

                } else if (totalSpace.getText().length() > 3 || Integer.parseInt(totalSpace.getText().toString()) < 1 || Integer.parseInt(totalSpace.getText().toString()) > 200) {
                    showDialog("Số chỗ phải lớn hơn 0 và nhỏ hơn 200", 0);
                } else {
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
                    listCityDTO = (ArrayList<CityDTO>) o;
                    for (int i = 0; i < listCityDTO.size(); i++) {
                        listCityString.add(listCityDTO.get(i).getCityName());
                    }
                    System.out.println(listCityString);
                    ArrayAdapter<String> adapter = new ArrayAdapter(DetailParkingActivity.this, android.R.layout.simple_spinner_item, listCityString);
                    adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                    sprinerCity.setAdapter(adapter);
                    sprinerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            cityid = listCityDTO.get(sprinerCity.getSelectedItemPosition()).getCityID();
                            System.out.println(cityid);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    new GetTariffTask(Integer.parseInt(parkingid), DetailParkingActivity.this).execute((Void) null);
//                    new ManagerParkingTask("getbyowner", null,null, DetailParkingActivity.this);
                }
            }
        }).execute((Void) null);
    }

    public void onClickBtn(final String type) {
        android.support.v7.app.AlertDialog.Builder mBuilder = new AlertDialog.Builder(DetailParkingActivity.this);
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
                        if (type.equals("update")) {   // button is "cập nhật"

                            Session.currentParking.setAddress(address.getText().toString());
                            Session.currentParking.setTotalspace(Integer.parseInt(totalSpace.getText().toString()));
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
                            Session.currentParking.setCity_id(cityid);
                            Session.currentParking.setTimeoc(openhour + ":" + openmin + "-" + closehour + ":" + closemin + "h");

                            if (!price9.getText().toString().isEmpty()) {
                                Session.currentParking.setPrice9(Double.parseDouble(price9.getText().toString()));
                            }
                            if (!price1648.getText().toString().isEmpty()) {
                                Session.currentParking.setPrice1629(Double.parseDouble(price1648.getText().toString()));
                            }
                            if (!price1648.getText().toString().isEmpty()) {
                                Session.currentParking.setPrice3445(Double.parseDouble(price1648.getText().toString()));
                            }

                            Session.currentParking.setStatus(4);
                        } else if (type.equals("close")) { // button is "tạm đóng"
                            Session.currentParking.setStatus(5);
                        } else if (type.equals("delete")) { // button is "Xóa"
                            Session.currentParking.setStatus(6);
                        } else if (type.equals("cancelclose") || type.equals("canceldelete")) {// button is "Hủy đóng" or "Hủy Xóa"
                            Session.currentParking.setStatus(1);
                        }

                        new ManagerParkingTask("update", Session.currentParking, null, new IAsyncTaskHandler() {
                            @Override
                            public void onPostExecute(Object o) {
                                dialog.cancel();
                                showDialog("Thực hiện thành công", 1);
//                                Intent i = new Intent(DetailParkingActivity.this,HomeActivity.class);
//                                finish();
//                                startActivity(i);
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

    public void showDialog(String text, final int type) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(DetailParkingActivity.this);
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
                if (type == 1) {
                    Intent i = new Intent(DetailParkingActivity.this, HomeActivity.class);
                    finish();
                    startActivity(i);
                }
            }
        });
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onPostExecute(Object o) {
        System.out.println("O = " + o);
        try {
            if (o instanceof ParkingDTO) {
               Session.currentParking = (ParkingDTO)o;
                System.out.println("onpost DetailParking : " + Session.currentParking);


                        address.setText(Session.currentParking.getAddress());
                        openHour.setText(Session.currentParking.getTimeoc().substring(0, 2) + "");
                        openMin.setText(Session.currentParking.getTimeoc().substring(3, 5) + "");
                        closeHour.setText(Session.currentParking.getTimeoc().substring(6, 8) + "");
                        closeMin.setText(Session.currentParking.getTimeoc().substring(9, 11) + "");
                        totalSpace.setText(Session.currentParking.getTotalspace() + "");
                        currentSpace.setText(Session.currentParking.getCurrentspace() + "");
                        NumberFormat formatter = new DecimalFormat("###,###");
                        desposits.setText(formatter.format(Session.currentParking.getDeposits()) + "");
                        price9.setText(Session.currentParking.getPrice9() + "");
                        price916.setText(Session.currentParking.getPrice1629() + "");
                        price1648.setText(Session.currentParking.getPrice3445() + "");
                        sprinerCity.setSelection(Session.currentParking.getCity_id());
                        // disable button when parkring is block, .....
                        if (Session.currentParking.getStatus() == 2) {
                            btnUpdate.setEnabled(false);
                            btnClose.setEnabled(false);
                            btnDelete.setEnabled(false);
                            btnUpdate.setBackgroundResource(R.drawable.button_selector2);
                            btnClose.setBackgroundResource(R.drawable.button_selector2);
                            btnDelete.setBackgroundResource(R.drawable.button_selector2);
                            process.setText("Bãi xe đã bị khóa, hãy liên hệ admin");
                            process.setVisibility(View.VISIBLE);
                        } else if (Session.currentParking.getStatus() == 3) {
                            btnUpdate.setEnabled(false);
                            btnClose.setEnabled(false);
                            btnDelete.setEnabled(false);
                            btnUpdate.setBackgroundResource(R.drawable.button_selector2);
                            btnClose.setBackgroundResource(R.drawable.button_selector2);
                            btnDelete.setBackgroundResource(R.drawable.button_selector2);
                            process.setText("Yêu cầu tạo mới của bạn sẽ được xử lý trong 24 giờ");
                            process.setVisibility(View.VISIBLE);

                        } else if (Session.currentParking.getStatus() == 4) {
                            process.setText("Yêu cầu cập nhật của bạn sẽ được xử lý trong 24 giờ");
                            process.setVisibility(View.VISIBLE);

                        } else if (Session.currentParking.getStatus() == 5) {
                            btnUpdate.setEnabled(false);
                            btnDelete.setEnabled(false);
                            btnUpdate.setBackgroundResource(R.drawable.button_selector2);
                            btnDelete.setBackgroundResource(R.drawable.button_selector2);
                            btnClose.setText("HỦY ĐÓNG");
                            process.setText("Yêu cầu tạm đóng bãi của bạn sẽ được xử lý trong 24 giờ");
                            process.setVisibility(View.VISIBLE);
                        } else if (Session.currentParking.getStatus() == 6) {
                            btnUpdate.setEnabled(false);
                            btnClose.setEnabled(false);
                            btnUpdate.setBackgroundResource(R.drawable.button_selector2);
                            btnClose.setBackgroundResource(R.drawable.button_selector2);
                            btnDelete.setText("HỦY XÓA");
                            process.setText("Yêu cầu xóa bãi của bạn sẽ được xử lý trong 24 giờ");
                            process.setVisibility(View.VISIBLE);
                        }
                    }
        } catch (Exception e) {
            System.out.println("lỗi onPost ở DetailParkingActivity : " + e);
        }
    }
}

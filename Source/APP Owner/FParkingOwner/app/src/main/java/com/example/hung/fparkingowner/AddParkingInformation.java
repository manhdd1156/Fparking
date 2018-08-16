package com.example.hung.fparkingowner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.hung.fparkingowner.R;
import com.example.hung.fparkingowner.asynctask.GetCityTask;
import com.example.hung.fparkingowner.asynctask.IAsyncTaskHandler;
import com.example.hung.fparkingowner.asynctask.ManagerParkingTask;
import com.example.hung.fparkingowner.config.Constants;
import com.example.hung.fparkingowner.config.Session;
import com.example.hung.fparkingowner.dto.CityDTO;
import com.example.hung.fparkingowner.dto.ParkingDTO;
import com.example.hung.fparkingowner.login.LoginActivity;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddParkingInformation extends AppCompatActivity implements IAsyncTaskHandler {

    String[] PARKINGLIST = {};
    EditText tbAddressAddParking, tbOpenTimeAddParking, tbSpace, tbPrice9AddParking, tbPrice16to29AddParking, tbPrice34to45AddParking;
    ArrayList<CityDTO> cityDTOS;
    TextView error;

    private int cityID;
    long latitude, longitude;
    Button btnUpdate, btnOK;

    MaterialBetterSpinner betterSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_add_parking);
        Intent i = getIntent();
            latitude = i.getLongExtra("latitude",0);
        longitude =i.getLongExtra("đasa",0);


        System.out.println("latitude = " + latitude);
        System.out.println("longitude = " + longitude);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, PARKINGLIST);
        betterSpinner = (MaterialBetterSpinner) findViewById(R.id.dropdownCity);
        betterSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < cityDTOS.size(); i++) {
                    if (PARKINGLIST[position].equals(cityDTOS.get(i).getCityName())) {
                        cityID = cityDTOS.get(i).getCityID();
                    }
                }
                Log.e("CityID: ", cityID + "");
            }
        });

        GetCityTask getCityTask = new GetCityTask(AddParkingInformation.this);
        getCityTask.execute();

        setProperties();
    }

    private void setProperties() {
        tbAddressAddParking = findViewById(R.id.tbAddressAddParking);
        tbOpenTimeAddParking = findViewById(R.id.tbOpenTimeAddParking);
        tbSpace = findViewById(R.id.tbSpace);
        tbPrice9AddParking = findViewById(R.id.tbPrice9AddParking);
        tbPrice16to29AddParking = findViewById(R.id.tbPrice16to29AddParking);
        tbPrice34to45AddParking = findViewById(R.id.tbPrice34to45AddParking);

        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidate()) {
                    double price9 =0;
                    double price1629=0;
                    double price3445 = 0;
                    if(!tbPrice9AddParking.getText().toString().isEmpty()) {
                        price9 = Double.parseDouble(tbPrice9AddParking.getText().toString());
                    }
                    if(!tbPrice16to29AddParking.getText().toString().isEmpty()) {
                        price1629 = Double.parseDouble(tbPrice16to29AddParking.getText().toString());
                    }
                    if(!tbPrice34to45AddParking.getText().toString().isEmpty()) {
                        price3445 = Double.parseDouble(tbPrice34to45AddParking.getText().toString());
                    }

                    ParkingDTO parkingDTO = new ParkingDTO(0,tbAddressAddParking.getText().toString(),0,0,"",latitude+"",longitude+"",3,tbOpenTimeAddParking.getText().toString(),Integer.parseInt(tbSpace.getText().toString()),cityID,price9,price1629,price3445);
                    new ManagerParkingTask("add", parkingDTO,null, new IAsyncTaskHandler() {
                        @Override
                        public void onPostExecute(Object o) {
                            if((boolean)o) {
                                showDialog("Thêm mới thành công, yêu cầu của bạn sẽ được xử lý trong 24h",1);
                            }else {
                                showDialog("Thêm không thành công",0);
                            }
                        }
                    });
                }
            }
        });
    }

    private boolean checkValidate() {
        String pattern = "\\d";
        Pattern p = Pattern.compile(pattern);

        Matcher m = p.matcher(tbOpenTimeAddParking.getText().toString());
        if (tbAddressAddParking.getText().toString().isEmpty() || tbAddressAddParking.getText().toString().equals("")) {
            showDialog("Hãy nhập địa chỉ",0);
        }else if(tbOpenTimeAddParking.getText().toString().isEmpty() || tbOpenTimeAddParking.getText().toString().equals("")) {
            showDialog("Hãy nhập giờ mở - đóng cửa",0);
        }else if(tbSpace.getText().toString().isEmpty() || tbSpace.getText().toString().equals("")) {
            showDialog("Hãy nhập số chỗ",0);
        }else if((tbPrice9AddParking.getText().toString().isEmpty() || tbPrice9AddParking.getText().toString().equals("")) && (tbPrice16to29AddParking.getText().toString().isEmpty() || tbPrice16to29AddParking.getText().toString().equals("")) && (tbPrice34to45AddParking.getText().toString().isEmpty() || tbPrice34to45AddParking.getText().toString().equals("")) ) {
            showDialog("Hãy nhập giá xe ",0);
        }else if(String.valueOf(cityID) == null) {
            showDialog("Hãy chọn tỉnh thành",0);
        } else if (tbAddressAddParking.getText().toString().length() < 3 || tbAddressAddParking.getText().toString().length() > 100) {
            showDialog("Địa chỉ phải lớn hơn 3 và nhỏ hơn 100 kí tự",0);
        }else if(Integer.parseInt(tbSpace.getText().toString()) <1 || Integer.parseInt(tbSpace.getText().toString())>200) {
        showDialog("Số chỗ phải lớn hơn 0 và nhỏ hơn 200",0);
        }
        else if(tbOpenTimeAddParking.getText().toString().length()>11 && (tbOpenTimeAddParking.getText().toString().charAt(2)!=':' || tbOpenTimeAddParking.getText().toString().charAt(5)!='-'
                || tbOpenTimeAddParking.getText().toString().charAt(8)!=':' || tbOpenTimeAddParking.getText().toString().charAt(11)!='h'
                || !p.matcher(tbOpenTimeAddParking.getText().toString().charAt(0)+"").find() || !p.matcher(tbOpenTimeAddParking.getText().toString().charAt(1)+"").find()
                || !p.matcher(tbOpenTimeAddParking.getText().toString().charAt(3)+"").find() || !p.matcher(tbOpenTimeAddParking.getText().toString().charAt(4)+"").find()
                || !p.matcher(tbOpenTimeAddParking.getText().toString().charAt(6)+"").find() || !p.matcher(tbOpenTimeAddParking.getText().toString().charAt(7)+"").find()
                || !p.matcher(tbOpenTimeAddParking.getText().toString().charAt(9)+"").find() || !p.matcher(tbOpenTimeAddParking.getText().toString().charAt(10)+"").find())) {
            showDialog("Nhập sai định dạng, vui lòng thử lại theo mẫu (00:00-23:59h",0);
        }
//        else if(!m.find()) {
//            showDialog("Nhập sai định dạng, vui lòng thử lại theo mẫu (00:00-23:59h");
//        }
        else {
            return true;
        }

        return false;
    }

    public void showDialog(String text, final int type) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddParkingInformation.this);
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
                if(type==1) {
                    Intent i = new Intent(AddParkingInformation.this,HomeActivity.class);
                    finish();
                    startActivity(i);
                }
            }
        });
    }

    @Override
    public void onPostExecute(Object o) {
        cityDTOS = (ArrayList<CityDTO>) o;
        if (cityDTOS.size() > 0) {
            PARKINGLIST = new String[cityDTOS.size()];
            for (int i = 0; i < cityDTOS.size(); i++) {
                PARKINGLIST[i] = cityDTOS.get(i).getCityName();
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, PARKINGLIST);
            betterSpinner.setAdapter(arrayAdapter);
        }
    }
}

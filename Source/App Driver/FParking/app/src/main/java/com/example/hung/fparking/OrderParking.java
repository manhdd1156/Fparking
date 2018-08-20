package com.example.hung.fparking;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hung.fparking.asynctask.BookingTask;
import com.example.hung.fparking.asynctask.IAsyncTaskHandler;
import com.example.hung.fparking.asynctask.ParkingInforTask;
import com.example.hung.fparking.asynctask.ParkingTask;
import com.example.hung.fparking.asynctask.TariffTask;
import com.example.hung.fparking.asynctask.VehicleTask;
import com.example.hung.fparking.config.Constants;
import com.example.hung.fparking.dto.ParkingDTO;
import com.example.hung.fparking.dto.TariffDTO;
import com.example.hung.fparking.dto.TypeDTO;
import com.example.hung.fparking.dto.VehicleDTO;
import com.example.hung.fparking.login.CustomToast;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.SpriteFactory;
import com.github.ybq.android.spinkit.Style;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.synnapps.carouselview.ViewListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import in.goodiebag.carouselpicker.CarouselPicker;

public class OrderParking extends AppCompatActivity implements IAsyncTaskHandler {

    private CarouselView customCarouselView;
    private CarouselPicker carouselPicker;
    private ArrayList<VehicleDTO> vehicle;
    private ArrayList<ParkingDTO> parkingDTOS;
    private ArrayList<TariffDTO> tariffDTOS;
    private ArrayList<ParkingDTO> parkingSortDTOS;
    private List<CarouselPicker.PickerItem> textItems;
    public static CountDownTimer yourCountDownTimer;

    boolean doubleBackToExitPressedOnce = false;
    int driverVehicleID, parkingID, vehicleID;
    AlertDialog.Builder builder;

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mPreferencesEditor;

    ImageView backOrder, addCarOrder;
    public Button buttonDat_Cho;
    Button btnOK;
    public static TextView textViewEmptySpace, textViewSlots, textViewPrice, textViewTime, textViewAddress, textViewTitlePB, textViewAlert;

    //addcar view
    private CarouselPicker carouselPickerCarType;
    Button btnAddCar;
    EditText editTextLS1, editTextLS2, editTextColor;
    String type;
    private ArrayList<TypeDTO> typeDTOS;
    private List<CarouselPicker.PickerItem> textItemsType;

    public static AlertDialog dialog, quickDialog, progessDialog, notiDialog;
    Button buttonOK, buttonCancel, btnCancelPB;
    TextView textViewAddressQB, textViewTotalTimeQB, textViewPriceQB;

    int[] sampleImages = {R.drawable.image_1, R.drawable.image_2, R.drawable.image_3, R.drawable.image_4, R.drawable.image_5};
    String[] sampleNetworkImageURLs = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_parking);
        carouselPicker = (CarouselPicker) findViewById(R.id.carouselPickerLicensePlates);
        customCarouselView = (CarouselView) findViewById(R.id.customCarouselView);
        customCarouselView.setPageCount(sampleNetworkImageURLs.length);
        customCarouselView.setSlideInterval(2500);
//        customCarouselView.setViewListener(viewListener);
        setProgessbar();

        setAlertDialog();

        // tạo dialog car
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(OrderParking.this);
        View mView = getLayoutInflater().inflate(R.layout.activity_dialog_add_car, null);
        mBuilder.setView(mView);
        dialog = mBuilder.create();

        // dialog đặt chỗ nhanh
        AlertDialog.Builder quickBookingBuilder = new AlertDialog.Builder(OrderParking.this);
        View quickBookingView = getLayoutInflater().inflate(R.layout.quick_booking_layout, null);
        quickBookingBuilder.setView(quickBookingView);
        quickDialog = quickBookingBuilder.create();
        quickDialog.setCancelable(false);

        setDialogProperties(quickBookingView);

        // tạo SharedPreferences
        mPreferences = getSharedPreferences("driver", 0);
        mPreferencesEditor = mPreferences.edit();

        // check data
        int status = mPreferences.getInt("status", 5);
        if (status == 2) {
            Intent intentCheckoutFlagment = new Intent(OrderParking.this, CheckOut.class);
            startActivity(intentCheckoutFlagment);
            finish();
        } else if (status == 3) {
            Intent intentCheckoutFlagment = new Intent(OrderParking.this, CheckOut.class);
            startActivity(intentCheckoutFlagment);
            finish();
        }

        // ánh xạ
        textViewAddress = findViewById(R.id.textViewAddress);
        textViewEmptySpace = findViewById(R.id.textViewEmptySpace);
        textViewSlots = findViewById(R.id.textViewSlots);
        textViewPrice = findViewById(R.id.textViewPrice);
        textViewTime = findViewById(R.id.textViewTime);
        buttonDat_Cho = findViewById(R.id.buttonDat_Cho_Ngay);
        backOrder = findViewById(R.id.imageViewBackOrder);
        addCarOrder = findViewById(R.id.imageViewAddCarOrder);

        // add car event
        addCarOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                new VehicleTask("type", null, "ty", OrderParking.this);
            }
        });
        buttonDat_Cho.setEnabled(false);

        // create dialog
        builder = new AlertDialog.Builder(OrderParking.this);

        // set text cho button theo data
        if (status == 1) {
            buttonDat_Cho.setText("CHỈ ĐƯỜNG");
        }

        backOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backOrderIntent = new Intent(OrderParking.this, HomeActivity.class);
                startActivity(backOrderIntent);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        buttonDat_Cho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonDat_Cho.getText().equals("CHỈ ĐƯỜNG")) {
                    Intent checkOutIntent = new Intent(OrderParking.this, Direction.class);
                    startActivity(checkOutIntent);
                    finish();
                } else {
                    mPreferencesEditor.putString("drivervehicleID", driverVehicleID + "").commit();
                    mPreferencesEditor.putString("vehicleID", vehicleID + "").commit();
                    mPreferencesEditor.putString("parkingID", parkingID + "").commit();
                    new BookingTask("create", vehicleID + "", parkingID + "", "", OrderParking.this);
                    counttime();
                }
            }
        });

        // SharedPreferences
        mPreferences = getSharedPreferences("driver", 0);
        mPreferencesEditor = mPreferences.edit();
        String parkingID = mPreferences.getString("parkingID", "");
        if (!parkingID.equals("")) {
            licensePlates(parkingID);
        }

        // sư kiện carouselPicker
        carouselPicker.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                String lis = textItems.get(position).getText();
                for (int j = 0; j < vehicle.size(); j++) {
                    if (vehicle.get(j).getLicenseplate().equals(lis)) {
                        for (int i = 0; i < tariffDTOS.size(); i++) {
                            if (vehicle.get(j).getVehicleTypeID() == tariffDTOS.get(i).getVehicleTypeID()) {
                                NumberFormat currencyVN = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                                textViewPrice.setText(currencyVN.format(tariffDTOS.get(i).getPrice()).toString());
                                driverVehicleID = vehicle.get(j).getDriverVehicleID();
                                vehicleID = vehicle.get(j).getVehicleID();
                                buttonDat_Cho.setBackground(getResources().getDrawable(R.drawable.button_selector2));
                                buttonDat_Cho.setEnabled(true);
                                Log.e("price", tariffDTOS.get(i).getPrice() + "");
                                break;
                            } else {
                                textViewPrice.setText("N/A");
                                buttonDat_Cho.setBackground(getResources().getDrawable(R.drawable.button_overload2));
                                buttonDat_Cho.setEnabled(false);
                            }
                        }
                    }
                }

                Log.e("postion", textItems.get(position).getText() + "");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // load data addcar view
//        loadDataAddCar();
        carouselPickerCarType = (CarouselPicker) mView.findViewById(R.id.carouselPickerType);
        btnAddCar = mView.findViewById(R.id.btnAddCar);
        editTextLS1 = mView.findViewById(R.id.editTextLS1);
        editTextLS2 = mView.findViewById(R.id.editTextLS2);
        editTextColor = mView.findViewById(R.id.editTextColor);

        btnAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pattern p = Pattern.compile(Constants.regBs);
                Matcher m = p.matcher(editTextLS1.getText().toString());
                if (!m.find() || editTextLS2.getText().toString().length() < 4) {
                    new CustomToast().Show_Toast(getApplicationContext(), findViewById(R.id.fragmentOrder),
                            "Biển số xe không hợp lệ!");
                } else {
                    VehicleDTO addnewcar = new VehicleDTO();
                    addnewcar.setType(type);
                    addnewcar.setColor(editTextColor.getText().toString());
                    addnewcar.setLicenseplate(editTextLS1.getText().toString().toUpperCase() + "-" + editTextLS2.getText().toString());
                    new VehicleTask("create", addnewcar, "add", OrderParking.this);
                }
            }
        });
        carouselPickerCarType.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                type = textItemsType.get(position).getText().toString();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setDialogProperties(View dialogView) {
        textViewAddressQB = dialogView.findViewById(R.id.textViewAddressQB);
        textViewTotalTimeQB = dialogView.findViewById(R.id.textViewTotalTimeQB);
        textViewPriceQB = dialogView.findViewById(R.id.textViewPriceQB);
        buttonOK = dialogView.findViewById(R.id.btnOKQB);
        buttonCancel = dialogView.findViewById(R.id.btnCancelQB);

        // sự kiện nút cancel dialog
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quickDialog.cancel();
            }
        });

    }

    public void counttime() {
        progessDialog.show();
        yourCountDownTimer = new CountDownTimer(20000, 1000) {
            boolean checkOwer = true;

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                new BookingTask("timeout", vehicleID + "", parkingID + "", "timeout", OrderParking.this);
//                            new pushToOwnerOverTime("2", "cancel").execute((Void) null);
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int choice) {
                        switch (choice) {
                            case DialogInterface.BUTTON_POSITIVE:
                                if (parkingSortDTOS == null) {
                                    double lat = mPreferences.getFloat("quicklat", 0);
                                    double lng = mPreferences.getFloat("quicklng", 0);
                                    new ParkingTask("order", lat, lng, vehicleID + "", "order", OrderParking.this);
                                } else {
                                    if (parkingSortDTOS.size() > 1) {
                                        for (int i = 0; i < parkingSortDTOS.size(); i++) {
                                            if (parkingID == parkingSortDTOS.get(i).getParkingID()) {
                                                parkingSortDTOS.remove(i);
                                            }
                                        }
                                        quickDialog.show();
                                        textViewAddressQB.setText(parkingSortDTOS.get(0).getAddress());
                                        textViewTotalTimeQB.setText(parkingSortDTOS.get(0).getTimeoc());
                                        textViewPriceQB.setText(parkingSortDTOS.get(0).getTotalspace() - parkingDTOS.get(0).getCurrentspace() + "");

                                        buttonOK.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                parkingID = parkingSortDTOS.get(0).getParkingID();
                                                mPreferencesEditor.putString("vehicleID", vehicleID + "").commit();
                                                mPreferencesEditor.putString("parkingID", parkingID + "").commit();
                                                new BookingTask("create", vehicleID + "", parkingID + "", "", OrderParking.this);
                                                quickDialog.cancel();
                                                counttime();
                                            }
                                        });
                                    } else {
                                        // thông báo không tìm thấy bãi xe nào
                                        textViewAlert.setText("Không còn bãi xe nào gần bạn");
                                        notiDialog.show();
                                    }
                                }
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:

                                break;
                        }
                    }
                };
                try {
                    progessDialog.cancel();
                    builder.setMessage("Chủ bãi đỗ đang bận! Bạn có muốn tìm bãi khác?")
                            .setPositiveButton("Đồng ý", dialogClickListener)
                            .setNegativeButton("Hủy", dialogClickListener)
                            .setCancelable(false).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void licensePlates(String parkingID) {

        // gọi vehicletask

        VehicleDTO v = new VehicleDTO();
        new VehicleTask("select", v, "vt", this);

        ParkingInforTask parkingInforTask = new ParkingInforTask(parkingID, "pi", this);
        parkingInforTask.execute();

        TariffTask tariffTask = new TariffTask(parkingID, "tt", this);
        tariffTask.execute();

    }

    // To set custom views
    ViewListener viewListener = new ViewListener() {
        @Override
        public View setViewForPosition(int position) {
            View customView = getLayoutInflater().inflate(R.layout.view_custom, null);
            ImageView fruitImageView = (ImageView) customView.findViewById(R.id.fruitImageView);
//            if (sampleNetworkImageURLs.length == 0) {
            Picasso.with(getApplicationContext()).load(sampleNetworkImageURLs[position]).error(sampleImages[3]).fit().centerCrop().into(fruitImageView);
//            }
            return customView;
        }
    };

    @Override
    public void onPostExecute(Object o, String action) {

        if (action.equals("vt")) {

            vehicle = (ArrayList<VehicleDTO>) o;
            textItems = new ArrayList<>();
            if (vehicle.size() > 0) {
                for (int i = 0; i < vehicle.size(); i++) {
                    textItems.add(new CarouselPicker.TextItem(vehicle.get(i).getLicenseplate(), 6)); // 5 is text size (sp)
                }
                driverVehicleID = vehicle.get(0).getDriverVehicleID();
                vehicleID = vehicle.get(0).getVehicleID();
            }
            CarouselPicker.CarouselViewAdapter textAdapter = new CarouselPicker.CarouselViewAdapter(this, textItems, 0);
            carouselPicker.setAdapter(textAdapter);
        } else if (action.equals("pi")) {
            parkingDTOS = (ArrayList<ParkingDTO>) o;
            sampleNetworkImageURLs = new String[]{parkingDTOS.get(0).getImage().toString()};
            customCarouselView.setViewListener(viewListener);
            customCarouselView.setPageCount(sampleNetworkImageURLs.length);
            textViewEmptySpace.setText(parkingDTOS.get(0).getTotalspace() - parkingDTOS.get(0).getCurrentspace() + "");
            textViewSlots.setText("/" + parkingDTOS.get(0).getTotalspace() + "");

            textViewTime.setText(parkingDTOS.get(0).getTimeoc());
            textViewAddress.setText(parkingDTOS.get(0).getAddress());
            parkingID = parkingDTOS.get(0).getParkingID();
        } else if (action.equals("tt")) {
            if (vehicle.size() > 0) {
                tariffDTOS = (ArrayList<TariffDTO>) o;
                for (int i = 0; i < tariffDTOS.size(); i++) {
                    if (vehicle.get(0).getVehicleTypeID() == tariffDTOS.get(i).getVehicleTypeID()) {
                        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                        textViewPrice.setText(currencyVN.format(tariffDTOS.get(i).getPrice()).toString());
                        buttonDat_Cho.setBackground(getResources().getDrawable(R.drawable.button_selector2));
                        buttonDat_Cho.setEnabled(true);
                    }
                }
            }
        } else if (action.equals("ty")) {
            typeDTOS = (ArrayList<TypeDTO>) o;
            textItemsType = new ArrayList<>();
            if (typeDTOS.size() > 0) {
                for (int i = 0; i < typeDTOS.size(); i++) {
                    textItemsType.add(new CarouselPicker.TextItem(typeDTOS.get(i).getType(), 6)); // 5 is text size (sp)
                }
                type = typeDTOS.get(0).getType();
            }
            CarouselPicker.CarouselViewAdapter textAdapter = new CarouselPicker.CarouselViewAdapter(this, textItemsType, 0);
            carouselPickerCarType.setAdapter(textAdapter);
        } else if (action.equals("add")) {
            dialog.cancel();
            startActivity(getIntent());
            finish();
        } else if (action.equals("order")) {
            parkingSortDTOS = (ArrayList<ParkingDTO>) o;
            if (parkingSortDTOS.size() > 1) {
                for (int i = 0; i < parkingSortDTOS.size(); i++) {
                    if (parkingID == parkingSortDTOS.get(i).getParkingID()) {
                        parkingSortDTOS.remove(i);
                    }
                }
                quickDialog.show();
                textViewAddressQB.setText(parkingSortDTOS.get(0).getAddress());
                textViewTotalTimeQB.setText(parkingSortDTOS.get(0).getTimeoc());
                textViewPriceQB.setText(parkingSortDTOS.get(0).getTotalspace() - parkingDTOS.get(0).getCurrentspace() + "");

                buttonOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        parkingID = parkingSortDTOS.get(0).getParkingID();
                        mPreferencesEditor.putString("vehicleID", vehicleID + "").commit();
                        mPreferencesEditor.putString("parkingID", parkingID + "").commit();
                        new BookingTask("create", vehicleID + "", parkingID + "", "", OrderParking.this);
                        quickDialog.cancel();
                        counttime();
                    }
                });
            } else {
                // thông báo không tìm thấy bãi xe nào
                textViewAlert.setText("Không còn bãi xe nào gần bạn");
                notiDialog.show();
            }
        }
    }

    private void setProgessbar() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(OrderParking.this);
        View mView = getLayoutInflater().inflate(R.layout.progress_bar, null);
        mBuilder.setView(mView);
        mBuilder.setCancelable(false);
        progessDialog = mBuilder.create();
        btnCancelPB = mView.findViewById(R.id.btnCancelPB);
        textViewTitlePB = mView.findViewById(R.id.textViewTitlePB);
        textViewTitlePB.setText("Vui lòng đợi chủ bãi xác nhận");
        btnCancelPB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progessDialog.cancel();
                yourCountDownTimer.cancel();
                new BookingTask("timeout", vehicleID + "", parkingID + "", "timeout", OrderParking.this);
            }
        });

        SpinKitView spinKitView = (SpinKitView) mView.findViewById(R.id.spin_kit);
        Style style = Style.values()[7];
        Sprite drawable = SpriteFactory.create(style);
        spinKitView.setIndeterminateDrawable(drawable);
    }

    private void setAlertDialog() {
        //dialog thông báo
        AlertDialog.Builder mNotiBuilder = new AlertDialog.Builder(OrderParking.this);
        View mNotiView = getLayoutInflater().inflate(R.layout.alert_dialog, null);
        mNotiBuilder.setView(mNotiView);
        notiDialog = mNotiBuilder.create();
        textViewAlert = mNotiView.findViewById(R.id.textViewAlert);
        btnOK = mNotiView.findViewById(R.id.btnOK);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notiDialog.cancel();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        startActivity(getIntent());
        finish();
    }

    @Override
    public void onBackPressed() {
        int status = mPreferences.getInt("status", 8);
        if (status != 1) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Ấn một lần nữa để đóng ứng dụng", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        progessDialog.cancel();
        if (yourCountDownTimer != null) {
            yourCountDownTimer.cancel();
        }

    }
}

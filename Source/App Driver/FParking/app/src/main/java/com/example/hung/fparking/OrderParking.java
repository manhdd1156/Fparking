package com.example.hung.fparking;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hung.fparking.asynctask.BookingTask;
import com.example.hung.fparking.asynctask.IAsyncTaskHandler;
import com.example.hung.fparking.asynctask.ParkingInforTask;
import com.example.hung.fparking.asynctask.TariffTask;
import com.example.hung.fparking.asynctask.VehicleTask;
import com.example.hung.fparking.config.Session;
import com.example.hung.fparking.dto.ParkingDTO;
import com.example.hung.fparking.dto.TariffDTO;
import com.example.hung.fparking.dto.TypeDTO;
import com.example.hung.fparking.dto.VehicleDTO;
import com.example.hung.fparking.entity.GetNearPlace;
import com.example.hung.fparking.notification.Notification;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.synnapps.carouselview.ViewListener;

import java.util.ArrayList;
import java.util.List;

import in.goodiebag.carouselpicker.CarouselPicker;

public class OrderParking extends AppCompatActivity implements IAsyncTaskHandler {

    private CarouselView customCarouselView;
    private CarouselPicker carouselPicker;
    private ArrayList<VehicleDTO> vehicle;
    private ArrayList<ParkingDTO> parkingDTOS;
    private ArrayList<TariffDTO> tariffDTOS;
    private List<CarouselPicker.PickerItem> textItems;

    int driverVehicleID, parkingID, vehicleID;
    ProgressDialog proD;
    AlertDialog.Builder builder;

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mPreferencesEditor;

    ImageView backOrder, addCarOrder;
    Button buttonDat_Cho;
    TextView textViewEmptySpace, textViewSlots, textViewPrice, textViewTime, textViewAddress;

    //addcar view
    private CarouselPicker carouselPickerCarType;
    Button btnAddCar;
    EditText editTextLS1, editTextLS2;
    String type;
    private ArrayList<TypeDTO> typeDTOS;
    private List<CarouselPicker.PickerItem> textItemsType;

    int[] sampleImages = {R.drawable.image_1, R.drawable.image_2, R.drawable.image_3, R.drawable.image_4, R.drawable.image_5};
    String[] sampleNetworkImageURLs = {
            "https://placeholdit.imgix.net/~text?txtsize=15&txt=image1&txt=350%C3%97150&w=350&h=150",
            "https://placeholdit.imgix.net/~text?txtsize=15&txt=image2&txt=350%C3%97150&w=350&h=150",
            "https://placeholdit.imgix.net/~text?txtsize=15&txt=image3&txt=350%C3%97150&w=350&h=150",
            "https://placeholdit.imgix.net/~text?txtsize=15&txt=image4&txt=350%C3%97150&w=350&h=150",
            "https://placeholdit.imgix.net/~text?txtsize=15&txt=image5&txt=350%C3%97150&w=350&h=150"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_parking);
        carouselPicker = (CarouselPicker) findViewById(R.id.carouselPickerLicensePlates);
        customCarouselView = (CarouselView) findViewById(R.id.customCarouselView);
        customCarouselView.setPageCount(sampleImages.length);
        customCarouselView.setSlideInterval(2500);
        customCarouselView.setViewListener(viewListener);

        // tạo dialog car
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(OrderParking.this);
        View mView = getLayoutInflater().inflate(R.layout.activity_dialog_add_car, null);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();

        // tạo SharedPreferences
        mPreferences = getSharedPreferences("driver", 0);
        mPreferencesEditor = mPreferences.edit();

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
        proD = new ProgressDialog(OrderParking.this);
        builder = new AlertDialog.Builder(OrderParking.this);

        // set text cho button theo data
        int status = mPreferences.getInt("status", 8);
        if (status == 1) {
            buttonDat_Cho.setText("CHỈ ĐƯỜNG");
        }

        backOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backOrderIntent = new Intent(OrderParking.this, HomeActivity.class);
                startActivity(backOrderIntent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        buttonDat_Cho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonDat_Cho.getText().equals("CHỈ ĐƯỜNG")) {
                    Intent checkOutIntent = new Intent(OrderParking.this, Direction.class);
                    startActivity(checkOutIntent);
                } else {
                    mPreferencesEditor.putString("drivervehicleID", driverVehicleID + "").commit();
                    mPreferencesEditor.putString("vehicleID", vehicleID + "").commit();
                    mPreferencesEditor.putString("parkingID", parkingID + "").commit();
                    new BookingTask("create", vehicleID + "", parkingID + "", "", OrderParking.this);

//                    Intent intent = new Intent(OrderParking.this, Notification.class);
//                    startService(intent);
                    proD.setCancelable(false);
                    proD.show();

                    new CountDownTimer(3000, 1000) {
                        boolean checkOwer = true;

                        public void onTick(long millisUntilFinished) {
                            proD.setMessage("\tĐang đợi chủ bãi đỗ xác nhận ... " + millisUntilFinished / 1000);
                            if (checkOwer) {
//                                new pushToOwner("2", "order").execute((Void) null);
                                checkOwer = false;
                            }
                        }

                        public void onFinish() {

//                            new pushToOwnerOverTime("2", "cancel").execute((Void) null);
                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int choice) {
                                    switch (choice) {
                                        case DialogInterface.BUTTON_POSITIVE:

                                            break;
                                        case DialogInterface.BUTTON_NEGATIVE:

                                            break;
                                    }
                                }
                            };
                            try {
                                proD.dismiss();
                                builder.setMessage("Chủ bãi đỗ đang bận!")
                                        .setPositiveButton("Chấp Nhận", dialogClickListener).setCancelable(false).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
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
                                textViewPrice.setText(tariffDTOS.get(i).getPrice() + "");
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

        btnAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        carouselPickerCarType.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                type = textItemsType.get(position).getText();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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

    // To set simple images
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {

            Picasso.with(getApplicationContext()).load(sampleNetworkImageURLs[position]).placeholder(sampleImages[0]).error(sampleImages[3]).fit().centerCrop().into(imageView);

            //imageView.setImageResource(sampleImages[position]);
        }
    };

    // To set custom views
    ViewListener viewListener = new ViewListener() {
        @Override
        public View setViewForPosition(int position) {
            View customView = getLayoutInflater().inflate(R.layout.view_custom, null);
            ImageView fruitImageView = (ImageView) customView.findViewById(R.id.fruitImageView);
            fruitImageView.setImageResource(sampleImages[position]);
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

            textViewEmptySpace.setText(parkingDTOS.get(0).getCurrentspace() + "");
            textViewSlots.setText("/" + parkingDTOS.get(0).getTotalspace() + "");

            textViewTime.setText(parkingDTOS.get(0).getTimeoc());
            textViewAddress.setText(parkingDTOS.get(0).getAddress());
            parkingID = parkingDTOS.get(0).getParkingID();
        } else if (action.equals("tt")) {
            if (vehicle.size() > 0) {
                tariffDTOS = (ArrayList<TariffDTO>) o;
                for (int i = 0; i < tariffDTOS.size(); i++) {
                    if (vehicle.get(0).getVehicleTypeID() == tariffDTOS.get(i).getVehicleTypeID()) {
                        textViewPrice.setText(tariffDTOS.get(i).getPrice() + "");
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
        }
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
    }
}

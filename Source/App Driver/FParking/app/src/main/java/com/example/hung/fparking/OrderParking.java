package com.example.hung.fparking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

    CarouselView customCarouselView;
    CarouselPicker carouselPicker;
    ArrayList<VehicleDTO> vehicle;
    ArrayList<ParkingDTO> parkingDTOS;
    ArrayList<TariffDTO> tariffDTOS;
    List<CarouselPicker.PickerItem> textItems;

    int driverVehicleID, parkingID;

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mPreferencesEditor;

    ImageView backOrder;
    Button buttonDat_Cho;
    TextView textViewEmptySpace, textViewSlots, textViewPrice, textViewTime, textViewAddress;

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
                    mPreferencesEditor.putString("parkingID", parkingID + "").commit();
                    new BookingTask("create", driverVehicleID + "", parkingID + "", "", OrderParking.this);

                    Intent intent = new Intent(OrderParking.this, Notification.class);
                    startService(intent);
                    Intent checkOutIntent = new Intent(OrderParking.this, Direction.class);
                    startActivity(checkOutIntent);
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
                                Log.e("price", tariffDTOS.get(i).getPrice() + "");
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
    }

    public void licensePlates(String parkingID) {

        // gọi vehicletask

        VehicleTask vehicleTask = new VehicleTask(Session.currentDriver.getPhone(), "vt", this);
        vehicleTask.execute();

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
                    }
                }
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}

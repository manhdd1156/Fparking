package com.example.hung.fparking;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.hung.fparking.asynctask.IAsyncTaskHandler;
import com.example.hung.fparking.asynctask.VehicleTask;
import com.example.hung.fparking.config.Session;
import com.example.hung.fparking.dto.VehicleDTO;
import com.example.hung.fparking.entity.GetNearPlace;
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
        carouselPicker = (CarouselPicker)findViewById(R.id.carouselPickerLicensePlates);
        customCarouselView = (CarouselView) findViewById(R.id.customCarouselView);
        customCarouselView.setPageCount(sampleImages.length);
        customCarouselView.setSlideInterval(2500);
        customCarouselView.setViewListener(viewListener);
        licensePlates();
    }
     public void licensePlates() {

        // gọi vehicletask

         VehicleTask vehicleTask = new VehicleTask(Session.currentDriver.getPhone(), "", this);
         vehicleTask.execute();

         //Carousel 2 with all text
//         List<CarouselPicker.PickerItem> textItems = new ArrayList<>();
//
//         textItems.add(new CarouselPicker.TextItem("30ZA-12580", 6)); // 5 is text size (sp)
//         textItems.add(new CarouselPicker.TextItem("40AH-15468", 6)); // 5 is text size (sp)
//         textItems.add(new CarouselPicker.TextItem("10AR-28459", 6)); // 5 is text size (sp)
//         textItems.add(new CarouselPicker.TextItem("10AR-28459", 6)); // 5 is text size (sp)
//         textItems.add(new CarouselPicker.TextItem("10AR-28459", 6)); // 5 is text size (sp)
//         CarouselPicker.CarouselViewAdapter textAdapter = new CarouselPicker.CarouselViewAdapter(this, textItems, 0);
//         carouselPicker.setAdapter(textAdapter);
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
        List<CarouselPicker.PickerItem> textItems = new ArrayList<>();
        vehicle = (ArrayList<VehicleDTO>) o;

        if (vehicle.size() > 0) {
            for (int i = 0; i < vehicle.size(); i++) {
                textItems.add(new CarouselPicker.TextItem(vehicle.get(i).getLicenseplate(), 6)); // 5 is text size (sp)
            }

        }
        CarouselPicker.CarouselViewAdapter textAdapter = new CarouselPicker.CarouselViewAdapter(this, textItems, 0);
        carouselPicker.setAdapter(textAdapter);
    }
}

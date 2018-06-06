package com.example.hungbui.fparking;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import in.goodiebag.carouselpicker.CarouselPicker;

public class Add_License_Plate extends DialogFragment {
    public String temp;

    public Add_License_Plate() {

    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    Button buttonOrder;
    CarouselPicker abc;
    Button buttonDemo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.license_plate_fragment, container, false);

        buttonOrder = view.findViewById(R.id.buttonOrderAddLicensePlate);
        //abc = view.findViewById(R.id.demo1);
        //Carousel with all text
//        List<CarouselPicker.PickerItem> textItems = new ArrayList<>();
//        textItems.add(new CarouselPicker.TextItem("2 chỗ",10));
//        textItems.add(new CarouselPicker.TextItem("5 chỗ",10));
//        textItems.add(new CarouselPicker.TextItem("7 chỗ",10));
//        textItems.add(new CarouselPicker.TextItem("9 chỗ",10));
//        textItems.add(new CarouselPicker.TextItem("16 chỗ",10));
//        textItems.add(new CarouselPicker.TextItem("29 chỗ",10));
//        textItems.add(new CarouselPicker.TextItem("45 chỗ",10));
//        CarouselPicker.CarouselViewAdapter textAdapter = new CarouselPicker.CarouselViewAdapter(getActivity(),textItems,0);
//        abc.setAdapter(textAdapter);
        buttonOrder.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {

                getDialog().dismiss();
//                CheckAccount("1", "1");
//                Intent intent = new Intent(getActivity(), OrderParking.class);
//                intent.putExtra("Status", "1");
//                startActivity(intent);

            }
        });

        return view;
    }

    public void CheckAccount(String username, String password) {
        Log.e("CheckAcount chay", "vl that");
        boolean check = false;
        try {

            final ProgressDialog progDailog = ProgressDialog.show(getActivity(),
                    "Chờ bãi gửi xe xác nhận",
                    "....vui lòng chờ trong giây lát....", true);
            new Thread() {
                public void run() {
                    try {
                        // sleep the thread, whatever time you want.
                        sleep(5000);

                    } catch (Exception e) {
                    }
                    progDailog.dismiss();
                }
            }.start();

            //getting data code here
            //getting data code here
            //getting data code here
            //getting data code here
            //getting data code here

        } catch (Exception e) {
//            Log.e(LOG_TAG, e.getMessage());
//            PopIt("CheckAccountError", e.getMessage(), "Denied");
        }

    }


}

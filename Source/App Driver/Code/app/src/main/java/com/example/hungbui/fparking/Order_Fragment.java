package com.example.hungbui.fparking;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Order_Fragment extends Fragment {
    @Nullable
    Button buttonOrderFragment;
    TextView textViewPrice, textViewAdress, textViewEmpty, textViewSpace, textViewTime;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.order_fragment, container, false);

        buttonOrderFragment = view.findViewById(R.id.buttonOrderFragment);


        show_Information_Prking();

        //bat su kien onclick button "dat cho"
        buttonOrderFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String position = getArguments().getString("Position");
                Bundle bundlePositionpParking = new Bundle();
                bundlePositionpParking.putString("PositionParking", position);

                Add_License_Plate add_license_plate = new Add_License_Plate();
                add_license_plate.setArguments(bundlePositionpParking);

                //goi dialog add bien so xe
                add_license_plate.show(getFragmentManager(), "Add licens plate fragment");
            }
        });
        return view;
    }

    //get thong tin position tu getArguments
//    public String[] get_Position_Parking() {
//        String position = getArguments().getString("Position");
//        String[] positionParking = position.substring(position.indexOf("(") + 1, position.indexOf(")")).split(",");
////        String lat = positionParking[0];
////        String log = positionParking[1];
//        return positionParking;
//    }

    //add thong tin vao fragment
    private void show_Information_Prking() {


    }
}

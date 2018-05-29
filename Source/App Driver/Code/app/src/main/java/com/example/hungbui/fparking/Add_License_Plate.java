package com.example.hungbui.fparking;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Add_License_Plate extends DialogFragment {

    Button buttonOrder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.license_plate_fragment,container,false);

            buttonOrder = view.findViewById(R.id.buttonOrderAddLicensePlate);

            buttonOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Confirm_Order confirm_order = new Confirm_Order();
                    confirm_order.show(getFragmentManager(),"dialog");
                }
            });
        return view;
    }



}

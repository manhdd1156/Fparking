package com.example.hungbui.fparking;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Confirm_Order extends DialogFragment {
    public FragmentManager fragmentManager;
    private EditText chiduongdemo;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder dialogConfirmOrder = new AlertDialog.Builder(getActivity());
        dialogConfirmOrder.setTitle("Thông báo");
        dialogConfirmOrder.setMessage("Yêu cầu đặt chỗ không được chấp nhận");

        dialogConfirmOrder.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addCall_Direction_Fragmaent();
            }
        });

//        dialogConfirmOrder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });

        Dialog dialogConfirm = dialogConfirmOrder.create();
        return dialogConfirm;
    }

    private void show() {
    }

    private void addCall_Direction_Fragmaent() {
        final String bundlePositionPaking = getArguments().getString("PositionParking");
        Bundle positionPostionParking = new Bundle();
        positionPostionParking.putString("PositionParking", bundlePositionPaking);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Call_Direction call_direction = new Call_Direction();

        call_direction.setArguments(positionPostionParking);
        fragmentTransaction.replace(R.id.fragmentOrder, call_direction);
        fragmentTransaction.commit();
    }
}

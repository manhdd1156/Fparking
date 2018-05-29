package com.example.hungbui.fparking;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;

public class Confirm_Order extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        final String bundlePositionPaking = getArguments().getString("PositionParking");


        AlertDialog.Builder dialogConfirmOrder = new AlertDialog.Builder(getActivity());
        dialogConfirmOrder.setTitle("Xác nhận");
        dialogConfirmOrder.setMessage("Bạn có muốn đặt chỗ đỗ?");

        dialogConfirmOrder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                addCall_Direction_Fragmaent(bundlePositionPaking);
            }
        });

        dialogConfirmOrder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        Dialog dialogConfirm = dialogConfirmOrder.create();
        return dialogConfirm;
    }

    private void addCall_Direction_Fragmaent(String position) {
        Bundle positionPostionParking = new Bundle();
        positionPostionParking.putString("PositionParking", position);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Call_Direction call_direction = new Call_Direction();
        call_direction.setArguments(positionPostionParking);

        fragmentTransaction.replace(R.id.fragmentOrder, call_direction);
        fragmentTransaction.commit();
    }
}

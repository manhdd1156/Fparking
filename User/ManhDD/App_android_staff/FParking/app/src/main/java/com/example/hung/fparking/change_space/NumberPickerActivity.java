package com.example.hung.fparking.change_space;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import com.example.hung.fparking.HomeActivity;
import com.example.hung.fparking.asynctask.IAsyncTaskHandler;
import com.example.hung.fparking.asynctask.ManagerParkingTask;
import com.example.hung.fparking.config.Session;
import com.example.hung.fparking.R;
import com.example.hung.fparking.config.Session;
import com.example.hung.fparking.dto.ParkingDTO;

public class NumberPickerActivity extends Activity implements IAsyncTaskHandler{
    private int oldValue;
    private int newValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.billing_day_dialog);
        NumberPicker np = (NumberPicker) findViewById(R.id.number_picker);

        if (Session.currentParking != null) {

            oldValue=0;
            newValue=0;
            np.setMinValue(0);// restricted number to minimum value i.e 1
//            np.setMaxValue(Session.currentParking.getTotalspace());// restricked number to maximum value i.e. 31
            np.setMaxValue(30);// restricked number to maximum value i.e. 31
        }
        np.setWrapSelectorWheel(true);

        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                // TODO Auto-generated method stub
            oldVal = oldVal;
            newValue = newVal;

            }
        });

//        Log.d("NumberPicker", "NumberPicker");
//        Button btnChangeSpace = (Button) findViewById(R.id.apply_button);
//        btnChangeSpace.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                if(Session.currentParking!=null) {
//                    Session.currentParking.setCurrentspace(newValue);
//                    new ManagerParkingTask("update", Session.currentParking, NumberPickerActivity.this);
//                    Intent _result = new Intent();
////                    _result.setData(Session.current);
//                    setResult(Activity.RESULT_OK, _result);
//                    finish();
//                }
//            }
//        });
    }


    @Override
    public void onPostExecute(Object o) {

    }
}/* NumberPickerActivity */
package com.example.hung.myapplication.change_space;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.NumberPicker;

import com.example.hung.myapplication.R;
import com.example.hung.myapplication.config.Session;

public class NumberPickerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.billing_day_dialog);
        NumberPicker np = (NumberPicker) findViewById(R.id.number_picker);
        if(Session.currentParking!=null) {
            np.setMinValue(Session.currentParking.getCurrentspace());// restricted number to minimum value i.e 1
            np.setMaxValue(Session.currentParking.getTotalspace());// restricked number to maximum value i.e. 31
        }
        np.setWrapSelectorWheel(true);

        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                // TODO Auto-generated method stub

                String Old = "Old Value : ";

                String New = "New Value : ";

            }
        });

        Log.d("NumberPicker", "NumberPicker");

    }

}/* NumberPickerActivity */
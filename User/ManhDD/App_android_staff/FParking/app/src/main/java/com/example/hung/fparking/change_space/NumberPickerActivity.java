package com.example.hung.fparking.change_space;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import com.example.hung.fparking.HomeActivity;
import com.example.hung.fparking.config.Session;
import com.example.hung.fparking.R;
import com.example.hung.fparking.config.Session;

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
        Button btnChangeSpace = (Button) findViewById(R.id.apply_button);
        btnChangeSpace.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if()
                Intent intent = new Intent(HomeActivity.this, NumberPickerActivity.class);
                startActivity(intent);
                // TODO Auto-generated method stub
            }
        });
    }

}/* NumberPickerActivity */
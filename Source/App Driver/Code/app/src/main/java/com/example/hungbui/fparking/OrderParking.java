package com.example.hungbui.fparking;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class OrderParking extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oder_parking);
         getSupportActionBar().hide();
        Intent intent = getIntent();
        Bundle bundlPosition = intent.getBundleExtra("BundlePosition");
        Order_Fragment(bundlPosition);

    }

    private void Order_Fragment(Bundle bundlPosition) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Order_Fragment order_fragment = new Order_Fragment();

        //gui positon cua bai do dung phuong thuc setArguments
        order_fragment.setArguments(bundlPosition);
        fragmentTransaction.add(R.id.fragmentOrder, order_fragment);
        fragmentTransaction.commit();
    }
}

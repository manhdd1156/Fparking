package com.example.hung.manhdd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MembersList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members_list);
        getSupportActionBar().hide();
    }
}

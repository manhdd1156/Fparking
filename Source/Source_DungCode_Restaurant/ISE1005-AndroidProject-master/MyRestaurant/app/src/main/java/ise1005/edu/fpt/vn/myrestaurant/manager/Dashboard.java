package ise1005.edu.fpt.vn.myrestaurant.manager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import ise1005.edu.fpt.vn.myrestaurant.R;

public class Dashboard extends AppCompatActivity implements View.OnClickListener {
    private Button menu, table, user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        menu = (Button) findViewById(R.id.btnMenu);
        table = (Button) findViewById(R.id.btnTable);
        user = (Button) findViewById(R.id.btnUser);
        menu.setOnClickListener(this);
        table.setOnClickListener(this);
        user.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
     switch (v.getId()){
         case R.id.btnMenu:
             startActivity(new Intent(this, Menu.class));
             break;
         case R.id.btnTable:
             startActivity(new Intent(this, Table.class));
             break;
         case R.id.btnUser:
             startActivity(new Intent(this, User.class));
             break;
     }
    }
}

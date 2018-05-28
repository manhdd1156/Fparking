package com.sourcey.materiallogindemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class ListParking extends ListActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_list_parking);
//    }


   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

     String[] teams = getResources().getStringArray(R.array.teams);

        // Binding resources Array to ListAdapter
        this.setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, R.id.textview, teams));

        ListView lv = getListView();

        // listening to single list item on click
        lv.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // selected item
                String team = ((TextView) view).getText().toString();

                // Launching new Activity on selecting single List Item
                Intent i = new Intent(getApplicationContext(), DetailParking.class);
                // sending data to new activity
                startActivity(i);

            }
        });
   }

}

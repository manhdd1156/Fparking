package com.example.hung.fparking;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hung.fparking.asynctask.GetRateTask;
import com.example.hung.fparking.asynctask.IAsyncTaskHandler;
import com.example.hung.fparking.asynctask.ManagerBookingTask;
import com.example.hung.fparking.asynctask.ManagerParkingTask;
import com.example.hung.fparking.config.Session;
import com.example.hung.fparking.dto.BookingDTO;
import com.example.hung.fparking.dto.ParkingDTO;
import com.example.hung.fparking.model.CheckNetwork;
import com.example.hung.fparking.other.Contact;
import com.example.hung.fparking.other.TermsAndConditions;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.hung.fparking.config.Constants.PICK_CONTACT_REQUEST;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IAsyncTaskHandler {
    ListView lv;
    TextView tvSpace, textViewMPhone;
    TextView tvAddress;
    NavigationView navigationView;
    View headerView;
    ImageView imageViewFParking;
    EditText tbPass;
    Button update;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ImageView backParkingManagement, addParking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        CheckNetworkReciever.thisregisterReceiver(CheckNetworkReciever, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
        Session.homeActivity = HomeActivity.this;
        //Ánh xạ
        tbPass = findViewById(R.id.tbPassHP);
        tbPass.setFocusable(false);
        update = findViewById(R.id.btnUpdate);
        tbPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentChangePass = new Intent(HomeActivity.this, ChangePassword.class);
                startActivity(intentChangePass);
            }
        });
        //Gọi alertDialog
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(HomeActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.activity_cf_pass_dialog, null);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });
        registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Session.homeActivity = HomeActivity.this;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        Session.container = this;
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//        backParkingManagement = findViewById(R.id.imageViewBackParking);
//        backParkingManagement.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intentBackParkingManagement = new Intent(ParkingManagement.this, HomeActivity.class);
//                startActivity(intentBackParkingManagement);
//                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//            }
//        });
        mRecyclerView = (RecyclerView) findViewById(R.id.parking_list_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
//        lv = (ListView) findViewById(R.id.cars_list);
//        if (Session.currentStaff != null) {
//            if (Session.currentParking == null) {
//                new ManagerParkingTask("get", Session.currentParking, HomeActivity.this);
//            } else {
////                tvSpace = (TextView) findViewById(R.id.tvSpace);
////                tvAddress = (TextView) findViewById(R.id.tvAddress);
//                setText(tvAddress, Session.currentParking.getAddress());
//                setText(tvSpace, Session.currentParking.getCurrentspace() + "/" + Session.currentParking.getTotalspace());
//
//            }
//
//            BookingDTO b = new BookingDTO();
////            b.setParkingID(Session.currentStaff.getParking_id());
        new ManagerParkingTask("getbyowner", null, this);
//            new ManagerBookingTask("homeget", b, this);
//        }

//        Button btnChangeSpace = (Button) findViewById(R.id.btnChange);
//        btnChangeSpace.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, NumberPickerActivity.class);
//                startActivityForResult(intent, PICK_CONTACT_REQUEST);
////                startActivity(intent);
//                // TODO Auto-generated method stub
//            }
//        });


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        headerView = navigationView.getHeaderView(0);
        textViewMPhone = headerView.findViewById(R.id.textViewMPhone);
//        textViewMPhone.setText(Session.currentStaff.getPhone());
        textViewMPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDriverInfo = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intentDriverInfo);
            }
        });
        imageViewFParking = headerView.findViewById(R.id.imageViewFParking);
        imageViewFParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDriverInfo = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intentDriverInfo);
            }
        });
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            CheckNetwork checkNetwork = new CheckNetwork(HomeActivity.this, getApplicationContext());
            if (!checkNetwork.isNetworkConnected()) {
                checkNetwork.createDialog();
            } else {
                System.out.println("đã có mạng");
//                recreate();
            }
        }
    };

    @Override
    public void recreate() {
//       super.recreate();
//        lv = (ListView) findViewById(R.id.cars_list);
//            if (Session.currentParking == null) {
//                new ManagerParkingTask("get", Session.currentParking, HomeActivity.this);
//            } else {
//                tvSpace = (TextView) findViewById(R.id.tvSpace);
//                tvAddress = (TextView) findViewById(R.id.tvAddress);
//                setText(tvAddress, Session.currentParking.getAddress());
//                setText(tvSpace, Session.currentParking.getCurrentspace() + "/" + Session.currentParking.getTotalspace());
//
//            }
        new ManagerParkingTask("get", Session.currentParking, HomeActivity.this);
        BookingDTO b = new BookingDTO();
//            b.setParkingID(Session.currentStaff.getParking_id());
        new GetRateTask(Session.currentParking.getId(), this).execute((Void) null);
        new ManagerBookingTask("homeget", b, this);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == PICK_CONTACT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                System.out.println("ở trong activityressult");
                setText(tvSpace, Session.currentParking.getCurrentspace() + "/" + Session.currentParking.getTotalspace());

                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.

                // Do something with the contact here (bigger example below)
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

    }

    private void setText(final TextView text, final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(value);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_parking) {
            // Handle the camera action
            Intent intentParking = new Intent(HomeActivity.this, ParkingManagement.class);
            startActivity(intentParking);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        } else if (id == R.id.nav_contact) {

            Intent intentContact = new Intent(HomeActivity.this, Contact.class);
            startActivity(intentContact);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (id == R.id.nav_members) {
            Intent intentStaff = new Intent(HomeActivity.this, StaffManagement.class);
            startActivity(intentStaff);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (id == R.id.nav_DK) {
            Intent intentDK = new Intent(HomeActivity.this, TermsAndConditions.class);
            startActivity(intentDK);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPostExecute(Object o) {
        System.out.println("O = " + o);
       final ArrayList<ParkingDTO> plist;
        try {
            if (o instanceof List) {
                plist = (ArrayList<ParkingDTO>) o;
                mAdapter = new MyRecyclerViewAdapter(plist);
                mRecyclerView.setAdapter(mAdapter);

                ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
                        .MyClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {
                        Intent intentDetail = new Intent(HomeActivity.this, DetailedParking.class);
                        intentDetail.putExtra("parkingid", plist.get(position).getId());
                        startActivity(intentDetail);
                        finish();
                    }
                });
            } else if (o instanceof String) {

            } else if (o instanceof Boolean) {

            }
        } catch (Exception e) {
            System.out.println("lỗi onPost ở HomeActivity : " + e);
        }
    }


}

class MyRecyclerViewAdapter extends RecyclerView
        .Adapter<MyRecyclerViewAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<ParkingDTO> mDataset;
    private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView label;
        TextView amount;
        TextView time;

        public DataObjectHolder(View itemView) {
            super(itemView);
//            label = (TextView) itemView.findViewById(R.id.textViewHLicenseplate);
//            amount = (TextView) itemView.findViewById(R.id.textViewAmount);
//            time = (TextView) itemView.findViewById(R.id.textViewTime);
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public MyRecyclerViewAdapter(ArrayList<ParkingDTO> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_detailed_parking, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
//        holder.label.setText(mDataset.get(position).getLicenseplate());
//        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
//        holder.amount.setText(currencyVN.format(mDataset.get(position).getAmount()).toString());
//        holder.time.setText(mDataset.get(position).getTimeIn().substring(8));
    }

    public void addItem(ParkingDTO dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}

package com.example.hung.fparkingowner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
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

import com.example.hung.fparkingowner.asynctask.IAsyncTaskHandler;
import com.example.hung.fparkingowner.asynctask.ManagerParkingTask;
import com.example.hung.fparkingowner.config.Session;
import com.example.hung.fparkingowner.dto.ParkingDTO;
import com.example.hung.fparkingowner.login.LoginActivity;
import com.example.hung.fparkingowner.model.CheckNetwork;
import com.example.hung.fparkingowner.other.Contact;
import com.example.hung.fparkingowner.other.Guide;
import com.example.hung.fparkingowner.other.TermsAndConditions;
import com.example.hung.fparkingowner.profile.ProfileActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.hung.fparkingowner.config.Constants.PICK_CONTACT_REQUEST;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IAsyncTaskHandler {
    TextView tvSpace, textViewMPhone;
    TextView tvTotalParking, tvTotalSpace;
    View headerView;
    ImageView imageViewFParking;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ImageView addParking;

    AlertDialog dialog;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_home);
            Session.homeActivity = HomeActivity.this;
            //Ánh xạ
            Session.spref = getSharedPreferences("intro", 0);
            editor = Session.spref.edit();
            addParking = findViewById(R.id.imageViewAddParking);
            addParking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentDK = new Intent(HomeActivity.this, AddParkingInformation.class);
                    startActivity(intentDK);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            });
            tvTotalParking = (TextView) findViewById(R.id.tvTotalParking);
            tvTotalSpace = (TextView) findViewById(R.id.tvTotalCar);
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
            mRecyclerView = (RecyclerView) findViewById(R.id.parking_list_view);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            new ManagerParkingTask("getbyowner", null, null, this);

            navigationView = (NavigationView) findViewById(R.id.nav_view);
            headerView = navigationView.getHeaderView(0);
            textViewMPhone = headerView.findViewById(R.id.textViewMPhone);
//        textViewMPhone.setText(Se);
            textViewMPhone.setText(Session.currentOwner.getPhone());
            textViewMPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentDriverInfo = new Intent(getApplicationContext(), ProfileActivity.class);
                    startActivity(intentDriverInfo);
                }
            });
            imageViewFParking = headerView.findViewById(R.id.imageViewFParking);
            imageViewFParking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentDriverInfo = new Intent(getApplicationContext(), ProfileActivity.class);
                    startActivity(intentDriverInfo);
                }
            });
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                CheckNetwork checkNetwork = new CheckNetwork(HomeActivity.this, getApplicationContext());
                if (!checkNetwork.isNetworkConnected()) {
                    checkNetwork.createDialog();
                } else {
                    System.out.println("đã có mạng");
//                recreate();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    };

    @Override
    public void recreate() {
        super.recreate();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        try {
            if (requestCode == PICK_CONTACT_REQUEST) {
                // Make sure the request was successful
                if (resultCode == RESULT_OK) {
                    System.out.println("ở trong activityressult");
                    setText(tvSpace, Session.currentParking.getCurrentspace() + "/" + Session.currentParking.getTotalspace());
                }
            }
        } catch (Exception e) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

    }

    private void setText(final TextView text, final String value) {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    text.setText(value);
                }
            });
        } catch (Exception e) {

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    public void onBackPressed() {
        try {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        } catch (Exception e) {

        }

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        try {
            int id = item.getItemId();


            if (id == R.id.nav_members) {
                Intent intentStaff = new Intent(HomeActivity.this, StaffManagement.class);
                startActivity(intentStaff);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            } else if (id == R.id.nav_statistic) {
                Intent intentStatistic = new Intent(HomeActivity.this, StatisticalActivity.class);
                startActivity(intentStatistic);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            } else if (id == R.id.nav_contact) {
                Intent intentContact = new Intent(HomeActivity.this, Contact.class);
                startActivity(intentContact);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            } else if (id == R.id.nav_DK) {
                Intent intent = new Intent(HomeActivity.this, TermsAndConditions.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            } else if (id == R.id.nav_guide) {
                Intent intentGuide = new Intent(HomeActivity.this, Guide.class);
                startActivity(intentGuide);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            } else if (id == R.id.Logout) {
                editor.putBoolean("first_time", true);
                editor.commit();
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                finish();
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }


            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

        } catch (Exception e) {

        }
        return true;
    }

    @Override
    public void onPostExecute(Object o) {
        System.out.println("O = " + o);
        final ArrayList<ParkingDTO> plist;
        try {
            if (o instanceof List) {
                plist = (ArrayList<ParkingDTO>) o;
                int totalSpace = 0;
                for (int i = 0; i < plist.size(); i++) {
                    totalSpace += plist.get(i).getTotalspace();
                }
                tvTotalSpace.setText(totalSpace + "");
                tvTotalParking.setText(plist.size() + "");
                mAdapter = new MyRecyclerViewAdapter(plist);
                mRecyclerView.setAdapter(mAdapter);
                ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
                        .MyClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {
                        finish();
                        Intent intentDetail = new Intent(HomeActivity.this, DetailParkingActivity.class);

                        intentDetail.putExtra("parkingid", plist.get(position).getId() + "");
                        Session.currentParking = plist.get(position);
                        startActivity(intentDetail);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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
        TextView address;
        TextView slot;
        ImageView imgCancel;

        public DataObjectHolder(View itemView) {
            super(itemView);
            try {
                address = (TextView) itemView.findViewById(R.id.tvAdreesPL);
                slot = (TextView) itemView.findViewById(R.id.tvSpacee);
                imgCancel = (ImageView) itemView.findViewById(R.id.imgCancel);
                Log.i(LOG_TAG, "Adding Listener");
                itemView.setOnClickListener(this);
            } catch (Exception e) {

            }
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
                .inflate(R.layout.parking_list, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        try {
            holder.address.setText(mDataset.get(position).getAddress());
            holder.slot.setText(mDataset.get(position).getTotalspace() + "");
            if (mDataset.get(position).getStatus() == 2) {
                holder.imgCancel.setBackgroundResource(R.drawable.warning);
                holder.imgCancel.setVisibility(View.VISIBLE);
                System.out.println("2" + mDataset.get(position).getAddress());
            } else if (mDataset.get(position).getStatus() == 3) {
                holder.imgCancel.setBackgroundResource(R.drawable.clock);
                holder.imgCancel.setVisibility(View.VISIBLE);
                System.out.println("3" + mDataset.get(position).getAddress());
            } else if (mDataset.get(position).getStatus() == 5) {
                holder.imgCancel.setBackgroundResource(R.drawable.close);
                holder.imgCancel.setVisibility(View.VISIBLE);
                System.out.println("5" + mDataset.get(position).getAddress());
            } else if (mDataset.get(position).getStatus() == 6) {
                holder.imgCancel.setBackgroundResource(R.drawable.delete);
                holder.imgCancel.setVisibility(View.VISIBLE);
                System.out.println("6" + mDataset.get(position).getAddress());
            }
        }catch (Exception e) {

        }
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

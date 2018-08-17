package com.example.hung.fparking;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hung.fparking.adapter.BookingRecyclerViewAdapter;
import com.example.hung.fparking.asynctask.ManagerNotiTask;
import com.example.hung.fparking.asynctask.VehicleTask;
import com.example.hung.fparking.asynctask.IAsyncTaskHandler;
import com.example.hung.fparking.asynctask.ManagerBookingTask;
import com.example.hung.fparking.asynctask.ManagerParkingTask;
import com.example.hung.fparking.change_space.NumberPickerActivity;
import com.example.hung.fparking.config.Constants;
import com.example.hung.fparking.config.Session;
import com.example.hung.fparking.dto.BookingDTO;
import com.example.hung.fparking.dto.TypeDTO;
import com.example.hung.fparking.dto.VehicleDTO;
import com.example.hung.fparking.login.LoginActivity;
import com.example.hung.fparking.model.CheckNetwork;
import com.example.hung.fparking.notification.Notification;
import com.example.hung.fparking.other.Contact;
import com.example.hung.fparking.other.Guide;
import com.example.hung.fparking.other.TermsAndConditions;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.synnapps.carouselview.CarouselView;

import in.goodiebag.carouselpicker.CarouselPicker;

import static com.example.hung.fparking.config.Constants.PICK_CONTACT_REQUEST;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IAsyncTaskHandler {
    ListView lv;
    TextView tvSpace, textViewMPhone;
    TextView tvAddress, error;
    Button btnOK;
    NavigationView navigationView;
    View headerView;
    ImageView imageViewFParking;
    private SharedPreferences.Editor editor;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private CarouselView customCarouselView;
    private CarouselPicker carouselPicker;
    private CarouselPicker carouselPickerCarType;
    public static AlertDialog dialog;
    private List<CarouselPicker.PickerItem> textItemsType;
    private ArrayList<TypeDTO> typeDTOS;
    private int vehicleIDSelected;
    private List<VehicleDTO> vList;
    Button btnAddCar, buttonOK, buttonCancel;
    String type;
    EditText editTextLS1, editTextLS2, editTextColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        CheckNetworkReciever.thisregisterReceiver(CheckNetworkReciever, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
        registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Session.homeActivity = HomeActivity.this;
        editor = Session.spref.edit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        Session.container = this;
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        lv = (ListView) findViewById(R.id.cars_list);


        // recyleview
        mRecyclerView = (RecyclerView) findViewById(R.id.booking_list_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        if (Session.currentStaff != null) {
            if (Session.currentParking == null) {
                new ManagerParkingTask("get", Session.currentParking, HomeActivity.this);
            } else {
                tvSpace = (TextView) findViewById(R.id.tvSpace);
                tvAddress = (TextView) findViewById(R.id.tvAddress);
                setText(tvAddress, Session.currentParking.getAddress());
                setText(tvSpace, Session.currentParking.getCurrentspace() + "/" + Session.currentParking.getTotalspace());

            }

            BookingDTO b = new BookingDTO();
            b.setParkingID(Session.currentStaff.getParking_id());
            new ManagerBookingTask("homeget", b, this);
        }

        Button btnChangeSpace = (Button) findViewById(R.id.btnChange);
        btnChangeSpace.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, NumberPickerActivity.class);
                startActivityForResult(intent, PICK_CONTACT_REQUEST);
//                startActivity(intent);
                // TODO Auto-generated method stub
            }
        });


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        headerView = navigationView.getHeaderView(0);
        textViewMPhone = headerView.findViewById(R.id.textViewMPhone);
        textViewMPhone.setText(Session.currentStaff.getPhone());
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
        Intent i = getIntent();

        if (i.getStringExtra("touchNoti") != null) {
            new ManagerNotiTask("get", new IAsyncTaskHandler() {
                @Override
                public void onPostExecute(Object o) {

                }
            });
        }
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
        b.setParkingID(Session.currentStaff.getParking_id());
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

            } else {
                showDialog("Vẫn còn xe trong bãi, thay đổi không thành công");
            }
        }
    }

    public void showDialog(String text) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(HomeActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.activity_alert_dialog, null);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        error = (TextView) mView.findViewById(R.id.tvAlert);
        btnOK = (Button) mView.findViewById(R.id.btnOK);
        error.setText(text);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
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

        if (id == R.id.nav_statistical) {
            // Handle the camera action
            Intent intent = new Intent(HomeActivity.this, StatisticalActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        } else if (id == R.id.nav_contact) {

            Intent intent = new Intent(HomeActivity.this, Contact.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (id == R.id.nav_DK) {
            Intent intent = new Intent(HomeActivity.this, TermsAndConditions.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (id == R.id.nav_guide) {
            Intent intent = new Intent(HomeActivity.this, Guide.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (id == R.id.Logout) {
            editor.putBoolean("first_time", true);
            editor.commit();
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            finish();
            startActivity(intent);
            Intent inttentPusher = new Intent(HomeActivity.this, Notification.class);
            stopService(inttentPusher);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showDialogChangeCar(final List<BookingDTO> bookingList, final int position) {
        // dialog đặt chỗ nhanh
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(HomeActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.quick_license_plate, null);
        mBuilder.setView(mView);
        mBuilder.setCancelable(true);
        dialog = mBuilder.create();

        carouselPicker = (CarouselPicker) mView.findViewById(R.id.carouselPickerLicensePlatesQLS);
        buttonOK = mView.findViewById(R.id.btnOKQLS);
        buttonCancel = mView.findViewById(R.id.btnCancelQLS);

        // sự kiện nút cancel dialog
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                showDialogAddNewCar(bookingList,position);
//
            }
        });

        // sự kiện nút ok dialog
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VehicleDTO vtemp = new VehicleDTO();
                vtemp.setDriverVehicleID(bookingList.get(position).getDriverVehicleID());
                vtemp.setVehicleID(vehicleIDSelected);
                new VehicleTask("update", vtemp, bookingList.get(position).getDriverID() + "", new IAsyncTaskHandler() {
                    @Override
                    public void onPostExecute(Object o) {
                        dialog.cancel();
                            if((boolean) o) {

                                showDialog("Đổi thành công");
                            }else {
                                showDialog("Đổi không thành công");
                            }
                    }
                });
            }
        });

        carouselPicker.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                vehicleIDSelected = vList.get(position).getVehicleID();
//                Log.e("drivervehicleID", vehicle.get(position).getDriverVehicleID() + "");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        new VehicleTask("select", null, bookingList.get(position).getDriverID() + "", new IAsyncTaskHandler() {
            @Override
            public void onPostExecute(Object o) {
                vList = (ArrayList<VehicleDTO>) o;
                ArrayList textItems = new ArrayList<>();
                if (vList.size() > 0) {
                    for (int i = 0; i < vList.size(); i++) {
                        textItems.add(new CarouselPicker.TextItem(vList.get(i).getLicenseplate(), 6)); // 5 is text size (sp)
                    }
                    vehicleIDSelected = vList.get(0).getVehicleID();
                    CarouselPicker.CarouselViewAdapter textAdapter = new CarouselPicker.CarouselViewAdapter(HomeActivity.this, textItems, 0);
                    carouselPicker.setAdapter(textAdapter);
                    dialog.show();
                }
            }
        });
    }


    public void showDialogAddNewCar(final List<BookingDTO> bookingList, int position) {
        final int pos = 0;
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(HomeActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.activity_dialog_add_car, null);
        mBuilder.setView(mView);
        dialog = mBuilder.create();

        carouselPickerCarType = (CarouselPicker) mView.findViewById(R.id.carouselPickerType);
        btnAddCar = mView.findViewById(R.id.btnAddCar);
        editTextLS1 = mView.findViewById(R.id.editTextLS1);
        editTextLS2 = mView.findViewById(R.id.editTextLS2);
        editTextColor = mView.findViewById(R.id.editTextColor);

        btnAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pattern p = Pattern.compile(Constants.regBs);
                Matcher m = p.matcher(editTextLS1.getText().toString());
                if (!m.find() || editTextLS2.getText().toString().length() < 4) {

                    showDialog("Biển số xe không hợp lệ!");
                } else {
                    VehicleDTO addnewcar = new VehicleDTO();
                    addnewcar.setType(type);
                    addnewcar.setColor(editTextColor.getText().toString());
                    addnewcar.setLicenseplate(editTextLS1.getText().toString().toUpperCase() + "-" + editTextLS2.getText().toString());
                    new VehicleTask("create", addnewcar, bookingList.get(pos).getDriverID() + "", new IAsyncTaskHandler() {
                        @Override
                        public void onPostExecute(Object o) {
                            dialog.cancel();
                            if((boolean) o) {
                                showDialog("Thêm hành công");
                            }else {
                                showDialog("Thêm không thành công");
                            }
                        }
                    });
                }
            }
        });
        carouselPickerCarType.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                type = textItemsType.get(position).getText().toString();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        new VehicleTask("type", null, bookingList.get(position).getDriverID() + "", new IAsyncTaskHandler() {
            @Override
            public void onPostExecute(Object o) {
                typeDTOS = (ArrayList<TypeDTO>) o;
                textItemsType = new ArrayList<>();
                System.out.println("==============");
                if (typeDTOS.size() > 0) {
                    for (int i = 0; i < typeDTOS.size(); i++) {
                        textItemsType.add(new CarouselPicker.TextItem(typeDTOS.get(i).getType(), 6)); // 5 is text size (sp)
                    }
                    type = typeDTOS.get(0).getType();
                }
                CarouselPicker.CarouselViewAdapter textAdapter = new CarouselPicker.CarouselViewAdapter(HomeActivity.this, textItemsType, 0);
                carouselPickerCarType.setAdapter(textAdapter);
                dialog.show();
            }
        });
    }

    @Override
    public void onPostExecute(Object o) {
        System.out.println("O = " + o);
        try {
            if (o instanceof List) {
                final ArrayList<BookingDTO> blist = (ArrayList<BookingDTO>) o;
                System.out.println(blist);
                mAdapter = new BookingRecyclerViewAdapter(blist, HomeActivity.this, this);
                mRecyclerView.setAdapter(mAdapter);
                ((BookingRecyclerViewAdapter) mAdapter).setOnItemClickListener(new BookingRecyclerViewAdapter
                        .MyClickListener() {
                    @Override
                    public void onItemClick(final int position, View v) {
                        showDialogChangeCar(blist, position);

                    }

                });


            } else if (o instanceof String) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int choice) {
                        switch (choice) {
                            case DialogInterface.BUTTON_POSITIVE:
//                                BookingDTO b = new BookingDTO();
//                                b.setParkingID(Session.currentParking.getId());
//                                b.setStatus(3);
//                                new ManagerBookingTask("updatebystatus", b, null);
                                recreate();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                try {
                    final DateFormat df = new SimpleDateFormat("hh:mm:ss dd-MM-yyyy");
                    Date datein = df.parse(Session.bookingTemp.getTimein());
                    Date dateout = df.parse(Session.bookingTemp.getTimeout());

                    NumberFormat currencyVN = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                    long diff = dateout.getTime() - datein.getTime();
                    double diffInHours = diff / ((double) 1000 * 60 * 60);
                    if(diffInHours<1) {
                        diffInHours =1;
                    }
                    NumberFormat formatter = new DecimalFormat("###,###");
                    NumberFormat formatterHour = new DecimalFormat("0");
                    builder.setMessage("\t\t\t\t\t\t\t\t\t\tHÓA ĐƠN THANH TOÁN \n\n"
                            + "            Biển số : " + Session.bookingTemp.getLicensePlate() + "\n"
                            + "            Loại xe : " + Session.bookingTemp.getTypeCar() + "\n"
                            + " Thời gian vào : " + Session.bookingTemp.getTimein() + "\n"
                            + "    Thời gian ra : " + Session.bookingTemp.getTimeout() + "\n"
                            + "        Giá gửi xe : " + formatter.format(Session.bookingTemp.getPrice()) + " vnđ/giờ\n"
                            + "   Thời gian đỗ : " + formatterHour.format(diffInHours) + " giờ \n"
                            + "Số tiền bị phạt : " + currencyVN.format(Session.bookingTemp.getTotalfine()) + "\n"
                            + "          Tổng giá : " + formatter.format(Session.bookingTemp.getAmount()) + " vnđ")
                            .setPositiveButton("Xác nhận", dialogClickListener).setCancelable(false).show();

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else if (o instanceof Boolean) {

            }
        } catch (Exception e) {
            System.out.println("lỗi onPost ở HomeActivity : " + e);
        }
    }


}


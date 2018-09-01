package com.example.hung.fparking;

import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hung.fparking.adapter.StatisticRecyclerViewAdapter;
import com.example.hung.fparking.asynctask.IAsyncTaskHandler;
import com.example.hung.fparking.asynctask.ManagerBookingTask;
import com.example.hung.fparking.asynctask.ManagerParkingTask;
import com.example.hung.fparking.config.Session;
import com.example.hung.fparking.dto.BookingDTO;
import com.example.hung.fparking.dto.ParkingDTO;
import com.example.hung.fparking.model.CheckNetwork;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class StatisticalActivity extends AppCompatActivity implements IAsyncTaskHandler {
    private static final String TAG = "Statistical";

    TextView tvTotalMoney, tvParkingFines, tvTotalCar, tvToDate, tvFromDate, error;
    ImageView backStatistical;
    Button btnOK;
    LinearLayout chooseFromDate, chooseToDate;
    Button btnShow;
    Date fromDate;
    Date toDate;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int DEFAULT_TYPE = 0;
    private DatePickerDialog.OnDateSetListener mDateSetListener1;
    private DatePickerDialog.OnDateSetListener mDateSetListener2;
    MaterialBetterSpinner betterSpinner;
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistical);
        registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
//        lv = (ListView) findViewById(R.id.cars_list2);
        tvFromDate = (TextView) findViewById(R.id.tvFromD);
        tvToDate = (TextView) findViewById(R.id.tvToD);
        tvTotalCar = (TextView) findViewById(R.id.tvTotalCar);
        tvTotalMoney = (TextView) findViewById(R.id.tvMoney);
        tvParkingFines = (TextView) findViewById(R.id.tvParkingFines);
        chooseFromDate = (LinearLayout) findViewById(R.id.chooseFromDate);
        chooseToDate = findViewById(R.id.chooseToDate);
        btnShow = (Button) findViewById(R.id.btnStat);
        backStatistical = findViewById(R.id.imageViewBackStatistical);
        //recycleView
        mRecyclerView = (RecyclerView) findViewById(R.id.statistic_list_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        backStatistical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Session.homeActivity.recreate();
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        BookingDTO b = new BookingDTO();
        b.setParkingID(Session.currentStaff.getParking_id());
        new ManagerBookingTask("statisticget", b, StatisticalActivity.this);

        chooseFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        StatisticalActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener1, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        chooseToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        StatisticalActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener2, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");

                if (toDate != null && fromDate != null && toDate.getTime() <= fromDate.getTime()) {
                    System.out.println("từ ngày : " + dateFormatter.format(fromDate) + " ; đến ngày :" + dateFormatter.format(toDate));
                    showDialog("Hãy chọn thời gian kết thúc muộn hơn");
                } else {
                    BookingDTO b = new BookingDTO();
                    b.setParkingID(Session.currentStaff.getParking_id());
                    new ManagerBookingTask("statisticget", b, StatisticalActivity.this);

                }

            }
        });
        mDateSetListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                try {
                    month = month + 1;
                    Log.d(TAG, "onDateSet: yyy/mm/dd: " + year + "-" + month + "-" + day);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

                    String monthh = month + "";
                    String dayy = day + "";

                    if (month < 10) {

                        monthh = "0" + month;
                    }
                    if (day < 10) {
                        dayy = "0" + day;
                    }

                    String date = dayy + "-" + monthh + "-" + year;

                    fromDate = sdf.parse(date);


                    tvFromDate.setText(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };
        mDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                try {
                    month = month + 1;
                    Log.d(TAG, "onDateSet: yyy/mm/dd: " + year + "-" + month + "-" + day);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    String monthh = month + "";
                    String dayy = day + "";

                    if (month < 10) {

                        monthh = "0" + month;
                    }
                    int daytemp = day+1;
                    String dayytemp = daytemp + "";
                    if (day < 10) {
                        dayy = "0" + day;
                        dayytemp = "0" + daytemp;

                    }

                    String date = dayy + "-" + monthh + "-" + year;
                    toDate = sdf.parse(date + " 24:00:00");
//                    if(toDate.getTime()>fromDate.getTime())

                    tvToDate.setText(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };


    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            CheckNetwork checkNetwork = new CheckNetwork(StatisticalActivity.this, getApplicationContext());
            if (!checkNetwork.isNetworkConnected()) {
                checkNetwork.createDialog();
            } else {
//                recreate();
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
    public void setAdapterView(ArrayList<BookingDTO> blist) {
        mAdapter = new StatisticRecyclerViewAdapter(blist, StatisticalActivity.this, this);
        mRecyclerView.setAdapter(mAdapter);
        ((StatisticRecyclerViewAdapter) mAdapter).setOnItemClickListener(new StatisticRecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(final int position, View v) {

            }

        });
    }

    public void showDialog(String text) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(StatisticalActivity.this);
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
    public String formatMoney(double money) {
        NumberFormat formatter = new DecimalFormat("###,###");
        int temp = (int) money;
        String returnMoney = formatter.format(temp);
        if (temp > 1000000 && temp < 1000000000) {
            if(temp %1000000 <999)  {
                returnMoney = temp / 1000000 + "tr";
            }else {
                returnMoney = temp / 1000000 + "," + (temp % 1000000) / 1000 + "tr";
            }
        } else if (temp > 1000000000) {
            if(temp %1000000000 <999999)  {
                returnMoney = temp / 1000000000 + "tỷ";
            }else {
                returnMoney = temp / 1000000000 + "," + (temp % 1000000000) / 1000000 + "tỷ";
            }
        }
        return returnMoney;
    }
    @Override
    public void onPostExecute(Object o) {
        ArrayList<BookingDTO> lstBooking = (ArrayList<BookingDTO>) o;
        if(lstBooking!=null && lstBooking.size()>0) {
            for (int i = 0; i < lstBooking.size(); i++) {
                if (lstBooking.get(i).getTimein().equals("null") && lstBooking.get(i).getTimeout().equals("null")) {
                    lstBooking.remove(lstBooking.get(i));
                    i--;
                }
            }

        ArrayList<BookingDTO> lstBookingAdapter = new ArrayList<>();



        double money = 0;
        final NumberFormat formatter = new DecimalFormat("###,###");
        try {
            if (toDate == null && fromDate == null) {
                ParkingDTO p = new ParkingDTO();
                p.setId(Session.currentParking.getId());
                p.setTimeoc("");
                p.setAddress("");
                p.setStatus(0);
                new ManagerParkingTask("getFines", p, new IAsyncTaskHandler() {
                    @Override
                    public void onPostExecute(Object o) {
                        tvParkingFines.setText(formatMoney(Double.parseDouble(o.toString()))+" vnđ");
                    }
                });
                for (int i = 0; i < lstBooking.size(); i++) {
                    if(lstBooking.get(i).getTimeout().equals("null") || lstBooking.get(i).getTimeout().isEmpty())
                        continue;
                    lstBookingAdapter.add(lstBooking.get(i));
                    money += lstBooking.get(i).getAmount();
                }
                tvTotalCar.setText(lstBookingAdapter.size() + "");


                tvTotalMoney.setText(formatMoney(money) + " vnđ");
                setAdapterView(lstBookingAdapter);
//                ListBookingStatisticAdapter arrayAdapter = new ListBookingStatisticAdapter(this, lstBooking, this);
//                lv.setAdapter(arrayAdapter);

            } else {

                if (fromDate != null && toDate == null) {
                    ParkingDTO p = new ParkingDTO();
                    p.setId(Session.currentParking.getId());
                    p.setTimeoc(fromDate.getTime()+"");
                    p.setAddress("");
                    p.setStatus(1);
                    new ManagerParkingTask("getFines", p, new IAsyncTaskHandler() {
                        @Override
                        public void onPostExecute(Object o) {
                            tvParkingFines.setText(formatMoney(Double.parseDouble(o.toString()))+" vnđ");
                        }
                    });
                    for (int i = 0; i < lstBooking.size(); i++) {
                        System.out.println("<" + lstBooking.get(i).getTimeout()+">");
                        final DateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
                        if(lstBooking.get(i).getTimeout().equals("null") || lstBooking.get(i).getTimeout().isEmpty())
                            continue;
                        Date datein = dateFormatter.parse(lstBooking.get(i).getTimeout());
                    System.out.println("ngày dbi       = " + dateFormatter.format(datein));
                    System.out.println("từ ngày = " + dateFormatter.format(fromDate));
                        if (datein.getTime() >= fromDate.getTime()) {
                            System.out.println("chọn");
                            lstBookingAdapter.add(lstBooking.get(i));
                            money += lstBooking.get(i).getAmount();

                        }

                    }

                } else if (fromDate == null && toDate != null) {
                    ParkingDTO p = new ParkingDTO();
                    p.setId(Session.currentParking.getId());
                    p.setTimeoc("");
                    p.setAddress(toDate.getTime()+"");
                    p.setStatus(1);
                    new ManagerParkingTask("getFines", p, new IAsyncTaskHandler() {
                        @Override
                        public void onPostExecute(Object o) {
                            tvParkingFines.setText(formatMoney(Double.parseDouble(o.toString()))+" vnđ");
                        }
                    });
                    System.out.println("toDate2 = " + toDate.getTime());
                    for (int i = 0; i < lstBooking.size(); i++) {
                        if(lstBooking.get(i).getTimeout()==null)
                            continue;
                        final DateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
                        Date datein = dateFormatter.parse(lstBooking.get(i).getTimeout());
                    System.out.println("ngày dbi       = " + dateFormatter.format(datein));
                    System.out.println("đến ngày = " + dateFormatter.format(toDate));
                        if (datein.getTime() <= toDate.getTime()) {
                            System.out.println("chọn");
                            lstBookingAdapter.add(lstBooking.get(i));
                            money += lstBooking.get(i).getAmount();

                        }

                    }
                } else if (fromDate != null && toDate != null) {
                    ParkingDTO p = new ParkingDTO();
                    p.setId(Session.currentParking.getId());
                    p.setTimeoc(fromDate.getTime()+"");
                    p.setAddress(toDate.getTime()+"");
                    p.setStatus(1);
                    new ManagerParkingTask("getFines", p, new IAsyncTaskHandler() {
                        @Override
                        public void onPostExecute(Object o) {
                            tvParkingFines.setText(formatMoney(Double.parseDouble(o.toString()))+" vnđ");
                        }
                    });
                    System.out.println("fromDate3 =" + fromDate.getTime()  + " ;;;  toDate3 = " + toDate.getTime());
                    for (int i = 0; i < lstBooking.size(); i++) {
                        if(lstBooking.get(i).getTimeout()==null)
                            continue;
                        final DateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
                        Date datein = dateFormatter.parse(lstBooking.get(i).getTimeout());
//                        System.out.println("timeOut3 = " +datein.toString());
                    System.out.println(" ====A :" + dateFormatter.format(fromDate));
                    System.out.println(" ====B :" + dateFormatter.format(datein));
                    System.out.println(" ====C :" + dateFormatter.format(toDate));
                        if (fromDate != null && toDate != null && datein.getTime() >= fromDate.getTime() && datein.getTime() <= toDate.getTime()) {
                            System.out.println("chọn");
                            lstBookingAdapter.add(lstBooking.get(i));
                            money += lstBooking.get(i).getAmount();
                        }
                    }
                }
                tvTotalCar.setText(lstBookingAdapter.size() + "");

                tvTotalMoney.setText(formatMoney(money) + " vnđ");
                setAdapterView(lstBookingAdapter);
//                ListBookingStatisticAdapter arrayAdapter = new ListBookingStatisticAdapter(this, lstBookingAdapter, this);
//                lv.setAdapter(arrayAdapter);
            }
        } catch (ParseException e) {
            System.out.println("lỗi in onPost StatisticalActivity : " + e);
        }
        Log.d("Statistical_onPost: ", lstBooking.toString());
        }
    }
}
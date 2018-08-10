package com.example.hung.fparking;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hung.fparking.adapter.ListBookingHomeAdapter;
import com.example.hung.fparking.adapter.ListBookingStatisticAdapter;
import com.example.hung.fparking.asynctask.GetRateTask;
import com.example.hung.fparking.asynctask.IAsyncTaskHandler;
import com.example.hung.fparking.asynctask.ManagerBookingTask;
import com.example.hung.fparking.config.Session;
import com.example.hung.fparking.dto.BookingDTO;
import com.example.hung.fparking.model.CheckNetwork;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class StatisticalActivity extends AppCompatActivity implements IAsyncTaskHandler {
    private static final String TAG = "Statistical";
    ListView lv;
    TextView tvFromDate;
    TextView tvToDate;
    TextView tvTotalCar;
    TextView tvTotalMoney;
    TextView tvDateError;
    ImageView backStatistical;
    Button btnShow;
    Date fromDate;
    Date toDate;
    private int DEFAULT_TYPE = 0;
    private DatePickerDialog.OnDateSetListener mDateSetListener1;
    private DatePickerDialog.OnDateSetListener mDateSetListener2;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistical);
        registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        lv = (ListView) findViewById(R.id.cars_list2);
        tvFromDate = (TextView) findViewById(R.id.tvFromDate);
        tvToDate = (TextView) findViewById(R.id.tvToDate);
        tvTotalCar = (TextView) findViewById(R.id.tvTotalCar);
        tvTotalMoney = (TextView) findViewById(R.id.tvMoney);
        tvDateError = (TextView) findViewById(R.id.tvDateError);
        btnShow = (Button) findViewById(R.id.btnStat);
        backStatistical = findViewById(R.id.imageViewBackStatistical);
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
        tvToDate.setOnClickListener(new View.OnClickListener() {
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
        tvFromDate.setOnClickListener(new View.OnClickListener() {
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
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toDate != null && fromDate != null && toDate.getTime() < fromDate.getTime()) {
                    tvDateError.setVisibility(View.VISIBLE);
                } else {
                    tvDateError.setVisibility(View.INVISIBLE);
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
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                    String monthh = month + "";
                    String dayy = day + "";

                    if (month < 10) {

                        monthh = "0" + month;
                    }
                    if (day < 10) {
                        dayy = "0" + day;
                    }

                    String date = year + "-" + monthh + "-" + dayy;

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
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String monthh = month + "";
                    String dayy = day + "";

                    if (month < 10) {

                        monthh = "0" + month;
                    }
                    if (day < 10) {
                        dayy = "0" + day;
                    }

                    String date = year + "-" + monthh + "-" + dayy;

                    toDate = sdf.parse(date);
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
    public void onPostExecute(Object o) {
        List<BookingDTO> lstBooking = (List<BookingDTO>) o;
        List<BookingDTO> lstBookingAdapter = new ArrayList<>();
        double money = 0;
        if (toDate==null && fromDate==null) {
            for (int i = 0; i < lstBooking.size(); i++) {
                money += lstBooking.get(i).getAmount();
            }
            tvTotalCar.setText(lstBooking.size() + "");
            NumberFormat formatter = new DecimalFormat("###,###");
            tvTotalMoney.setText(formatter.format(money) + " vnđ");
            ListBookingStatisticAdapter arrayAdapter = new ListBookingStatisticAdapter(this, lstBooking, this);
            lv.setAdapter(arrayAdapter);

        } else if(toDate != null || fromDate != null){
            for (int i = 0; i < lstBooking.size(); i++) {
                final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                final DateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
                try {
                    Date datein = dateFormatter.parse(lstBooking.get(i).getTimeout());
                    if (fromDate != null && lstBooking.get(i).getTimeout().isEmpty() && datein.getTime() <= fromDate.getTime()) {
                        lstBookingAdapter.add(lstBooking.get(i));
                        money += lstBooking.get(i).getAmount();
                        continue;
                    } else if (fromDate!=null && toDate!=null && datein.getTime() >= fromDate.getTime() && datein.getTime() <= toDate.getTime()) {
                        lstBookingAdapter.add(lstBooking.get(i));
                        money += lstBooking.get(i).getAmount();
                    }
                } catch (ParseException e) {
                    System.out.println("lỗi in onPost StatisticalActivity : " + e);
                }
            }
//            System.out.println("size = " + lstBookingAdapter.size() + ";");
            tvTotalCar.setText(lstBookingAdapter.size() + "");
            NumberFormat formatter = new DecimalFormat("###,###");

            tvTotalMoney.setText(formatter.format(money) + " vnđ");
            ListBookingStatisticAdapter arrayAdapter = new ListBookingStatisticAdapter(this, lstBookingAdapter, this);
            lv.setAdapter(arrayAdapter);

        }
        Log.d("Statistical_onPost: ", lstBooking.toString());

    }
}
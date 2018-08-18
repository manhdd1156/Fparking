package com.example.hung.fparkingowner;

import android.app.Activity;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hung.fparkingowner.asynctask.GetBookingTask;
import com.example.hung.fparkingowner.asynctask.IAsyncTaskHandler;
import com.example.hung.fparkingowner.asynctask.ManagerParkingTask;
import com.example.hung.fparkingowner.config.Session;
import com.example.hung.fparkingowner.dto.BookingDTO;
import com.example.hung.fparkingowner.dto.ParkingDTO;
import com.example.hung.fparkingowner.model.CheckNetwork;
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

    TextView tvTotalMoney, tvDateError, tvParkingFines, tvTotalCar, tvToDate, tvFromDate, error;
    ImageView backStatistical;
    Button btnOK;
    LinearLayout chooseFromDate, chooseToDate;
    Button btnShow;
    ArrayList<String> listParkingString;

    Date fromDate;
    Date toDate;
    ArrayList<ParkingDTO> listParkingDTO;
    String[] PARKINGLIST = {};
    private int parkingidSelected;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Spinner sprinerParking;
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
        sprinerParking = (Spinner) findViewById(R.id.spinnerStatistic);
        listParkingString = new ArrayList<>();
        //recycleView
        mRecyclerView = (RecyclerView) findViewById(R.id.statistic_list_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        new ManagerParkingTask("getbyowner", null, null, new IAsyncTaskHandler() {
            @Override
            public void onPostExecute(Object o) {
                System.out.println("oooooooooooooooooo = " + o);
                listParkingDTO = (ArrayList<ParkingDTO>) o;
                if (listParkingDTO.size() > 0) {
                    for(int i = 0; i< listParkingDTO.size(); i++) {
                        listParkingString.add(listParkingDTO.get(i).getAddress());
                    }
                    System.out.println("========================= " + listParkingString);
                    ArrayAdapter<String> adapter = new ArrayAdapter(StatisticalActivity.this, android.R.layout.simple_spinner_item, listParkingString);
                    adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                    sprinerParking.setAdapter(adapter);
                    sprinerParking.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            parkingidSelected = listParkingDTO.get(sprinerParking.getSelectedItemPosition()).getId();
                            System.out.println(parkingidSelected);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                }
            }
        });
        backStatistical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Session.homeActivity.recreate();
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        new GetBookingTask(StatisticalActivity.this).execute((Void) null);

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
                if (toDate != null && fromDate != null && toDate.getTime() < fromDate.getTime()) {
                    showDialog("Hãy chọn thời gian kết thúc muộn hơn");
                } else {
                    BookingDTO b = new BookingDTO();
//                    b.setParkingID(Session.currentStaff.getParking_id());
                    new GetBookingTask(StatisticalActivity.this).execute((Void) null);

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
                    day++;
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

    @Override
    public void onPostExecute(Object o) {
        ArrayList<BookingDTO> lstBooking = (ArrayList<BookingDTO>) o;
        ArrayList<BookingDTO> lstBookingAdapter = new ArrayList<>();


        double money = 0;
        final NumberFormat formatter = new DecimalFormat("###,###");
        try {

            if (fromDate == null && toDate == null && String.valueOf(parkingidSelected) == null) {         // khi không chọn gì cả, thì select all
                ArrayList<ParkingDTO> plistTemp = new ArrayList<>();
                for (int i = 0; i < listParkingDTO.size(); i++) {
                    ParkingDTO p = new ParkingDTO();
                    p.setId(plistTemp.get(i).getId());
                    p.setTimeoc("");
                    p.setAddress("");
                    p.setStatus(0);
                    plistTemp.add(p);

                }
                new ManagerParkingTask("getFines", null, plistTemp, new IAsyncTaskHandler() {
                    @Override
                    public void onPostExecute(Object o) {
                        tvParkingFines.setText(formatter.format(Double.parseDouble(o.toString())) + "vnđ");
                    }
                });
                for (int i = 0; i < lstBooking.size(); i++) {
                    if (lstBooking.get(i).getTimeout().equals("null") || lstBooking.get(i).getTimeout().isEmpty())
                        continue;
                    lstBookingAdapter.add(lstBooking.get(i));
                    money += lstBooking.get(i).getAmount();
                }

            } else if (fromDate != null && toDate == null && String.valueOf(parkingidSelected) == null) {            // khi chọn ngày mà k chọn bãi xe
                ArrayList<ParkingDTO> plistTemp = new ArrayList<>();
                for (int i = 0; i < listParkingDTO.size(); i++) {
                    ParkingDTO p = new ParkingDTO();
                    p.setId(plistTemp.get(i).getId());
                    p.setTimeoc(fromDate.getTime() + "");
                    p.setAddress("");
                    p.setStatus(1);
                    plistTemp.add(p);
                }
                new ManagerParkingTask("getFines", null, plistTemp, new IAsyncTaskHandler() {
                    @Override
                    public void onPostExecute(Object o) {
                        tvParkingFines.setText(o.toString());
                    }
                });
                for (int i = 0; i < lstBooking.size(); i++) {
                    System.out.println("<" + lstBooking.get(i).getTimeout() + ">");
                    final DateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
                    if (lstBooking.get(i).getTimeout().equals("null") || lstBooking.get(i).getTimeout().isEmpty())
                        continue;
                    Date datein = dateFormatter.parse(lstBooking.get(i).getTimeout());
                    if (datein.getTime() >= fromDate.getTime()) {
                        lstBookingAdapter.add(lstBooking.get(i));
                        money += lstBooking.get(i).getAmount();

                    }

                }
            } else if (fromDate == null && toDate != null && String.valueOf(parkingidSelected) == null) {                // khi chọn ngày mà k chọn bãi xe
                ArrayList<ParkingDTO> plistTemp = new ArrayList<>();
                for (int i = 0; i < listParkingDTO.size(); i++) {
                    ParkingDTO p = new ParkingDTO();
                    p.setId(plistTemp.get(i).getId());
                    p.setTimeoc("");
                    p.setAddress(toDate.getTime() + "");
                    p.setStatus(1);
                    plistTemp.add(p);
                }
                new ManagerParkingTask("getFines", null, plistTemp, new IAsyncTaskHandler() {
                    @Override
                    public void onPostExecute(Object o) {
                        tvParkingFines.setText(o.toString());
                    }
                });
                for (int i = 0; i < lstBooking.size(); i++) {
                    if (lstBooking.get(i).getTimeout() == null)
                        continue;
                    final DateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
                    Date datein = dateFormatter.parse(lstBooking.get(i).getTimeout());
                    if (datein.getTime() <= toDate.getTime()) {
                        System.out.println("chọn");
                        lstBookingAdapter.add(lstBooking.get(i));
                        money += lstBooking.get(i).getAmount();

                    }

                }
            } else if (fromDate != null && toDate != null && String.valueOf(parkingidSelected) == null) {                // khi chọn ngày mà k chọn bãi xe
                ArrayList<ParkingDTO> plistTemp = new ArrayList<>();
                for (int i = 0; i < listParkingDTO.size(); i++) {
                    ParkingDTO p = new ParkingDTO();
                    p.setId(plistTemp.get(i).getId());
                    p.setTimeoc(fromDate.getTime() + "");
                    p.setAddress(toDate.getTime() + "");
                    p.setStatus(1);
                    plistTemp.add(p);
                }
                new ManagerParkingTask("getFines", null, plistTemp, new IAsyncTaskHandler() {
                    @Override
                    public void onPostExecute(Object o) {
                        tvParkingFines.setText(o.toString());
                    }
                });
                for (int i = 0; i < lstBooking.size(); i++) {
                    if (lstBooking.get(i).getTimeout() == null)
                        continue;
                    final DateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
                    Date datein = dateFormatter.parse(lstBooking.get(i).getTimeout());
//                        System.out.println("timeOut3 = " +datein.toString());
                    if (fromDate != null && toDate != null && datein.getTime() >= fromDate.getTime() && datein.getTime() <= toDate.getTime()) {
                        lstBookingAdapter.add(lstBooking.get(i));
                        money += lstBooking.get(i).getAmount();
                    }
                }
            } else if (toDate == null && fromDate == null && String.valueOf(parkingidSelected) != null) {                  // khi k chọn ngày mà chọn bãi xe

                ParkingDTO p = new ParkingDTO();
                p.setId(parkingidSelected);
                p.setTimeoc("");
                p.setAddress("");
                p.setStatus(0);

                new ManagerParkingTask("getFines", p, null, new IAsyncTaskHandler() {
                    @Override
                    public void onPostExecute(Object o) {
                        tvParkingFines.setText(formatter.format(Double.parseDouble(o.toString())) + "vnđ");
                    }
                });
                for (int i = 0; i < lstBooking.size(); i++) {
                    if (lstBooking.get(i).getTimeout().equals("null") || lstBooking.get(i).getTimeout().isEmpty())
                        continue;
                    lstBookingAdapter.add(lstBooking.get(i));
                    money += lstBooking.get(i).getAmount();
                }

            } else if (fromDate != null && toDate == null && String.valueOf(parkingidSelected) != null) {                 // khi  chọn cả ngày và bãi xe
                ParkingDTO p = new ParkingDTO();
                p.setId(parkingidSelected);
                p.setTimeoc(fromDate.getTime() + "");
                p.setAddress("");
                p.setStatus(1);
                new ManagerParkingTask("getFines", p, null, new IAsyncTaskHandler() {
                    @Override
                    public void onPostExecute(Object o) {
                        tvParkingFines.setText(o.toString());
                    }
                });
                for (int i = 0; i < lstBooking.size(); i++) {
                    System.out.println("<" + lstBooking.get(i).getTimeout() + ">");
                    final DateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
                    if (lstBooking.get(i).getTimeout().equals("null") || lstBooking.get(i).getTimeout().isEmpty())
                        continue;
                    Date datein = dateFormatter.parse(lstBooking.get(i).getTimeout());
                    System.out.println("timeOut1       = " + datein.toString());
                    System.out.println("timeFromeDate1 = " + fromDate.toString());
                    if (datein.getTime() >= fromDate.getTime()) {
                        System.out.println("chọn");
                        lstBookingAdapter.add(lstBooking.get(i));
                        money += lstBooking.get(i).getAmount();

                    }

                }

            } else if (fromDate == null && toDate != null && String.valueOf(parkingidSelected) != null) {
                ParkingDTO p = new ParkingDTO();
                p.setId(parkingidSelected);
                p.setTimeoc("");
                p.setAddress(toDate.getTime() + "");
                p.setStatus(1);
                new ManagerParkingTask("getFines", p, null, new IAsyncTaskHandler() {
                    @Override
                    public void onPostExecute(Object o) {
                        tvParkingFines.setText(o.toString());
                    }
                });
                System.out.println("toDate2 = " + toDate.getTime());
                for (int i = 0; i < lstBooking.size(); i++) {
                    if (lstBooking.get(i).getTimeout() == null)
                        continue;
                    final DateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
                    Date datein = dateFormatter.parse(lstBooking.get(i).getTimeout());
                    System.out.println("timeOut2       = " + datein.toString());
                    System.out.println("timeFromeDate2 = " + toDate.toString());
                    if (datein.getTime() <= toDate.getTime()) {
                        System.out.println("chọn");
                        lstBookingAdapter.add(lstBooking.get(i));
                        money += lstBooking.get(i).getAmount();

                    }

                }
            } else if (fromDate != null && toDate != null && String.valueOf(parkingidSelected) != null) {
                ParkingDTO p = new ParkingDTO();
                p.setId(parkingidSelected);
                p.setTimeoc(fromDate.getTime() + "");
                p.setAddress(toDate.getTime() + "");
                p.setStatus(1);
                new ManagerParkingTask("getFines", p, null, new IAsyncTaskHandler() {
                    @Override
                    public void onPostExecute(Object o) {
                        tvParkingFines.setText(o.toString());
                    }
                });
                System.out.println("fromDate3 =" + fromDate.getTime() + " ;;;  toDate3 = " + toDate.getTime());
                for (int i = 0; i < lstBooking.size(); i++) {
                    if (lstBooking.get(i).getTimeout() == null)
                        continue;
                    final DateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
                    Date datein = dateFormatter.parse(lstBooking.get(i).getTimeout());
//                        System.out.println("timeOut3 = " +datein.toString());
                    System.out.println(" ====A :" + fromDate.toString());
                    System.out.println(" ====B :" + datein.toString());
                    System.out.println(" ====C :" + toDate.toString());
                    if (fromDate != null && toDate != null && datein.getTime() >= fromDate.getTime() && datein.getTime() <= toDate.getTime()) {
                        System.out.println("chọn");
                        lstBookingAdapter.add(lstBooking.get(i));
                        money += lstBooking.get(i).getAmount();
                    }
                }
            }
            tvTotalCar.setText(lstBookingAdapter.size() + "");

            tvTotalMoney.setText(formatter.format(money) + "vnđ");
            setAdapterView(lstBookingAdapter);

//                ListBookingStatisticAdapter arrayAdapter = new ListBookingStatisticAdapter(this, lstBookingAdapter, this);
//                lv.setAdapter(arrayAdapter);

        } catch (ParseException e) {
            System.out.println("lỗi in onPost StatisticalActivity : " + e);
        }
        Log.d("Statistical_onPost: ", lstBooking.toString());

    }
}

class StatisticRecyclerViewAdapter extends RecyclerView
        .Adapter<StatisticRecyclerViewAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "StatisticRecyclerViewAdapter";
    private ArrayList<BookingDTO> mDataset;
    private static MyClickListener myClickListener;
    AlertDialog dialog;
    EditText confirmPasssOwner;
    Button btnConfirmPassOwner, btnOK;
    TextView tvError, error;
    private Context mContext;
    private Activity activity;
    private IAsyncTaskHandler container;


    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView licenseplate, typecar, parkingfines, amount, time;

        public DataObjectHolder(View itemView) {
            super(itemView);
            licenseplate = (TextView) itemView.findViewById(R.id.tvItemLicense);
            typecar = (TextView) itemView.findViewById(R.id.tvItemTypeCar);
            parkingfines = (TextView) itemView.findViewById(R.id.tvItemFines);
            amount = (TextView) itemView.findViewById(R.id.tvItemAmount);
            time = (TextView) itemView.findViewById(R.id.tvItemTime);

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

    public StatisticRecyclerViewAdapter(ArrayList<BookingDTO> myDataset, IAsyncTaskHandler container, Context mContext) {
        this.mContext = mContext;
        this.container = container;
        this.activity = (Activity) container;
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_statistic_list, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        NumberFormat formatter = new DecimalFormat("###,###");
        final DateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        holder.licenseplate.setText(mDataset.get(position).getLicensePlate());
        holder.typecar.setText(mDataset.get(position).getTypeCar());
        holder.parkingfines.setText(formatter.format(mDataset.get(position).getTotalfine()) + "vnđ");
        holder.amount.setText(formatter.format(mDataset.get(position).getAmount()) + "vnđ");
        try {
            holder.time.setText(dateFormatter.format(dateFormatter.parse(mDataset.get(position).getTimeout())));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    public void addItem(BookingDTO dataObj, int index) {
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
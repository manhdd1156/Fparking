package com.fparking.pushertest.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.fparking.pushertest.MainActivity;
import com.fparking.pushertest.R;
import com.fparking.pushertest.asynctask.IAsyncTaskHandler;
import com.fparking.pushertest.asynctask.ManagerBookingTask;
import com.fparking.pushertest.dto.BookingDTO;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CarAdapter extends BaseAdapter {
    private Context mContext;
    private List<BookingDTO> listGrade;
    private View view;
    public CarAdapter(View view,Context context, List<BookingDTO> list) {
        mContext = context;
        listGrade = list;
        this.view = view;
    }

    @Override
    public int getCount() {
        return listGrade.size();
    }

    @Override
    public Object getItem(int pos) {
        return listGrade.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        final BookingDTO entry = listGrade.get(pos);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.cars_list, null);
        }
        TextView tvBienso = (TextView) convertView.findViewById(R.id.tvBienso);
        TextView tvTypeCar = (TextView) convertView.findViewById(R.id.tvTypeCar);
        final Button btnAcept=(Button)convertView.findViewById(R.id.btnAccept);
        Button btnCancel=(Button)convertView.findViewById(R.id.btnCancel);

        if (entry.getStatus().equals("1")) {
            btnAcept.setText("CheckIn");
            btnAcept.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    Calendar calendar = Calendar.getInstance();
                    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String checkinTime = dateFormatter.format(calendar.getTime().getTime());
                    BookingDTO b = entry;
                    b.setStatus("2");
                    b.setCheckinTime(checkinTime.toString());
                    new ManagerBookingTask("update",view.getContext(),view, null, null,null, b);
//                    btnAcept.setText("CHeckOut");
                    Intent refresh = new Intent(mContext, MainActivity.class);
                    mContext.startActivity(refresh);
                }
            });
        }else if(entry.getStatus().equals("2")){
            btnCancel.setVisibility(convertView.INVISIBLE);
            btnAcept.setText("CheckOut");
            btnAcept.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    Calendar calendar = Calendar.getInstance();
                    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    final String checkoutTime = dateFormatter.format(calendar.getTime().getTime());
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int choice) {
                            switch (choice) {
                                case DialogInterface.BUTTON_POSITIVE:

                                    BookingDTO b = entry;
                                    b.setStatus("3");
                                    b.setCheckoutTime(checkoutTime.toString());
                                    new ManagerBookingTask("update",view.getContext(),view, null, null,null, b);

                                    Intent refresh = new Intent(mContext, MainActivity.class);
//                                    refresh.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                    mContext.startActivity(refresh);
//                                    ((Activity) mContext.getApplicationContext()).recreate();
//                                    ActivityCompat.finishAffinity(mContext);

                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    try {
                        Date date1 = dateFormatter.parse(entry.getCheckinTime());
                        Date date2 = dateFormatter.parse(checkoutTime);
                        long diff = date2.getTime() - date1.getTime();
                        double diffInHours = diff / ((double) 1000 * 60 * 60);
                        NumberFormat formatter = new DecimalFormat("###,###");
                        NumberFormat formatterHour = new DecimalFormat("0.00");
                        System.out.println(formatter.format(4.0));
                        builder.setMessage("\tHóa đơn checkout \n"
                                +"Biển số :          " + entry.getLicensePlate() +"\n"
                                +"loại xe :           " + entry.getType()+"\n"
                                +"thời gian vào : " + entry.getCheckinTime()+"\n"
                                +"thời gian ra :   " + checkoutTime +"\n"
                                +"giá đỗ :           " + formatter.format(entry.getPrice()) + "vnđ\n"
                                +"thời gian đỗ :  " + formatterHour.format(diffInHours) +" giờ \n"
                                +"tổng giá :        " + formatter.format(diffInHours * entry.getPrice())+"vnđ")
                                .setPositiveButton("Yes", dialogClickListener)
                                .setNegativeButton("No", dialogClickListener).show();

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }




//                            Toast.makeText(MainActivity.this,"save",Toast.LENGTH_SHORT).show();

                }
            });
        }

        return convertView;
    }
}
package com.example.hung.fparking.adapter;

import android.app.Activity;
//import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.hung.fparking.HomeActivity;
import com.example.hung.fparking.R;
import com.example.hung.fparking.asynctask.IAsyncTaskHandler;
import com.example.hung.fparking.asynctask.ManagerBookingTask;
import com.example.hung.fparking.dto.BookingDTO;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by Klot on 15/7/2018.
 */

public class ListBookingAdapter extends ArrayAdapter<BookingDTO> {
    Button show_dailog;
    private List<BookingDTO> dataSet;
    Context mContext;
    Activity activity;
    IAsyncTaskHandler container;

    public ListBookingAdapter(IAsyncTaskHandler container, List<BookingDTO> dataSet, Context mContext) {
        super(mContext, R.layout.cars_list, dataSet);
        this.dataSet = dataSet;
        this.mContext = mContext;
        this.container = container;
        this.activity = (Activity) container;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        // Check if an existing view is being reused, otherwise inflate the view
        final BookingDTO dataModel = (BookingDTO) getItem(position);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.cars_list, null);
        }
        TextView tvBienso = (TextView) convertView.findViewById(R.id.tvBienso);
        TextView tvTypeCar = (TextView) convertView.findViewById(R.id.tvTypeCar);
        TextView tvRating = (TextView) convertView.findViewById(R.id.tvRating);
        final Button btnAcept = (Button) convertView.findViewById(R.id.btnAccept);
        Button btnCancel = (Button) convertView.findViewById(R.id.btnCancel);
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSZ");
        if (dataModel.getStatus() == 1) {
            btnAcept.setText("VÀO BÃI");
            btnAcept.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    final Date datetimein = new Date();
                    dataModel.setStatus(2);
                    dataModel.setTimein(df.format(datetimein));
                    new ManagerBookingTask("update", dataModel, container);
                    btnAcept.setText("THANH TOÁN");
                    reload();
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    BookingDTO b = dataModel;
                    b.setStatus(0);
                    new ManagerBookingTask("update", b, container);
                    reload();
                }
            });
        } else if (dataModel.getStatus() == 2) {
            btnCancel.setVisibility(parent.INVISIBLE);
            btnAcept.setText("THANH TOÁN");
            btnAcept.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
////                    FragmentManager fm = activity.getFragmentManager();
//                    DialogActivity mydialogfragment = DialogActivity.newInstance("Nguyễn Văn Linh");
////                    mydialogfragment.show(fm, null);
//                    mydialogfragment.show(fm,"myTag");


                    // TODO Auto-generated method stub
                    final DateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
//                    final DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSZ");
                    final Date datetimeout = new Date();
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int choice) {
                            switch (choice) {
                                case DialogInterface.BUTTON_POSITIVE:
//                                        dialog.dismiss();
                                    dataModel.setStatus(3);
                                    dataModel.setTimeout(df.format(datetimeout));
                                    new ManagerBookingTask("update", dataModel, container);
//                                    onResume();
                                    reload();
                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:

                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    try {
                        Date date1 = df.parse(dataModel.getTimein());
                        String dateformat1 = dateFormatter.format(date1);
                        String dateformat2 = dateFormatter.format(datetimeout);
                        long diff = datetimeout.getTime() - date1.getTime();
                        double diffInHours = diff / ((double) 1000 * 60 * 60);
                        NumberFormat formatter = new DecimalFormat("###,###");
                        NumberFormat formatterHour = new DecimalFormat("0.00");
                        String totalPrice = formatter.format(diffInHours * dataModel.getPrice());
                        if (diffInHours < 1) {
                            totalPrice = dataModel.getPrice() + "";
                        }
                        builder.setMessage("\tHóa đơn checkout \n"
                                + "Biển số :          " + dataModel.getLicensePlate() + "\n"
                                + "loại xe :           " + dataModel.getTypeCar() + "\n"
                                + "thời gian vào : " + dateformat1 + "\n"
                                + "thời gian ra :   " + dateformat2 + "\n"
                                + "giá đỗ :           " + formatter.format(dataModel.getPrice()) + "vnđ\n"
                                + "thời gian đỗ :  " + formatterHour.format(diffInHours) + " giờ \n"
                                + "tổng giá :        " + totalPrice + "vnđ")
                                .setPositiveButton("Yes", dialogClickListener)
                                .setNegativeButton("No", dialogClickListener).setCancelable(false).show();

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
        tvBienso.setText(dataModel.getLicensePlate());
        tvTypeCar.setText(dataModel.getTypeCar());
        if (dataModel.getRating() < 3) {
            tvRating.setText("Kém");
        } else if (dataModel.getRating() >= 3) {
            tvRating.setText("Tốt");
        }
        return convertView;
    }

    public void reload() {

        Intent intent = new Intent(getContext(), HomeActivity.class);
        activity.overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        activity.finish();

        activity.overridePendingTransition(0, 0);
        activity.startActivity(intent);
    }
}
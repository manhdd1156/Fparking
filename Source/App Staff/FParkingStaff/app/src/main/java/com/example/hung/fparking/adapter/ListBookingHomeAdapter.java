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
import com.example.hung.fparking.config.Session;
import com.example.hung.fparking.dialog.DialogActivity;
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

public class ListBookingHomeAdapter extends ArrayAdapter<BookingDTO> {
    Button show_dailog;
    private List<BookingDTO> dataSet;
    Context mContext;
    Activity activity;
    IAsyncTaskHandler container;

    public ListBookingHomeAdapter(IAsyncTaskHandler container, List<BookingDTO> dataSet, Context mContext) {
        super(mContext, R.layout.layout_cars_list, dataSet);
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
            convertView = inflater.inflate(R.layout.layout_cars_list, null);
        }
        TextView tvBienso = (TextView) convertView.findViewById(R.id.tvBienso);
        TextView tvTypeCar = (TextView) convertView.findViewById(R.id.tvTypeCar);
        TextView tvRating = (TextView) convertView.findViewById(R.id.tvRating);
        final Button btnAcept = (Button) convertView.findViewById(R.id.btnAccept);
        Button btnCancel = (Button) convertView.findViewById(R.id.btnCancel);
//        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSZ");
        final DateFormat df = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
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
                    activity.recreate();
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int choice) {
                            switch (choice) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    BookingDTO b = dataModel;
                                    b.setDrivervehicleID(dataModel.getDrivervehicleID());
                                    b.setStatus(0);
                                    new ManagerBookingTask("update", b, container);
                                    activity.recreate();
                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                    NumberFormat formatter = new DecimalFormat("###,###");
                    NumberFormat formatterHour = new DecimalFormat("0.00");
                    builder.setMessage("Bạn sẽ bị phạt nếu hủy đặt chỗ này!\n Bạn có muốn tiếp tục ?")
                            .setPositiveButton("Xác nhận", dialogClickListener)
                            .setNegativeButton("Hủy", dialogClickListener).setCancelable(false).show();


                }
            });
        } else if (dataModel.getStatus() == 2) {
            btnCancel.setVisibility(parent.INVISIBLE);
            btnAcept.setText("THANH TOÁN");
            btnAcept.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    dataModel.setStatus(3);
                    dataModel.setTimeout(df.format(new Date()));
                    System.out.println("nhấn nút thanh toán ");
                    new ManagerBookingTask("update", dataModel, container);

                    BookingDTO b = new BookingDTO();
                    b.setParkingID(Session.currentParking.getId());
                    b.setStatus(3);
                    new ManagerBookingTask("getInfoCheckout", b, container);


                }
            });
        }
        tvBienso.setText(dataModel.getLicensePlate());
        tvTypeCar.setText(dataModel.getTypeCar());
        if (dataModel.getRating() < 3) {
            tvRating.setText("Không Tốt");
        } else if (dataModel.getRating() >= 3) {
            tvRating.setText("Tốt");
        }
        return convertView;
    }

}
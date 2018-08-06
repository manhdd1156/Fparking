package com.example.hung.fparkingowner.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.hung.fparking.R;
import com.example.hung.fparkingowner.asynctask.IAsyncTaskHandler;
import com.example.hung.fparkingowner.dto.BookingDTO;

import java.util.List;

//import android.app.FragmentManager;


/**
 * Created by Klot on 15/7/2018.
 */

public class ListBookingStatisticAdapter extends ArrayAdapter<BookingDTO> {
    Button show_dailog;
    private List<BookingDTO> dataSet;
    Context mContext;
    Activity activity;
    IAsyncTaskHandler container;

    public ListBookingStatisticAdapter(IAsyncTaskHandler container, List<BookingDTO> dataSet, Context mContext) {
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

        System.out.println("listbookingstatisticAdapter");
        final BookingDTO dataModel = (BookingDTO) getItem(position);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.layout_cars_list2, null);
        }
        TextView tvBienso = (TextView) convertView.findViewById(R.id.tvBienso2);
        TextView tvTypeCar = (TextView) convertView.findViewById(R.id.tvTypeCar2);
        TextView tvRating = (TextView) convertView.findViewById(R.id.tvRating2);
//        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSZ");

        tvBienso.setText(dataModel.getLicensePlate());
        tvTypeCar.setText(dataModel.getTypeCar());
        if (dataModel.getRating() < 3) {
            tvRating.setText("Không tốt");
        } else if (dataModel.getRating() >= 3) {
            tvRating.setText("Tốt");
        }
        return convertView;
    }

}
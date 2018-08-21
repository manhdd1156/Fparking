package com.example.hung.fparking.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hung.fparking.R;
import com.example.hung.fparking.asynctask.IAsyncTaskHandler;
import com.example.hung.fparking.asynctask.ManagerBookingTask;
import com.example.hung.fparking.dto.BookingDTO;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StatisticRecyclerViewAdapter extends RecyclerView
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
        TextView licenseplate,typecar,parkingfines,amount,time;

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
        holder.parkingfines.setText(formatMoney(mDataset.get(position).getTotalfine())+" vnđ");
        holder.amount.setText(formatMoney(mDataset.get(position).getAmount())+" vnđ");
        try {
            holder.time.setText(dateFormatter.format(dateFormatter.parse(mDataset.get(position).getTimeout())));
        } catch (ParseException e) {
            e.printStackTrace();
        }

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
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BookingRecyclerViewAdapter extends RecyclerView
        .Adapter<BookingRecyclerViewAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "BookingRecyclerViewAdapter";
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
        TextView licenseplate,typecar;
        Button btnAcept,btnCancel;

        public DataObjectHolder(View itemView) {
            super(itemView);
            licenseplate = (TextView) itemView.findViewById(R.id.tvItemLicense);
            typecar = (TextView) itemView.findViewById(R.id.tvItemTypeCar);
            btnAcept = (Button) itemView.findViewById(R.id.btnItemAccept);
            btnCancel = (Button) itemView.findViewById(R.id.btnItemCancel);
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

    public BookingRecyclerViewAdapter(ArrayList<BookingDTO> myDataset,IAsyncTaskHandler container,Context mContext) {
        this.mContext = mContext;
        this.container = container;
        this.activity = (Activity) container;
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_bookings_list, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }
    public void showDialog(String text) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
        View mView =  activity.getLayoutInflater().inflate(R.layout.activity_alert_dialog, null);
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
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        holder.licenseplate.setText(mDataset.get(position).getLicensePlate());
        holder.typecar.setText(mDataset.get(position).getTypeCar());
        final DateFormat df = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        if (mDataset.get(position).getStatus() == 1) {
            holder.btnAcept.setText("VÀO BÃI");
            holder.btnAcept.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // TODO Auto-generated method stub
                    final Date datetimein = new Date();
                    mDataset.get(position).setStatus(2);
                    mDataset.get(position).setTimein(df.format(datetimein));
                    new ManagerBookingTask("update", mDataset.get(position), container);
                    holder.btnAcept.setText("THANH TOÁN");
                    activity.recreate();
                }
            });
            holder.btnCancel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int choice) {
                            switch (choice) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    BookingDTO b = mDataset.get(position);
                                    b.setDriverID(mDataset.get(position).getDriverID());
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

//                    NumberFormat formatter = new DecimalFormat("###,###");
//                    NumberFormat formatterHour = new DecimalFormat("0.00");
                    builder.setMessage("Bạn sẽ bị phạt nếu hủy đơn đặt chỗ này!\n Bạn có muốn tiếp tục ?")
                            .setPositiveButton("Xác nhận", dialogClickListener)
                            .setNegativeButton("Hủy", dialogClickListener).setCancelable(false).show();


                }
            });
        } else if (mDataset.get(position).getStatus() == 2) {
            holder.btnCancel.setVisibility(View.INVISIBLE);
            holder.btnAcept.setText("THANH TOÁN");
            holder.btnAcept.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    mDataset.get(position).setStatus(3);
                    mDataset.get(position).setTimeout(df.format(new Date()));
                    System.out.println("nhấn nút thanh toán ");
                    new ManagerBookingTask("update", mDataset.get(position), new IAsyncTaskHandler() {
                        @Override
                        public void onPostExecute(Object o) {

                        }
                    });

                    new ManagerBookingTask("getInfoCheckout", mDataset.get(position),container);
                }
            });
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
package com.example.hung.fparking.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hung.fparking.HomeActivity;
import com.example.hung.fparking.R;
import com.example.hung.fparking.asynctask.IAsyncTaskHandler;
import com.example.hung.fparking.asynctask.ManagerBookingTask;
import com.example.hung.fparking.config.Session;
import com.example.hung.fparking.dialog.DialogActivity;
import com.example.hung.fparking.dto.BookingDTO;
import com.example.hung.fparking.notification.Notification;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BookingRecyclerViewAdapter extends RecyclerView
        .Adapter<BookingRecyclerViewAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "StaffRecyclerViewAdapter";
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
                .inflate(R.layout.bookings_list, parent, false);

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

                    NumberFormat formatter = new DecimalFormat("###,###");
                    NumberFormat formatterHour = new DecimalFormat("0.00");
                    builder.setMessage("Bạn sẽ bị phạt nếu hủy đặt chỗ này!\n Bạn có muốn tiếp tục ?")
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

//                    BookingDTO b = new BookingDTO();
//                    b.setParkingID(Session.currentParking.getId());
//                    b.setStatus(3);
//                    mDataset.get(position).setStatus(3);
//                    new ManagerBookingTask("getInfoCheckout", mDataset.get(position), new IAsyncTaskHandler() {
//                        @Override
//                        public void onPostExecute(Object o) {
//                            if(o instanceof String) {
////                                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
////                                    @Override
////                                    public void onClick(DialogInterface dialog, int choice) {
////                                        switch (choice) {
////                                            case DialogInterface.BUTTON_POSITIVE:
//////                                BookingDTO b = new BookingDTO();
//////                                b.setParkingID(Session.currentParking.getId());
//////                                b.setStatus(3);
//////                                new ManagerBookingTask("updatebystatus", b, null);
////                                                Session.homeActivity.recreate();
////                                                break;
////                                        }
////                                    }
////                                };
//
////                                AlertDialog.Builder builder = new AlertDialog.Builder(Session.homeActivity);
//                                try {
//                                    final DateFormat df = new SimpleDateFormat("hh:mm:ss dd-MM-yyyy");
//                                    Date datein = df.parse(Session.bookingTemp.getTimein());
//                                    Date dateout = df.parse(Session.bookingTemp.getTimeout());
//
//                                    NumberFormat currencyVN = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
//                                    long diff = dateout.getTime() - datein.getTime();
//                                    double diffInHours = diff / ((double) 1000 * 60 * 60);
//                                    NumberFormat formatter = new DecimalFormat("###,###");
//                                    NumberFormat formatterHour = new DecimalFormat("0.00");
////                                    builder.setMessage("\t\t\t\t\t\t\t\t\t\tHÓA ĐƠN THANH TOÁN \n\n"
////                                            + "            Biển số : " + Session.bookingTemp.getLicensePlate() + "\n"
////                                            + "            Loại xe : " + Session.bookingTemp.getTypeCar() + "\n"
////                                            + " Thời gian vào : " + Session.bookingTemp.getTimein() + "\n"
////                                            + "    Thời gian ra : " + Session.bookingTemp.getTimeout() + "\n"
////                                            + "           Giá gửi xe : " + formatter.format(Session.bookingTemp.getPrice()) + " vnđ/giờ\n"
////                                            + "   Thời gian đỗ : " + formatterHour.format(diffInHours) + " giờ \n"
////                                            + "Số tiền bị phạt : " + currencyVN.format(Session.bookingTemp.getTotalfine()) + "\n"
////                                            + "          Tổng giá : " + formatter.format(Session.bookingTemp.getAmount()) + " vnđ")
////                                            .setPositiveButton("Yes", dialogClickListener).setCancelable(false).show();
////                                    showDialog();
//                                    Intent i = new Intent(mContext, AlertDialog.class);
//                                    i.putExtra("StringAleart", "\t\t\t\t\t\t\t\t\t\tHÓA ĐƠN THANH TOÁN \n\n"
//                                            + "            Biển số : " + Session.bookingTemp.getLicensePlate() + "\n"
//                                            + "            Loại xe : " + Session.bookingTemp.getTypeCar() + "\n"
//                                            + " Thời gian vào : " + Session.bookingTemp.getTimein() + "\n"
//                                            + "    Thời gian ra : " + Session.bookingTemp.getTimeout() + "\n"
//                                            + "           Giá gửi xe : " + formatter.format(Session.bookingTemp.getPrice()) + " vnđ/giờ\n"
//                                            + "   Thời gian đỗ : " + formatterHour.format(diffInHours) + " giờ \n"
//                                            + "Số tiền bị phạt : " + currencyVN.format(Session.bookingTemp.getTotalfine()) + "\n"
//                                            + "          Tổng giá : " + formatter.format(Session.bookingTemp.getAmount()) + " vnđ");
////                                    i.putExtra("data", data);
//                                    activity.startActivity(i);
//
//                                } catch (ParseException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//                    });

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
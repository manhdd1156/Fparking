package com.example.hung.fparking;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hung.fparking.asynctask.BookingTask;
import com.example.hung.fparking.asynctask.IAsyncTaskHandler;
import com.example.hung.fparking.asynctask.VehicleTask;
import com.example.hung.fparking.config.Session;
import com.example.hung.fparking.dto.BookingDTO;
import com.example.hung.fparking.dto.VehicleDTO;

import java.util.ArrayList;

public class CarsList extends AppCompatActivity implements IAsyncTaskHandler {

    ImageView backCarsList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    AlertDialog.Builder builder;

    ArrayList<VehicleDTO> vehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_lists);

        //ánh xạ
        backCarsList = findViewById(R.id.imageViewBackCarsList);
        backCarsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backHistoryIntent = new Intent(CarsList.this, HomeActivity.class);
                startActivity(backHistoryIntent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        //set adapter list
        mRecyclerView = (RecyclerView) findViewById(R.id.cars_list_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
//        mAdapter = new MyRecyclerViewAdapter(getDataSet());
//        mRecyclerView.setAdapter(mAdapter);

        VehicleDTO v = new VehicleDTO();
        new VehicleTask("select", v, "vt", this);

        builder = new AlertDialog.Builder(CarsList.this);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
//                .MyClickListener() {
//            @Override
//            public void onItemClick(int position, View v) {
//                Log.i("adsdasdasdasdas", " Clicked on Item " + position);
//            }
//        });
    }

    private void DeleteVehicle(VehicleDTO v){
        new VehicleTask("delete", v, "vt", this);
    }

    @Override
    public void onPostExecute(Object o, String action) {
        vehicle = (ArrayList<VehicleDTO>) o;
        mAdapter = new CarsListViewAdapter(vehicle);
        mRecyclerView.setAdapter(mAdapter);

        ((CarsListViewAdapter) mAdapter).setOnItemClickListener(new CarsListViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {

            }

            @Override
            public void onDeleteItemClick(final String licensePlate, View v) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int choice) {
                        switch (choice) {
                            case DialogInterface.BUTTON_POSITIVE:
                                VehicleDTO v = new VehicleDTO();
                                v.setLicenseplate(licensePlate);
                                DeleteVehicle(v);
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:

                                break;
                        }
                    }
                };
                builder.setMessage("Chủ bãi đỗ đang bận!")
                        .setPositiveButton("Đồng Ý", dialogClickListener).setCancelable(false)
                        .setNegativeButton("Hủy", dialogClickListener).show();
            }
        });
    }
}

class CarsListViewAdapter extends RecyclerView
        .Adapter<CarsListViewAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<VehicleDTO> mDataset;
    private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView licensePlate;
        TextView type;
        ImageView imageDelete;
//        TextView time;

        public DataObjectHolder(View itemView) {
            super(itemView);
            licensePlate = (TextView) itemView.findViewById(R.id.textViewLicensePlate);
            type = (TextView) itemView.findViewById(R.id.textViewType);
            imageDelete = itemView.findViewById(R.id.imageViewDelete);
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

    public CarsListViewAdapter(ArrayList<VehicleDTO> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cars_list, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, final int position) {
        holder.licensePlate.setText(mDataset.get(position).getLicenseplate());
        holder.type.setText("Loại Xe: Xe " + mDataset.get(position).getType());
        holder.imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClickListener.onDeleteItemClick(mDataset.get(position).getLicenseplate(), v);
            }
        });


//        holder.time.setText(mDataset.get(position).getTimeIn().substring(8));
    }

    public void addItem(VehicleDTO dataObj, int index) {
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

        public void onDeleteItemClick(String licensePlate, View v);
    }
}

package com.example.hung.fparking;

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
import com.example.hung.fparking.config.Session;
import com.example.hung.fparking.dto.BookingDTO;

import java.util.ArrayList;

public class ParkingHistory extends AppCompatActivity implements IAsyncTaskHandler {

    ImageView backHistory;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ArrayList<BookingDTO> booking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_history);
        //ánh xạ
        backHistory = findViewById(R.id.imageViewBackHistory);
        backHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backHistoryIntent = new Intent(ParkingHistory.this,HomeActivity.class);
                startActivity(backHistoryIntent);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });

        //set adapter list
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
//        mAdapter = new MyRecyclerViewAdapter(getDataSet());
//        mRecyclerView.setAdapter(mAdapter);

        new BookingTask("phone", Session.currentDriver.getPhone(),"", "", this);

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

    @Override
    public void onPostExecute(Object o, String action) {
        booking = (ArrayList<BookingDTO>) o;
        mAdapter = new MyRecyclerViewAdapter(booking);
        mRecyclerView.setAdapter(mAdapter);

                ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new MyRecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i("adsdasdasdasdas", " Clicked on Item " + position);
            }
        });
    }
}

class MyRecyclerViewAdapter extends RecyclerView
        .Adapter<MyRecyclerViewAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<BookingDTO> mDataset;
    private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView label;
        TextView dateTime;

        public DataObjectHolder(View itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.textViewHLicenseplate);
            dateTime = (TextView) itemView.findViewById(R.id.textViewAmount);
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

    public MyRecyclerViewAdapter(ArrayList<BookingDTO> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_list, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.label.setText(mDataset.get(position).getLicenseplate());
        holder.dateTime.setText(mDataset.get(position).getAmount()+"");
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

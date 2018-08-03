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
import com.example.hung.fparking.asynctask.FineTask;
import com.example.hung.fparking.asynctask.IAsyncTaskHandler;
import com.example.hung.fparking.config.Session;
import com.example.hung.fparking.dto.BookingDTO;
import com.example.hung.fparking.dto.FineDTO;

import java.util.ArrayList;

public class FineHistory extends AppCompatActivity implements IAsyncTaskHandler {

    ImageView backFineHistory;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ArrayList<FineDTO> fineDTOS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fine_history);

        //ánh xạ
        backFineHistory = findViewById(R.id.imageViewBackHistory);
        backFineHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backHistoryIntent = new Intent(FineHistory.this, HomeActivity.class);
                startActivity(backHistoryIntent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        //set adapter list
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        new FineTask("select", null, "", FineHistory.this);

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
        fineDTOS = (ArrayList<FineDTO>) o;
        mAdapter = new FinerViewAdapter(fineDTOS);
        mRecyclerView.setAdapter(mAdapter);

        ((FinerViewAdapter) mAdapter).setOnItemClickListener(new FinerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Intent intentDetail = new Intent(FineHistory.this, DetailedHistory.class);
                intentDetail.putExtra("bookingid", fineDTOS.get(position).getFineID());
                startActivity(intentDetail);
            }
        });
    }
}

class FinerViewAdapter extends RecyclerView
        .Adapter<FinerViewAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<FineDTO> mDataset;
    private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView label;
        TextView amount;
        TextView time;

        public DataObjectHolder(View itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.textViewHLicenseplate);
            amount = (TextView) itemView.findViewById(R.id.textViewAmount);
            time = (TextView) itemView.findViewById(R.id.textViewTime);
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

    public FinerViewAdapter(ArrayList<FineDTO> myDataset) {
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
        holder.amount.setText(mDataset.get(position).getPrice() + "");
        holder.time.setText(mDataset.get(position).getDate().substring(8));
    }

    public void addItem(FineDTO dataObj, int index) {
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

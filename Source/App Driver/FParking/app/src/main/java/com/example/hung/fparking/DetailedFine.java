package com.example.hung.fparking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hung.fparking.asynctask.BookingTask;
import com.example.hung.fparking.asynctask.FineTask;
import com.example.hung.fparking.asynctask.IAsyncTaskHandler;
import com.example.hung.fparking.dto.BookingDTO;
import com.example.hung.fparking.dto.FineDTO;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class DetailedFine extends AppCompatActivity implements IAsyncTaskHandler {

    TextView textViewAddress, textViewTime, textViewPrice, textViewLicensePlate, textViewTotalPrice, textViewType;
    ArrayList<FineDTO> fineDTOS;
    ImageView backDetailedHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_fine);

        // ánh xạ
        textViewLicensePlate = findViewById(R.id.textBSDF);
        textViewType = findViewById(R.id.textViewLSDF);
        textViewTotalPrice = findViewById(R.id.textViewAMDF);
        textViewAddress = findViewById(R.id.textViewAddressDF);
        textViewTime = findViewById(R.id.textViewTotalTimeDF);
        backDetailedHistory = findViewById(R.id.imageViewBackDetailedHistory);

        backDetailedHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHistory = new Intent(DetailedFine.this, FineHistory.class);
                startActivity(intentHistory);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        // get data intent
        Intent intent = getIntent();
        int fineID = intent.getIntExtra("fineID", 0);

        FineDTO fineDTO = new FineDTO();
        fineDTO.setFineID(fineID);

        new FineTask("getone", fineDTO, "", DetailedFine.this);
    }

    @Override
    public void onPostExecute(Object o, String action) {
        fineDTOS = (ArrayList<FineDTO>) o;
        if (fineDTOS.size() > 0) {
            FineDTO fineDTO = fineDTOS.get(0);

            textViewLicensePlate.setText(fineDTO.getLicenseplate());
            textViewType.setText(fineDTO.getVehicletype());
            NumberFormat currencyVN = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            textViewTotalPrice.setText(currencyVN.format(fineDTO.getPrice()).toString());
            textViewAddress.setText(fineDTO.getAddress());
            textViewTime.setText(fineDTO.getDate());
        }
    }
}

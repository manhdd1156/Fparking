package com.example.hungbui.fparking;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import Service.HttpHandler;
import in.goodiebag.carouselpicker.CarouselPicker;

public class Add_License_Plate extends DialogFragment {
    public String temp;

    public Add_License_Plate() {

    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    Button buttonOrder;
    CarouselPicker abc;
    Button buttonDemo;
    private ProgressDialog progressDialog;
    String demo = "chua qua count";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.license_plate_fragment, container, false);

        buttonOrder = view.findViewById(R.id.buttonOrderAddLicensePlate);
//        abc = view.findViewById(R.id.demo1);
////        Carousel with all text
//        List<CarouselPicker.PickerItem> textItems = new ArrayList<>();
//        textItems.add(new CarouselPicker.TextItem("2 chỗ",10));
//        textItems.add(new CarouselPicker.TextItem("5 chỗ",10));
//        textItems.add(new CarouselPicker.TextItem("7 chỗ",10));
//        textItems.add(new CarouselPicker.TextItem("9 chỗ",10));
//        textItems.add(new CarouselPicker.TextItem("16 chỗ",10));
//        textItems.add(new CarouselPicker.TextItem("29 chỗ",10));
//        textItems.add(new CarouselPicker.TextItem("45 chỗ",10));
//        CarouselPicker.CarouselViewAdapter textAdapter = new CarouselPicker.CarouselViewAdapter(getActivity(),textItems,0);
//        abc.setAdapter(textAdapter);


        buttonOrder.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                Log.e("Chay loading", "vl that");
                AlertDialog.Builder dialogConfirmOrder = new AlertDialog.Builder(getActivity());
                progressDialog = ProgressDialog.show(getActivity(), "Chờ bãi đậu xe xác nhận",
                        "Vui lòng chờ trong giây lát...!", true);
                new PushServer().execute();
                // getDialog().dismiss();
            }
        });
        return view;
    }

    public void GetData() {


    }

    private boolean handleDataMessage(String json) {
        return false;
    }

    class PushServer extends AsyncTask<Void, Void, Boolean> {

        boolean success = false;

        public PushServer() {
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(Void... voids) {

            HttpHandler httpHandler = new HttpHandler();
            try {
                JSONObject formData = new JSONObject();
                System.out.println(formData.toString());
                formData.put("carID", "2");
                String json = httpHandler.post("https://fparking.net/realtimeTest/driver/booking.php", formData.toString());
                System.out.println(json);
//                JSONObject jsonObj = new JSONObject(json);


            } catch (Exception ex) {
                Log.e("Error:", ex.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            progressDialog.dismiss();

        }
    }
}




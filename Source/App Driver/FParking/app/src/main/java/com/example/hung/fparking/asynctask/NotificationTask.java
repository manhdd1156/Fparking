package com.example.hung.fparking.asynctask;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.hung.fparking.config.Constants;
import com.example.hung.fparking.config.Session;

import org.json.JSONObject;

public class NotificationTask {
    public NotificationTask(String type, String data1, String data2, String action, IAsyncTaskHandler container) {
        if (type.equals("checkin")) {
            new UpdateNotification(data1, data2, action, container, "checkin").execute((Void) null);
        } else if (type.equals("checkout")) {
            new UpdateNotification(data1, data2, action, container, "checkout").execute((Void) null);
        } else if (type.equals("cancelorder")) {
            new DeleteNotification(data1, data2, action, container, "order").execute((Void) null);
        } else if (type.equals("cancelcheckin")) {
            new DeleteNotification(data1, data2, action, container, "checkin").execute((Void) null);
        } else if (type.equals("cancelcheckout")) {
            new DeleteNotification(data1, data2, action, container, "checkout").execute((Void) null);
        } else if (type.equals("after")) {
            new DeleteNotification(data1, data2, action, container, "order").execute((Void) null);
        }
    }
}

class UpdateNotification extends AsyncTask<Void, Void, Boolean> {

    IAsyncTaskHandler container;
    String vehicleid, parkingid, action, event;
    boolean success = false;

    public UpdateNotification(String vehicleid, String parkingid, String action, IAsyncTaskHandler container, String event) {
        this.container = container;
        this.vehicleid = vehicleid;
        this.parkingid = parkingid;
        this.action = action;
        this.event = event;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... voids) {
        HttpHandler httpHandler = new HttpHandler();
        try {
            JSONObject formData = new JSONObject();
            formData.put("driver_id", Session.currentDriver.getId());
            formData.put("vehicle_id", vehicleid);
            formData.put("parking_id", parkingid);
            formData.put("status", 0);
            formData.put("type", 1);
            formData.put("event", event);

            String json = httpHandler.requestMethod(Constants.API_URL + "notifications/create", formData.toString(), "POST");
            JSONObject jsonObj = new JSONObject(json);
            if (jsonObj != null) {
                success = true;
            }
        } catch (Exception ex) {
            Log.e("Error Update Noti:", ex.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
//        Intent intent = new Intent();
//        if(success)
//            intent.putExtra("result", "success!");
//        else
//            intent.putExtra("result", "failed");
//        this.activity.setResult(RESULT_OK, intent);
//        this.activity.finish();
    }
}

class DeleteNotification extends AsyncTask<Void, Void, Boolean> {

    IAsyncTaskHandler container;
    String vehicleid, parkingid, action, event;
    boolean success = false;

    public DeleteNotification(String vehicleid, String parkingid, String action, IAsyncTaskHandler container, String event) {
        this.container = container;
        this.vehicleid = vehicleid;
        this.parkingid = parkingid;
        this.action = action;
        this.event = event;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... voids) {
        HttpHandler httpHandler = new HttpHandler();
        try {
            JSONObject formData = new JSONObject();
            formData.put("driver_id", Session.currentDriver.getId());
            formData.put("vehicle_id", vehicleid);
            formData.put("parking_id", parkingid);
            formData.put("status", 0);
            formData.put("type", 2);
            formData.put("event", event);

            String json = httpHandler.requestMethod(Constants.API_URL + "notifications/delete ", formData.toString(), "POST");
            Log.e("Xóa Noti: ", json);
        } catch (Exception ex) {
            Log.e("Error Delete Noti:", "");
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }
}

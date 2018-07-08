package com.fparking.pushertest.asynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.fparking.pushertest.MainActivity;
import com.fparking.pushertest.config.Constants;
import com.fparking.pushertest.dto.ParkingInforDTO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ManagerParkingTask {
    public ManagerParkingTask(String method, String txtSearch, IAsyncTaskHandler container, ParkingInforDTO p) {

        if (method.equals("get")) {
            new GetParkingTask(txtSearch, container).execute((Void) null);
        }
//        else if (method.equals("create")) {
//            new CreateMenuTask(container, p).execute((Void) null);
//        } else if (method.equals("delete")) {
//            new DeleteMenuTask(p).execute((Void) null);
//        } else if (method.equals("updateGetForm")) {
//            new UpdateFormMenuTask(container, p).execute((Void) null);
//        } else if (method.equals("update")) {
//            new Update(container, p).execute((Void) null);
//        }

    }
}

class GetParkingTask extends AsyncTask<Void, Void, Boolean> {

    private final String txtSearch;
    private final IAsyncTaskHandler container;
    private JSONObject oneParking;
    private ContextCompat context;
    private Activity activity;
    private ListView lv;

    public GetParkingTask(String txtSearch, IAsyncTaskHandler container) {
        this.txtSearch = txtSearch;
        this.container = container;
        this.context = context;
        activity = (Activity) container;

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... voids) {
        HttpHandler httpHandler = new HttpHandler();
        try {
            String json = httpHandler.get(Constants.API_URL + "parking/get/?ownerPhoneNumber=" + txtSearch);
            JSONObject jsonObj = new JSONObject(json);
            JSONArray parkings = jsonObj.getJSONArray("parkingInfor");
                oneParking = parkings.getJSONObject(0);
                String parkingID = oneParking.getString("parkingID");
                String address = oneParking.getString("address");
                String space = oneParking.getString("space");
                ParkingInforDTO p = new ParkingInforDTO(Integer.parseInt(parkingID), address,"","","", Integer.parseInt(space),"",0);
        } catch (Exception ex) {
            Log.e("Error:", ex.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
//        activity.getApplicationContext().fi
//        activity.getApplication().find
//        ListAdapter adapter = new SimpleAdapter(activity.getApplicationContext(), lstMenus,
//                R.layout.menu_list, new String[]{"name", "description", "price"},
//                new int[]{R.id.mlTvName, R.id.mlTvDesc, R.id.mlTvPrice});
//        lv.setAdapter(adapter);
    }
}

package com.example.hung.myapplication.asyntask;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hung.myapplication.R;
import com.example.hung.myapplication.config.Constants;
import com.example.hung.myapplication.config.Session;

import org.json.JSONArray;
import org.json.JSONObject;


import static android.app.Activity.RESULT_OK;

/**
 * Created by klot on 3/7/2018.
 */

public class ManagerParkingTask {

    public ManagerParkingTask(String method,String text,IAsyncTaskHandler container, ListView lv) {

        if (method.equals("get")) {
            new GetParkingTask(Integer.parseInt(text)).execute((Void) null);
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

    int id;

    public GetParkingTask(int parkingID) {
        this.id = parkingID;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        HttpHandler httpHandler = new HttpHandler();
        try {
            String json = httpHandler.get(Constants.API_URL + "parkings/" + id );
            JSONObject jsonObj = new JSONObject(json);
//            JSONArray parkings = jsonObj.getJSONArray("parkingInfor");
//            oneParking = jsonObj;
            Session.currentParking.setId(jsonObj.getInt("id"));
            Session.currentParking.setAddress(jsonObj.getString("address"));
            Session.currentParking.setCurrentspace(jsonObj.getInt("currentspace"));
            Session.currentParking.setDeposits(jsonObj.getDouble("deposits"));
            Session.currentParking.setImage(jsonObj.getString("image"));
            Session.currentParking.setLatitude(jsonObj.getString("latitude"));
            Session.currentParking.setLongitude(jsonObj.getString("longitude"));
            Session.currentParking.setStatus(jsonObj.getInt("status"));
            Session.currentParking.setTimeoc(jsonObj.getString("timeoc"));
            Session.currentParking.setTotalspace(jsonObj.getInt("totalspace"));
        } catch (Exception ex) {
            Log.e("Error:", ex.getMessage());
        }
        return null;
    }
    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }
//}
//
//class CreateMenuTask extends AsyncTask<Void, Void, Boolean> {
//
//    IAsyncTaskHandler container;
//    ProductDTO p;
//    Activity activity;
//    boolean success = false;
//
//    public CreateMenuTask(IAsyncTaskHandler container, ProductDTO p) {
//        this.container = container;
//        this.activity = (Activity) container;
//        this.p = p;
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    @Override
//    protected Boolean doInBackground(Void... voids) {
//        HttpHandler httpHandler = new HttpHandler();
//        try {
//            JSONObject formData = new JSONObject();
//            formData.put("name", p.getName());
//            formData.put("description", p.getDescription());
//            formData.put("price", "" + p.getPrice());
//            formData.put("image", "" + p.getImage());
//            String json = httpHandler.post(Constants.API_URL + "product/create/", formData.toString());
//            JSONObject jsonObj = new JSONObject(json);
//            if (jsonObj.getInt("size") > 0) {
//                success = true;
//            }
//
//        } catch (Exception ex) {
//            Log.e("Error:", ex.getMessage());
//        }
//        return null;
//    }
//
//    @Override
//    protected void onPostExecute(Boolean aBoolean) {
//        super.onPostExecute(aBoolean);
//        Intent intent = new Intent();
//        if (success)
//            intent.putExtra("result", "success!");
//        else
//            intent.putExtra("result", "failed");
//        this.activity.setResult(RESULT_OK, intent);
//        this.activity.finish();
//    }
//}
//
//class DeleteMenuTask extends AsyncTask<Void, Void, Boolean> {
//
//    ProductDTO p;
//    boolean success;
//
//    public DeleteMenuTask(ProductDTO p) {
//        this.p = p;
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    @Override
//    protected Boolean doInBackground(Void... voids) {
//        HttpHandler httpHandler = new HttpHandler();
//        try {
//            JSONObject formData = new JSONObject();
//            formData.put("id", p.getId());
//            String json = httpHandler.post(Constants.API_URL + "product/delete/", formData.toString());
//            JSONObject jsonObj = new JSONObject(json);
//            if (!jsonObj.getBoolean("hasErr")) {
//                success = true;
//            }
//
//        } catch (Exception ex) {
//            Log.e("Error:", ex.getMessage());
//        }
//        return null;
//    }
//
//    @Override
//    protected void onPostExecute(Boolean aBoolean) {
//        super.onPostExecute(aBoolean);
//        if (success)
//            Log.e("Result: ", "YES!");
//        else
//            Log.e("Result: ", "Nah!");
//    }
//}
//
//class UpdateFormMenuTask extends AsyncTask<Void, Void, Boolean> {
//
//    ProductDTO p;
//    ProductDTO p_i;
//    IAsyncTaskHandler container;
//    Activity activity;
//
//    public String id;
//    public String name;
//    public String desc;
//    public String price;
//
//
//    public UpdateFormMenuTask(IAsyncTaskHandler container, ProductDTO p) {
//        this.container = container;
//        activity = (Activity) container;
//        this.p = p;
//    }
//
//    @Override
//    protected Boolean doInBackground(Void... voids) {
//        HttpHandler httpHandler = new HttpHandler();
//        try {
//
//            String json = httpHandler.get(Constants.API_URL + "product/get/?id=" + p.getId());
//            JSONObject jsonObj = new JSONObject(json);
//            JSONArray menus = jsonObj.getJSONArray("result");
//
//            JSONObject oneMenu = menus.getJSONObject(0);
//            id = oneMenu.getString("id");
//            name = oneMenu.getString("name");
//            desc = oneMenu.getString("description");
//            price = oneMenu.getString("price");
//            p_i = new ProductDTO(Integer.parseInt(id), name, Double.parseDouble(price), desc);
//
//            MenuForm.up_p = p_i;
//
//            activity.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    MenuForm.id = id.toString();
//                    MenuForm.name.setText(name.toString());
//                    MenuForm.desc.setText(desc.toString());
//                    MenuForm.price.setText(price.toString());
//                    new DownloadImageTask(MenuForm.mMenuIvImage)
//                            .execute(Constants.API_URL + "product/image/?id=" + id.toString());
//                }
//            });
//
//
//            Log.e("PPP: ", MenuForm.up_p.toString());
//
//
//        } catch (Exception ex) {
//            Log.e("Update-Error:", ex.getMessage());
//        }
//        return null;
//    }
//
//    @Override
//    protected void onPostExecute(Boolean aBoolean) {
//        super.onPostExecute(aBoolean);
//        Log.e("UpdateMenu: ", p_i.toString());
//    }
//}
//
//class Update extends AsyncTask<Void, Void, Boolean> {
//
//    IAsyncTaskHandler container;
//    ProductDTO p;
//    Activity activity;
//    boolean success = false;
//
//    public Update(IAsyncTaskHandler container, ProductDTO p) {
//        this.container = container;
//        this.activity = (Activity) container;
//        this.p = p;
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    @Override
//    protected Boolean doInBackground(Void... voids) {
//        HttpHandler httpHandler = new HttpHandler();
//        try {
//            Log.e("Update-Async", p.toString());
//            JSONObject formData = new JSONObject();
//            formData.put("id", p.getId());
//            formData.put("name", p.getName());
//            formData.put("description", p.getDescription());
//            formData.put("price", "" + p.getPrice());
//            formData.put("image", "" + p.getImage());
//            String json = httpHandler.post(Constants.API_URL + "product/update/", formData.toString());
//            JSONObject jsonObj = new JSONObject(json);
//            if (jsonObj.getInt("size") > 0) {
//                success = true;
//            }
//
//        } catch (Exception ex) {
//            Log.e("Error:", ex.getMessage());
//        }
//        return null;
//    }
//
//    @Override
//    protected void onPostExecute(Boolean aBoolean) {
//        super.onPostExecute(aBoolean);
//        Intent intent = new Intent();
//        if (success)
//            intent.putExtra("result", "success!");
//        else
//            intent.putExtra("result", "failed");
//        this.activity.setResult(RESULT_OK, intent);
//        this.activity.finish();
//    }

}
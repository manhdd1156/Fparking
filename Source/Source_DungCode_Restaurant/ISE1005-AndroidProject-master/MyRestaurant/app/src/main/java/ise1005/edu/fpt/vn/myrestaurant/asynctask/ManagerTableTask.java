package ise1005.edu.fpt.vn.myrestaurant.asynctask;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ise1005.edu.fpt.vn.myrestaurant.R;
import ise1005.edu.fpt.vn.myrestaurant.config.Constants;
import ise1005.edu.fpt.vn.myrestaurant.dto.TableDTO;
import ise1005.edu.fpt.vn.myrestaurant.manager.TableForm;

import static android.app.Activity.RESULT_OK;

/**
 * Created by sengx on 10/24/2017.
 */

public class ManagerTableTask {

    public ManagerTableTask(String method, String txtSearch, IAsyncTaskHandler container, ListView lv, TableDTO t) {

        if (method.equals("get")) {
            new GetTableTask(txtSearch, container, lv).execute((Void) null);
        } else if (method.equals("create")) {
            new CreateTableTask(container, t).execute((Void) null);
        } else if (method.equals("delete")) {
            new DeleteTableTask(t).execute((Void) null);
        } else if (method.equals("updateGetForm")) {
            new UpdateFormTable(container, t).execute((Void) null);
        } else if (method.equals("update")) {
            new UpdateTable(container, t).execute((Void) null);
        }
    }

}

class GetTableTask extends AsyncTask<Void, Void, Boolean> {

    private final String txtSearch;
    private final IAsyncTaskHandler container;
    private final List<HashMap<String,String>> lstTables;
    private JSONObject oneTable;
    private Activity activity;
    private ListView lv;

    public GetTableTask(String txtSearch, IAsyncTaskHandler container, ListView lv){
        this.txtSearch = txtSearch;
        this.container = container;
        activity = (Activity)container;
        lstTables = new ArrayList<>();
        this.lv = lv;

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... voids) {
        HttpHandler httpHandler = new HttpHandler();
        try{
            String json = httpHandler.get(Constants.API_URL + "table/get/?name=" + txtSearch);
            JSONObject jsonObj = new JSONObject(json);
            JSONArray tables = jsonObj.getJSONArray("result");

            lstTables.clear();

            for(int i=0;i<tables.length();i++){
                oneTable = tables.getJSONObject(i);
                String id = oneTable.getString("id");
                String name = oneTable.getString("name");
                String desc = oneTable.getString("description");
                String status = oneTable.getString("status");
                TableDTO t = new TableDTO(Integer.parseInt(id),name, desc, Integer.parseInt(status));
                lstTables.add(t.toHashMap());
            }

        }catch (Exception ex){
            Log.e("Error:", ex.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        ListAdapter adapter = new SimpleAdapter(activity.getApplicationContext(), lstTables,
                R.layout.menu_list, new String[]{ "name","description", ""},
                new int[]{R.id.mlTvName, R.id.mlTvDesc, R.id.mlTvPrice});
        lv.setAdapter(adapter);
    }
}

class CreateTableTask extends AsyncTask<Void, Void, Boolean> {

    IAsyncTaskHandler container;
    TableDTO t;
    Activity activity;
    boolean success = false;

    public CreateTableTask(IAsyncTaskHandler container, TableDTO t){
        this.container = container;
        this.activity = (Activity)container;
        this.t = t;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... voids) {
        HttpHandler httpHandler = new HttpHandler();
        try{
            JSONObject formData = new JSONObject();
            formData.put("name", t.getName());
            formData.put("description", t.getDescription());
            String json = httpHandler.post(Constants.API_URL + "table/create/", formData.toString());
            JSONObject jsonObj = new JSONObject(json);
            if (jsonObj.getInt("size") > 0) {
                success = true;
            }

        }catch (Exception ex){
            Log.e("Error:", ex.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        Intent intent = new Intent();
        if(success)
            intent.putExtra("result", "success!");
        else
            intent.putExtra("result", "failed");
        this.activity.setResult(RESULT_OK, intent);
        this.activity.finish();
    }
}

class DeleteTableTask extends AsyncTask<Void, Void, Boolean> {

    TableDTO t;
    boolean success;

    public DeleteTableTask(TableDTO t){
        this.t = t;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... voids) {
        HttpHandler httpHandler = new HttpHandler();
        try{
            JSONObject formData = new JSONObject();
            formData.put("id",t.getId());
            String json = httpHandler.post(Constants.API_URL + "table/delete/", formData.toString());
            JSONObject jsonObj = new JSONObject(json);
            if (!jsonObj.getBoolean("hasErr")) {
                success = true;
            }

        }catch (Exception ex){
            Log.e("Error:", ex.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if(success)
            Log.e("Result: ", "YES!");
        else
            Log.e("Result: ", "Nah!");
    }
}

class UpdateFormTable extends AsyncTask<Void, Void, Boolean> {

    TableDTO t;
    TableDTO t_i;
    IAsyncTaskHandler container;
    Activity activity;

    public String id;
    public String name;
    public String desc;
    public String status;


    public UpdateFormTable(IAsyncTaskHandler container ,TableDTO t){
        this.container = container;
        activity = (Activity)container;
        this.t = t;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        HttpHandler httpHandler = new HttpHandler();
        try{

            String json = httpHandler.get(Constants.API_URL + "table/get/?id="+t.getId());
            JSONObject jsonObj = new JSONObject(json);
            JSONArray tables = jsonObj.getJSONArray("result");

            JSONObject oneTable = tables.getJSONObject(0);
            id = oneTable.getString("id");
            name = oneTable.getString("name");
            desc = oneTable.getString("description");
            status = oneTable.getString("status");
            t_i = new TableDTO(Integer.parseInt(id),name, desc, Integer.parseInt(status));

            TableForm.up_t = t_i;

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TableForm.TableId = id.toString();
                    TableForm.TableName.setText(name.toString());
                    TableForm.TableDesc.setText(desc.toString());
                    TableForm.TableStatus = status.toString();
                }
            });


            Log.e("PPP: ", TableForm.up_t.toString());


        }catch (Exception ex){
            Log.e("Update-Error:", ex.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        Log.e("UpdateMenu: ", t_i.toString());
    }
}

class UpdateTable extends AsyncTask<Void, Void, Boolean> {

    IAsyncTaskHandler container;
    TableDTO t;
    Activity activity;
    boolean success = false;

    public UpdateTable(IAsyncTaskHandler container, TableDTO t){
        this.container = container;
        this.activity = (Activity)container;
        this.t = t;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... voids) {
        HttpHandler httpHandler = new HttpHandler();
        try{
            Log.e("Update-Async", t.toString());
            JSONObject formData = new JSONObject();
            formData.put("id", t.getId());
            formData.put("name", t.getName());
            formData.put("description", t.getDescription());
            formData.put("status", ""+ t.getStatus());
            String json = httpHandler.post(Constants.API_URL + "table/update/", formData.toString());
            JSONObject jsonObj = new JSONObject(json);
            if (jsonObj.getInt("size") > 0) {
                success = true;
            }

        }catch (Exception ex){
            Log.e("Error:", ex.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        Intent intent = new Intent();
        if(success)
            intent.putExtra("result", "success!");
        else
            intent.putExtra("result", "failed");
        this.activity.setResult(RESULT_OK, intent);
        this.activity.finish();
    }

}
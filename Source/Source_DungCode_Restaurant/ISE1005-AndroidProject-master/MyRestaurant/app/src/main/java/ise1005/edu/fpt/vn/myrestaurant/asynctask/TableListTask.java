package ise1005.edu.fpt.vn.myrestaurant.asynctask;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ise1005.edu.fpt.vn.myrestaurant.dto.TableDTO;

/**
 * Created by Administrator on 22-Oct-17.
 */

public class TableListTask extends AsyncTask<Void, Void, Boolean> {
    List<TableDTO> lstTable = new ArrayList<>();
    private IAsyncTaskHandler container;
    private String name;

    public TableListTask(String name, IAsyncTaskHandler container) {
        this.name = name;
        this.container = container;
    }
    @Override
    protected Boolean doInBackground(Void... voids) {
        lstTable = new ArrayList<>();
        HttpHandler sh = new HttpHandler();
        String jsonStr = sh.get(name);
        if(jsonStr !=null) {
            try{
                JSONObject jsonObject = new JSONObject(jsonStr);
                JSONArray results = jsonObject.getJSONArray("result");
                for(int i=0;i<results.length();i++) {
                    JSONObject result = results.getJSONObject(i);
                    TableDTO table;
                    int id = result.getInt("id");
                    String name = result.getString("name");
                    String description = result.getString("description");
                    int status = result.getInt("status");
                    table = new TableDTO(id,name,description,status);
                    lstTable.add(table);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        container.onPostExecute(lstTable);
    }
}

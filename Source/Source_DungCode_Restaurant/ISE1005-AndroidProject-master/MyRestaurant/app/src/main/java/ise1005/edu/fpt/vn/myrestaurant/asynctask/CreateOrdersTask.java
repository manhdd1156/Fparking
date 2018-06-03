package ise1005.edu.fpt.vn.myrestaurant.asynctask;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.json.JSONObject;

import ise1005.edu.fpt.vn.myrestaurant.config.Constants;
import ise1005.edu.fpt.vn.myrestaurant.dto.OrderDTO;
import ise1005.edu.fpt.vn.myrestaurant.dto.TableDTO;

/**
 * Created by authinh on 10/24/17.
 */

public class CreateOrdersTask extends AsyncTask<Void, Void, Boolean> {

    String url = Constants.API_URL+"order/create/";
    private IAsyncTaskHandler container;
    private String name;
    private OrderDTO order;
    private TableDTO table;

    public CreateOrdersTask(IAsyncTaskHandler container, String name, OrderDTO order, TableDTO table) {
        this.container = container;
        this.name = name;
        this.order = order;
        this.table = table;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... voids) {
        HttpHandler httpHandler = new HttpHandler();
        try {
            String json = httpHandler.post(url, order.toString());
            JSONObject jsonObj = new JSONObject(json);
            if (jsonObj.getInt("size") > 0) {
                /*JSONArray resultList = jsonObj.getJSONArray("result");
                JSONObject result = resultList.getJSONObject(0);
                Session.currentUser = new UserDTO();
                Session.currentUser.setId(result.getInt("id"));
                Session.currentUser.setName(result.getString("name"));
                Session.currentUser.setUsername(result.getString("username"));
                Session.currentUser.setRole_id(result.getInt("role_id"));
                Session.currentUser.setStatus(result.getInt("status"));*/
                return true;
            }
        } catch (Exception e) {
            Log.e("Exception", "order fail");
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }
}

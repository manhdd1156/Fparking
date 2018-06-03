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
import ise1005.edu.fpt.vn.myrestaurant.dto.ProductDTO;
import ise1005.edu.fpt.vn.myrestaurant.manager.MenuForm;

import static android.app.Activity.RESULT_OK;

/**
 * Created by sengx on 10/22/2017.
 */

public class ManagerMenuTask {

    public ManagerMenuTask(String method, String txtSearch, IAsyncTaskHandler container, ListView lv, ProductDTO p) {

        if (method.equals("get")) {
            new GetMenuTask(txtSearch, container, lv).execute((Void) null);
        } else if (method.equals("create")) {
            new CreateMenuTask(container, p).execute((Void) null);
        } else if (method.equals("delete")) {
            new DeleteMenuTask(p).execute((Void) null);
        } else if (method.equals("updateGetForm")) {
            new UpdateFormMenuTask(container, p).execute((Void) null);
        } else if (method.equals("update")) {
            new Update(container, p).execute((Void) null);
        }

    }

}

class GetMenuTask extends AsyncTask<Void, Void, Boolean> {

    private final String txtSearch;
    private final IAsyncTaskHandler container;
    private final List<HashMap<String, String>> lstMenus;
    private JSONObject oneMenu;
    private Activity activity;
    private ListView lv;

    public GetMenuTask(String txtSearch, IAsyncTaskHandler container, ListView lv) {
        this.txtSearch = txtSearch;
        this.container = container;
        activity = (Activity) container;
        lstMenus = new ArrayList<>();
        this.lv = lv;

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... voids) {
        HttpHandler httpHandler = new HttpHandler();
        try {
            String json = httpHandler.get(Constants.API_URL + "product/get/?name=" + txtSearch);
            JSONObject jsonObj = new JSONObject(json);
            JSONArray menus = jsonObj.getJSONArray("result");
            lstMenus.clear();
            for (int i = 0; i < menus.length(); i++) {
                oneMenu = menus.getJSONObject(i);
                String id = oneMenu.getString("id");
                String name = oneMenu.getString("name");
                String desc = oneMenu.getString("description");
                String price = oneMenu.getString("price");
                ProductDTO p = new ProductDTO(Integer.parseInt(id), name, Double.parseDouble(price), desc);
                lstMenus.add(p.toHashMap());
            }

        } catch (Exception ex) {
            Log.e("Error:", ex.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        ListAdapter adapter = new SimpleAdapter(activity.getApplicationContext(), lstMenus,
                R.layout.menu_list, new String[]{"name", "description", "price"},
                new int[]{R.id.mlTvName, R.id.mlTvDesc, R.id.mlTvPrice});
        lv.setAdapter(adapter);
    }
}

class CreateMenuTask extends AsyncTask<Void, Void, Boolean> {

    IAsyncTaskHandler container;
    ProductDTO p;
    Activity activity;
    boolean success = false;

    public CreateMenuTask(IAsyncTaskHandler container, ProductDTO p) {
        this.container = container;
        this.activity = (Activity) container;
        this.p = p;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... voids) {
        HttpHandler httpHandler = new HttpHandler();
        try {
            JSONObject formData = new JSONObject();
            formData.put("name", p.getName());
            formData.put("description", p.getDescription());
            formData.put("price", "" + p.getPrice());
            formData.put("image", "" + p.getImage());
            String json = httpHandler.post(Constants.API_URL + "product/create/", formData.toString());
            JSONObject jsonObj = new JSONObject(json);
            if (jsonObj.getInt("size") > 0) {
                success = true;
            }

        } catch (Exception ex) {
            Log.e("Error:", ex.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        Intent intent = new Intent();
        if (success)
            intent.putExtra("result", "success!");
        else
            intent.putExtra("result", "failed");
        this.activity.setResult(RESULT_OK, intent);
        this.activity.finish();
    }
}

class DeleteMenuTask extends AsyncTask<Void, Void, Boolean> {

    ProductDTO p;
    boolean success;

    public DeleteMenuTask(ProductDTO p) {
        this.p = p;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... voids) {
        HttpHandler httpHandler = new HttpHandler();
        try {
            JSONObject formData = new JSONObject();
            formData.put("id", p.getId());
            String json = httpHandler.post(Constants.API_URL + "product/delete/", formData.toString());
            JSONObject jsonObj = new JSONObject(json);
            if (!jsonObj.getBoolean("hasErr")) {
                success = true;
            }

        } catch (Exception ex) {
            Log.e("Error:", ex.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if (success)
            Log.e("Result: ", "YES!");
        else
            Log.e("Result: ", "Nah!");
    }
}

class UpdateFormMenuTask extends AsyncTask<Void, Void, Boolean> {

    ProductDTO p;
    ProductDTO p_i;
    IAsyncTaskHandler container;
    Activity activity;

    public String id;
    public String name;
    public String desc;
    public String price;


    public UpdateFormMenuTask(IAsyncTaskHandler container, ProductDTO p) {
        this.container = container;
        activity = (Activity) container;
        this.p = p;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        HttpHandler httpHandler = new HttpHandler();
        try {

            String json = httpHandler.get(Constants.API_URL + "product/get/?id=" + p.getId());
            JSONObject jsonObj = new JSONObject(json);
            JSONArray menus = jsonObj.getJSONArray("result");

            JSONObject oneMenu = menus.getJSONObject(0);
            id = oneMenu.getString("id");
            name = oneMenu.getString("name");
            desc = oneMenu.getString("description");
            price = oneMenu.getString("price");
            p_i = new ProductDTO(Integer.parseInt(id), name, Double.parseDouble(price), desc);

            MenuForm.up_p = p_i;

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    MenuForm.id = id.toString();
                    MenuForm.name.setText(name.toString());
                    MenuForm.desc.setText(desc.toString());
                    MenuForm.price.setText(price.toString());
                    new DownloadImageTask(MenuForm.mMenuIvImage)
                            .execute(Constants.API_URL + "product/image/?id=" + id.toString());
                }
            });


            Log.e("PPP: ", MenuForm.up_p.toString());


        } catch (Exception ex) {
            Log.e("Update-Error:", ex.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        Log.e("UpdateMenu: ", p_i.toString());
    }
}

class Update extends AsyncTask<Void, Void, Boolean> {

    IAsyncTaskHandler container;
    ProductDTO p;
    Activity activity;
    boolean success = false;

    public Update(IAsyncTaskHandler container, ProductDTO p) {
        this.container = container;
        this.activity = (Activity) container;
        this.p = p;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... voids) {
        HttpHandler httpHandler = new HttpHandler();
        try {
            Log.e("Update-Async", p.toString());
            JSONObject formData = new JSONObject();
            formData.put("id", p.getId());
            formData.put("name", p.getName());
            formData.put("description", p.getDescription());
            formData.put("price", "" + p.getPrice());
            formData.put("image", "" + p.getImage());
            String json = httpHandler.post(Constants.API_URL + "product/update/", formData.toString());
            JSONObject jsonObj = new JSONObject(json);
            if (jsonObj.getInt("size") > 0) {
                success = true;
            }

        } catch (Exception ex) {
            Log.e("Error:", ex.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        Intent intent = new Intent();
        if (success)
            intent.putExtra("result", "success!");
        else
            intent.putExtra("result", "failed");
        this.activity.setResult(RESULT_OK, intent);
        this.activity.finish();
    }

}
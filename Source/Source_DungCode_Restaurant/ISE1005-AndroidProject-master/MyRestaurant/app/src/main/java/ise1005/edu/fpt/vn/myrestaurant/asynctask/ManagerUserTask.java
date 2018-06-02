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
import ise1005.edu.fpt.vn.myrestaurant.dto.UserDTO;
import ise1005.edu.fpt.vn.myrestaurant.manager.UserForm;

import static android.app.Activity.RESULT_OK;

/**
 * Created by sengx on 10/24/2017.
 */

public class ManagerUserTask {

    public ManagerUserTask(String method, String txtSearch, IAsyncTaskHandler container, ListView lv, UserDTO u){

        if(method.equals("get")){
            new GetUserTask(txtSearch,container,lv).execute((Void) null);
        }
        else if(method.equals("create")){
            new CreateUserTask(container, u).execute((Void) null);
        }
        else if(method.equals("delete")){
            new DeleteUserTask(u).execute((Void) null);
        }
        else if(method.equals("updateGetForm")){
            new UpdateFormUser(container, u).execute((Void) null);
        }
        else  if(method.equals("update")){
            new UpdateUser(container, u).execute((Void) null);
        }

    }

}

class GetUserTask extends AsyncTask<Void, Void, Boolean> {

    private final String txtSearch;
    private final IAsyncTaskHandler container;
    private final List<HashMap<String,String>> lstUsers;
    private JSONObject oneUser;
    private Activity activity;
    private ListView lv;

    public GetUserTask(String txtSearch, IAsyncTaskHandler container, ListView lv){
        this.txtSearch = txtSearch;
        this.container = container;
        activity = (Activity)container;
        lstUsers = new ArrayList<>();
        this.lv = lv;

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... voids) {
        HttpHandler httpHandler = new HttpHandler();
        try{
            String json = httpHandler.get(Constants.API_URL + "user/get/?name=" + txtSearch);
            JSONObject jsonObj = new JSONObject(json);
            JSONArray menus = jsonObj.getJSONArray("result");

            lstUsers.clear();

            for(int i=0;i<menus.length();i++){
                oneUser = menus.getJSONObject(i);
                String id = oneUser.getString("id");
                String name = oneUser.getString("name");
                String username = oneUser.getString("username");
                String role_id = oneUser.getString("role_id");
                UserDTO u = new UserDTO();
                u.setId(Integer.parseInt(id));
                u.setName(name);
                u.setUsername(username);
                u.setRole_id(Integer.parseInt(role_id));
                lstUsers.add(u.toHashMap());
            }

        }catch (Exception ex){
            Log.e("Error:", ex.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        ListAdapter adapter = new SimpleAdapter(activity.getApplicationContext(), lstUsers,
                R.layout.menu_list, new String[]{ "name","username", "role_name"},
                new int[]{R.id.mlTvName, R.id.mlTvDesc, R.id.mlTvPrice});
        lv.setAdapter(adapter);
    }
}

class CreateUserTask extends AsyncTask<Void, Void, Boolean> {

    IAsyncTaskHandler container;
    UserDTO u;
    Activity activity;
    boolean success = false;

    public CreateUserTask(IAsyncTaskHandler container, UserDTO u){
        this.container = container;
        this.activity = (Activity)container;
        this.u = u;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... voids) {
        HttpHandler httpHandler = new HttpHandler();
        try{
            JSONObject formData = new JSONObject();
            formData.put("name", u.getName());
            formData.put("username", u.getUsername());
            formData.put("password", u.getPassword());
            formData.put("role_id", u.getRole_id());
            formData.put("status", u.getStatus());

            String json = httpHandler.post(Constants.API_URL + "user/create/", formData.toString());
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

class DeleteUserTask extends AsyncTask<Void, Void, Boolean> {

    UserDTO u;
    boolean success;

    public DeleteUserTask(UserDTO u){
        this.u = u;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... voids) {
        HttpHandler httpHandler = new HttpHandler();
        try{
            JSONObject formData = new JSONObject();
            formData.put("id",u.getId());
            String json = httpHandler.post(Constants.API_URL + "user/delete/", formData.toString());
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

class UpdateFormUser extends AsyncTask<Void, Void, Boolean> {

    UserDTO u;
    UserDTO u_i;
    IAsyncTaskHandler container;
    Activity activity;

    public String uid;
    public String fullname;
    public String username;
    public String password;
    public String role_id;
    public String ustatus;
    public String userRoleName;


    public UpdateFormUser(IAsyncTaskHandler container ,UserDTO u){
        this.container = container;
        activity = (Activity)container;
        this.u = u;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        HttpHandler httpHandler = new HttpHandler();
        try{

            String json = httpHandler.get(Constants.API_URL + "user/get/?id="+u.getId());
            JSONObject jsonObj = new JSONObject(json);
            JSONArray tables = jsonObj.getJSONArray("result");

            JSONObject oneUser = tables.getJSONObject(0);
            uid = oneUser.getString("id");
            fullname = oneUser.getString("name");
            username = oneUser.getString("username");
            role_id = oneUser.getString("role_id");
            ustatus = oneUser.getString("status");
            userRoleName = oneUser.getString("role_name");
            u_i = new UserDTO(Integer.parseInt(uid),fullname, username, "", Integer.parseInt(role_id), Integer.parseInt(ustatus));

            UserForm.up_u = u_i;

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    UserForm.UserId = uid.toString();
                    UserForm.UserFullName.setText(fullname.toString());
                    UserForm.UserName.setText(username.toString());
                    UserForm.UserRoleId = role_id.toString();
                    UserForm.UserStt = ustatus.toString();
                    UserForm.UserRoleNameString = userRoleName.toString();

                    int currentPosition = UserForm.dataAdapter.getPosition(UserForm.UserRoleNameString);
                    Log.e("position: ", ""+currentPosition);
                    Log.e("position_name: ", ""+UserForm.UserRoleNameString);
                    UserForm.UserRoleName.setSelection(currentPosition);

                    if(ustatus.equals("0")) UserForm.UserStatus.setChecked(true);
                    else UserForm.UserStatus.setChecked(false);

                }
            });


            Log.e("PPP: ", UserForm.up_u.toString());


        }catch (Exception ex){
            Log.e("Update-Error:", ex.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        Log.e("UpdateMenu: ", u_i.toString());
    }
}

class UpdateUser extends AsyncTask<Void, Void, Boolean> {

    IAsyncTaskHandler container;
    UserDTO u;
    Activity activity;
    boolean success = false;

    public UpdateUser(IAsyncTaskHandler container, UserDTO u){
        this.container = container;
        this.activity = (Activity)container;
        this.u = u;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... voids) {
        HttpHandler httpHandler = new HttpHandler();
        try{
            Log.e("Update-Async", u.toString());
            JSONObject formData = new JSONObject();
            formData.put("id", u.getId());
            formData.put("name", u.getName());
            formData.put("username", u.getUsername());
            formData.put("password", ""+ u.getPassword());
            formData.put("role_id", ""+u.getRole_id());
            formData.put("status",""+u.getStatus());
            String json = httpHandler.post(Constants.API_URL + "user/update/", formData.toString());
            JSONObject jsonObj = new JSONObject(json);
            Log.e("Update-Async: ", jsonObj.toString());
            success = true;


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
package ise1005.edu.fpt.vn.myrestaurant.manager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

import ise1005.edu.fpt.vn.myrestaurant.R;
import ise1005.edu.fpt.vn.myrestaurant.asynctask.IAsyncTaskHandler;
import ise1005.edu.fpt.vn.myrestaurant.asynctask.ManagerUserTask;
import ise1005.edu.fpt.vn.myrestaurant.config.Session;
import ise1005.edu.fpt.vn.myrestaurant.dto.UserDTO;

public class UserForm extends AppCompatActivity implements IAsyncTaskHandler {

    public static String UserId;
    public static EditText UserFullName;
    public static EditText UserName;
    public static EditText UserPass;
    public static Spinner UserRoleName;
    public static String UserRoleId;
    public static String UserRoleNameString;
    public static Switch UserStatus;
    public static String UserStt;

    Button btnReset;
    Button btnCreate;

    public static String up_uid = null;
    public static UserDTO up_u = null;

    public static ArrayAdapter<String> dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_form);

        if(Session.currentUser == null || Session.currentUser.getRole_id() != 1 || Session.currentUser.getStatus() != 0){
            new CheckSession(this);
        }

        UserFullName = (EditText)findViewById(R.id.mUserEdtName);
        UserName = (EditText)findViewById(R.id.mUserEdtUserName);
        UserPass = (EditText)findViewById(R.id.mUserEdtPassword);
        UserRoleName = (Spinner)findViewById(R.id.mUserSpRole);
        UserStatus = (Switch) findViewById(R.id.mUserStatus);

        btnCreate = (Button)findViewById(R.id.mUserBtnCreate);
        btnReset = (Button)findViewById(R.id.mUserBtnReset);

        List<String> roles = new ArrayList<>();
        roles.add("Staff");
        roles.add("Manager");
        roles.add("Cooker");

        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, roles);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        UserRoleName.setAdapter(dataAdapter);

        UserRoleName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (UserRoleName.getSelectedItemPosition()){
                    case 0:
                        UserRoleId = "3";
                        break;
                    case 1:
                        UserRoleId = "1";
                        break;
                    case 2:
                        UserRoleId = "2";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if(UserStatus.isChecked()) UserStt = "0";
        else UserStt = "1";

        if(getIntent().getStringExtra("id") != null) {
            up_uid = getIntent().getStringExtra("id");
            btnCreate.setText("Update");
            updateTable();
        }

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(getIntent().getStringExtra("id") != null){
                    Log.e("Clicked: ", "Update");
                    update();
                }else{
                    Log.e("Clicked: ", "Create");
                    createTable();
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserName.setText("");
                UserFullName.setText("");
                UserPass.setText("");
            }
        });


    }

    public void createTable(){

        boolean cancel = false;
        View focusView = null;

        UserDTO u = new UserDTO();
        u.setName(UserFullName.getText().toString());
        u.setUsername(UserName.getText().toString());
        u.setPassword(UserPass.getText().toString());
        u.setRole_id(Integer.parseInt(UserRoleId));
        u.setStatus(Integer.parseInt(UserStt));

        UserFullName.setError(null);
        UserName.setError(null);
        UserPass.setError(null);

        if(TextUtils.isEmpty(UserFullName.getText())){
            UserFullName.setError(getString(R.string.error_field_required));
            focusView = UserFullName;
            cancel = true;
        }

        if(TextUtils.isEmpty(UserName.getText())){
            UserName.setError(getString(R.string.error_field_required));
            focusView = UserName;
            cancel = true;
        }

        if(TextUtils.isEmpty(UserPass.getText())){
            UserPass.setError(getString(R.string.error_field_required));
            focusView = UserPass;
            cancel = true;
        }


        if(cancel){
            focusView.requestFocus();
        }else{
            new ManagerUserTask("create",null, UserForm.this, null, u);
        }

    }

    public void update(){

        boolean cancel = false;
        View focusView = null;

        UserDTO u = new UserDTO();
        u.setId(Integer.parseInt(UserId));
        u.setName(UserFullName.getText().toString());
        u.setUsername(UserName.getText().toString());
        u.setPassword(UserPass.getText().toString());
        u.setRole_id(Integer.parseInt(UserRoleId));
        if(UserStatus.isChecked())
            u.setStatus(0);
        else
            u.setStatus(1);

        UserFullName.setError(null);
        UserName.setError(null);
        UserPass.setError(null);

        if(TextUtils.isEmpty(UserFullName.getText())){
            UserFullName.setError(getString(R.string.error_field_required));
            focusView = UserFullName;
            cancel = true;
        }

        if(TextUtils.isEmpty(UserName.getText())){
            UserName.setError(getString(R.string.error_field_required));
            focusView = UserName;
            cancel = true;
        }

        if(TextUtils.isEmpty(UserPass.getText())){
            UserPass.setError(getString(R.string.error_field_required));
            focusView = UserPass;
            cancel = true;
        }

        if(cancel){
            focusView.requestFocus();
        }else{
            Log.e("Update: ", u.toString());
            new ManagerUserTask("update",null, UserForm.this, null, u);
        }



    }

    public void updateTable(){
        try {

            UserDTO u = new UserDTO();
            u.setId(Integer.parseInt(up_uid));

            new ManagerUserTask("updateGetForm", null, UserForm.this, null, u);


        }catch (Exception ex){
            Log.e("Error: ", ex.getMessage());
        }
    }


    @Override
    public void onPostExecute(Object o) {

    }
}

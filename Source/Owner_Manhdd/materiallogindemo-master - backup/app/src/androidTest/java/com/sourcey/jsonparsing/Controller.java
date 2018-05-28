package com.sourcey.jsonparsing;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class Controller extends AppCompatActivity {
    HashMap<String, String> hashMap = new HashMap<>();
    String UserNameHolder, PasswordHolder, FullNameHolder;
    private ProgressDialog pDialog;
    HttpHandler httphander;
    Button AddUserClick, UpdateUserClick, DeleteUserClick, ShowUserClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);
        httphander = new HttpHandler();

        AddUserClick = (Button) findViewById(R.id.buttonAdd);
        UpdateUserClick = (Button) findViewById(R.id.buttonUpdate);
        DeleteUserClick = (Button) findViewById(R.id.buttonDelete);
        ShowUserClick = (Button) findViewById(R.id.buttonShow);

        // add
        AddUserClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText username = (EditText) findViewById(R.id.editTextUserName);
                EditText password = (EditText) findViewById(R.id.editTextPassWord);
                EditText fullname = (EditText) findViewById(R.id.editTextFullName);
                UserNameHolder = username.getText().toString();
                PasswordHolder = password.getText().toString();
                FullNameHolder = fullname.getText().toString();

                AddUser(UserNameHolder, PasswordHolder, FullNameHolder);
                Toast.makeText(Controller.this, "OK done!", Toast.LENGTH_LONG).show();
            }
        });
        // update
        UpdateUserClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText username = (EditText) findViewById(R.id.editTextUserName);
                EditText password = (EditText) findViewById(R.id.editTextPassWord);
                EditText fullname = (EditText) findViewById(R.id.editTextFullName);
                UserNameHolder = username.getText().toString();
                PasswordHolder = password.getText().toString();
                FullNameHolder = fullname.getText().toString();

                UserRecordUpdated(UserNameHolder, PasswordHolder, FullNameHolder);
                Toast.makeText(Controller.this, "OK done!", Toast.LENGTH_LONG).show();
            }
        });
        DeleteUserClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText username = (EditText) findViewById(R.id.editTextUserName);
                UserNameHolder = username.getText().toString();
                UserDelete(UserNameHolder);
            }
        });
        ShowUserClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToNextActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(goToNextActivity);
            }
        });
    }

    // -----------------------------------------ADD-------------------------------------------------
    public void AddUser(String S_UserName, String S_Password, String S_FullName) {

        class AddUserClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // Showing progress dialog
                pDialog = new ProgressDialog(Controller.this);
                pDialog.setMessage("Please wait...");
                pDialog.setButton("cxzcxzcxz",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton){}
//                                        ButtonActivity.this.finish();
                        });
                pDialog.setCancelable(false);
                pDialog.show();
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                pDialog.dismiss();

//                Toast.makeText(Controller.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("UserName", params[0]);

                hashMap.put("Password", params[1]);

                hashMap.put("FullName", params[2]);

                String finalResult = httphander.postRequest(hashMap, "http://192.168.43.177/scripts/AddUser.php");

                return finalResult;
            }
        }

        AddUserClass AddUserClass = new AddUserClass();

        AddUserClass.execute(S_UserName, S_Password, S_FullName);
    }


    // update
    public void UserRecordUpdate(final String S_UserName, final String S_Password, final String S_FullName) {

        UserDelete(S_UserName);
        AddUser(S_UserName, S_Password, S_FullName);
    }
    // -----------------------------------------DELETE-------------------------------------------------
    public void UserDelete(final String UserName) {

        class UserDeleteClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);
            }

            @Override
            protected String doInBackground(String... params) {

                // Sending STUDENT id.
                hashMap.put("UserName", params[0]);

                String finalResult = httphander.postRequest(hashMap, "http://192.168.43.177/scripts/DeleteUser.php");

                return finalResult;
            }
        }

        UserDeleteClass userDeleteClass = new UserDeleteClass();

        userDeleteClass.execute(UserName);
    }

// -----------------------------------------UPDATE-------------------------------------------------
    public void UserRecordUpdated(final String S_UserName, final String S_Pass, final String S_FullName) {

        class UserRecordUpdateClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
            }

            @Override
            protected String doInBackground(String... params) {
                hashMap.clear();
                hashMap.put("UserName", params[0]);

                hashMap.put("Password", params[1]);

                hashMap.put("FullName", params[2]);

               String finalResult = httphander.postRequest(hashMap, "http://192.168.43.177/scripts/UpdateUser.php");

                return finalResult;
            }
        }

        UserRecordUpdateClass userRecordUpdateClass = new UserRecordUpdateClass();

        userRecordUpdateClass.execute(S_UserName,S_Pass, S_FullName);
    }
}

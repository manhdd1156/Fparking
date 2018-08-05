package ise1005.edu.fpt.vn.myrestaurant.manager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ise1005.edu.fpt.vn.myrestaurant.R;
import ise1005.edu.fpt.vn.myrestaurant.asynctask.IAsyncTaskHandler;
import ise1005.edu.fpt.vn.myrestaurant.asynctask.ManagerTableTask;
import ise1005.edu.fpt.vn.myrestaurant.config.Session;
import ise1005.edu.fpt.vn.myrestaurant.dto.TableDTO;

public class TableForm extends AppCompatActivity implements IAsyncTaskHandler {

    public static String TableId;
    public static EditText TableName;
    public static EditText TableDesc;
    public static String TableStatus;

    Button btnReset;
    Button btnCreate;

    public static String up_tid = null;
    public static TableDTO up_t = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_form);

        if(Session.currentUser == null || Session.currentUser.getRole_id() != 1 || Session.currentUser.getStatus() != 0){
            new CheckSession(this);
        }

        TableName = (EditText)findViewById(R.id.mTableEdtName);
        TableDesc = (EditText)findViewById(R.id.mTableEdtDesc);

        btnCreate = (Button)findViewById(R.id.mTableBtnCreate);
        btnReset = (Button)findViewById(R.id.mTableBtnReset);

        if(getIntent().getStringExtra("id") != null) {
            up_tid = getIntent().getStringExtra("id");
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
                TableName.setText("");
                TableDesc.setText("");
            }
        });

    }

    public void createTable(){

        boolean cancel = false;
        View focusView = null;

        TableDTO t = new TableDTO();
        t.setName(TableName.getText().toString());
        t.setDescription((TableDesc.getText().toString()));

        TableName.setError(null);
        TableDesc.setError(null);

        if(TextUtils.isEmpty(TableName.getText())){
            TableName.setError(getString(R.string.error_field_required));
            focusView = TableName;
            cancel = true;
        }

        if(TextUtils.isEmpty(TableDesc.getText())){
            TableDesc.setError(getString(R.string.error_field_required));
            focusView = TableDesc;
            cancel = true;
        }


        if(cancel){
            focusView.requestFocus();
        }else{
            new ManagerTableTask("create",null, TableForm.this, null, t);
        }

    }

    public void update(){

        boolean cancel = false;
        View focusView = null;

        TableDTO t = new TableDTO();
        t.setId(Integer.parseInt(TableId));
        t.setName(TableName.getText().toString());
        t.setDescription((TableDesc.getText().toString()));

        TableName.setError(null);
        TableDesc.setError(null);

        if(TextUtils.isEmpty(TableName.getText())){
            TableName.setError(getString(R.string.error_field_required));
            focusView = TableName;
            cancel = true;
        }

        if(TextUtils.isEmpty(TableDesc.getText())){
            TableDesc.setError(getString(R.string.error_field_required));
            focusView = TableDesc;
            cancel = true;
        }

        if(cancel){
            focusView.requestFocus();
        }else{
            Log.e("Update: ", t.toString());
            new ManagerTableTask("update",null, TableForm.this, null, t);
        }



    }

    public void updateTable(){
        try {

            TableDTO t = new TableDTO();
            t.setId(Integer.parseInt(up_tid));

            new ManagerTableTask("updateGetForm", null, TableForm.this, null, t);


        }catch (Exception ex){
            Log.e("Error: ", ex.getMessage());
        }
    }



    @Override
    public void onPostExecute(Object o) {
        up_tid = null;
        up_t = null;
    }
}

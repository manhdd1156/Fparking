package ise1005.edu.fpt.vn.myrestaurant.staff;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ise1005.edu.fpt.vn.myrestaurant.R;
import ise1005.edu.fpt.vn.myrestaurant.asynctask.IAsyncTaskHandler;
import ise1005.edu.fpt.vn.myrestaurant.asynctask.ManagerOrderDetailTask;

public class PayForm extends AppCompatActivity implements View.OnClickListener,IAsyncTaskHandler {

    EditText listPay;
    Button btnClose;
    Button btnTable;
    String tableID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_form);
        listPay = (EditText) findViewById(R.id.listPay);
        btnClose = (Button) findViewById(R.id.btnClose);
        btnClose.setOnClickListener(this);

        btnTable = (Button) findViewById(R.id.btnTable);
        btnTable.setOnClickListener(this);
        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        if (b != null) {
            listPay.setText(b.getString("listPay"));
            tableID = b.getString("table_id");
        } else {
            listPay.setText("don't have any product to pay!!!!");
        }
    }

    @Override
    public void onClick(View view) {
        int getwide = view.getId();
        Intent intent = new Intent(this, TableActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        switch (getwide){
            case R.id.btnClose:
                ManagerOrderDetailTask orderDetailTask = new ManagerOrderDetailTask("close", tableID, this, null, null, null,null);
                Snackbar.make(view,"Closed", Snackbar.LENGTH_LONG);
                Toast.makeText(this, "The order have been closed", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
            case R.id.btnTable:
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onPostExecute(Object o) {

    }
}

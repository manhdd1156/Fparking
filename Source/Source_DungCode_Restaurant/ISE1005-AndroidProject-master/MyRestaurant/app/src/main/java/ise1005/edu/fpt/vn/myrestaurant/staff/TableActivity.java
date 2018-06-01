package ise1005.edu.fpt.vn.myrestaurant.staff;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ise1005.edu.fpt.vn.myrestaurant.R;
import ise1005.edu.fpt.vn.myrestaurant.adapter.TableStaffAdapter;
import ise1005.edu.fpt.vn.myrestaurant.asynctask.IAsyncTaskHandler;
import ise1005.edu.fpt.vn.myrestaurant.asynctask.TableListTask;
import ise1005.edu.fpt.vn.myrestaurant.config.Constants;
import ise1005.edu.fpt.vn.myrestaurant.dto.TableDTO;

public class TableActivity extends AppCompatActivity implements IAsyncTaskHandler, AdapterView.OnItemSelectedListener,TextWatcher {
    private ListView listTable;
    String url = "";
    int status = -1;
    String input = "";
    EditText inputdata;
   // private List<TableDTO> TableList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_table);
        listTable = (ListView) findViewById(R.id.lstTable);
        inputdata = (EditText) findViewById(R.id.search);
        inputdata.addTextChangedListener(this);
        input = inputdata.getText().toString().trim().toLowerCase();
        listTable.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                TableDTO o = (TableDTO) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),"You open "+o.getName()+"",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TableActivity.this, ListOrderItem.class);
                intent.putExtra("table_id", o.getId()+"");
                startActivity(intent);
            }
        });
        try {
            Spinner spinner = (Spinner) findViewById(R.id.status);
            List<String> lstStatus = new ArrayList<String>();
            lstStatus.add("All");
            lstStatus.add("Free");
            lstStatus.add("Hold");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lstStatus);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);
            spinner.setOnItemSelectedListener(this);
        }catch (Exception e) {
            e.printStackTrace();
        }



//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        TableListTask tableListTask = new TableListTask("", this);
        tableListTask.execute();

    }

    @Override
    public void onPostExecute(Object o) {
       // Log.d("--------", lstTable.toString());
        List<TableDTO> lstTable = (List<TableDTO>) o;
        Log.d("ISE1005", lstTable.toString());
//        TableDTO[] tables = new TableDTO[lstTable.size()];
//        Log.d("----", tables.toString());
//        for(int i=0;i<lstTable.size();i++) {
//            tables[i] = lstTable.get(i);
//            Log.d("3333", tables[i].toString());
//        }
//        ArrayAdapter<TableDTO> arrayAdapter = new ArrayAdapter<TableDTO>(this, R.layout.table_list_row, tables);
        TableStaffAdapter arrayAdapter = new TableStaffAdapter(lstTable, this);
        listTable.setAdapter(arrayAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();
        input = inputdata.getText().toString().trim().toLowerCase();
        if(input==null) {
            if(item =="All") {
                status = -1;
                url = Constants.API_URL+"table/get/?status="+status+"&name=";
            }
            if (item == "Free") {
                status = 0;
                url = Constants.API_URL+"table/get/?status="+status+"&name=";
            }
            if (item == "Hold") {
                status = 1;
                url = Constants.API_URL+"table/get/?status="+status+"&name=";
            }
        } else {
            if(item =="All") {
                status = -1;
                url = Constants.API_URL+"table/get/?status="+status+"&name="+input;
            }
            if (item == "Free") {
                status = 0;
                url = Constants.API_URL+"table/get/?status="+status+"&name="+input;
            }
            if (item == "Hold") {
                status = 1;
                url = Constants.API_URL+"table/get/?status="+status+"&name="+input;
            }
        }

        TableListTask tableListTask = new TableListTask(url, this);
        tableListTask.execute();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        input = inputdata.getText().toString().trim().toLowerCase();
        Log.d("9999",input);
        if(input == null) {
            url = Constants.API_URL+"table/get/?status="+status;
        } else {
            url = Constants.API_URL + "table/get/?status=" + status + "&name=" + input;
        }
        TableListTask tableListTask = new TableListTask(url, this);
        tableListTask.execute();
    }

    @Override
    protected void onResume() {
        inputdata.setText("");
        super.onResume();
    }
}

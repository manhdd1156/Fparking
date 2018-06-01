package ise1005.edu.fpt.vn.myrestaurant.manager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import ise1005.edu.fpt.vn.myrestaurant.R;
import ise1005.edu.fpt.vn.myrestaurant.asynctask.IAsyncTaskHandler;
import ise1005.edu.fpt.vn.myrestaurant.asynctask.ManagerTableTask;
import ise1005.edu.fpt.vn.myrestaurant.config.Session;
import ise1005.edu.fpt.vn.myrestaurant.dto.TableDTO;

public class Table extends AppCompatActivity implements IAsyncTaskHandler {

    ListView lv;
    EditText edtSearch;
    Button btnAdd;
    ManagerTableTask getTableTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        if(Session.currentUser == null || Session.currentUser.getRole_id() != 1 || Session.currentUser.getStatus() != 0){
            new CheckSession(this);
        }

        lv = (ListView)findViewById(R.id.mTableLv);
        edtSearch = (EditText)findViewById(R.id.mTableEdtSearch);
        btnAdd = (Button)findViewById(R.id.mTableBtnAdd);

        registerForContextMenu(lv);

        edtSearch.requestFocus();

        getTableTask = new ManagerTableTask("get",edtSearch.getText().toString(), Table.this, lv, null);


        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getTableTask = new ManagerTableTask("get",edtSearch.getText().toString(), Table.this, lv, null);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Table.this.startActivityForResult(new Intent(Table.this.getApplicationContext(),TableForm.class),1);
            }
        });

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        String json = lv.getItemAtPosition(info.position).toString();
        String[] txt_split = json.split(", ");
        String[] txt2_split = txt_split[0].split("=");
        String id = txt2_split[1];

        Intent intent = new Intent(Table.this.getApplicationContext(),TableForm.class);
        intent.putExtra("id",id);

        switch (item.getItemId()) {
            case R.id.id_update:
                Table.this.startActivityForResult(intent,1);
                break;
            case R.id.id_delete:
                TableDTO t = new TableDTO();
                t.setId(Integer.parseInt(id));
                try{
                    new ManagerTableTask("delete",null,null,null,t);
                    Log.e("Deleted: ", ""+t.getId());
                    new ManagerTableTask("get","", Table.this, lv, null);
                }catch (Exception ex){
                    Log.e("Error:", ex.getMessage());
                }

                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.context_menu,menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String result = data.getStringExtra("result");
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                new ManagerTableTask("get","", Table.this, lv, null);
            }
        }
    }


    @Override
    public void onPostExecute(Object o) {

    }
}

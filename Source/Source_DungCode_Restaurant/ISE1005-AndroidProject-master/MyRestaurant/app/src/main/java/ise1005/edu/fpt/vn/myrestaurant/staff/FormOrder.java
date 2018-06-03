package ise1005.edu.fpt.vn.myrestaurant.staff;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import ise1005.edu.fpt.vn.myrestaurant.R;
import ise1005.edu.fpt.vn.myrestaurant.adapter.ListProductAdapter;
import ise1005.edu.fpt.vn.myrestaurant.asynctask.IAsyncTaskHandler;
import ise1005.edu.fpt.vn.myrestaurant.asynctask.ProductListTask;
import ise1005.edu.fpt.vn.myrestaurant.dto.ProductDTO;

public class FormOrder extends AppCompatActivity implements IAsyncTaskHandler,View.OnClickListener,TextWatcher {

    ArrayList<ProductDTO> dataModels;
    ListView listView;
    private static ListProductAdapter adapter;
    FloatingActionButton mProductBtnCancel;
    EditText inputdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_form);
//        mProductBtnCancel = (FloatingActionButton) findViewById(R.id.mProductBtnCancel);
//        mProductBtnCancel.setOnClickListener(this);
        inputdata = (EditText) findViewById(R.id.mProductEdtSearch);
        inputdata.addTextChangedListener(this);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        listView=(ListView)findViewById(R.id.mTableLv);

        ProductListTask productListTask = new ProductListTask(this);
        productListTask.execute();
    }

    @Override
    public void onPostExecute(Object o) {
        dataModels= (ArrayList<ProductDTO>) o;
        adapter= new ListProductAdapter(dataModels,getApplicationContext());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ProductDTO dataModel= dataModels.get(position);
                Snackbar.make(view, dataModel.getName()+"\n"+dataModel.getDescription()+" Price: "+dataModel.getPrice(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                Intent returnIntent = new Intent();
                returnIntent.putExtra("productDTO",dataModel);

                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
//        int getWiget = view.getId();
//        if(getWiget == R.id.mProductBtnCancel){
//            Intent returnIntent = new Intent();
//            setResult(Activity.RESULT_CANCELED,returnIntent);
//            finish();
//        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String input = inputdata.getText().toString().trim().toLowerCase();
        ProductListTask productListTask = new ProductListTask(this,input);
        productListTask.execute();
    }
}

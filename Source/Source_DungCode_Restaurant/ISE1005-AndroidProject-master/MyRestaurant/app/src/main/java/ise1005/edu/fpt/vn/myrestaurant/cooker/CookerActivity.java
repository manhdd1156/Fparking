package ise1005.edu.fpt.vn.myrestaurant.cooker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.pusher.client.channel.SubscriptionEventListener;

import java.util.List;

import ise1005.edu.fpt.vn.myrestaurant.R;
import ise1005.edu.fpt.vn.myrestaurant.adapter.CookerAdapter;
import ise1005.edu.fpt.vn.myrestaurant.asynctask.CookerTask;
import ise1005.edu.fpt.vn.myrestaurant.asynctask.IAsyncTaskHandler;
import ise1005.edu.fpt.vn.myrestaurant.config.Constants;
import ise1005.edu.fpt.vn.myrestaurant.config.Session;
import ise1005.edu.fpt.vn.myrestaurant.dto.OrderDetailDTO;


public class CookerActivity extends AppCompatActivity implements IAsyncTaskHandler, AdapterView.OnItemSelectedListener, SubscriptionEventListener {
    ListView lv;
    OrderDetailDTO orderDetailDTO = new OrderDetailDTO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooker);
        lv = (ListView) findViewById(R.id.lv);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                orderDetailDTO = (OrderDetailDTO) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), orderDetailDTO.getId() + "", Toast.LENGTH_LONG).show();
                orderDetailDTO.setStatus(orderDetailDTO.getStatus() + 1);
                loadTable();
                //   url = Constants.API_URL+"orderdetail/updateforcooker/?id="+o.getId() +"&status="+o.getStatus();
//                Intent intent = new Intent(TableActivity.this, OrderForm.class);
//                startActivity(intent);
            }
        });
        loadTable();
        if (Session.pusher != null && Session.channel != null) {
            Session.channel.bind(Constants.PUSHER_EVENT_FOR_COOKER, this);
        }
    }

    @Override
    public void onPostExecute(Object o) {
        List<OrderDetailDTO> lstOrderDetail = (List<OrderDetailDTO>) o;
        CookerAdapter arrayAdapter = new CookerAdapter(lstOrderDetail, this);
        lv.setAdapter(arrayAdapter);
    }

    public void loadTable() {
        Log.d("00000", orderDetailDTO.toString());
        CookerTask cookerTask = new CookerTask(this, orderDetailDTO);
        cookerTask.execute();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onEvent(String channelName, String eventName, final String data) {
        loadTable();
    }
}

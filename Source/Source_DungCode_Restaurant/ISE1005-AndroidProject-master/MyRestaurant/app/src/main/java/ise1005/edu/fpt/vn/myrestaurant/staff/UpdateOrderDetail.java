package ise1005.edu.fpt.vn.myrestaurant.staff;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ise1005.edu.fpt.vn.myrestaurant.R;
import ise1005.edu.fpt.vn.myrestaurant.dto.OrderDetailDTO;

public class UpdateOrderDetail extends AppCompatActivity implements View.OnClickListener{

    TextView name;
    EditText quantity;
    EditText note;
    Button updateBtn;
    Button cancelBtn;
    OrderDetailDTO orderDetailDTO;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_order_detail);
        Intent intent = getIntent();
        name = (TextView) findViewById(R.id.mName);
        quantity = (EditText) findViewById(R.id.mQuantity);
        note = (EditText) findViewById(R.id.mNote);
        updateBtn = (Button) findViewById(R.id.mUpdate) ;
        updateBtn.setOnClickListener(this);
        cancelBtn = (Button) findViewById(R.id.mCancel);
        cancelBtn.setOnClickListener(this);
        Bundle bundle = intent.getExtras();

        if(bundle != null){
            orderDetailDTO = (OrderDetailDTO) bundle.getSerializable("orderDetailDTO");
            id = bundle.getInt("id");
            name.setText(orderDetailDTO.getProduct().getName());
            quantity.setText(orderDetailDTO.getQuantity()+"");
            note.setText(orderDetailDTO.getNote());
        }
    }

    @Override
    public void onClick(View view) {
        int getWiget = view.getId();
        if(getWiget == R.id.mUpdate){

            //chua validate
            orderDetailDTO.setQuantity(Integer.parseInt(quantity.getText().toString()));
            orderDetailDTO.setNote(note.getText().toString());
            Intent returnIntent = new Intent();
            returnIntent.putExtra("orderDetailDTO",orderDetailDTO);
            returnIntent.putExtra("id",id);
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        }
        if(getWiget == R.id.mCancel){
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_CANCELED,returnIntent);
            finish();
        }
    }
}

package ise1005.edu.fpt.vn.myrestaurant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ise1005.edu.fpt.vn.myrestaurant.R;
import ise1005.edu.fpt.vn.myrestaurant.dto.OrderDetailDTO;

/**
 * Created by Administrator on 27-Oct-17.
 */

public class CookerAdapter extends BaseAdapter {
    List<OrderDetailDTO> lstOrderDetail;
    private LayoutInflater layoutInflater;

    public CookerAdapter(List<OrderDetailDTO> lstOrderDetail, Context layoutInflater) {
        this.lstOrderDetail = lstOrderDetail;
        this.layoutInflater = LayoutInflater.from(layoutInflater);
    }


    @Override
    public int getCount() {
        return lstOrderDetail.size();
    }

    @Override
    public Object getItem(int i) {
        return lstOrderDetail.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view==null) {
            view = layoutInflater.inflate(R.layout.order_cooker_list, null);
            holder = new ViewHolder();
            holder.tableName = (TextView) view.findViewById(R.id.tableName);
            holder.quantity = (TextView) view.findViewById(R.id.quantity);
            holder.productname = (TextView) view.findViewById(R.id.productname);
            holder.status = (TextView) view.findViewById(R.id.status);
            view.setTag(holder);

        } else{
            holder = (ViewHolder) view.getTag();
        }
        OrderDetailDTO orderDetailDTO = lstOrderDetail.get(i);
        holder.tableName.setText(orderDetailDTO.getTable().getName());
        holder.quantity.setText(orderDetailDTO.getQuantity()+"");
        holder.productname.setText(orderDetailDTO.getProduct().getName());
        if(orderDetailDTO.getStatus() == 1) {
            holder.status.setText("Cooking");
        }
        if(orderDetailDTO.getStatus() == 0) {
            holder.status.setText("Ordered");
        }
        if(orderDetailDTO.getStatus() == 2) {
            holder.status.setText("Done");
        }
        return view;
    }

    static class ViewHolder{
        TextView tableName;
        TextView productname;
        TextView quantity;
        TextView status;
    }
}

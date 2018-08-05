package ise1005.edu.fpt.vn.myrestaurant.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ise1005.edu.fpt.vn.myrestaurant.R;
import ise1005.edu.fpt.vn.myrestaurant.dto.TableDTO;

/**
 * Created by Administrator on 23-Oct-17.
 */

public class TableStaffAdapter extends BaseAdapter implements View.OnClickListener{
    private List<TableDTO> lstTable;
    private LayoutInflater layoutInflater;

    public TableStaffAdapter(List<TableDTO> lstTable, Context layoutInflater) {
        this.lstTable = lstTable;
        this.layoutInflater = LayoutInflater.from(layoutInflater);
    }

    @Override
    public int getCount() {
       return lstTable.size();
    }

    @Override
    public Object getItem(int i) {
        return lstTable.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            view = layoutInflater.inflate(R.layout.table_list_row, null);
            holder = new ViewHolder();
            holder.name = (TextView) view.findViewById(R.id.name);
            holder.description = (TextView) view.findViewById(R.id.description);
            holder.status = (TextView) view.findViewById(R.id.status);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        Log.d("SE10000",i+"");
        TableDTO table = lstTable.get(i);
        Log.d("SE10000",table.toString());
        Log.d("SE10000", String.valueOf(holder == null));
        Log.d("SE10000", String.valueOf(holder.name == null));
        holder.name.setText(table.getName());
        holder.description.setText(table.getDescription());
        if(table.getStatus() == 0){
            holder.status.setText("Free");
        }else{
            holder.status.setText("Hold");
        }
        return view;
    }

    @Override
    public void onClick(View view) {

    }

    static class ViewHolder{
        TextView name;
        TextView description;
        TextView status;
    }
}

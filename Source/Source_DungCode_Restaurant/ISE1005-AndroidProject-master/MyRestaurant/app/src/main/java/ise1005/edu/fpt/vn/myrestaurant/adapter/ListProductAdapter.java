package ise1005.edu.fpt.vn.myrestaurant.adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ise1005.edu.fpt.vn.myrestaurant.R;
import ise1005.edu.fpt.vn.myrestaurant.asynctask.DownloadImageTask;
import ise1005.edu.fpt.vn.myrestaurant.config.Constants;
import ise1005.edu.fpt.vn.myrestaurant.dto.ProductDTO;

/**
 * Created by Admin on 10/24/2017.
 */

public class ListProductAdapter extends ArrayAdapter<ProductDTO> implements View.OnClickListener {

    private ArrayList<ProductDTO> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtdescription;
        TextView txtprice;
        TextView txtQuantity;
        ImageView info;
        ImageView pic;
    }

    public ListProductAdapter(ArrayList<ProductDTO> dataSet, Context mContext) {
        super(mContext, R.layout.row_item, dataSet);
        this.dataSet = dataSet;
        this.mContext = mContext;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        Object object = getItem(position);
        ProductDTO dataModel = (ProductDTO) object;

        switch (v.getId()) {
            case R.id.item_info:
                Snackbar.make(v, "pick: " + dataModel.getName(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }

    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ProductDTO dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.txtdescription = (TextView) convertView.findViewById(R.id.description);
            viewHolder.txtprice = (TextView) convertView.findViewById(R.id.price_number);
            viewHolder.info = (ImageView) convertView.findViewById(R.id.item_info);
            viewHolder.txtQuantity = (TextView) convertView.findViewById(R.id.quantity_number);
            viewHolder.pic = (ImageView) convertView.findViewById(R.id.pic_product);
            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtName.setText(dataModel.getName());
        viewHolder.txtdescription.setText(dataModel.getDescription());
        viewHolder.txtprice.setText(dataModel.getPrice() + "");
        viewHolder.txtQuantity.setText("");
        viewHolder.info.setOnClickListener(this);
        viewHolder.info.setTag(position);
//        viewHolder.pic.setImageResource(R.mipmap.ic_shop);
        new DownloadImageTask(viewHolder.pic)
                .execute(Constants.API_URL + "product/image/?id=" + dataModel.getId());
        // Return the completed view to render on screen
        return convertView;
    }


}
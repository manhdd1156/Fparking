package ise1005.edu.fpt.vn.myrestaurant.apihelper;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ise1005.edu.fpt.vn.myrestaurant.dto.OrderDTO;
import ise1005.edu.fpt.vn.myrestaurant.dto.OrderDetailDTO;
import ise1005.edu.fpt.vn.myrestaurant.dto.ProductDTO;

/**
 * Created by Admin on 10/27/2017.
 */

public class JSonHelper {


    public static ProductDTO get(JSONObject obj) {
        ProductDTO productDTO = new ProductDTO();
        try {
            productDTO.setDescription(obj.getString("description"));
            productDTO.setName(obj.getString("name"));
            productDTO.setId(Integer.parseInt(obj.getString("id")));
            productDTO.setPrice(Double.parseDouble(obj.getString("price")));
            return productDTO;
        } catch (JSONException e) {
            e.printStackTrace();

        }
        return null;
    }

    public static JSONArray parseJsonOrderDetail(ArrayList<OrderDetailDTO> orderDetailDTO1) throws JSONException {
    JSONArray formData1 = new JSONArray();
        for (OrderDetailDTO orderDetailDTO :
                orderDetailDTO1) {
            JSONObject formData = new JSONObject();
            if(orderDetailDTO.getOrder_id() >-1) {
                formData.put("order_id", orderDetailDTO.getId());
            }
            formData.put("id", orderDetailDTO.getId());
            formData.put("product_id", orderDetailDTO.getProduct_id());
            formData.put("price", orderDetailDTO.getPrice());
            formData.put("quantity", orderDetailDTO.getQuantity());
            formData.put("note", orderDetailDTO.getNote());
            formData1.put(formData);

        }
        Log.d("ListorderDetail:",formData1.toString());
        return formData1;
    }

    public static JSONObject parseJsonOrder(OrderDTO orderDTO) throws JSONException {

        return null;
    }

}

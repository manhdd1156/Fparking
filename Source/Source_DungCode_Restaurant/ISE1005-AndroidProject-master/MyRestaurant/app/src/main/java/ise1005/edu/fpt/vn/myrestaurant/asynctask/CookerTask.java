package ise1005.edu.fpt.vn.myrestaurant.asynctask;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ise1005.edu.fpt.vn.myrestaurant.config.Constants;
import ise1005.edu.fpt.vn.myrestaurant.dto.OrderDetailDTO;
import ise1005.edu.fpt.vn.myrestaurant.dto.ProductDTO;
import ise1005.edu.fpt.vn.myrestaurant.dto.TableDTO;

/**
 * Created by Administrator on 27-Oct-17.
 */

public class CookerTask extends AsyncTask<Void, Void, Boolean> {
    List<OrderDetailDTO> lstOrderDetail = new ArrayList<>();
    private IAsyncTaskHandler container;
    private OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
    String url = Constants.API_URL + "orderdetail/getforcooker/";

    public CookerTask(IAsyncTaskHandler container, OrderDetailDTO orderDetailDTO) {
        this.container = container;
        this.orderDetailDTO = orderDetailDTO;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... voids) {
        HttpHandler sh = new HttpHandler();

        String jsonStr = null;
        if(orderDetailDTO.getId()!=0){
            try{
                JSONObject update = new JSONObject();
                update.put("id", orderDetailDTO.getId());
                update.put("status", orderDetailDTO.getStatus());
                String json = sh.post(Constants.API_URL+"orderdetail/updateforcooker/" , update.toString());
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
            jsonStr = sh.get(url);

        decodeData(jsonStr);
        return null;
    }

    public void decodeData(String jsonStr){
        ProductDTO productDTO1 = new ProductDTO();
        TableDTO tableDTO = new TableDTO();
        lstOrderDetail.clear();
        if (jsonStr != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                JSONArray results = jsonObject.getJSONArray("result");
                for (int i = 0; i < results.length(); i++) {
                    JSONObject result = results.getJSONObject(i);
                    Log.d("1111", result.toString());
                    OrderDetailDTO orderDetailDTO;
                    int id = result.getInt("id");
                    int order_id = result.getInt("order_id");
                    int product_id = result.getInt("product_id");
                    double price = result.getDouble("price");
                    int quantity = result.getInt("quantity");
                    String note = result.getString("note");
                    int status = result.getInt("status");
                    int table_id = result.getInt("table_id");

                    JSONObject productDTO = result.getJSONObject("product");

                    int idPro = productDTO.getInt("id");
                    String namePro = productDTO.getString("name");
                    String descriptionPro = productDTO.getString("description");
                    double pricePro = productDTO.getDouble("price");
                    productDTO1 = new ProductDTO(idPro, namePro, pricePro, descriptionPro);

                    JSONObject table = result.getJSONObject("table");
                    int idTable = table.getInt("id");
                    String nameTable = table.getString("name");
                    String descriptionTable = table.getString("description");
                    int statusTable = table.getInt("status");
                    tableDTO = new TableDTO(idTable, nameTable, descriptionTable, statusTable);

                    orderDetailDTO = new OrderDetailDTO();
                    orderDetailDTO.setId(id);
                    orderDetailDTO.setOrder_id(order_id);
                    orderDetailDTO.setNote(note);
                    orderDetailDTO.setPrice(price);
                    orderDetailDTO.setProduct(productDTO1);
                    orderDetailDTO.setQuantity(quantity);
                    orderDetailDTO.setProduct_id(product_id);
                    orderDetailDTO.setStatus(status);
                    orderDetailDTO.setTable(tableDTO);

                    Log.d("7777", orderDetailDTO.toString());
                    lstOrderDetail.add(orderDetailDTO);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        container.onPostExecute(lstOrderDetail);
    }
}

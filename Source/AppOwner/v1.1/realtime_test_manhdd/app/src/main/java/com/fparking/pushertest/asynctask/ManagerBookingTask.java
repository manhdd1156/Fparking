package com.fparking.pushertest.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.fparking.pushertest.MainActivity;
import com.fparking.pushertest.R;
import com.fparking.pushertest.adapter.CarAdapter;
import com.fparking.pushertest.config.Constants;
import com.fparking.pushertest.dto.BookingDTO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ManagerBookingTask {
    public ManagerBookingTask(String method, Context t, View v, String txtSearch, IAsyncTaskHandler container, ListView lv, BookingDTO b) {

        if (method.equals("get")) {
            new GetBookingTask(txtSearch,v, container, lv).execute();
        }else if (method.equals("update")) {
            new UpdateBookingTask(t,v,container, b).execute((Void) null);
        }else if(method.equals("insert")) {
            new CreateBookingTask(v,container,b).execute((Void) null);
        }else if(method.equals("search")) {
            new SearchBookingTask(txtSearch,t,v,container,lv).execute((Void) null);
        }
// else if (method.equals("delete")) {
//            new DeleteMenuTask(p).execute((Void) null);
//        } else if (method.equals("updateGetForm")) {
//            new UpdateFormMenuTask(container, p).execute((Void) null);
//        } else if (method.equals("update")) {
//            new Update(container, p).execute((Void) null);
//        }

    }
}

class GetBookingTask extends AsyncTask<Void, Void, List> {
    ProgressDialog pdLoading;
    private final String txtSearch;
    private final IAsyncTaskHandler container;
    private final List<HashMap<String, String>> lstBookings;
    private JSONObject oneBooking;
    private Activity activity;
    private ListView lv;
    private View view;

    public GetBookingTask(String txtSearch,View view, IAsyncTaskHandler container, ListView lv) {
        this.txtSearch = txtSearch;
        this.container = container;
        activity = (Activity) container;
        lstBookings = new ArrayList<>();
        this.view = view;
        this.lv = lv;
        pdLoading = new ProgressDialog(view.getContext());
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        //this method will be running on UI thread
        pdLoading.setMessage("\tĐợi xíu...");
        pdLoading.setCancelable(false);
        pdLoading.show();

    }

    @Override
    protected List doInBackground(Void... voids) {
        HttpHandler httpHandler = new HttpHandler();
        try {
            ArrayList<BookingDTO> list = new ArrayList<>();
            String json = httpHandler.get(Constants.API_URL + "booking/get_listBookingInfor.php?parkingID="+txtSearch);
            JSONObject jsonObj = new JSONObject(json);
            JSONArray bookings = jsonObj.getJSONArray("cars");
            for (int i = 0; i < bookings.length(); i++) {
                oneBooking = bookings.getJSONObject(i);
                String bookingID = oneBooking.getString("bookingID");
                String status = oneBooking.getString("status");
                if(!status.equals("1") && !status.equals("2")) {
                    continue;
                }
                String licensePlate = oneBooking.getString("licensePlate");
                String type = oneBooking.getString("type");
                String checkinTime = oneBooking.getString("checkinTime");
                String checkoutTime = oneBooking.getString("checkoutTime");

                String price = oneBooking.getString("price");
                BookingDTO b = new BookingDTO(Integer.parseInt(bookingID),0,0,status,checkinTime,checkoutTime,licensePlate,type,Double.parseDouble(price));
                list.add(b);
            }
            return list;
        } catch (Exception ex) {
            Log.e("Error managerBooking:", ex.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(List list) {
        super.onPostExecute(list);
        container.onPostExecute(list);
        pdLoading.dismiss();
        System.out.println("set list view data : " + list);
        if (list != null && list.size() > 0) {
            System.out.println(list);

            CarAdapter adapter = new CarAdapter(view,view.getContext(), list);
            lv.setAdapter(adapter);
        }
    }
}
class SearchBookingTask extends AsyncTask<Void, Void, List> {
    ProgressDialog pdLoading;
    private final String txtSearch;
    private final IAsyncTaskHandler container;
    private final List<HashMap<String, String>> lstBookings;
    private JSONObject oneBooking;
    private Activity activity;
    private ListView lv;
    private Context context;
    private View view;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public SearchBookingTask(String txtSearch,Context context,View view, IAsyncTaskHandler container, ListView lv) {
        this.txtSearch = txtSearch;
        this.container = container;
        this.context = context;
        activity = (Activity) container;
        lstBookings = new ArrayList<>();
        this.view = view;
        this.lv = lv;
        pdLoading = new ProgressDialog(view.getContext());
        pref = context.getSharedPreferences("searchVariable", 0);// 0 - là chế độ private
        editor = pref.edit();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        //this method will be running on UI thread
        pdLoading.setMessage("\tĐợi xíu...");
        pdLoading.setCancelable(false);
        pdLoading.show();
    }

    @Override
    protected List doInBackground(Void... voids) {
        HttpHandler httpHandler = new HttpHandler();
        try {
            ArrayList<BookingDTO> list = new ArrayList<>();
            String json = httpHandler.get(Constants.API_URL + "booking/search_BookingInfor.php?"+txtSearch);
            JSONObject jsonObj = new JSONObject(json);
            JSONArray bookings = jsonObj.getJSONArray("result");
            for (int i = 0; i < bookings.length(); i++) {
                oneBooking = bookings.getJSONObject(i);
                String bookingID = oneBooking.getString("bookingID");
                String status = oneBooking.getString("status");
                String parkingID = oneBooking.getString("parkingID");
                String licensePlate = oneBooking.getString("licensePlate");
                String type = oneBooking.getString("type");
                String carID = oneBooking.getString("carID");
                String checkinTime = oneBooking.getString("checkinTime");
                String checkoutTime = oneBooking.getString("checkoutTime");
                String price = oneBooking.getString("price");
                editor.putString("bookingID",bookingID);
                editor.putString("status",status);
                editor.putString("parkingID",parkingID);
                editor.putString("licensePlate",licensePlate);
                editor.putString("type",type);
                editor.putString("carID",carID);
                editor.putString("checkinTime",checkinTime);
                editor.putString("checkoutTime",checkoutTime);
                editor.putString("price",price);
                editor.commit();
                BookingDTO b = new BookingDTO(Integer.parseInt(bookingID),Integer.parseInt(parkingID),Integer.parseInt(carID),status,checkinTime,checkoutTime,licensePlate,type,Double.parseDouble(price));
                list.add(b);
            }
            return list;
        } catch (Exception ex) {
            Log.e("Error managerBooking:", ex.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(List list) {
        super.onPostExecute(list);
//        container.setList(list);
        pdLoading.dismiss();
//        System.out.println(list);
//        if (list != null && list.size() > 0) {
//            System.out.println(list);
//
//            CarAdapter adapter = new CarAdapter(view,view.getContext(), list);
//            lv.setAdapter(adapter);
//        }
    }
}
class UpdateBookingTask extends AsyncTask<Void, Void, Boolean> {
    ProgressDialog pdLoading;
    IAsyncTaskHandler container;
    BookingDTO b;
    Activity activity;
    Context context;
    boolean success = false;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    public UpdateBookingTask(Context context,View v, IAsyncTaskHandler container, BookingDTO b) {
        this.container = container;
        this.activity = (Activity) container;
        this.b = b;
        this.context = context;
        pdLoading = new ProgressDialog(v.getContext());
        pref = context.getSharedPreferences("searchVariable", 0);// 0 - là chế độ private
        editor = pref.edit();
    }

//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//
//        //this method will be running on UI thread
//        pdLoading.setMessage("\tĐợi xíu...");
//        pdLoading.setCancelable(false);
//        pdLoading.show();
//
//    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... voids) {
        HttpHandler httpHandler = new HttpHandler();
        try {
            Log.e("Update-Async", b.toString());
            JSONObject formData = new JSONObject();
            formData.put("bookingID", b.getBookingID());
            formData.put("status", b.getStatus());
            Calendar calendar = Calendar.getInstance();
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String checkinTime= dateFormatter.format(calendar.getTime().getTime());
            if(b.getStatus().equals("2")) {
                b.setCheckinTime(checkinTime);
                formData.put("checkinTime", b.getCheckinTime());
                formData.put("carID", b.getCarID());
                formData.put("actioncheck","checkin");
                formData.put("actionspace","Inc");

            }else if(b.getStatus().equals("3")) {
                String checkoutTime = dateFormatter.format(calendar.getTime().getTime());
                b.setCheckoutTime(checkoutTime);
                formData.put("checkoutTime", b.getCheckoutTime());
                formData.put("actioncheck","checkout");
                formData.put("actionspace","Desc");
            }
            formData.put("carID",b.getCarID());
            formData.put("licensePlate", b.getLicensePlate());
            formData.put("type", b.getType());
//            editor.putInt("bookingID", b.getBookingID());
            editor.putString("checkinTime", b.getCheckinTime());
            editor.commit();

            Date date1 = dateFormatter.parse(b.getCheckinTime());
            if(b.getStatus().equals("3")) {
                Date date2 = dateFormatter.parse(b.getCheckoutTime());
                long diff = date2.getTime() - date1.getTime();
                double diffInHours = diff / ((double) 1000 * 60 * 60);
                NumberFormat formatter = new DecimalFormat("###,###");
                NumberFormat formatterHour = new DecimalFormat("0.00");
                formData.put("price", formatter.format(b.getPrice()) + "vnđ");
                formData.put("hours", formatterHour.format(diffInHours) + " giờ \n");
                formData.put("totalPrice", formatter.format(diffInHours * b.getPrice()) + "vnđ");
            }
            System.out.println(formData.toString());
            String json = httpHandler.post(Constants.API_URL + "booking/update_BookingInfor.php", formData.toString());
            httpHandler.post(Constants.API_URL + "booking/update_BookingSpace.php", formData.toString());
            System.out.println("=============================");
            System.out.println(json);
            System.out.println("=============================");
            JSONObject jsonObj = new JSONObject(json);
            if (jsonObj.getInt("size") > 0) {
                success = true;
            }

        } catch (Exception ex) {
            Log.e("Error up manager:", ex.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
//        pdLoading.dismiss();

        Intent refresh = new Intent(activity, MainActivity.class);
//                                    refresh.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//
        this.activity.setResult(RESULT_OK, refresh);
//        this.activity.;
        activity.startActivity(refresh);
    }

}

class CreateBookingTask extends AsyncTask<Void, Void, Boolean> {
    ProgressDialog pdLoading;
    IAsyncTaskHandler container;
    BookingDTO b;
    Activity activity;
    boolean success = false;

    public CreateBookingTask(View v, IAsyncTaskHandler container, BookingDTO b) {
        this.container = container;
        this.activity = (Activity) container;
        this.b = b;
        pdLoading = new ProgressDialog(v.getContext());
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        //this method will be running on UI thread
        pdLoading.setMessage("\tĐợi xíu...");
        pdLoading.setCancelable(false);
        pdLoading.show();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Boolean doInBackground(Void... voids) {
        HttpHandler httpHandler = new HttpHandler();
        try {
            Log.e("Create-Async", b.toString());
            JSONObject formData = new JSONObject();
            formData.put("carID", b.getCarID());
            formData.put("parkingID", b.getParkingID());

            System.out.println(formData.toString());
            String json = httpHandler.post(Constants.API_URL + "booking/create_BookingInfor.php", formData.toString());
            System.out.println("=============================");
            System.out.println( "json tạo : " +  json);
            System.out.println("=============================");
            JSONObject jsonObj = new JSONObject(json);
            if (jsonObj.getInt("size") > 0) {
                success = true;
            }

        } catch (Exception ex) {
            Log.e("Error tạo :", ex.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        pdLoading.dismiss();
        Intent refresh = new Intent(activity, MainActivity.class);
//                                    refresh.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


//        Intent intent = new Intent();
//        if (success)
//            intent.putExtra("result", "success!");
//        else
//            intent.putExtra("result", "failed");
        this.activity.setResult(RESULT_OK, refresh);
        this.activity.finish();
        activity.startActivity(refresh);
    }

}

//class SearchBookingTask extends AsyncTask<Void, Void, Boolean> {
//    private IAsyncTaskHandler container;
//    List<BookingDTO> lstBookingDetail = new ArrayList<>();
//    private BookingDTO bookingDetail;
//    HttpHandler httpHandler = new HttpHandler();
//
//    public SearchBookingTask(IAsyncTaskHandler container, BookingDTO bookingDetailDTO) {
//        this.container = container;
//        this.bookingDetail = bookingDetailDTO;
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    @Override
//    protected Boolean doInBackground(Void... voids) {
//
//        ArrayList<BookingDTO> list = new ArrayList<>();
//        String json = httpHandler.get(Constants.API_URL + "booking/search_BookingInfor.php?="+txtSearch);
//        JSONObject jsonObj = new JSONObject(json);
//        JSONArray bookings = jsonObj.getJSONArray("cars");
//        for (int i = 0; i < bookings.length(); i++) {
//            oneBooking = bookings.getJSONObject(i);
//            String bookingID = oneBooking.getString("bookingID");
//            String status = oneBooking.getString("status");
//            if(!status.equals("1") && !status.equals("2")) {
//                continue;
//            }
//            String licensePlate = oneBooking.getString("licensePlate");
//            String type = oneBooking.getString("type");
//            String checkinTime = oneBooking.getString("checkinTime");
//            String checkoutTime = oneBooking.getString("checkoutTime");
//
//            String price = oneBooking.getString("price");
//            BookingDTO b = new BookingDTO(Integer.parseInt(bookingID),0,0,status,checkinTime,checkoutTime,licensePlate,type,Double.parseDouble(price));
//            list.add(b);
//        }
//        HttpHandler sh = new HttpHandler();
//
//        String jsonStr = null;
//        if(bookingDetail.getBookingID()!=0 || bookingDetail.getCarID()!=0){
//            try{
//                JSONObject search = new JSONObject();
//                search.put("bookingID", bookingDetail.getBookingID());
//                search.put("carID", bookingDetail.getCarID());
//                String json = httpHandler.post(Constants.API_URL + "booking/search_BookingInfor.php", search.toString());
//                JSONObject jsonObject = new JSONObject(json);
//                JSONObject oneBooking = jsonObject.getJSONObject("result");
//                String licensePlate = oneBooking.getString("licensePlate");
//                String parkingID = oneBooking.getString("parkingID");
//                String carID = oneBooking.getString("carID");
//                String status = oneBooking.getString("status");
//                String type = oneBooking.getString("type");
//                String checkinTime = oneBooking.getString("checkinTime");
//                String checkoutTime = oneBooking.getString("checkoutTime");
//                String price = oneBooking.getString("price");
//
//                bookingDetail = new BookingDTO(bookingDetail.getBookingID(),Integer.parseInt(parkingID),Integer.parseInt(carID),status,checkinTime,checkoutTime,licensePlate,type,Double.parseDouble(price));
//                lstBookingDetail.add(bookingDetail);
//            }catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//
//    }
//    @Override
//    protected void onPostExecute(Boolean aBoolean) {
//        super.onPostExecute(aBoolean);
//        container.onPostExecute(lstBookingDetail);
//    }
//}

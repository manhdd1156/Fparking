package com.fparking.pushertest;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
//import android.support.v7.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.fparking.pushertest.adapter.CarAdapter;
import com.fparking.pushertest.asynctask.HttpHandler;
import com.fparking.pushertest.asynctask.IAsyncTaskHandler;
import com.fparking.pushertest.asynctask.ManagerBookingTask;
import com.fparking.pushertest.config.Constants;
import com.fparking.pushertest.dto.BookingDTO;
import com.fparking.pushertest.dto.ParkingInforDTO;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity implements IAsyncTaskHandler {
    String ownerPhoneNumber, bookingSpace, totalSpace, parkingID;
    public int currentSpace, outOfBooking;
    ManagerBookingTask getBookings;
    ListView lv;
    BookingDTO bookingDTO;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    TextView tvSpace, tvAddress;
    String wantPermission = android.Manifest.permission.READ_PHONE_STATE;
    private static final int PERMISSION_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bookingDTO = new BookingDTO();

        if (!checkPermission(wantPermission)) {
            requestPermission(wantPermission);
        } else {
            ownerPhoneNumber = getPhone();
        }
        /*setting toolbar*/
        System.out.println(ownerPhoneNumber);
        getSupportActionBar().hide();

        pref = getApplicationContext().getSharedPreferences("searchVariable", 0);// 0 - là chế độ private
        editor = pref.edit();

//        getSupportActionBar().hide();
        tvSpace = (TextView) findViewById(R.id.tvSpace);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        lv = (ListView) findViewById(R.id.cars_list);

        /*start pusher*/
        PusherOptions options = new PusherOptions();
        options.setCluster("ap1");
        Pusher pusher = new Pusher(Constants.PUSHER_KEY, options);

        Channel channel = pusher.subscribe(Constants.PUSHER_CHANNEL);

        channel.bind("ORDER_FOR_OWNER", new SubscriptionEventListener() {
            @Override
            public void onEvent(String channelName, String eventName, final String data) {

                try {
                    System.out.println("data order : " + data);
                    JSONObject json = new JSONObject(data);
                    handleDataMessage(json, "order");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                createNotification();
            }
        });
        channel.bind("CHECKIN_FOR_OWNER", new SubscriptionEventListener() {
            @Override
            public void onEvent(String channelName, String eventName, final String data) {

                try {
                    System.out.println("data checkin là : " + data);
                    JSONObject json = new JSONObject(data);
                    handleDataMessage(json, "checkin");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                createNotification();

            }
        });
        channel.bind("CHECKOUT_FOR_OWNER", new SubscriptionEventListener() {
            @Override
            public void onEvent(String channelName, String eventName, final String data) {

                try {
                    System.out.println("data checkout là : " + data);
                    JSONObject json = new JSONObject(data);
                    handleDataMessage(json, "checkout");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                createNotification();

            }
        });
        pusher.connect();

        /*End pusher*/
        new GetParkingTask().execute((Void) null);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pref = getApplicationContext().getSharedPreferences("searchVariable", 0);// 0 - là chế độ private
        editor = pref.edit();
        new GetParkingTask().execute((Void) null);
    }

    private void handleDataMessage(final JSONObject json, String action) {
//        Log.e(TAG, "push json: " + json.toString());
        try {
//            JSONObject data = json.getJSONObject("data");
            final String carID = json.getString("carID");


            if (action.equals("order")) { // người dùng order => insert booking với status = 1
                new SearchBookingTask("carID=" + carID, "order").execute((Void) null);
//                        final BookingDTO b = new BookingDTO(0, Integer.parseInt(parkingID), Integer.parseInt(carID), "1", "", "", "", "", Double.parseDouble("0"));
            } else if (action.equals("checkin")) {
                final String bookingID = json.getString("bookingID");
                new SearchBookingTask("bookingID=" + bookingID, "checkin").execute((Void) null);

            } else if (action.equals("checkout")) {
                final String bookingID = json.getString("bookingID");
                new SearchBookingTask("bookingID=" + bookingID, "checkout").execute((Void) null);
            }
//            else
        } catch (JSONException e) {
//            Log.e(TAG, "Json Exception: " + e.getMessage());
            System.out.println("Json Exception: " + e.getMessage());
        } catch (Exception e) {
//            Log.e(TAG, "Exception: " + e.getMessage());
            System.out.println("Json Exception: " + e.getMessage());
        }
    }

//    public void createBooing() {
////        public BookingDTO(int bookingID,int parkingID,int carID, String status, String checkinTime, String checkoutTime, String licensePlate, String type, double price) {
//
//    }



    private String getPhone() {
        TelephonyManager phoneMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(MainActivity.this, wantPermission) != PackageManager.PERMISSION_GRANTED) {
            return "??";
        }
        return phoneMgr.getLine1Number();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ownerPhoneNumber = getPhone();
                } else {

                    Toast.makeText(MainActivity.this, "Permission Denied. We can't get phone number.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void requestPermission(String permission) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permission)) {
            Toast.makeText(MainActivity.this, "Phone state permission allows us to get phone number. Please allow it for additional functionality.", Toast.LENGTH_LONG).show();
        }
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, PERMISSION_REQUEST_CODE);
    }

    private boolean checkPermission(String permission) {
        if (Build.VERSION.SDK_INT >= 23) {
            int result = ContextCompat.checkSelfPermission(MainActivity.this, permission);
            if (result == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    class GetParkingTask extends AsyncTask<Void, Void, Boolean> {

        private JSONObject oneParking;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected Boolean doInBackground(Void... voids) {
            HttpHandler httpHandler = new HttpHandler();
            try {
                String json = httpHandler.get(Constants.API_URL + "parking/get.php?ownerPhoneNumber=" + getPhone().replace("+84","0"));
                JSONObject jsonObj = new JSONObject(json);
                JSONArray parkings = jsonObj.getJSONArray("parkingInfor");
                oneParking = parkings.getJSONObject(0);
                String parkingid = oneParking.getString("parkingID");
                String address = oneParking.getString("address");
                String space = oneParking.getString("space");
                String currentSpace = oneParking.getString("currentSpace");
                editor.putInt("parkingID",Integer.parseInt(parkingid));
                editor.commit();
                setText(tvAddress, address);
                totalSpace = space;
                setText(tvSpace,currentSpace + "/" + space);
//                tvSpace.setText(currentSpace + "/" + space);
//                json = httpHandler.get(Constants.API_URL + "booking/get_countBookingInfor.php?parkingID=" + parkingID);
//                jsonObj = new JSONObject(json);
//                JSONArray bookingInfors = jsonObj.getJSONArray("numberCarInParking");
//                JSONObject c = bookingInfors.getJSONObject(0);
//                bookingSpace = c.getString("NumberOfBooking");
            } catch (Exception ex) {
                Log.e("Error:", ex.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            new GetBookingTask(pref.getInt("parkingID",0)+"").execute((Void) null);
//            new ManagerBookingTask("get", getApplicationContext(), getWindow().getDecorView().getRootView(), parkingID, MainActivity.this, lv, null);
        }
    }
    class GetBookingTask extends AsyncTask<Void, Void, List> {
        ProgressDialog pdLoading;
        private final String txtSearch;
//        private final IAsyncTaskHandler container;
        private final List<HashMap<String, String>> lstBookings;
        private JSONObject oneBooking;
        private Activity activity;
//        private View view;

        public GetBookingTask(String txtSearch) {
            this.txtSearch = txtSearch;
//            this.container = container;
//            activity = (Activity) container;
            lstBookings = new ArrayList<>();
//            this.view = view;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading = new ProgressDialog(MainActivity.this);
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
                    BookingDTO b = new BookingDTO(Integer.parseInt(bookingID),Integer.parseInt(txtSearch),0,status,checkinTime,checkoutTime,licensePlate,type,Double.parseDouble(price));
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
            pdLoading.dismiss();
//            System.out.println("set list view data : " + list);
            if (list != null && list.size() > 0) {
                System.out.println(list);

                CarAdapter adapter = new CarAdapter(getWindow().getDecorView().getRootView(),MainActivity.this, list);
                lv.setAdapter(adapter);
            }
        }
    }
    class SearchBookingTask extends AsyncTask<Void, Void, Boolean> {

        private JSONObject oneBooking;
//        ProgressDialog pdLoading;
        String txtSearch, action;



        public SearchBookingTask(String txtSearch, String action) {


            this.txtSearch = txtSearch;
            this.action = action;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            runOnUiThread(new Runnable() {
//
//                @Override
//                public void run() {
//                    pdLoading = new ProgressDialog(MainActivity.this);
//                    //this method will be running on UI thread
//                    pdLoading.setMessage("\tĐợi xíu...");
//                    pdLoading.setCancelable(false);
//                    pdLoading.show();
//                    // Stuff that updates the UI
//
//                }
//            });

        }
        @Override
        protected Boolean doInBackground(Void... voids) {
            HttpHandler httpHandler = new HttpHandler();
            try {
                String json = httpHandler.get(Constants.API_URL + "booking/search_BookingInfor.php?" + txtSearch);
                JSONObject jsonObj = new JSONObject(json);
                JSONArray bookings = jsonObj.getJSONArray("result");
                if (action.contains("order")) {
                    for (int i = 0; i < bookings.length(); i++) {
                        oneBooking = bookings.getJSONObject(i);

//                        String licensePlate = oneBooking.getString("licensePlate");
//                        String carID = oneBooking.getString("carID");
                        editor.putString("licensePlate",oneBooking.getString("licensePlate"));
                        editor.putInt("carID",Integer.parseInt(oneBooking.getString("carID")));
                        editor.commit();
//                        bookingDTO = new BookingDTO(0, Integer.parseInt(parkingID), Integer.parseInt(carID), "", "", "", licensePlate, "", 3000);
                    }
                } else {

                    oneBooking = bookings.getJSONObject(0);
                    editor.putInt("bookingID",Integer.parseInt(oneBooking.getString("bookingID")));
                    editor.putString("status",oneBooking.getString("status"));
                    editor.putString("licensePlate",oneBooking.getString("licensePlate"));
                    editor.putString("type",oneBooking.getString("type"));
                    editor.putString("checkinTime",oneBooking.getString("checkinTime"));
                    editor.putString("checkoutTime",oneBooking.getString("checkoutTime"));
                    editor.putString("price",oneBooking.getString("price"));
                    editor.commit();
//                    bookingID = oneBooking.getString("bookingID");

//                    String status = oneBooking.getString("status");
//                    String licensePlate = oneBooking.getString("licensePlate");
//                    String type = oneBooking.getString("type");
//                    String checkinTime = oneBooking.getString("checkinTime");
//                    String checkoutTime = oneBooking.getString("checkoutTime");
//                    String price = oneBooking.getString("price");
//
//                    bookingDTO = new BookingDTO(Integer.parseInt(bookingID), Integer.parseInt(parkingID), Integer.parseInt(txtSearch), status, checkinTime, checkoutTime, licensePlate, type, Double.parseDouble(price));


                }
            } catch (Exception ex) {
                Log.e("Error managerBooking:", ex.getMessage());
            }
            return null;
        }
//        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
//            pdLoading.dismiss();
            if (action.contains("order")) {
                createNotification("Có Xe : " + pref.getString("licensePlate","") + "muốn đặt chỗ");
                createDialog("order");
            } else if (action.contains("checkin")) {
                createNotification("Xe " + pref.getString("licensePlate","") + " đã đến và muốn vào bãi");
                createDialog("checkin");
            } else if (action.contains("checkout")) {
                createNotification("Xe " + pref.getString("licensePlate","") + " muốn thanh toán");
                createDialog("checkout");


            }
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
        public UpdateBookingTask(BookingDTO bookingDTO) {
//            this.container = container;
//            this.activity = (Activity) container;
            this.b = bookingDTO;
//            this.context = context;
            pdLoading = new ProgressDialog(MainActivity.this.getApplicationContext());
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
                editor.putString("checkinTime", b.getCheckinTime());
                editor.putString("checkoutTime", b.getCheckoutTime());

                editor.commit();
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
                Log.e("Error:", ex.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        pdLoading.dismiss();
            onResume();
//            Intent refresh = new Intent(activity, MainActivity.class);
////                                    refresh.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////
////
//            this.activity.setResult(RESULT_OK, refresh);
//            this.activity.;
//        activity.startActivity(refresh);
        }

    }
    public void createNotification(String title) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
//
//        // Build notification
//        // Actions are just fake
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle(title)
                .setContentText("Hãy xác nhận yêu cầu").setSmallIcon(R.drawable.apply)
                .setAutoCancel(true)
                .setContentIntent(pIntent)
                .addAction(R.drawable.apply, "Đồng ý", pIntent)
                .addAction(R.drawable.apply, "Hủy", pIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(0, mBuilder.build());
    }

    public void createDialog(String action) {
        if(action.equals("action")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int choice) {
                    switch (choice) {
                        case DialogInterface.BUTTON_POSITIVE:
                            // create booking
                            BookingDTO b = new BookingDTO(pref.getInt("bookingID", 0), pref.getInt("parkingID", 0), pref.getInt("carID", 0), "", "", "", "", "", 5000);
                            new ManagerBookingTask("insert", getApplicationContext(), getWindow().getDecorView().getRootView(), "not need", MainActivity.this, lv, bookingDTO);
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };
            try {
                builder.setMessage("Xe " + pref.getString("licensePlate", "") + "muốn đỗ xe trong bãi bạn có đồng ý không")
                        .setPositiveButton("Có", dialogClickListener)
                        .setNegativeButton("Không", dialogClickListener).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(action.equals("checkin")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int choice) {
                    switch (choice) {
                        case DialogInterface.BUTTON_POSITIVE:
                            editor.putString("status","2");
                            editor.commit();
//                            bookingDTO.setStatus("2");
//                            bookingDTO.setCarID(2);
//                            bookingDTO.setBookingID(Integer.parseInt(bookingID));
                            BookingDTO b = new BookingDTO(pref.getInt("bookingID", 0), pref.getInt("parkingID", 0), pref.getInt("carID", 0), pref.getString("status",""), "", "", "", "", 5000);
                            new UpdateBookingTask(b).execute((Void)null);
//                            new ManagerBookingTask("update", getApplicationContext(), getWindow().getDecorView().getRootView(), parkingID, MainActivity.this, lv, b);
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };

            try {
                builder.setMessage("Xe " + pref.getString("lisencePlate","") + "muốn checkin bạn có đồng ý không")
                        .setPositiveButton("Có", dialogClickListener)
                        .setNegativeButton("Không", dialogClickListener).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if(action.equals("checkout")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int choice) {
                    switch (choice) {
                        case DialogInterface.BUTTON_POSITIVE:
                            Calendar calendar = Calendar.getInstance();
                            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            final String checkoutTime = dateFormatter.format(calendar.getTime().getTime());
                            DialogInterface.OnClickListener dialogClickListener2 = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int choice) {
                                    switch (choice) {
                                        case DialogInterface.BUTTON_POSITIVE:

                                            editor.putString("status","3");
                                            editor.commit();
//                                            bookingDTO.setCheckoutTime(checkoutTime.toString());
                                            BookingDTO b = new BookingDTO(pref.getInt("bookingID", 0), pref.getInt("parkingID", 0), pref.getInt("carID", 0), pref.getString("status",""), pref.getString("checkinTime",""), "", pref.getString("licensePlate",""), pref.getString("type",""), Double.parseDouble(pref.getString("price","")));
                                            new UpdateBookingTask(b).execute((Void)null);
//
//                                            new ManagerBookingTask("update", getApplicationContext(), getWindow().getDecorView().getRootView(), parkingID, MainActivity.this, lv, bookingDTO);
                                            break;
                                        case DialogInterface.BUTTON_NEGATIVE:
                                            break;
                                    }
                                }
                            };
                            AlertDialog.Builder builder2 = new AlertDialog.Builder(MainActivity.this);
                            try {
                                Date date1 = dateFormatter.parse(pref.getString("checkinTime",""));
                                Date date2 = dateFormatter.parse(checkoutTime);
                                long diff = date2.getTime() - date1.getTime();
                                double diffInHours = diff / ((double) 1000 * 60 * 60);
                                NumberFormat formatter = new DecimalFormat("###,###");
                                NumberFormat formatterHour = new DecimalFormat("0.00");
                                System.out.println(formatter.format(4.0));
                                builder2.setMessage("\tHóa đơn checkout \n"
                                        + "Biển số :          " + pref.getString("licensePlate","") + "\n"
                                        + "loại xe :           " + pref.getString("type","") + "\n"
                                        + "thời gian vào : " + pref.getString("checkinTime","") + "\n"
                                        + "thời gian ra :   " + checkoutTime + "\n"
                                        + "giá đỗ :           " + formatter.format(pref.getString("price","")) + "vnđ\n"
                                        + "thời gian đỗ :  " + formatterHour.format(diffInHours) + " giờ \n"
                                        + "tổng giá :        " + formatter.format(diffInHours * Double.parseDouble(pref.getString("price",""))) + "vnđ")
                                        .setPositiveButton("Yes", dialogClickListener2)
                                        .setNegativeButton("No", dialogClickListener2).show();

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };


            try {
                builder.setMessage("Xe " + pref.getString("licensePlate","") + "muốn checkout bạn có đồng ý không")
                        .setPositiveButton("Có", dialogClickListener)
                        .setNegativeButton("Không", dialogClickListener).show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void setText(final TextView text, final String value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(value);
            }
        });
    }

    public void test() {

    }

    @Override
    public void onPostExecute(Object o) {

    }

}

package com.example.hung.fparking;

import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hung.fparking.asynctask.BookingTask;
import com.example.hung.fparking.asynctask.DriverLoginTask;
import com.example.hung.fparking.asynctask.FineTask;
import com.example.hung.fparking.asynctask.IAsyncTaskHandler;
import com.example.hung.fparking.asynctask.NotificationTask;
import com.example.hung.fparking.asynctask.ParkingInforTask;
import com.example.hung.fparking.asynctask.ParkingTask;
import com.example.hung.fparking.dto.FineDTO;
import com.example.hung.fparking.dto.ParkingDTO;
import com.example.hung.fparking.entity.Route;
import com.example.hung.fparking.model.DirectionFinder;
import com.example.hung.fparking.model.DirectionFinderListener;
import com.example.hung.fparking.model.GPSTracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Direction extends FragmentActivity implements OnMapReadyCallback, DirectionFinderListener, IAsyncTaskHandler, LocationListener, GoogleMap.OnCameraMoveStartedListener {

    private GoogleMap mMap;
    Button buttonCheckin, buttonHuy, btnOK;
    TextView textViewAlert;
    View mMapView;
    private TextToSpeech textToSpeechNearPlace;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;
    private LocationManager locationManager = null;

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mPreferencesEditor;

    ArrayList<ParkingDTO> parkingDTOS;
    private boolean userGesture = false;
    private boolean noti = true;
    private CameraPosition cameraPosition;
    private Location distination;

    private String parkingID, vehicleID;
    AlertDialog.Builder builder;
    ArrayList<FineDTO> fineDTOS;

    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        builder = new AlertDialog.Builder(Direction.this);
        progressDialog = ProgressDialog.show(this, "Vui lòng đợi.",
                "Đang tìm đường đi..!", true);

        // khởi tạo shareRePreference
        mPreferences = getSharedPreferences("driver", 0);
        mPreferencesEditor = mPreferences.edit();

        //excute chỉ đường
        parkingID = mPreferences.getString("parkingID", "");
        if (!parkingID.equals("")) {
            ParkingInforTask parkingInforTask = new ParkingInforTask(parkingID, "pi", this);
            parkingInforTask.execute();
        }

        // Button Checkin
        buttonCheckin = (Button) findViewById(R.id.buttonCheckin);
        buttonCheckin.setEnabled(false);
        buttonCheckin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager.removeUpdates(Direction.this);
                new NotificationTask("checkin", mPreferences.getString("vehicleID", ""), parkingID, "", Direction.this);
//                Intent checkOutIntent = new Intent(Direction.this, CheckOut.class);
//                startActivity(checkOutIntent);
            }
        });

        // Button Hủy đặt chỗ
        vehicleID = mPreferences.getString("vehicleID", "");
        buttonHuy = (Button) findViewById(R.id.buttonHuy);
        buttonHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FineTask("getall", null, "fine", Direction.this);
            }
        });

        // đặt vị trí nút mylocation
        mMapView = mapFragment.getView();
        View locationButton = ((View) mMapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();

        // position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        rlp.setMargins(0, 1500, 0, 0);

        //tạo dialog
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Direction.this);
        View mView = getLayoutInflater().inflate(R.layout.alert_dialog, null);
        mBuilder.setView(mView);
        dialog = mBuilder.create();
        textViewAlert = mView.findViewById(R.id.textViewAlert);
        btnOK = mView.findViewById(R.id.btnOK);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // move camera đến vị trí của mình
        GPSTracker gps = new GPSTracker(this);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gps.getLatitude(), gps.getLongitude()), 17));

        // setEnabled nút locaiotn
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        // Sự kiện bấm vào nút
        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                userGesture = false;
                return true;
            }
        });

        // Gọi listener OnCameraMoveStartedListener
        mMap.setOnCameraMoveStartedListener(this);
        // Gọi listener LocationChanged
        callLocationChangedListener();

    }

    @Override
    public void onDirectionFinderStart() {
        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline : polylinePaths) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 17));
            ((TextView) findViewById(R.id.tvDuration)).setText(route.duration.text);
            ((TextView) findViewById(R.id.tvDistance)).setText(route.distance.text);

            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(15);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
    }

    @Override
    public void onPostExecute(Object o, String action) {
        if (action.equals("pi")) {
            parkingDTOS = (ArrayList<ParkingDTO>) o;
            ParkingDTO parkingDTO = parkingDTOS.get(0);
            if (parkingDTO != null) {
                textToSpeech(parkingDTO.getAddress());
            }
            //  tạo điểm đến
            distination = new Location("distination");
            distination.setLatitude(parkingDTO.getLatitude());
            distination.setLongitude(parkingDTO.getLongitude());

            GPSTracker gps = new GPSTracker(this);

            try {
                new DirectionFinder(this, gps.getLatitude() + "," + gps.getLongitude(), parkingDTO.getLatitude() + "," + parkingDTO.getLongitude()).execute();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else if (action.equals("fine")) {
            fineDTOS = (ArrayList<FineDTO>) o;
            if (fineDTOS.size() > 0) {
                int countFine = 0;
                for (int i = 0; i < fineDTOS.size(); i++) {
                    if (fineDTOS.get(i).getStatus() == 0 && fineDTOS.get(i).getType() == 0) {
                        countFine++;
                    }
                }
                createDialog(countFine);
            } else {
                createDialog(0);
            }
        } else if (action.equals("cancel")) {
            if (Boolean.TRUE.equals(o)) {
                mPreferencesEditor.clear().commit();
                new DriverLoginTask("second_time", null, "", Direction.this);
                Intent intentHome = new Intent(Direction.this, HomeActivity.class);
                startActivity(intentHome);
                finish();
            } else {
                textViewAlert.setText("Không kết nối được đến máy chủ!");
                dialog.show();
            }
        }
    }

    private void callLocationChangedListener() {
        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0, this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onLocationChanged(Location location) {

        if (!userGesture) {
            Log.e("auto move", location.getBearing()+ " ok");
            cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))             // Sets the center of the map to current location
                    .zoom(17)                   // Sets the zoom
                    .bearing(location.getBearing()) // Sets the orientation of the camera to east
                    .tilt(0)                   // Sets the tilt of the camera to 0 degrees
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }

        try {
            double distanceValue = distination.distanceTo(location);
//            Log.e("khoảng cách", distanceValue + "");
            if (distanceValue <= 500) {
                buttonCheckin.setBackground(getResources().getDrawable(R.drawable.button_selector2));
                buttonCheckin.setEnabled(true);
                if (noti) {
                    createNotification("Fparking");
                    noti = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onCameraMoveStarted(int reason) {
        if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
            userGesture = true;
            Log.e("camera move", "ok");
        } else if (reason == GoogleMap.OnCameraMoveStartedListener
                .REASON_API_ANIMATION) {
        } else if (reason == GoogleMap.OnCameraMoveStartedListener
                .REASON_DEVELOPER_ANIMATION) {
        }
    }

    private void createDialog(final int content) {
        String title;
        if (content == 0) {
            title = "Bạn có muốn hủy đặt chỗ";
        } else {
            title = "Bạn đã hủy đặt chỗ " + content + " lần";
        }
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int choice) {
                switch (choice) {
                    case DialogInterface.BUTTON_POSITIVE:
                        new BookingTask("cancel", vehicleID + "", parkingID + "", "cancel", Direction.this);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:

                        break;
                }
            }
        };
        try {
            builder.setTitle(title)
                    .setMessage("Nếu hủy đặt chỗ quá 3 lần tài khoản bạn sẽ bị khóa.")
                    .setPositiveButton("Đồng ý", dialogClickListener)
                    .setNegativeButton("Hủy", dialogClickListener)
                    .setCancelable(false).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createNotification(String title) {
        Intent intent = new Intent(this, Direction.class);
//        intent.putExtra("NotificationMessage", "order");
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_HIGH);

            // Configure the notification channel.
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

        notificationBuilder.setAutoCancel(true)
//                .setDefaults(Notification.DE)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.icon_noti)
                .setTicker("Hearty365")
                .setPriority(3)
                .setContentTitle(title)
                .setContentIntent(pIntent)
                .setContentText("Xe đã đến điểm đỗ. Vui lòng vào check in!")
                .setContentInfo("Info");
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        notificationManager.notify(/*notification id*/1, notificationBuilder.build());
    }

    protected void textToSpeech(final String address) {
        textToSpeechNearPlace = new TextToSpeech(Direction.this, new TextToSpeech.OnInitListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onInit(int status) {
                textToSpeechNearPlace.setLanguage(Locale.forLanguageTag("vi-VN"));
                textToSpeechNearPlace.speak("Bạn đã đặt chỗ tại bãi xe " + address, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        progressDialog.dismiss();
        startActivity(new Intent(this, OrderParking.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        textToSpeechNearPlace.shutdown();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}

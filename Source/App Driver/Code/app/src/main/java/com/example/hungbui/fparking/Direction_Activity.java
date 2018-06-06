package com.example.hungbui.fparking;

import android.Manifest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
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

import Models.DirectionFinder;
import Models.DirectionFinderListener;
import Models.GPSTracker;
import Models.Route;

public class Direction_Activity extends AppCompatActivity implements OnMapReadyCallback, DirectionFinderListener, LocationListener, GoogleMap.OnCameraMoveStartedListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,  GoogleMap.OnCameraMoveListener {
public class Direction_Activity extends AppCompatActivity implements OnMapReadyCallback, DirectionFinderListener, LocationListener, GoogleMap.OnCameraMoveStartedListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnCameraMoveListener {


    private GoogleMap mMap;
    GoogleApiClient googleApiClient;

    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;

    float degreesBearing = 0.0f;
    CameraPosition moveCameratoLocation;

    Location myLocation = null;

    private static final String TAG = "MyLocationService";
    private static final int LOCATION_INTERVAL = 1000;
    private static final float LOCATION_DISTANCE = 10f;
    private LocationManager mLocationManager = null;
    boolean userGesture = false;
    boolean notificationStatus = false;

    Button btnStopDirection;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction_);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // create and button listener Stop Direction
        btnStopDirection = (Button) findViewById(R.id.stopDirection);
        btnStopDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Direction_Activity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        sendRequest();
    }

    /**
     * excute interface DirectionFinder
     */
    private void sendRequest() {

        GPSTracker gps = new GPSTracker(this);

        String[] destination = get_Position_Parking();
//        Toast.makeText(Direction_Activity.this, positionParking[0] + "----" + positionParking[1], Toast.LENGTH_LONG).show();
        if (destination == null) {
            Toast.makeText(this, "Please enter destination address!", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            // new DirectionFinder(this, +gps.getLatitude() + "," + gps.getLongitude(), destination[0] + "," + destination[1]).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        mMap.getUiSettings().setZoomControlsEnabled(true);
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

        mMap.setOnCameraMoveStartedListener(this);

        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                userGesture = false;
                callChangedLocationListener();
                Log.e(TAG, "Location Button Action: User gestture = " + userGesture);
                return false;
            }
        });

    }

    //get thong tin position tu getArguments
    public String[] get_Position_Parking() {
        pref = getApplicationContext().getSharedPreferences("positionParking", 0);// 0 - là chế độ private
        String postionPakring = pref.getString("positionParking", "null");
        String[] positionParking = postionPakring.substring(postionPakring.indexOf("(") + 1, postionPakring.indexOf(")")).split(",");
        return positionParking;
    }

    /**
     * override interface onDirectionFinderStart
     */
    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "Please wait.",
                "Finding direction..!", true);

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

    /**
     * override interface onDirectionFinderStart
     */
    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
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
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));

        }

    }

    /*
    Khởi tạo Location Manager
     */
    public void callChangedLocationListener() {

        // call listener khi thay đổi vị trí
        mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        mLocationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0, this);

    }

    /*
    Bắt sự kiện Location của người dùng thay đổi
     */
    @Override
    public void onLocationChanged(Location location) {

        degreesBearing = location.getBearing();
//        Toast.makeText(this, "Bearing: " + location.getBearing(),
//                Toast.LENGTH_SHORT).show();
        if (!userGesture) {
            moveCameratoLocation = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))
                    .zoom(18.0f)
                    .bearing(degreesBearing)
                    .tilt(0)
                    .build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(moveCameratoLocation));
        }

        String[] destination = get_Position_Parking();
        Location desLocation = new Location("Điểm đến");

        desLocation.setLatitude(Double.parseDouble(destination[0]));
        desLocation.setLongitude(Double.parseDouble(destination[1]));

        float distance = location.distanceTo(desLocation);
        Toast.makeText(this, "Distance: " + distance,
                Toast.LENGTH_SHORT).show();
        if(distance <=30 && !notificationStatus){
        if (distance <= 15) {
            Intent intent = new Intent(this, HomeActivity.class);
            PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
//
//        // Build notification
//        // Actions are just fake
            Notification noti  = new Notification.Builder(this)
            Notification noti = new Notification.Builder(this)
                    .setContentTitle("New mail from " + "test@gmail.com")
                    .setContentText("Subject").setSmallIcon(R.drawable.android)
                    .setAutoCancel(true)
                    .setContentIntent(pIntent)
                    .addAction(R.drawable.android, "Đồng ý", pIntent)
                    .addAction(R.drawable.android, "Hủy", pIntent).build();
            NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, noti);
        }
    }


    /*
    Bắt sự kiện người dùng bắt đầu di chuyển map
     */

    @Override
    public void onCameraMoveStarted(int reason) {
        if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
            Toast.makeText(this, "The user gestured on the map.",
                    Toast.LENGTH_SHORT).show();
            userGesture = true;
        } else if (reason == GoogleMap.OnCameraMoveStartedListener
                .REASON_API_ANIMATION) {
            Toast.makeText(this, "The user tapped something on the map.",
                    Toast.LENGTH_SHORT).show();
        } else if (reason == GoogleMap.OnCameraMoveStartedListener
                .REASON_DEVELOPER_ANIMATION) {
            Toast.makeText(this, "The app moved the camera.",
                    Toast.LENGTH_SHORT).show();
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
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onCameraMove() {

    }
}

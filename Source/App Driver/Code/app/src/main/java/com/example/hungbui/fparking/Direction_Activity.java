package com.example.hungbui.fparking;

import android.Manifest;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class Direction_Activity extends AppCompatActivity implements OnMapReadyCallback, DirectionFinderListener, LocationListener, GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraMoveStartedListener {


    private GoogleMap mMap;
    private Button btnFindPath;
    private EditText etOrigin;
    private EditText etDestination;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;

    private Handler handler;
    private Runnable runnable;
    float degreesBearing = 0.0f;
    CameraPosition moveCameratoLocation;

    private static final String TAG = "MyLocationService";
    private static final int LOCATION_INTERVAL = 1000;
    private static final float LOCATION_DISTANCE = 10f;
    private LocationManager mLocationManager = null;
    boolean userGesture = false;

    Button btnStopDirection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction_);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        sendRequest();

        // create and button listener Stop Direction
        btnStopDirection = (Button) findViewById(R.id.stopDirection);
        btnStopDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Direction_Activity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

    }

    /**
     * excute interface DirectionFinder
     */
    private void sendRequest() {

        GPSTracker gps = new GPSTracker(this);

        String[] destination = get_Position_Parking();
//        Toast.makeText(Direction_Activity.this, positionParking[0] + "----" + positionParking[1], Toast.LENGTH_LONG).show();

        if (destination == null) {
            Toast.makeText(this, "Please enter origin address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (destination == null) {
            Toast.makeText(this, "Please enter destination address!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            new DirectionFinder(this, +gps.getLatitude() + "," + gps.getLongitude(), destination[0] + "," + destination[1]).execute();
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

        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                userGesture = false;
                callChangedLocationListener();
                Log.e(TAG, "Location Button Action: User gestture = " + userGesture);
                return false;
            }
        });

        callChangedLocationListener();

    }

    //get thong tin position tu getArguments
    public String[] get_Position_Parking() {
        Intent intentPositionParking = getIntent();
        String postionPakring = intentPositionParking.getStringExtra("PositionParking");
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

        // Move camera đến mylocation
//        GPSTracker gps = new GPSTracker(this);
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gps.getLatitude(), gps.getLongitude()), 18.0f));

        //location moving

//        initializeLocationManager();


//        try {
//            mLocationManager.requestLocationUpdates(
//                    LocationManager.PASSIVE_PROVIDER,
//                    LOCATION_INTERVAL,
//                    LOCATION_DISTANCE,
//                    mLocationListeners[0]
//            );
//        } catch (java.lang.SecurityException ex) {
//            Log.i(TAG, "fail to request location update, ignore", ex);
//        } catch (IllegalArgumentException ex) {
//            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
//        }
    }

//    private void initializeLocationManager() {
//        Log.e(TAG, "initializeLocationManager - LOCATION_INTERVAL: " + LOCATION_INTERVAL + " LOCATION_DISTANCE: " + LOCATION_DISTANCE);
//        if (mLocationManager == null) {
//            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
//        }
//    }

    public void callChangedLocationListener() {

        // call listener khi thay đổi vị trí
        mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        mLocationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0, this);

    }

    @Override
    public void onLocationChanged(Location location) {

        degreesBearing = location.getBearing();
        Toast.makeText(this, "Bearing: " + location.getBearing(),
                Toast.LENGTH_SHORT).show();
        moveCameratoLocation = new CameraPosition.Builder()
                .target(new LatLng(location.getLatitude(),location.getLongitude()))
                .zoom(18.0f)
                .bearing(degreesBearing)
                .tilt(0)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(moveCameratoLocation));

    }

    @Override
    public void onCameraMoveStarted(int reason) {
        if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
            Toast.makeText(this, "The user gestured on the map.",
                    Toast.LENGTH_SHORT).show();
            userGesture = true;
//            handleStatus = false;
            handler.removeCallbacks(runnable);
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
    public void onCameraMove() {

    }

//    private class LocationListener implements android.location.LocationListener {
//        Location mLastLocation;
//
//
//        public LocationListener(String provider) {
//            Log.e(TAG, "LocationListener " + provider);
//            mLastLocation = new Location(provider);
//        }
//
//        @Override
//        public void onLocationChanged(final Location location) {
//            Log.e(TAG, "onLocationChanged: " + location);
//
//            handler = new Handler();
//            runnable = new Runnable() {
//                @Override
//                public void run() {
//
//                    if (location.hasBearing()) {
//                        bearing = location.getBearing();
//                    }
////                    Log.e(TAG, "bearing: " + location.hasBearing());
//
//                    mLastLocation.set(location);
////                    mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("My place").icon(BitmapDescriptorFactory.fromResource(R.drawable.my_location_icon)));
//
//                    CameraPosition cameraPosition = new CameraPosition.Builder()
//                            .target(new LatLng(location.getLatitude(), location.getLongitude()))             // Sets the center of the map to current location
//                            .zoom(18.0f)                   // Sets the zoom
//                            .bearing(bearing) // Sets the orientation of the camera to east
//                            .tilt(0)                   // Sets the tilt of the camera to 0 degrees
//                            .build();                   // Creates a CameraPosition from the builder
//                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//
//                    handler.postDelayed(this, 1000);
//                }
//            };
//
//
//            handler.postDelayed(runnable, 1000);
//        }
//
//        @Override
//        public void onProviderDisabled(String provider) {
//            Log.e(TAG, "onProviderDisabled: " + provider);
//        }
//
//        @Override
//        public void onProviderEnabled(String provider) {
//            Log.e(TAG, "onProviderEnabled: " + provider);
//        }
//
//        @Override
//        public void onStatusChanged(String provider, int status, Bundle extras) {
//            Log.e(TAG, "onStatusChanged: " + provider);
//        }
//    }

//    LocationListener[] mLocationListeners = new LocationListener[]{
//            new LocationListener(LocationManager.PASSIVE_PROVIDER)
//    };


}

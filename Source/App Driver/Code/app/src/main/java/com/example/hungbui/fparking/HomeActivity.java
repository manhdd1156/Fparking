package com.example.hungbui.fparking;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Logger;

import Entity.NearParking;
import Entity.ParkingInfor;
import Models.GPSTracker;
import Service.HttpHandler;

public class HomeActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, PlaceSelectionListener, android.location.LocationListener, GoogleMap.OnCameraMoveStartedListener {

    GoogleMap googleMap;
    GoogleApiClient googleApiClient;
    ImageView imageViewMylocation;
    ArrayList<NearParking> nearParkingInfor_List;
    double searchPlaceLat = 0;
    double searchPlaceLng = 0;
    double myLocationLat = 0;
    double myLocationLng = 0;
    int check = 0;
    boolean isBlockMyposition = false;
    GetNearPlace getNearPlace;

    Location myLocation;

    LocationManager mLocationManager;

    View mMapView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mMapView = mapFragment.getView();
        mapFragment.getMapAsync(this);

        getNearPlace = new GetNearPlace();
        // search vi tri
        PlaceAutocompleteFragment mplaceAutocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment_home);
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setCountry("VN") //  This should be a ISO 3166-1 Alpha-2 country code
                .build();
        mplaceAutocompleteFragment.setFilter(typeFilter);
        mplaceAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {

            Marker marker;

            @Override
            public void onPlaceSelected(Place place) {
                MarkerOptions markerOptions = new MarkerOptions();
                if (marker != null) {
                    marker.remove();
                }
                googleMap.clear();

                LatLng latLngMaker = place.getLatLng();
                check = 1;
                String[] lat_lng = getLatLng(latLngMaker.toString());
                searchPlaceLat = Double.parseDouble(lat_lng[0]);
                searchPlaceLng = Double.parseDouble(lat_lng[1]);

                new GetNearPlace().execute();

                for (int i = 0; i < nearParkingInfor_List.size(); i++) {
                    LatLng m = new LatLng(nearParkingInfor_List.get(i).getLatitude(), nearParkingInfor_List.get(i).getLongitude());
                    googleMap.addMarker(new MarkerOptions().position(m).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                }

                markerOptions.position(latLngMaker);

                //marker = googleMap.addMarker(markerOptions);
                //marker.showInfoWindow();
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngMaker, 15));

            }

            @Override
            public void onError(Status status) {

            }
        });
    }

    //add vi tri tren ban do tu data
    @Override
    public void onMapReady(final GoogleMap googleMap) {

        Log.e("on Map Ready", "vl that");
        this.googleMap = googleMap;

        //Click marker vi tri bai xe
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String position = marker.getPosition().toString();
                Intent intent = new Intent(HomeActivity.this, OrderParking.class);
                Bundle bundlePosition = new Bundle();
                bundlePosition.putString("Position", position);
                intent.putExtra("BundlePosition", bundlePosition);
                startActivity(intent);
                return false;
            }
        });

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);

        //googleMap.setPadding(500, 1500, 11, 80);
        View locationButton = ((View) mMapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        // position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        rlp.setMargins(0, 1500, 20, 80);

        googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                check = 1;
                GPSTracker gpsTracker = new GPSTracker(getApplicationContext());
                searchPlaceLat = gpsTracker.getLatitude();
                searchPlaceLng = gpsTracker.getLongitude();
                new GetNearPlace().execute();
                return false;
            }
        });
        googleMap.setOnCameraMoveStartedListener(this);
        callChangedLocationListener();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    //get ra vi tri cua minh
    @Override
    public void onConnected(@Nullable Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onPlaceSelected(Place place) {

    }

    @Override
    public void onError(Status status) {

    }

    // khoi tao connction cho onConnectied()
    private void buildGoogleApiClient() {
        Log.e("Mo connection", "vl that");
        googleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        googleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        //Toast.makeText(HomeActivity.this, "Dang thay doi nay"+ location, Toast.LENGTH_SHORT).show();
        check = 1;
        searchPlaceLat = location.getLatitude();
        searchPlaceLng = location.getLongitude();
        new GetNearPlace().execute();
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
    public void onCameraMoveStarted(int i) {
        LatLng cameraLatLng = googleMap.getCameraPosition().target;
        googleMap.clear();

        String[] lat_lng = getLatLng(cameraLatLng.toString());
        check = 1;
        searchPlaceLat = Double.parseDouble(lat_lng[0]);
        searchPlaceLng = Double.parseDouble(lat_lng[1]);
        new GetNearPlace().execute();
    }

    public void callChangedLocationListener() {

        // call listener khi thay đổi vị trí
        mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        mLocationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0, this);

    }

    private class GetNearPlace extends AsyncTask<Void, Void, Void> {

        private String jSonStr;

        @Override
        protected Void doInBackground(Void... voids) {
            Log.e("Bat dau doinbackground", "vl that");

            isBlockMyposition = true;
            if (check == 0) {
                Intent intent = getIntent();
                double[] locaton = intent.getDoubleArrayExtra("Location");

                myLocationLat = locaton[0];
                myLocationLng = locaton[1];
            } else {
                myLocationLat = searchPlaceLat;
                myLocationLng = searchPlaceLng;
            }

            HttpHandler httpHandler = new HttpHandler();

            jSonStr = httpHandler.makeServiceCall("https://fparking.net/realtimeTest/driver/get_near_my_location.php?latitude=" + myLocationLat + "&" + "longitude=" + myLocationLng);
            Log.e("SQL", "https://fparking.net/realtimeTest/driver/get_near_my_location.php?latitude=" + myLocationLat + "&" + "longitude=" + myLocationLng);

            if (jSonStr != null) {
                try {
                    nearParkingInfor_List = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(jSonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObject.getJSONArray("near_location");
                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        double longitude = Double.parseDouble(c.getString("longitude"));
                        double latitude = Double.parseDouble(c.getString("latitude"));

                        nearParkingInfor_List.add(new NearParking(latitude, longitude));
                    }
                } catch (final JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            Toast.makeText(getApplicationContext(),
//                                    "Json parsing error: " + e.getMessage(),
//                                    Toast.LENGTH_LONG)
//                                    .show();
                        }
                    });
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(getApplicationContext(),
//                                "Couldn't get json from server. Check LogCat for possible errors!",
//                                Toast.LENGTH_LONG)
//                                .show();
                    }
                });
            }

            Log.e("Bat dau doinbackground", " end execute");

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.e("Bat dau doinbackground", " Poset start execute");
            super.onPostExecute(aVoid);
            for (int i = 0; i < nearParkingInfor_List.size(); i++) {
                LatLng m = new LatLng(nearParkingInfor_List.get(i).getLatitude(), nearParkingInfor_List.get(i).getLongitude());
                System.out.println(nearParkingInfor_List.get(i).getLatitude() + "----" + nearParkingInfor_List.get(i).getLongitude());
                googleMap.addMarker(new MarkerOptions().position(m).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            }
            Log.e("Bat dau doinbackground", " Poset end execute");
            isBlockMyposition = false;
        }

    }

    //get thong tin position tu getArguments
    public String[] getLatLng(String location) {
        String[] lat_lng = location.substring(location.indexOf("(") + 1, location.indexOf(")")).split(",");
        return lat_lng;
    }
}

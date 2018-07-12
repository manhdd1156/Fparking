package com.example.hung.fparking;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.speech.RecognizerIntent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.hung.fparking.asynctask.IAsyncTaskHandler;
import com.example.hung.fparking.asynctask.ParkingTask;
import com.example.hung.fparking.entity.GetNearPlace;
import com.example.hung.fparking.model.GPSTracker;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
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


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback, IAsyncTaskHandler, GoogleMap.OnCameraMoveStartedListener, LocationListener {

    private GoogleMap mMap;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mPreferencesEditor;
    private PlaceAutocompleteFragment placeAutocompleteFragment;
    private LocationManager locationManager = null;

    View mMapView;
    ImageView imageViewVoiceSearch, imageViewMute;

    ArrayList<GetNearPlace> nearParkingList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // tạo SharedPreferences
        mPreferences = getSharedPreferences("driver", 0);
        mPreferencesEditor = mPreferences.edit();

        // ánh xạ button
        imageViewVoiceSearch = findViewById(R.id.imageView_search_voice);
        imageViewMute = findViewById(R.id.imageViewMute);

        // gọi hàm search theo địa chỉ
        searchPlace();

        // chỉnh vị trí nút mylocation
        mMapView = mapFragment.getView();
        View locationButton = ((View) mMapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        // position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        rlp.setMargins(0, 1500, 0, 0);

        // event button
        imageViewVoiceSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onStopSpeech();
                speechToText();
            }
        });
//        imageViewMute.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onStopSpeech();
//            }
//        });

        //Menu
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(navigationItemSelectedListener);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    NavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            mDrawerLayout.closeDrawer(GravityCompat.START);

            Fragment fragment = null;
            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.nav_history:
                    Intent intent = new Intent(HomeActivity.this, Direction.class);
                    startActivity(intent);
                    break;
                case R.id.nav_mCar:
                    Intent intent1 = new Intent(HomeActivity.this, CheckOut.class);
                    startActivity(intent1);
                    break;
            }
            return true;
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.setChecked(false);
        if (mToggle.onOptionsItemSelected(item)) {
            item.setChecked(false);
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        // hiển thị nút vị trí của mình
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);

        // di chuyển camera đến vị trí của mình
        GPSTracker gpsTracker = new GPSTracker(getApplicationContext());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude()), 15));

        // Gọi Listener của movecamera
        mMap.setOnCameraMoveStartedListener(this);
        callLocationChangedListener();

        // event click nút mylocation
        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                callLocationChangedListener();
                GPSTracker gpsTracker = new GPSTracker(getApplicationContext());
                double myLatitude = gpsTracker.getLatitude();
                double myLongitude = gpsTracker.getLongitude();
                doSearchAsyncTask(myLatitude, myLongitude);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLatitude, myLongitude), 15));
                return false;
            }
        });

        // move camera đến bãi đỗ xe mình đỗ nếu đã đặt chỗ
        if (!mPreferences.getString("parkingLat", "").equals("") && !mPreferences.getString("parkingLng", "").equals("")) {
            Double lt = Double.parseDouble(mPreferences.getString("parkingLat", ""));
            Double ln = Double.parseDouble(mPreferences.getString("parkingLng", ""));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lt, ln), 15));
        } else {
            double myLatitude = gpsTracker.getLatitude();
            double myLongitude = gpsTracker.getLongitude();
            doSearchAsyncTask(myLatitude, myLongitude);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLatitude, myLongitude), 15));
        }

        // event click khi chọn marker
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
//                onStopSpeech();
                String parkingLocation = marker.getPosition().toString();
                String[] latlng = getLat_lng(parkingLocation);
                String lat = latlng[0];
                String lng = latlng[1];
                if (mPreferences.getString("bookingID", "").equals("")) {
                    Intent intentOrderFlagment = new Intent(HomeActivity.this, OrderParking.class);
                    intentOrderFlagment.putExtra("ParkingLocation", parkingLocation);
                    startActivity(intentOrderFlagment);
                } else if (mPreferences.getString("parkingLat", "").equals(lat) && mPreferences.getString("parkingLng", "").equals(lng)) {
                    if (mPreferences.getString("action", "").equals("2")) {
                        Intent intentOrderFlagment = new Intent(HomeActivity.this, CheckOut.class);
                        intentOrderFlagment.putExtra("ParkingLocation", parkingLocation);
                        startActivity(intentOrderFlagment);
                    } else {
                        Intent intentOrderFlagment = new Intent(HomeActivity.this, OrderParking.class);
                        intentOrderFlagment.putExtra("ParkingLocation", parkingLocation);
                        startActivity(intentOrderFlagment);
                    }

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int choice) {
                            switch (choice) {
                                case DialogInterface.BUTTON_POSITIVE:


                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:

                                    break;
                            }
                        }
                    };
                    try {
                        builder.setMessage("Bạn đang đỗ xe tại nơi khác!").show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });


    }

    public void searchPlace() {
        placeAutocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment_home);
        AutocompleteFilter autocompleteFilter = new AutocompleteFilter.Builder().setCountry("VN").build();


        placeAutocompleteFragment.setFilter(autocompleteFilter);
        placeAutocompleteFragment.setHint("Nhập địa chỉ tìm kiếm");

        placeAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            Marker marker;

            @Override
            public void onPlaceSelected(Place place) {
                locationManager.removeUpdates(HomeActivity.this);
                if (marker != null) {
                    marker.remove();
                }
                mMap.clear();
                MarkerOptions markerOptions = new MarkerOptions();
                LatLng latLngMaker = place.getLatLng();

                markerOptions.position(latLngMaker);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngMaker, 15));

                double lat = latLngMaker.latitude;
                double lng = latLngMaker.longitude;

                doSearchAsyncTask(lat, lng);
            }

            @Override
            public void onError(Status status) {
            }
        });
    }

    public String[] getLat_lng(String location) {
        String[] latlng = location.substring(location.indexOf("(") + 1, location.indexOf(")")).split(",");
        return latlng;
    }

    private void doSearchAsyncTask(double lat, double lng) {
        ParkingTask parkingTask = new ParkingTask(lat, lng, this);
        parkingTask.execute();
    }

    @Override
    public void onPostExecute(Object o) {
        nearParkingList = (ArrayList<GetNearPlace>) o;
        int height = 90;
        int width = 90;
        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.parking_icon);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

        BitmapDrawable bitmapPark = (BitmapDrawable) getResources().getDrawable(R.drawable.parking_icon_green);
        Bitmap p = bitmapPark.getBitmap();
        Bitmap parkMarker = Bitmap.createScaledBitmap(p, width, height, false);

        if (nearParkingList.size() > 0) {
            for (int i = 0; i < nearParkingList.size(); i++) {
                LatLng latLng = new LatLng(nearParkingList.get(i).getLattitude(), nearParkingList.get(i).getLongitude());

                mMap.addMarker(new MarkerOptions()
                        .position(latLng).icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
            }

        }

    }

    @Override
    public void onCameraMoveStarted(int reason) {
        if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
            locationManager.removeUpdates(HomeActivity.this);
            LatLng cameraLatLng = mMap.getCameraPosition().target;
            mMap.clear();

            double lat = cameraLatLng.latitude;
            double lng = cameraLatLng.longitude;

            doSearchAsyncTask(lat, lng);

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
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        doSearchAsyncTask(lat, lng);
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

    protected void speechToText() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "vi-VN");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Nói gì đó đi");


        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(HomeActivity.this, "Khong ho tro", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> str = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    placeAutocompleteFragment.setText(str.get(0).toString());
                    getLocationFromVoiceSearch(str.get(0).toString());
                }
                break;
        }
    }

    protected void getLocationFromVoiceSearch(String location) {
        Geocoder geocoder = new Geocoder(HomeActivity.this);
        List<Address> addressList = new ArrayList<>();
        try {
            addressList = geocoder.getFromLocationName(location, 1);
        } catch (IOException e) {
            Log.e("getLocationFromVoiceSearch:", e.getMessage());
        }

        if (addressList.size() > 0) {
            Address address = addressList.get(0);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(address.getLatitude(), address.getLongitude()), 15));
            locationManager.removeUpdates(HomeActivity.this);
            double voiceLatitude = address.getLatitude();
            double voiceLongitude = address.getLongitude();
            doSearchAsyncTask(voiceLatitude, voiceLongitude);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        startActivity(getIntent());
    }
}


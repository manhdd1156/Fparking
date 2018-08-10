package com.example.hung.fparking;

import android.Manifest;
import android.content.Context;
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
import android.os.Build;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hung.fparking.dto.ParkingDTO;
import com.example.hung.fparking.login.MainActivity;
import com.example.hung.fparking.other.Contact;
import com.example.hung.fparking.asynctask.IAsyncTaskHandler;
import com.example.hung.fparking.asynctask.ParkingTask;
import com.example.hung.fparking.config.Session;
import com.example.hung.fparking.entity.GetNearPlace;
import com.example.hung.fparking.model.GPSTracker;
import com.example.hung.fparking.other.Feedback;
import com.example.hung.fparking.other.TermsAndConditions;
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
import java.util.Locale;

public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback, IAsyncTaskHandler, GoogleMap.OnCameraMoveStartedListener, LocationListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mPreferencesEditor;
    private PlaceAutocompleteFragment placeAutocompleteFragment;
    private LocationManager locationManager = null;
    private TextToSpeech textToSpeechNearPlace;

    NavigationView navigationView;
    View headerView;
    View mMapView;
    ImageView imageViewVoiceSearch, imageViewMute, imageViewFParking;
    TextView textViewMPhone;
    Button quickBooking;

    ArrayList<GetNearPlace> nearParkingList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    private Toolbar mToolbar;
    boolean doubleBackToExitPressedOnce = false;

    AlertDialog dialog, notiDialog;
    Button buttonOK, buttonCancel, btnOK;
    TextView textViewAddressQB, textViewTotalTimeQB, textViewPriceQB, textViewAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // dialog đặt chỗ nhanh
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(HomeActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.quick_booking_layout, null);
        mBuilder.setView(mView);
        dialog = mBuilder.create();

        setDialogProperties(mView);

        //dialog thông báo
        AlertDialog.Builder mNotiBuilder = new AlertDialog.Builder(HomeActivity.this);
        View mNotiView = getLayoutInflater().inflate(R.layout.alert_dialog, null);
        mNotiBuilder.setView(mNotiView);
        notiDialog = mNotiBuilder.create();
        textViewAlert = mNotiView.findViewById(R.id.textViewAlert);
        btnOK = mNotiView.findViewById(R.id.btnOK);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notiDialog.cancel();
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // tạo SharedPreferences
        mPreferences = getSharedPreferences("driver", 0);
        mPreferencesEditor = mPreferences.edit();
        // ánh xạ button
        imageViewVoiceSearch = findViewById(R.id.imageView_search_voice);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        headerView = navigationView.getHeaderView(0);
        textViewMPhone = headerView.findViewById(R.id.textViewMPhone);
        textViewMPhone.setText(Session.currentDriver.getPhone());
        quickBooking = findViewById(R.id.buttonFastSearch);

        // sự kiện click vào số điện thoại
        textViewMPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDriverInfo = new Intent(HomeActivity.this, DriverInformation.class);
                startActivity(intentDriverInfo);
            }
        });

        // sự kiện click vào profile
        imageViewFParking = headerView.findViewById(R.id.imageViewFParking);
        imageViewFParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDriverInfo = new Intent(HomeActivity.this, DriverInformation.class);
                startActivity(intentDriverInfo);
            }
        });

        // sự kiện click vào đặt chỗ nhanh
        quickBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng cameraLatLng = mMap.getCameraPosition().target;
                double lat = cameraLatLng.latitude;
                double lng = cameraLatLng.longitude;
                mPreferencesEditor.putFloat("quicklat", (float) lat);
                mPreferencesEditor.putFloat("quicklng", (float) lng);
                mPreferencesEditor.commit();
                new ParkingTask("order", lat, lng, "order", HomeActivity.this);
            }
        });

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

        // event button giọng nói
        imageViewVoiceSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onStopSpeech();
                speechToText();
            }
        });

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

        // check data
        int status = mPreferences.getInt("status", 8);
        if (Session.currentDriver.getStatus().equals("0")) {
            textViewAlert.setText("Tài khoản bạn đang bị khóa!");
            notiDialog.show();
        } else if (status == 1) {
            if (locationManager != null) {
                locationManager.removeUpdates(HomeActivity.this);
            }
            Intent intentOrderFlagment = new Intent(HomeActivity.this, OrderParking.class);
            startActivity(intentOrderFlagment);
            finish();
        } else if (status == 2) {
            if (locationManager != null) {
                locationManager.removeUpdates(HomeActivity.this);
            }
            Intent intentCheckoutFlagment = new Intent(HomeActivity.this, CheckOut.class);
            startActivity(intentCheckoutFlagment);
            finish();
        } else if (status == 3) {
            if (locationManager != null) {
                locationManager.removeUpdates(HomeActivity.this);
            }
            Intent intentCheckoutFlagment = new Intent(HomeActivity.this, CheckOut.class);
            startActivity(intentCheckoutFlagment);
            finish();
        }
    }

    private void setDialogProperties(View dialogView) {
        textViewAddressQB = dialogView.findViewById(R.id.textViewAddressQB);
        textViewTotalTimeQB = dialogView.findViewById(R.id.textViewTotalTimeQB);
        textViewPriceQB = dialogView.findViewById(R.id.textViewPriceQB);
        buttonOK = dialogView.findViewById(R.id.btnOKQB);
        buttonCancel = dialogView.findViewById(R.id.btnCancelQB);

        // sự kiện nút cancel dialog
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

    }

    NavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            mDrawerLayout.closeDrawer(GravityCompat.START);

            Fragment fragment = null;
            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.nav_history:
                    if (locationManager != null) {
                        locationManager.removeUpdates(HomeActivity.this);
                    }
                    Intent intentParkingHistory = new Intent(HomeActivity.this, ParkingHistory.class);
                    startActivity(intentParkingHistory);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
                case R.id.nav_mCar:
                    if (locationManager != null) {
                        locationManager.removeUpdates(HomeActivity.this);
                    }
                    Intent intentCarsList = new Intent(HomeActivity.this, CarsList.class);
                    startActivity(intentCarsList);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
                case R.id.nav_contact:
                    if (locationManager != null) {
                        locationManager.removeUpdates(HomeActivity.this);
                    }
                    Intent intentContact = new Intent(HomeActivity.this, Contact.class);
                    startActivity(intentContact);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
                case R.id.nav_fine_history:
                    if (locationManager != null) {
                        locationManager.removeUpdates(HomeActivity.this);
                    }
                    Intent intentFineHistory = new Intent(HomeActivity.this, FineHistory.class);
                    startActivity(intentFineHistory);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
                case R.id.nav_feedback:
                    if (locationManager != null) {
                        locationManager.removeUpdates(HomeActivity.this);
                    }
                    Intent intentFeedback = new Intent(HomeActivity.this, Feedback.class);
                    startActivity(intentFeedback);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
                case R.id.nav_DK:
                    if (locationManager != null) {
                        locationManager.removeUpdates(HomeActivity.this);
                    }
                    Intent intentDK = new Intent(HomeActivity.this, TermsAndConditions.class);
                    startActivity(intentDK);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    break;
                case R.id.nav_logout:
                    if (locationManager != null) {
                        locationManager.removeUpdates(HomeActivity.this);
                    }
                    Intent intentLogout = new Intent(HomeActivity.this, MainActivity.class);
                    startActivity(intentLogout);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    Session.spref = getSharedPreferences("intro", 0);
                    mPreferencesEditor.clear().commit();
                    Session.spref.edit().clear().commit();
                    finish();
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
        mMap.setOnMarkerClickListener(this);
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
                if (locationManager != null) {
                    locationManager.removeUpdates(HomeActivity.this);
                }
                if (marker != null) {
                    marker.remove();
                }
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
        new ParkingTask("list", lat, lng, "list", this);
    }

    @Override
    public void onPostExecute(Object o, String s) {
        if (s.equals("list")) {
            nearParkingList = (ArrayList<GetNearPlace>) o;
            int height = 150;
            int width = 150;
            BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.parking_icon);
            Bitmap b = bitmapdraw.getBitmap();
            Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

            BitmapDrawable bitmapPark = (BitmapDrawable) getResources().getDrawable(R.drawable.parking_icon_green);
            Bitmap p = bitmapPark.getBitmap();
            Bitmap parkMarker = Bitmap.createScaledBitmap(p, width, height, false);

            if (nearParkingList.size() > 0) {
                mMap.clear();
                for (int i = 0; i < nearParkingList.size(); i++) {
                    LatLng latLng = new LatLng(nearParkingList.get(i).getLattitude(), nearParkingList.get(i).getLongitude());

                    mMap.addMarker(new MarkerOptions()
                            .position(latLng).icon(BitmapDescriptorFactory.fromBitmap(smallMarker)).title(nearParkingList.get(i).getId() + ""));
                }
            }
        } else if (s.equals("order")) {
            ArrayList<ParkingDTO> parkingDTOS;
            parkingDTOS = (ArrayList<ParkingDTO>) o;
            if (parkingDTOS.size() > 0) {
                dialog.show();
                textViewAddressQB.setText(parkingDTOS.get(0).getAddress());
                textViewTotalTimeQB.setText(parkingDTOS.get(0).getTimeoc());
                textViewPriceQB.setText(parkingDTOS.get(0).getTotalspace() - parkingDTOS.get(0).getCurrentspace() + "");
                final String parkingID = parkingDTOS.get(0).getParkingID() + "";

                buttonOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mPreferences.getInt("status", 8) == 8) {
                            mPreferencesEditor.putString("parkingID", parkingID);
                            mPreferencesEditor.commit();
                            if (locationManager != null) {
                                locationManager.removeUpdates(HomeActivity.this);
                            }
                            Intent intentOrderFlagment = new Intent(HomeActivity.this, OrderParking.class);
                            startActivity(intentOrderFlagment);
                            finish();
                        }
                    }
                });
            } else {
// thông báo không tìm thấy bãi xe nào
            }
        }
    }

    @Override
    public void onCameraMoveStarted(int reason) {
        if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
            if (locationManager != null) {
                locationManager.removeUpdates(HomeActivity.this);
            }
            LatLng cameraLatLng = mMap.getCameraPosition().target;

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
            locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 10000, 1000, this);
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

    protected void textToSpeech() {
        textToSpeechNearPlace = new TextToSpeech(HomeActivity.this, new TextToSpeech.OnInitListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onInit(int status) {
                textToSpeechNearPlace.setLanguage(Locale.forLanguageTag("vi-VN"));
                textToSpeechNearPlace.speak("Địa chỉ không hợp lệ", TextToSpeech.QUEUE_FLUSH, null);
            }
        });
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
            Log.e("List location", addressList + "");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addressList.size() > 0) {
            Address address = addressList.get(0);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(address.getLatitude(), address.getLongitude()), 15));
            if (locationManager != null) {
                locationManager.removeUpdates(HomeActivity.this);
            }
            double voiceLatitude = address.getLatitude();
            double voiceLongitude = address.getLongitude();
            doSearchAsyncTask(voiceLatitude, voiceLongitude);
        } else {
            textToSpeech();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.hideInfoWindow();
        if (Session.currentDriver.getStatus().equals("1")) {
            if (mPreferences.getInt("status", 8) == 8) {
                mPreferencesEditor.putString("parkingID", marker.getTitle().toString());
                LatLng cameraLatLng = mMap.getCameraPosition().target;
                double lat = cameraLatLng.latitude;
                double lng = cameraLatLng.longitude;
                mPreferencesEditor.putFloat("quicklat", (float) lat);
                mPreferencesEditor.putFloat("quicklng", (float) lng);
                mPreferencesEditor.commit();
                if (locationManager != null) {
                    locationManager.removeUpdates(HomeActivity.this);
                }
                Intent intentOrderFlagment = new Intent(HomeActivity.this, OrderParking.class);
                startActivity(intentOrderFlagment);
            }
        } else {
            textViewAlert.setText("Tài khoản bạn đang bị khóa!");
            notiDialog.show();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Ấn một lần nữa để đóng ứng dụng", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}



package com.example.hung.fparkingowner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Build;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.hung.fparkingowner.model.GPSTracker;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddParkingLocation extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private PlaceAutocompleteFragment placeAutocompleteFragment;
    Bitmap smallMarker;
    private LocationManager locationManager = null;
    View mMapView;
    ImageView imageViewVoiceSearch;
    private TextToSpeech textToSpeechNearPlace;

    Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_parking_location);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // chỉnh vị trí nút mylocation
        mMapView = mapFragment.getView();
        View locationButton = ((View) mMapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        // position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        rlp.setMargins(0, 500, 0, 0);

        setProperties();

        // event button giọng nói
        imageViewVoiceSearch = findViewById(R.id.imageView_search_voice);
        imageViewVoiceSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onStopSpeech();
                speechToText();
            }
        });

        // gọi hàm search theo địa chỉ
        searchPlace();
    }

    private void setProperties() {
        // tạo marker
        int height = 150;
        int width = 150;
        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.parking_icon);
        Bitmap b = bitmapdraw.getBitmap();
        smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

        btnUpdate = findViewById(R.id.buttonAddLocation);

        //sự kiện ấn vào nút thêm bãi
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng cameraLatLng = mMap.getCameraPosition().target;

                double lat = cameraLatLng.latitude;
                double lng = cameraLatLng.longitude;
                Intent i = new Intent(AddParkingLocation.this,AddParkingInformation.class);
                i.putExtra("latitude",lat);
                i.putExtra("longitude",lng);
                startActivity(i);

                finish();
                Log.e("Tọa độ: ", "Lat: " + lat + " Lng: " + lng);
            }
        });
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
        final GPSTracker gpsTracker = new GPSTracker(getApplicationContext());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude()), 15));
        mMap.addMarker(new MarkerOptions().position(new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude()))
                .title("Your Title")
                .snippet("Please move the marker if needed.")
                .draggable(true));

        // event click nút mylocation
        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
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
                if (marker != null) {
                    marker.remove();
                }
                MarkerOptions markerOptions = new MarkerOptions();
                LatLng latLngMaker = place.getLatLng();

                markerOptions.position(latLngMaker);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngMaker, 15));
            }

            @Override
            public void onError(Status status) {
            }
        });
    }

    protected void speechToText() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "vi-VN");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Nói gì đó đi");


        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(AddParkingLocation.this, "Khong ho tro", Toast.LENGTH_LONG).show();
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
        Geocoder geocoder = new Geocoder(AddParkingLocation.this);
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
        } else {
            textToSpeech();
        }
    }

    protected void textToSpeech() {
        textToSpeechNearPlace = new TextToSpeech(AddParkingLocation.this, new TextToSpeech.OnInitListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onInit(int status) {
                textToSpeechNearPlace.setLanguage(Locale.forLanguageTag("vi-VN"));
                textToSpeechNearPlace.speak("Địa chỉ không hợp lệ", TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }
}

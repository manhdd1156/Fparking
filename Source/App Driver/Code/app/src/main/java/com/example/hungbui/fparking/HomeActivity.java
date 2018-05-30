package com.example.hungbui.fparking;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
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

public class HomeActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, PlaceSelectionListener {

    GoogleMap googleMap;
    GoogleApiClient googleApiClient;
    ImageView imageViewMylocation;

    TextView textViewSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


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
                LatLng latLngMaker = place.getLatLng();
                String placeID = place.getId().toString();
                Toast.makeText(HomeActivity.this, placeID, Toast.LENGTH_LONG).show();
                markerOptions.position(latLngMaker);
                markerOptions.title(place.getName().toString());

                marker = googleMap.addMarker(markerOptions);
                marker.showInfoWindow();
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngMaker, 17));
            }

            @Override
            public void onError(Status status) {

            }
        });

        //quay ve my location
        imageViewMylocation = findViewById(R.id.imageViewMyLocation);
        imageViewMylocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildGoogleApiClient();
            }
        });

    }

    //add vi tri tren ban do tu data
    @Override
    public void onMapReady(GoogleMap googleMap) {
        buildGoogleApiClient();

        LatLng m1 = new LatLng(21.013162, 105.524942);
        LatLng m2 = new LatLng(21.013948, 105.525494);
        LatLng m3 = new LatLng(21.013357, 105.526186);
        LatLng m4 = new LatLng(21.013470, 105.527280);
        //add nhieu vi tri
        googleMap.addMarker(new MarkerOptions().position(m1).title("Nhà ăn DH FPT").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        googleMap.addMarker(new MarkerOptions().position(m2).title("THPT FPT").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        googleMap.addMarker(new MarkerOptions().position(m3).title("Ally Cafe FPT").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        googleMap.addMarker(new MarkerOptions().position(m4).title("Đại học FPT").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));


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
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    //get ra vi tri cua minh
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        @SuppressLint("MissingPermission") Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
        // googleMap.addMarker(new MarkerOptions().position(myLocation).title("My Place").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        googleMap.addMarker(new MarkerOptions().position(myLocation).title("My place").icon(BitmapDescriptorFactory.fromResource(R.drawable.my_location_icon)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 17));

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
        googleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        googleApiClient.connect();
    }
}

package com.example.hung.fparkingowner;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hung.fparkingowner.R;
import com.example.hung.fparkingowner.asynctask.GetCityTask;
import com.example.hung.fparkingowner.asynctask.IAsyncTaskHandler;
import com.example.hung.fparkingowner.asynctask.ManagerParkingTask;
import com.example.hung.fparkingowner.config.Constants;
import com.example.hung.fparkingowner.config.Session;
import com.example.hung.fparkingowner.dto.CityDTO;
import com.example.hung.fparkingowner.dto.ParkingDTO;
import com.example.hung.fparkingowner.login.LoginActivity;
import com.example.hung.fparkingowner.model.CheckNetwork;
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
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddParkingInformation extends AppCompatActivity implements OnMapReadyCallback, IAsyncTaskHandler {

    String[] PARKINGLIST = {};
    EditText tbAddressAddParking, tbOpenHourAddParking, tbOpenMinAddParking, tbCloseHourAddParking, tbCloseMinAddParking, tbSpace, tbPrice9AddParking, tbPrice16to29AddParking, tbPrice34to45AddParking;
ScrollView scrollviewAddparking;
    private PlaceAutocompleteFragment placeAutocompleteFragment;
    ImageView backToHome;
    ArrayList<CityDTO> cityDTOS;
    TextView error;
    private GoogleMap mMap;
    View mMapView;

    String cityID;
    long latitude, longitude;
    Button btnAddParking, btnOK;
    Bitmap smallMarker;
    MaterialBetterSpinner betterSpinner;
    private TextToSpeech textToSpeechNearPlace;
    ImageView imageViewVoiceSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_add_parking);
        registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        cityID = "";
        scrollviewAddparking = findViewById(R.id.scrollviewAddparking);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // chỉnh vị trí nút mylocation
        mMapView = mapFragment.getView();
        View locationButton = ((View) mMapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();

        backToHome = (ImageView) findViewById(R.id.imageViewBackToHome);
        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        rlp.setMargins(0, 500, 0, 0);

        imageViewVoiceSearch = findViewById(R.id.imageView_search_voiceAP);
        imageViewVoiceSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onStopSpeech();
                speechToText();
            }
        });

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, PARKINGLIST);
        betterSpinner = (MaterialBetterSpinner) findViewById(R.id.dropdownCityAP);
        betterSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < cityDTOS.size(); i++) {
                    if (PARKINGLIST[position].equals(cityDTOS.get(i).getCityName())) {
                        cityID = cityDTOS.get(i).getCityID() + "";
                    }
                }
                Log.e("CityID: ", cityID + "");
            }
        });

        GetCityTask getCityTask = new GetCityTask(AddParkingInformation.this);
        getCityTask.execute();
        // gọi hàm search theo địa chỉ
        searchPlace();
        setProperties();

    }
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            CheckNetwork checkNetwork = new CheckNetwork(AddParkingInformation.this, getApplicationContext());
            if (!checkNetwork.isNetworkConnected()) {
                checkNetwork.createDialog();
            } else {
//                recreate();
            }
        }
    };
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    private void setProperties() {
        tbAddressAddParking = findViewById(R.id.tbAddressAP);
        tbOpenHourAddParking = findViewById(R.id.tbOpenTimeHourAP);
        tbOpenMinAddParking = findViewById(R.id.tbOpenTimeMinAP);
        tbCloseHourAddParking = findViewById(R.id.tbCloseTimeHourAP);
        tbCloseMinAddParking = findViewById(R.id.tbCloseTimeMinAP);

        tbSpace = findViewById(R.id.tbTotalSpaceAP);
        tbPrice9AddParking = findViewById(R.id.tbPrice9AddParking2);
        tbPrice16to29AddParking = findViewById(R.id.tbPrice16to29AddParking2);
        tbPrice34to45AddParking = findViewById(R.id.tbPrice34to45AddParking2);

        btnAddParking = (Button) findViewById(R.id.btnAddParking);

        // tạo marker
        int height = 150;
        int width = 150;
        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.parking_icon);
        Bitmap b = bitmapdraw.getBitmap();
        smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

//        btnAddParking = findViewById(R.id.buttonAddLocation);

//        //sự kiện ấn vào nút thêm bãi
//        btnAddParking.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LatLng cameraLatLng = mMap.getCameraPosition().target;
//
//                double lat = cameraLatLng.latitude;
//                double lng = cameraLatLng.longitude;
//                Intent i = new Intent(AddParkingInformation.this,AddParkingInformation.class);
//                i.putExtra("latitude",lat);
//                i.putExtra("longitude",lng);
//                startActivity(i);
//
//                finish();
//                Log.e("Tọa độ: ", "Lat: " + lat + " Lng: " + lng);
//            }
//        });


        tbAddressAddParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchPlace();
            }
        });
        btnAddParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidate()) {
                    LatLng cameraLatLng = mMap.getCameraPosition().target;

                    double lat = cameraLatLng.latitude;
                    double lng = cameraLatLng.longitude;
                    double price9 = 0;
                    double price1629 = 0;
                    double price3445 = 0;
                    if (!tbPrice9AddParking.getText().toString().isEmpty()) {
                        price9 = Double.parseDouble(tbPrice9AddParking.getText().toString());
                    }
                    if (!tbPrice16to29AddParking.getText().toString().isEmpty()) {
                        price1629 = Double.parseDouble(tbPrice16to29AddParking.getText().toString());
                    }
                    if (!tbPrice34to45AddParking.getText().toString().isEmpty()) {
                        price3445 = Double.parseDouble(tbPrice34to45AddParking.getText().toString());
                    }

                    String openhour = tbOpenHourAddParking.getText().toString();
                    if (openhour.length() < 2) {
                        openhour = "0" + openhour;
                    }
                    String openmin = tbOpenMinAddParking.getText().toString();
                    if (openmin.length() < 2) {
                        openmin = "0" + openhour;
                    }
                    String closehour = tbCloseHourAddParking.getText().toString();
                    if (closehour.length() < 2) {
                        closehour = "0" + closehour;
                    }
                    String closemin = tbCloseMinAddParking.getText().toString();
                    if (closemin.length() < 2) {
                        closemin = "0" + closemin;
                    }
                    String timeoc = openhour + ":" + openmin + "-" + closehour + ":" + closemin + "h";
                    ParkingDTO parkingDTO = new ParkingDTO(0, tbAddressAddParking.getText().toString(), 0, 0, "", lat + "", lng + "", 3, timeoc, Integer.parseInt(tbSpace.getText().toString()), Integer.parseInt(cityID), price9, price1629, price3445);
                    new ManagerParkingTask("add", parkingDTO, null, new IAsyncTaskHandler() {
                        @Override
                        public void onPostExecute(Object o) {
                            if ((boolean) o) {
                                showDialog("Thêm mới thành công, yêu cầu của bạn sẽ được xử lý trong 24h", 1);
                            } else {
                                showDialog("Thêm không thành công", 0);
                            }
                        }
                    });
                }
            }
        });
    }
    private boolean checkValidate() {
//        String pattern = "\\d";
//        Pattern p = Pattern.compile(pattern);

//        Matcher m = p.matcher(tbOpenTimeAddParking.getText().toString());
        if (tbAddressAddParking.getText().toString().isEmpty() || tbAddressAddParking.getText().toString().equals("")) {
            showDialog("Hãy nhập địa chỉ", 0);
        } else if (tbOpenHourAddParking.getText().toString().isEmpty() || tbOpenHourAddParking.getText().toString().equals("")
                || tbOpenMinAddParking.getText().toString().isEmpty() || tbOpenMinAddParking.getText().toString().equals("")
                || tbCloseHourAddParking.getText().toString().isEmpty() || tbCloseHourAddParking.getText().toString().equals("")
                || tbCloseMinAddParking.getText().toString().isEmpty() || tbCloseMinAddParking.getText().toString().equals("")) {
            showDialog("Hãy nhập giờ mở - đóng cửa", 0);
        } else if (tbSpace.getText().toString().isEmpty() || tbSpace.getText().toString().equals("")) {
            showDialog("Hãy nhập số chỗ", 0);
        } else if ((tbPrice9AddParking.getText().toString().isEmpty() || tbPrice9AddParking.getText().toString().equals("")) && (tbPrice16to29AddParking.getText().toString().isEmpty() || tbPrice16to29AddParking.getText().toString().equals("")) && (tbPrice34to45AddParking.getText().toString().isEmpty() || tbPrice34to45AddParking.getText().toString().equals(""))) {
            showDialog("Hãy nhập giá xe ", 0);
        } else if (cityID.isEmpty() || cityID.equals("")) {
            showDialog("Hãy chọn tỉnh thành", 0);
        } else if (tbAddressAddParking.getText().toString().length() < 3 || tbAddressAddParking.getText().toString().length() > 100) {
            showDialog("Địa chỉ phải lớn hơn 3 và nhỏ hơn 100 kí tự", 0);
        } else if ( Integer.parseInt(tbOpenHourAddParking.getText().toString())>23 ||Integer.parseInt(tbOpenMinAddParking.getText().toString())>59
                || Integer.parseInt(tbCloseHourAddParking.getText().toString())>24 ||Integer.parseInt(tbCloseMinAddParking.getText().toString())>59
                || (Integer.parseInt(tbCloseHourAddParking.getText().toString())== 24 &&Integer.parseInt(tbCloseMinAddParking.getText().toString())>0)) {
            showDialog("Thời gian không hợp lệ", 0);
        } else if ( Integer.parseInt(tbOpenHourAddParking.getText().toString())>Integer.parseInt(tbCloseHourAddParking.getText().toString())
                || (Integer.parseInt(tbOpenHourAddParking.getText().toString())<Integer.parseInt(tbCloseHourAddParking.getText().toString()) &&
                Integer.parseInt(tbOpenMinAddParking.getText().toString())>Integer.parseInt(tbCloseMinAddParking.getText().toString()))) {
            showDialog("Giờ đóng cửa phải muộn hơn giờ mở cửa", 0);
        } else if ( tbSpace.getText().length()>3 || Integer.parseInt(tbSpace.getText().toString()) < 1 || Integer.parseInt(tbSpace.getText().toString()) > 200) {
            showDialog("Số chỗ phải lớn hơn 0 và nhỏ hơn 200", 0);
        }
        else {
            return true;
        }

        return false;
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
            Toast.makeText(AddParkingInformation.this, "Khong ho tro", Toast.LENGTH_LONG).show();
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
                    tbAddressAddParking.setText(str.get(0).toString());
                    getLocationFromVoiceSearch(str.get(0).toString());
                }
                break;
        }
    }

    protected void getLocationFromVoiceSearch(String location) {
        Geocoder geocoder = new Geocoder(AddParkingInformation.this);
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
        textToSpeechNearPlace = new TextToSpeech(AddParkingInformation.this, new TextToSpeech.OnInitListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onInit(int status) {
                textToSpeechNearPlace.setLanguage(Locale.forLanguageTag("vi-VN"));
                textToSpeechNearPlace.speak("Địa chỉ không hợp lệ", TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }



    public void showDialog(String text, final int type) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddParkingInformation.this);
        View mView = getLayoutInflater().inflate(R.layout.activity_alert_dialog, null);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        error = (TextView) mView.findViewById(R.id.tvAlert);
        btnOK = (Button) mView.findViewById(R.id.btnOK);
        error.setText(text);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                if (type == 1) {
                    Intent i = new Intent(AddParkingInformation.this, HomeActivity.class);
                    finish();
                    startActivity(i);
                }
            }
        });
    }

    @Override
    public void onPostExecute(Object o) {
        cityDTOS = (ArrayList<CityDTO>) o;
        if (cityDTOS.size() > 0) {
            PARKINGLIST = new String[cityDTOS.size()];
            for (int i = 0; i < cityDTOS.size(); i++) {
                PARKINGLIST[i] = cityDTOS.get(i).getCityName();
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, PARKINGLIST);
            betterSpinner.setAdapter(arrayAdapter);
        }
    }
}

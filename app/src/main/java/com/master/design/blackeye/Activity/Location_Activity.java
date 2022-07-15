package com.master.design.blackeye.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.master.design.blackeye.R;
import com.master.design.blackeye.Utils.Helper;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Location_Activity extends AppCompatActivity implements OnMapReadyCallback {
//
//    @BindView(R.id.card_img)
//    Frag card_img;

    private FusedLocationProviderClient client;
    private SupportMapFragment mapFragment;
    private int REQUEST_CODE = 111;
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;
    private GoogleMap mMap;
    private Geocoder geocoder;
    private double selectedlat, selecteclng;
    private List<Address> addresses;
    private String selectedAddress;
    private SearchView searchView;
    private GoogleMap map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        ButterKnife.bind(this);
        searchView = findViewById(R.id.sv_location);


        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);

        client = LocationServices.getFusedLocationProviderClient(Location_Activity.this);

        if (ActivityCompat.checkSelfPermission(Location_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(Location_Activity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }

        searchLocation();
    }

    public void searchLocation() {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;

                if (location != null || !location.equals("") || location.equalsIgnoreCase(String.valueOf(addressList))) {
                    Geocoder geocoder = new Geocoder(Location_Activity.this);
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = null;
                    if(addressList.size()==1){
                          address = addressList.get(0);
                          LatLng latLng= new LatLng(address.getLatitude(),address.getLongitude());
                          mMap.addMarker(new MarkerOptions().position(latLng).title(location));
                          mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));

                    }else{
                        Helper.showToast(Location_Activity.this,"kindly enter correct name");
                    }

                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mapFragment.getMapAsync(this);

    }

    private void getCurrentLocation() {

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
        Task<Location> task = client.getLastLocation();

        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            mMap = googleMap;
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());


                            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("You are here");

                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
                            googleMap.addMarker(markerOptions).showInfoWindow();

                            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                                @Override
                                public void onMapClick(LatLng latLng) {
                                    CheckConnection();

                                    if (networkInfo.isConnected() && networkInfo.isAvailable()) {
                                        selectedlat = latLng.latitude;
                                        selecteclng = latLng.longitude;

                                        GetAddress(selectedlat, selecteclng);

                                    } else {
                                        Toast.makeText(Location_Activity.this, "please check connection", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                        }
                    });
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            }
        } else {
            Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    private void CheckConnection() {
        connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
    }


    private void GetAddress(double mlat, double mlong) {
        geocoder = new Geocoder(Location_Activity.this, Locale.getDefault());

        if (mlat != 0) {

            try {
                addresses = geocoder.getFromLocation(mlat, mlong, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (addresses.size()!=0) {
                String mAddress = addresses.get(0).getAddressLine(0);

                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String postalCode = addresses.get(0).getPostalCode();
                String areaName = addresses.get(0).getFeatureName();
                String dis = addresses.get(0).getSubAdminArea();

                selectedAddress = mAddress;

                if (mAddress != null) {
                    MarkerOptions markerOptions = new MarkerOptions();

                    LatLng latLng = new LatLng(mlat, mlong);
                    markerOptions.position(latLng).title(selectedAddress);
                    mMap.addMarker(markerOptions).showInfoWindow();
                } else {
                    Helper.showToast(Location_Activity.this, "Something went  wrong");
                }
            } else {
                Helper.showToast(Location_Activity.this, "Something went  wrong");
            }
        } else {
            Helper.showToast(Location_Activity.this, "latlng null");
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
}
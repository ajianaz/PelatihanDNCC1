package com.viluvasa.pelatihandncc1.activity;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.viluvasa.pelatihandncc1.R;
import com.viluvasa.pelatihandncc1.helper.UserHelper;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in peacock and move the camera
//        LatLng sydney = new LatLng(-6.978539, 110.411588);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Judul").snippet("Sub Judul"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 17));
        if (mMap!=null){
            if (!UserHelper.GPS_Loc_Lat.equals("")){
                AddMarker( UserHelper.GPS_Loc_Lat, UserHelper.GPS_Loc_Lang, "Lokasi Saya", "Testing");
            }
        }
//        for (int i = 0; i < 10; i++){
//            AddMarker("-6.97"+(i+8), "110.4"+(i+8), "Marker "+i, "Sub "+i);
//        }

    }
    private void AddMarker(String lat, String longi, String title, String snippet){

        //Pasang Lokasi/titik marker
        LatLng Marker = new LatLng(Double.valueOf(lat), Double.valueOf(longi));

        //Tambah marker
        mMap.addMarker(new MarkerOptions()
                .position(Marker)
                .title(title)
                .snippet(snippet)

        ).showInfoWindow();//untuk menampilkan info windows secara langsung


        //Zoom ke Marker
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Marker, 17));
    }
}

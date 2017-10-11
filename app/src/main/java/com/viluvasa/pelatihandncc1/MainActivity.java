package com.viluvasa.pelatihandncc1;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.viluvasa.pelatihandncc1.activity.MapsActivity;
import com.viluvasa.pelatihandncc1.helper.UserHelper;

public class MainActivity extends AppCompatActivity implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    //TODO (4) Inisiasi Button
    Button btnF_B;
    EditText edInputan;

    //Set For GPS
    protected GoogleApiClient mGoogleApiClient;

    int currentapiVersion;
    int time_request_GPS = 30;
    boolean gps_enabled = false;
    boolean network_enabled = false;
    LocationManager locationManager;
    Location mCurlocation;
    String TAG = "Pelatihan Maps";
    private static final int REQUEST_CHECK_SETTINGS = 1;
    private static final int ACCESS_FINE_LOCATION_INTENT_ID = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Pemanggilan Fungsi
        InitViews();

        getAndroidVersion();
        buildGoogleApiClient();//maps google location

        checkPermissions();
        cekGPSandProvider();

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }
    //get Android version
    private void getAndroidVersion(){
        currentapiVersion = android.os.Build.VERSION.SDK_INT;
        Log.d(TAG, "Android Version : "+currentapiVersion);

    }

    //set location
    private void setMyLocation(Location location) {
        //getGPS();
        if (location != null) {
            UserHelper.GPS_Loc_Lat = String.valueOf(location.getLatitude());
            UserHelper.GPS_Loc_Lang = String.valueOf(location.getLongitude());
        } else {
            UserHelper.GPS_Loc_Lat = null;
            UserHelper.GPS_Loc_Lang = null;
        }

        Log.d(TAG, "Lokasi Saat Ini : "+UserHelper.GPS_Loc_Lat + " dan " + UserHelper.GPS_Loc_Lang);
        //Toast.makeText(this, ""+UserHelper.Loc_Lat + " dan " + UserHelper.Loc_Lang, Toast.LENGTH_SHORT).show();
    }
    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED)
                requestLocationPermission();
            else
                showSettingDialog();
        } else
            showSettingDialog();

    }
    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_FINE_LOCATION_INTENT_ID);

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_FINE_LOCATION_INTENT_ID);
        }
    }
    private void showSettingDialog() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);//Setting priotity of Location request to high
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);//5 sec Time interval for location update
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true); //this is the key ingredient to show dialog always when GPS is off

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.

                        //DialogAlert("GPS is Enabled in your device");

                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(MainActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case RESULT_OK:
                        Log.d("Settings", "GPS Result OK");
                        //DialogAlert("GPS is Enabled in your device");
                        buildGoogleApiClient();
                        //startLocationUpdates();
                        break;
                    case RESULT_CANCELED:
                        Log.d("Settings", "GPS Result Cancel");
                        DialogAlert("Aplikasi ini membutuhkan akses GPS. Mohon Aktifkan GPS. Terimakasih");
                        break;
                }
                break;
        }
    }
    //Hasil request permission
/* On Request permission method to check the permisison is granted or not for Marshmallow+ Devices  */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ACCESS_FINE_LOCATION_INTENT_ID: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //If permission granted show location dialog if APIClient is not null
                    if (mGoogleApiClient == null) {
                        buildGoogleApiClient();
                        showSettingDialog();
                    } else
                        showSettingDialog();


                } else {
                    DialogAlert("Aplikasi ini membutuhkan akses GPS. Mohon Aktifkan GPS. Terimakasih");
                    //Toast.makeText(MainBotActivity.this, "Location Permission denied.", Toast.LENGTH_SHORT).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }
    //Set For GPS
    private void getLocFromGoogle(){
        // Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
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
        mCurlocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mCurlocation != null) {
            setMyLocation(mCurlocation);
        } else {
            //Toast.makeText(this, "tes", Toast.LENGTH_LONG).show();
        }
    }
    private void cekGPSandProvider() {
        getGPS();
        //Toast.makeText(getApplicationContext(), "GPS : "+gps_enabled+"\nProvider : "+network_enabled, Toast.LENGTH_SHORT).show();
        if (gps_enabled == true || network_enabled == true) {
            //return true;
            setMyLocation(mCurlocation);
            //getLocFromGoogle();
            //GPS_Aktif = true;
        } else {
            getLocFromGoogle();
            //openSettingSet();
            // komen setting gps
            //return false;
        }
    }
    //init gps
    private void getGPS() {
        // Get the LocationManager object from the System Service LOCATION_SERVICE
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        // Create a criteria object needed to retrieve the provider
        Criteria criteria = new Criteria();

        // Get the name of the best available provider
        String provider = locationManager.getBestProvider(criteria, true);

        // We can use the provider immediately to get the last known location
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;

        }


        // request that the provider send this activity GPS updates every time_request_GPS=30 seconds
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, time_request_GPS*1000, 0, this);
        } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, time_request_GPS*1000, 0, this);
        }

        mCurlocation = locationManager.getLastKnownLocation(provider);

        //cekGPSandProvider();
        setMyLocation(mCurlocation);


    }

    //start GoogleApiClient.ConnectionCallbacks
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "Google Connected");
        getLocFromGoogle();
    }
    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }
    //end GoogleApiClient.ConnectionCallbacks
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Google Connection Failed");
    }
    //start Location Listener
    @Override
    public void onLocationChanged(Location location) {
        if (mCurlocation!=location){
            setMyLocation(location);
        }else {
            setMyLocation(mCurlocation);
        }
    }
    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }
    @Override
    public void onProviderEnabled(String s) {

    }
    @Override
    public void onProviderDisabled(String s) {

    }
    //end Location Listener
    protected void onStart() {
        Log.i(TAG, "Start Apps");
        mGoogleApiClient.connect();
        super.onStart();
    }
    protected void onStop() {
        Log.i(TAG, "Stop Apps");
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    private void DialogAlert(String message){
        new MaterialDialog.Builder(this)
                .title("PERHATIAN")
                .content(message)
                .positiveText("OK")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        checkPermissions();
                    }
                })
                .show();
    }


    //TODO (5) Inisiasi Views/Layout
    private void InitViews(){
        edInputan = (EditText)findViewById(R.id.edInputan);
        btnF_B = (Button)findViewById(R.id.btnF_B);
        btnF_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Fragment B", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //TODO (3) Tambah fungsi klik Fragment_A
    public void ClickedF_A(View view){
        Intent i = new Intent(this, MapsActivity.class);
//        i.putExtra("tujuan", 1);
//        i.putExtra("judul", edInputan.getText().toString());
        startActivity(i);
    }

    public void ClickedF_C(View view){
        //Toast.makeText(this, "Fragment A", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, TampungFragmentActivity.class);
        i.putExtra("tujuan", 1);
        i.putExtra("judul", "Dari Main Ke FA");
        startActivity(i);
    }
}

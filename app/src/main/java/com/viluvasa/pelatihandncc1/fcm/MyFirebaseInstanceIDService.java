package com.viluvasa.pelatihandncc1.fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.viluvasa.pelatihandncc1.helper.UserHelper;

/**
 * Created by ASA on 12/14/2016.
 */

//Class extending FirebaseInstanceIdService
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private String SenderID = "595405354893";
    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();
    String refreshedToken;

    @Override
    public void onTokenRefresh() {
        //Getting registration token
        refreshedToken = FirebaseInstanceId.getInstance().getToken();

        //simpan token ke variabel global
        UserHelper.token_FCM = refreshedToken;

        //Displaying token on logcat
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        //Toast.makeText(getApplicationContext(), "Token Ready", Toast.LENGTH_SHORT).show();
        //sendRegistrationToServer(refreshedToken);
    }

    public String getTokenID() {
        //Getting registration token

        refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //sendRegistrationToServer(refreshedToken);

        //simpan token ke variabel global
        UserHelper.token_FCM = refreshedToken;

        //Displaying token on logcat
        Log.d(TAG, "Refreshed get token: " + refreshedToken);
        return refreshedToken;
    }

    private void sendRegistrationToServer(String token) {
        //You can implement this method to store the token on your server
        //Not required for current project
//        UserHelper.token_FCM = token;
//        Log.d(TAG, "RegistrationToServer token: " + refreshedToken);
    }
}

package com.viluvasa.pelatihandncc1;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.viluvasa.pelatihandncc1.R;
import com.viluvasa.pelatihandncc1.fragment.FragmentA;

public class SampelActivity extends AppCompatActivity {
    Fragment fragment = null;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sampel);
        getSupportActionBar().setTitle("Sampel Tampung");
        setFragment();

    }

    public void setFragment(){
        if(fragment == null ){
            fragment = new FragmentA();//Default Fragment
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_sampel, fragment, null);
            fragmentTransaction.commit();
        }
        else if (fragment != null) {
            //Fragment yang di tuju
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_sampel, fragment, null);
            fragmentTransaction.commit();
        }
    }
}

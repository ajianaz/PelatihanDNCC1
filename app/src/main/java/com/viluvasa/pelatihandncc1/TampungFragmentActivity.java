package com.viluvasa.pelatihandncc1;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.viluvasa.pelatihandncc1.fragment.FragmentA;
import com.viluvasa.pelatihandncc1.fragment.FragmentB;
import com.viluvasa.pelatihandncc1.fragment.FragmentC;

public class TampungFragmentActivity extends AppCompatActivity {
//    TODO (1) Tambah 2 Line di bawah
    Fragment fragment = null;
    FragmentTransaction fragmentTransaction;
    int FragmentTujuan = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampung_fragment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentTujuan = getIntent().getIntExtra("tujuan", 1);
        SetJudul(getIntent().getStringExtra("judul"));
        BukaTujuan(FragmentTujuan);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //app icon in action bar clicked
                this.finish();
                return true;
            default:

        }
        return super.onOptionsItemSelected(item);
    }
    private void SetJudul(String s){
        getSupportActionBar().setTitle(s);
    }
    private void BukaTujuan(int fragmentTujuan){
        if (fragmentTujuan==1){
            fragment = new FragmentA();
            setFragment();
        }else if (fragmentTujuan==2){
            fragment = new FragmentB();
            setFragment();
        }else if (fragmentTujuan==3){
            fragment = new FragmentC();
            setFragment();
        }
    }
    //TODO (2) Tambah Fungsi setFragment
    public void setFragment(){
        if(fragment == null ){
            fragment = new FragmentA();//Default Fragment
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content, fragment, null);
            fragmentTransaction.commit();
        }
        else if (fragment != null) {
            //Fragment yang di tuju
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content, fragment, null);
            fragmentTransaction.commit();
        }
    }
}

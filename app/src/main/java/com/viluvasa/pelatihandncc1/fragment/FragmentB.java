package com.viluvasa.pelatihandncc1.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.viluvasa.pelatihandncc1.R;
import com.viluvasa.pelatihandncc1.TampungFragmentActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentB extends Fragment {
    View rootView;
    Button FB_A, FB_B, FB_C;

    public FragmentB() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_b, container, false);
        InitViews();
        return rootView;
    }
    private void InitViews(){
        FB_A = (Button)rootView.findViewById(R.id.FB_A);
        FB_A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), TampungFragmentActivity.class);
                i.putExtra("tujuan", 1);
                i.putExtra("judul", "Dari FB ke FA");
                startActivity(i);
            }
        });

        FB_B = (Button)rootView.findViewById(R.id.FB_B);
        FB_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), TampungFragmentActivity.class);
                i.putExtra("tujuan", 2);
                i.putExtra("judul", "Dari FB ke FB");
                startActivity(i);
            }
        });

        FB_C = (Button)rootView.findViewById(R.id.FB_C);
        FB_C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), TampungFragmentActivity.class);
                i.putExtra("tujuan", 3);
                i.putExtra("judul", "Dari FB ke FC");
                startActivity(i);
            }
        });
    }
}

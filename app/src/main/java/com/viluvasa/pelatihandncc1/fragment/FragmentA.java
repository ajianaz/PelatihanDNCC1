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
public class FragmentA extends Fragment {
    View rootView;
    Button FA_A, FA_B, FA_C;

    public FragmentA() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_a, container, false);
        InitViews();

        return rootView;
    }
    private void InitViews(){

        FA_A = (Button)rootView.findViewById(R.id.FA_A);
        FA_A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), TampungFragmentActivity.class);
                i.putExtra("tujuan", 1);
                i.putExtra("judul", "Dari FA ke FA");
                startActivity(i);
            }
        });

        FA_B = (Button)rootView.findViewById(R.id.FA_B);
        FA_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), TampungFragmentActivity.class);
                i.putExtra("tujuan", 2);
                i.putExtra("judul", "Dari FA ke FB");
                startActivity(i);
            }
        });

        FA_C = (Button)rootView.findViewById(R.id.FA_C);
        FA_C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), TampungFragmentActivity.class);
                i.putExtra("tujuan", 3);
                i.putExtra("judul", "Dari FA ke FC");
                startActivity(i);
            }
        });
    }
}


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
public class FragmentC extends Fragment {
    View rootView;
    Button FC_A, FC_B, FC_C;

    public FragmentC() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_c, container, false);
        InitViews();

        return rootView;
    }
    private void InitViews(){
        FC_A = (Button)rootView.findViewById(R.id.FC_A);
        FC_A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), TampungFragmentActivity.class);
                i.putExtra("tujuan", 1);
                i.putExtra("judul", "Dari FC ke FA");
                startActivity(i);
            }
        });

        FC_B = (Button)rootView.findViewById(R.id.FC_B);
        FC_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), TampungFragmentActivity.class);
                i.putExtra("tujuan", 2);
                i.putExtra("judul", "Dari FC ke FB");
                startActivity(i);
            }
        });

        FC_C = (Button)rootView.findViewById(R.id.FC_C);
        FC_C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), TampungFragmentActivity.class);
                i.putExtra("tujuan", 3);
                i.putExtra("judul", "Dari FC ke FC");
                startActivity(i);
            }
        });
    }
}

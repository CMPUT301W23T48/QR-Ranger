package com.example.qrranger;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class MapFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        // Call the init method and pass the inflated View as a parameter
        init(view);

        return view;
    }

    public void init(View view){
        Button btnViewMap = view.findViewById(R.id.viewMap);

        btnViewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start a new Map activity when button is clicked
                Toast.makeText(getActivity(), "Button clicked", Toast.LENGTH_SHORT).show();
                Intent intent  = new Intent(getActivity(), MapActivity.class);
                startActivity(intent);
            }
        });
    }
}
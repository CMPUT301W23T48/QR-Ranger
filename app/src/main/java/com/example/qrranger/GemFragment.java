package com.example.qrranger;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class GemFragment extends Fragment {
//    ImageView imageView;
//    gemID ID = new gemID();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gemview, container, false);
//        imageView.findViewById(R.id.gem);

    }
//    public void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        imageView.findViewById(R.id.gem);
//        imageView.setImageResource(getResources().getIdentifier((ID.getGemType()),"drawable", getActivity().getPackageName()));
//    }
}
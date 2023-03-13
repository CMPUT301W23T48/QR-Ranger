package com.example.qrranger;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GemFragment extends Fragment {

    private TextView qrTitle;
    private TextView qrScore;
    private ImageView gemShape;
    private ImageView backgroundColor;
    private ImageView gemBorder;
    private ImageView gemLustre;
    private Button deleteButton;

    private QRCode qrCode;
    private PlayerCollection collection;
    private UserState state;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gemview, container, false);

        qrTitle = view.findViewById(R.id.gemName);
        qrScore = view.findViewById(R.id.gemValue);
        gemShape = view.findViewById(R.id.gem);
        backgroundColor = view.findViewById(R.id.backgroundColor);
        gemBorder = view.findViewById(R.id.borderType);
        gemLustre = view.findViewById(R.id.lusterLevel);
        deleteButton = view.findViewById(R.id.delete);

        collection = new PlayerCollection(null);
        state = UserState.getInstance();
        collection.read(state.getUserID(), data -> {

            }, error -> {
                     
            });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return view;
    }

    private void updateUi() {
        gemShape.setImageResource(qrCode.getGemID().getGemType());
        gemLustre.setImageResource(qrCode.getGemID().getLusterLevel());
        gemBorder.setImageResource(qrCode.getGemID().getBoarder());
        backgroundColor.setImageResource(qrCode.getGemID().getBgColor());
        qrTitle.setText(qrCode.getName());
        qrScore.setText(qrCode.getPoints());
    }
}
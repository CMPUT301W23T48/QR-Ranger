package com.example.qrranger;

import static android.content.ContentValues.TAG;
import static android.content.Intent.getIntent;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class GemFragment extends Fragment {

    private TextView qrTitle;
    private TextView qrScore;
    private ImageView gemShape;
    private ImageView backgroundColor;
    private ImageView gemBorder;
    private ImageView gemLustre;
    private Button deleteButton;

    private QRCode qrCode;
    private PlayerCollection playerCollection;
    private QRCollection qrCollection;
    private UserState state;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gemview, container, false);

        // Link all the layout components to the fragment.
        qrTitle = view.findViewById(R.id.gemName);
        qrScore = view.findViewById(R.id.gemValue);
        gemShape = view.findViewById(R.id.gem);
        backgroundColor = view.findViewById(R.id.backgroundColor);
        gemBorder = view.findViewById(R.id.borderType);
        gemLustre = view.findViewById(R.id.lusterLevel);
        deleteButton = view.findViewById(R.id.delete);

        // Retrieve the id of the qr that was clicked on.
        String id = getArguments().getString("qr_id");

        // Read the corresponding qr from the db.
        qrCollection.read(id, data ->{
            String qrId = Objects.requireNonNull(data.get("qr_id").toString());
            String name = Objects.requireNonNull(data.get("name").toString());
            String url = Objects.requireNonNull(data.get("url").toString());
            Integer points = (Integer) data.get("points");
            gemID gem = (gemID) data.get("gem_id");

            qrCode.setName(name);
            qrCode.setPoints(points);
            qrCode.setUrl(url);
            qrCode.setGemId(gem);
        }, error -> {
            Log.e(TAG, "Error when loading QR from database.");
        });

        updateUi();

        // Deleting the QR code from the account.
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the player instance.
                state = UserState.getInstance();
                String playerId = state.getUserID();

                // Remove the qr from the account.
                playerCollection = new PlayerCollection(null);
                playerCollection.delete_QR_from_players(playerId, qrCode.getId());

                // Switch fragments to the User Profile.
                ((MainActivity) getActivity()).replaceFragment(new ProfileFragment());
            }
        });

        return view;
    }

    /**
     * Sets the UI elements to reflect the current QR Code. Note that the private field qrCode
     * needs to be adequately updated to get the proper results.
     */
    private void updateUi() {
        gemShape.setImageResource(qrCode.getGemID().getGemType());
        gemLustre.setImageResource(qrCode.getGemID().getLusterLevel());
        gemBorder.setImageResource(qrCode.getGemID().getBoarder());
        backgroundColor.setImageResource(qrCode.getGemID().getBgColor());
        qrTitle.setText(qrCode.getName());
        qrScore.setText(qrCode.getPoints());
    }
}
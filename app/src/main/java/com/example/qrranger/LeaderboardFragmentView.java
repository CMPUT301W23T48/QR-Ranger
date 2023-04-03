package com.example.qrranger;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LeaderboardFragmentView extends Fragment {

    TextView rank1Username;
    TextView rank2Username;
    TextView rank3Username;
    TextView rank4Username;
    TextView rank5Username;
    TextView rank6Username;
    TextView rank1Score;
    TextView rank2Score;
    TextView rank3Score;
    TextView rank4Score;
    TextView rank5Score;
    TextView rank6Score;
    LinearLayout rank1LL;
    LinearLayout rank2LL;
    LinearLayout rank3LL;
    LinearLayout rank4LL;
    LinearLayout rank5LL;
    LinearLayout rank6LL;
    List<Map<String, Object>> top6;

    /**
     * Initializes the fragment and sets up the views for displaying the leaderboard.
     *
     * @param inflater           A LayoutInflater to inflate the layout for this fragment.
     * @param container          A ViewGroup in which the fragment's layout will be added.
     * @param savedInstanceState A Bundle containing the saved state of the fragment.
     * @return The View for the fragment's UI, or null.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.leaderboard_view, container, false);

        rank1Username = view.findViewById(R.id.Rank1Username);
        rank2Username = view.findViewById(R.id.Rank2Username);
        rank3Username = view.findViewById(R.id.Rank3Username);
        rank4Username = view.findViewById(R.id.Rank4Username);
        rank5Username = view.findViewById(R.id.Rank5Username);
        rank6Username = view.findViewById(R.id.Rank6Username);
        rank1Score = view.findViewById(R.id.Rank1Score);
        rank2Score = view.findViewById(R.id.Rank2Score);
        rank3Score = view.findViewById(R.id.Rank3Score);
        rank4Score = view.findViewById(R.id.Rank4Score);
        rank5Score = view.findViewById(R.id.Rank5Score);
        rank6Score = view.findViewById(R.id.Rank6Score);
        rank1LL = view.findViewById(R.id.LeaderboardRank1LL);
        rank2LL = view.findViewById(R.id.LeaderboardRank2LL);
        rank3LL = view.findViewById(R.id.LeaderboardRank3LL);
        rank4LL = view.findViewById(R.id.LeaderboardRank4LL);
        rank5LL = view.findViewById(R.id.LeaderboardRank5LL);
        rank6LL = view.findViewById(R.id.LeaderboardRank6LL);

        PlayerCollection pc = new PlayerCollection(null);
        pc.getTop6Players(
                top6Players -> {
                    // handle successful retrieval of top 3 players
                    // top3Players is a List<Map<String, Object>> containing the data of the top 3 players
                    System.out.println("Top 6 Players: " + top6Players);
                    top6 = top6Players;
                    setViews(top6Players);
                },
                error -> {
                    // handle error
                    // error is an Exception object containing details about the error
                    System.out.println("Error getting top 6 players.");
                }
        );


        rank1LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (top6 != null)
                {
                    Map<String, Object> player_data = top6.get(0);
                    startOtherUserProfile(player_data);
                }
            }
        });

        rank2LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (top6 != null)
                {
                    Map<String, Object> player_data = top6.get(1);
                    startOtherUserProfile(player_data);
                }
            }
        });

        rank3LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (top6 != null)
                {
                    Map<String, Object> player_data = top6.get(2);
                    startOtherUserProfile(player_data);
                }
            }
        });

        rank4LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (top6 != null)
                {
                    Map<String, Object> player_data = top6.get(3);
                    startOtherUserProfile(player_data);
                }
            }
        });

        rank5LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (top6 != null)
                {
                    Map<String, Object> player_data = top6.get(4);
                    startOtherUserProfile(player_data);
                }
            }
        });

        rank6LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (top6 != null)
                {
                    Map<String, Object> player_data = top6.get(5);
                    startOtherUserProfile(player_data);
                }
            }
        });

        return view;
    }

    /**
     * Sets up the views with the appropriate data of the top 6 players.
     *
     * @param top6Players A List of Maps containing the data of the top 6 players.
     */
    public void setViews(List<Map<String, Object>> top6Players)
    {
        rank1Username.setText(top6Players.get(0).get("username").toString());
        rank2Username.setText(top6Players.get(1).get("username").toString());
        rank3Username.setText(top6Players.get(2).get("username").toString());
        rank4Username.setText(top6Players.get(3).get("username").toString());
        rank5Username.setText(top6Players.get(4).get("username").toString());
        rank6Username.setText(top6Players.get(5).get("username").toString());

        rank1Score.setText(top6Players.get(0).get("totalScore").toString());
        rank2Score.setText(top6Players.get(1).get("totalScore").toString());
        rank3Score.setText(top6Players.get(2).get("totalScore").toString());
        rank4Score.setText(top6Players.get(3).get("totalScore").toString());
        rank5Score.setText(top6Players.get(4).get("totalScore").toString());
        rank6Score.setText(top6Players.get(5).get("totalScore").toString());
    }


    /**
     * Starts a new activity displaying the details of another user's profile.
     *
     * @param data A Map containing the data of the user's profile to display.
     */
    private void startOtherUserProfile(Map<String,Object> data) {
        // send the info in data as intent for the new activity
        Intent intent = new Intent(getActivity(), OtherUserProfileActivityView.class);
        intent.putExtra("username", data.get("username").toString());
        intent.putExtra("email", data.get("email").toString());
        intent.putExtra("phoneNumber", data.get("phoneNumber").toString());
        intent.putExtra("userID", data.get("userID").toString());
        intent.putExtra("totalQRCode", data.get("totalQRCode").toString());
        intent.putExtra("totalScore", data.get("totalScore").toString());
        intent.putExtra("qr_code_ids", (ArrayList<String>)data.get("qr_code_ids"));

        startActivity(intent);
    }
}
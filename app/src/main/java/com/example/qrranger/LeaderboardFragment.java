package com.example.qrranger;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.qrranger.R;

import java.util.List;
import java.util.Map;

public class LeaderboardFragment extends Fragment {

    TextView rank1Username;
    TextView rank2Username;
    TextView rank3Username;
    TextView rank1Score;
    TextView rank2Score;
    TextView rank3Score;
    LinearLayout rank1LL;
    LinearLayout rank2LL;
    LinearLayout rank3LL;
    List<Map<String, Object>> top3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        rank1Username = view.findViewById(R.id.Rank1Username);
        rank2Username = view.findViewById(R.id.Rank2Username);
        rank3Username = view.findViewById(R.id.Rank3Username);
        rank1Score = view.findViewById(R.id.Rank1Score);
        rank2Score = view.findViewById(R.id.Rank2Score);
        rank3Score = view.findViewById(R.id.Rank3Score);
        rank1LL = view.findViewById(R.id.LeaderboardRank1LL);
        rank2LL = view.findViewById(R.id.LeaderboardRank2LL);
        rank3LL = view.findViewById(R.id.LeaderboardRank3LL);

        PlayerCollection pc = new PlayerCollection(null);
        pc.getTop3Players(
                top3Players -> {
                    // handle successful retrieval of top 3 players
                    // top3Players is a List<Map<String, Object>> containing the data of the top 3 players
                    System.out.println("Top 3 Players: " + top3Players);
                    top3 = top3Players;
                    setViews(top3Players);
                },
                error -> {
                    // handle error
                    // error is an Exception object containing details about the error
                    System.out.println("Error getting top 3 players.");
                }
        );


        rank1LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (top3 != null)
                {
                    Map<String, Object> player_data = top3.get(0);
                    startOtherUserProfile(player_data);
                }
            }
        });

        rank2LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (top3 != null)
                {
                    Map<String, Object> player_data = top3.get(1);
                    startOtherUserProfile(player_data);
                }
            }
        });

        rank3LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (top3 != null)
                {
                    Map<String, Object> player_data = top3.get(2);
                    startOtherUserProfile(player_data);
                }
            }
        });

        return view;
    }
    public void setViews(List<Map<String, Object>> top3Players)
    {
        rank1Username.setText(top3Players.get(0).get("username").toString());
        rank2Username.setText(top3Players.get(1).get("username").toString());
        rank3Username.setText(top3Players.get(2).get("username").toString());

        rank1Score.setText(top3Players.get(0).get("totalScore").toString());
        rank2Score.setText(top3Players.get(1).get("totalScore").toString());
        rank3Score.setText(top3Players.get(2).get("totalScore").toString());
    }


    private void startOtherUserProfile(Map<String,Object> data) {
        // send the info in data as intent for the new activity
        Intent intent = new Intent(getActivity(), OtherUserProfileActivity.class);
        intent.putExtra("username", data.get("username").toString());
        intent.putExtra("email", data.get("email").toString());
        intent.putExtra("phoneNumber", data.get("phoneNumber").toString());
        intent.putExtra("userID", data.get("userID").toString());
        intent.putExtra("totalQRCode", data.get("totalQRCode").toString());
        intent.putExtra("totalScore", data.get("totalScore").toString());
//        intent.putExtra("qr_code_ids", data.get("qr_code_ids"));
        startActivity(intent);
    }
}
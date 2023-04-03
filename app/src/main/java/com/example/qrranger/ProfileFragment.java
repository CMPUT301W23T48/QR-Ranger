package com.example.qrranger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A fragment representing the user's profile screen in the QR Ranger application.
 * Displays the user's information, such as name, email, phone number, total score,
 * total QR codes collected, profile rank, and lists the collected QR codes.
 * Allows the user to navigate to the Settings and Gem activities.
 */
public class ProfileFragment extends Fragment {
    Player myUser = new Player();
    private TextView playerName;
    private TextView playerEmail;
    private Map<String, Object> value = new HashMap<>();
    private TextView playerPhoneNumb;
    private TextView playerTotalScore;
    private TextView playerTotalQRCodes;
    private TextView profileRank;
    private ImageView myAvatar;
    private ImageButton mySettButton;
    private Intent data;
    private ArrayAdapter<String> adapter;
    private ListView listView;
    private TextView highestQR;
    private TextView lowestQR;
    private ImageView myHighestImage[] = new ImageView[4];
    private ImageView myLowestImage[] = new ImageView[4];
    private Map gem_data[] = new Map[2];
    PlayerCollection myPlayerCollection = new PlayerCollection(Database.getInstance());

    private ActivityResultLauncher<Intent> startSettingsForResult =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null) {
                                boolean dataChanged = data.getBooleanExtra("dataChanged", false);
                                if (dataChanged) {
                                    System.out.println("Data changed");
                                    myUser = (Player) data.getSerializableExtra("myUser");
                                    setViews();
                                }
                            }
                        }
                    });

    private ActivityResultLauncher<Intent> startGemForResult =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null) {
                                boolean dataChanged = data.getBooleanExtra("dataDeleted", false);
                                if (dataChanged) {
                                    System.out.println("Data deleted");
                                    String qr_id = data.getStringExtra("qr_id");
                                    myPlayerCollection.delete_QR_from_players(myUser.getPlayerId(), qr_id);
                                    getAndSetList(myUser.getPlayerId());
                                    MainActivity mainActivity = (MainActivity) getActivity();
                                    mainActivity.replaceFragment(new ProfileFragment());
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });


    /**
     * Called to create the view hierarchy associated with the fragment.
     * Inflates the layout for the fragment, initializes views, and sets up listeners.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container          If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return The View for the fragment's UI, or null.
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        playerEmail = view.findViewById(R.id.ProfileUserEmail);
        playerName = view.findViewById(R.id.ProfileUserName);
        playerPhoneNumb = view.findViewById(R.id.ProfileUserPhoneNumber);
        playerTotalScore = view.findViewById(R.id.ProfileTS);
        playerTotalQRCodes = view.findViewById(R.id.ProfileQRNum);
        mySettButton = view.findViewById(R.id.ProfileSettingButton);
        profileRank = view.findViewById(R.id.ProfileRank);
        listView = view.findViewById(R.id.ProfileQR_list_view);
        highestQR = view.findViewById(R.id.highestScore);
        lowestQR = view.findViewById(R.id.lowestScore);

        myHighestImage[0] = view.findViewById(R.id.highestScoreImageBackgroud);
        myHighestImage[1] = view.findViewById(R.id.highetsScoreImageBorder);
        myHighestImage[2] = view.findViewById(R.id.highestScoreImageLuster);
        myHighestImage[3] = view.findViewById(R.id.highestScoreImageShape);

        myLowestImage[0] = view.findViewById(R.id.lowestScoreImageBackground);
        myLowestImage[1] = view.findViewById(R.id.lowestScoreBorder);
        myLowestImage[2] = view.findViewById(R.id.lowestScoreImageLuster);
        myLowestImage[3] = view.findViewById(R.id.lowestScoreImageShape);


        UserState us = UserState.getInstance();
        String userID = us.getUserID();

        CompletableFuture<Boolean> future = myPlayerCollection.checkUserExists(userID);
        future.thenAccept(userExists -> {
                    if (userExists) {
                        // User exists
                        System.out.println("User exists");
                        setValues(userID);
                    } else {
                        // User does not exist, handle errors
                    }

                });

        mySettButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSettingsActivity();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String name = adapterView.getItemAtPosition(i).toString();
                        startGemActivity(name, i);
                    }
                });
            }
        });


        return view;
    }
    public void setValues(String userID){
        // get data from userID
        myPlayerCollection.read(userID, data -> {
            // player found so handle code using data here
            System.out.println("Data for user: " + data);
            myUser.setUserName(Objects.requireNonNull(data.get("username")).toString());
            myUser.setEmail(Objects.requireNonNull(data.get("email")).toString());
            myUser.setPhoneNumber(Objects.requireNonNull(data.get("phoneNumber")).toString());
            myUser.setTotalScore(((Long) data.get("totalScore")));
            myUser.setTotalQRCode(((Long) data.get("totalQRCode")));
            myUser.setGeoLocationSett((Boolean) data.get("geolocation_setting"));
            myUser.setPlayerId(userID);
            myUser.setQrCodeCollection((ArrayList<String>) data.get("qr_code_ids"));

            getAndSetRank(userID);
            System.out.println("Setting views");

            getAndSetTotalQRCodes(userID);
            System.out.println("Setting Total QR Count.");

            getAndSetTotalScore(userID);

            setHighestLowest(userID, highestQR, lowestQR);

            getAndSetList(userID);
            System.out.println("Setting List");
            setViews();
            }, error -> {
                // Player not found, cannot set values
                // perhaps change to default values for less chance of error during demo
                System.out.println("Error getting player data: " + error);
        });

    }

    /**
     * Sets the user's information to the views.
     */
    public void setViews(){
        playerName.setText(myUser.getUserName());
        playerEmail.setText(myUser.getEmail());
        playerPhoneNumb.setText(myUser.getPhoneNumber());
        playerTotalScore.setText(myUser.getTotalScore().toString());
        playerTotalQRCodes.setText(myUser.getTotalQRCode().toString());
    }

    /**
     * Starts the SettingsActivity and passes the user data to it.
     */
    private void startSettingsActivity() {
        Intent intent = new Intent(getActivity(), SettingActivity.class);
        intent.putExtra("myUser", myUser); // pass the user data to the settings activity
        startSettingsForResult.launch(intent);
    }

    /**
     * Starts the GemActivity and passes the QR code name and index to it.
     *
     * @param name  The name of the selected QR code.
     * @param index The index of the selected QR code in the list.
     */
    private void startGemActivity(String name, Integer index)
    {
        Intent intent = new Intent(getActivity(), GemActivity.class);
        String qr_id = myUser.getQrCodeCollection().get(index);
        intent.putExtra("qr_id", qr_id);
        //intent.putExtra("name", name);
        startGemForResult.launch(intent);
    }

    /**
     * Gets and sets the user's rank based on their ID.
     *
     * @param userID The ID of the user.
     */
    public void getAndSetRank(String userID){
        CompletableFuture<Integer> rankFuture = myPlayerCollection.getPlayerRank(userID);
        rankFuture.thenAccept(rank -> {
            System.out.println("Player rank: " + rank);
            profileRank.setText(rank.toString());
        }).exceptionally(e -> {
            System.err.println("Failed to get player rank: " + e.getMessage());
            return null;
        });
    }

    /**
     * Gets and sets the list of collected QR codes for the user.
     *
     * @param userID The ID of the user.
     */
    public void getAndSetList(String userID){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ArrayList<String> qrCodeCollection = myUser.getQrCodeCollection();
                //ArrayList<String> qrNames = new ArrayList<>();
                //QRCollection qrc = new QRCollection(null);
                //for (String qrCode : qrCodeCollection) {
                    //qrc.read(qrCode, data -> {
                        // qr found
                        //qrNames.add(data.get("name").toString());
                        //if (qrNames.size() == qrCodeCollection.size()) {
                            // All QR names retrieved, update list view
                            //if (qrNames != null){
                            adapter = new QRLIstArrayAdapter(getContext(), qrCodeCollection);
                            listView.setAdapter(adapter);}
                        //}
                    //}, error -> {
                        // qr not found, cannot set values
                        //System.out.println("Error getting player data: " + error);
                    //});

                //}
            //}
        });
    }

    /**
     * Sets the highest and lowest scoring QR codes for the user.
     *
     * @param userID               The ID of the user.
     * @param highestPointsTextView The TextView displaying the highest scoring QR code.
     * @param lowestPointsTextView  The TextView displaying the lowest scoring QR code.
     */
    private void setHighestLowest(String userID, TextView highestPointsTextView, TextView lowestPointsTextView) {
        PlayerCollection pc = new PlayerCollection(null);
        QRCollection qrc = new QRCollection(null);

        pc.read(userID, userData -> {
            List<String> qrIds = (List<String>) userData.get("qr_code_ids");

            if (qrIds != null && !qrIds.isEmpty()) {
                int[] highestPoints = {0};
                int[] lowestPoints = {Integer.MAX_VALUE};
                String[] highestName = {""};
                String[] lowestName = {""};

                int count = qrIds.size();
                AtomicInteger completed = new AtomicInteger(0);

                for (String qrId : qrIds) {
                    qrc.read(qrId, qrData -> {
                        int points = ((Long) qrData.get("points")).intValue();
                        String name = (String) qrData.get("name");

                        if (points >= highestPoints[0]) {
                            highestPoints[0] = points;
                            highestName[0] = name;
                            gem_data[0] = (Map) qrData.get("gem_id");
                        }

                        if (points < lowestPoints[0]) {
                            lowestPoints[0] = points;
                            lowestName[0] = name;
                            gem_data[1] = (Map) qrData.get("gem_id");
                        }

                        int currentCount = completed.incrementAndGet();
                        if (currentCount == count) {
                            highestPointsTextView.setText(highestName[0] + " (" + highestPoints[0] + ")");
                            lowestPointsTextView.setText(lowestName[0] + " (" + lowestPoints[0] + ")");

                            myHighestImage[0].setImageResource((int) (long) gem_data[0].get("bgColor"));
                            myHighestImage[1].setImageResource((int) (long) gem_data[0].get("boarder"));
                            myHighestImage[2].setImageResource((int) (long) gem_data[0].get("lusterLevel"));
                            myHighestImage[3].setImageResource((int) (long) gem_data[0].get("gemType"));

                            myLowestImage[0].setImageResource((int) (long) gem_data[1].get("bgColor"));
                            myLowestImage[1].setImageResource((int) (long) gem_data[1].get("boarder"));
                            myLowestImage[2].setImageResource((int) (long) gem_data[1].get("lusterLevel"));
                            myLowestImage[3].setImageResource((int) (long) gem_data[1].get("gemType"));
                        }
                    }, e -> {
                        // handle error
                        int currentCount = completed.incrementAndGet();
                        if (currentCount == count) {
                            highestPointsTextView.setText("N/A");
                            lowestPointsTextView.setText("N/A");

                            myLowestImage[3].setImageResource(R.drawable.sadfaceemoji);
                            myHighestImage[0].setImageResource(R.drawable.sadfaceemoji);
                        }
                    });
                }
            } else {
                // handle case where user has no qr ids
                highestPointsTextView.setText("N/A");
                lowestPointsTextView.setText("N/A");

                myLowestImage[3].setImageResource(R.drawable.sadfaceemoji);
                myHighestImage[0].setImageResource(R.drawable.sadfaceemoji);
            }
        }, e -> {
            // handle error
            highestPointsTextView.setText("N/A");
            lowestPointsTextView.setText("N/A");

            myLowestImage[3].setImageResource(R.drawable.sadfaceemoji);
            myHighestImage[0].setImageResource(R.drawable.sadfaceemoji);
        });
    }


    private void getAndSetTotalQRCodes(String userID)
    {
        CompletableFuture<Integer> futureCount = myPlayerCollection.countTotalQRCodes(userID);
        futureCount.thenAccept(count -> {
            System.out.println("Player rank: " + count);
            playerTotalQRCodes.setText(count.toString());
            myUser.setTotalQRCode(count.longValue());

        }).exceptionally(e -> {
            System.err.println("Failed to get player rank: " + e.getMessage());
            return null;
        });
    }


    private void getAndSetTotalScore(String userID) {
        myPlayerCollection.calcScore(userID, score -> {
            System.out.println("Total score for user " + userID + ": " + score);

            // Set the score in the UI
            playerTotalScore.setText(String.valueOf(score));

            // Update the user object
            myUser.setTotalScore((long) score);

            // Create the updated values map for the user
            Map<String, Object> values = myPlayerCollection.createValues(userID, myUser.getUserName(), myUser.getPhoneNumber(), myUser.getEmail(), myUser.getGeoLocationFlag(), (int) (long) myUser.getTotalScore(), (int) (long) myUser.getTotalQRCode());

            // Update the user's document in the database with the new total score
            myPlayerCollection.update(userID, values);
        }, error -> {
            System.out.println("Error calculating score: " + error);
        });
    }

}
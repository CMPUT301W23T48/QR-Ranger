package com.example.qrranger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Map;

public class OtherUserGemView extends AppCompatActivity {
    private TextView nameView;
    private TextView qrScore;
    private ImageView gemShape;
    private ImageView backgroundColor;
    private ImageView gemBorder;
    private ImageView gemLustre;
    private Button commentsButton;
    private Button geoLocation;
    private Button usersThatScannedButton;
    private Button backButton;
    private String name;
    private String qr_id;
    private String points;
    private Map gem_data;

    /**
     * Initializes the activity and sets up the views for displaying the gem details.
     *
     * @param savedInstanceState A Bundle containing the saved state of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ou_gemview_view);

        ConstraintLayout cl = findViewById(R.id.gem_layoutOU);
        nameView = cl.findViewById(R.id.gemNameOU);
        qrScore = cl.findViewById(R.id.gemValueOU);
        commentsButton = cl.findViewById(R.id.commentsOU);
        backButton = cl.findViewById(R.id.gem_view_back_buttonOU);
        usersThatScannedButton = cl.findViewById(R.id.users_that_scannedOU);
        gemShape = cl.findViewById(R.id.gemOU);
        backgroundColor = cl.findViewById(R.id.backgroundColorOU);
        gemBorder = cl.findViewById(R.id.borderTypeOU);
        gemLustre = cl.findViewById(R.id.lusterLevelOU);
        geoLocation = cl.findViewById(R.id.geoLocationOU);

        Intent intent = getIntent();
        qr_id = intent.getStringExtra("qr_id");


        QRCollectionController qrc = new QRCollectionController(null);
        qrc.read(qr_id, data -> {
            name = data.get("name").toString();
            points = data.get("points").toString();
            gem_data = (Map) data.get("gem_id");
            setViews();

        }, error -> {
            // qr not found, cannot set values
            System.out.println("Error getting player data: " + error);
        });

        /**
         * Sets a click listener on the geoLocation view object. When the view is clicked,
         * it starts a new activity called geoLocationView.
         */
        geoLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OtherUserGemView.this, geoLocationView.class);
                startActivity(intent);
            }
        });
        /**
         * Sets an onClickListener for the "commentsButton" button. When clicked,
         * starts the "CommentActivity" activity with an intent that includes the
         * "qr_id" string value.
         * @param qr_id the string value of the QR code ID to pass to the CommentActivity
         */
        commentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OtherUserGemView.this, CommentView.class);
                intent.putExtra("qr_id", qr_id);
                startActivity(intent);
            }
        });
        /**
         * Sets a listener to the "usersThatScannedButton" button which starts the UsersThatScannedActivity and
         * passes the qr_id as an extra.
         */
        usersThatScannedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OtherUserGemView.this, UsersThatScannedActivityView.class);
                intent.putExtra("qr_id", qr_id);
                startActivity(intent);
            }
        });

        /**
         * Sets an onClickListener for the backButton View. When the user clicks on the backButton,
         * the activity will finish and the user will be taken back to the previous activity.
         */
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    /**
     * Sets up the views with the appropriate gem details.
     */
    public void setViews(){
        nameView.setText(name);
        qrScore.setText(points);
        gemShape.setImageResource((int) (long) gem_data.get("gemType"));
        backgroundColor.setImageResource((int) (long) gem_data.get("bgColor"));
        gemBorder.setImageResource((int) (long) gem_data.get("boarder"));
        gemLustre.setImageResource((int) (long) gem_data.get("lusterLevel"));

    }


}

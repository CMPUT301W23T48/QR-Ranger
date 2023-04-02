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

/**
 * An Activity class that displays the details of a specific gem, retrieved
 * from a QR code.
 */
public class GemActivity extends AppCompatActivity {

    private TextView nameView;
    private TextView qrScore;
    private ImageView gemShape;
    private ImageView backgroundColor;
    private ImageView gemBorder;
    private ImageView gemLustre;
    private Button deleteButton;
    private Button commentsButton;
    private Button geoLocation;
    private Button usersThatScannedButton;
    private Button backButton;
    private String name;
    private String qr_id;
    private String points;
    private Map gem_data;
    private Boolean deleted = false;

    /**
     * Initializes the activity and sets up the views for displaying the gem details.
     *
     * @param savedInstanceState A Bundle containing the saved state of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_gemview);

        ConstraintLayout cl = findViewById(R.id.gem_layout);
        nameView = cl.findViewById(R.id.gemName);
        qrScore = cl.findViewById(R.id.gemValue);
        deleteButton = cl.findViewById(R.id.delete);
        commentsButton = cl.findViewById(R.id.comments);
        backButton = cl.findViewById(R.id.gem_view_back_button);
        usersThatScannedButton = cl.findViewById(R.id.users_that_scanned);
        gemShape = cl.findViewById(R.id.gem);
        backgroundColor = cl.findViewById(R.id.backgroundColor);
        gemBorder = cl.findViewById(R.id.borderType);
        gemLustre = cl.findViewById(R.id.lusterLevel);
        geoLocation = cl.findViewById(R.id.geoLocation);

        Intent intent = getIntent();
        //name = intent.getStringExtra("name");
        qr_id = intent.getStringExtra("qr_id");


        QRCollection qrc = new QRCollection(null);
        qrc.read(qr_id, data -> {
            name = data.get("name").toString();
            points = data.get("points").toString();
            gem_data = (Map) data.get("gem_id");
            setViews();

        }, error -> {
            // qr not found, cannot set values
            System.out.println("Error getting player data: " + error);
        });


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // need to add array adapter to handle delete
                // error on return from activity
//                deleted = true;
//                Intent returnIntent = new Intent();
//                returnIntent.putExtra("dataDeleted", deleted);
//                returnIntent.putExtra("qr_id", qr_id); // Pass the modified object back to the first activity
//                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
        geoLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GemActivity.this, geoLocationView.class);
                startActivity(intent);
            }
        });

        commentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GemActivity.this, CommentActivity.class);
                intent.putExtra("qr_id", qr_id);
                startActivity(intent);
            }
        });

        usersThatScannedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GemActivity.this, UsersThatScannedActivity.class);
                intent.putExtra("qr_id", qr_id);
                startActivity(intent);
            }
        });

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

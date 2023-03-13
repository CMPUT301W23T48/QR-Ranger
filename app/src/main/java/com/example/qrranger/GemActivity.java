package com.example.qrranger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.HashMap;
import java.util.Map;


public class GemActivity extends AppCompatActivity {

    private TextView nameView;
    private TextView qrScore;
    private ImageView gemShape;
    private ImageView backgroundColor;
    private ImageView gemBorder;
    private ImageView gemLustre;
    private Button deleteButton;
    private String name;
    private String qr_id;
    private String points;
    private Map gem_data;
    private Boolean deleted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_gemview);

        ConstraintLayout cl = findViewById(R.id.gem_layout);
        nameView = cl.findViewById(R.id.gemName);
        qrScore = cl.findViewById(R.id.gemValue);
        deleteButton = cl.findViewById(R.id.delete);
        gemShape = cl.findViewById(R.id.gem);
        backgroundColor = cl.findViewById(R.id.backgroundColor);
        gemBorder = cl.findViewById(R.id.borderType);
        gemLustre = cl.findViewById(R.id.lusterLevel);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        qr_id = intent.getStringExtra("qr_id");


        QRCollection qrc = new QRCollection(null);
        qrc.read(qr_id, data -> {
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
//                deleted = true;
//                Intent returnIntent = new Intent();
//                returnIntent.putExtra("dataDeleted", deleted);
//                returnIntent.putExtra("qr_id", qr_id); // Pass the modified object back to the first activity
//                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });


    }


    public void setViews(){
        nameView.setText(name);
        qrScore.setText(points);
        gemShape.setImageResource((int) (long) gem_data.get("gemType"));
        backgroundColor.setImageResource((int) (long) gem_data.get("bgColor"));
        gemBorder.setImageResource((int) (long) gem_data.get("boarder"));
        gemLustre.setImageResource((int) (long) gem_data.get("lusterLevel"));

    }

}

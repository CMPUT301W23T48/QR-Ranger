package com.example.qrranger;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class QRLIstArrayAdapter extends ArrayAdapter<String> {

    private QRCollection qrCollection = new QRCollection(null);
    private QRCode qrCode = new QRCode();
    private Map gem_data;
    public QRLIstArrayAdapter(Context context, ArrayList<String> qrID){
        super(context, 0, qrID);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View view;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.qrlistcontent_view, parent, false);
        }else{
            view = convertView;
        }
        String id = getItem(position);

        TextView qrName = view.findViewById(R.id.qrName);
        ImageView qrShape = view.findViewById(R.id.shape);
        ImageView qrLustre = view.findViewById(R.id.luster);
        ImageView qrBorder = view.findViewById(R.id.border);
        ImageView qrBackGround = view.findViewById(R.id.background);

        qrCollection.read(id, data ->{
            String qrId = Objects.requireNonNull(data.get("name").toString());
            //String name = Objects.requireNonNull(data.get("name").toString());
            //String url = Objects.requireNonNull(data.get("url").toString());
            //Integer points = (Integer) data.get("points");
            //gemID gem = (gemID) data.get("gem_id");

            qrCode.setName(qrId);
            gem_data = (Map) data.get("gem_id");
            //qrCode.setPoints(points);
            //qrCode.setUrl(url);
            //qrCode.setGemId(gem);

            qrName.setText(qrCode.getName());
//            qrShape.setImageResource(qrCode.getGemID().getGemType());
//            qrLustre.setImageResource(qrCode.getGemID().getLusterLevel());
//            qrBorder.setImageResource(qrCode.getGemID().getBoarder());
//            qrBackGround.setImageResource(qrCode.getGemID().getBgColor());


            qrBackGround.setImageResource((int) (long) gem_data.get("bgColor"));
            qrBorder.setImageResource((int) (long) gem_data.get("boarder"));
            qrLustre.setImageResource((int) (long) gem_data.get("lusterLevel"));
            qrShape.setImageResource((int) (long) gem_data.get("gemType"));
        }, error -> {
            Log.e(TAG, "Error when loading QR from database.");
        });


        return view;
    }
}

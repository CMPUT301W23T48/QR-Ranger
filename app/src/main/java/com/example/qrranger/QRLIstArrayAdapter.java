package com.example.qrranger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class QRLIstArrayAdapter extends ArrayAdapter<String> {
    public QRLIstArrayAdapter(Context context, List<String> qrID){
        super(context, 0, qrID);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View view;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.qrlistcontent, parent, false);
        }else{
            view = convertView;
        }
        String id = getItem(position);
        TextView qrName = view.findViewById(R.id.qrName);
        ImageView qrShape = view.findViewById(R.id.shape);

        qrName.setText(id);
        qrShape.setImageResource(R.drawable.bluegem1);

        return view;
    }
}

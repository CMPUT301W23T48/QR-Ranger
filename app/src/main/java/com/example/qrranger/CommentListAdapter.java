package com.example.qrranger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CommentListAdapter extends ArrayAdapter<Comment> {
    private final LayoutInflater inflater;

    public CommentListAdapter(@NonNull Context context, List<Comment> comments) {
        super(context, 0, comments);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_comment_list_item, parent, false);
        }

        Comment comment = getItem(position);
        TextView commentAuthor = convertView.findViewById(R.id.comment_author);
        TextView commentText = convertView.findViewById(R.id.comment_text);

        if (comment != null) {
            commentAuthor.setText(comment.getAuthor());
            commentText.setText(comment.getComment());
        }

        return convertView;
    }
}

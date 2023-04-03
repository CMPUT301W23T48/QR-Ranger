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

/**
 * CommentListAdapter is a custom ArrayAdapter for displaying comments in a ListView.
 * It takes a list of Comment objects and binds the data to the corresponding views.
 */
public class CommentListAdapter extends ArrayAdapter<CommentModel> {
    private final LayoutInflater inflater;

    /**
     * Constructs a CommentListAdapter with the specified context and list of comments.
     * @param context The current context.
     * @param comments The list of Comment objects to be displayed.
     */
    public CommentListAdapter(@NonNull Context context, List<CommentModel> comments) {
        super(context, 0, comments);
        inflater = LayoutInflater.from(context);
    }

    /**
     * Gets a View that displays the data at the specified position in the data set.
     * @param position The position of the item within the adapter's data set.
     * @param convertView The old view to reuse, if possible.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return A View corresponding to the data at the specified position.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.comment_list_item_view, parent, false);
        }

        CommentModel comment = getItem(position);
        TextView commentAuthor = convertView.findViewById(R.id.comment_author);
        TextView commentText = convertView.findViewById(R.id.comment_text);

        if (comment != null) {
            commentAuthor.setText(comment.getAuthor());
            commentText.setText(comment.getComment());
        }

        return convertView;
    }
}

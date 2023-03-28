package com.example.qrranger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * CommentActivity allows users to view, add, and delete comments related to a specific QR code.
 * This activity fetches comments from the Firestore database, and updates the UI accordingly.
 */
public class CommentActivity extends AppCompatActivity {

    private ListView commentsListView;
    private CommentListAdapter commentListAdapter;
    private List<Comment> commentsList;
    private FirebaseFirestore firebaseFirestore;
    private String QR_ID;
    private Button backButton;
    private Button addButton;

    /**
     * Initializes the activity and sets up UI components and event listeners.
     * @param savedInstanceState A bundle to pass data between activities.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        Intent intent = getIntent();
        QR_ID = intent.getStringExtra("qr_id");
        LinearLayout ll = findViewById(R.id.comments_ll);
        commentsListView = findViewById(R.id.comments_list_view);
        commentsList = new ArrayList<>();
        commentListAdapter = new CommentListAdapter(this, commentsList);
        commentsListView.setAdapter(commentListAdapter);
        backButton = ll.findViewById(R.id.comment_back_button);
        addButton = ll.findViewById(R.id.comment_add_button);

        firebaseFirestore = FirebaseFirestore.getInstance();

        fetchCommentsByQRId(QR_ID);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddCommentDialog();
            }
        });

        commentsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Comment clickedComment = commentsList.get(position);
                UserState us = UserState.getInstance();
                String userID = us.getUserID();

                if (clickedComment.getAuthorID().equals(userID)) {
                    showDeleteCommentDialog(clickedComment);
                }
            }
        });
    }

    /**
     * Fetches comments associated with the specified QR_ID from the Firestore database.
     * @param qrId The ID of the QR code for which comments are to be fetched.
     */
    private void fetchCommentsByQRId(String qrId) {
        Query query = firebaseFirestore.collection("comments").whereEqualTo("QR_ID", qrId);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Comment comment = document.toObject(Comment.class);
                        commentsList.add(comment);
                    }
                    commentListAdapter.notifyDataSetChanged();
                } else {
                    // Handle the error
                }
            }
        });
    }

    /**
     * Displays a dialog to input a new comment.
     */
    private void showAddCommentDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.popup_comment_input, null);
        final EditText commentInput = view.findViewById(R.id.comment_input);

        builder.setView(view)
                .setTitle("Add Comment")
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String commentText = commentInput.getText().toString().trim();
                        if (!commentText.isEmpty()) {
                            // Save the comment to the database
                            // have QR_ID
                            saveComment(commentText);
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Saves a new comment to the Firestore database and updates the UI.
     * @param commentText The text of the comment to be saved.
     */
    private void saveComment(String commentText) {
        CommentCollection cc = new CommentCollection(null);
        PlayerCollection pc = new PlayerCollection(null);
        UserState us = UserState.getInstance();
        String userID = us.getUserID();
        pc.read(userID, data -> {
            String username = data.get("username").toString();
            Map<String, Object> values = cc.createValues(QR_ID, username, userID, commentText);
            cc.create(values);

            // Add a new Comment object to the commentsList
            Comment newComment = new Comment();
            newComment.setAuthorID(userID);
            newComment.setQr_id(QR_ID);
            newComment.setAuthor(username);
            newComment.setComment(commentText);
            commentsList.add(newComment);

            // Notify the adapter that the data has changed
            commentListAdapter.notifyDataSetChanged();
        }, error -> {
            System.out.println("Error getting player data: " + error);
        });
    }

    /**
     * Displays a dialog to confirm the deletion
     * of a comment.
     * @param comment The Comment object to be deleted.
     **/
    private void showDeleteCommentDialog(final Comment comment) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Delete Comment")
                .setMessage("Are you sure you want to delete this comment?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        deleteComment(comment);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Deletes a comment from the Firestore database and updates the UI.
     * @param comment The Comment object to be deleted.
     */
    private void deleteComment(Comment comment) {
        CommentCollection cc = new CommentCollection(null);
        cc.delete(comment.getAuthorID());
        commentsList.remove(comment);
        commentListAdapter.notifyDataSetChanged();
    }


}
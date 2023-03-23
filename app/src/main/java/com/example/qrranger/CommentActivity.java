package com.example.qrranger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity {

    private ListView commentsListView;
    private CommentListAdapter commentListAdapter;
    private List<Comment> commentsList;
    private FirebaseFirestore firebaseFirestore;
    private String qrId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        Intent intent = getIntent();
        qrId = intent.getStringExtra("qr_id");

        commentsListView = findViewById(R.id.comments_list_view);
        commentsList = new ArrayList<>();
        commentListAdapter = new CommentListAdapter(this, commentsList);
        commentsListView.setAdapter(commentListAdapter);

        firebaseFirestore = FirebaseFirestore.getInstance();

        fetchCommentsByQRId(qrId);
    }

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
}

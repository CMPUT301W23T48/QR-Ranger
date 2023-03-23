package com.example.qrranger;

public class Comment {

    private String comment_ID;
    private String QR_ID;
    private String author;
    private String comment;

    // empty constructor
    public Comment(){
    }

    // setters
    public void setComment_id(String comment_id){ this.comment_ID = comment_id; }
    public void setQr_id(String QR_id) { this.QR_ID = QR_id; }
    public void setAuthor(String authorName) { this.author = authorName; }
    public void setComment(String comment) { this.comment = comment; }

    // getters
    public String getComment_id() { return this.comment_ID; }
    public String getQR_id() { return this.QR_ID; }
    public String getAuthor() { return this.author; }
    public String getComment() { return this.comment; }
}

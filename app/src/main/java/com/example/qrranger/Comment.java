package com.example.qrranger;

/**
 * This class represents a Comment object with information about the author, the associated QR code, and the comment text.
 */
public class Comment {

    private String authorID;
    private String QR_ID;
    private String author;
    private String comment;

    /**
     * Default empty constructor for creating a Comment object.
     */
    public Comment(){
    }

    /**
     * Sets the author's ID for this comment.
     * @param authorID The author's ID as a String.
     */
    public void setAuthorID(String authorID){ this.authorID = authorID; }

    /**
     * Sets the QR code ID associated with this comment.
     * @param QR_ID The QR code ID as a String.
     */
    public void setQr_id(String QR_ID) { this.QR_ID = QR_ID; }

    /**
     * Sets the author's name for this comment.
     * @param authorName The author's name as a String.
     */
    public void setAuthor(String authorName) { this.author = authorName; }

    /**
     * Sets the text of this comment.
     * @param comment The comment text as a String.
     */
    public void setComment(String comment) { this.comment = comment; }


    /**
     * Returns the author's ID for this comment.
     * @return The author's ID as a String.
     */
    public String getAuthorID() { return this.authorID; }

    /**
     * Returns the QR code ID associated with this comment.
     * @return The QR code ID as a String.
     */
    public String getQR_id() { return this.QR_ID; }

    /**
     * Returns the author's name for this comment.
     * @return The author's name as a String.
     */
    public String getAuthor() { return this.author; }

    /**
     * Returns the text of this comment.
     * @return The comment text as a String.
     */
    public String getComment() { return this.comment; }
}

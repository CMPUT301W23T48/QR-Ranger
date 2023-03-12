package com.example.qrranger;


public class UserState {
    private static UserState single_instance = null;
    private String userID;

    private UserState(){
    }

    // returns the instance of the database
    public static synchronized UserState getInstance()
    {
        if (single_instance == null)
        {
            single_instance = new UserState();
        }
        return single_instance;
    }

    public void setUserID(String userID){
        this.userID = userID;
    }

    public String getUserID(){
        return this.userID;
    }
}
// usage:
// UserState us = UserState.getInstance();
// us.setUserID("example");
// String example = us.getUserID();

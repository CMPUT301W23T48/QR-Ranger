package com.example.qrranger;

/**
 * UserState is a singleton class that holds the state of the current user.
 * It provides methods to set and get the user ID.
 */
public class UserState {
    private static UserState single_instance = null;
    private String userID;

    /**
     * Private constructor to prevent instantiation from other classes.
     */
    private UserState(){
    }

    /**
     * Returns the single instance of the UserState class.
     * If an instance doesn't exist, it creates one.
     *
     * @return The single instance of the UserState class.
     */
    public static synchronized UserState getInstance()
    {
        if (single_instance == null)
        {
            single_instance = new UserState();
        }
        return single_instance;
    }

    /**
     * Sets the user ID for the current user.
     *
     * @param userID The user ID to set.
     */
    public void setUserID(String userID){
        this.userID = userID;
    }

    /**
     * Returns the user ID of the current user.
     *
     * @return The user ID of the current user.
     */
    public String getUserID(){
        return this.userID;
    }
}
// usage:
// UserState us = UserState.getInstance();
// us.setUserID("example");
// String example = us.getUserID();

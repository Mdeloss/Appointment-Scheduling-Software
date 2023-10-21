package model;

/** This class holds the attributes for Users .*/

public class User {

    private int userID;
    private String userName;
    private String password;

    /** This constructor is for the User object. */
    public User(int userID, String userName, String password) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;

    }

    /** @param userID sets userID */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /** @param password sets password */
    public void setPassword(String password) {
        this.password = password;
    }

    /** @param userName sets username */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** @return the userID */
    public int getUserID() {
        return userID;
    }
    /** @return the password */
    public String getPassword() {
        return password;
    }
    /** @return the userName */
    public String getUserName() {
        return userName;
    }
}

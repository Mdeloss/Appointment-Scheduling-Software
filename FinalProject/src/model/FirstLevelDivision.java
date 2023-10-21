package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This class holds the attributes for FirstLevelDivision .*/

public class FirstLevelDivision {

    private int id, countryID;
    private String state;


    /** This constructor is for the FirstLevelDivision object. */
    public FirstLevelDivision(int id, String state, int countryID) {

        this.id = id;
        this.state = state;
        this.countryID = countryID;

    }
    /** @param id sets id */
    public void setId(int id) {this.id = id;}
    /** @return the id */
    public int getId() {return id;}

    /** @param state sets state */
    public void setState(String state) {this.state = state;}
    /** @return the state */
    public String getState() {return state;}

    /** @param countryID sets countryID */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }
    /** @return the countryID */
    public int getCountryID() {
        return countryID;
    }
}

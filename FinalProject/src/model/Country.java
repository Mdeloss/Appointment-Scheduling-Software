package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This class holds the attributes for Country .*/

public class Country {

    private int id;
    private String name;

    /** This constructor is for the Country object. */
    public Country(int id, String name) {

        this.id = id;
        this.name = name;
    }

    /** @param id sets id */
    public void setId(int id) {this.id = id;}
    /** @return the id */
    public int getId() {return id;}

    /** @param name sets name */
    public void setName(String name) {this.name = name;}
    /** @return the name */
    public String getName() {return name;}


}

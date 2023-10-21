package model;

import java.time.LocalDateTime;

/** This class holds the attributes for Appointment.*/

public class Appointment {

    private int apptID, customerID, userID, contactID;
    private String title, description, location, type;
    private LocalDateTime start,  end;

    /** This constructor is for the Appointment object. */
    public Appointment(int apptID, int customerID, int userID, int contactID, String title, String description, String location,
                        String type, LocalDateTime start, LocalDateTime end ) {

        this.apptID = apptID;
        this.customerID = customerID;
        this.userID = userID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contactID = contactID;
        this.type = type;
        this.start = start;
        this.end = end;

    }

    /** @param apptID sets appointment ID */
    public void setApptID(int apptID) {
        this.apptID = apptID;
    }

    /** @return the Appointment ID */
    public int getApptID() {
        return apptID;
    }

    /** @param userID sets userID */
    public void setUserID(int userID) {
        this.userID = userID;
    }
    /** @return the userID */
    public int getUserID() {
        return userID;
    }

    /** @param customerID sets customerID */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
    /** @return the customerID */
    public int getCustomerID() { return customerID; }

    /** @param title sets title */
    public void setTitle(String title) {
        this.title = title;
    }

    /** @return the title*/
    public String getTitle() {
        return title;
    }

    /** @param description sets description */
    public void setDescription(String description) {
        this.description = description;
    }

    /** @return the description */
    public String getDescription() {
        return description;
    }

    /** @param location sets location */
    public void setLocation(String location) {
        this.location = location;
    }

    /** @return the location */
    public String getLocation() {
        return location;
    }

    /** @param contactID sets contactID */
    public void setContactID(int contactID) { this.contactID = contactID; }

    /** @return the contactID */
    public int getContactID() {
        return contactID;
    }

    /** @param type sets type */
    public void setType(String type) {
        this.type = type;
    }

    /** @return the type */
    public String getType() {
        return type;
    }

    /** @param start sets start */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /** @return the start */
    public LocalDateTime getStart() {
        return start;
    }
    /** @param end sets end */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
    /** @return the end */
    public LocalDateTime getEnd() {
        return end;
    }
}

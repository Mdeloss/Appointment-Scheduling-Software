package model;

/** This class holds the attributes for Contacts .*/

public class Contact {

    private int id;
    private String name;
    private String email;


    public Contact(int id, String name, String email) {

        this.id = id;
        this.name = name;
        this.email = email;

    }

    /** @param id sets id */
    public void setId(int id) {this.id = id;}
    /** @return the id */
    public int getId() {return id;}

    /** @param name sets name */
    public void setName(String name) {this.name = name;}
    /** @return the name */
    public String getName() {return name;}

    /** @param email sets email */
    public void setEmail(String email) { this.email = email; }
    /** @return the email */
    public String getEmail() { return email; }
}

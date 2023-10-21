package Utilities;

import javafx.collections.ObservableList;
import model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/** This class holds the attributes for Query .*/

public abstract class Query {


    /** @param u username to set
     * @param p password to set */
    // Login Query fills input for username and password
    public static boolean login(String u, String p) throws SQLException {
        String sql = "SELECT * FROM client_schedule.users WHERE User_Name = '" + u + "' AND Password = '" + p + "'";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs;
        int row;

        // Find rows matching results, then prints data.
        rs = ps.executeQuery();
        rs.next();
        row = rs.getRow();

        System.out.println("Row found: " + row);

        if (row != 0) {

                String un = rs.getString("User_Name");
                String pw = rs.getString("Password");
                int userId = rs.getInt("User_ID");

                if (un.contains(u) && pw.contains(p)) {
                    System.out.println(userId + " | " + un + " | " + pw);

                    return true;

                }
                return false;

        }
        else {
            System.out.println("No match");

            return false;

        }

    }

    /** @param month month to set
     * @param type type to set */
    // Reports
    public static int selectTotalAppointments(int month, String type ) throws SQLException {
        String sql = "SELECT count(*) FROM client_schedule.appointments WHERE month(Start)= '" + month +
                "' and Type= '" + type + "'";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        int count = 0;
        
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            
            count = rs.getInt(1);
        }
        return count;

    }
    /** @param type type to set */
    public static int selectTodayAppointments(String type ) throws SQLException {
        String sql = "SELECT count(*) FROM client_schedule.appointments WHERE date(Start)=curdate() and " +
                "Type= '" + type + "'";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        int count = 0;

        ResultSet rs = ps.executeQuery();
        while (rs.next()){

            count = rs.getInt(1);
        }
        return count;

    }

    // ---------------------------------------------------------------------//

    /** This calls the SELECT query for Countries
    * @return the list of Countries */
    // SELECT all Countries
    public static ObservableList<Country> selectCountries() throws SQLException {
        int id;
        String name;

        // Makes sure data is not repopulated
        Records.getAllCountries().clear();

        String sql = "SELECT * FROM client_schedule.countries";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();


        while (rs.next()) {
            id = rs.getInt("Country_ID");
            name = rs.getString("Country");

            Country object = new Country(id, name);
            Records.addCountries(object);

        }

        return Records.getAllCountries();
    }

    /** This calls the SELECT query for Customers
    * @return the list of Customer */
    // SELECT all Customers
    public static ObservableList<Customer> selectCustomers() throws SQLException {

        int id;
        String name;
        String address;
        String postal_code;
        String phone;
        int div_id;

        Records.getAllCustomers().clear();

        String sql = "SELECT * FROM client_schedule.customers";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();


        while (rs.next()) {
            id = rs.getInt("Customer_ID");
            name = rs.getString("Customer_Name");
            address = rs.getString("Address");
            postal_code = rs.getString("Postal_Code");
            phone = rs.getString("Phone");
            div_id = rs.getInt("Division_ID");

            Customer object = new Customer(id, name, address, postal_code, phone, div_id);
            Records.addCustomer(object);

        }

        return Records.getAllCustomers();
    }

    /** This calls the SELECT query for User
     * @return the list of User */
    // SELECT all Users
    public static ObservableList<User> selectUsers() throws SQLException {
        int id;
        String name;
        String password;

        // Makes sure data is not repopulated
        Records.getAllUsers().clear();


        String sql = "SELECT * FROM client_schedule.users";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();


        while (rs.next()) {
            id = rs.getInt("User_ID");
            name = rs.getString("User_Name");
            password = rs.getString("Password");

            User object = new User(id, name, password);
            Records.addUser(object);

        }

        return Records.getAllUsers();
    }

    /** This calls the SELECT query for Division
     * @return the list of Division */
    // SELECT all Divisions
    public static ObservableList<FirstLevelDivision> selectDivisions() throws SQLException {
        int id;
        String state;
        int countryID;

        // Makes sure data is not repopulated
        Records.getAllDivisions().clear();

        String sql = "SELECT * FROM client_schedule.first_level_divisions";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();


        while (rs.next()) {
            id = rs.getInt("Division_ID");
            state = rs.getString("Division");
            countryID = rs.getInt("Country_ID");

            FirstLevelDivision object = new FirstLevelDivision(id, state, countryID);
            Records.addDivisions(object);

        }

        return Records.getAllDivisions();
    }

    /** This calls the SELECT query for Contacts
     * @return the list of Contacts */
    // SELECT all Contacts
    public static ObservableList<Contact> selectContacts() throws SQLException {
        int id;
        String name;
        String email;

        // Makes sure data is not repopulated
        Records.getAllContacts().clear();

        String sql = "SELECT * FROM client_schedule.contacts";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();


        while (rs.next()) {
            id = rs.getInt("Contact_ID");
            name = rs.getString("Contact_Name");
            email = rs.getString("Email");

            Contact object = new Contact(id, name, email);
            Records.addContacts(object);

        }

        return Records.getAllContacts();
    }

    /** This calls the SELECT query for Appointments
     * @return the list of Appointments */
    // SELECT all Appointments
    public static ObservableList<Appointment> selectAppointments() throws SQLException {

        int id;
        int userID;
        int contactID;
        int customerID;
        String title;
        String description;
        String location;
        String type;
        Timestamp startTime;
        Timestamp endTime;


        // Makes sure data is not repopulated
        Records.getAllAppointments().clear();


        String sql = "SELECT * FROM client_schedule.appointments";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();


        while (rs.next()) {
            id = rs.getInt("Appointment_ID");
            userID = rs.getInt("User_ID");
            contactID = rs.getInt("Contact_ID");
            customerID = rs.getInt("Customer_ID");
            title = rs.getString("Title");
            description = rs.getString("Description");
            location = rs.getString("Location");
            type = rs.getString("Type");
            startTime = rs.getTimestamp("Start");
            endTime = rs.getTimestamp("End");

            // Converts from Timestamp to LocalDateTime
            LocalDateTime startLDT = startTime.toLocalDateTime();
            LocalDateTime endLDT = endTime.toLocalDateTime();

            Appointment object = new Appointment(id, customerID, userID, contactID, title, description, location, type,
                    startLDT, endLDT );

            Records.addAppointment(object);

        }

        return Records.getAllAppointments();
    }

    // --------------------------------------------//

    //INSERT Statements

    /** This calls the INSERT query for Appointments
     * @return number of rows affected */
    // INSERT Appointment
    public static int insert(int Appointment_ID, String Title, String Description, String Location, String Type,
                             Timestamp Start, Timestamp End, int Customer_ID, int User_ID, int Contact_ID) throws SQLException
    {

        /*
        int apptID = 0, customerID = 0, userID =0, contactID = 0;
        String title= null, description= null, location= null, type = null ;
        LocalDateTime start= null, end= null;

        Appointment object = new Appointment( apptID, customerID, userID, contactID, title, description, location,
                type, start, end );

        Records.getAllAppointments();

        int id = object.getApptID();
        int userID;
        int contactID;
        int customerID;
        String title;
        String description;
        String location;
        String type;
        Timestamp startTime;
        Timestamp endTime;

         */

        String sql = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, Appointment_ID);
        ps.setString(2, Title);
        ps.setString(3, Description);
        ps.setString(4, Location);
        ps.setString(5, Type);
        ps.setTimestamp(6, Start);
        ps.setTimestamp(7, End);
        ps.setInt(8, Customer_ID);
        ps.setInt(9, User_ID);
        ps.setInt(10, Contact_ID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /** This calls the INSERT query for Appointments
    * @return number of rows affected */
    // INSERT Customer
    public static int insert(int id, String name, String address, String postal_code, String phone, int div_id) throws SQLException
    {

        /*
        int apptID = 0, customerID = 0, userID =0, contactID = 0;
        String title= null, description= null, location= null, type = null ;
        LocalDateTime start= null, end= null;

        Appointment object = new Appointment( apptID, customerID, userID, contactID, title, description, location,
                type, start, end );

        Records.getAllAppointments();

        int id = object.getApptID();
        int userID;
        int contactID;
        int customerID;
        String title;
        String description;
        String location;
        String type;
        Timestamp startTime;
        Timestamp endTime;

         */

        String sql = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES(?,?,?,?,?,?)";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setString(3, address);
        ps.setString(4, postal_code);
        ps.setString(5, phone);
        ps.setInt(6, div_id);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    // ----------------------------------------------//

    //UPDATE Statements

    /** This calls the UPDATE query for Appointments
    * @return number of rows affected */
    // UPDATE Appointment
    public static int update(int Appointment_ID, String Title, String Description, String Location, String Type,
                             Timestamp Start, Timestamp End, int Customer_ID, int User_ID, int Contact_ID) throws SQLException
    {


        String sql = "UPDATE appointments SET Title=?, Description=?, Location=?, Type=?, Start=?," +
                " End=?, Customer_ID=?, User_ID=?, Contact_ID=? WHERE Appointment_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(10, Appointment_ID);
        ps.setString(1, Title);
        ps.setString(2, Description);
        ps.setString(3, Location);
        ps.setString(4, Type);
        ps.setTimestamp(5, Start);
        ps.setTimestamp(6, End);
        ps.setInt(7, Customer_ID);
        ps.setInt(8, User_ID);
        ps.setInt(9, Contact_ID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /** This calls the UPDATE query for Customers
     * @return number of rows affected */
    // UPDATE Customer
    public static int update(int id, String name, String address, String postal_code, String phone, int div_id) throws SQLException
    {


        String sql = "UPDATE customers SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Division_ID=? WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(6, id);
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postal_code);
        ps.setString(4, phone);
        ps.setInt(5, div_id);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }


    // -------------------------------------------//

    //DELETE Statements

    /** This calls the DELETE query for Appointments
     * @return number of rows affected */
    // Delete Appointment
    public static int deleteAppointment(int Appointment_ID) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_Id = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, Appointment_ID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /** This calls the DELETE query for Associated Appointments
     * @return number of rows affected */
    // Delete Customer's Appointments
    public static int deleteAssocAppt(int Customer_ID) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Customer_Id = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, Customer_ID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /** This calls the DELETE query for Customers
     * @return number of rows affected */
    // Delete Customer
    public static int deleteCustomer(int Customer_ID) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_Id = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, Customer_ID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    //--------------------------------------------------------------------//

   

}

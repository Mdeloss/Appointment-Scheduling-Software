package Utilities;


import javafx.collections.ObservableList;
import model.Appointment;
import model.Country;
import model.Customer;
import model.Records;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLException;

/** This class holds the database queries. */

public class Implementation {


    public static void mySQLCalls() throws SQLException {

        // SELECT calls
        Query.selectCountries();
        Query.selectUsers();
        Query.selectDivisions();
        Query.selectContacts();
        Query.selectAppointments();
        Query.selectCustomers();

    }

}

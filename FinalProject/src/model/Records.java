package model;

import controller.AddCustomer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.*;
import java.time.chrono.ChronoLocalDateTime;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;

/** This class holds the attributes and lists for Customer, Appointment, Country, Contact,
 * FirstLevelDivision, and User .*/

public class Records {

    // Observable Lists
     private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
     private static ObservableList<Customer> filteredCustomers = FXCollections.observableArrayList();

     private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
     private static ObservableList<Appointment> filteredAppointments = FXCollections.observableArrayList();

    private static ObservableList<Country> allCountries = FXCollections.observableArrayList();
    private static ObservableList<Country> filteredCountries = FXCollections.observableArrayList();

    private static ObservableList<FirstLevelDivision> allDivisions = FXCollections.observableArrayList();
    private static ObservableList<FirstLevelDivision> filteredDivisions = FXCollections.observableArrayList();

    private static ObservableList<Contact> allContacts = FXCollections.observableArrayList();
    private static ObservableList<Contact> filteredContacts = FXCollections.observableArrayList();

    private static ObservableList<User> allUsers = FXCollections.observableArrayList();
    private static ObservableList<User> filteredUsers = FXCollections.observableArrayList();


    //------------------------------------------------------------------//

    // Customers Methods

    /** @return the list of customers */
    public static ObservableList<Customer> getAllCustomers() { return allCustomers; }
    /** @return the list of filtered customers */
    public static ObservableList<Customer> getAllFilteredCustomers() {
        return filteredCustomers;
    }
    /** @param custName customer name to search */
    public static ObservableList<Customer> lookupCustomers(String custName) {
            // This makes sure data is not repopulated.
            if (!(Records.getAllFilteredCustomers().isEmpty()))
                Records.getAllFilteredCustomers().clear();

            for (Customer object : Records.getAllCustomers())
            {
                if (object.getName().contains(custName))
                    Records.getAllFilteredCustomers().add(object);
            }
            // This lets all data populate if not found
            if (Records.getAllFilteredCustomers().isEmpty())
                return Records.getAllCustomers();
            else
                return Records.getAllFilteredCustomers();
        }
    /** @param custId customer ID to search */
    public static Customer lookupCustomers(int custId) {
        // This makes sure data ia not repopulated.
        if (!(Records.getAllFilteredCustomers().isEmpty()))
            Records.getAllFilteredCustomers().clear();

        for (Customer object : Records.getAllCustomers()) {
            if (object.getId() == custId)
                return object;
        }
            return null;
    }
    /** @param newCust customer to add to list of customers */
    public static void addCustomer(Customer newCust) {
        allCustomers.add(newCust);
    }

    /** @param index ID to search
     * @param selectedCust customer object to set */
    public static void updateCustomer(int index, Customer selectedCust) {

        int id = -1;
        for (Customer object : Records.getAllCustomers()) {
            id++;

            if (object.getId() == index) {
                Records.getAllCustomers().set(id, selectedCust);
            }
        }
    }
    /** @param selectedCust customer to delete */
    public static boolean deleteCust(int selectedCust) {

        for (Customer object : getAllCustomers()) {
            if (object.getId() == selectedCust)
                return Records.getAllCustomers().remove(object);
        }
        return false;
    }



    // ------------------------------------------------------------------//

    // Appointments methods

    /** @return the list of Appointments */
    public static ObservableList<Appointment> getAllAppointments() {
        return allAppointments;
    }
    /** @return the list of filtered Appointments */
    public static ObservableList<Appointment> getAllFilteredAppointments() {
        return filteredAppointments;
    }
    /** @param apptTitle appointment title to search */
    public static ObservableList<Appointment> lookupAppointment(String apptTitle) {
        // This makes sure data ia not repopulated.
            if (!(Records.getAllFilteredAppointments().isEmpty()))
                Records.getAllFilteredAppointments().clear();

            for (Appointment object : Records.getAllAppointments())
            {
                if (object.getTitle().contains(apptTitle))
                    Records.getAllFilteredAppointments().add(object);
            }
            // This lets all data populate if not found
            if (Records.getAllFilteredAppointments().isEmpty())
                return Records.getAllAppointments();
            else
                return Records.getAllFilteredAppointments();
}
    /** @param newAppointment Appointment to add to list of Appointments */
    public static void addAppointment(Appointment newAppointment) {
        allAppointments.add(newAppointment);
    }
    /** @param apptID Appointment ID to search */
    public static Appointment lookupAppointment(int apptID) {
        // This makes sure data ia not repopulated.
        if (!(Records.getAllFilteredAppointments().isEmpty()))
            Records.getAllFilteredAppointments().clear();

        for (Appointment object : Records.getAllAppointments()) {
            if (object.getApptID() == apptID)
                return object;
        }
        return null;
    }
    /** @param index ID to search
     * @param selectedAppt Appointment object to set */
    public static void updateAppointment(int index, Appointment selectedAppt) {

        int id = -1;
        for (Appointment object : Records.getAllAppointments()) {
            id++;

            if (object.getApptID() == index) {
                Records.getAllAppointments().set(id, selectedAppt);
            }
        }
    }
    /** @param selectedAppt Appointment to delete */
    public static boolean deleteAppointment(int selectedAppt) {
        for (Appointment object : getAllAppointments()) {
            if (object.getApptID() == selectedAppt)
                return Records.getAllAppointments().remove(object);
        }
        return false;
    }


    /** @param input Month input to search */
    // Appointment Month Dates
    public static ObservableList<Appointment> monthViewAppointment(Month input) {

        Month m = null;

        // This makes sure data ia not repopulated.
        if (!(Records.getAllFilteredAppointments().isEmpty())) {
            Records.getAllFilteredAppointments().clear();
        }

        for (Appointment object : Records.getAllAppointments())
        {
            m = object.getStart().getMonth();
            if (m.equals(input))
                Records.getAllFilteredAppointments().add(object);
        }
        // This lets all data populate if not found
        if (Records.getAllFilteredAppointments().isEmpty()) {
            System.out.println("No match!");
            return Records.getAllAppointments();
        }
        else {
            return Records.getAllFilteredAppointments();
        }

    }
    /** @param input LocalDateTime input to search */
    // Appointment Week Dates
    public static ObservableList<Appointment> weekViewAppointment(LocalDateTime input) {

        // Create WeekFields
        WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY,1);
        // Apply WeekOFYear()
        TemporalField weekofYear = weekFields.weekOfWeekBasedYear();
        // get week of month for local date
        int wom = input.get(weekofYear);

        // This makes sure data ia not repopulated.
        if (!(Records.getAllFilteredAppointments().isEmpty())) {
            Records.getAllFilteredAppointments().clear();
        }

        // Loop through appointments and add matching weekOfYear
        for (Appointment object : Records.getAllAppointments()) {
           int w = object.getStart().get(weekofYear);

           if (w == wom) {
               Records.getAllFilteredAppointments().add(object);
           }
           else {continue;}
        }

        // This lets all data populate if not found
        if (Records.getAllFilteredAppointments().isEmpty()) {
            System.out.println("No match!");
            return Records.getAllAppointments();
        }
        else {
            return Records.getAllFilteredAppointments();
        }

    }


    //--------------------------------------------------------------------//

    // Countries methods

    /** @param newCountry country to add to list of countries */
    public static void addCountries(Country newCountry) { allCountries.add(newCountry); }
    /** @return the list of countries */
    public static ObservableList<Country> getAllCountries() { return allCountries; }
    /** @return the list of filtered countries */
    public static ObservableList<Country> getAllFilteredCountries() {
        return filteredCountries;
    }

    //-----------------------------------------------------------------//

    // First-Level Divisions Methods

    /** @param newDivision division to add to list of Division */
    public static void addDivisions(FirstLevelDivision newDivision) {
        allDivisions.add(newDivision);
    }
    /** @return the list of Divisions */
    public static ObservableList<FirstLevelDivision> getAllDivisions() { return allDivisions; }
    /** @return the list of filtered Divisions */
    public static ObservableList<FirstLevelDivision> getAllFilteredDivisions() {
        return filteredDivisions;
    }
    /** @param countryId country ID to search */
    public static ObservableList<FirstLevelDivision> lookupDivision(int countryId) {

        // This makes sure data ia not repopulated.
        if (!(Records.getAllFilteredDivisions().isEmpty()))
            Records.getAllFilteredDivisions().clear();

        for (FirstLevelDivision object : Records.getAllDivisions()) {
            if (object.getCountryID() == countryId) {

                Records.getAllFilteredDivisions().add(object);
            }

        }
        return getAllFilteredDivisions();
    }

    //-----------------------------------------------------------------//

    // Contacts

    /** @param newContact contact to add to list of contacts */
    public static void addContacts(Contact newContact) {
        allContacts.add(newContact);
    }
    /** @return the list of contacts */
    public static ObservableList<Contact> getAllContacts() { return allContacts; }
    /** @return the list of filtered contacts */
    public static ObservableList<Contact> getAllFilteredContacts() {
        return filteredContacts;
    }


    /** @param contact contact name to search */
    public static ObservableList<Contact> lookupContact(String contact) {
        // This makes sure data ia not repopulated.
        if (!(Records.getAllFilteredContacts().isEmpty()))
            Records.getAllFilteredContacts().clear();

        for (Contact object : Records.getAllContacts())
        {
            if (object.getName().contains(contact))
                Records.getAllFilteredContacts().add(object);
        }
        // This lets all data populate if not found
        if (Records.getAllFilteredContacts().isEmpty())
        return null;
        else
            return Records.getAllFilteredContacts();
    }

    /** @param contactID contact ID to search */
    public static ObservableList<Appointment> lookupContactAppointment(int contactID) {

        // This makes sure data ia not repopulated.
        if (!(Records.getAllFilteredAppointments().isEmpty()))
            Records.getAllFilteredAppointments().clear();

        for (Appointment object : Records.getAllAppointments()) {
            if (object.getContactID() == contactID) {

                Records.getAllFilteredAppointments().add(object);
            }

        }
        return getAllFilteredAppointments();
    }

    //-----------------------------------------------------------------//

    // Users
    /** @param newUser user to add to list of users */
    public static void addUser(User newUser) {
        allUsers.add(newUser);
    }
    /** @return the list of users */
    public static ObservableList<User> getAllUsers() { return allUsers; }
    /** @return the list of filtered users */
    public static ObservableList<User> getAllFilteredUsers() {
        return filteredUsers;
    }
    /** @param userID part ID to search */
    /*
    public static ObservableList<User> lookupUsers(int userID) {

        // This makes sure data ia not repopulated.
        if (!(Records.getAllFilteredContacts().isEmpty()))
            Records.getAllFilteredContacts().clear();

        for (Contact object : Records.getAllContacts()) {
            if (object.getId() == contactID) {

                Records.getAllFilteredContacts().add(object);
            }

        }
        return getAllFilteredContacts();


    }

         */


    // ------------------------------------------------------------------//

}

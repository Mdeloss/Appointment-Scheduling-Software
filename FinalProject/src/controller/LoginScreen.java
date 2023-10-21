package controller;

import Utilities.Query;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Records;
import model.User;

/** This class controls the actions and functions of the LoginScreen view. */
public class LoginScreen implements Initializable {

    Stage stage;
    Parent scene;

        @FXML
        private TextField passwordTxt;

        @FXML
        private TextField usernameTxt;

        @FXML
        private Label zoneIdLabel;

        @FXML
        private Label passwordLabel;

        @FXML
        private Label userNameLabel;

        @FXML
        private Button exitButtonLabel;

        @FXML
        private Button loginButtonLabel;


        // Translates language
        ResourceBundle rb = ResourceBundle.getBundle("main/Nat", Locale.getDefault());



    /** This method reads the input from username and password text fields, then
     * compares them to the database. If found, it will allow access to the rest of the GUI.
     * @param event The selection event. */
        @FXML
        public void onActionSearchPassword(ActionEvent event) throws IOException {
            // username and password variables
            String name = usernameTxt.getText();
            String password = passwordTxt.getText();

            // Login activity File name and attempt variable
            String filename = "login_activity.txt", attempt;
            //create filewriter object
            FileWriter fwriter = new FileWriter(filename, true);
            // create and open file
            PrintWriter outputFile = new PrintWriter(fwriter);
            System.out.println("File opened!");

            // create scanner object
            Scanner action = new Scanner(name);

            // Checks for value in text fields
            if (usernameTxt.getText().isBlank() || passwordTxt.getText().isBlank()) {
                System.out.println("Text field is blank");

                if(!usernameTxt.getText().isBlank()) {
                    // Write to login_activity.txt file
                    attempt = "User (" + action.nextLine() + ") failed login at: " + LocalDateTime.now();
                    outputFile.println(attempt);
                    System.out.println("File written!");

                }

                // Warning dialog box for invalid text fields
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(rb.getString("Login") + " " + rb.getString("Error") );
                alert.setContentText(rb.getString("Enter") + " " + rb.getString("Valid") + " " + rb.getString("Credentials"));
                alert.showAndWait();

            }
            else {
                System.out.println("Username:" + name);
                System.out.println("Password:" + password);

                try {
                    // Query.login(name, password);
                    if (Query.login(name, password)) {

                        int uID = 0;
                        int aID = 0;
                        int totalMatch = 0;


                        for (User object : Records.getAllUsers()) {
                            if ((object.getUserName().matches(name))) {
                                uID = object.getUserID();
                                System.out.println("UserID HERE: " + uID);
                            }
                        }

                        for (Appointment time : Records.getAllAppointments()) {

                            aID = time.getUserID();
                            if (aID == uID) {
                                System.out.println("Matched UserID: Appt " + time.getApptID());

                                if (LocalDateTime.now().isAfter(time.getStart().minusMinutes(15)) &&
                                        LocalDateTime.now().isBefore(time.getStart()))
                                {
                                    totalMatch++;
                                    System.out.println("Within 15 mins: " + "  " +
                                            time.getStart() + "  " + LocalDateTime.now());

                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                    alert.setTitle(rb.getString("Upcoming") + " " + rb.getString("Appointment") );
                                    alert.setContentText(rb.getString("Appointment") + ": " +
                                            time.getApptID() + " " + rb.getString("Date") + ": " + time.getStart());
                                    alert.showAndWait();

                                }
                                else {System.out.println("Not within 15 mins: " + "  " +
                                        time.getStart() + "  " + LocalDateTime.now());
                                }
                            }
                        }

                        // If no appointments are within 15 mins from start
                        if (totalMatch == 0) {

                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Appointment");
                            alert.setContentText(rb.getString("No") + " " + rb.getString("Upcoming") +
                                    " " + rb.getString("Appointment"));
                            alert.showAndWait();
                        }


                        stage = (Stage) ((TextField) event.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                        stage.setScene(new Scene(scene));
                        stage.show();


                        System.out.println("true");

                        // write to login_activity.txt file
                        attempt = "User (" + action.nextLine() + ") successful login at: " + LocalDateTime.now();
                        outputFile.println(attempt);
                        System.out.println("File written!");

                    }

                    else {
                        System.out.println("false");

                        // Write to login_activity.txt file
                        attempt = "User (" + action.nextLine() + ") failed login at: " + LocalDateTime.now();
                        outputFile.println(attempt);
                        System.out.println("File written!");

                        // Warning dialog box for login error
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle(rb.getString("Login") + " " + rb.getString("Error"));
                        alert.setContentText(rb.getString("Incorrect") + " " + rb.getString("Login") + " " + rb.getString("Credentials"));
                        alert.showAndWait();
                    }

                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
            }
            //close file
            outputFile.close();
            System.out.println("File closed!");
        }

    /** This method reads the input from the username text field and prints the string.
     * @param event The selection event. */
        @FXML
        public void onActionSearchUsername(ActionEvent event) throws IOException {
            // username and password variables
            String name = usernameTxt.getText();
            String password = passwordTxt.getText();

            // Login activity File name and attempt variable
            String filename = "login_activity.txt", attempt;
            //create filewriter object
            FileWriter fwriter = new FileWriter(filename, true);
            // create and open file
            PrintWriter outputFile = new PrintWriter(fwriter);
            System.out.println("File opened!");

            // create scanner object
            Scanner action = new Scanner(name);

            // Checks for value in text fields
            if (usernameTxt.getText().isBlank() || passwordTxt.getText().isBlank()) {
                System.out.println("Text field is blank");

                if(!usernameTxt.getText().isBlank()) {
                    // Write to login_activity.txt file
                    attempt = "User (" + action.nextLine() + ") failed login at: " + LocalDateTime.now();
                    outputFile.println(attempt);
                    System.out.println("File written!");

                }

                // Warning dialog box for invalid text fields
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(rb.getString("Login") + " " + rb.getString("Error") );
                alert.setContentText(rb.getString("Enter") + " " + rb.getString("Valid") + " " + rb.getString("Credentials"));
                alert.showAndWait();

            }
            else {
                System.out.println("Username:" + name);
                System.out.println("Password:" + password);

                try {
                    // Query.login(name, password);
                    if (Query.login(name, password)) {

                        int uID = 0;
                        int aID = 0;
                        int totalMatch = 0;


                        for (User object : Records.getAllUsers()) {
                            if ((object.getUserName().matches(name))) {
                                uID = object.getUserID();
                                System.out.println("UserID HERE: " + uID);
                            }
                        }

                        for (Appointment time : Records.getAllAppointments()) {

                            aID = time.getUserID();
                            if (aID == uID) {
                                System.out.println("Matched UserID: Appt " + time.getApptID());

                                if (LocalDateTime.now().isAfter(time.getStart().minusMinutes(15)) &&
                                        LocalDateTime.now().isBefore(time.getStart()))
                                {
                                    totalMatch++;
                                    System.out.println("Within 15 mins: " + "  " +
                                            time.getStart() + "  " + LocalDateTime.now());

                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                    alert.setTitle(rb.getString("Upcoming") + " " + rb.getString("Appointment") );
                                    alert.setContentText(rb.getString("Appointment") + ": " +
                                            time.getApptID() + " " + rb.getString("Date") + ": " + time.getStart());
                                    alert.showAndWait();

                                }
                                else {System.out.println("Not within 15 mins: " + "  " +
                                        time.getStart() + "  " + LocalDateTime.now());
                                }
                            }
                        }

                        // If no appointments are within 15 mins from start
                        if (totalMatch == 0) {

                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Appointment");
                            alert.setContentText(rb.getString("No") + " " + rb.getString("Upcoming") +
                                    " " + rb.getString("Appointment"));
                            alert.showAndWait();
                        }


                        stage = (Stage) ((TextField) event.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                        stage.setScene(new Scene(scene));
                        stage.show();


                        System.out.println("true");

                        // write to login_activity.txt file
                        attempt = "User (" + action.nextLine() + ") successful login at: " + LocalDateTime.now();
                        outputFile.println(attempt);
                        System.out.println("File written!");

                    }

                    else {
                        System.out.println("false");

                        // Write to login_activity.txt file
                        attempt = "User (" + action.nextLine() + ") failed login at: " + LocalDateTime.now();
                        outputFile.println(attempt);
                        System.out.println("File written!");

                        // Warning dialog box for login error
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle(rb.getString("Login") + " " + rb.getString("Error"));
                        alert.setContentText(rb.getString("Incorrect") + " " + rb.getString("Login") + " " + rb.getString("Credentials"));
                        alert.showAndWait();
                    }

                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
            }

            //close file
            outputFile.close();
            System.out.println("File closed!");
        }

    /** This method reads the input from username and password text fields, then
     * compares them to the database. If found, it will allow access to the rest of the GUI and
     * write login attempts to the login_activity.txt file.
     * @param event The selection event. */
        @FXML
        public void onActionLogin(ActionEvent event) throws IOException {
            System.out.println("Login button clicked.");

            // username and password variables
            String name = usernameTxt.getText();
            String password = passwordTxt.getText();

            // Login activity File name and attempt variable
            String filename = "login_activity.txt", attempt;
            //create filewriter object
            FileWriter fwriter = new FileWriter(filename, true);
            // create and open file
            PrintWriter outputFile = new PrintWriter(fwriter);
            System.out.println("File opened!");

            // create scanner object
            Scanner action = new Scanner(name);



            // Checks for value in text fields
            if (usernameTxt.getText().isBlank() || passwordTxt.getText().isBlank()) {
                System.out.println("Text field is blank");

                if(!usernameTxt.getText().isBlank()) {
                    // Write to login_activity.txt file
                    attempt = "User (" + action.nextLine() + ") failed login at: " + LocalDateTime.now();
                    outputFile.println(attempt);
                    System.out.println("File written!");

                }

                // Warning dialog box for invalid text fields
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle(rb.getString("Login") + " " + rb.getString("Error") );
                alert.setContentText(rb.getString("Enter") + " " + rb.getString("Valid") + " " + rb.getString("Credentials"));
                alert.showAndWait();

            }
            else {
                System.out.println("Username:" + name);
                System.out.println("Password:" + password);

                try {
                   // Query.login(name, password);

                    // Check if Query returns true
                    if (Query.login(name, password)) {

                        int uID = 0;
                        int aID = 0;
                        int totalMatch = 0;


                        // User name found
                        for (User object : Records.getAllUsers()) {
                            if ((object.getUserName().matches(name))) {
                                uID = object.getUserID();
                                System.out.println("UserID HERE: " + uID);
                            }
                        }

                        // Appointment 15 min warning
                        for (Appointment time : Records.getAllAppointments()) {

                            aID = time.getUserID();
                            if (aID == uID) {
                                System.out.println("Matched UserID: Appt " + time.getApptID());

                                if (LocalDateTime.now().isAfter(time.getStart().minusMinutes(15)) &&
                                        LocalDateTime.now().isBefore(time.getStart()))
                                {
                                    totalMatch++;
                                    System.out.println("Within 15 mins: " + "  " +
                                            time.getStart() + "  " + LocalDateTime.now());

                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                    alert.setTitle(rb.getString("Upcoming") + " " + rb.getString("Appointment") );
                                    alert.setContentText(rb.getString("Appointment") + ": " +
                                            time.getApptID() + " " + rb.getString("Date") + ": " + time.getStart());
                                    alert.showAndWait();

                                }
                                else {System.out.println("Not within 15 mins: " + "  " +
                                        time.getStart() + "  " + LocalDateTime.now());
                                }
                            }
                        }

                        // If no appointments are within 15 mins from start
                        if (totalMatch == 0) {

                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Appointment");
                            alert.setContentText(rb.getString("No") + " " + rb.getString("Upcoming") +
                                    " " + rb.getString("Appointment"));
                            alert.showAndWait();
                        }


                        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                        stage.setScene(new Scene(scene));
                        stage.show();


                        System.out.println("true");

                        // write to login_activity.txt file
                        attempt = "User (" + action.nextLine() + ") successful login at: " + LocalDateTime.now();
                        outputFile.println(attempt);
                        System.out.println("File written!");
                    }

                    else {
                        System.out.println("false");

                        // Write to login_activity.txt file
                        attempt = "User (" + action.nextLine() + ") failed login at: " + LocalDateTime.now();
                        outputFile.println(attempt);
                        System.out.println("File written!");

                        // Warning dialog box for login error
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle(rb.getString("Login") + " " + rb.getString("Error"));
                        alert.setContentText(rb.getString("Incorrect") + " " + rb.getString("Login")
                                + " " + rb.getString("Credentials"));
                        alert.showAndWait();
                    }

                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
            }

            //close file
            outputFile.close();
            System.out.println("File closed!");
        }

    /** This method will exit the application after a confirmation alert.
     * @param event The selection event. */
        @FXML
        public void onActionExitLogin(ActionEvent event) {
            System.out.println("Exit button clicked.");

            // Confirmation dialog box
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, rb.getString("Confirm") + " " +
                    rb.getString("Exit") + "?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                System.out.println("Exiting application");
                System.exit(0);
            }

        }


    /** This allows for initialization of this screen with table data populated.
     * @param url
     * @param resourceBundle */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            Query.selectAppointments();
            Query.selectUsers();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

            // ZoneID object
            ZoneId z = ZoneId.systemDefault();
            System.out.println("ZoneID: " + z);
            // Sets user zoneID
            zoneIdLabel.setText(String.valueOf(z));

            // Sets username and password labels translation
            passwordLabel.setText(rb.getString("Password"));
            userNameLabel.setText(rb.getString("Username"));

            // Sets button text translation
            loginButtonLabel.setText(rb.getString("Login"));
            exitButtonLabel.setText(rb.getString("Exit"));


    }
}

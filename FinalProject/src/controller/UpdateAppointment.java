package controller;

import Utilities.Query;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

    /** This class controls the actions and functions of the UpdateAppointment view. */
    public class UpdateAppointment implements Initializable {


        // Appointment Text fields
        @FXML
        private TextField appointmentDescriptionTxt;

        @FXML
        private TextField appointmentIDTxt;

        @FXML
        private TextField appointmentLocationTxt;

        @FXML
        private TextField appointmentTitleTxt;

        @FXML
        private TextField appointmentTypeTxt;

        //---------------------------------------------------//
        // Contact Text fields
        @FXML
        private TableView<Contact> contactTableView;

        @FXML
        private TextField contactIDTxt;

        @FXML
        private TableColumn<Contact, String> contactEmailCol;

        @FXML
        private TableColumn<Contact, Integer> contactIDCol;

        @FXML
        private TableColumn<Contact, String> contactNameCol;

        //----------------------------------------------//

        // Customer Text fields

        @FXML
        private TableView<Customer> customerTableView;
        @FXML
        private TextField customerIDTxt;
        @FXML
        private TableColumn<Customer, Integer> customerIDCol;
        @FXML
        private TableColumn<Customer, String> customerNameCol;
        @FXML
        private TableColumn<Customer, String> customerAddressCol;
        @FXML
        private TableColumn<Customer, String> customerPhoneCol;
        @FXML
        private TableColumn<Customer, String> customerPostalCol;
        @FXML
        private TableColumn<Customer, Integer> customerDivIDCol;


//---------------------------------------------//

        // Date and Time Text fields
        @FXML
        private TextField endTimeTxt;

        @FXML
        private TextField startTimeTxt;

        //-------------------------------------//

        // User Text fields
        @FXML
        private TextField userIDTxt;

        @FXML
        private TableView<User> userTableView;

        @FXML
        private TableColumn<User, Integer> userIDCol;

        @FXML
        private TableColumn<User, String> userNameCol;


        //-------------------------------------------------------//

        // Set scene
        Stage stage;
        Parent scene;

        // Local Object used to hold data
        int apptID, customerID, userID, contactID = 0;
        String title, description, location, type = null ;
        LocalDateTime start, end;

        Appointment object = new Appointment( apptID, customerID, userID, contactID, title, description, location,
                type, start, end );

        // This formats LocalDateTime to String
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


        //--------------------------------------------------------------//

        // @FXML Methods

        /** This method sends a selected contact's ID from the contact table to the
         * contactID text field.
         * @param event The selection event. */
        @FXML
        public void onActionAddContact(ActionEvent event) {
            System.out.println("Add contact button clicked");

            // Select and transfer from table selection
            // customerIDTxt.clear();
            Contact selectedObj = contactTableView.getSelectionModel().getSelectedItem();

            // Logical check for part selected
            if (selectedObj == null) {
                // Error dialog box
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a contact.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    return;
                }
            }
            // This grabs the Contact ID and places it in the text box
            contactIDTxt.setText(String.valueOf(selectedObj.getId()));

        }

        /** This method sends a selected customer's ID from the customer table to the
         * customerID text field.
         * @param event The selection event. */
        @FXML
        public void onActionAddCustomer(ActionEvent event) {
            System.out.println("Add customer button clicked");

            // Select and transfer from table selection
            // customerIDTxt.clear();
            Customer selectedObj = customerTableView.getSelectionModel().getSelectedItem();

            // Logical check for part selected
            if (selectedObj == null) {
                // Error dialog box
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a customer.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    return;
                }
            }
            // This grabs the Contact ID and places it in the text box
            customerIDTxt.setText(String.valueOf(selectedObj.getId()));
        }

        /** This method sends a selected user's ID from the user table to the
         * userID text field.
         * @param event The selection event. */
        @FXML
        public void onActionAddUser(ActionEvent event) {
            System.out.println("Add user button clicked");

            // Select and transfer from table selection
            // customerIDTxt.clear();
            User selectedObj = userTableView.getSelectionModel().getSelectedItem();

            // Logical check for part selected
            if (selectedObj == null) {
                // Error dialog box
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a user.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    return;
                }
            }
            // This grabs the Contact ID and places it in the text box
            userIDTxt.setText(String.valueOf(selectedObj.getUserID()));
        }


        /** This method saves the input provided for editable fields, saves Appointment list,
         * and returns to the Main Screen.
         * @param event The selection event. */
        @FXML
        public void onActionSaveAppointment(ActionEvent event) throws IOException {

            try {
                System.out.println("Save button clicked");

                // This will parse from input text fields
                int apptID = Integer.parseInt(appointmentIDTxt.getText());
                int customerID = Integer.parseInt(customerIDTxt.getText());
                int userID = Integer.parseInt(userIDTxt.getText());
                int contactID = Integer.parseInt(contactIDTxt.getText());
                String title = appointmentTitleTxt.getText();
                String description = appointmentDescriptionTxt.getText();
                String location = appointmentLocationTxt.getText();
                String type = appointmentTypeTxt.getText();
                String start = startTimeTxt.getText();
                String end = endTimeTxt.getText();



                // This parses String to LocalDateTime
                LocalDateTime startTime = LocalDateTime.parse(start, dtf);
                LocalDateTime endTime = LocalDateTime.parse(end, dtf);
                LocalDateTime now = LocalDateTime.now();


                // Timezone conversion from local to EST
                LocalDateTime estStartTime = startTime.atZone(ZoneId.systemDefault())
                        .withZoneSameInstant(ZoneId.of("America/New_York"))
                        .toLocalDateTime();
                LocalDateTime estEndTime = endTime.atZone(ZoneId.systemDefault())
                        .withZoneSameInstant(ZoneId.of("America/New_York"))
                        .toLocalDateTime();
                System.out.println("Converts: " + startTime  +" to " + estStartTime);
                System.out.println("Converts: " + endTime  +" to " + estEndTime);


                //This converts LocalDateTime to TimeStamp
                Timestamp startTS = Timestamp.valueOf(startTime);
                Timestamp endTS = Timestamp.valueOf(endTime);


                // Logical check for Name
                if (appointmentTitleTxt.getText().isEmpty()) {
                    // Error dialog box
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a Title");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        return;
                    }
                }


                // Create localTime business start and end Times-only in EST
                LocalTime startT = estStartTime.toLocalTime();
                LocalTime endT = estEndTime.toLocalTime();
                LocalTime businessStart = LocalTime.of(8,00, 00);
                LocalTime businessEnd = LocalTime.of(22,00, 00);

                // Logical check for start and end times
                if((startTime.isBefore(now)) || (startTime.isAfter(endTime)) || (startTime.isEqual(endTime))) {
                    // Error dialog box
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid Start and End Time" +
                            " beginning from now. 24HR Format");
                    Optional<ButtonType> result = alert.showAndWait();
                    if(result.get() == ButtonType.OK) {
                        return;
                    }
                }

                // Logical check for business start times in EST
                if((startT.isBefore((businessStart))) || (startT.isAfter((businessEnd)))) {
                    // Error dialog box
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Start time not within business hours: "
                            + businessStart + " to " + businessEnd + " EST");
                    Optional<ButtonType> result = alert.showAndWait();
                    if(result.get() == ButtonType.OK) {
                        return;
                    }
                }
                // Logical check for business end times in EST
                if((endT.isBefore((businessStart))) || (endT.isAfter((businessEnd)))) {
                    // Error dialog box
                    Alert alert = new Alert(Alert.AlertType.ERROR, "End time not within business hours: "
                            + businessStart + " to " + businessEnd + " EST");
                    Optional<ButtonType> result = alert.showAndWait();
                    if(result.get() == ButtonType.OK) {
                        return;
                    }
                }

                // Logical check for overlapping appointments of same customer
                for (Appointment object : Records.getAllAppointments())
                {
                    // Makes sure current appointment is not counted
                    if (object.getApptID() == apptID){
                        continue;
                    }
                    else {
                        // first checks matching customers
                        if (object.getCustomerID() == customerID) {
                            // start and end time checks
                            if ((startTime.isAfter(object.getStart()) && startTime.isBefore(object.getEnd())) ||
                                    (endTime.isAfter(object.getStart()) && endTime.isBefore(object.getEnd()))) {
                                System.out.println("Overlap");

                                // Error dialog box
                                Alert alert = new Alert(Alert.AlertType.ERROR, object.getTitle() + " already scheduled: " +
                                        object.getStart() + " to " + object.getEnd());
                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.get() == ButtonType.OK) {
                                    return;
                                }
                            }
                            if ((startTime.isAfter(object.getStart()) && startTime.isBefore(object.getEnd())) ||
                                    (endTime.isAfter(object.getStart()) && endTime.isBefore(object.getEnd()))) {
                                System.out.println("Overlap");

                                // Error dialog box
                                Alert alert = new Alert(Alert.AlertType.ERROR, object.getTitle() + " already scheduled: " +
                                        object.getStart() + " to " + object.getEnd());
                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.get() == ButtonType.OK) {
                                    return;
                                }
                            }

                            // nested appointment checks
                            if ((startTime.isAfter(object.getStart()) && endTime.isBefore(object.getEnd())) ||
                                    (startTime.isBefore(object.getStart()) && endTime.isAfter(object.getEnd()))) {
                                System.out.println("Overlap");

                                // Error dialog box
                                Alert alert = new Alert(Alert.AlertType.ERROR, object.getTitle() + " already scheduled: " +
                                        object.getStart() + " to " + object.getEnd());
                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.get() == ButtonType.OK) {
                                    return;
                                }
                            }

                            // appointments with equal start or end times
                            if ((startTime.isEqual(object.getStart()) && endTime.isBefore(object.getEnd())) ||
                                    (startTime.isEqual(object.getStart()) && endTime.isAfter(object.getEnd()))) {
                                System.out.println("Overlap");

                                // Error dialog box
                                Alert alert = new Alert(Alert.AlertType.ERROR, object.getTitle() + " already scheduled: " +
                                        object.getStart() + " to " + object.getEnd());
                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.get() == ButtonType.OK) {
                                    return;
                                }
                            }
                            if ((startTime.isAfter(object.getStart()) && endTime.isEqual(object.getEnd())) ||
                                    (startTime.isBefore(object.getStart()) && endTime.isEqual(object.getEnd()))) {
                                System.out.println("Overlap");

                                // Error dialog box
                                Alert alert = new Alert(Alert.AlertType.ERROR, object.getTitle() + " already scheduled: " +
                                        object.getStart() + " to " + object.getEnd());
                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.get() == ButtonType.OK) {
                                    return;
                                }
                            }
                            if ((startTime.isEqual(object.getStart()) && endTime.isEqual(object.getEnd()))) {
                                System.out.println("Overlap");

                                // Error dialog box
                                Alert alert = new Alert(Alert.AlertType.ERROR, object.getTitle() + " already scheduled: " +
                                        object.getStart() + " to " + object.getEnd());
                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.get() == ButtonType.OK) {
                                    return;
                                }
                            }
                        }
                    }

                }

                // This adds appointment object to Database
                Query.update(apptID, title, description, location, type, startTS, endTS, customerID, userID, contactID);

                // This take us back to MainScreen
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();


            }
            catch(NumberFormatException e) {
                System.out.println("Please enter valid value here.");
                System.out.println("Exception: " + e);
                System.out.println("Exception: " + e.getMessage());

                // Warning dialog box
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error Dialog");
                alert.setContentText("Please enter a valid value for each Text Field!");
                alert.showAndWait();
            }
            catch(DateTimeException e) {
                System.out.println("Please enter valid format here:  yyyy-MM-dd HH:mm.");
                System.out.println("Exception: " + e);
                System.out.println("Exception: " + e.getMessage());

                // Warning dialog box
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error Dialog");
                alert.setContentText("Valid format:  yyyy-MM-dd HH:mm");
                alert.showAndWait();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }


        /** This method cancels all actions and returns to Main Screen,
         * but will first ask for confirmation.
         * @param event The selection event. */
        @FXML
        public void onActionCancelAppointment(ActionEvent event) throws IOException{
            // Confirmation dialog box
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all values, do you want to continue?");

            Optional<ButtonType> result = alert.showAndWait();

            if(result.get() == ButtonType.OK) {
                System.out.println("Cancel part button clicked");
                stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }


        /** This method catches information selected from the Appointments
         * table on the Main Screen
         * @param object  The product holding received data. */
        public void sendAppointment(Appointment object) {

            // Localizes time to system
            ZonedDateTime startZDT = object.getStart().atZone(ZoneId.systemDefault());
            ZonedDateTime endZDT = object.getEnd().atZone(ZoneId.systemDefault());

            // This formats LocalDateTime to String
            String startTime = dtf.format(startZDT);
            String endTime = dtf.format(endZDT);

            appointmentIDTxt.setText(String.valueOf(object.getApptID()));
            appointmentTitleTxt.setText(object.getTitle());
            customerIDTxt.setText(String.valueOf(object.getCustomerID()));
            userIDTxt.setText(String.valueOf(object.getUserID()));
            contactIDTxt.setText(String.valueOf(object.getContactID()));
            appointmentDescriptionTxt.setText(object.getDescription());
            appointmentLocationTxt.setText(object.getLocation());
            appointmentTypeTxt.setText(object.getType());
            startTimeTxt.setText(startTime);
            endTimeTxt.setText(endTime);


        }




        /** This allows for initialization of this screen with auto-gen ID and
         * table data populated.
         * @param resourceBundle
         * @param url */

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

            /*
            try {
                Query.selectCountries();
                Query.selectUsers();
                Query.selectDivisions();
                Query.selectContacts();
               // Query.selectAppointments();
               // Query.selectCustomers();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

             */


            // Auto ID generator
            int idNum = 1;
            for (Appointment list : Records.getAllAppointments()) {
                if (list.getApptID() == idNum)
                    idNum++;
            }
            String randomNum = String.valueOf(idNum);
            appointmentIDTxt.setText(randomNum);

            // This shows data in the Contact table
            contactTableView.setItems(Records.getAllContacts());
            contactIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            contactNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            contactEmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

            // This shows data in the User table
            userTableView.setItems(Records.getAllUsers());
            userIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
            userNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));

            // This shows data in the Customer table
            customerTableView.setItems(Records.getAllCustomers());
            customerIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            customerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
            customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
            customerPostalCol.setCellValueFactory(new PropertyValueFactory<>("postal_code"));
            customerDivIDCol.setCellValueFactory(new PropertyValueFactory<>("div_id"));


        }


}




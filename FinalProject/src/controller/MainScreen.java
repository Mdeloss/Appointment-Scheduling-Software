package controller;

import Utilities.Implementation;
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
import java.time.*;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class controls the actions and functions of the MainScreen view. */
public class MainScreen implements Initializable {

        Stage stage;
        Parent scene;

        // ------------------------------------------------------//

    // Customer. Casting and Wrappers
        @FXML
        private TextField custIDTxt;
        @FXML
        private TableView<Customer> custTableView;

        @FXML
        private TableColumn<Customer, Integer> custIDCol;
        @FXML
        private TableColumn<Customer, String> custNameCol;
        @FXML
        private TableColumn<Customer, String> addressCol;
        @FXML
        private TableColumn<Customer, String> phoneCol;
        @FXML
        private TableColumn<Customer, String> postCodeCol;
        @FXML
        private TableColumn<Customer, Integer> divIDCol;

        // ----------------------------------------------------------------------//


    // Appointment. Casters and Wrappers.
        @FXML
        private TextField apptIDTxt;
        @FXML
        private TableView<Appointment> appointmentTableView;

        @FXML
        private TableColumn<Appointment, Integer> apptIDCol;

        @FXML
        private TableColumn<Appointment, String> contactCol;

        @FXML
        private TableColumn<Appointment, Integer> custApptIDCol;

        @FXML
        private TableColumn<Appointment, String> descriptionCol;

        @FXML
        private TableColumn<Appointment, String> endCol;

        @FXML
        private TableColumn<Appointment, String> locationCol;

        @FXML
        private TableColumn<Appointment, String> startCol;

        @FXML
        private TableColumn<Appointment, String> titleCol;

        @FXML
        private TableColumn<Appointment, String> typeCol;

        @FXML
        private TableColumn<Appointment, Integer> userIDCol;
        @FXML
        private RadioButton monthViewRB;
        @FXML
        private RadioButton weekViewRB;
        @FXML
        private RadioButton allViewRB;

        // Radio buttons
        boolean isMonthViewRB;
        boolean isWeekViewRB;
        boolean isAllViewRB;


        //----------------------------------------------------------------//


        // Customer Actions
        /** This method deletes a selected customer and associated appointments after confirmation.
         * @param event The selection event. */
        // Will delete associated appointments first
        @FXML
        public void onActionDeleteCustomer(ActionEvent event) throws IOException, SQLException {
            System.out.println("Delete button clicked");

            Customer selectedObj = null;
            int objectID = -1;

            // Delete from table selection
            custIDTxt.clear();
            selectedObj = custTableView.getSelectionModel().getSelectedItem();

            // Logical check for selected customer
            if(selectedObj == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Choose a customer.");
                Optional<ButtonType> result = alert.showAndWait();

                if(result.get() == ButtonType.OK) {
                    return;
                }
            }
            // Confirmation dialog box
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete, " + selectedObj.getName() + " and all associated appointments. Do you want to continue?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {

                if (selectedObj == null)
                    return;

                // Delete customer appointments from database
                Query.deleteAssocAppt(selectedObj.getId());
                // Delete customer appointments from local list
                Records.deleteAppointment(selectedObj.getId());
                // Delete Customer from database
                Query.deleteCustomer(selectedObj.getId());
                // Delete Customer from local list
                Records.deleteCust(selectedObj.getId());

                // Confirmation dialog box
                Alert alert2 = new Alert(Alert.AlertType.WARNING, selectedObj.getId() + " " +
                        selectedObj.getName() + ". Deleted");
                Optional<ButtonType> result2 = alert2.showAndWait();
                if(result2.get() == ButtonType.OK) {

                    if (selectedObj == null)
                        return;
                }

                // Resets Appointment Table view
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();



            }
        }
        /** This method sends a selected customer to the UpdateCustomer screen
         * and launches the UpdateCustomer screen.
         * @param event The selection event. */
        @FXML
        public void onActionUpdateCustomer(ActionEvent event) throws IOException{

            try{
                System.out.println("Update button clicked");

                // Mouse click
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/UpdateCustomer.fxml"));
                loader.load();

                UpdateCustomer selectedItem = loader.getController();
                selectedItem.sendCustomer(custTableView.getSelectionModel().getSelectedItem());


                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = loader.getRoot();
                stage.setScene(new Scene(scene));
                stage.show();
            }

            catch(NullPointerException e){
                    System.out.println("Please select a customer.");
                    System.out.println("Exception: " + e);
                    System.out.println("Exception: " + e.getMessage());

                    // Warning dialog box
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("Please select a customer.");
                    alert.showAndWait();
                }

        }
        /** This method launches the AddCustomer screen.
         * @param event The selection event. */
        @FXML
        public void onActionAddCustomer(ActionEvent event) throws IOException{
            System.out.println("Add button clicked");

            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
            stage. setScene(new Scene(scene));
            stage.show();

        }
        /** This method filters for customer name or ID and will display an
         * error if neither is found.
         * @param event The selection event. */
        @FXML
        public void onActionSearchCustomer(ActionEvent event) throws IOException {

            // Get user Input for name first
            String userInput = custIDTxt.getText();
            custTableView.setItems(Records.lookupCustomers(userInput));


            // If list is empty, check for an Integer.
            if (Records.getAllFilteredCustomers().size() == 0) {
                try {
                    int inputID = Integer.parseInt(custIDTxt.getText());

                    // Look up ID
                    Customer object = Records.lookupCustomers(inputID);

                    // Alert for incorrect ID search
                    if (object == null) {
                        Customer selectedObj = null;
                        // Confirmation dialog box
                        Alert alert = new Alert(Alert.AlertType.WARNING, "ID not found");
                        Optional<ButtonType> result = alert.showAndWait();
                        if(result.get() == ButtonType.OK) {

                            if (selectedObj == null)
                                return;
                        }
                    }

                    if (object != null) {
                        Records.getAllFilteredCustomers().add(object);
                        custTableView.setItems(Records.getAllFilteredCustomers());
                    }
                } catch (NumberFormatException e) {
                    // Warning dialog box
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("Not found");
                    alert.showAndWait();
                }


                // Displays table with data
                custIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
                custNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
                addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
                phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
                postCodeCol.setCellValueFactory(new PropertyValueFactory<>("postal_code"));
                divIDCol.setCellValueFactory(new PropertyValueFactory<>("div_id"));
            }
        }


    // ------------------------------------------------------------------//

    // Appointment Actions

        /** This method deletes a selected Appointment.
         * @param event The selection event. */
        @FXML
        public void onActionDeleteAppointment(ActionEvent event) throws IOException, SQLException {
                System.out.println("Delete button clicked");

                Appointment selectedObj = null;
                int objectID = -1;

                // Grab from table selection
                apptIDTxt.clear();
                selectedObj = appointmentTableView.getSelectionModel().getSelectedItem();

                // Logical check for selected Products
                if(selectedObj == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Choose an appointment.");
                    Optional<ButtonType> result = alert.showAndWait();

                    if(result.get() == ButtonType.OK) {
                        return;
                    }
                }

                // Confirmation dialog box
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete, " + selectedObj.getTitle() +
                        ". Do you want to continue?");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == ButtonType.OK) {

                    if (selectedObj == null)
                     return;

                    Query.deleteAppointment(selectedObj.getApptID());
                    Records.deleteAppointment(selectedObj.getApptID());

                    // Confirmation dialog box
                    Alert alert2 = new Alert(Alert.AlertType.WARNING, selectedObj.getApptID() + " " +
                            selectedObj.getTitle() + ". Deleted");
                    Optional<ButtonType> result2 = alert2.showAndWait();
                    if(result2.get() == ButtonType.OK) {

                        if (selectedObj == null)
                            return;
                    }
                    // Resets Appointment Table view
                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();


            }
        }
        /** This method sends a selected Appointment to the UpdateAppointment screen
         * and launches the UpdateAppointment screen.
         * @param event The selection event. */
        @FXML
        public void onActionUpdateAppointment(ActionEvent event) throws IOException {
            try{
            System.out.println("Update button clicked");

            // Mouse click
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/UpdateAppointment.fxml"));
            loader.load();

            UpdateAppointment selectedItem = loader.getController();
            selectedItem.sendAppointment(appointmentTableView.getSelectionModel().getSelectedItem());


            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        }
            catch(NullPointerException e){
                System.out.println("Please select an appointment.");
                System.out.println("Exception: " + e);
                System.out.println("Exception: " + e.getMessage());

                // Warning dialog box
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error Dialog");
                alert.setContentText("Please select an appointment.");
                alert.showAndWait();
            }

        }
        /** This method launches the AddAppointment screen.
         * @param event The selection event. */
        @FXML
        public void onActionAddAppointment(ActionEvent event) throws IOException{
            System.out.println("Add button clicked");

            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
            stage. setScene(new Scene(scene));
            stage.show();


        }
        /** This method filters for Appointment name or ID and will display an
         * error if neither is found.
         * @param event The selection event. */
        @FXML
        public void onActionSearchAppointment(ActionEvent event) throws IOException {

        // Get user Input for name first
        String userInput = apptIDTxt.getText();
        appointmentTableView.setItems(Records.lookupAppointment(userInput));

        // If list is empty, check for an Integer.
        if (Records.getAllFilteredAppointments().size() == 0) {
            try {
                int inputID = Integer.parseInt(apptIDTxt.getText());

                // Look up ID
                Appointment object = Records.lookupAppointment(inputID);

                // Alert for incorrect ID search
                if (object == null) {
                    Customer selectedObj = null;
                    // Confirmation dialog box
                    Alert alert = new Alert(Alert.AlertType.WARNING, "ID not found");
                    Optional<ButtonType> result = alert.showAndWait();
                    if(result.get() == ButtonType.OK) {

                        if (selectedObj == null)
                            return;
                    }
                }
                if (object != null) {
                    Records.getAllFilteredAppointments().add(object);
                    appointmentTableView.setItems(Records.getAllFilteredAppointments());
                }
            }
            catch (NumberFormatException e) {
                // Warning dialog box
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error Dialog");
                alert.setContentText("Not found");
                alert.showAndWait();
            }

        }

        // Displays table with data

            apptIDCol.setCellValueFactory(new PropertyValueFactory<>("apptID"));
            contactCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
            custApptIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
            locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
            startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            userIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
    }

        /** This method filters for Appointment by current month or current week.
     * @param event The selection event. */
        @FXML
        public void onActionRadioButton(ActionEvent event) {
            System.out.println("Radio button clicked");

            // Get today's datetime
            LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
            Month m = now.getMonth();
            DayOfWeek w = now.getDayOfWeek();

            if(monthViewRB.isSelected()) {
                System.out.println("Month viewing: " + m);

                // Filter by current Month
                Records.monthViewAppointment(m);
                appointmentTableView.setItems(Records.getAllFilteredAppointments());

            }
            else if (weekViewRB.isSelected()) {
                System.out.println("Week viewing: " + w);

                // Filter by current week
                Records.weekViewAppointment(now);
                appointmentTableView.setItems(Records.getAllFilteredAppointments());

            }
            else if (allViewRB.isSelected()) {
                System.out.println("Week viewing: ALL");

                // Display all
                appointmentTableView.setItems(Records.getAllAppointments());
            }

            // Appointment Table View
            apptIDCol.setCellValueFactory(new PropertyValueFactory<>("apptID"));
            contactCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
            custApptIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
            locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
            startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            userIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));

        }

    //-----------------------------------------------------------------------------//

    /** This method launches the Reports screen.
     * @param event The selection event. */
        @FXML
        public void onActionReports(ActionEvent event) throws IOException {
            System.out.println("Reports button clicked");

            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/Reports.fxml"));
            stage. setScene(new Scene(scene));
            stage.show();
        }
        // Exit Button
        /** This method will exit the application after a confirmation alert.
         * @param event The selection event. */
        @FXML
        public void onActionExit(ActionEvent event) {
            System.out.println("Exit button clicked.");
            // Confirmation dialog box
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                System.out.println("Exiting application");
                System.exit(0);
            }
        }
        /** This method launches the LoginScreen screen.
         * @param event The selection event. */
        @FXML
        public void onActionLogout(ActionEvent event) throws IOException {
            System.out.println("Log Out button clicked.");


            // Confirmation dialog box
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to sign out?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

            }
        }

        /** This allows for initialization of this screen with table data populated.
         * @param url
         * @param resourceBundle */
        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

            // Call Database Queries
            try {
                Implementation.mySQLCalls();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            /*
            try {
                Query.selectCountries();
                Query.selectUsers();
                Query.selectDivisions();
                Query.selectContacts();
                Query.selectAppointments();
                Query.selectCustomers();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

             */

            // This shows data in the table
            // Customers
            custTableView.setItems(Records.getAllCustomers());
            custIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            custNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
            phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
            postCodeCol.setCellValueFactory(new PropertyValueFactory<>("postal_code"));
            divIDCol.setCellValueFactory(new PropertyValueFactory<>("div_id"));


            // Appointments
            appointmentTableView.setItems(Records.getAllAppointments());
            apptIDCol.setCellValueFactory(new PropertyValueFactory<>("apptID"));
            contactCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
            custApptIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
            endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
            locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
            startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            userIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));


        }


    }


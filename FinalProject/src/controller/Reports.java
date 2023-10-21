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
import Utilities.Lambda2;
import Utilities.Lambda1;
import model.*;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.Optional;
import java.util.ResourceBundle;

/** This class controls the actions and functions of the Reports view. */
public class Reports implements Initializable {

    Stage stage;
    Parent scene;


        @FXML
        private TextField ContactAppointmentsTxt;

        @FXML
        private TableView<Contact> contactTableView;

        @FXML
        private TableColumn<Contact, String> contactIDCol;

        @FXML
        private TableColumn<Contact, String> contactNameCol;

        @FXML
        private TextField contactSearchTxt;

        @FXML
        private TextField monthTxt;

        @FXML
        private TextField totalCountTxt;

        @FXML
        private TextField dayTotalTxt;

        @FXML
        private TextField typeTxt;

        @FXML
        private TextField dayTypeTxt;

        @FXML
        private TextField dayTxt;



        //----------------------------------------------------------//

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

        //----------------------------------------------------------------//
    /** This method launches the MainScreen screen.
     * @param event The selection event. */
        @FXML
        public void onActionExitInventory(ActionEvent event) throws IOException {

                System.out.println("Exit button clicked.");
                // Confirmation dialog box
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {

                        System.out.println("Cancel part button clicked");
                        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                        stage.setScene(new Scene(scene));
                        stage.show();
                    }
                }

    /** This method gets input from Type and Month text fields. It will query the database
     * for matching conditions and the display the total count.
     * Lambda2 expression used for displaying error message if appointment is not found.
     * The reason for this is to make this area of code more readable.
     * @param event The selection event. */
        @FXML
        public void onActionFindTotal(ActionEvent event) throws SQLException {

            int i;
            int month;
            String type;

            try {
                month = Integer.parseInt(monthTxt.getText());
                type = typeTxt.getText();

                if (typeTxt.getText().isEmpty()){

                    // Warning dialog box
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("Enter type");
                    alert.showAndWait();
                }

                else if (!typeTxt.getText().isEmpty()) {

                    i = Query.selectTotalAppointments(month, type);
                    System.out.println(i);
                    totalCountTxt.setText(String.valueOf(i));

                    // lambda expression, two parameters
                    Lambda2 message = (m, t) -> "No appointments found for Month: " + m + " and Type: " + t;

                    if (i == 0) {
                        // Warning dialog box
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Error Dialog");
                        alert.setContentText(message.getMessage(month, type));
                        alert.showAndWait();

                    }
                }
            }
            catch(NumberFormatException e) {
                // Warning dialog box
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error Dialog");
                alert.setContentText("Make sure month number is entered: Ex. 2, 02 for Feb");
                alert.showAndWait();
            }



        }


        /** This method gets input from Type and Day text fields. It will query the database
         * for matching conditions and the display the total count.
         * Lambda1 expression used for displaying error message if appointment is not found.
         * The reason for this is to make this area of code more readable.
     * @param event The selection event. */
        @FXML
        public void onActionFindDay(ActionEvent event) throws SQLException {

            int i;
            int day;
            String type;

                day = LocalDateTime.now().getDayOfYear();
                dayTxt.setText(String.valueOf(day));
                type = dayTypeTxt.getText();


                if (dayTypeTxt.getText().isEmpty()){

                    // Warning dialog box
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("Enter type");
                    alert.showAndWait();
                }

                else if (!dayTypeTxt.getText().isEmpty()) {

                    i = Query.selectTodayAppointments(type);
                    System.out.println(i);
                    dayTotalTxt.setText(String.valueOf(i));

                    // Lambda expression one parameter
                    Lambda1 message = s -> "No appointments found for Today and Type: " + s;

                    if (i == 0) {
                        // Warning dialog box
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Error Dialog");
                        alert.setContentText(message.getMessage(type));
                        alert.showAndWait();

                    }
                }

        }


    /** This method filters for Appointment by the selected Contact object.
     * @param event The selection event. */
        @FXML
        public void onActionFindAppointment(ActionEvent event) {

            // Select and transfer from table selection
            // customerIDTxt.clear();
            int id = 0;

            Contact selectedObj = contactTableView.getSelectionModel().getSelectedItem();
            id = selectedObj.getId();

            appointmentTableView.setItems(Records.lookupContactAppointment(id));

            // Logical check for part selected
            if(selectedObj == null) {
                // Error dialog box
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a country.");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == ButtonType.OK) {
                    return;
                }
            }

            // This shows data in the bottom table
            //appointmentTableView.setItems(Records.getAllAppointments());
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


    /** This allows for initialization of this screen with table data populated.
     * @param url
     * @param resourceBundle */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

            int day;

        day = LocalDateTime.now().getDayOfYear();
        dayTxt.setText(String.valueOf(day));

        try {
            Query.selectContacts();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // This shows data in the Contact table
        contactTableView.setItems(Records.getAllContacts());
        contactIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        contactNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    }
}




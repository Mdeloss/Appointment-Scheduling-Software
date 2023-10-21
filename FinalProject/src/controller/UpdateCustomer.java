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
import model.Country;
import model.Customer;
import model.FirstLevelDivision;
import model.Records;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;


    /** This class controls the actions and functions of the UpdateCustomer view. */
    public class UpdateCustomer implements Initializable {


        // Text fields
        @FXML
        private TextField customerIDTxt;
        @FXML
        private TextField customerNameTxt;
        @FXML
        private TextField customerAddressTxt;
        @FXML
        private TextField customerPostalTxt;
        @FXML
        private TextField customerPhoneTxt;
        @FXML
        private TextField customerDivIDTxt;

        // Country Table
        @FXML
        private TableView<Country> countryTableView;

        @FXML
        private TableColumn<Country, Integer> countryIDCol;
        @FXML
        private TableColumn<Country, String> countryNameCol;

        // Division Table
        @FXML
        private TableView<FirstLevelDivision> divisionTableView;

        @FXML
        private TableColumn<FirstLevelDivision, Integer> DivisionIDCol;
        @FXML
        private TableColumn<FirstLevelDivision, String> StateCol;
        @FXML
        private TableColumn<FirstLevelDivision, Integer> CountryIDCol;


    /*
    // Part Table views. Casting and Wrappers
    @FXML
    private TableView<Part> partTableView;
    @FXML
    private TableColumn<Part, Integer> partIDCol;
    @FXML
    private TableColumn<Part, String> partNameCol;
    @FXML
    private TableColumn<Part,Integer> inventoryCol;
    @FXML
    private TableColumn<Part, Double> priceCol;

    // Associated Table views. Casting and Wrappers
    @FXML
    private TableView<Part> AssocProdTableView;
    @FXML
    private TableColumn<Part, Integer> AssocIDCol;
    @FXML
    private TableColumn<Part, String> AssocNameCol;
    @FXML
    private TableColumn<Part, Integer> AssocInvCol;
    @FXML
    private TableColumn<Part, Double> AssocPriceCol;
     */

        //-------------------------------------------------------//

        Stage stage;
        Parent scene;

        // Object used to hold data
        int id = 0, divID = 0;
        String name = null, address = null, postal = null, phone = null;

        Customer object = new Customer( id, name, address, postal, phone, divID );


        /** This method finds the Divisions of a selected Country from the top table and displays them in
         *  the bottom table.
         * @param event The selection event. */

        @FXML
        public void onActionFindDivision(ActionEvent event) throws IOException {

            System.out.println("Find division button clicked");


            // Select and transfer from table selection
            // customerIDTxt.clear();
            int id = 0;

            Country selectedObj = countryTableView.getSelectionModel().getSelectedItem();
            id = selectedObj.getId();

            divisionTableView.setItems(Records.lookupDivision(id));

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
            //divisionTableView.setItems(Records.getAllFilteredDivisions());
            DivisionIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            StateCol.setCellValueFactory(new PropertyValueFactory<>("state"));
            CountryIDCol.setCellValueFactory(new PropertyValueFactory<>("countryID"));


        }

        /** This method sends a selected Division ID from the Division table to the
         * customerDIVID text field.
         * @param event The selection event. */
        @FXML
        public void onActionAddDivision(ActionEvent event) throws IOException {

            System.out.println("Add division button clicked");


            // Select and transfer from table selection
            // customerIDTxt.clear();
            FirstLevelDivision selectedObj = divisionTableView.getSelectionModel().getSelectedItem();

            // Logical check for part selected
            if (selectedObj == null) {
                // Error dialog box
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a division.");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    return;
                }
            }
            // This grabs the Division ID and places it in the text box
            customerDivIDTxt.setText(String.valueOf(selectedObj.getId()));
        }


        /** This method saves the input provided for editable fields, saves customer list,
         * and returns to the Main Screen.
         * @param event The selection event. */
        @FXML
        public void onActionSaveCustomer(ActionEvent event) throws IOException{

            try {
                System.out.println("Save product button clicked");

                // This will parse typed input
                int id = Integer.parseInt(customerIDTxt.getText());
                String name = customerNameTxt.getText();
                String address = customerAddressTxt.getText();
                String postal = customerPostalTxt.getText();
                String phone = customerPhoneTxt.getText();
                int divID = Integer.parseInt(customerDivIDTxt.getText());


                this.object.setId(id);
                this.object.setName(name);
                this.object.setAddress(address);
                this.object.setPostal_code(postal);
                this.object.setPhone(phone);
                this.object.setDiv_id(divID);

                // This add to Database
                Query.update(id, name, address, postal, phone, divID);

                // Logical check for Name
                if (customerNameTxt.getText().isEmpty()) {
                    // Error dialog box
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a Name");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        return;
                    }
                }
            /*
            // Logical check for Min and Max
            if((min > max) || (min < 0)) {
                // Error dialog box
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid Min and Max value.");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == ButtonType.OK) {
                    return;
                }
            }
            // Logical check for Stock
            if((stock < min) || (stock > max)) {
                // Error dialog box
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid Inventory level.");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == ButtonType.OK) {
                    return;
                }
            }
            // Logical check for Price
            if(price < 0) {
                // Error dialog box
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid Price.");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == ButtonType.OK) {
                    return;
                }
            }

             */

                Records.updateCustomer(id, this.object);

                // This take us back to MainScreen
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
            catch(NumberFormatException | SQLException e) {
                System.out.println("Please enter valid value here.");
                System.out.println("Exception: " + e);
                System.out.println("Exception: " + e.getMessage());

                // Warning dialog box
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error Dialog");
                alert.setContentText("Please enter a valid value for each Text Field!");
                alert.showAndWait();
            }
        }
        /** This method cancels all actions and returns to Main Screen,
         * but will first ask for confirmation.
         * @param event The selection event. */
        @FXML
        public void onActionCancelProduct(ActionEvent event) throws IOException{
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
        public void sendCustomer(Customer object) {

            customerIDTxt.setText(String.valueOf(object.getId()));
            customerNameTxt.setText(object.getName());
            customerAddressTxt.setText(String.valueOf(object.getAddress()));
            customerPostalTxt.setText(String.valueOf(object.getPostal_code()));
            customerPhoneTxt.setText(String.valueOf(object.getPhone()));
            customerDivIDTxt.setText(String.valueOf(object.getDiv_id()));

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
                Query.selectAppointments();
                Query.selectCustomers();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

             */

            // This shows data in the top table
            countryTableView.setItems(Records.getAllCountries());
            countryIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            countryNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        }

    }




package main;

import Utilities.DBConnection;
import Utilities.Implementation;
import Utilities.Query;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.*;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Scanner;

/** This class instantiates the beginning screen of an application that displays inventory.
 * JavaDoc files are located at "C:\Users\LabUser\Downloads\Software 2\FinalProject\javadoc"
 **/

public class Main extends Application {

    /** This is the start method. This calls to show the MainScreen view.
     * @param primaryStage The scene object. */
    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));
       // primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
    }

    /** This is the main method.
     * This is the first method that gets called
     * @param args Used to launch method */
    public static void main(String[] args) throws SQLException, IOException {

        // Set local PC language for testing
        //Locale.setDefault(new Locale("en"));

        // Open Database connection
        DBConnection.openConnection();


        // Launches GUI
        launch(args);

        // Close Database connection
        DBConnection.closeConnection();
    }
}

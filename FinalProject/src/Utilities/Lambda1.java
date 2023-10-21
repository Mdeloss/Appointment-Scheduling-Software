package Utilities;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.Optional;

/** This Interface supports the single parameter expression
 * The reason for this is to make the code that displays an error message more readable.*/
public interface Lambda1 {

    /**@param s the input string*/
    // Single parameter abstract method
    String getMessage(String s);


}

package team.group3.restaurantmanagementsystem.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class OrderMngController {
    public CheckBox QtyInKg;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }


    public void chooseitemBtnOnPress(ActionEvent actionEvent) {
        Parent root;
        try{
            root = FXMLLoader.load(getClass().getResource("/team/group3/restaurantmanagementsystem/MenuItemSelectForm.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Select Item");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
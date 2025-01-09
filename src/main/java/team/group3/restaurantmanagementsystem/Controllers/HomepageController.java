package team.group3.restaurantmanagementsystem.Controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HomepageController {

    public StackPane InventoryMngBtn;
    public StackPane staffMngBtn;
    public StackPane menuMngBtn;
    public StackPane orderMngBtn;
    private Stage stage;


    public void setStage(Stage stage){
        this.stage=stage;
    }



    public void orderManageBtnClk(MouseEvent mouseEvent) {
        if(stage!=null){
            try {
                FXMLLoader root =new FXMLLoader(getClass().getResource("/team/group3/restaurantmanagementsystem/orderManage.fxml"));
                stage.setScene(new Scene(root.load()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

package team.group3.restaurantmanagementsystem.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import team.group3.restaurantmanagementsystem.Database.MenuItemDao;
import team.group3.restaurantmanagementsystem.Model.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MenuItemSelectController {

    @FXML
    public ListView<MenuItem> menuItemListView;

    @FXML
    public Button selectBtn;

    private ObservableList<MenuItem> menuItems;

    private MenuItem selectedMenuItem;

    public void initialize() {
        // Disable the button by default
        selectBtn.setDisable(true);

        // Fetch menu items from the database and populate the ListView
        fetchMenuItems();

        // Add a listener to enable the button when an item is selected
        menuItemListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedMenuItem = newValue;
            selectBtn.setDisable(newValue == null); // Enable button only if an item is selected
        });
    }

    private void fetchMenuItems() {
        try {
            // Fetch items from DAO
            MenuItemDao menuItemDao = new MenuItemDao();
            List<MenuItem> items = menuItemDao.getAllMenuItems();

            List<String> nameAndPrice=new ArrayList<>(items.size());

            for(MenuItem item : items) {
                nameAndPrice.add(""+item.getName()+": "+item.getPrice());
            }

            // Add items to ObservableList and bind to ListView
            menuItems = FXCollections.observableArrayList(items);
            menuItemListView.setItems(menuItems);

        } catch (Exception e) {
            e.printStackTrace();
            // Handle errors (e.g., show an error dialog)
        }
    }

    @FXML
    public void selectItemOnAction(ActionEvent actionEvent) {
        if (selectedMenuItem != null) {
            System.out.println("Selected Item: " + selectedMenuItem.getName());
            // Add additional logic if needed (e.g., pass data to another scene)
        }
    }
}

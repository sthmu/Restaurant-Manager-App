package team.group3.restaurantmanagementsystem.Database;

import team.group3.restaurantmanagementsystem.Model.MenuItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MenuItemDao {

    public List<MenuItem> getAllMenuItems() throws Exception {
        List<MenuItem> menuItems = new ArrayList<>();
        String query = "SELECT item_id, name, price FROM menu_items";

        try (Connection connection = DatabaseConnector.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("item_id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");

                menuItems.add(new MenuItem(id, name, price));
            }
        }
        return menuItems;
    }
}

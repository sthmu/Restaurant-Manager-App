# Menu Management Module

This module is responsible for managing the restaurant’s menu items. It includes adding, updating, retrieving, and removing menu items. Below is a detailed guide to the file structure, responsibilities of each class, and how database interaction is handled.

## File Structure
```
src/
├── main/
│   ├── java/
│   │   └── team.group3.restaurantmanagementsystem/
│   │       ├── Controllers/
│   │       │   └── MenuController.java
│   │       ├── Models/
│   │       │   └── MenuItemModel.java
│   │       ├── DAOs/
│   │       │   └── MenuDAO.java
│   │       └── Database/
│   │           └── DatabaseConnection.java
│   └── resources/
│       └── fxml/
│           └── menuManage.fxml
```

---

## Files and Their Responsibilities

### 1. **Model**: `MenuItemModel.java`
Represents a menu item in the system.

#### Responsibilities:
- Encapsulates data related to menu items.
- Provides getter and setter methods for accessing and modifying fields.
- Implements constructors for creating objects and a `toString` method for debugging purposes.

#### Example Code:
```java
package team.group3.restaurantmanagementsystem.Models;

public class MenuItemModel {
    private int itemId;
    private String itemName;
    private String description;
    private double price;

    // Constructor
    public MenuItemModel(int itemId, String itemName, String description, double price) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.description = description;
        this.price = price;
    }

    // Getters and setters
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "MenuItemModel{" +
               "itemId=" + itemId +
               ", itemName='" + itemName + '\'' +
               ", description='" + description + '\'' +
               ", price=" + price +
               '}';
    }
}
```

---

### 2. **DAO**: `MenuDAO.java`
Handles all database-related operations for menu items.

#### Responsibilities:
- Connects to the database using `DatabaseConnection.java`.
- Performs CRUD operations (Create, Read, Update, Delete) for menu data.

#### Example Code:
```java
package team.group3.restaurantmanagementsystem.DAOs;

import team.group3.restaurantmanagementsystem.Models.MenuItemModel;
import team.group3.restaurantmanagementsystem.Database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuDAO {
    private Connection connection;

    public MenuDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public void addMenuItem(MenuItemModel item) {
        String query = "INSERT INTO menu (itemName, description, price) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, item.getItemName());
            stmt.setString(2, item.getDescription());
            stmt.setDouble(3, item.getPrice());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<MenuItemModel> getAllMenuItems() {
        List<MenuItemModel> items = new ArrayList<>();
        String query = "SELECT * FROM menu";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                items.add(new MenuItemModel(
                        rs.getInt("itemId"),
                        rs.getString("itemName"),
                        rs.getString("description"),
                        rs.getDouble("price")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public void updateMenuItem(MenuItemModel item) {
        String query = "UPDATE menu SET itemName = ?, description = ?, price = ? WHERE itemId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, item.getItemName());
            stmt.setString(2, item.getDescription());
            stmt.setDouble(3, item.getPrice());
            stmt.setInt(4, item.getItemId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteMenuItem(int itemId) {
        String query = "DELETE FROM menu WHERE itemId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, itemId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```

---

### 3. **Controller**: `MenuController.java`
Coordinates the UI and the DAO, handling logic for menu management operations.

#### Responsibilities:
- Invokes DAO methods for database operations.
- Validates input from the UI before passing data to the DAO.

#### Example Code:
```java
package team.group3.restaurantmanagementsystem.Controllers;

import team.group3.restaurantmanagementsystem.DAOs.MenuDAO;
import team.group3.restaurantmanagementsystem.Models.MenuItemModel;

import java.util.List;

public class MenuController {
    private MenuDAO menuDAO = new MenuDAO();

    public void addItem(String itemName, String description, double price) {
        MenuItemModel item = new MenuItemModel(0, itemName, description, price);
        menuDAO.addMenuItem(item);
    }

    public List<MenuItemModel> getAllItems() {
        return menuDAO.getAllMenuItems();
    }

    public void updateItem(int itemId, String itemName, String description, double price) {
        MenuItemModel item = new MenuItemModel(itemId, itemName, description, price);
        menuDAO.updateMenuItem(item);
    }

    public void deleteItem(int itemId) {
        menuDAO.deleteMenuItem(itemId);
    }
}
```

---

### 4. **FXML**: `menuManage.fxml`
Contains the UI layout for menu management.

#### Responsibilities:
- Provides a visual interface for managing menu items.
- Contains controls like text fields, tables, and buttons to interact with the menu.

---

### 5. **Database Connection**: `DatabaseConnection.java`
Handles the initialization of the database connection.

#### Example Code:
```java
package team.group3.restaurantmanagementsystem.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/restaurant", "root", "password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
```

---

## Notes:
1. **Error Handling**: Ensure proper error messages for failed operations.
2. **Input Validation**: Validate data before saving or updating menu items.
3. **Testing**: Test DAO methods independently with dummy data.
4. **Integration**: Ensure smooth integration with the main application.


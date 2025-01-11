# Inventory Management Module

මෙම මොඩියුලය ආපනශාලා කළමනාකරණ පද්ධතියේ ගබඩා කළමනාකරණය සඳහා වගකියයි. මෙය ගබඩා අයිතම එකතු කිරීම, යාවත්කාලීන කිරීම, ලබා ගැනීම සහ ඉවත් කිරීම සැලසේ. ගබඩා කළමනාකරණ මොඩියුලය ක්‍රියාත්මක කිරීමට අදාළ ලිපිගොනු ව්‍යුහය, එක් එක් පන්තියේ වගකීම් සහ දත්ත ගබඩාව සමඟ සම්ප්‍රේෂණය කිරීමට ආශ්‍රිත විස්තරාත්මක මාර්ගෝපදේශයක් පහත දැක්වේ.

## File Structure
```
src/
├── main/
│   ├── java/
│   │   └── team.group3.restaurantmanagementsystem/
│   │       ├── Controllers/
│   │       │   └── InventoryController.java
│   │       ├── Models/
│   │       │   └── InventoryModel.java
│   │       ├── DAOs/
│   │       │   └── InventoryDAO.java
│   │       └── Database/
│   │           └── DatabaseConnection.java
│   └── resources/
│       └── fxml/
│           └── inventoryManage.fxml
```

---

## Files and Their Responsibilities

### 1. **Model**: `InventoryModel.java`
Represents an inventory item in the system.

#### Responsibilities:
- Encapsulates data related to inventory items.
- Provides getter and setter methods for accessing and modifying fields.
- Implements constructors for creating objects and a `toString` method for debugging purposes.

#### Example Code:
```java
package team.group3.restaurantmanagementsystem.Models;

public class InventoryModel {
    private int itemId;
    private String itemName;
    private int quantity;
    private double unitPrice;

    // Constructor
    public InventoryModel(int itemId, String itemName, int quantity, double unitPrice) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "InventoryModel{" +
               "itemId=" + itemId +
               ", itemName='" + itemName + '\'' +
               ", quantity=" + quantity +
               ", unitPrice=" + unitPrice +
               '}';
    }
}
```

---

### 2. **DAO**: `InventoryDAO.java`
Handles all database-related operations for inventory items.

#### Responsibilities:
- Connects to the database using `DatabaseConnection.java`.
- Performs CRUD operations (Create, Read, Update, Delete) for inventory data.

#### Example Code:
```java
package team.group3.restaurantmanagementsystem.DAOs;

import team.group3.restaurantmanagementsystem.Models.InventoryModel;
import team.group3.restaurantmanagementsystem.Database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAO {
    private Connection connection;

    public InventoryDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public void addInventoryItem(InventoryModel item) {
        String query = "INSERT INTO inventory (itemName, quantity, unitPrice) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, item.getItemName());
            stmt.setInt(2, item.getQuantity());
            stmt.setDouble(3, item.getUnitPrice());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<InventoryModel> getAllInventoryItems() {
        List<InventoryModel> items = new ArrayList<>();
        String query = "SELECT * FROM inventory";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                items.add(new InventoryModel(
                        rs.getInt("itemId"),
                        rs.getString("itemName"),
                        rs.getInt("quantity"),
                        rs.getDouble("unitPrice")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public void updateInventoryItem(InventoryModel item) {
        String query = "UPDATE inventory SET itemName = ?, quantity = ?, unitPrice = ? WHERE itemId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, item.getItemName());
            stmt.setInt(2, item.getQuantity());
            stmt.setDouble(3, item.getUnitPrice());
            stmt.setInt(4, item.getItemId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteInventoryItem(int itemId) {
        String query = "DELETE FROM inventory WHERE itemId = ?";
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

### 3. **Controller**: `InventoryController.java`
Coordinates the UI and the DAO, handling logic for inventory management operations.

#### Responsibilities:
- Invokes DAO methods for database operations.
- Validates input from the UI before passing data to the DAO.

#### Example Code:
```java
package team.group3.restaurantmanagementsystem.Controllers;

import team.group3.restaurantmanagementsystem.DAOs.InventoryDAO;
import team.group3.restaurantmanagementsystem.Models.InventoryModel;

import java.util.List;

public class InventoryController {
    private InventoryDAO inventoryDAO = new InventoryDAO();

    public void addItem(String itemName, int quantity, double unitPrice) {
        InventoryModel item = new InventoryModel(0, itemName, quantity, unitPrice);
        inventoryDAO.addInventoryItem(item);
    }

    public List<InventoryModel> getAllItems() {
        return inventoryDAO.getAllInventoryItems();
    }

    public void updateItem(int itemId, String itemName, int quantity, double unitPrice) {
        InventoryModel item = new InventoryModel(itemId, itemName, quantity, unitPrice);
        inventoryDAO.updateInventoryItem(item);
    }

    public void deleteItem(int itemId) {
        inventoryDAO.deleteInventoryItem(itemId);
    }
}
```

---

### 4. **FXML**: `inventoryManage.fxml`
Contains the UI layout for inventory management.

#### Responsibilities:
- Provides a visual interface for managing inventory items.
- Contains controls like text fields, tables, and buttons to interact with the inventory.

---

### 5. **Database Connection**: `DatabaseConnection.java`
Centralizes the initialization of the database connection in a separate class. This approach promotes code reusability and simplifies connection management across the application, ensuring a single point of configuration for database access.

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
2. **Input Validation**: Validate data before saving or updating inventory items.
3. **Testing**: Test DAO methods independently with dummy data.
4. **Integration**: Ensure smooth integration with the main application.


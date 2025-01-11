### **File Structure for Inventory Management**

Managing inventory in your restaurant management system involves tracking items, their quantities, and statuses (e.g., in stock, out of stock). Here's a structured approach to implementing inventory management in your application:

---

### **Suggested File Structure**

#### 1. **Model Layer**
- Represents the structure of an inventory item and its attributes.
- **File: `InventoryItem.java`**
    - Location: `src/main/java/team/group3/restaurantmanagementsystem/models/`

   ```java
   package team.group3.restaurantmanagementsystem.models;

   public class InventoryItem {
       private int itemId;
       private String name;
       private String category;
       private int quantity;
       private String status;

       // Constructors, Getters, and Setters
       public InventoryItem(int itemId, String name, String category, int quantity, String status) {
           this.itemId = itemId;
           this.name = name;
           this.category = category;
           this.quantity = quantity;
           this.status = status;
       }

       // Additional methods like toString(), equals(), hashCode() if necessary
   }
   ```

---

#### 2. **Database Layer (DAO - Data Access Object)**
- Handles all database interactions for inventory records.
- **File: `InventoryDAO.java`**
    - Location: `src/main/java/team/group3/restaurantmanagementsystem/dao/`

   ```java
   package team.group3.restaurantmanagementsystem.dao;

   import team.group3.restaurantmanagementsystem.models.InventoryItem;
   import java.sql.*;
   import java.util.ArrayList;
   import java.util.List;

   public class InventoryDAO {
       private Connection connection;

       public InventoryDAO(Connection connection) {
           this.connection = connection;
       }

       public void addInventoryItem(InventoryItem item) throws SQLException {
           String query = "INSERT INTO inventory (name, category, quantity, status) VALUES (?, ?, ?, ?)";
           try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
               preparedStatement.setString(1, item.getName());
               preparedStatement.setString(2, item.getCategory());
               preparedStatement.setInt(3, item.getQuantity());
               preparedStatement.setString(4, item.getStatus());
               preparedStatement.executeUpdate();
           }
       }

       public List<InventoryItem> getAllItems() throws SQLException {
           String query = "SELECT * FROM inventory";
           List<InventoryItem> itemList = new ArrayList<>();
           try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
               while (resultSet.next()) {
                   InventoryItem item = new InventoryItem(
                       resultSet.getInt("item_id"),
                       resultSet.getString("name"),
                       resultSet.getString("category"),
                       resultSet.getInt("quantity"),
                       resultSet.getString("status")
                   );
                   itemList.add(item);
               }
           }
           return itemList;
       }
   }
   ```

---

#### 3. **Service Layer**
- Contains business logic for inventory-related operations.
- **File: `InventoryService.java`**
    - Location: `src/main/java/team/group3/restaurantmanagementsystem/services/`

   ```java
   package team.group3.restaurantmanagementsystem.services;

   import team.group3.restaurantmanagementsystem.dao.InventoryDAO;
   import team.group3.restaurantmanagementsystem.models.InventoryItem;

   import java.sql.SQLException;
   import java.util.List;

   public class InventoryService {
       private InventoryDAO inventoryDAO;

       public InventoryService(InventoryDAO inventoryDAO) {
           this.inventoryDAO = inventoryDAO;
       }

       public void addItem(InventoryItem item) throws SQLException {
           inventoryDAO.addInventoryItem(item);
       }

       public List<InventoryItem> getAllItems() throws SQLException {
           return inventoryDAO.getAllItems();
       }
   }
   ```

---

#### 4. **Controller Layer**
- Handles user interactions and coordinates between the UI and the service layer.
- **File: `InventoryController.java`**
    - Location: `src/main/java/team/group3/restaurantmanagementsystem/controllers/`

   ```java
   package team.group3.restaurantmanagementsystem.controllers;

   import team.group3.restaurantmanagementsystem.models.InventoryItem;
   import team.group3.restaurantmanagementsystem.services.InventoryService;

   import java.sql.SQLException;
   import java.util.List;

   public class InventoryController {
       private InventoryService inventoryService;

       public InventoryController(InventoryService inventoryService) {
           this.inventoryService = inventoryService;
       }

       public void createInventoryItem(String name, String category, int quantity, String status) {
           InventoryItem item = new InventoryItem(0, name, category, quantity, status);
           try {
               inventoryService.addItem(item);
           } catch (SQLException e) {
               System.err.println("Error creating inventory item: " + e.getMessage());
           }
       }

       public List<InventoryItem> listAllItems() {
           try {
               return inventoryService.getAllItems();
           } catch (SQLException e) {
               System.err.println("Error fetching inventory items: " + e.getMessage());
               return List.of();
           }
       }
   }
   ```

---

### **Database Structure (Example)**
Your `inventory` table in the database can look like this:
```sql
CREATE TABLE inventory (
    item_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    category VARCHAR(100),
    quantity INT,
    status VARCHAR(50)
);
```

---

### **Summary**
- **Model Layer**: Represents the structure of an inventory item.
- **DAO Layer**: Handles CRUD operations for inventory items in the database.
- **Service Layer**: Contains business logic for inventory management.
- **Controller Layer**: Interfaces with the UI for inventory-related actions.

This modular design ensures a clean separation of concerns, making it easier to add, view, or modify inventory records.
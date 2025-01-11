### **File Structure for Menu Management**

Managing the menu involves organizing menu items, their prices, descriptions, and categories. Here's how you can structure the menu management module in your restaurant management system:

---

### **Suggested File Structure**

#### 1. **Model Layer**
- Represents the structure of a menu item and its attributes.
- **File: `MenuItem.java`**
    - Location: `src/main/java/team/group3/restaurantmanagementsystem/models/`

   ```java
   package team.group3.restaurantmanagementsystem.models;

   public class MenuItem {
       private int itemId;
       private String name;
       private String category;
       private double price;
       private String description;

       // Constructors, Getters, and Setters
       public MenuItem(int itemId, String name, String category, double price, String description) {
           this.itemId = itemId;
           this.name = name;
           this.category = category;
           this.price = price;
           this.description = description;
       }

       // Additional methods like toString(), equals(), hashCode() if necessary
   }
   ```

---

#### 2. **Database Layer (DAO - Data Access Object)**
- Handles all database interactions for menu records.
- **File: `MenuDAO.java`**
    - Location: `src/main/java/team/group3/restaurantmanagementsystem/dao/`

   ```java
   package team.group3.restaurantmanagementsystem.dao;

   import team.group3.restaurantmanagementsystem.models.MenuItem;
   import java.sql.*;
   import java.util.ArrayList;
   import java.util.List;

   public class MenuDAO {
       private Connection connection;

       public MenuDAO(Connection connection) {
           this.connection = connection;
       }

       public void addMenuItem(MenuItem menuItem) throws SQLException {
           String query = "INSERT INTO menu (name, category, price, description) VALUES (?, ?, ?, ?)";
           try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
               preparedStatement.setString(1, menuItem.getName());
               preparedStatement.setString(2, menuItem.getCategory());
               preparedStatement.setDouble(3, menuItem.getPrice());
               preparedStatement.setString(4, menuItem.getDescription());
               preparedStatement.executeUpdate();
           }
       }

       public List<MenuItem> getAllMenuItems() throws SQLException {
           String query = "SELECT * FROM menu";
           List<MenuItem> menuItems = new ArrayList<>();
           try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
               while (resultSet.next()) {
                   MenuItem menuItem = new MenuItem(
                       resultSet.getInt("item_id"),
                       resultSet.getString("name"),
                       resultSet.getString("category"),
                       resultSet.getDouble("price"),
                       resultSet.getString("description")
                   );
                   menuItems.add(menuItem);
               }
           }
           return menuItems;
       }
   }
   ```

---

#### 3. **Service Layer**
- Contains business logic for menu-related operations.
- **File: `MenuService.java`**
    - Location: `src/main/java/team/group3/restaurantmanagementsystem/services/`

   ```java
   package team.group3.restaurantmanagementsystem.services;

   import team.group3.restaurantmanagementsystem.dao.MenuDAO;
   import team.group3.restaurantmanagementsystem.models.MenuItem;

   import java.sql.SQLException;
   import java.util.List;

   public class MenuService {
       private MenuDAO menuDAO;

       public MenuService(MenuDAO menuDAO) {
           this.menuDAO = menuDAO;
       }

       public void addMenuItem(MenuItem menuItem) throws SQLException {
           menuDAO.addMenuItem(menuItem);
       }

       public List<MenuItem> getAllMenuItems() throws SQLException {
           return menuDAO.getAllMenuItems();
       }
   }
   ```

---

#### 4. **Controller Layer**
- Handles user interactions and coordinates between the UI and the service layer.
- **File: `MenuController.java`**
    - Location: `src/main/java/team/group3/restaurantmanagementsystem/controllers/`

   ```java
   package team.group3.restaurantmanagementsystem.controllers;

   import team.group3.restaurantmanagementsystem.models.MenuItem;
   import team.group3.restaurantmanagementsystem.services.MenuService;

   import java.sql.SQLException;
   import java.util.List;

   public class MenuController {
       private MenuService menuService;

       public MenuController(MenuService menuService) {
           this.menuService = menuService;
       }

       public void createMenuItem(String name, String category, double price, String description) {
           MenuItem menuItem = new MenuItem(0, name, category, price, description);
           try {
               menuService.addMenuItem(menuItem);
           } catch (SQLException e) {
               System.err.println("Error adding menu item: " + e.getMessage());
           }
       }

       public List<MenuItem> listAllMenuItems() {
           try {
               return menuService.getAllMenuItems();
           } catch (SQLException e) {
               System.err.println("Error fetching menu items: " + e.getMessage());
               return List.of();
           }
       }
   }
   ```

---

### **Database Structure (Example)**
Your `menu` table in the database can look like this:
```sql
CREATE TABLE menu (
    item_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    category VARCHAR(100),
    price DECIMAL(10, 2),
    description TEXT
);
```

---

### **Summary**
- **Model Layer**: Represents the structure of a menu item.
- **DAO Layer**: Handles CRUD operations for menu items in the database.
- **Service Layer**: Contains business logic for menu management.
- **Controller Layer**: Interfaces with the UI for menu-related actions.

This structured design ensures clean separation of concerns, making it easy to add, update, and display menu items effectively in your application.
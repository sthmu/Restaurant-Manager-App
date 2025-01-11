For managing orders in your restaurant management system, you'll need a well-structured file organization to maintain clarity and modularity. Below is a suggested structure:

---

### **Suggested File Structure**

#### 1. **Model Layer**
- Responsible for defining the data structure and basic operations for the `Order` object.
- **File: `Order.java`**
    - Location: `src/main/java/team/group3/restaurantmanagementsystem/models/`

   ```java
   package team.group3.restaurantmanagementsystem.models;

   public class Order {
       private int orderId;
       private String customerName;
       private List<String> items; // Could also use a custom Item class
       private double totalPrice;
       private String status; // e.g., "Pending", "Completed", etc.
       private LocalDateTime orderDate;

       // Constructors, Getters, and Setters
       public Order(int orderId, String customerName, List<String> items, double totalPrice, String status, LocalDateTime orderDate) {
           this.orderId = orderId;
           this.customerName = customerName;
           this.items = items;
           this.totalPrice = totalPrice;
           this.status = status;
           this.orderDate = orderDate;
       }

       // Additional Methods like toString(), equals(), hashCode() if necessary
   }
   ```

---

#### 2. **Database Layer (DAO - Data Access Object)**
- Responsible for interacting with the database to perform CRUD operations.
- **File: `OrderDAO.java`**
    - Location: `src/main/java/team/group3/restaurantmanagementsystem/dao/`

   ```java
   package team.group3.restaurantmanagementsystem.dao;

   import team.group3.restaurantmanagementsystem.models.Order;
   import java.sql.*;
   import java.util.ArrayList;
   import java.util.List;

   public class OrderDAO {
       private Connection connection;

       public OrderDAO(Connection connection) {
           this.connection = connection;
       }

       public void saveOrder(Order order) throws SQLException {
           String query = "INSERT INTO orders (customer_name, items, total_price, status, order_date) VALUES (?, ?, ?, ?, ?)";
           try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
               preparedStatement.setString(1, order.getCustomerName());
               preparedStatement.setString(2, String.join(",", order.getItems())); // Assuming items are comma-separated
               preparedStatement.setDouble(3, order.getTotalPrice());
               preparedStatement.setString(4, order.getStatus());
               preparedStatement.setTimestamp(5, Timestamp.valueOf(order.getOrderDate()));
               preparedStatement.executeUpdate();
           }
       }

       public List<Order> getAllOrders() throws SQLException {
           String query = "SELECT * FROM orders";
           List<Order> orders = new ArrayList<>();
           try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
               while (resultSet.next()) {
                   Order order = new Order(
                       resultSet.getInt("order_id"),
                       resultSet.getString("customer_name"),
                       List.of(resultSet.getString("items").split(",")), // Convert back to list
                       resultSet.getDouble("total_price"),
                       resultSet.getString("status"),
                       resultSet.getTimestamp("order_date").toLocalDateTime()
                   );
                   orders.add(order);
               }
           }
           return orders;
       }
   }
   ```

---

#### 3. **Service Layer**
- Acts as a bridge between the controller and DAO for business logic.
- **File: `OrderService.java`**
    - Location: `src/main/java/team/group3/restaurantmanagementsystem/services/`

   ```java
   package team.group3.restaurantmanagementsystem.services;

   import team.group3.restaurantmanagementsystem.dao.OrderDAO;
   import team.group3.restaurantmanagementsystem.models.Order;

   import java.sql.SQLException;
   import java.util.List;

   public class OrderService {
       private OrderDAO orderDAO;

       public OrderService(OrderDAO orderDAO) {
           this.orderDAO = orderDAO;
       }

       public void addOrder(Order order) throws SQLException {
           orderDAO.saveOrder(order);
       }

       public List<Order> getOrders() throws SQLException {
           return orderDAO.getAllOrders();
       }
   }
   ```

---

#### 4. **Controller Layer**
- Manages user interaction and calls service methods as needed.
- **File: `OrderController.java`**
    - Location: `src/main/java/team/group3/restaurantmanagementsystem/controllers/`

   ```java
   package team.group3.restaurantmanagementsystem.controllers;

   import team.group3.restaurantmanagementsystem.models.Order;
   import team.group3.restaurantmanagementsystem.services.OrderService;

   import java.sql.SQLException;
   import java.time.LocalDateTime;
   import java.util.List;

   public class OrderController {
       private OrderService orderService;

       public OrderController(OrderService orderService) {
           this.orderService = orderService;
       }

       public void createOrder(String customerName, List<String> items, double totalPrice) {
           Order order = new Order(0, customerName, items, totalPrice, "Pending", LocalDateTime.now());
           try {
               orderService.addOrder(order);
           } catch (SQLException e) {
               System.err.println("Error creating order: " + e.getMessage());
           }
       }

       public List<Order> listOrders() {
           try {
               return orderService.getOrders();
           } catch (SQLException e) {
               System.err.println("Error fetching orders: " + e.getMessage());
               return List.of();
           }
       }
   }
   ```

---

### **Database Structure (Example)**
Your `orders` table in the database can look like this:
```sql
CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(255),
    items TEXT,
    total_price DECIMAL(10, 2),
    status VARCHAR(50),
    order_date TIMESTAMP
);
```

---

### **Summary**
- **Model Layer**: Represents the `Order` structure.
- **DAO Layer**: Handles database interactions (CRUD operations).
- **Service Layer**: Contains business logic and communicates with DAO.
- **Controller Layer**: Interfaces with the UI or other inputs to process user actions.

This structure separates concerns, ensuring that each component of your application has a focused responsibility, making the code easier to maintain and extend.
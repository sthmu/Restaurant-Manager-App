Based on the file structure shown in your screenshot, here's an updated section for the **Order Management Module** that matches your folder layout:

---

## **Order Management Module**

The Order Management module is responsible for managing orders in the restaurant management system. It follows the **Model-View-Controller (MVC)** architecture, ensuring clean separation of concerns for maintainability and scalability.

### **File Structure**
```
src/
└── main/
    ├── java/
    │   └── team/group3/restaurantmanagementsystem/
    │       ├── Controllers/
    │       │   └── OrderController.java  # Handles user interactions and business logic.
    │       ├── HelloApplication.java     # Entry point of the JavaFX application.
    │       └── module-info.java          # Java module system configuration.
    └── resources/
        └── team/group3/restaurantmanagementsystem/
            ├── assets/                   # Contains resources such as images, icons, etc.
            ├── homepage.fxml             # Defines the UI layout for the homepage.
            └── orderManage.fxml          # Defines the UI layout for managing orders.
```

---

### **Model Structure (`Order.java`)**

The `Order` class represents the structure of an order object. It encapsulates all the fields related to an order and provides methods for data handling.

```java
package team.group3.restaurantmanagementsystem;

public class Order {
    private int orderId;
    private String customerName;
    private String items; // JSON or comma-separated list of items
    private double totalAmount;
    private String orderTime; // Format: "YYYY-MM-DD HH:MM:SS"

    // Constructor
    public Order(int orderId, String customerName, String items, double totalAmount, String orderTime) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.items = items;
        this.totalAmount = totalAmount;
        this.orderTime = orderTime;
    }

    // Getters and Setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", customerName='" + customerName + '\'' +
                ", items='" + items + '\'' +
                ", totalAmount=" + totalAmount +
                ", orderTime='" + orderTime + '\'' +
                '}';
    }
}
```

---

### **Controller (`OrderController.java`)**

The `OrderController` handles the user interactions and database logic for the Order Management module. It ensures communication between the model and the view.

```java
package team.group3.restaurantmanagementsystem.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import team.group3.restaurantmanagementsystem.Order;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderController {

    @FXML
    private TableView<Order> ordersTable; // A TableView defined in the orderManage.fxml

    private static final String DB_URL = "jdbc:mysql://localhost:3306/restaurant_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    // Load all orders from the database and populate the table
    public void loadOrders() {
        List<Order> orders = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM orders";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int orderId = resultSet.getInt("order_id");
                String customerName = resultSet.getString("customer_name");
                String items = resultSet.getString("items");
                double totalAmount = resultSet.getDouble("total_amount");
                String orderTime = resultSet.getString("order_time");

                orders.add(new Order(orderId, customerName, items, totalAmount, orderTime));
            }

            // Populate the TableView
            ordersTable.getItems().setAll(orders);

        } catch (Exception e) {
            showError("Error loading orders: " + e.getMessage());
        }
    }

    // Helper method to display error messages
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Add more methods here (e.g., addOrder, deleteOrder) as needed
}
```

---

### **Key Features**

- The **Model** encapsulates the structure of an order with fields such as `orderId`, `customerName`, `items`, `totalAmount`, and `orderTime`.
- The **Controller** handles loading data from the database (`loadOrders`), populating the view, and handling user interactions.
- FXML files such as `orderManage.fxml` define the layout for managing orders.

This structure aligns with the current file layout shown in your project.
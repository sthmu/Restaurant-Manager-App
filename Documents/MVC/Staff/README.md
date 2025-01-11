Here's the **Staff Management Module** with a **Data Access Object (DAO)** structure for managing database operations:

---

## **Staff Management Module**

The Staff Management module is responsible for handling staff-related data and operations. It uses **Model-View-Controller (MVC)** architecture with a separate **Data Access Object (DAO)** layer for database interactions. This structure ensures better separation of concerns and code maintainability.

---

### **File Structure**
```
src/
└── main/
    ├── java/
    │   └── team/group3/restaurantmanagementsystem/
    │       ├── Controllers/
    │       │   └── StaffController.java    # Handles user interactions and updates the view.
    │       ├── DAOs/
    │       │   └── StaffDAO.java           # Handles all database operations for staff.
    │       ├── Models/
    │       │   └── Staff.java              # Represents the structure of a staff record.
    │       ├── HelloApplication.java       # Entry point of the JavaFX application.
    │       └── module-info.java            # Java module system configuration.
    └── resources/
        └── team/group3/restaurantmanagementsystem/
            ├── assets/                     # Contains resources such as images, icons, etc.
            ├── homepage.fxml               # Defines the UI layout for the homepage.
            └── staffManage.fxml            # Defines the UI layout for managing staff records.
```

---

### **Model Structure (`Staff.java`)**

The `Staff` class represents the structure of a staff record.

```java
package team.group3.restaurantmanagementsystem.Models;

public class Staff {
    private int staffId;
    private String name;
    private String position;
    private double salary;
    private String contactNumber;

    // Constructor
    public Staff(int staffId, String name, String position, double salary, String contactNumber) {
        this.staffId = staffId;
        this.name = name;
        this.position = position;
        this.salary = salary;
        this.contactNumber = contactNumber;
    }

    // Getters and Setters
    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "staffId=" + staffId +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", salary=" + salary +
                ", contactNumber='" + contactNumber + '\'' +
                '}';
    }
}
```

---

### **Data Access Object (`StaffDAO.java`)**

The `StaffDAO` class handles all database operations related to staff.

```java
package team.group3.restaurantmanagementsystem.DAOs;

import team.group3.restaurantmanagementsystem.Models.Staff;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StaffDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/restaurant_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    // Retrieve all staff records from the database
    public List<Staff> getAllStaff() {
        List<Staff> staffList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM staff";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int staffId = resultSet.getInt("staff_id");
                String name = resultSet.getString("name");
                String position = resultSet.getString("position");
                double salary = resultSet.getDouble("salary");
                String contactNumber = resultSet.getString("contact_number");

                staffList.add(new Staff(staffId, name, position, salary, contactNumber));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return staffList;
    }

    // Add a new staff member to the database
    public void addStaff(Staff staff) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO staff (name, position, salary, contact_number) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, staff.getName());
            statement.setString(2, staff.getPosition());
            statement.setDouble(3, staff.getSalary());
            statement.setString(4, staff.getContactNumber());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add more methods for updating and deleting staff records as needed
}
```

---

### **Controller (`StaffController.java`)**

The `StaffController` handles user interactions and communicates with the DAO for database operations.

```java
package team.group3.restaurantmanagementsystem.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import team.group3.restaurantmanagementsystem.DAOs.StaffDAO;
import team.group3.restaurantmanagementsystem.Models.Staff;

import java.util.List;

public class StaffController {

    @FXML
    private TableView<Staff> staffTable; // TableView defined in staffManage.fxml

    private StaffDAO staffDAO;

    public StaffController() {
        this.staffDAO = new StaffDAO();
    }

    // Load staff records into the table
    public void loadStaff() {
        try {
            List<Staff> staffList = staffDAO.getAllStaff();
            staffTable.getItems().setAll(staffList);
        } catch (Exception e) {
            showError("Error loading staff records: " + e.getMessage());
        }
    }

    // Helper method to display error messages
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Add methods to handle user interactions for adding, updating, and deleting staff
}
```

---

### **Key Features**

1. **Model (`Staff.java`)**: Defines the structure of staff records.
2. **DAO (`StaffDAO.java`)**: Handles all database operations like retrieving, adding, updating, and deleting staff records.
3. **Controller (`StaffController.java`)**: Interacts with the view and uses the DAO for database operations.
4. **View (`staffManage.fxml`)**: The UI for managing staff records, displaying them in a `TableView`.

This separation of concerns makes the code modular and easier to test, debug, and maintain.
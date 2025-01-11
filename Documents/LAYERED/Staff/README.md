### **File Structure for Staff Management**

Managing staff records in your restaurant management system will require a similar structure to the order management module. Here’s how you can structure the files:

---

### **Suggested File Structure**

#### 1. **Model Layer**
- Represents the structure of a staff member and contains its attributes.
- **File: `Staff.java`**
    - Location: `src/main/java/team/group3/restaurantmanagementsystem/models/`

   ```java
   package team.group3.restaurantmanagementsystem.models;

   public class Staff {
       private int staffId;
       private String name;
       private String position;
       private String contactNumber;
       private String email;
       private double salary;

       // Constructors, Getters, and Setters
       public Staff(int staffId, String name, String position, String contactNumber, String email, double salary) {
           this.staffId = staffId;
           this.name = name;
           this.position = position;
           this.contactNumber = contactNumber;
           this.email = email;
           this.salary = salary;
       }

       // Additional methods like toString(), equals(), hashCode() if necessary
   }
   ```

---

#### 2. **Database Layer (DAO - Data Access Object)**
- Handles all interactions with the database for staff records.
- **File: `StaffDAO.java`**
    - Location: `src/main/java/team/group3/restaurantmanagementsystem/dao/`

   ```java
   package team.group3.restaurantmanagementsystem.dao;

   import team.group3.restaurantmanagementsystem.models.Staff;
   import java.sql.*;
   import java.util.ArrayList;
   import java.util.List;

   public class StaffDAO {
       private Connection connection;

       public StaffDAO(Connection connection) {
           this.connection = connection;
       }

       public void saveStaff(Staff staff) throws SQLException {
           String query = "INSERT INTO staff (name, position, contact_number, email, salary) VALUES (?, ?, ?, ?, ?)";
           try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
               preparedStatement.setString(1, staff.getName());
               preparedStatement.setString(2, staff.getPosition());
               preparedStatement.setString(3, staff.getContactNumber());
               preparedStatement.setString(4, staff.getEmail());
               preparedStatement.setDouble(5, staff.getSalary());
               preparedStatement.executeUpdate();
           }
       }

       public List<Staff> getAllStaff() throws SQLException {
           String query = "SELECT * FROM staff";
           List<Staff> staffList = new ArrayList<>();
           try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
               while (resultSet.next()) {
                   Staff staff = new Staff(
                       resultSet.getInt("staff_id"),
                       resultSet.getString("name"),
                       resultSet.getString("position"),
                       resultSet.getString("contact_number"),
                       resultSet.getString("email"),
                       resultSet.getDouble("salary")
                   );
                   staffList.add(staff);
               }
           }
           return staffList;
       }
   }
   ```

---

#### 3. **Service Layer**
- Contains business logic for staff-related operations.
- **File: `StaffService.java`**
    - Location: `src/main/java/team/group3/restaurantmanagementsystem/services/`

   ```java
   package team.group3.restaurantmanagementsystem.services;

   import team.group3.restaurantmanagementsystem.dao.StaffDAO;
   import team.group3.restaurantmanagementsystem.models.Staff;

   import java.sql.SQLException;
   import java.util.List;

   public class StaffService {
       private StaffDAO staffDAO;

       public StaffService(StaffDAO staffDAO) {
           this.staffDAO = staffDAO;
       }

       public void addStaff(Staff staff) throws SQLException {
           staffDAO.saveStaff(staff);
       }

       public List<Staff> getStaff() throws SQLException {
           return staffDAO.getAllStaff();
       }
   }
   ```

---

#### 4. **Controller Layer**
- Handles user interactions and passes data between the UI and the service layer.
- **File: `StaffController.java`**
    - Location: `src/main/java/team/group3/restaurantmanagementsystem/controllers/`

   ```java
   package team.group3.restaurantmanagementsystem.controllers;

   import team.group3.restaurantmanagementsystem.models.Staff;
   import team.group3.restaurantmanagementsystem.services.StaffService;

   import java.sql.SQLException;
   import java.util.List;

   public class StaffController {
       private StaffService staffService;

       public StaffController(StaffService staffService) {
           this.staffService = staffService;
       }

       public void createStaff(String name, String position, String contactNumber, String email, double salary) {
           Staff staff = new Staff(0, name, position, contactNumber, email, salary);
           try {
               staffService.addStaff(staff);
           } catch (SQLException e) {
               System.err.println("Error creating staff: " + e.getMessage());
           }
       }

       public List<Staff> listStaff() {
           try {
               return staffService.getStaff();
           } catch (SQLException e) {
               System.err.println("Error fetching staff: " + e.getMessage());
               return List.of();
           }
       }
   }
   ```

---

### **Database Structure (Example)**
Your `staff` table in the database can look like this:
```sql
CREATE TABLE staff (
    staff_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    position VARCHAR(100),
    contact_number VARCHAR(15),
    email VARCHAR(255),
    salary DECIMAL(10, 2)
);
```

---

### **Summary**
- **Model Layer**: Represents the structure of a staff member.
- **DAO Layer**: Handles CRUD operations for staff in the database.
- **Service Layer**: Contains business logic for staff management.
- **Controller Layer**: Interfaces with the UI for staff-related actions.

This modular design ensures that each layer is independent and manageable, making it easy to extend or debug in the future.
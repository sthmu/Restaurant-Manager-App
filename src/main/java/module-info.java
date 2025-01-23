module team.group3.restaurantmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;

    opens team.group3.restaurantmanagementsystem to javafx.fxml;
    exports team.group3.restaurantmanagementsystem;

    opens team.group3.restaurantmanagementsystem.Controllers to javafx.fxml;
    exports team.group3.restaurantmanagementsystem.Controllers;
    exports team.group3.restaurantmanagementsystem.Database;
    opens team.group3.restaurantmanagementsystem.Database to javafx.fxml;
}
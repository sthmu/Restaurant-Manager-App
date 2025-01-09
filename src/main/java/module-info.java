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

    opens team.group3.restaurantmanagementsystem to javafx.fxml;
    exports team.group3.restaurantmanagementsystem;

    opens team.group3.restaurantmanagementsystem.Controllers to javafx.fxml;
    exports team.group3.restaurantmanagementsystem.Controllers;
}
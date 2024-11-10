module net.jaskar.events {
    requires javafx.controls;
    requires javafx.fxml;


    opens net.jaskar.events to javafx.fxml;
    exports net.jaskar.events;
}
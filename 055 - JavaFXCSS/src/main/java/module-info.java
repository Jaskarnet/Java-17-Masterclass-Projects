module net.jaskar.javafxcss {
    requires java.desktop;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.web;


    opens net.jaskar.javafxcss to javafx.fxml;
    exports net.jaskar.javafxcss;
}
module net.jaskar.controls {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    // icon pack modules:
    requires org.kordamp.ikonli.material2;

    opens net.jaskar.controls to javafx.fxml;
    exports net.jaskar.controls;
}
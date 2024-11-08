module net.jaskar.javafxlayouts {
    requires javafx.controls;
    requires javafx.fxml;


    opens net.jaskar.javafxlayouts to javafx.fxml;
    exports net.jaskar.javafxlayouts;
}
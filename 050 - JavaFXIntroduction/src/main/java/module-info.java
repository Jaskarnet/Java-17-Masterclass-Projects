module net.jaskar.javafxintroduction {
    requires javafx.controls;
    requires javafx.fxml;


    opens net.jaskar.javafxintroduction to javafx.fxml;
    exports net.jaskar.javafxintroduction;
}
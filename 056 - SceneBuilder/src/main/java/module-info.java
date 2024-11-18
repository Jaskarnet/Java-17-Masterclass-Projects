module net.jaskar.scenebuilder {
    requires javafx.controls;
    requires javafx.fxml;


    opens net.jaskar.scenebuilder to javafx.fxml;
    exports net.jaskar.scenebuilder;
}
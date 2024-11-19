module net.jaskar.mycontacts {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;


    opens net.jaskar.mycontacts to javafx.fxml;
    exports net.jaskar.mycontacts;
    exports net.jaskar.mycontacts.datamodel;
    opens net.jaskar.mycontacts.datamodel to javafx.fxml;
}
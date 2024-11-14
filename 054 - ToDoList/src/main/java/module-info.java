module net.jaskar.todolist {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires org.kordamp.ikonli.javafx;

    opens net.jaskar.todolist to javafx.fxml;
    exports net.jaskar.todolist;
}
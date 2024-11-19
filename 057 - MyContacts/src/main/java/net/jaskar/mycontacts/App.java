package net.jaskar.mycontacts;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.jaskar.mycontacts.datamodel.ContactData;

import java.io.IOException;

public class App extends Application {
    private final ContactData contactData = new ContactData();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("mainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1090, 500);
        MainWindowController controller = fxmlLoader.getController();
        contactData.loadContacts();
        controller.setContactData(contactData);
        stage.setTitle("My Contacts App");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        contactData.saveContacts();
    }

    public static void main(String[] args) {
        launch();
    }
}
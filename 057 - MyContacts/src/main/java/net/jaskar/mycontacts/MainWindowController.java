package net.jaskar.mycontacts;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import net.jaskar.mycontacts.datamodel.Contact;
import net.jaskar.mycontacts.datamodel.ContactData;

import java.io.IOException;
import java.util.Optional;

public class MainWindowController {
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private TableView<Contact> tableView;
    private ContextMenu contextMenu;
    private ContactData contactData;

    public void initialize() {
        contextMenu = new ContextMenu();
        MenuItem editMenuItem = new MenuItem("Edit");
        editMenuItem.setOnAction(event -> {
            Contact contact = tableView.getSelectionModel().getSelectedItem();
            editContact(contact);
        });
        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(event -> {
            Contact contact = tableView.getSelectionModel().getSelectedItem();
            deleteContact(contact);
        });
        contextMenu.getItems().addAll(editMenuItem, deleteMenuItem);
        tableView.setRowFactory(tv -> {
            TableRow<Contact> row = new TableRow<>();

            row.emptyProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    row.setContextMenu(null);
                } else  {
                    row.setContextMenu(contextMenu);
                }
            });

            return row;
        });
    }

    private void editContact(Contact contact) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Edit Contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("contactDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        ContactDialogController controller = fxmlLoader.getController();
        controller.editContact(contact);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            controller.updateContact(contact);
        } else {
            System.out.println("Cancel pressed");
        }
    }

    public void deleteContact(Contact contact) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Contact");
        alert.setHeaderText("Delete contact: " + contact.getFirstName() + " " + contact.getLastName());
        alert.setContentText("Are you sure? Press OK to confirm, or cancel to Back out.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            contactData.deleteContact(contact);
        }
    }

    @FXML
    public void showNewContactDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Add New Contact");
        dialog.setHeaderText("Use this dialog to create a new contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("contactDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            throw new RuntimeException(e);
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ContactDialogController controller = fxmlLoader.getController();
            Contact newContact = controller.createContact();
            contactData.addContact(newContact);
        } else {
            System.out.println("Cancel pressed");
        }
    }

    public void setContactData(ContactData contactData) {
        this.contactData = contactData;
        tableView.setItems(contactData.getContacts());
    }
}
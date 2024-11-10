package net.jaskar.events;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {
    @FXML
    private TextField nameField;
    @FXML
    private Button helloButton;
    @FXML
    private Button byeButton;
    @FXML
    private CheckBox ourCheckbox;
    @FXML
    private Label ourLabel;

    @FXML
    public void initialize() {
        disableButtons();
    }

    // Not necessary annotation for Event Handlers but helps with readability
    @FXML
    public void onButtonClicked(ActionEvent e) {
        if (e.getSource().equals(helloButton)) {
            System.out.println("Hello, " + nameField.getText());
        } else {
            System.out.println("Bye, " + nameField.getText());
        }
        Runnable task = () -> {
            try {
                String s = Platform.isFxApplicationThread() ? "UI Thread" : "Background Thread";
                System.out.println("I'm going to sleep on the: " + s);
                Thread.sleep(10000);
                Platform.runLater(() -> {
                    String s2 = Platform.isFxApplicationThread() ? "UI Thread" : "Background Thread";
                    System.out.println("I'm updating the label on the: " + s2);
                    ourLabel.setText("We did something!");
                });
            } catch (InterruptedException ex) {
                // We don't care about this
            }
        };

        new Thread(task).start();

        if (ourCheckbox.isSelected()) {
            nameField.clear();
            disableButtons();
        }
    }

    @FXML
    public void handleKeyReleased() {
        String text = nameField.getText();
        boolean disableButtons = text.trim().isEmpty();
        helloButton.setDisable(disableButtons);
        byeButton.setDisable(disableButtons);
    }

    @FXML
    public void handleChange() {
        System.out.println("The checkbox is " + ((ourCheckbox.isSelected()) ? "checked" : "not checked"));
    }

    private void disableButtons() {
        helloButton.setDisable(true);
        byeButton.setDisable(true);
    }
}
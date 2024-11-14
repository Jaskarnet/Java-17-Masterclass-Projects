package net.jaskar.todolist;

import javafx.application.Platform;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import net.jaskar.todolist.datamodel.IconToggleButton;
import net.jaskar.todolist.datamodel.TodoData;
import net.jaskar.todolist.datamodel.TodoItem;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Predicate;

public class MainWindowController {
    @FXML
    private ListView<TodoItem> todoListView;
    @FXML
    private TextArea itemDetailsTextArea;
    @FXML
    private Label deadlineLabel;
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private ContextMenu listContextMenu;
    @FXML
    private ToggleButton filterToggleButton;
    @FXML
    private HBox toolBarHBox;
    private FilteredList<TodoItem> filteredList;
    private FontIcon fntIcon;
    private Predicate<TodoItem> wantAllItems;
    private Predicate<TodoItem> wantTodaysItems;



    public void initialize() {
        fntIcon = new FontIcon("mdmz-reorder");
        fntIcon.setIconColor(Paint.valueOf("linear-gradient(red, chocolate, black)"));
        fntIcon.setIconSize(20);

        // Find the index of the original filterToggleButton in the HBox
        int index = toolBarHBox.getChildren().indexOf(filterToggleButton);

        // Create the IconToggleButton using the builder
        filterToggleButton = new IconToggleButton.Builder()
                .icon(new FontIcon("mdmz-reorder"))
                .colorsSelected("red")
                .colorsNotSelected("red", "chocolate", "black")
                .tooltipSelected("Show all items")
                .tooltipNotSelected("Show Today's items only")
                .tooltipDelay(100)
                .onAction(event -> handleFilterButton())
                .iconSize(20)
                .build();

        // Replace the original filterToggleButton with the new iconToggleButton
        if (index >= 0) {
            toolBarHBox.getChildren().set(index, filterToggleButton);
        }

        listContextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(event -> {
            TodoItem item = todoListView.getSelectionModel().getSelectedItem();
            deleteItem(item);
        });

        listContextMenu.getItems().addAll(deleteMenuItem);
        todoListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                TodoItem item = todoListView.getSelectionModel().getSelectedItem();
                itemDetailsTextArea.setText(item.getDetails());
                DateTimeFormatter df = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.US);
                deadlineLabel.setText(df.format(item.getDeadline()));
            }
        });

        wantAllItems = item -> true;
        wantTodaysItems = item -> item.getDeadline().equals(LocalDate.now());

        filteredList = new FilteredList<>(TodoData.getInstance().getTodoItems());

        SortedList<TodoItem> sortedList = new SortedList<>(
                filteredList,
                Comparator.comparing(
                        (TodoItem item) -> item.getDeadline().isBefore(LocalDate.now()))
                        .thenComparing(TodoItem::getDeadline)
        );

//        todoListView.setItems(TodoData.getInstance().getTodoItems());
        todoListView.setItems(sortedList);
        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        todoListView.getSelectionModel().selectFirst();

        todoListView.setCellFactory(param -> {
            ListCell<TodoItem> cell = new ListCell<>() {
                @Override
                protected void updateItem(TodoItem item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(item.getShortDescription());
                        if (item.getDeadline().isBefore(LocalDate.now())) {
                            setTextFill(Color.GRAY);
                        } else if (item.getDeadline().equals(LocalDate.now())) {
                            setTextFill(Color.RED);
                        } else if (item.getDeadline().equals(LocalDate.now().plusDays(1))) {
                            setTextFill(Color.CHOCOLATE);
                        }
                    }
                }
            };

            cell.emptyProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    cell.setContextMenu(null);
                } else {
                    cell.setContextMenu(listContextMenu);
                }
            });

            return cell;
        });
    }

    @FXML
    private void showNewItemDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Add New Todo Item");
        dialog.setHeaderText("Use this dialog to create a new todo item");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("todoItemDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            DialogController controller = fxmlLoader.getController();
            TodoItem newItem = controller.processResults();
            todoListView.getSelectionModel().select(newItem);
        } else {
            System.out.println("Cancel pressed");
        }
    }

    public void handleKeyPressed(KeyEvent keyEvent) {
        TodoItem selectedItem = todoListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            if (keyEvent.getCode().equals(KeyCode.DELETE)) {
                deleteItem(selectedItem);
            }
        }
    }

    public void deleteItem(TodoItem item) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Todo Item");
        alert.setHeaderText("Delete item: " + item.getShortDescription());
        alert.setContentText("Are you sure? Press OK to confirm, or cancel to Back out.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            TodoData.getInstance().deleteTodoItem(item);
        }
    }

    @FXML
    public void handleFilterButton() {
        TodoItem selectedItem = todoListView.getSelectionModel().getSelectedItem();
        if (filterToggleButton.isSelected()) {
            filteredList.setPredicate(wantTodaysItems);
            if (filteredList.isEmpty()) {
                itemDetailsTextArea.clear();
                deadlineLabel.setText("");
            } else if (filteredList.contains(selectedItem)) {
                todoListView.getSelectionModel().select(selectedItem);
            } else {
                todoListView.getSelectionModel().selectFirst();
            }
        } else {
            filteredList.setPredicate(wantAllItems);
            todoListView.getSelectionModel().select(selectedItem);
        }
    }

    @FXML
    public void handleExit() {
        Platform.exit();
    }
}
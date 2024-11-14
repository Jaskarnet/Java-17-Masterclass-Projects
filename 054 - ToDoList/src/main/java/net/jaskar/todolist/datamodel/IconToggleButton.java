package net.jaskar.todolist.datamodel;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Paint;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IconToggleButton extends ToggleButton {
    private FontIcon icon;
    private List<String> colorsSelected;
    private List<String> colorsNotSelected;
    private String tooltipSelected;
    private String tooltipNotSelected;
    private int tooltipDelay; // Delay time for tooltip in milliseconds

    // Private constructor to be used by the builder
    private IconToggleButton(Builder builder) {
        this.icon = builder.icon;
        this.colorsSelected = builder.colorsSelected;
        this.colorsNotSelected = builder.colorsNotSelected;
        this.tooltipSelected = builder.tooltipSelected;
        this.tooltipNotSelected = builder.tooltipNotSelected;
        this.tooltipDelay = builder.tooltipDelay; // Set the tooltip delay time

        // Set initial icon size
        if (builder.iconSize > 0) {
            icon.setIconSize(builder.iconSize);
        }

        // Set the action if provided
        if (builder.onAction != null) {
            setOnAction(builder.onAction);
        }

        // Initial update for the icon and tooltip based on the current selection state
        updateIcon(isSelected());

        // Listener to change icon color and tooltip text when toggled
        selectedProperty().addListener((observable, oldValue, newValue) -> updateIcon(newValue));
    }

    // Method to update the icon and tooltip based on selection state
    private void updateIcon(boolean isSelected) {
        // Choose the appropriate color list
        List<String> colors = isSelected ? colorsSelected : colorsNotSelected;
        String iconColor;

        // Build a gradient string "linear-gradient(red, chocolate, black)"
        if (colors.isEmpty()) {
            iconColor = "black";
        } else if (colors.size() == 1) {
            iconColor = colors.get(0);
        } else {
            iconColor = String.format("linear-gradient(%s)", String.join(", ", colors));
        }

        // Set the icon color to the gradient
        icon.setIconColor(Paint.valueOf(iconColor));
        setGraphic(icon);

        // Update the tooltip text based on selection state
        String tooltipText = isSelected ? tooltipSelected : tooltipNotSelected;
        Tooltip tooltip = new Tooltip(tooltipText);

        // Set the show delay for the tooltip
        tooltip.setShowDelay(javafx.util.Duration.millis(tooltipDelay));

        setTooltip(tooltip);
    }

    // Builder class
    public static class Builder {
        private FontIcon icon;
        private final List<String> colorsSelected = new ArrayList<>();
        private final List<String> colorsNotSelected = new ArrayList<>();
        private String tooltipSelected = "Show all items";  // Default text
        private String tooltipNotSelected = "Show Today's items only";  // Default text
        private int iconSize = 16;  // Default icon size
        private EventHandler<ActionEvent> onAction;  // Action event handler
        private int tooltipDelay = 500;  // Default delay time in milliseconds

        // Builder method to set the icon
        public Builder icon(FontIcon icon) {
            this.icon = icon;
            return this;
        }

        // Builder method to set the icon size
        public Builder iconSize(int size) {
            this.iconSize = size;
            return this;
        }

        // Builder method to set the colors for the selected state
        public Builder colorsSelected(String... colors) {
            this.colorsSelected.clear();
            this.colorsSelected.addAll(Arrays.asList(colors));
            return this;
        }

        // Builder method to set the colors for the not-selected state
        public Builder colorsNotSelected(String... colors) {
            this.colorsNotSelected.clear();
            this.colorsNotSelected.addAll(Arrays.asList(colors));
            return this;
        }

        // Builder method to set tooltip text for the selected state
        public Builder tooltipSelected(String tooltipText) {
            this.tooltipSelected = tooltipText;
            return this;
        }

        // Builder method to set tooltip text for the not-selected state
        public Builder tooltipNotSelected(String tooltipText) {
            this.tooltipNotSelected = tooltipText;
            return this;
        }

        // Builder method to set an onAction event handler
        public Builder onAction(EventHandler<ActionEvent> eventHandler) {
            this.onAction = eventHandler;
            return this;
        }

        // Builder method to set the ms time for the tooltip (in milliseconds)
        public Builder tooltipDelay(int ms) {
            this.tooltipDelay = ms;
            return this;
        }

        // Build method to create the IconToggleButton instance
        public IconToggleButton build() {
            return new IconToggleButton(this);
        }
    }
}

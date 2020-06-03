package tools;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class CustomBox extends ComboBox<String> {
    private ObservableList<String> initialList;
    private ObservableList<String> bufferList = FXCollections.observableArrayList();
    private String previousValue = "";

    public CustomBox(ObservableList<String> items) {

        super(items);
        super.setEditable(true);
        this.initialList = items;

        this.configAutoFilterListener();
    }

    private void configAutoFilterListener() {
        final CustomBox currentInstance = this;
        this.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                previousValue = oldValue;
                final TextField editor = currentInstance.getEditor();
                final String selected = currentInstance.getSelectionModel().getSelectedItem();

                if (selected == null || !selected.equals(editor.getText())) {
                    filterItems(newValue, currentInstance);

                    currentInstance.show();
                    if (currentInstance.getItems().size() == 1) {
                        setUserInputToOnlyOption(currentInstance, editor);
                    }
                }
            }
        });
    }

    private void filterItems(String filter, ComboBox<String> comboBox) {
        if (filter.startsWith(previousValue) && !previousValue.isEmpty()) {
            ObservableList<String> filteredList = this.readFromList(filter, bufferList);
            bufferList.clear();
            bufferList = filteredList;
        } else {
            bufferList = this.readFromList(filter, initialList);
        }
        comboBox.setItems(bufferList);
    }

    private ObservableList<String> readFromList(String filter, ObservableList<String> originalList) {
        ObservableList<String> filteredList = FXCollections.observableArrayList();
        for (String item : originalList) {
            if (item.toLowerCase().startsWith(filter.toLowerCase())) {
                filteredList.add(item);
            }
        }

        return filteredList;
    }

    private void setUserInputToOnlyOption(ComboBox<String> currentInstance, final TextField editor) {
        final String onlyOption = currentInstance.getItems().get(0);
        final String currentText = editor.getText();
        if (onlyOption.length() > currentText.length()) {
            editor.setText(onlyOption);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    editor.selectRange(currentText.length(), onlyOption.length());
                }
            });
        }
    }
}
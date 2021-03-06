package org.hui1601.emulator.platform.application.views.tabs;

import javafx.collections.ObservableMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.VBox;
import org.hui1601.emulator.platform.application.views.actions.AddChatMessageAction;
import org.hui1601.emulator.platform.ui.components.IListView;
import org.hui1601.emulator.platform.ui.components.ITextArea;
import org.hui1601.emulator.utils.ResourceUtils;

public class DebugRoomTab {
    private static ObservableMap<String, Object> nameSpace;

    private static VBox root;

    public static void initialize() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ResourceUtils.getForm("DebugRoomTab"));
        loader.setController(null);
        loader.load();

        nameSpace = loader.getNamespace();

        root = loader.getRoot();

        IListView listView = (IListView) nameSpace.get("listView");
        ITextArea textArea = (ITextArea) nameSpace.get("textArea");
        Button button = (Button) nameSpace.get("button");

        textArea.textProperty().addListener((observable, oldString, newString) ->
        {
            button.setDisable(newString.trim().isEmpty());
        });

        button.setOnAction(event ->
        {
            AddChatMessageAction.update(textArea.getText(), false);
            textArea.requestFocus();
            textArea.clear();
        });

        textArea.requestFocus();

        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listView.setOnKeyPressed(event ->
        {
            if (event.isControlDown()) {
                switch (event.getCode()) {
                    case X:
                        listView.cut();
                        break;
                    case C:
                        listView.copy();
                        break;
                }
            }

            switch (event.getCode()) {
                case DELETE:
                    listView.delete();
                    break;
            }
        });
    }

    public static Node getRoot() {
        return root;
    }

    public static IListView getComponent() {
        return (IListView) root.getChildren().get(0);
    }

    public static ObservableMap<String, Object> getNameSpace() {
        return nameSpace;
    }
}
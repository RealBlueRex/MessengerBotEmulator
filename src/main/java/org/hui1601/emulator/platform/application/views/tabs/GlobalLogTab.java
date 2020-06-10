package org.hui1601.emulator.platform.application.views.tabs;

import javafx.collections.ObservableMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import org.hui1601.emulator.platform.ui.components.ILogView;
import org.hui1601.emulator.utils.ResourceUtils;

public class GlobalLogTab {
    private static ObservableMap<String, Object> nameSpace;

    private static AnchorPane root;

    public static void initialize() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ResourceUtils.getForm("GlobalLogTab"));
        loader.setController(null);
        loader.load();

        nameSpace = loader.getNamespace();

        root = loader.getRoot();
    }

    public static Node getRoot() {
        return root;
    }

    public static ILogView getComponent() {
        return (ILogView) root.getChildren().get(0);
    }
}
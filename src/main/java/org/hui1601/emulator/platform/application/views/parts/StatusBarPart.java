package org.hui1601.emulator.platform.application.views.parts;

import javafx.collections.ObservableMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import org.hui1601.emulator.utils.ResourceUtils;

public class StatusBarPart {
    private static ObservableMap<String, Object> nameSpace;

    private static AnchorPane root;

    public static void initialize() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ResourceUtils.getForm("StatusBarPart"));
        loader.setController(null);
        loader.load();

        nameSpace = loader.getNamespace();

        root = loader.getRoot();
    }

    public static Node getRoot() {
        return root;
    }

    public static ObservableMap<String, Object> getNameSpace() {
        return nameSpace;
    }
}
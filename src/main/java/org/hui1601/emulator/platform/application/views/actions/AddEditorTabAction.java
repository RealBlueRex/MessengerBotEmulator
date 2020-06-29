package org.hui1601.emulator.platform.application.views.actions;

import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import org.hui1601.emulator.platform.application.views.parts.EditorAreaPart;
import org.hui1601.emulator.platform.ui.editor.IEditorPane;
import org.hui1601.emulator.platform.ui.editor.IEditorTab;
import org.hui1601.emulator.utils.ResourceUtils;

import java.util.List;

public class AddEditorTabAction {
    private static SplitPane pane;

    public static void initialize() {
        pane = EditorAreaPart.getComponent();
    }

    public static void update(String title) {
        update(title, null);
    }

    public static void update(String title, Node node) {
        update(title, title, node);
    }

    public static void update(String id, String title, Node content) {
        update(ResourceUtils.getImage("tab_default"), id, title, content);
    }

    public static void update(Image image, String id, String title, Node content) {
        List<Node> list = pane.getItems();

        for (Node node : list) {
            IEditorPane pane = (IEditorPane) node;

            if (pane.isTabExists(id)) {
                pane.selectTab(id);

                return;
            }
        }

        EditorAreaPart.getSelectedPane().addTab(new IEditorTab(image, id, title, content));
    }
}
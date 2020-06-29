package org.hui1601.emulator.platform.application.views.actions;

import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import org.hui1601.emulator.platform.application.views.parts.EditorAreaPart;
import org.hui1601.emulator.platform.ui.editor.IEditorPane;

import java.util.List;

public class SaveAllEditorTabsAction {
    private static SplitPane pane;

    public static void initialize() {
        pane = EditorAreaPart.getComponent();
    }

    public static void update() {
        List<Node> nodes = pane.getItems();

        for (Node node : nodes) {
            IEditorPane editor = (IEditorPane) node;

            SaveEditorTabAction.update(editor.getTabList());
        }
    }
}
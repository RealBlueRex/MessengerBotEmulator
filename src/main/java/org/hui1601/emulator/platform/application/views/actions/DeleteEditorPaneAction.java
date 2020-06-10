package org.hui1601.emulator.platform.application.views.actions;

import javafx.scene.control.SplitPane;
import org.hui1601.emulator.platform.application.views.parts.EditorAreaPart;
import org.hui1601.emulator.platform.ui.editor.IEditorPane;

public class DeleteEditorPaneAction {
    private static SplitPane pane;

    public static void initialize() {
        pane = EditorAreaPart.getComponent();
    }

    public static void update(IEditorPane editor) {
        pane.getItems().remove(editor);
    }
}
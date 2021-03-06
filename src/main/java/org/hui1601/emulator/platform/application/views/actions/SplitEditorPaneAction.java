package org.hui1601.emulator.platform.application.views.actions;

import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import org.hui1601.emulator.platform.application.views.dialogs.ShowWarningDialog;
import org.hui1601.emulator.platform.application.views.parts.EditorAreaPart;
import org.hui1601.emulator.platform.ui.editor.IEditorPane;
import org.hui1601.emulator.platform.ui.editor.IEditorTab;

import java.net.MalformedURLException;
import java.util.List;

public class SplitEditorPaneAction {
    private static SplitPane pane;

    public static void initialize() {
        pane = EditorAreaPart.getComponent();
    }

    // 기본값 오른쪽
    public static void update() {
        update(pane.getItems().size());
    }

    public static void update(int index) {
        if (pane.getItems().size() < 4) {
            pane.getItems().add(index, new IEditorPane());
        } else {
            new ShowWarningDialog("The editor can no longer be split").display();
        }
    }

    // 현재 가로만 지원
    public static void update(IEditorTab tab, Side pos) throws MalformedURLException {
        // IEditor Pane
        List<Node> panes = pane.getItems();

        IEditorPane editor = (IEditorPane) tab.getTabPane();

        int index = panes.indexOf(editor);

        switch (pos) {
            case RIGHT:
                update(index + 1);
                break;
            case LEFT:
                update(index);
                break;
        }
    }
}
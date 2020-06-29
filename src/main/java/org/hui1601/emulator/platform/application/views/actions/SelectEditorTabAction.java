package org.hui1601.emulator.platform.application.views.actions;

import javafx.geometry.Side;
import org.hui1601.emulator.platform.ui.components.ITab;
import org.hui1601.emulator.platform.ui.components.ITabPane;

public class SelectEditorTabAction {
    public static void update(ITabPane pane, Side pos) {
        update(pane.getSelectedTab(), pos);
    }

    public static void update(ITab tab, Side pos) {
        ITabPane pane = tab.getITabPane();

        pane.selectTab(tab, pos);
    }
}
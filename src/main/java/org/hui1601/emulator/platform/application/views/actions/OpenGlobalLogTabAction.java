package org.hui1601.emulator.platform.application.views.actions;

import org.hui1601.emulator.platform.application.views.tabs.GlobalLogTab;
import org.hui1601.emulator.platform.ui.components.ILogView;

public class OpenGlobalLogTabAction {
    private static ILogView listView;

    public static void initialize() {
        listView = GlobalLogTab.getComponent();
    }

    public static void update() {
        AddEditorTabAction.update("@global::log", "Global Log", listView);
    }
}
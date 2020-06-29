package org.hui1601.emulator.platform.application.views.actions;

import org.hui1601.emulator.managers.LogManager;
import org.hui1601.emulator.platform.application.views.tabs.GlobalLogTab;
import org.hui1601.emulator.platform.ui.components.ILogItem;
import org.hui1601.emulator.platform.ui.components.ILogView;

public class AddBotLogItemAction {
    private static ILogView listView;

    public static void initialize() {
        listView = GlobalLogTab.getComponent();
    }

    // Global
    public static void update(ILogItem item) {
        listView.addItem(item);
        listView.scrollToLast();
    }

    public static void update(String name, ILogItem item) {
        ILogView logView = LogManager.getView(name);

        if (logView != null) {
            logView.addItem(item);
            logView.scrollToLast();
        }
    }
}
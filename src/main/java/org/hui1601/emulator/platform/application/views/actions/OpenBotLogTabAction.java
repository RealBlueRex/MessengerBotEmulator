package org.hui1601.emulator.platform.application.views.actions;

import org.hui1601.emulator.managers.LogManager;

public class OpenBotLogTabAction {
    // Bot Name
    public static void update(String name) {
        AddEditorTabAction.update
                (
                        "@log::" + name,
                        "Log : " + name,
                        LogManager.getView(name)
                );
    }
}
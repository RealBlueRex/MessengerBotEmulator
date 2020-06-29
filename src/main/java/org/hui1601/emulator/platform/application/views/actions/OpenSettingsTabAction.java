package org.hui1601.emulator.platform.application.views.actions;

import javafx.scene.layout.StackPane;
import org.hui1601.emulator.platform.application.views.tabs.SettingsTab;

public class OpenSettingsTabAction {
    private static StackPane pane;

    public static void initialize() {
        pane = SettingsTab.getRoot();
    }

    public static void update() {
        AddEditorTabAction.update("@global::settings", "Settings", pane);
    }
}
package org.hui1601.emulator.platform.application.views.actions;

import javafx.scene.layout.StackPane;
import org.hui1601.emulator.platform.application.views.tabs.GlobalSettingsTab;

public class OpenSettingsTabAction {
    private static StackPane pane;

    public static void initialize() {
        pane = GlobalSettingsTab.getRoot();
    }

    public static void update() {
        AddEditorTabAction.update("@global::settings", "Settings", pane);
    }
}
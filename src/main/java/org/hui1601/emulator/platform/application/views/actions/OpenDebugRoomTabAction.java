package org.hui1601.emulator.platform.application.views.actions;

import javafx.scene.Node;
import org.hui1601.emulator.platform.application.views.tabs.DebugRoomTab;
import org.hui1601.emulator.settings.Settings;
import org.hui1601.emulator.utils.ResourceUtils;

public class OpenDebugRoomTabAction {
    private static Node pane;

    public static void initialize() {
        pane = DebugRoomTab.getRoot();
    }

    public static void update() {
        AddEditorTabAction.update
                (
                        ResourceUtils.getImage("tab_debug"),
                        "@global::debug",
                        Settings.getPublicSetting("debug").getString("room"),
                        pane
                );
    }
}
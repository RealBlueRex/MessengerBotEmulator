package org.hui1601.emulator.platform.application.views.actions;

import org.hui1601.emulator.managers.BotManager;
import org.hui1601.emulator.managers.FileManager;
import org.hui1601.emulator.managers.LogManager;
import org.hui1601.emulator.platform.application.views.parts.ActiveAreaPart;
import org.hui1601.emulator.platform.ui.components.IListView;
import org.hui1601.emulator.platform.ui.components.ILogView;

import java.io.File;

public class RefreshBotsAction {
    private static IListView listView;

    public static void initialize() {
        listView = ActiveAreaPart.BotsTab.getComponent();
    }

    public static void update() {
        listView.getItems().clear();

        BotManager.data.clear();
        LogManager.data.clear();

        File[] folders = FileManager.BOTS_FOLDER.listFiles(File::isDirectory);

        for (File folder : folders) {
            String name = folder.getName();

            AddExplorerBotAction.update(name);

            LogManager.data.put(name, new ILogView(name));
        }
    }
}
package org.hui1601.emulator.platform.application.views.actions;

import org.hui1601.emulator.managers.FileManager;
import org.hui1601.emulator.platform.application.views.dialogs.ExistsBotDialog;

import java.io.File;
import java.net.MalformedURLException;

public class RenameBotAction {
    public static void update(String name, String newName) {
        File before = FileManager.getBotFolder(name);
        File after = FileManager.getBotFolder(newName);

        if (after.exists()) {
            new ExistsBotDialog(newName).display();
        } else {
            before.renameTo(after);
        }
    }
}

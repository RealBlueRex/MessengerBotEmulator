package org.hui1601.emulator.platform.application.views.actions;

import org.hui1601.emulator.managers.FileManager;
import org.hui1601.emulator.platform.application.views.dialogs.ShowErrorDialog;

import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;

public class OpenDesktopAction {
    public static void update() {
        update(FileManager.BOTS_FOLDER);
    }

    public static void update(String path) {
        update(new File(path));
    }

    public static void update(File file) {
        try {
            Desktop.getDesktop().open(file);
        } catch (Exception e) {
            new ShowErrorDialog(e).display();
        }
    }
}
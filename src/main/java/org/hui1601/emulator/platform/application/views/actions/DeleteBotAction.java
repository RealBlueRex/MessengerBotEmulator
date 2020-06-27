package org.hui1601.emulator.platform.application.views.actions;

import org.hui1601.emulator.managers.FileManager;

import java.net.MalformedURLException;

public class DeleteBotAction {
    public static void update(String name) {
        FileManager.remove(FileManager.getBotFolder(name));
    }
}

package org.beuwi.simulator.platform.application.views.actions;

import org.beuwi.simulator.managers.FileManager;

import java.net.MalformedURLException;

public class DeleteBotAction
{
    public static void update(String name) throws MalformedURLException {
        FileManager.remove(FileManager.getBotFolder(name));
    }
}

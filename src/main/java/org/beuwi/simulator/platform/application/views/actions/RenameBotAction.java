package org.beuwi.simulator.platform.application.views.actions;

import org.beuwi.simulator.managers.FileManager;
import org.beuwi.simulator.platform.application.views.dialogs.ExistsBotDialog;

import java.io.File;
import java.net.MalformedURLException;

public class RenameBotAction
{
    public static void update(String name, String newName) throws MalformedURLException {
        File before = FileManager.getBotFolder(name);
        File after  = FileManager.getBotFolder(newName);

        if (after.exists())
        {
            new ExistsBotDialog(newName).display();
        }
        else
        {
            before.renameTo(after);
        }
    }
}

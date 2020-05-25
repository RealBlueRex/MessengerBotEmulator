package org.beuwi.simulator.platform.application.views.actions;

import org.beuwi.simulator.managers.FileManager;
import org.beuwi.simulator.platform.application.views.dialogs.ShowErrorDialog;

import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;

public class OpenDesktopAction
{
	public static void update() throws MalformedURLException {
		update(FileManager.BOTS_FOLDER);
	}

	public static void update(String path) throws MalformedURLException {
		update(new File(path));
	}

	public static void update(File file) throws MalformedURLException {
		try
		{
			Desktop.getDesktop().open(file);
		}
		catch (Exception e)
		{
			new ShowErrorDialog(e).display();
		}
	}
}
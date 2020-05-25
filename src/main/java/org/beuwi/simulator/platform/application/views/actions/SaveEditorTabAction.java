package org.beuwi.simulator.platform.application.views.actions;

import org.beuwi.simulator.managers.FileManager;
import org.beuwi.simulator.platform.application.views.parts.EditorAreaPart;
import org.beuwi.simulator.platform.ui.components.ICodeArea;
import org.beuwi.simulator.platform.ui.components.ITab;

import java.net.MalformedURLException;
import java.util.List;

public class SaveEditorTabAction
{
	public static void update() throws MalformedURLException {
		update(EditorAreaPart.getSelectedPane().getSelectedTab());
	}

	public static void update(String name) throws MalformedURLException {
		update(EditorAreaPart.getSelectedPane().getTabItem("@script::" + name));
	}

	public static void update(ITab tab) throws MalformedURLException {
		if (tab != null)
		{
			if (tab.getType().equals("script"))
			{
				FileManager.save(FileManager.getBotScript(tab.getName()), ((ICodeArea) tab.getContent()).getText());
			}
		}
	}

	public static void update(List<ITab> tabs) throws MalformedURLException {
		for (ITab tab : tabs)
		{
			if (tab.getType().equals("script"))
			{
				update(tab);
			}
		}
	}
}
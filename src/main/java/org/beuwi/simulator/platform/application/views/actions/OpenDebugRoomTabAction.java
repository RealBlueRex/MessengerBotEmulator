package org.beuwi.simulator.platform.application.views.actions;

import javafx.scene.Node;
import org.beuwi.simulator.platform.application.views.tabs.DebugRoomTab;
import org.beuwi.simulator.settings.Settings;
import org.beuwi.simulator.utils.ResourceUtils;

import java.net.MalformedURLException;

public class OpenDebugRoomTabAction
{
	private static Node pane;

	public static void initialize()
	{
		pane = DebugRoomTab.getRoot();
	}

	public static void update() throws MalformedURLException {
		AddEditorTabAction.update
		(
			ResourceUtils.getImage("tab_debug"),
			"@global::debug",
			Settings.getPublicSetting("debug").getString("room"),
			pane
		);
	}
}
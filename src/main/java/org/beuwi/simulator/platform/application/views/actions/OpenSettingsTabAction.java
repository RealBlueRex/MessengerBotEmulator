package org.beuwi.simulator.platform.application.views.actions;

import javafx.scene.layout.StackPane;
import org.beuwi.simulator.platform.application.views.tabs.SettingsTab;

import java.net.MalformedURLException;

public class OpenSettingsTabAction
{
	private static StackPane pane;

	public static void initialize()
	{
		pane = SettingsTab.getRoot();
	}

	public static void update() {
		AddEditorTabAction.update("@global::settings", "Settings", pane);
	}
}
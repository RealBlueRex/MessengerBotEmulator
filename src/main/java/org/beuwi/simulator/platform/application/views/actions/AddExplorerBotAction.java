package org.beuwi.simulator.platform.application.views.actions;

import com.jfoenix.controls.JFXToggleButton;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.beuwi.simulator.compiler.api.Api;
import org.beuwi.simulator.compiler.engine.ScriptEngine;
import org.beuwi.simulator.managers.BotManager;
import org.beuwi.simulator.managers.FileManager;
import org.beuwi.simulator.platform.application.actions.CopyAction;
import org.beuwi.simulator.platform.application.actions.OpenDesktopAction;
import org.beuwi.simulator.platform.application.views.dialogs.DeleteBotIDialog;
import org.beuwi.simulator.platform.application.views.dialogs.RenameBotIDialog;
import org.beuwi.simulator.platform.application.views.parts.ActiveAreaPart;
import org.beuwi.simulator.platform.ui.components.ICodeArea;
import org.beuwi.simulator.platform.ui.components.IContextMenu;
import org.beuwi.simulator.platform.ui.components.IMenuItem;
import org.beuwi.simulator.settings.Settings;
import org.beuwi.simulator.utils.ResourceUtils;

public class AddExplorerBotAction
{
	private static ListView<HBox> listView;

	public static void initialize()
	{
		listView = (ListView) ActiveAreaPart.getNameSpace().get("lsvExplorerArea");
	}

	public static void update(String name)
	{
		IContextMenu itemMenu = new IContextMenu
		(
			new IMenuItem("Compile", event -> ScriptEngine.setInitialize(name, true, false)),
			new IMenuItem("Power On / Off", event -> BotManager.setPower(name, !BotManager.getPower(name))),
			new IMenuItem("Show Log", event -> OpenBotLogTabAction.update(name)),
			new SeparatorMenuItem(),
			new IMenuItem("Show in Explorer", "Shift + Alt + R", event -> OpenDesktopAction.update(FileManager.getBotFolder(name))),
			new SeparatorMenuItem(),
			new IMenuItem("Copy Path", "Ctrl + Alt + C", event -> CopyAction.update(FileManager.getBotFolder(name).getAbsolutePath())),
			new IMenuItem("Copy Relative Path", "Ctrl + Shift + C", event -> CopyAction.update(FileManager.getBotFolder(name).getPath())),
			new SeparatorMenuItem(),
			new IMenuItem("Rename", event -> new RenameBotIDialog(name).display()),
			new IMenuItem("Delete", event -> new DeleteBotIDialog(name).display())
		);

		HBox 			itemCell    = new HBox();
		CheckBox		itemCheck   = new CheckBox();
		Label 			itemName    = new Label(name);
		Button 		    itemCompile = new Button();
		JFXToggleButton itemSwitch  = new JFXToggleButton();

		itemCell.setId(name);
		itemCell.setPrefHeight(40);
		itemCell.getChildren().addAll
		(
			getItemVBox(itemCheck,   Pos.CENTER, 	  Priority.NEVER,  25),
			getItemVBox(itemName,    Pos.CENTER_LEFT, Priority.ALWAYS, 50),
			getItemVBox(itemCompile, Pos.CENTER, 	  Priority.NEVER,  45),
			getItemVBox(itemSwitch,  Pos.CENTER_LEFT, Priority.NEVER,  40)
		);

		itemCell.getStyleClass().add("list-item");
		itemCell.setOnMousePressed(event ->
		{
			if (event.isPrimaryButtonDown() || event.isMiddleButtonDown())
			{
				AddEditorTabAction.update
				(
					ResourceUtils.getImage("tab_script.png"),
					"@script::" + name, name,
					new ICodeArea(FileManager.read(FileManager.getBotScript(name)))
				);
			}
			if (event.isSecondaryButtonDown())
			{
				itemMenu.show(itemCell, event);
			}
		});

		itemCompile.setOnMousePressed(event ->
		{
			if (event.isPrimaryButtonDown())
			{
				ScriptEngine.setInitialize(name, true, false);
			}
		});

		itemCheck.setSelected(Api.isCompiled(name));
		itemSwitch.setSelected(Api.isOn(name));
		itemSwitch.selectedProperty().addListener((observable, oldValue, newValue) ->
		{
			Settings.getScriptSetting(name).putBoolean("power", newValue);
		});

		itemCompile.setPrefSize(30, 30);

		BotManager.data.put("@check::" + name, itemCheck);
		BotManager.data.put("@switch::" + name, itemSwitch);

		listView.getItems().add(itemCell);
	}

	private static VBox getItemVBox(Node node, Pos pos, Priority priority, double prefWidth)
	{
		VBox box = new VBox(node);
		box.setAlignment(pos);
		box.setPrefWidth(prefWidth);

		VBox.setVgrow(box, priority);
		HBox.setHgrow(box, priority);

		return box;
	}
}
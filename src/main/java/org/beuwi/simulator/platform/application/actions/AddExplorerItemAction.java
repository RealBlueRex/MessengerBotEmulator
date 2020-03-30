package org.beuwi.simulator.platform.application.actions;

import com.jfoenix.controls.JFXToggleButton;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.beuwi.simulator.compiler.api.Api;
import org.beuwi.simulator.compiler.engine.ScriptManager;
import org.beuwi.simulator.managers.BotManager;
import org.beuwi.simulator.platform.application.views.parts.SideBarPart;
import org.beuwi.simulator.settings.Settings;

public class AddExplorerItemAction
{
	private static ListView<HBox> listView;

	public static void initialize()
	{
		listView = (ListView) SideBarPart.getNameSpace().get("lsvExplorerPart");
	}

	// Script Name (No Extension)
	public static void update(String name)
	{
		HBox 			itemCell    = new HBox();
		CheckBox        itemCheck   = new CheckBox();
		Label 			itemName    = new Label(name);
		Button 		    itemCompile = new Button();
		JFXToggleButton itemSwitch  = new JFXToggleButton();

		itemCell.setId(name);
		itemCell.setPrefHeight(35);
		itemCell.getChildren().addAll
		(
			getItemVBox(itemCheck,   Pos.CENTER, 	  Priority.NEVER,  25),
			getItemVBox(itemName,    Pos.CENTER_LEFT, Priority.ALWAYS, 50),
			getItemVBox(itemCompile, Pos.CENTER, 	  Priority.NEVER,  40),
			getItemVBox(itemSwitch,  Pos.CENTER_LEFT, Priority.NEVER,  40)
		);

		itemCell.getStyleClass().add("list-item");
		itemCell.setOnMousePressed(event ->
		{
			if (event.isPrimaryButtonDown() || event.isMiddleButtonDown())
			{
				AddEditorTabAction.update(name);
			}
		});

		itemCompile.setOnMousePressed(event ->
		{
			if (event.isPrimaryButtonDown())
			{
				ScriptManager.setInitialize(name, true, false);
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
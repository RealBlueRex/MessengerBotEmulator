package org.hui1601.emulator.platform.ui.components;

import javafx.beans.property.BooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;

public class IMenuItem extends MenuItem {
    public IMenuItem(String text) {
        this(text, (String) null);
    }

    public IMenuItem(String text, String command) {
        this(text, command, null);
    }

    public IMenuItem(String text, EventHandler handler) {
        this(text, null, handler);
    }

    public IMenuItem(String text, String command, EventHandler handler) {
        this(text, command, null, handler);
    }

    public IMenuItem(String text, String command, BooleanProperty property, EventHandler handler) {
        Label name = new Label(text);
        name.setMinWidth(150);

        if (command != null) {
            setAccelerator(KeyCombination.keyCombination(command));
        }

        if (property != null) {
            property.addListener((observable, oldValue, newValue) ->
            {
                setDisable(newValue);
            });
        }

        setGraphic(new HBox(name));
        setOnAction(handler);
    }

	/* private static class IMenuItems extends HashMap<String, MenuItem>
	{
		{
			put("Separator", 		new SeparatorMenuItem());

			put("New.Bot", 			new IMenuItem("New Bot", "Ctrl + N"));
			put("Import.Script", 	new IMenuItem("Import Script", "Ctrl + I"));
			put("Save",				new IMenuItem("Save", "Ctrl + S"));
			put("Save.All", 		new IMenuItem("Save All", "Ctrl + Shift + S"));
			put("Reload.All.Bots",  new IMenuItem("Reload All Bots", "Ctrl + Alt + Y"));
			put("Settings", 		new IMenuItem("Settings", "Ctrl + Alt + S"));


			put("Undo", 			new IMenuItem("Undo", "Ctrl + Z"));
			put("Redo",  			new IMenuItem("Redo", "Ctrl + Y"));
			put("Cut", 				new IMenuItem("Cut", "Ctrl + X"));
			put("Copy", 			new IMenuItem("Copy", "Ctrl + C"));
			put("Paste", 			new IMenuItem("Paste", "Ctrl + V"));

			put("Open.Debug.Room", 	new IMenuItem("Open Debug Room", "F8"));
			put("Show.Global.Log", 	new IMenuItem("Show Global Log", "F9"));
		}
	} */
}
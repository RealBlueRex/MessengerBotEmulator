package org.hui1601.emulator.platform.application.views.actions;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.hui1601.emulator.compiler.engine.ScriptEngine;
import org.hui1601.emulator.compiler.engine.ScriptManager;
import org.hui1601.emulator.managers.BotManager;
import org.hui1601.emulator.managers.FileManager;
import org.hui1601.emulator.platform.application.actions.CopyAction;
import org.hui1601.emulator.platform.application.views.dialogs.DeleteBotDialog;
import org.hui1601.emulator.platform.application.views.dialogs.RenameBotDialog;
import org.hui1601.emulator.platform.application.views.parts.ActiveAreaPart;
import org.hui1601.emulator.platform.ui.components.IContextMenu;
import org.hui1601.emulator.platform.ui.components.IListView;
import org.hui1601.emulator.platform.ui.components.IMenuItem;
import org.hui1601.emulator.platform.ui.components.IToggleButton;
import org.hui1601.emulator.settings.Settings;
import org.json.simple.JSONObject;
import org.mozilla.javascript.ScriptableObject;

import java.net.MalformedURLException;

public class AddExplorerBotAction {
    private static IListView listView;

    public static void initialize() {
        listView = ActiveAreaPart.BotsTab.getComponent();
    }

    public static void update(String name) {
        IContextMenu menu = new IContextMenu
                (
                        new IMenuItem("Compile", event -> {
                            ScriptManager.setInitialize(name, true, false);
                        }),
                        new IMenuItem("Power On / Off", event -> BotManager.setPower(name, !BotManager.getPower(name))),
                        new SeparatorMenuItem(),
                        new IMenuItem("Show Log", event -> OpenBotLogTabAction.update(name)),
                        new SeparatorMenuItem(),
                        new IMenuItem("Show in Explorer", "Shift + Alt + R", event -> {
                            OpenDesktopAction.update(FileManager.getBotFolder(name));
                        }),
                        new SeparatorMenuItem(),
                        new IMenuItem("Copy Path", "Ctrl + Alt + C", event -> CopyAction.update(FileManager.getBotFolder(name).getAbsolutePath())),
                        new IMenuItem("Copy Relative Path", "Ctrl + Shift + C", event -> CopyAction.update(FileManager.getBotFolder(name).getPath())),
                        new SeparatorMenuItem(),
                        new IMenuItem("Rename", event -> new RenameBotDialog(name).display()),
                        new IMenuItem("Delete", event -> new DeleteBotDialog(name).display())
                );

        HBox item = new HBox();            // Bot Item Cell
        CheckBox check = new CheckBox();        // Bot Compiled Check Box
        Label label = new Label(name);        // Bot Name Label
        Button button = new Button();            // Bot Compile Button
        IToggleButton power = new IToggleButton();    // Bot Power Switch

        menu.setNode(item);

        item.setId(name);
        item.setPrefHeight(35);
        item.getChildren().addAll
                (
                        getItemVBox(check, Pos.CENTER, Priority.NEVER, 25),
                        getItemVBox(label, Pos.CENTER_LEFT, Priority.ALWAYS, 50),
                        getItemVBox(button, Pos.CENTER, Priority.NEVER, 45),
                        getItemVBox(power, Pos.CENTER_LEFT, Priority.NEVER, 45)
                );

        item.getStyleClass().add("list-item");
        item.setOnMouseClicked(event ->
        {
            if (MouseButton.PRIMARY.equals(event.getButton()) || MouseButton.MIDDLE.equals(event.getButton())) {
                OpenScriptTabAction.update(name);
            }
        });

        button.setPrefSize(30, 30);
        button.setOnMouseClicked(event ->
        {
            if (MouseButton.PRIMARY.equals(event.getButton())) {
                ScriptManager.setInitialize(name, true, false);
            }
        });

        Settings.Script setting = Settings.getScriptSetting(name);

        BotManager.data.put("@check::" + name, check);
        BotManager.data.put("@switch::" + name, power);

        check.setSelected(ScriptEngine.container.containsKey(name));
        power.setSelected((Boolean) ((JSONObject)setting.get("option")).get("scriptPower"));
        power.selectedProperty().addListener((observable, oldValue, newValue) ->
        {
            setting.put("power", newValue);
        });

        listView.getItems().add(item);
    }

    private static VBox getItemVBox(Node node, Pos pos, Priority priority, double prefWidth) {
        VBox box = new VBox(node);
        box.setAlignment(pos);
        box.setPrefWidth(prefWidth);

        VBox.setVgrow(box, priority);
        HBox.setHgrow(box, priority);

        return box;
    }
}
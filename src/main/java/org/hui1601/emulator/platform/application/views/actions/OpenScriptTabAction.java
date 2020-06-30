package org.hui1601.emulator.platform.application.views.actions;

import org.hui1601.emulator.managers.FileManager;
import org.hui1601.emulator.platform.ui.components.ICodeArea;
import org.hui1601.emulator.utils.ResourceUtils;

import java.net.MalformedURLException;

public class OpenScriptTabAction {
    public static void update(String name) {
        AddEditorTabAction.update
                (
                        ResourceUtils.getImage("tab_script"),
                        "@script::" + name, name,
                        new ICodeArea(FileManager.read(FileManager.getBotScript(name)))
                );
    }
}
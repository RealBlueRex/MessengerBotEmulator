package org.hui1601.emulator.platform.application.views.actions;

import org.hui1601.emulator.managers.FileManager;
import org.hui1601.emulator.platform.application.views.parts.EditorAreaPart;
import org.hui1601.emulator.platform.ui.components.ICodeArea;
import org.hui1601.emulator.platform.ui.components.ITab;

import java.util.List;

public class SaveEditorTabAction {
    public static void update() {
        update(EditorAreaPart.getSelectedPane().getSelectedTab());
    }

    public static void update(String name) {
        update(EditorAreaPart.getSelectedPane().getTabItem("@script::" + name));
    }

    public static void update(ITab tab) {
        if (tab != null) {
            if (tab.getType().equals("script")) {
                FileManager.save(FileManager.getBotScript(tab.getName()), ((ICodeArea) tab.getContent()).getText());
            }
        }
    }

    public static void update(List<ITab> tabs) {
        for (ITab tab : tabs) {
            if (tab.getType().equals("script")) {
                update(tab);
            }
        }
    }
}
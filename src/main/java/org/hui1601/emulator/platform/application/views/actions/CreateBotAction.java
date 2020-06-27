package org.hui1601.emulator.platform.application.views.actions;

import org.hui1601.emulator.managers.FileManager;
import org.hui1601.emulator.platform.application.views.dialogs.ExistsBotDialog;

import java.io.File;

public class CreateBotAction {
    public static void update(String name, String content, boolean isImport, boolean isUnified, boolean isOffError) {
        File project = FileManager.getBotFolder(name);

        if (project.exists()) {
            new ExistsBotDialog(name).display();
        } else {
            project.mkdir();

            if (!isImport) {
                if (!isUnified) {
                    content = FileManager.read(FileManager.getDataFile("script_default.js"));
                } else {
                    content = FileManager.read(FileManager.getDataFile("script_unified.js"));
                }
            }

            FileManager.save
                    (
                            FileManager.getBotSetting(name),
                            "{\n" +
                                    "\t\"main\":\"index.js\",\n" +
                                    "\t\"option\":{\n" +
                                    "\t\t\"scriptPower\":true,\n" +
                                    "\t\t\"offOnRuntimeError\":" + isOffError + ",\n" +
                                    "\t\t\"optimization\":1,\n" +
                                    "\t\t\"useUnifiedParams\":" + isUnified + ",\n" +
                                    "\t\t\"useBabel\":false,\n" +
                                    "\t\t\"apiLevel\":1,\n" +
                                    "\t\t\"Bridge\":false\n" +
                                    "\t}\n" +
                                    "}\n"
                    );

            FileManager.save(FileManager.getBotScript(name), content);

            FileManager.save(FileManager.getBotLog(name), "[]");
            FileManager.save(FileManager.getBotType(name), "msgbot");
        }
    }
}

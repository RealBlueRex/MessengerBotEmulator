package org.hui1601.emulator.platform.openapi;

import org.hui1601.emulator.managers.FileManager;
import org.hui1601.emulator.platform.application.views.dialogs.ShowErrorDialog;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.util.List;

public class IJSONArray extends JSONArray {
    public IJSONArray() {
        super();
    }

    public IJSONArray(File file) {
        try {
            this.addAll((List) new JSONParser().parse(FileManager.read(file)));
        } catch (Exception e) {
            new ShowErrorDialog(e).display();
        }
    }
}
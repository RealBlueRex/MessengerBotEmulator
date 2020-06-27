package org.hui1601.emulator.managers;

import org.hui1601.emulator.platform.application.views.dialogs.ShowErrorDialog;
import org.hui1601.emulator.platform.openapi.IJSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AppDataManager {
    public static HashMap<String, Object> data = new HashMap<>();

    public static List<Object> load(File file) {
        try {
            if (!file.exists()) {
                return new ArrayList<>();
            }

            List<Object> list = new ArrayList<>();

            for (Object object : new IJSONArray(file)) {
                JSONObject data = (JSONObject) object;

                list.add(data);
            }

            return list;
        } catch (Exception e) {
            new ShowErrorDialog(e).display();
        }

        return new ArrayList<>();
    }
    public static Object getView(String name) {
        return data.get(name);
    }

    public static void append(String key, Object data) {
        AppDataManager.data.put(key, data);
        JSONObject object = new JSONObject(AppDataManager.data);
        FileManager.save(FileManager.getAppData(), object.toJSONString());
    }
    public static void clear() {
        data.clear();
        FileManager.save(FileManager.getAppData(), "[]");
    }

    public static void clear(String name) {
        data.remove(name);
        JSONObject object = new JSONObject(AppDataManager.data);
        FileManager.save(FileManager.getAppData(), object.toJSONString());
    }
}

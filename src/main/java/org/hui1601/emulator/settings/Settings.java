package org.hui1601.emulator.settings;

import org.hui1601.emulator.managers.FileManager;
import org.hui1601.emulator.platform.application.views.dialogs.ShowErrorDialog;
import org.hui1601.emulator.platform.openapi.IJSONObject;

import java.io.File;
import java.util.Map;

public class Settings {
    public static Public getPublicSetting(String type) {
        return new Public(type);
    }

    public static Script getScriptSetting(String name) {
        return new Script(name);
    }

    public static class Public extends IJSONObject {
        private String type;
        private File file;

        public Public(String type) {
            try {
                this.file = FileManager.getDataFile("global_setting.json");
                this.type = type;

                putAll(new IJSONObject(file).getJSONObject(type));
            } catch (Exception e) {
                new ShowErrorDialog(e).display();
            }
        }

        public void putString(String type, String data) {
            put(type, data);
            apply();
        }

        public void putInt(String type, int data) {
            put(type, data);
            apply();
        }

        public void putBoolean(String type, boolean data) {
            put(type, data);
            apply();
        }

        public void putMap(Map map) {
            putAll(map);
            apply();
        }

        private void apply() {
            try {
                IJSONObject data = new IJSONObject(file);

                data.put(type, this);

                FileManager.save(file, data.toBeautifyString());
            } catch (Exception e) {
                new ShowErrorDialog(e).display();
            }
        }
    }

    public static class Script extends IJSONObject {
        private File file;

        public Script(String name) {
            try {
                file = FileManager.getBotSetting(name);

                putAll(new IJSONObject(file));
            } catch (Exception e) {
                new ShowErrorDialog(e).display();
            }
        }

        public void putString(String type, String data) {
            put(type, data);
            apply();
        }

        public void putInt(String type, int data) {
            put(type, data);
            apply();
        }

        public void putBoolean(String type, boolean data) {
            put(type, data);
            apply();
        }

        public void putMap(Map map) {
            putAll(map);
            apply();
        }

        private void apply() {
            FileManager.save(file, toBeautifyString());
        }
    }
}
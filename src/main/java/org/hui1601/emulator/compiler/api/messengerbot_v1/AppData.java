package org.hui1601.emulator.compiler.api.messengerbot_v1;

import org.hui1601.emulator.managers.AppDataManager;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSFunction;

public class AppData extends ScriptableObject {
    public AppData(ScriptableObject object) {
        super(object, object.getPrototype());
    }

    @Override
    public String getClassName() {
        return "AppData";
    }

    @JSFunction
    public void putBoolean(String key, Boolean value) {
        AppDataManager.append(key, value);
    }

    @JSFunction
    public Boolean getBoolean(String key) {
        return (Boolean) AppDataManager.getView(key);
    }

    @JSFunction
    public void putInt(String key, int value) {
        AppDataManager.append(key, value);
    }

    @JSFunction
    public int getInt(String key) {
        return (int) AppDataManager.getView(key);
    }

    @JSFunction
    public void putString(String key, String value) {
        AppDataManager.append(key, value);
    }

    @JSFunction
    public String getString(String key, String value) {
        return (String) AppDataManager.getView(key);
    }

    @JSFunction
    public void remove(String key) {
        AppDataManager.clear(key);
    }

    @JSFunction
    public void clear() {
        AppDataManager.clear();
    }
}
package org.hui1601.emulator.compiler.api;

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
    public void putBoolean(String key, Boolean bool) {
    }

    @JSFunction
    public Boolean getBoolean(String ket, Boolean value) {
        return null;
    }

    @JSFunction
    public void putInt(String ket, int value) {
    }

    @JSFunction
    public int getInt(String key, int value) {
        return 0;
    }

    @JSFunction
    public void putString(String key, String value) {
    }

    @JSFunction
    public String getString(String key, String value) {
        return null;
    }

    @JSFunction
    public void remove(String key) {
    }

    @JSFunction
    public void clear() {
    }
}
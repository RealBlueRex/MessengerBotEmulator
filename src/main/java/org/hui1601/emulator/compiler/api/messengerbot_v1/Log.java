package org.hui1601.emulator.compiler.api.messengerbot_v1;

import org.hui1601.emulator.managers.LogManager;
import org.hui1601.emulator.platform.ui.components.ILogType;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSFunction;

public class Log extends ScriptableObject {
    final String bot_name;

    public Log(ScriptableObject object, String name) {
        super(object, object.getPrototype());

        this.bot_name = name;
    }

    @Override
    public String getClassName() {
        return "Log";
    }

    @Override
    public String toString() {
        return getClassName();
    }

    @JSFunction
    public void d(String data, Boolean showToast) {
        debug(data, showToast);
    }

    @JSFunction
    public void e(String data, Boolean showToast) {
        error(data, showToast);
    }

    @JSFunction
    public void i(String data, Boolean showToast) {
        info(data, showToast);
    }

    @JSFunction
    public void debug(String data, Boolean showToast) {
        LogManager.append(bot_name, data, ILogType.DEBUG);
    }

    @JSFunction
    public void error(String data, Boolean showToast) {
        LogManager.append(bot_name, data, ILogType.ERROR);
    }

    @JSFunction
    public void info(String data, Boolean showToast) {
        LogManager.append(bot_name, data, ILogType.EVENT);
    }

    @JSFunction
    public void clear() {
        LogManager.clear(bot_name);
    }
}
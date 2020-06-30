package org.hui1601.emulator.compiler.api.messengerbot_v1;

import org.hui1601.emulator.compiler.engine.ScriptEngine;
import org.hui1601.emulator.settings.Settings;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSFunction;

public class Bridge extends ScriptableObject {
    public Bridge(ScriptableObject object) {
        super(object, object.getPrototype());
    }

    @Override
    public String getClassName() {
        return "Bridge";
    }

    @JSFunction
    public ScriptableObject getScopeOf(String scriptName) {
        if (ScriptEngine.container.get(scriptName) != null) {
            try {
                return ScriptEngine.container.get(scriptName).getScope();
            } catch (Throwable e) {
                Context.reportError(e.toString());
            }

        }

        return null;
    }

    @JSFunction
    public Boolean isAllowed(String scriptName) {
        return (Boolean) Settings.getScriptSetting(scriptName).getJSONObject("options").get("Bridge");
    }
}
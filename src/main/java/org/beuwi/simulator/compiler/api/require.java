package org.beuwi.simulator.compiler.api;

import org.beuwi.simulator.compiler.engine.ScriptUtils;
import org.beuwi.simulator.managers.LogManager;
import org.beuwi.simulator.platform.ui.components.ILogType;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSFunction;

import java.io.*;

public class require extends ScriptableObject{
    private static final long serialVersionUID = 1L;
    private static final boolean silent = false;
    public require()
    {
    }
    @Override
    public String getClassName()
    {
        return "global";
    }
    public static void print(Context cx, Scriptable thisObj, Object[] args, Function funObj) {
        if (silent)
            return;
        for (int i = 0; i < args.length; i++)
            LogManager.append(Context.toString(args[i]), ILogType.EVENT);
    }

    public static void load(Context cx, Scriptable thisObj, Object[] args, Function funObj) {
        require shell = (require) getTopLevelScope(thisObj);
        for (int i = 0; i < args.length; i++) {
            LogManager.append("Loading file " + Context.toString(args[i]), ILogType.EVENT);
            try {
                shell.processSource(cx, Context.toString(args[i]));
            }catch (Exception e){
            }
        }
    }

    private void processSource(Context cx, String filename) throws IOException {
        cx.evaluateReader(this, new InputStreamReader(getInputStream(filename)), filename, 1, null);
    }

    private InputStream getInputStream(String file) throws IOException {
        return new FileInputStream(file);
    }
}
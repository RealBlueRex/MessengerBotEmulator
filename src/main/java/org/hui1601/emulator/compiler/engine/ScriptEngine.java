package org.hui1601.emulator.compiler.engine;

import javafx.application.Platform;
import org.hui1601.emulator.compiler.api.*;
import org.hui1601.emulator.managers.BotManager;
import org.hui1601.emulator.managers.FileManager;
import org.hui1601.emulator.managers.LogManager;
import org.hui1601.emulator.platform.application.views.actions.SaveEditorTabAction;
import org.hui1601.emulator.platform.ui.components.ILogType;
import org.hui1601.emulator.settings.Settings;
import org.hui1601.emulator.utils.ResourceUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.mozilla.javascript.*;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;

public class ScriptEngine {
    public static HashMap<String, ScriptContainer> container = new HashMap<>();
    public static HashMap<String, Boolean> compiling = new HashMap<>();
    public static ScriptableObject execScope = null;

    public static void run(String room, String message, String sender, boolean isGroupChat, ImageDB imageDB, String packageName) {
        for (String name : FileManager.getBotNames()) {
            if (!BotManager.getPower(name)) {
                continue;
            }
            if (!container.containsKey(name)) {
                continue;
            }

            Platform.runLater(() -> callResponder(name, room, message, sender, isGroupChat, imageDB, packageName));
        }
    }

    public static boolean initialize(String name, boolean isManual, boolean ignoreError) {
        LogManager.append("컴파일 시작 : " + name, ILogType.EVENT);

        compiling.put(name, true);

        File file = FileManager.getBotScript(name);

        if (Settings.getPublicSetting("program").getBoolean("autoSave")) {
            SaveEditorTabAction.update(name);
        }
        int optimization = ((Long) Settings.getScriptSetting(name)
                .getJSONObject("option")
                .get("optimization")).intValue();

        Context parseContext;

        try {
            parseContext = Context.enter();
            parseContext.setWrapFactory(new PrimitiveWrapFactory());
            parseContext.setLanguageVersion(Context.VERSION_ES6);
            parseContext.setOptimizationLevel(optimization);
        } catch (Exception e) {
            if (!isManual) {
                Context.reportError(e.toString());
            }
            return false;
        }

        System.gc();

        ScriptableObject scope;
        Script script;

        try {
            if (container.get(name) != null) {
                if (container.get(name).getOnStartCompile() != null) {
                    container.get(name).getOnStartCompile().call(parseContext, execScope, execScope, new Object[]{});
                }
            }

            int flags = ScriptableObject.EMPTY;
            scope = parseContext.initStandardObjects(new ImporterTopLevel(parseContext), true);
            script = parseContext.compileString(FileManager.read(file), file.getName(), 0, null);

            parseContext.evaluateReader(scope, Objects.requireNonNull(ResourceUtils.getJS("require")), "require", 1, null);

            ScriptableObject.defineProperty(scope, "BOT_NAME", name, flags);
            String type = FileManager.read(FileManager.getBotType(name));
            if(type == null)
            {
                if(FileManager.read(FileManager.getBotSetting(name)) == null)
                    FileManager.save(FileManager.getBotType(name), "greenbot");
                else
                    FileManager.save(FileManager.getBotType(name), "msgbot");
                type = FileManager.read(FileManager.getBotType(name));
            }
            switch (Objects.requireNonNull(type).trim()) {
                case "msgbot" -> {
                    if((Long)(((JSONObject) ((JSONObject) new JSONParser().parse(FileManager.read(FileManager.getBotSetting(name)))).get("option")).get("apiLevel")) == 1) {
                        parseContext.evaluateReader(scope, Objects.requireNonNull(ResourceUtils.getJS("timer")), "timer", 1, null);
                        ScriptableObject.defineProperty(scope, "Api",
                                ScriptUtils.convert(new org.hui1601.emulator.compiler.api.messengerbot_v1.Api(scope, name)), flags);
                        ScriptableObject.defineProperty(scope, "Device",
                                ScriptUtils.convert(new org.hui1601.emulator.compiler.api.messengerbot_v1.Device(scope)), flags);
                        ScriptableObject.defineProperty(scope, "Log",
                                ScriptUtils.convert(new org.hui1601.emulator.compiler.api.messengerbot_v1.Log(scope, name)), flags);
                        ScriptableObject.defineProperty(scope, "DataBase",
                                ScriptUtils.convert(new org.hui1601.emulator.compiler.api.messengerbot_v1.DataBase(scope, name)), flags);
                        ScriptableObject.defineProperty(scope, "Utils",
                                ScriptUtils.convert(new org.hui1601.emulator.compiler.api.messengerbot_v1.Utils(scope, name)), flags);
                        ScriptableObject.defineProperty(scope, "Bridge",
                                ScriptUtils.convert(new org.hui1601.emulator.compiler.api.messengerbot_v1.Bridge(scope)), flags);
                        ScriptableObject.defineProperty(scope, "AppData",
                                ScriptUtils.convert(new org.hui1601.emulator.compiler.api.messengerbot_v1.AppData(scope)), flags);
                        ScriptableObject.defineProperty(scope, "FileStream",
                                ScriptUtils.convert(new org.hui1601.emulator.compiler.api.messengerbot_v1.FileStream(scope)), flags);
                    }
                }
                case "greenbot" -> {
                    ScriptableObject.defineProperty(scope, "Api",
                            ScriptUtils.convert(new org.hui1601.emulator.compiler.api.greenbot.Api(scope, name)), flags);
                    ScriptableObject.defineProperty(scope, "Device",
                            ScriptUtils.convert(new org.hui1601.emulator.compiler.api.greenbot.Device(scope)), flags);
                    ScriptableObject.defineProperty(scope, "Utils",
                            ScriptUtils.convert(new org.hui1601.emulator.compiler.api.greenbot.Utils(scope, name)), flags);
                    ScriptableObject.defineProperty(scope, "FileStream",
                            ScriptUtils.convert(new org.hui1601.emulator.compiler.api.greenbot.FileStream(scope)), flags);
                    ScriptableObject.defineProperty(scope, "KakaoTalk",
                            ScriptUtils.convert(new org.hui1601.emulator.compiler.api.greenbot.KakaoTalk(scope)), flags);
                }
                default -> throw new RuntimeException("Error: no such bot type");
            }
            execScope = scope;

            script.exec(parseContext, scope);
            Function onStartCompile = scope.has("onStartCompile", scope) ? (Function) scope.get("onStartCompile", scope) : null;
            Function responder = scope.has("response", scope) ? (Function) scope.get("response", scope) : null;

            container.put(name, new ScriptContainer()
                    .setExecScope(scope)
                    .setResponder(responder)
                    .setOnStartCompile(onStartCompile)
                    .setOptimization(optimization)
                    .setScope(scope));

            Context.exit();

            compiling.put(name, false);
        } catch (Throwable e) {
            if (container.get(name) != null) {
                container.get(name).setOnStartCompile(null);
            }

            LogManager.append(e.toString() + " : " + name, ILogType.ERROR);

            compiling.put(name, false);

            if (!isManual) {
                if (!ignoreError) {
                    Context.reportError(e.toString());
                }
            }

            e.printStackTrace();

            return false;
        }

        BotManager.setCompiled(name, true);

        LogManager.append("컴파일 완료 : " + name, ILogType.EVENT);

        return true;
    }

    public static void callResponder(String name, String room, String message, String sender, Boolean isGroupChat, ImageDB imageDB, String packageName) {
        ScriptableObject scope = container.get(name).getExecScope();
        Function responder = container.get(name).getResponder();

        try {
            Context context = Context.enter();
            context.setWrapFactory(new PrimitiveWrapFactory());
            context.setLanguageVersion(Context.VERSION_ES6);
            context.setOptimizationLevel(container.get(name).getOptimization());

            if (responder != null) {
                if ((Boolean) Settings.getScriptSetting(name).getJSONObject("option").get("useUnifiedParams")) {
                    responder.call(context, scope, scope, new Object[]{new ResponseParameters(room, message, sender, isGroupChat, new Replier(), imageDB, packageName)});
                } else {
                    responder.call(context, scope, scope, new Object[]{room, message, sender, isGroupChat, new Replier(), imageDB, packageName});
                }
            }

            Context.exit();
        } catch (Throwable e) {
            e.printStackTrace();
            LogManager.append(e.toString() + " : " + name, ILogType.ERROR);

            if ((boolean)Settings.getScriptSetting(name).getJSONObject("option").get("offOnRuntimeError")) {
                BotManager.setPower(name, false);
            }
        }
    }
}
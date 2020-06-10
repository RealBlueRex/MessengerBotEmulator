package org.hui1601.emulator.compiler.api;

import org.hui1601.emulator.settings.Settings;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSFunction;

import java.io.IOException;

public class Utils extends ScriptableObject {
    final String bot_name;
    ScriptableObject scope;

    public Utils(ScriptableObject object, String name) {
        super(object, object.getPrototype());
        this.scope = object;
        this.bot_name = name;
    }

    @Override
    public String getClassName() {
        return "Utils";
    }

    @JSFunction
    public String getWebText(String url) {
        try {
            return parse(url).toString();
        } catch (Exception e) {
            Context.reportError(e.toString());
        }

        return null;
    }

    @JSFunction
    public Document parse(String url) {
        try {
            return Jsoup.connect(url)
                    .ignoreContentType(true)
                    .timeout(Settings.getPublicSetting("debug").getInt("htmlTimeOut"))
                    .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36")
                    .referrer("http://www.google.com")
                    .post();
        } catch (IOException e) {
            Context.reportError(e.toString());
            return null;
        }
    }

    @JSFunction
    public int getAndroidVersionCode() {
        return (new Utils(scope, "")).getAndroidVersionCode();
    }

    @JSFunction
    public String getAndroidVersionName() {
        return (new Utils(scope, "")).getAndroidVersionName();
    }

    @JSFunction
    public String getPhoneBrand() {
        return (new Utils(scope, "")).getPhoneBrand();
    }

    @JSFunction
    public String getPhoneModel() {
        return (new Utils(scope, "")).getPhoneModel();
    }
}
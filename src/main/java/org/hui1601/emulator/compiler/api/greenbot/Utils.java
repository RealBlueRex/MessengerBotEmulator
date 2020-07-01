package org.hui1601.emulator.compiler.api.greenbot;

import org.hui1601.emulator.platform.application.views.actions.ShowToastMessageAction;
import org.hui1601.emulator.settings.Settings;
import org.jsoup.Jsoup;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSFunction;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
    public static String removeTags(String str){
        return str.replaceAll("<.*?>","");
    }
    @JSFunction
    public static String getWebText(String url, Object removeTags) {
        boolean rT;//=removeTags
        if(removeTags instanceof Boolean) rT = (Boolean) removeTags;
        else rT = false;
        try {
            String return_value = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .timeout(Settings.getPublicSetting("debug").getInt("htmlTimeOut"))
                    .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36")
                    .referrer("https://www.google.co.kr")
                    .get()
                    .toString();
            if(rT) return removeTags(return_value);
            return return_value;
        } catch (Exception e) {
            Context.reportError(e.toString());
        }
        return null;
    }
    @JSFunction
    public static String getWebText2(String url, Object encoding) {
        String ec;//=encoding
        if(encoding instanceof String) ec = (String) encoding;
        else ec = "UTF-8";
        try {
            URL courl = new URL(url);
            HttpURLConnection co;
            BufferedReader buf;
            StringBuilder response = new StringBuilder();
            String tmp;
            co = (HttpURLConnection) courl.openConnection();
            buf = new BufferedReader(new InputStreamReader(co.getInputStream(), ec));
            while((tmp = buf.readLine()) != null){
                response.append(tmp);
            }
            return response.toString();
        } catch (Exception e) {
            Context.reportError(e.toString());
        }
        return null;
    }
    @JSFunction
    public static String compress(){
        return "\u200b".repeat(500);
    }
    @JSFunction
    public static String translate(String lang1, String lang2, String value){
        new ShowToastMessageAction("Utils.translate;는 더 이상 작동하지 않습니다.\nApi.papagoTranslate(); 함수를 사용해주세요.", 0, 0);
        return "";
    }
    @JSFunction
    public static String getJsoup(String url, Object removeTags) {
        return getWebText(url, removeTags);
    }
    @JSFunction
    public static void copyToClipboard(String value) {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(value), null);
    }
    @JSFunction
    public void showToast(String content, int length) {
        new Thread(() -> new ShowToastMessageAction(content, 0, 0).showToast()).start();
    }
}
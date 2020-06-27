package org.hui1601.emulator.compiler.api.greenbot;

import org.hui1601.emulator.Launcher;
import org.hui1601.emulator.managers.FileManager;
import org.hui1601.emulator.platform.application.views.actions.ShowToastMessageAction;
import org.hui1601.emulator.platform.openapi.IJSONObject;
import org.hui1601.emulator.platform.ui.components.ILogItem;
import org.hui1601.emulator.settings.Settings;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSFunction;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

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
        try {
            String result = Jsoup.connect("https://openapi.naver.com/v1/papago/n2mt")
                    .timeout(Settings.getPublicSetting("debug").getInt("htmlTimeOut"))
                    .userAgent("Messenger Bot Emulator " + Launcher.version)//user agent
                    .ignoreContentType(true)
                    .header("X-Naver-Client-Id", "tG5JVqBz98300Ph5DbGN")//papago client id
                    .header("X-Naver-Client-Secret", "Iq92AFKqc0")//papago client secret key
                    .requestBody("source=" + lang1 + "&target=" + lang2 + "&text="+ URLEncoder.encode(value, StandardCharsets.UTF_8))
                    .post()
                    .toString();
            result = removeTags(result);
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(result);
            return (String)((JSONObject)((JSONObject)json.get("message")).get("result")).get("translatedText");
        }catch (Exception e){
            Context.reportError(e.toString());
        }
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
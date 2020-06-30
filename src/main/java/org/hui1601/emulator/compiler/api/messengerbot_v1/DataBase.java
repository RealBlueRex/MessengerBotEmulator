package org.hui1601.emulator.compiler.api.messengerbot_v1;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSFunction;

import java.io.*;
import java.nio.charset.StandardCharsets;

@SuppressWarnings({"ResultOfMethodCallIgnored", "unused", "serial"})
public class DataBase extends ScriptableObject {
    final String bot_name;

    public DataBase(ScriptableObject object, String name) {
        super(object, object.getPrototype());

        this.bot_name = name;
    }

    @Override
    public String getClassName() {
        return "DataBase";
    }
    public String DataBaseSavePath = "";
    @JSFunction
    public String setDataBase(String fileName, String data) {
        try {
            File file = new File(DataBaseSavePath + fileName);

            file.getParentFile().mkdirs();
            file.createNewFile();

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, false), StandardCharsets.UTF_8));
            writer.write(data);
            writer.close();

            return data;
        } catch (Exception e) {
            Context.reportError(e.toString());
        }

        return null;
    }

    @JSFunction
    public String getDataBase(String fileName) {
        try {
            File file = new File(DataBaseSavePath + fileName);
            if (!file.exists()) {
                return null;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            String line;
            StringBuilder text = new StringBuilder(reader.readLine());
            while ((line = reader.readLine()) != null) {
                text.append("\n").append(line);
            }
            reader.close();
            return text.toString();
        } catch (Exception e) {
            Context.reportError(e.toString());
        }
        return null;
    }

    @JSFunction
    public String appendDataBase(String fileName, String data) {
        try {
            File file = new File(DataBaseSavePath + fileName);

            file.getParentFile().mkdirs();
            file.createNewFile();

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8));
            writer.write(data);
            writer.close();

            return data;
        } catch (Exception e) {
            Context.reportError(e.toString());
        }

        return null;
    }

    @JSFunction
    public Boolean removeDataBase(String fileName) {
        try {
            File file = new File(DataBaseSavePath + fileName);

            if (!file.exists()) {
                return false;
            }
            return file.delete();
        } catch (Exception e) {
            Context.reportError(e.toString());
        }
        return null;
    }
}

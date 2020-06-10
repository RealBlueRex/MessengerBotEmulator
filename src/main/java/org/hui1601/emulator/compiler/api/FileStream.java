package org.hui1601.emulator.compiler.api;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSFunction;

import java.io.*;

public class FileStream extends ScriptableObject {

    public FileStream(ScriptableObject object) {
        super(object, object.getPrototype());
    }

    @Override
    public String getClassName() {
        return "FileStream";
    }

    @JSFunction
    public String read(String path) {
        try {
            File file = new File("Sdcard_Root/" + path);

            if (!file.exists()) {
                return null;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String line = "";
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
    public String write(String path, String data) {
        try {
            File file = new File("Sdcard_Root/" + path);

            file.getParentFile().mkdirs();
            file.createNewFile();

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, false), "UTF8"));
            writer.write(data);
            writer.close();

            return data;
        } catch (Exception e) {
            Context.reportError(e.toString());
        }

        return null;
    }

    @JSFunction
    public String append(String path, String data) {
        try {
            File file = new File("Sdcard_Root/" + path);

            if (!file.getParentFile().mkdirs())

                file.createNewFile();

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF8"));
            writer.write(data);
            writer.close();

            return data;
        } catch (Exception e) {
            Context.reportError(e.toString());
        }

        return null;
    }

    @JSFunction
    public Boolean remove(String path) {
        try {
            File file = new File("Sdcard_Root/" + path);

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
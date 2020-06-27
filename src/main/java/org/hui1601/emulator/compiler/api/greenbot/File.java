package org.hui1601.emulator.compiler.api.greenbot;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSFunction;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SuppressWarnings({"ResultOfMethodCallIgnored", "unused"})
public class File extends ScriptableObject {

    public File(ScriptableObject object) {
        super(object, object.getPrototype());
    }

    @Override
    public String getClassName() {
        return "File";
    }

    @JSFunction
    public String read(String path) {
        try {
            java.io.File file = new java.io.File("Sdcard_Root" + java.io.File.separator + path);

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
    public String save(String path, String data) {
        try {
            java.io.File file = new java.io.File("Sdcard_Root" + java.io.File.separator + path);

            file.getParentFile().mkdirs();
            file.createNewFile();

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, false), StandardCharsets.UTF_8));
            writer.write(data);
            writer.close();

            return data;
        } catch (Exception e) {
            Context.reportError(e.toString());
        }

        return null;
    }

    @JSFunction
    public void createFolder(String path) {
        try {
            Path dir = Paths.get("Sdcard_Root" + java.io.File.separator + path);

            Files.createDirectories(dir);
        } catch (Exception e) {
            Context.reportError(e.toString());
        }
    }

    @JSFunction
    public Boolean remove(String path) {
        try {
            java.io.File file = new java.io.File("Sdcard_Root" + java.io.File.separator + path);

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
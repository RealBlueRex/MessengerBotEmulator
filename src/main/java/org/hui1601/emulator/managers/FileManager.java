package org.hui1601.emulator.managers;

import org.hui1601.emulator.platform.application.views.dialogs.ShowErrorDialog;
import org.hui1601.emulator.platform.application.views.dialogs.ShowWarningDialog;
import org.hui1601.emulator.platform.ui.components.ILogType;
import org.hui1601.emulator.settings.Settings;
import org.hui1601.emulator.utils.ResourceUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

@SuppressWarnings({"ResultOfMethodCallIgnored", "unused"})
public class FileManager {
    final public static File PROGRAM_FOLDER = new File(System.getProperty("user.dir"));
    final public static File DATA_FOLDER = new File(PROGRAM_FOLDER + "/data");
    final public static File BOTS_FOLDER = new File(PROGRAM_FOLDER + "/data/bots/");

    // ex.js -> ex
    public static String getFileBaseName(String name) {
        return (name.contains(".")) ? name.substring(0, name.lastIndexOf(".")) : name;
    }

    public static File getDataFile(String name) {
        return new File(DATA_FOLDER + File.separator + name);
    }

    public static String[] getBotNames() {
        File[] files = BOTS_FOLDER.listFiles(File::isDirectory);
        assert files != null;
        String[] names = new String[files.length];

        for (int i = 0; i < files.length; i++) {
            names[i] = files[i].getName();
        }

        return names;
    }

    public static File getBotFolder(String name) {
        return new File(BOTS_FOLDER + File.separator + getFileBaseName(name));
    }

    public static File getBotScript(String name) {
        return new File(getBotFolder(name).getPath() + File.separator + Settings.getScriptSetting(name).get("main"));
    }

    public static File getBotType(String name) {
        return new File(getBotFolder(name).getPath() + File.separator + "type.txt");
    }

    public static File getBotSetting(String name) {
        return new File(getBotFolder(name).getPath() + File.separator + "bot.json");
    }

    public static File getBotLog(String name) {
        return new File(getBotFolder(name).getPath() + File.separator + "log.json");
    }

    public static File getAppData() {
        return new File(DATA_FOLDER + File.separator + "AppData.json");
    }

    public static void save(File file, String content) {
        try {
            file.createNewFile();

            if (content != null) {
                if (!content.substring(content.length() - 1).equals(System.lineSeparator())) {
                    content += System.getProperty("line.separator");
                }
            }

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), StandardCharsets.UTF_8));
            assert content != null;
            writer.write(content);
            writer.close();
        } catch (Exception e) {
            new ShowErrorDialog(e).display();
        }
    }

    public static String append(File file, String content) {
        try {
            file.createNewFile();

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8));
            writer.write(content);
            writer.close();

            return content;
        } catch (Exception e) {
            new ShowErrorDialog(e).display();
        }

        return null;
    }

    public static String read(File file) {
        try {
            if (!file.exists()) {
                return null;
            }
            return String.join("\n", Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));
        } catch (Exception e) {
            new ShowErrorDialog(e).display();
        }

        return null;
    }

    public static boolean remove(File file) {
        try {
            if (!file.exists()) {
                return false;
            }

            if (file.isDirectory()) {
                // 폴더는 안의 파일들 제거하고 폴더를 제거해야 함.
                for (File data : Objects.requireNonNull(file.listFiles())) {
                    if (data.isFile()) {
                        if(!data.delete())
                            LogManager.append("File Deletion Failed: " + file.getAbsolutePath(), ILogType.ERROR);
                    } else {
                        remove(data);//재귀 호출
                    }
                }

                return file.delete();
            }

            return file.delete();
        } catch (Exception e) {
            new ShowErrorDialog(e).display();
        }

        return false;
    }
}
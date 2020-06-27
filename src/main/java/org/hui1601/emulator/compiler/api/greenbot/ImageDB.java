package org.hui1601.emulator.compiler.api.greenbot;

import org.apache.commons.io.IOUtils;
import org.hui1601.emulator.platform.application.views.dialogs.ShowErrorDialog;
import org.mozilla.javascript.annotations.JSFunction;

import java.io.File;
import java.io.FileInputStream;
import java.util.Base64;

import static org.hui1601.emulator.managers.FileManager.DATA_FOLDER;

@SuppressWarnings("unused")
public class ImageDB {
    public String getProfileImage() {
        return getProfileBase64();
    }
    @JSFunction
    public String getProfileBase64() {
        try {
            byte[] bytes = IOUtils.toByteArray(new FileInputStream(DATA_FOLDER + File.separator + "profile_sender.png"));
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            new ShowErrorDialog(e).display();
        }

        return null;
    }

    @JSFunction
    public int getProfileHash() {
        return getProfileBase64().hashCode();
    }

    @JSFunction
    public Object getProfileBitmap() {
        return null;
    }

    @JSFunction
    public String getImage() {
        return null;
    }
}

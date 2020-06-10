package org.hui1601.emulator.platform.application.views.actions;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import org.hui1601.emulator.platform.ui.About.IAboutDialog;
import org.hui1601.emulator.platform.ui.chat.IChatViewAllContents;
import org.hui1601.emulator.utils.ResourceUtils;

public class OpenAboutDialogAction  extends IAboutDialog {
    @FXML
    private Text contents;

    private String Contents;

    public OpenAboutDialogAction(String Contents) {
        this.Contents = Contents;
    }

    public void display() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ResourceUtils.getForm("AboutDialog"));
        loader.setController(this);

        Region root = null;

        try {
            root = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.initialize();
        this.setContent(root);
        this.setTitle();
        this.create();
    }

    private void initialize() {
        contents.setText(this.Contents);
    }
}

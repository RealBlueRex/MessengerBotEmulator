package org.hui1601.emulator.platform.application.views.actions;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import org.hui1601.emulator.platform.ui.chat.IChatViewAllContents;
import org.hui1601.emulator.utils.ResourceUtils;

public class ShowChatViewAllContents extends IChatViewAllContents {
    @FXML
    private TextArea contents;

    private String Contents;

    public ShowChatViewAllContents(String Contents) {
        this.Contents = Contents;
    }

    public void display() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ResourceUtils.getForm("ShowChatContents"));
        loader.setController(this);

        Region root = null;

        try {
            root = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.initialize();
        this.setContent(root);
        this.setTitle("전체보기");
        this.create();
    }

    private void initialize() {
        contents.setText(this.Contents);
    }
}
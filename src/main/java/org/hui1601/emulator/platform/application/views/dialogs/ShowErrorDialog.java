package org.hui1601.emulator.platform.application.views.dialogs;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import org.hui1601.emulator.platform.ui.dialog.IDialogBoxType.DOCUMENT;
import org.hui1601.emulator.platform.ui.dialog.IDialogBoxView;
import org.hui1601.emulator.utils.ResourceUtils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ShowErrorDialog extends IDialogBoxView {
    @FXML
    private Label header;
    @FXML
    private TextArea content;

    private Exception error;

    public ShowErrorDialog(Exception error) {
        super(DOCUMENT.ERROR);
        this.error = error;
    }

    public void display() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ResourceUtils.getForm("ShowErrorDialog"));
        loader.setController(this);

        Region root = null;

        try {
            root = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.initialize();
        this.setUseButton(true, false);
        this.setContent(root);
        this.setTitle("Error");
        this.create();
    }

    private void initialize() {
        StringWriter message = new StringWriter();
        error.printStackTrace(new PrintWriter(message));

        header.setText(error.toString());
        content.setText(message.toString());
    }
}
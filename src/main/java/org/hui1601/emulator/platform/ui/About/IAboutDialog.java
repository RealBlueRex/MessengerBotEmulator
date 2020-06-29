package org.hui1601.emulator.platform.ui.About;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.hui1601.emulator.platform.ui.window.IWindowType;
import org.hui1601.emulator.platform.ui.window.IWindowView;
import org.hui1601.emulator.utils.ResourceUtils;

import java.io.Console;
import java.net.URL;
import java.util.ResourceBundle;

public class IAboutDialog extends StackPane implements Initializable {
    final IWindowView window;
    final Stage stage;
    Region content;
    String title;
    @FXML
    private BorderPane brpRootPane;

    public IAboutDialog() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ResourceUtils.getForm("ShowAboutDialog"));
        loader.setController(this);

        try {
            loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.stage = new Stage();
        this.window = new IWindowView(stage);
    }

    public void setContent(Region content) {
        this.content = content;
    }

    public void setTitle() {
        this.title = "About";
    }

    public void create() {
        brpRootPane.getChildren().remove(brpRootPane.getLeft());
        setMinSize(content.getMinWidth(), content.getMinHeight() + 68);
        setPrefSize(content.getPrefWidth(), content.getPrefHeight() + 68);

        brpRootPane.setCenter(content);

        getChildren().add(brpRootPane);
        getStyleClass().add("Contents");
        getStylesheets().add(ResourceUtils.getStyle("ContentsView"));

        stage.addEventFilter(KeyEvent.KEY_PRESSED, event ->
        {
            switch (event.getCode()) {
                case ESCAPE:
                    stage.close();
                    break;
            }
        });

        window.setContent(this);
        window.setType(IWindowType.DIALOG);
        window.setTitle(title);
        window.create();
        window.show();
    }

    public void close() {
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
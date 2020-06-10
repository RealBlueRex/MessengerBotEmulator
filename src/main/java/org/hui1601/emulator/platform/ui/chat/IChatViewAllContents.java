package org.hui1601.emulator.platform.ui.chat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.hui1601.emulator.platform.ui.window.IWindowType;
import org.hui1601.emulator.platform.ui.window.IWindowView;
import org.hui1601.emulator.utils.ResourceUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class IChatViewAllContents extends StackPane implements Initializable {
    final IWindowView window;
    final Stage stage;
    Region content;
    String title;
    @FXML
    private BorderPane brpRootPane;

    public IChatViewAllContents() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ResourceUtils.getForm("ChatViewAllContents"));
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

    public void setTitle(String title) {
        this.title = title;
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
            if (event.getCode() == KeyCode.ESCAPE) {
                stage.close();
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
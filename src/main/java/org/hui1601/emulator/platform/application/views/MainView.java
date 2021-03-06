package org.hui1601.emulator.platform.application.views;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.hui1601.emulator.platform.application.views.parts.ActiveAreaPart;
import org.hui1601.emulator.platform.application.views.parts.EditorAreaPart;
import org.hui1601.emulator.platform.application.views.parts.StatusBarPart;

public class MainView extends BorderPane {
    private static Stage stage;

    // Application Stage
    public MainView(Stage stage) {
        MainView.stage = stage;

        setLeft(ActiveAreaPart.getRoot());
        setCenter(EditorAreaPart.getRoot());
        setBottom(StatusBarPart.getRoot());

        setMinWidth(800);
        setMinHeight(600);
        setPrefWidth(1400);
        setPrefHeight(900);
        // setMaxWidth(1920);
        // setMaxHeight(1080);
    }

    public static Stage getStage() {
        return stage;
    }
}
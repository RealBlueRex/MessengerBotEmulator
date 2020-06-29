package org.hui1601.emulator.platform.application.views.actions;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import org.hui1601.emulator.platform.application.views.parts.ActiveAreaPart;

public class ResizeSideBarAction {
    private static StackPane pane;

    public static void initialize() {
        pane = ActiveAreaPart.getSideBar();
    }

    public static void update(MouseEvent event) {
        int size = (int) event.getSceneX() - 30;

        if (HideSideBarAction.isHided()) return;
        if (size > 180 && size <= 500) {
            pane.setPrefWidth(size);
        }
    }
}
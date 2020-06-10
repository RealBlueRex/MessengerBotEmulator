package org.hui1601.emulator.platform.ui.window;

import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import org.hui1601.emulator.utils.ResourceUtils;

public class IWindowScene extends Scene {
    public IWindowScene(Region root) {
        super(root);

        setFill(Color.TRANSPARENT);

        getStylesheets().add(ResourceUtils.getTheme("base"));
        getStylesheets().add(ResourceUtils.getTheme("dark"));
    }
}
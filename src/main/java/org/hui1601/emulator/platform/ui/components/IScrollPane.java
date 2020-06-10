package org.hui1601.emulator.platform.ui.components;

import javafx.scene.Node;
import org.fxmisc.flowless.VirtualizedScrollPane;

public class IScrollPane extends VirtualizedScrollPane {
    {
        getStyleClass().add("ifx-scroll-pane");
    }

    public IScrollPane() {
        this(null);
    }

    public IScrollPane(Node node) {
        super(node);
    }
}
package org.hui1601.emulator.platform.ui.components;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Button;

public class IButton extends Button {
    public static final int DEFAULT = 0;
    public static final int ACTION = 1;
    public final IntegerProperty property = new SimpleIntegerProperty(1);
    private final double DEFAULT_WIDTH = 70;
    private final double DEFAULT_HEIGHT = 30;

    {
        getStyleClass().add("ifx-button");
    }

    public IButton() {
        this(DEFAULT);
    }

    public IButton(int type) {
        setPrefWidth(DEFAULT_WIDTH);
        setPrefHeight(DEFAULT_HEIGHT);

        property.set(type);

        property.addListener((observable, oldValue, newValue) ->
        {
            switch (property.get()) {
                case DEFAULT:

                    getStyleClass().add("ifx-button-default");

                    break;

                case ACTION:

                    if (getStyleClass().contains("ifx-button-default")) {
                        getStyleClass().remove("ifx-button-default");
                    }

                    getStyleClass().add("ifx-button-action");

                    break;
            }
        });
    }

    public void setMenu(IContextMenu menu) {
        menu.setNode(this);
    }

    public int getType() {
        return property.get();
    }

    public void setType(int type) {
        property.set(type);
    }
}
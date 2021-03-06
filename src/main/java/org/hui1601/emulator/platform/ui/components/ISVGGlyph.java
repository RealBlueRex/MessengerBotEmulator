package org.hui1601.emulator.platform.ui.components;

import com.jfoenix.svg.SVGGlyph;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

import java.util.HashMap;

public class ISVGGlyph extends SVGGlyph {
    private Color fill;

    {
        getStyleClass().add("glyph");
    }

    public ISVGGlyph() {
        this(null);
    }

    public ISVGGlyph(String path) {
        this(path, Color.valueOf("#CCCCCC"));
    }

    public ISVGGlyph(String path, Color fill) {
        this(path, fill, 12, 12);
    }

    public ISVGGlyph(String path, double width, double height) {
        this(path, Color.valueOf("#CCCCCC"), width, height);
    }

    public ISVGGlyph(String path, Color fill, double width, double height) {
        super(path);

        this.fill = fill;

        setFill(fill);
        setSize(width, height);
    }

    public static ISVGGlyph getGlyph(String key) {
        return new ISVGGlyphs().get(key);
    }

    // Parent
    public void applyStyles(Node node) {
        if (node instanceof Button) {
            node.hoverProperty().addListener((observable, oldValue, newValue) ->
            {
                if (newValue) {
                    setFill(Color.WHITE);
                } else {
                    setFill(fill);
                }
            });

            node.focusedProperty().addListener((observable, oldValue, newValue) ->
            {
                if (newValue) {
                    setFill(Color.WHITE);
                } else {
                    setFill(fill);
                }
            });
        }
    }

    public ISVGGlyph setFill(String color) {
        setFill(Color.valueOf(color));

        return this;
    }

    public ISVGGlyph setSize(int width, int height) {
        setSize(Double.valueOf(width), Double.valueOf(height));

        return this;
    }

    public ISVGGlyph setTranslateX(int translateX) {
        setTranslateX(Double.valueOf(translateX));

        return this;
    }

    public ISVGGlyph setTranslateY(int translateY) {
        setTranslateY(Double.valueOf(translateY));

        return this;
    }

    public ISVGGlyph setTranslateZ(int translateZ) {
        setTranslateZ(Double.valueOf(translateZ));

        return this;
    }

    private static class ISVGGlyphs extends HashMap<String, ISVGGlyph> {
        {
            put("Window.Minimize", new ISVGGlyph("M3,8h10v1H3V8z", Color.valueOf("#8C8C8C"), 10, 1));
            put("Window.Maximize", new ISVGGlyph("M3,3 L13,3 L13,13 L3,13 L3,3 Z M4,4 L4,12 L12,12 L12,4 L4,4 Z", Color.valueOf("#8C8C8C"), 10, 10));
            put("Window.Close", new ISVGGlyph("M12.3,13L8,8.7L3.7,13L3,12.3L7.3,8L3,3.7L3.7,3L8,7.3L12.3,3L13,3.7L8.7,8l4.3,4.3L12.3,13z", Color.valueOf("#8C8C8C"), 10, 10));

            put("Tab.Close", new ISVGGlyph("M12.3,13L8,8.7L3.7,13L3,12.3L7.3,8L3,3.7L3.7,3L8,7.3L12.3,3L13,3.7L8.7,8l4.3,4.3L12.3,13z", 8, 8));

            put("Editor.Split", new ISVGGlyph("M28.4,0H1.6C0.7,0,0,0.7,0,1.6v26.9C0,29.3,0.7,30,1.6,30h26.9c0.9,0,1.6-0.7,1.6-1.6V1.6C30,0.7,29.3,0,28.4,0z M2.8,28.3c-0.6,0-1-0.4-1-1V2.8c0-0.6,0.4-1,1-1H14v26.5H2.8z M28.3,27.3c0,0.6-0.4,1-1,1H16V1.8h11.3c0.6,0,1,0.4,1,1V27.3z", 13, 13));
            put("Editor.More", new ISVGGlyph("M2,0C0.9,0,0,0.9,0,2s0.9,2,2,2s2-0.9,2-2S3.1,0,2,0z M14,0c-1.1,0-2,0.9-2,2s0.9,2,2,2s2-0.9,2-2S15.1,0,14,0z M8,0 C6.9,0,6,0.9,6,2s0.9,2,2,2s2-0.9,2-2S9.1,0,8,0z", 13, 2));
        }
    }
}

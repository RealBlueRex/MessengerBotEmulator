package org.hui1601.emulator.platform.ui.window;

import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.hui1601.emulator.platform.application.views.MainView;
import org.hui1601.emulator.platform.application.views.actions.*;
import org.hui1601.emulator.platform.application.views.dialogs.CreateBotDialog;
import org.hui1601.emulator.platform.application.views.dialogs.ImportScriptDialog;
import org.hui1601.emulator.platform.ui.components.IContextMenu;
import org.hui1601.emulator.platform.ui.components.IMenuButton;
import org.hui1601.emulator.platform.ui.components.IMenuItem;
import org.hui1601.emulator.platform.ui.components.ISVGGlyph;
import org.hui1601.emulator.utils.ResourceUtils;

@SuppressWarnings("unused")
public class IWindowView extends StackPane {
    private final double WINDOW_BORDER_WIDTH = 5;

    private final Background WINDOW_BACK_GROUND = new Background(new BackgroundFill(Color.valueOf("#323233"), CornerRadii.EMPTY, Insets.EMPTY));
    private final Background DIALOG_BACK_GROUND = new Background(new BackgroundFill(Color.valueOf("#252526"), CornerRadii.EMPTY, Insets.EMPTY));

    private final Border WINDOW_DEFAULT_BORDER = new Border(new BorderStroke(Color.valueOf("#434346"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1)));
    private final Border WINDOW_FOCUSED_BORDER = new Border(new BorderStroke(Color.valueOf("#007ACC"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1)));

    private final Border DIALOG_FOCUSED_BORDER = new Border(new BorderStroke(Color.valueOf("#898989"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1)));
    private final Border DIALOG_DEFAULT_BORDER = new Border(new BorderStroke(Color.valueOf("#252526"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1)));

    private final DropShadow WINDOW_DEFAULT_SHADOW = new DropShadow(BlurType.GAUSSIAN, Color.rgb(0, 0, 0, 0.5), 10, 0.4, 0, 0);
    private final DropShadow WINDOW_FOCUSED_SHADOW = new DropShadow(BlurType.GAUSSIAN, Color.rgb(0, 122, 204, 0.5), 10, 0.4, 0, 0);

    private final DropShadow DIALOG_DEFAULT_SHADOW = new DropShadow(BlurType.GAUSSIAN, Color.rgb(0, 0, 0, 0.5), 10, 0.4, 0, 0);
    private final DropShadow DIALOG_FOCUSED_SHADOW = new DropShadow(BlurType.GAUSSIAN, Color.rgb(0, 0, 0, 0.5), 10, 0.4, 0, 0);
    private final IWindowEvent ievent;
    private final Stage stage;
    @FXML
    private BorderPane brpRootPane;
    @FXML
    private AnchorPane anpTitleBar;
    @FXML
    private Label lblWinTitle;
    // Menu Bar
    @FXML
    private HBox hbxMenuBar;
    @FXML
    private IMenuButton btnFileMenu;
    @FXML
    private IMenuButton btnEditMenu;
    @FXML
    private IMenuButton btnViewMenu;
    @FXML
    private IMenuButton btnDebugMenu;
    @FXML
    private IMenuButton btnHelpMenu;
    // Button Bar
    @FXML
    private HBox hbxButtonBar;
    @FXML
    private Button btnMinimize;
    @FXML
    private Button btnMaximize;
    @FXML
    private Button btnClose;
    private IWindowType type;
    private Region content;
    private String title;

    public IWindowView(Stage stage) {
        this.stage = stage;
        this.ievent = new IWindowEvent(this);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ResourceUtils.getForm("WindowView"));
        loader.setController(this);

        try {
            loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Stage getStage() {
        return stage;
    }

    public IWindowType getType() {
        return type;
    }

    public void setType(IWindowType type) {
        this.type = type;
    }

    public Region getContent() {
        return content;
    }

    public void setContent(Region content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private void initMenuBar() {
        btnFileMenu.setMenus
                (
                        new IMenuItem("New Bot", "Ctrl + N", event -> new CreateBotDialog().display()),
                        new IMenuItem("Import Script", "Ctrl + I", event -> new ImportScriptDialog().display()),
                        new SeparatorMenuItem(),
                        new IMenuItem("Save", "Ctrl + S", event -> SaveEditorTabAction.update()),
                        new IMenuItem("Save All", event -> SaveAllEditorTabsAction.update()),
                        new SeparatorMenuItem(),
                        new IMenuItem("Refresh All Bots", "Ctrl + Alt + Y", event -> RefreshBotsAction.update()),
                        new SeparatorMenuItem(),
                        new IMenuItem("Settings", "Ctrl + Alt + S", event -> OpenSettingsTabAction.update())
                );

        btnEditMenu.setMenus
                (
                        new IMenuItem("Undo", "Ctrl + Z"),
                        new IMenuItem("Redo", "Ctrl + Y"),
                        new SeparatorMenuItem(),
                        new IMenuItem("Cut", "Ctrl + X"),
                        new IMenuItem("Copy", "Ctrl + C"),
                        new IMenuItem("Paste", "Ctrl + V")
                );

        btnViewMenu.setMenu(new IContextMenu());

        btnDebugMenu.setMenus
                (
                        new IMenuItem("Show Global Log", "F8", event -> OpenGlobalLogTabAction.update()),
                        new IMenuItem("Open Debug Room", "F9", event -> OpenDebugRoomTabAction.update())
                );

        btnHelpMenu.setMenus
                (
                        new IMenuItem("About", null, event -> new OpenAboutDialogAction(
                                "Messenger Bot Emulator\n"+
                                "Developer: hui1601\n"+
                                "version: 0.9.2\n\n"+
                                "OpenSource Licence\n"+
                                "org.beuwi.simulator(MessengerBotSimulator) - GPL3.0\n"+
                                "Rhino-Require - Public Domain\n"+
                                "ScriptUtils.java(NenkaLab) - Unknown\n").display())
                );
    }

    private void computeStageSize() {
        double border = WINDOW_BORDER_WIDTH * 2;

        double minW = content.getMinWidth();
        double minH = content.getMinHeight();
        double preW = content.getPrefWidth();
        double preH = content.getPrefHeight();
        double maxW = content.getMaxWidth();
        double maxH = content.getMaxHeight();

        stage.setMinWidth(minW > 0 ? minW + border : 400);
        stage.setMinHeight(minH > 0 ? minH + border + 28 : 200);
        stage.setWidth(preW > 0 ? preW + border : 600);
        stage.setHeight(preH > 0 ? preH + border + 28 : 400);
        stage.setMaxWidth(maxW > 0 ? maxW : Double.MAX_VALUE);
        stage.setMaxHeight(maxH > 0 ? maxH : Double.MAX_VALUE);
    }

    public void create() {
        switch (type) {
            case WINDOW -> {
                brpRootPane.setBackground(WINDOW_BACK_GROUND);
                stage.focusedProperty().addListener((observable, oldValue, newValue) ->
                {
                    brpRootPane.setBorder(newValue ? WINDOW_FOCUSED_BORDER : WINDOW_DEFAULT_BORDER);
                    brpRootPane.setEffect(newValue ? WINDOW_FOCUSED_SHADOW : WINDOW_DEFAULT_SHADOW);
                });
                lblWinTitle.setVisible(false);
                initMenuBar();
            }
            case DIALOG -> {
                brpRootPane.setBackground(DIALOG_BACK_GROUND);
                hbxMenuBar.setVisible(false);
                btnMinimize.setVisible(false);
                btnMaximize.setVisible(false);
                lblWinTitle.setText(title);
                stage.focusedProperty().addListener((observable, oldValue, newValue) ->
                {
                    brpRootPane.setBorder(newValue ? DIALOG_FOCUSED_BORDER : DIALOG_DEFAULT_BORDER);
                    brpRootPane.setEffect(newValue ? DIALOG_FOCUSED_SHADOW : DIALOG_DEFAULT_SHADOW);
                });
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(MainView.getStage());
            }
        }

        IContextMenu menu = new IContextMenu
                (
                        new IMenuItem("Minimize", event -> ievent.setMinimized()),
                        new IMenuItem("Maximize", event -> ievent.setMaximized()),
                        new SeparatorMenuItem(),
                        new IMenuItem("Close", "Ctrl + F4", event -> ievent.setClosed())
                );

        menu.setNode(anpTitleBar);

        // When the stage appears
        stage.showingProperty().addListener((observable, oldValue, newValue) ->
        {
            if (newValue) {
                ievent.setMovable(anpTitleBar);
                ievent.setResizable(brpRootPane);
            }
        });

        stage.focusedProperty().addListener((observable, oldValue, newValue) ->
                pseudoClassStateChanged(PseudoClass.getPseudoClass("focused"), newValue));

        ievent.maximizedProperty().addListener((observable, oldValue, newValue) ->
        {
            setPadding(new Insets(newValue ? 0 : 5));
            pseudoClassStateChanged(PseudoClass.getPseudoClass("maximized"), newValue);
        });

        btnMinimize.setGraphic(ISVGGlyph.getGlyph("Window.Minimize"));
        btnMinimize.setOnAction(event ->
                ievent.setMinimized());

        btnMaximize.setGraphic(ISVGGlyph.getGlyph("Window.Maximize"));
        btnMaximize.setOnAction(event ->
                ievent.setMaximized());

        btnClose.setGraphic(ISVGGlyph.getGlyph("Window.Close"));
        btnClose.setOnAction(event ->
                ievent.setClosed());

        brpRootPane.setCenter(content);

        for (Node node : brpRootPane.getChildren()) {
            node.setCursor(Cursor.DEFAULT);
        }

        setPadding(new Insets(WINDOW_BORDER_WIDTH));
        getChildren().add(brpRootPane);
        getStyleClass().add("window");
        getStylesheets().add(ResourceUtils.getStyle("WindowView"));
        computeStageSize();

        stage.getIcons().add(ResourceUtils.getImage("program"));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("Messenger Bot Emulator"); // Default Title
        stage.setScene(new IWindowScene(this));
        stage.toFront();
    }

    public void show() {
        stage.show();
    }
}
package org.hui1601.emulator;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.hui1601.emulator.compiler.engine.ScriptManager;
import org.hui1601.emulator.managers.FileManager;
import org.hui1601.emulator.platform.application.views.MainView;
import org.hui1601.emulator.platform.application.views.actions.*;
import org.hui1601.emulator.platform.application.views.dialogs.CreateBotDialog;
import org.hui1601.emulator.platform.application.views.dialogs.ImportScriptDialog;
import org.hui1601.emulator.platform.application.views.dialogs.ShowErrorDialog;
import org.hui1601.emulator.platform.application.views.parts.ActiveAreaPart;
import org.hui1601.emulator.platform.application.views.parts.EditorAreaPart;
import org.hui1601.emulator.platform.application.views.parts.StatusBarPart;
import org.hui1601.emulator.platform.application.views.tabs.DebugRoomTab;
import org.hui1601.emulator.platform.application.views.tabs.GlobalLogTab;
import org.hui1601.emulator.platform.application.views.tabs.SettingsTab;
import org.hui1601.emulator.platform.ui.window.IWindowType;
import org.hui1601.emulator.platform.ui.window.IWindowView;
import org.hui1601.emulator.utils.ResourceUtils;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class Launcher extends Application {
    public static String version = "v.9.2";
    private final WatchService WATCH_SERVICE = FileSystems.getDefault().newWatchService();
    private WatchKey WATCH_KEY = null;

    public Launcher() throws IOException {

    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            // Text Anti Aliasing
            System.setProperty("prism.text", "t2k");
            System.setProperty("prism.lcdtext", "false");
            System.setProperty("prism.subpixeltext", "false");

            if (!FileManager.BOTS_FOLDER.exists()) {
                FileManager.BOTS_FOLDER.mkdir();
            }

            // Refresh
            Paths.get(FileManager.BOTS_FOLDER.getPath()).register
                    (
                            WATCH_SERVICE,
                            StandardWatchEventKinds.ENTRY_CREATE,
                            StandardWatchEventKinds.ENTRY_DELETE,
                            StandardWatchEventKinds.ENTRY_MODIFY,
                            StandardWatchEventKinds.OVERFLOW
                    );

            new Thread(() ->
            {
                while (true) {
                    try {
                        WATCH_KEY = WATCH_SERVICE.take();
                    } catch (InterruptedException e) {
                        break;
                    }

                    List<WatchEvent<?>> events = WATCH_KEY.pollEvents();

                    for (WatchEvent<?> ignored : events) {
                        Platform.runLater(RefreshBotsAction::update);
                    }

                    if (!WATCH_KEY.reset()) {
                        try {
                            WATCH_SERVICE.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

            // Load Fonts
            Font.loadFont(ResourceUtils.getFont("Consola"), 0); // Family : "Consolas"
            Font.loadFont(ResourceUtils.getFont("ConsolaBold"), 0); // Family :
            Font.loadFont(ResourceUtils.getFont("D2Coding"), 0); // Family : "D2Coding"
            Font.loadFont(ResourceUtils.getFont("D2CodingBold"), 0); // Family : "D2Coding"
            Font.loadFont(ResourceUtils.getFont("Roboto"), 0); // Family : "Roboto"
            Font.loadFont(ResourceUtils.getFont("RobotoBold"), 0); // Family : "Roboto Bold"
            Font.loadFont(ResourceUtils.getFont("RobotoMedium"), 0); // Family : "Roboto Medium"

            // 기본 스타일 지정
            Application.setUserAgentStylesheet(ResourceUtils.getTheme("base"));

            // Initialize Views
            ActiveAreaPart.initialize();
            EditorAreaPart.initialize();
            StatusBarPart.initialize();

            DebugRoomTab.initialize();
            GlobalLogTab.initialize();
            SettingsTab.initialize();

            // Initialize Actions
            AddBotLogItemAction.initialize();
            AddChatMessageAction.initialize();
            AddEditorTabAction.initialize();
            AddExplorerBotAction.initialize();
            ChangeActivityTabAction.initialize();
            DeleteEditorPaneAction.initialize();
            HideSideBarAction.initialize();
            MoveEditorTabAction.initialize();
            OpenDebugRoomTabAction.initialize();
            OpenGlobalLogTabAction.initialize();
            OpenSettingsTabAction.initialize();
            RefreshBotsAction.initialize();
            ResizeSideBarAction.initialize();
            SaveAllEditorTabsAction.initialize();
            SelectActivityTabAction.initialize();
            SplitEditorPaneAction.initialize();
            stage.addEventHandler(KeyEvent.KEY_PRESSED, event ->
            {
                if (event.isControlDown()) {
                    KeyCode KEY = event.getCode();
                    if (KEY == KeyCode.N) new CreateBotDialog().display();
                    else if (KEY == KeyCode.I) new ImportScriptDialog().display();
                    else if (event.isAltDown() && KEY == KeyCode.S) OpenSettingsTabAction.update();
                }

                switch (event.getCode()) {
                    case F8 -> OpenGlobalLogTabAction.update();
                    case F9 -> OpenDebugRoomTabAction.update();
                    case F10 -> ScriptManager.allInitialize(true);
                }

                event.consume();
            });

            RefreshBotsAction.update();
            ScriptManager.preInitialize();

            IWindowView window = new IWindowView(stage);

            window.setContent(new MainView(stage));
            window.setType(IWindowType.WINDOW);
            window.create();
            window.show();
        } catch (Exception e) {
            new ShowErrorDialog(e).display();
        }
    }

    @Override
    public void stop() {
        System.exit(0);
    }
}

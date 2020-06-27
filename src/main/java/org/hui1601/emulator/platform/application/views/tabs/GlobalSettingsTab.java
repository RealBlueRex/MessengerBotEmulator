package org.hui1601.emulator.platform.application.views.tabs;

import javafx.collections.ObservableMap;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import org.hui1601.emulator.managers.FileManager;
import org.hui1601.emulator.platform.application.views.MainView;
import org.hui1601.emulator.platform.application.views.dialogs.ShowErrorDialog;
import org.hui1601.emulator.platform.ui.components.IButton;
import org.hui1601.emulator.platform.ui.components.ICodeArea;
import org.hui1601.emulator.settings.Settings;
import org.hui1601.emulator.utils.ResourceUtils;

import javax.imageio.ImageIO;
import java.io.File;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class GlobalSettingsTab {
    private static ObservableMap<String, Object> nameSpace;

    private static StackPane root;

    public static void initialize() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ResourceUtils.getForm("GlobalSettingsTab"));
        loader.setController(null);
        loader.load();

        nameSpace = loader.getNamespace();

        root = loader.getRoot();

        IButton btnApply = (IButton) nameSpace.get("btnApply");
        IButton btnRefresh = (IButton) nameSpace.get("btnRefresh");

        btnApply.setType(IButton.ACTION);
        btnApply.setOnAction(event ->
        {
            ProgramTab.apply();
            DebugTab.apply();
            BotsTab.apply();
            DeviceTab.apply();
        });

        btnRefresh.setOnAction(event ->
        {
            ProgramTab.refresh();
            DebugTab.refresh();
            BotsTab.refresh();
            DeviceTab.refresh();
        });

        ProgramTab.initialize();
        DebugTab.initialize();
        BotsTab.initialize();
        DeviceTab.initialize();
    }

    public static ObservableMap<String, Object> getNameSpace() {
        return nameSpace;
    }

    public static TabPane getComponent() {
        return (TabPane) root.getChildren().get(0);
    }

    public static StackPane getRoot() {
        return root;
    }

    public static class ProgramTab {
        static Settings.Public data = Settings.getPublicSetting("program");

        static CheckBox chkAutoCompile = (CheckBox) nameSpace.get("chkAutoCompile");
        static CheckBox chkAutoSave = (CheckBox) nameSpace.get("chkAutoSave");

        static ICodeArea cdaScriptDefault = (ICodeArea) nameSpace.get("cdaScriptDefault");
        static ICodeArea cdaScriptUnified = (ICodeArea) nameSpace.get("cdaScriptUnified");

        static File fileScriptDefault = FileManager.getDataFile("script_default.js");
        static File fileScriptUnified = FileManager.getDataFile("script_unified.js");

        public static void initialize() {
            refresh();
        }

        public static void apply() {
            HashMap<String, Object> map = new HashMap<>();

            map.put("autoCompile", chkAutoCompile.isSelected());
            map.put("autoSave", chkAutoSave.isSelected());

            data.putMap(map);

            FileManager.save(fileScriptDefault, cdaScriptDefault.getText());
            FileManager.save(fileScriptUnified, cdaScriptUnified.getText());
        }

        public static void refresh() {
            chkAutoCompile.setSelected(data.getBoolean("autoCompile"));
            chkAutoSave.setSelected(data.getBoolean("autoSave"));

            cdaScriptDefault.setText(FileManager.read(fileScriptDefault));
            cdaScriptUnified.setText(FileManager.read(fileScriptUnified));
        }
    }

    public static class DebugTab {
        static Settings.Public data;
        static TextField txfHtmlTime = (TextField) nameSpace.get("txfHtmlTime");
        static TextField txfRoomName = (TextField) nameSpace.get("txfRoomName");
        static TextField txfSenderName = (TextField) nameSpace.get("txfSenderName");
        static TextField txfBotName = (TextField) nameSpace.get("txfBotName");
        static TextField txfPackageName = (TextField) nameSpace.get("txfPackageName");
        static CheckBox chkIsGroupChat = (CheckBox) nameSpace.get("chkIsGroupChat");
        static IButton btnSenderProfile = (IButton) nameSpace.get("btnSenderProfile");
        static IButton btnBotProfile = (IButton) nameSpace.get("btnBotProfile");
        static AtomicReference<Image> imgSenderProfile = new AtomicReference<>();
        static AtomicReference<Image> imgBotProfile = new AtomicReference<>();

        static {
            data = Settings.getPublicSetting("debug");
        }

        public static void initialize() {
            btnSenderProfile.setType(IButton.ACTION);
            btnSenderProfile.setOnAction(event ->
            {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*.jpg", "*.png", "*.gif"));
                fileChooser.setTitle("Change Profile");

                File file = fileChooser.showOpenDialog(MainView.getStage());

                if (file != null) {
                    imgSenderProfile.set(new Image(file.toURI().toString()));
                }
            });

            btnBotProfile.setType(IButton.ACTION);
            btnBotProfile.setOnAction(event ->
            {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image File", "*.jpg", "*.png", "*.gif"));
                fileChooser.setTitle("Change Profile");

                File file = fileChooser.showOpenDialog(MainView.getStage());

                if (file != null) {
                    imgBotProfile.set(new Image(file.toURI().toString()));
                }
            });

            refresh();
        }

        public static void apply() {
            HashMap<String, Object> map = new HashMap<>();

            map.put("room", txfRoomName.getText());
            map.put("sender", txfSenderName.getText());
            map.put("bot", txfBotName.getText());
            map.put("package", txfPackageName.getText());
            map.put("isGroupChat", chkIsGroupChat.isSelected());
            map.put("htmlTimeOut", Integer.valueOf(txfHtmlTime.getText()));

            try {
                if (imgSenderProfile.get() != null) {
                    ImageIO.write(SwingFXUtils.fromFXImage(imgSenderProfile.get(), null), "png", FileManager.getDataFile("profile_sender"));
                }

                if (imgBotProfile.get() != null) {
                    ImageIO.write(SwingFXUtils.fromFXImage(imgBotProfile.get(), null), "png", FileManager.getDataFile("profile_bot"));
                }

            } catch (Exception e) {
                new ShowErrorDialog(e).display();
            }

            data.putMap(map);
        }

        public static void refresh() {
            data = Settings.getPublicSetting("debug");
            txfHtmlTime.setText(data.getString("htmlTimeOut"));
            txfRoomName.setText(data.getString("room"));
            txfSenderName.setText(data.getString("sender"));
            txfBotName.setText(data.getString("bot"));
            txfPackageName.setText(data.getString("package"));

            chkIsGroupChat.setSelected(data.getBoolean("isGroupChat"));

            imgSenderProfile.set(null);
            imgBotProfile.set(null);
        }
    }

    public static class DeviceTab {
        static Settings.Public data;
        static TextField txfAndroidVersionCode = (TextField) nameSpace.get("txfAndroidVersionCode");
        static TextField txfAndroidVersionName = (TextField) nameSpace.get("txfAndroidVersionName");
        static TextField txfPhoneBrand = (TextField) nameSpace.get("txfPhoneBrand");
        static TextField txfPhoneModel = (TextField) nameSpace.get("txfPhoneModel");
        static TextField txfPlugType = (TextField) nameSpace.get("txfPlugType");
        static TextField txfBatteryLevel = (TextField) nameSpace.get("txfBatteryLevel");
        static TextField txfBatteryHealth = (TextField) nameSpace.get("txfBatteryHealth");
        static TextField txfBatteryTemperature = (TextField) nameSpace.get("txfBatteryTemperature");
        static TextField txfBatteryVoltage = (TextField) nameSpace.get("txfBatteryVoltage");
        static TextField txfBatteryStatus = (TextField) nameSpace.get("txfBatteryStatus");
        static CheckBox chkIsCharging = (CheckBox) nameSpace.get("chkIsCharging");

        static {
            data = Settings.getPublicSetting("device");
        }

        public static void initialize() {
            refresh();
        }

        public static void apply() {
            HashMap<String, Object> map = new HashMap<>();

            map.put("androidVersionCode", txfAndroidVersionCode.getText());
            map.put("androidVersionName", txfAndroidVersionName.getText());
            map.put("phoneBrand", txfPhoneBrand.getText());
            map.put("phoneModel", txfPhoneModel.getText());
            map.put("plugType", txfPlugType.getText());
            map.put("batteryLevel", Integer.valueOf(txfBatteryLevel.getText()));
            map.put("batteryHealth", Integer.valueOf(txfBatteryHealth.getText()));
            map.put("batteryTemperature", Integer.valueOf(txfBatteryTemperature.getText()));
            map.put("batteryVoltage", Integer.valueOf(txfBatteryVoltage.getText()));
            map.put("batteryStatus", Integer.valueOf(txfBatteryStatus.getText()));

            map.put("isCharging", chkIsCharging.isSelected());

            data.putMap(map);
        }

        public static void refresh() {
            txfAndroidVersionCode.setText(data.getString("androidVersionCode"));
            txfAndroidVersionName.setText(data.getString("androidVersionName"));
            txfPhoneBrand.setText(data.getString("plugType"));
            txfPhoneModel.setText(data.getString("phoneModel"));
            txfPlugType.setText(data.getString("plugType"));
            txfBatteryLevel.setText(data.getString("batteryLevel"));
            txfBatteryHealth.setText(data.getString("batteryHealth"));
            txfBatteryTemperature.setText(data.getString("batteryTemperature"));
            txfBatteryVoltage.setText(data.getString("batteryVoltage"));
            txfBatteryStatus.setText(data.getString("batteryStatus"));

            chkIsCharging.setSelected(data.getBoolean("isCharging"));
        }
    }

    public static class BotsTab {
        public static void initialize() {
            // refresh();
        }

        public static void apply() {

        }

        public static void refresh() {

        }
    }
}
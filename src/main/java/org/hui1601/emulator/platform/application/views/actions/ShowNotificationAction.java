package org.hui1601.emulator.platform.application.views.actions;

import org.hui1601.emulator.platform.application.views.dialogs.ShowEventDialog;

import java.awt.*;

public class ShowNotificationAction {
    public static void update(String title, String content) {
        try {
            TrayIcon trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().createImage("src/main/resources/icons/program.ico"), "Messenger Bot Simulator");
            trayIcon.setImageAutoSize(true);
            trayIcon.setToolTip("Messenger Bot Simulator");
            trayIcon.setActionCommand("aa");

            SystemTray.getSystemTray().add(trayIcon);

            trayIcon.displayMessage(title, content, TrayIcon.MessageType.INFO);
        } catch (Exception e) {
            new ShowEventDialog(title, content).display();//오류 발생시(ex. Gnome)
        }
    }
}
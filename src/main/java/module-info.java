open module org.hui1601.emulator
{
    // Java
    requires java.desktop;

    // Java Fx
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.swing;
    requires javafx.web;

    requires com.jfoenix;
    requires json.simple;
    requires org.apache.commons.io;
    requires org.fxmisc.richtext;
    requires flowless;
    // requires org.json;
    requires org.jsoup;
    requires rhino;
    requires java.net.http;
    requires jdk.httpserver;
    // exports org.hui1601.emulator;
}
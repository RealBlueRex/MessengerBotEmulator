package org.hui1601.emulator.compiler.engine;

import org.hui1601.emulator.compiler.api.Replier;

public class ResponseParameters {

    public String room;
    public String msg;
    public String sender;
    public boolean isGroupChat;
    public Replier replier;
    public org.hui1601.emulator.compiler.api.ImageDB ImageDB;
    public String packageName;

    public ResponseParameters(String room, String msg, String sender, boolean isGroupChat, Replier replier, org.hui1601.emulator.compiler.api.ImageDB imageDB, String packName) {
        this.room = room;
        this.msg = msg;
        this.sender = sender;
        this.isGroupChat = isGroupChat;
        this.replier = replier;
        this.ImageDB = imageDB;
        this.packageName = packName;
    }
}
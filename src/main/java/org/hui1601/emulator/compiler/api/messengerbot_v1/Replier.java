package org.hui1601.emulator.compiler.api.messengerbot_v1;

import org.hui1601.emulator.platform.application.views.actions.AddChatMessageAction;
import org.mozilla.javascript.annotations.JSFunction;

public class Replier {
    @JSFunction
    public Boolean reply(String message) {
        AddChatMessageAction.update(message, true);

        return true;
    }

    @JSFunction
    public Boolean reply(String room, String message, Boolean hideToast) {
        AddChatMessageAction.update(message, true);
        return true;
    }

    @JSFunction
    public Boolean reply(String room, String message) {
        AddChatMessageAction.update(message, true);
        return true;
    }
}
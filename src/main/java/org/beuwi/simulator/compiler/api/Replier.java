package org.beuwi.simulator.compiler.api;

import org.beuwi.simulator.platform.application.views.actions.AddChatMessageAction;
import org.mozilla.javascript.annotations.JSFunction;

import java.net.MalformedURLException;

public class Replier
{
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
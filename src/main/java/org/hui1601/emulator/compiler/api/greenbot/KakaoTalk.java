package org.hui1601.emulator.compiler.api.greenbot;

import org.hui1601.emulator.platform.application.views.actions.AddChatMessageAction;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSFunction;

public class KakaoTalk extends ScriptableObject{
    ScriptableObject scope;

    public KakaoTalk(ScriptableObject object) {
        super(object, object.getPrototype());
        this.scope = object;
    }

    @Override
    public String getClassName() {
        return "KakaoTalk";
    }

    @JSFunction
    public void reply(String room, String msg) {
        AddChatMessageAction.update(msg, true);
    }

    @JSFunction
    public void replyLast(String msg) {
        AddChatMessageAction.update(msg, true);
    }
}

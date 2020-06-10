package org.hui1601.emulator.compiler.engine;

import org.mozilla.javascript.Function;
import org.mozilla.javascript.ScriptableObject;

public class ScriptContainer {
    private Function responder = null;
    private ScriptableObject execScope = null;
    private Function onStartCompile = null;
    private ScriptableObject scope = null;
    private int optimization = 0;

    public void initialize(Function responder, ScriptableObject execScope, Function onStartCompile, ScriptableObject scope) {
        this.responder = responder;
        this.execScope = execScope;
        this.onStartCompile = onStartCompile;
        this.scope = scope;
    }

    public ScriptableObject getScope() {
        return scope;
    }

    public ScriptContainer setScope(ScriptableObject scope) {
        this.scope = scope;
        return this;
    }

    public ScriptableObject getExecScope() {
        return execScope;
    }

    public ScriptContainer setExecScope(ScriptableObject execScope) {
        this.execScope = execScope;
        return this;
    }

    public Function getOnStartCompile() {
        return onStartCompile;
    }

    public ScriptContainer setOnStartCompile(Function onStartCompile) {
        this.onStartCompile = onStartCompile;
        return this;
    }

    /* public ScriptContainer setScriptActivity(Function onCreate, Function onStop, Function onResume, Function onPause) {
        this.onCreate = onCreate;
        this.onStop = onStop;
        this.onResume = onResume;
        this.onPause = onPause;
        return this;
    } */

    public Function getResponder() {
        return responder;
    }

    public ScriptContainer setResponder(Function responder) {
        this.responder = responder;
        return this;
    }

    public int getOptimization() {
        return optimization;
    }

    public ScriptContainer setOptimization(int optimization) {
        this.optimization = optimization;
        return this;
    }
}
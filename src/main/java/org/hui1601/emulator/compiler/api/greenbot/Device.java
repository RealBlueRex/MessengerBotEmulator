package org.hui1601.emulator.compiler.api.greenbot;

import org.hui1601.emulator.settings.Settings;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSFunction;

@SuppressWarnings("serial")
public class Device extends ScriptableObject {
    public Device(ScriptableObject object) {
        super(object, object.getPrototype());
    }

    @Override
    public String getClassName() {
        return "Device";
    }

    @JSFunction
    public int getApiLevel() {
        return Settings.getPublicSetting("device").getInt("androidVersionCode");
    }

    @JSFunction
    public String getAndroidVersion() {
        return Settings.getPublicSetting("device").getString("androidVersionName");
    }

    @JSFunction
    public String getPhoneModel() {
        return Settings.getPublicSetting("device").getString("phoneModel");
    }

    @JSFunction
    public int getBatteryLevel() {
        return Settings.getPublicSetting("device").getInt("batteryLevel");
    }

    @JSFunction
    public int getBatteryTemp() {
        return Settings.getPublicSetting("device").getInt("batteryTemperature");
    }

}

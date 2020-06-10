package org.hui1601.emulator.compiler.api;

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
    public Object getBuild() {
        return null;
    }

    @JSFunction
    public int getAndroidVersionCode() {
        return Settings.getPublicSetting("device").getInt("androidVersionCode");
    }

    @JSFunction
    public String getAndroidVersionName() {
        return Settings.getPublicSetting("device").getString("androidVersionName");
    }

    @JSFunction
    public String getPhoneBrand() {
        return Settings.getPublicSetting("device").getString("phoneBrand");
    }

    @JSFunction
    public String getPhoneModel() {
        return Settings.getPublicSetting("device").getString("phoneModel");
    }

    @JSFunction
    public Boolean isCharging() {
        return Settings.getPublicSetting("device").getBoolean("isCharging");
    }

    @JSFunction
    public String getPlugType() {
        return Settings.getPublicSetting("device").getString("plugType");
    }

    @JSFunction
    public int getBatteryLevel() {
        return Settings.getPublicSetting("device").getInt("plugLevel");
    }

    @JSFunction
    public int getBatteryHealth() {
        // BATTERY_HEALTH_GOOD
        return Settings.getPublicSetting("device").getInt("batteryHealth");
    }

    @JSFunction
    public int getBatteryTemperature() {
        return Settings.getPublicSetting("device").getInt("batteryTemperature");
    }

    @JSFunction
    public int getBatteryVoltage() {
        return Settings.getPublicSetting("device").getInt("batteryVoltage");
    }

    @JSFunction
    public int getBatteryStatus() {
        return Settings.getPublicSetting("device").getInt("batteryStatus");
    }

    @JSFunction
    public Object getBatteryIntent() {
        return null;
    }
}

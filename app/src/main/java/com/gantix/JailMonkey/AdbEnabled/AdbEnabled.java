package com.gantix.JailMonkey.AdbEnabled;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

public class AdbEnabled {
    public static boolean AdbEnabled(Context context) {
        StringBuilder sb = new StringBuilder();
        sb.append("AdbEnabled: ");
        sb.append(Settings.Secure.getInt(context.getContentResolver(), "adb_enabled", 0) == 1);
        Log.i("AdbEnabled", sb.toString());
        return Settings.Global.getInt(context.getContentResolver(), "adb_enabled", 0) == 1;
    }
}

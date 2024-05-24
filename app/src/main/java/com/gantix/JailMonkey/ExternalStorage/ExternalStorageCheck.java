package com.gantix.JailMonkey.ExternalStorage;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

public class ExternalStorageCheck {
    public static boolean isOnExternalStorage(Context context) {
        String absolutePath = "";
        if (Build.VERSION.SDK_INT > 7) {
            try {
                return (context.getPackageManager().getPackageInfo(context.getPackageName(), 0).applicationInfo.flags & 262144) == 262144;
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        try {
            absolutePath = context.getFilesDir().getAbsolutePath();
        } catch (Throwable unused2) {
        }
        if (absolutePath.startsWith("/data/")) {
            return false;
        }
        if (!absolutePath.contains("/mnt/")) {
            if (!absolutePath.contains("/sdcard/")) {
                return false;
            }
        }
        return true;
    }
}

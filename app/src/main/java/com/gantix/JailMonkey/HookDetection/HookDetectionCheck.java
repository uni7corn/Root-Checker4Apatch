package com.gantix.JailMonkey.HookDetection;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import com.google.android.gms.fido.fido2.api.common.UserVerificationMethods;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class HookDetectionCheck {
    public static boolean hookDetected(Context context) {
        List<ApplicationInfo> installedApplications = context.getPackageManager().getInstalledApplications(UserVerificationMethods.USER_VERIFY_PATTERN);
        String[] strArr = {"de.robv.android.xposed.installer", "com.saurik.substrate", "de.robv.android.xposed"};
        if (installedApplications != null) {
            Iterator<ApplicationInfo> it = installedApplications.iterator();
            while (it.hasNext()) {
                if (Arrays.asList(strArr).contains(it.next().packageName)) {
                    return true;
                }
            }
        }
        return advancedHookDetection(context);
    }

    private static boolean advancedHookDetection(Context context) {
        try {
            throw new Exception();
        } catch (Exception e) {
            int i = 0;
            for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                if (stackTraceElement.getClassName().equals("com.android.internal.os.ZygoteInit") && (i = i + 1) == 2) {
                    return true;
                }
                if (stackTraceElement.getClassName().equals("com.saurik.substrate.MS$2") && stackTraceElement.getMethodName().equals("invoked")) {
                    return true;
                }
                if (stackTraceElement.getClassName().equals("de.robv.android.xposed.XposedBridge") && stackTraceElement.getMethodName().equals("main")) {
                    return true;
                }
                if (stackTraceElement.getClassName().equals("de.robv.android.xposed.XposedBridge") && stackTraceElement.getMethodName().equals("handleHookedMethod")) {
                    return true;
                }
            }
            return checkFrida(context);
        }
    }

    private static boolean checkFrida(Context context) {
        List<ActivityManager.RunningServiceInfo> runningServices = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getRunningServices(300);
        if (runningServices != null) {
            for (int i = 0; i < runningServices.size(); i++) {
                if (runningServices.get(i).process.contains("fridaserver")) {
                    return true;
                }
            }
        }
        return false;
    }
}

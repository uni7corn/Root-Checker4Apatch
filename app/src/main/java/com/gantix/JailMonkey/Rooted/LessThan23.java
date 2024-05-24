package com.gantix.JailMonkey.Rooted;

import java.io.File;

/* loaded from: classes.dex */
public class LessThan23 implements CheckApiVersion {
    @Override // com.gantix.JailMonkey.Rooted.CheckApiVersion
    public boolean checkRooted() {
        return canExecuteCommand("/system/xbin/which su") || isSuperuserPresent();
    }

    private static boolean canExecuteCommand(String str) {
        try {
            return Runtime.getRuntime().exec(str).waitFor() == 0;
        } catch (Exception unused) {
            return false;
        }
    }

    private static boolean isSuperuserPresent() {
        String[] strArr = {"/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su", "/system/bin/failsafe/su", "/data/local/su"};
        for (int i = 0; i < 9; i++) {
            if (new File(strArr[i]).exists()) {
                return true;
            }
        }
        return false;
    }
}

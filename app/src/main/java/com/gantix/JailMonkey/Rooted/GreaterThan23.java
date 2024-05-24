package com.gantix.JailMonkey.Rooted;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/* loaded from: classes.dex */
public class GreaterThan23 implements CheckApiVersion {
    @Override // com.gantix.JailMonkey.Rooted.CheckApiVersion
    public boolean checkRooted() {
        return checkRootMethod1() || checkRootMethod2();
    }

    private boolean checkRootMethod1() {
        String[] strArr = {"/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su", "/system/bin/failsafe/su", "/data/local/su"};
        for (int i = 0; i < 9; i++) {
            if (new File(strArr[i]).exists()) {
                return true;
            }
        }
        return false;
    }

    private boolean checkRootMethod2() {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(new String[]{"/system/xbin/which", "su"});
            if (new BufferedReader(new InputStreamReader(process.getInputStream())).readLine() != null) {
                process.destroy();
                return true;
            }
            process.destroy();
            return false;
        } catch (Throwable unused) {
            if (process != null) {
                process.destroy();
            }
            return false;
        }
    }
}

package com.gantix.JailMonkey.MockLocation;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import com.google.android.gms.fido.fido2.api.common.UserVerificationMethods;
import java.util.List;

public class MockLocationCheck {
    public static boolean isMockLocationOn(Context context) {
        if (Build.VERSION.SDK_INT < 23) {
            return "0".equals(Settings.Secure.getString(context.getContentResolver(), "mock_location"));
        }
        PackageManager packageManager = context.getPackageManager();
        List<ApplicationInfo> installedApplications = packageManager.getInstalledApplications(UserVerificationMethods.USER_VERIFY_PATTERN);
        if (installedApplications != null) {
            for (ApplicationInfo applicationInfo : installedApplications) {
                try {
                    String[] strArr = packageManager.getPackageInfo(applicationInfo.packageName, 4096).requestedPermissions;
                    if (strArr != null) {
                        for (String str : strArr) {
                            if (str.equals("android.permission.ACCESS_MOCK_LOCATION") && !applicationInfo.packageName.equals(context.getPackageName())) {
                                return true;
                            }
                        }
                    } else {
                        continue;
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    Log.e("Mock location check error", e.getMessage());
                }
            }
        }
        return false;
    }
}

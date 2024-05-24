package com.gantix.JailMonkey.Rooted;

import android.content.Context;
import android.os.Build;
import com.scottyab.rootbeer.RootBeer;
import java.util.HashMap;
import java.util.Map;

public class RootedCheck {
    private final boolean jailMonkeyResult = checkWithJailMonkeyMethod();
    private final RootBeerResults rootBeerResults;

    private static boolean checkWithJailMonkeyMethod() {
        CheckApiVersion lessThan23;
        if (Build.VERSION.SDK_INT >= 23) {
            lessThan23 = new GreaterThan23();
        } else {
            lessThan23 = new LessThan23();
        }
        return lessThan23.checkRooted();
    }

    public RootedCheck(Context context) {
        this.rootBeerResults = new RootBeerResults(context);
    }

    public boolean isJailBroken() {
        return this.jailMonkeyResult || this.rootBeerResults.isJailBroken();
    }

    public Map<String, Object> getResultByDetectionMethod() {
        HashMap hashMap = new HashMap();
        hashMap.put("jailMonkey", Boolean.valueOf(this.jailMonkeyResult));
        hashMap.put("rootBeer", this.rootBeerResults.toNativeMap());
        return hashMap;
    }

    /* loaded from: classes.dex */
    private static class RootBeerResults {
        private final boolean checkForDangerousProps;
        private final boolean checkForMagiskBinary;
        private final boolean checkForRWPaths;
        private final boolean checkForRootNative;
        private final boolean checkForSuBinary;
        private final boolean checkSuExists;
        private final boolean detectPotentiallyDangerousApps;
        private final boolean detectRootManagementApps;
        private final boolean detectTestKeys;

        RootBeerResults(Context context) {
            RootBeer rootBeer = new RootBeer(context);
            rootBeer.setLogging(false);
            this.detectRootManagementApps = rootBeer.detectRootManagementApps();
            this.detectPotentiallyDangerousApps = rootBeer.detectPotentiallyDangerousApps();
            this.checkForSuBinary = rootBeer.checkForSuBinary();
            this.checkForDangerousProps = rootBeer.checkForDangerousProps();
            this.checkForRWPaths = rootBeer.checkForRWPaths();
            this.detectTestKeys = rootBeer.detectTestKeys();
            this.checkSuExists = rootBeer.checkSuExists();
            this.checkForRootNative = rootBeer.checkForRootNative();
            this.checkForMagiskBinary = rootBeer.checkForMagiskBinary();
        }

        public boolean isJailBroken() {
            return this.detectRootManagementApps || this.detectPotentiallyDangerousApps || this.checkForSuBinary || this.checkForDangerousProps || this.checkForRWPaths || this.detectTestKeys || this.checkSuExists || this.checkForRootNative || this.checkForMagiskBinary;
        }

        public Map<String, Object> toNativeMap() {
            HashMap hashMap = new HashMap();
            hashMap.put("detectRootManagementApps", Boolean.valueOf(this.detectRootManagementApps));
            hashMap.put("detectPotentiallyDangerousApps", Boolean.valueOf(this.detectPotentiallyDangerousApps));
            hashMap.put("checkForSuBinary", Boolean.valueOf(this.checkForSuBinary));
            hashMap.put("checkForDangerousProps", Boolean.valueOf(this.checkForDangerousProps));
            hashMap.put("checkForRWPaths", Boolean.valueOf(this.checkForRWPaths));
            hashMap.put("detectTestKeys", Boolean.valueOf(this.detectTestKeys));
            hashMap.put("checkSuExists", Boolean.valueOf(this.checkSuExists));
            hashMap.put("checkForRootNative", Boolean.valueOf(this.checkForRootNative));
            hashMap.put("checkForMagiskBinary", Boolean.valueOf(this.checkForMagiskBinary));
            return hashMap;
        }
    }
}

package com.vineelsai.rootchecker;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.gantix.JailMonkey.AdbEnabled.AdbEnabled;
import com.gantix.JailMonkey.ExternalStorage.ExternalStorageCheck;
import com.gantix.JailMonkey.HookDetection.HookDetectionCheck;
import com.gantix.JailMonkey.MockLocation.MockLocationCheck;
import com.gantix.JailMonkey.Rooted.RootedCheck;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.chainfire.libsuperuser.BuildConfig;


/**
 * Created by hackest on 2017/3/3.
 */

public class MyApplication extends Application {

    private static MyApplication mContext;


    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;

        initLog();


    }


    private void initLog() {
        LogConfiguration config = new LogConfiguration.Builder()
                .logLevel(BuildConfig.DEBUG ? LogLevel.ALL             // Specify log level, logs below this level won't be printed, default: LogLevel.ALL
                        : LogLevel.NONE)
                .enableThreadInfo()                                    // Enable thread info, disabled by default
                .enableStackTrace(2)
                .enableBorder()
                .build();

        XLog.init(config);

    }


    public static MyApplication getApplication() {
        return mContext;
    }

    /**
     * 是否是主进程，多进程中，防止某些类进行多次实例化，导致bug
     *
     * @return
     */
    private boolean isMainProcess() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }


    public Map<String, Object> getConstants() {
        RootedCheck rootedCheck = new RootedCheck(mContext);
        HashMap hashMap = new HashMap();
        hashMap.put("isJailBroken", Boolean.valueOf(rootedCheck.isJailBroken()));
        hashMap.put("rootedDetectionMethods", rootedCheck.getResultByDetectionMethod());
        hashMap.put("hookDetected", Boolean.valueOf(HookDetectionCheck.hookDetected(mContext)));
        hashMap.put("canMockLocation", Boolean.valueOf(MockLocationCheck.isMockLocationOn(mContext)));
        hashMap.put("isOnExternalStorage", Boolean.valueOf(ExternalStorageCheck.isOnExternalStorage(mContext)));
        hashMap.put("AdbEnabled", Boolean.valueOf(AdbEnabled.AdbEnabled(mContext)));
        return hashMap;
    }

}

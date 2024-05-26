package com.vineelsai.rootchecker;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.gantix.JailMonkey.Rooted.RootedCheck;

import androidx.appcompat.app.AppCompatActivity;
import eu.chainfire.libsuperuser.Shell;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button verify_root = findViewById(R.id.verify_root);
        TextView root_status = findViewById(R.id.root_status);
        TextView android_version = findViewById(R.id.android_version);
        TextView device = findViewById(R.id.device);

        String pass = "Pass";
        String fail = "Fail";

        String redColorCode = "#FF0000";
        String greenColorCode = "#00FF00";

        String version_text = "Android Version : " + android.os.Build.VERSION.RELEASE;
        android_version.setText(version_text);

        String device_model = "Device : " + Build.MODEL;
        device.setText(device_model);


        RootedCheck rootedCheck = new RootedCheck(MainActivity.this);

        verify_root.setOnClickListener(v -> {
            String phone_rooted = "Your Phone Is Rooted";
            String phone_not_rooted = "Phone Is Not Rooted";
            if (Shell.SU.available() || rootedCheck.isJailBroken()) {
                root_status.setText(phone_rooted);
                root_status.setTextSize(24f);
                root_status.setTextColor(Color.parseColor(redColorCode));
            } else {
                root_status.setText(phone_not_rooted);
                root_status.setTextSize(24f);
                root_status.setTextColor(Color.parseColor(greenColorCode));
            }
        });


        LogUtils.e(rootedCheck.getResultByDetectionMethod().get("rootBeer"));

    }


}
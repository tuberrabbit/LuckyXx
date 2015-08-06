package com.thoughtworks.btu.luckyxx;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Switch;


public class LaunchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        Switch accessibility = (Switch) findViewById(R.id.accessibility);
        Switch notification = (Switch) findViewById(R.id.notification);

        if (Build.VERSION.SDK_INT >= 18) {
            notification.setVisibility(View.VISIBLE);
        } else {
            notification.setVisibility(View.GONE);
        }
    }

    public void handleFetchLuckyMoneyButtonClicked(View view) {
        startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
    }

    public void handleNotificationButtonClicked(View view) {
        startActivity(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS));
    }

}

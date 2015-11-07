package com.thoughtworks.btu.luckyxx;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Switch;


public class LaunchActivity extends Activity {

    private Switch accessibility;
    private Switch notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        unlockScreen();
        setContentView(R.layout.activity_launch);

        accessibility = (Switch) findViewById(R.id.accessibility);
        notification = (Switch) findViewById(R.id.notification);

        if (Build.VERSION.SDK_INT >= 18) {
            notification.setVisibility(View.VISIBLE);
        } else {
            notification.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        try {
            PendingIntent pendingIntent = getIntent().getParcelableExtra(FetchLuckyMoneyService.PAR_KEY);
            if (pendingIntent != null) {
                pendingIntent.send();

                finish();
            }
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    private void unlockScreen() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
    }

    public void handleFetchLuckyMoneyButtonClicked(View view) {
        startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
    }

    public void handleNotificationButtonClicked(View view) {
        startActivity(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS));
    }

    @Override
    protected void onResume() {
        super.onResume();
        changeLabelStatus();
    }

    private void changeLabelStatus() {
        accessibility.setChecked(isAccessibleEnabled());
        if (Build.VERSION.SDK_INT >= 18) {
            notification.setChecked(isNotificationEnabled());
        }
    }

    private boolean isAccessibleEnabled() {
        String enabledProviders = Settings.Secure.getString(getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
        return (!TextUtils.isEmpty(enabledProviders)) && enabledProviders.contains(getPackageName() + "/" + getPackageName() + ".FetchLuckyMoneyService");
    }

    private boolean isNotificationEnabled() {
        String enabledListeners = Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners");
        return (!TextUtils.isEmpty(enabledListeners)) && enabledListeners.contains(getPackageName() + "/" + getPackageName() + ".NotificationListener");
    }


}

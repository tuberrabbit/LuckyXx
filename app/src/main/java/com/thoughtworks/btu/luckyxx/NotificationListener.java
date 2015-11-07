package com.thoughtworks.btu.luckyxx;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class NotificationListener extends NotificationListenerService {
    public static final String PAR_KEY = "com.thoughtworks.btu.luckyxx";

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Notification notification = sbn.getNotification();
        if (null != notification) {
            Bundle extras = notification.extras;
            if (null != extras) {
                List<String> textList = new ArrayList<String>();
                String title = extras.getString("android.title");
                if (!TextUtils.isEmpty(title)) textList.add(title);

                String detailText = extras.getString("android.text");
                if (!TextUtils.isEmpty(detailText)) textList.add(detailText);

                if (textList.size() > 0) {
                    for (String text : textList) {
                        if (!TextUtils.isEmpty(text) && text.contains("[微信红包]")) {
                            final PendingIntent pendingIntent = notification.contentIntent;

                            launchActivity(pendingIntent);
                            break;
                        }
                    }
                }
            }
        }
    }

    private void launchActivity(PendingIntent pendingIntent) {
        Intent intent = new Intent(this, LaunchActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(PAR_KEY, pendingIntent);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
    }
}

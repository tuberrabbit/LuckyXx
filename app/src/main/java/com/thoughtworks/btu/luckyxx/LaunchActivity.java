package com.thoughtworks.btu.luckyxx;

import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;


public class LaunchActivity extends ActionBarActivity {

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

}

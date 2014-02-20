package com.dummies.android.silentmodetoggle;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Chicken on 2/19/14.
 */
public class AppWidget extends android.appwidget.AppWidgetProvider {
    @Override
    public void onReceive(Context ctxt, Intent intent) {
        if (intent.getAction()==null) {
            // Do Something
        }
        else {
            super.onReceive(ctxt, intent);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // Do Something
    }
}

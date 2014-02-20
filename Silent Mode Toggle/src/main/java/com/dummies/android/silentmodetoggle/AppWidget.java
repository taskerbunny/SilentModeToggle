package com.dummies.android.silentmodetoggle;

import android.app.Activity;
import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.widget.RemoteViews;

/**
 * Created by Chicken on 2/19/14.
 */
public class AppWidget extends android.appwidget.AppWidgetProvider {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction()==null) {
            // Do Something
            context.startService(new Intent(context, ToggleService.class));
        }
        else {
            super.onReceive(context, intent);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // Do Something
        context.startService(new Intent(context, ToggleService.class));
    }

    /**
     * IntentService class
     */
    public static class ToggleService extends IntentService {
        public ToggleService() {
            //Call the Super class constructor
            super(ToggleService.class.getName());
        }

        @Override
        protected void onHandleIntent(Intent intent) {
            ComponentName me = new ComponentName(this, AppWidget.class);

            AppWidgetManager mgr = AppWidgetManager.getInstance(this);

            mgr.updateAppWidget(me, buildUpdate(this));
        }

        //The mola.
        private RemoteViews buildUpdate(Context context) {
            //RemoteView is the home screen.
            RemoteViews updateViews = new RemoteViews(context.getPackageName(),R.layout.widget);

            //Get AudioManager
            AudioManager audioManager = (AudioManager)context.getSystemService(Activity.AUDIO_SERVICE);

            //
            if(audioManager.getRingerMode() == AudioManager.RINGER_MODE_SILENT) {
                updateViews.setImageViewResource(R.id.phoneState, R.drawable.phone_state_normal);
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            }
            else {
                updateViews.setImageViewResource(R.id.phoneState, R.drawable.phone_state_silent);
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            }

            //Rebuild the entire event hierarchy in the remote view (home screen)
            Intent i = new Intent(this, AppWidget.class);
            PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
            updateViews.setOnClickPendingIntent(R.id.phoneState, pi);

            //
            return updateViews;
        }
    }
}




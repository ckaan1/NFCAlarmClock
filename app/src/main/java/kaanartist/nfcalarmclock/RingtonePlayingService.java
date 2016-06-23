package kaanartist.nfcalarmclock;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class RingtonePlayingService extends Service {

    MediaPlayer media_song;
    boolean isRunning;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        String state = intent.getExtras().getString("extra");

        if( !this.isRunning && state.equals("alarm set") ) {
            media_song = MediaPlayer.create(this, R.raw.all_i_want);
            media_song.start();
            isRunning = true;

            // notification service setup
            NotificationManager notifyManager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            // set up an intent that goest to the Main Activity
            Intent intent_main_activity = new Intent(this.getApplicationContext(), MainActivity.class);
            // set up a pending intent
            PendingIntent pending_intent_notification =
                    PendingIntent.getActivity(this, 0, intent_main_activity, 0);

            // make the notification parameters
            Notification notificationPopup = new Notification.Builder(this)
                    .setContentTitle("An alarm is going off!")
                    .setContentText("Click me!")
                    .setContentIntent(pending_intent_notification)
                    .setSmallIcon(R.drawable.chibi_link)
                    .setAutoCancel(true)
                    .build();

            notifyManager.notify(1,notificationPopup);
        }
        else if( this.isRunning && state.equals("alarm cancel") ) {
            media_song.stop();
            isRunning = false;
        }

        return START_NOT_STICKY;
    }

    public void onDestroy() {
        super.onDestroy();
        this.isRunning = false;
    }
}

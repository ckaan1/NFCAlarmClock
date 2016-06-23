package kaanartist.nfcalarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class Alarm_Receiver extends BroadcastReceiver {
    private AlarmManager alarm_manager;
    private PendingIntent pending_intent;

    @Override
    public void onReceive(Context context, Intent intent) {
        // fetch extra strings from the intent
        String get_your_string = intent.getExtras().getString("extra");

        // create an intent to the ringtone service
        Intent service_intent = new Intent(context, RingtonePlayingService.class);

        service_intent.putExtra("extra",get_your_string);

        context.startService(service_intent);
    }

    public void setAlarm(Context context, int hour, int minute) {

        alarm_manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, Alarm_Receiver.class);
        intent.putExtra("extra","alarm set");
        pending_intent = PendingIntent.getBroadcast(context, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        int current_hour = calendar.get(Calendar.HOUR_OF_DAY);
        int current_minute = calendar.get(Calendar.MINUTE);

        if( current_hour >= hour && current_minute >= minute ) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        alarm_manager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pending_intent);
    }

    public void cancelAlarm(Context context) {
        if( alarm_manager!=null ) {
            alarm_manager.cancel(pending_intent);
        }

        Intent intent = new Intent(context, Alarm_Receiver.class);
        intent.putExtra("extra","alarm cancel");
        context.sendBroadcast(intent);
    }
}

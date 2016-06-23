package kaanartist.nfcalarmclock;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends AppCompatActivity {

    TimePicker alarmTimePicker;
    TextView update_text;

    Alarm_Receiver alarm = new Alarm_Receiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // initialize the time picker
        alarmTimePicker = (TimePicker) findViewById(R.id.timePicker);

        // initialize our text update box
        update_text = (TextView) findViewById(R.id.update_text);

        // initialize set alarm button
        Button set_alarm = (Button) findViewById(R.id.set_alarm);

        // create an onClick listener to set the alarm
        set_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int hour = alarmTimePicker.getHour();
                int minute = alarmTimePicker.getMinute();

                String output;
                if( hour > 12 ) {
                    if( minute < 10 ) {
                        output = "Alarm Set to " + String.valueOf(hour-12) + ":0" + minute + " PM.";
                    }
                    else {
                        output = "Alarm Set to " + String.valueOf(hour-12) + ":" + minute + " PM.";
                    }
                }
                else {
                    if( minute < 10 ) {
                        output = "Alarm Set to " + String.valueOf(hour) + ":0" + minute + " AM.";
                    }
                    else {
                        output = "Alarm Set to " + String.valueOf(hour) + ":" + minute + " AM.";
                    }
                }
                set_alarm_text(output);

                // set the alarm
                alarm.setAlarm(MainActivity.this, hour, minute);
            }
        });

        // initialize cancel alarm button
        Button cancel_alarm = (Button) findViewById(R.id.cancel_alarm);

        // create an onClick listener to cancel the alarm
        cancel_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_alarm_text("Alarm Off.");

                // cancel the alarm
                alarm.cancelAlarm(MainActivity.this);
            }
        });
    }


    private void set_alarm_text(String output) {
        update_text.setText(output);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

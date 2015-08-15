package com.cwm71.memo808224;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.io.Serializable;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Ifetayo Agunbiade 808224 on 11/04/2015.
 * ReminderActivity class.
 * Thanks to the developers at http://icons8.com/ for all icons used in this project
 */
public class ReminderActivity extends ActionBarActivity {

    private String memotime = "";
    DatePicker alarmDate;
    TimePicker alarmTime;
    Bundle editAlarmBundle;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        calendar = Calendar.getInstance();
        alarmDate = (DatePicker) findViewById(R.id.datePicker);
        alarmTime = (TimePicker) findViewById(R.id.timePicker);

        editAlarmBundle = getIntent().getExtras();

        if(editAlarmBundle != null){
            memotime = editAlarmBundle.getString("eAlarm");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
            try {
                calendar.setTime(dateFormat.parse(memotime));
            } catch (ParseException ex) {
                //do nothing for now
            }
            alarmDate.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            alarmTime.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
            alarmTime.setCurrentMinute(calendar.get(Calendar.MINUTE));
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_reminder, menu);
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

    /**
     * This method handles the ok button event. It packs the date in string format
     * and sends to the memo activity callback method onActivityResult.
     * @param view
     */
    public void SetReminder(View view){
        Intent intent = getIntent();

        calendar.set(alarmDate.getYear(),alarmDate.getMonth(), alarmDate.getDayOfMonth(), alarmTime.getCurrentHour(), alarmTime.getCurrentMinute());

        memotime = String.format("%tF %1$tI:%1$tM %1$Tp", calendar);
        //"%1$tA %1$tb %1$td %1$tY at %1$tI:%1$tM %1$Tp"
        intent.putExtra("alarmTime", memotime);
        this.setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * Cancels the activity
     * @param view
     */
    public void CancelReminder(View view){
        finish();
    }
}

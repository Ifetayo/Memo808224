package com.cwm71.memo808224;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;

/**
 * Created by Ifetayo Agunbiade 808224 on 11/04/2015.
 * Memo Activity class. This class handles the activity that allows
 * the user create and edit memos.
 */
public class MemoActivity extends ActionBarActivity {

    private static final int NEW_ENTRY_REQUEST_CODE = 1;
    String memoAlarm = "";
    Calendar calendar;
    EditText memoText;
    Spinner modeDropDown;
    Bundle editMemoBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memo_activity);

        memoText = (EditText) findViewById(R.id.memoTextArea);
        modeDropDown = (Spinner) findViewById(R.id.mode);
        calendar = Calendar.getInstance();

        editMemoBundle = getIntent().getExtras();

        if(editMemoBundle != null){ //edit memo
            memoText.setText(editMemoBundle.getString("eMemoText"));
            modeDropDown.setSelection(editMemoBundle.getInt("eMemoMode"));
            memoAlarm = editMemoBundle.getString("eMemoAlarm");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_memo, menu);
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
     * This method is used to pack the data to be stored and return this packed data to the
     * onActivityResult method in the main activity.
     * @param view
     */
    public void SaveMemo(View view){
        Intent intent = new Intent();
        if(memoText.getText().toString().length() == 0){
            this.setResult(RESULT_CANCELED, intent);
        }
        else{
            if(editMemoBundle != null)
                intent.putExtra("memoId", editMemoBundle.getLong("id"));
            intent.putExtra("memotext", memoText.getText().toString());
            intent.putExtra("memomode", modeDropDown.getSelectedItemPosition());
            intent.putExtra("memoalarm", memoAlarm);
            this.setResult(RESULT_OK, intent);
        }
        finish();
    }

    /**
     * Used to handle the returned data from the startactivityforresult call to reminder activity.
     *
     * @param requestCode int request code information must match that of the calling activity
     * @param resultCode  int result code set by the called activity
     * @param data Intent This is data collected from the reminder activity
     * @return
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == NEW_ENTRY_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                memoAlarm = data.getStringExtra("alarmTime");
            }
        }
    }

    /**
     * Starts the Reminder activity
     * @param view
     */
    public void SetReminder(View view){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        if(editMemoBundle != null && !memoAlarm.isEmpty()){
            bundle.putString("eAlarm", memoAlarm);
            intent.putExtras(bundle);
        }
        intent.setClass(this, ReminderActivity.class);
        startActivityForResult(intent, NEW_ENTRY_REQUEST_CODE);
    }

    /**
     * Cancels the MemoActivity
     * Improvement: instead of finish method call, a result_cancelled can be sent to
     * the onActivityResult method in the main activity.
     * @param view
     */
    public void DiscardMemo(View view){
        finish();
    }
}

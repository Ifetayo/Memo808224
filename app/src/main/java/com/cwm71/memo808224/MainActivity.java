package com.cwm71.memo808224;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Ifetayo Agunbiade 808224 on 11/04/2015.
 * Main application class.
 * Thanks to the developers at http://icons8.com/ for all icons used in this project
 */

public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private static final int ADD_MEMO_REQUEST_CODE = 1;
    private static final int EDIT_MEMO_REQUEST_CODE = 2;
    private MemoDBWrapper dbWrapper;
    MemoCursorAdapter memoCursorAdapter;
    ListView memoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        memoList = (ListView) findViewById(R.id.memoListView);
        memoList.setOnItemClickListener(this);
        OpenDB();
        GetMemoList();
        registerForContextMenu(memoList);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, "Delete Memo");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals("Delete Memo")) {
            AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int position = menuInfo.position;
            Cursor memoCursor = (Cursor) memoCursorAdapter.getItem(position);
            dbWrapper.DeleteMemo(memoCursor.getInt(memoCursor.getColumnIndex(MemoDBWrapper.ID_COLUMN)));
            memoCursorAdapter.changeCursor(dbWrapper.GetMemoList());
            Toast.makeText(this, "Memo Deleted", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private void OpenDB() {
        dbWrapper = new MemoDBWrapper(this);
        dbWrapper.OpenDatabaseConnection();
    }

    private void CloseDB(){
        dbWrapper.CloseDatabaseConnection();
    }

    private void GetMemoList(){
        Cursor cursor = dbWrapper.GetMemoList();
        String[] from = new String[]{MemoDBWrapper.MEMO_COLUMN, MemoDBWrapper.DATE_TIME_COLUMN};
        int[] in = new int[]{R.id.memoText, R.id.alarmText};
        memoCursorAdapter = new MemoCursorAdapter(this, cursor, 0);
        memoList.setAdapter(memoCursorAdapter);
        //CloseDB();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
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
     * Add a new memo button event handler method.
     * @param view
     */
    public void AddNewMemo(View view){
        Intent intent = new Intent(this, MemoActivity.class);
        startActivityForResult(intent, ADD_MEMO_REQUEST_CODE);
    }

    /**
     * Used to handle the callback data from the startActivityForResult call to memo activity.
     *
     * @param requestCode int request code information must match that of the calling activity
     * @param resultCode  int result code set by the called activity
     * @param data Intent This is data collected from the memo activity
     * @return
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == ADD_MEMO_REQUEST_CODE){ //handles new entries
            if(resultCode == RESULT_OK){
                dbWrapper.InsertMemo(data.getStringExtra("memotext"), data.getStringExtra("memoalarm"), data.getIntExtra("memomode", 0));
                memoCursorAdapter.changeCursor(dbWrapper.GetMemoList());
            }
        }
        else if(requestCode == EDIT_MEMO_REQUEST_CODE){ //handles edited entries
            if(resultCode == RESULT_OK){
                dbWrapper.UpdateMemo(data.getLongExtra("memoId", 0), data.getStringExtra("memotext"), data.getStringExtra("memoalarm"), data.getIntExtra("memomode", 0));
                memoCursorAdapter.changeCursor(dbWrapper.GetMemoList());
            }
        }
    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p/>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor memoCursor = dbWrapper.GetMemo(id);

        if(memoCursor.moveToFirst()){
            Intent editIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putLong("id", memoCursor.getInt(memoCursor.getColumnIndex(MemoDBWrapper.ID_COLUMN)));
            bundle.putString("eMemoText", memoCursor.getString(memoCursor.getColumnIndex(MemoDBWrapper.MEMO_COLUMN)));
            bundle.putString("eMemoAlarm", memoCursor.getString(memoCursor.getColumnIndex(MemoDBWrapper.DATE_TIME_COLUMN)));
            bundle.putInt("eMemoMode", memoCursor.getInt(memoCursor.getColumnIndex(MemoDBWrapper.MEMO_MODE_COLUMN)));

            editIntent.setClass(this, MemoActivity.class);
            editIntent.putExtras(bundle);

            startActivityForResult(editIntent, EDIT_MEMO_REQUEST_CODE);
        }
        else{ //that is the memo record could not be found in the memo table
            //do nothing for now
        }
    }
}

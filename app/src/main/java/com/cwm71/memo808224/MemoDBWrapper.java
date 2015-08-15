package com.cwm71.memo808224;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;

import java.util.Date;

/**
 * Created by Ifetayo Agunbiade 808224 on 11/04/2015.
 * This class serves as a wrapper for the DBHelper class. It encapsulates SQL lite database functions.
 */
public class MemoDBWrapper {
    public static final String TABLE_NAME = "memotable";
    public static final String ID_COLUMN = "_id";
    public static final String MEMO_COLUMN = "memo";
    public static final String DATE_TIME_COLUMN = "datetime";
    public static final String MEMO_MODE_COLUMN = "mode";

    private DBHelper dbHelper;
    private SQLiteDatabase database;

    /**
     * Class constructor
     *
     * @param context Context
     * @return
     */
    public MemoDBWrapper(Context context){
        dbHelper = new DBHelper(context);
        //database = dbHelper.getWritableDatabase();
    }

    /**
     * Used to open the database connection
     *
     * @return MemoDBWrapper
     */
    public MemoDBWrapper OpenDatabaseConnection(){
        database = dbHelper.getWritableDatabase();
        return this;
    }

    /**
     * Used to close the database connection
     * @return
     */
    public void CloseDatabaseConnection(){
        dbHelper.close();
    }

    /**
     * Used to save a new memo.
     * @param memo String this is the text the user types in
     * @param datetime  String This is the date and time attribute for the alarm
     * @param mode int This is the mode of the memo, 1 for normal, 2 for important and 3 for urgent
     * @return
     */
    public void InsertMemo(String memo, String datetime, int mode){
        ContentValues contentValues = new ContentValues();

        contentValues.put(MEMO_COLUMN, memo);
        contentValues.put(DATE_TIME_COLUMN, datetime);
        contentValues.put(MEMO_MODE_COLUMN, mode);

        database.insert(TABLE_NAME, null, contentValues);
    }

    /**
     * Used to get the cursor of the selected row on the list memos
     * @param memoId column ID  of the selected memo
     */
    public Cursor GetMemo(long memoId){
        String where = ID_COLUMN + "=" + memoId;
        String[] allRows = new String[]{ID_COLUMN, MEMO_COLUMN, DATE_TIME_COLUMN, MEMO_MODE_COLUMN};
        Cursor cursor = database.query(true, TABLE_NAME, allRows, where, null, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    /**
     * Used to update a memo in the database
     * @param memoID int id of memo to be updated
     * @param memo String text of memo to be update
     * @param datetime String datetime of alarm, if any
     * @param mode int integer identifier of the memo mode
     */
    public void UpdateMemo(long memoID, String memo, String datetime, int mode){
        String where = ID_COLUMN + "=" + memoID;
        ContentValues contentValues = new ContentValues();
        contentValues.put(MEMO_COLUMN, memo);
        contentValues.put(DATE_TIME_COLUMN, datetime);
        contentValues.put(MEMO_MODE_COLUMN, mode);

        database.update(TABLE_NAME, contentValues, where, null);
        //return;
    }

    /**
     * Called to return a cursor of all the memos in the database
     * @return cursor Cursor
     */
    public Cursor GetMemoList() {
        String[] allRows = new String[]{ID_COLUMN, MEMO_COLUMN, DATE_TIME_COLUMN, MEMO_MODE_COLUMN};
        Cursor cursor = database.query(TABLE_NAME, allRows, null, null, null, null, MEMO_MODE_COLUMN + " DESC");
        if(cursor != null){
            cursor.moveToFirst();
        }
        return cursor;
    }

    /**
     * Called to delete a memo
     * @param memoId long id of memo to be deleted
     */
    public void DeleteMemo(long memoId){
        String where = ID_COLUMN + "=" + memoId;
        database.delete(TABLE_NAME, where, null);
    }
}

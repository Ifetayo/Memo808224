package com.cwm71.memo808224;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import static android.R.color.holo_blue_bright;

/**
 * Created by Ifetayo Agunbiade 808224 on 11/04/2015.
 * This class extends the CursorAdapter providing a much more flexible adapter
 * for my list view items
 */
public class MemoCursorAdapter extends CursorAdapter {

    public MemoCursorAdapter(Context context, Cursor cursor, int flag){
        super(context, cursor, 0);
    }
    /**
     * Makes a new view to hold the data pointed to by cursor.
     *
     * @param context Interface to application's global information
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.memo_list_item_layout, parent, false);
        return view;
    }

    /**
     * Bind an existing view to the data pointed to by cursor
     *
     * @param view    Existing view, returned earlier by newView
     * @param context Interface to application's global information
     * @param cursor  The cursor from which to get the data. The cursor is already
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView mText = (TextView) view.findViewById(R.id.memoText);
        mText.setText(cursor.getString(cursor.getColumnIndex(MemoDBWrapper.MEMO_COLUMN)));
        int memoColorID = cursor.getInt(cursor.getColumnIndex(MemoDBWrapper.MEMO_MODE_COLUMN));
        switch (memoColorID){
            case 0:
                mText.setBackgroundColor(context.getResources().getColor(R.color.normal_mode));// green
                break;
            case 1:
                mText.setBackgroundColor(context.getResources().getColor(R.color.important_mode)); //orange
                break;
            case 2:
                mText.setBackgroundColor(context.getResources().getColor(R.color.urgent_mode)); //red
                break;
        }

        TextView tText = (TextView) view.findViewById(R.id.alarmText);
        tText.setText(cursor.getString(cursor.getColumnIndex(MemoDBWrapper.DATE_TIME_COLUMN)));
    }
}




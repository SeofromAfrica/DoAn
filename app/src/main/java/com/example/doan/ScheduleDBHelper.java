package com.example.doan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public abstract class ScheduleDBHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "LapLichThoiKhoaBieu";
    public static final String TABLE_NAME = "tblSchedule";
    public static final String COLUMN_ID="id";
    public static final String COLUMN_TITLE="title";
    public static final String COLUMN_TIME="time";




    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS  " + TABLE_NAME + "("
                + COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " TEXT NOT NULL, "
                + COLUMN_TIME + " TEXT NOT NULL)";

    public List<Schedule> getAllSchedules() {
        SQLiteDatabase db = getReadableDatabase();

        List<Schedule> eventList = new ArrayList<>();

        Cursor cursor = db.query(
                TABLE_NAME,
                new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_TIME},
                null,
                null,
                null,
                null,
                null);

        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
            String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
            String time = cursor.getString(cursor.getColumnIndex(COLUMN_TIME));

            Schedule schedule = new Schedule(id, title, time);
            eventList.add(schedule);
        }

        cursor.close();
        db.close();

        return eventList;
    }

    public long addSchedule(Schedule schedule) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, schedule.getTitle());
        values.put(COLUMN_TIME, schedule.getTime());

        long id = db.insert(TABLE_NAME, null, values);

        db.close();

        return id;
    }

    public long updateSchedule(Schedule schedule) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, schedule.getTitle());
        values.put(COLUMN_TIME, schedule.getTime());

        int id = db.update(
                TABLE_NAME,
                values,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(schedule.getId())});

        db.close();

        return id;
    }
    public long deleteSchedule(long scheduleId) {

        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(scheduleId)});
        db.close();
        return scheduleId;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    public ScheduleDBHelper(Context context){
        super(context,DATABASE_NAME,null,1);
    }
}

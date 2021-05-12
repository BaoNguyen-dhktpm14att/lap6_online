package com.example.customlistview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Traval";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "students";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_PHONE_NUMBER = "phone_number";
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_students_table = String.format
                ("CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT)",
                        TABLE_NAME, KEY_ID, KEY_NAME);
        db.execSQL(create_students_table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop_students_table = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        db.execSQL(drop_students_table);

        onCreate(db);
    }
    public void addTraval(Traval Traval) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, Traval.getName());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public void deleteTravel(List<Traval> travels, int position) {
        String name = travels.get(position).getName();
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_NAME + " = ?", new String[] { name });
        db.close();
    }
    public void updateTravel(String name1, List<Traval> travels, int position) {
        String name = travels.get(position).getName();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name1);
        db.update(TABLE_NAME, values, KEY_NAME + " = ?", new String[] { name });
        db.close();
    }
    public List<Traval> getAllTravalList() {
        List<Traval> travaltList = new ArrayList<>();
        String sql = "select * from student";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                Traval traval = new Traval();
                traval.setId(cursor.getInt(0));
                traval.setName(cursor.getString(1));


                travaltList.add(traval);


            }while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return travaltList;
    }
}

package com.example.databaseexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE course(_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, description TEXT)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "DROP TABLE IF EXISTS course";
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);
    }

    public long addValue(String tilteInput, String descriptionInput){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", tilteInput);
        contentValues.put("description", descriptionInput);
        return sqLiteDatabase.insert("course", null, contentValues);
    }

    public Cursor display(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor =  sqLiteDatabase.rawQuery("SELECT * FROM course", null);
        return  cursor;
    }

    public Cursor search_display(String titleInput){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor =  sqLiteDatabase.rawQuery("SELECT * FROM course WHERE title='" + titleInput + "'", null);
        return  cursor;
    }

    public long updateValue(String titleInput, String descriptionInput){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("description", descriptionInput);
        return sqLiteDatabase.update("course",contentValues,"title=?", new String[]{titleInput});
    }

    public Cursor deleteValue(String titleInput){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM course WHERE title=?", new String[]{titleInput});
        if(cursor.getCount()>0){
            sqLiteDatabase.delete("course","title=?", new String[]{titleInput});
        }
        return cursor;
    }
}

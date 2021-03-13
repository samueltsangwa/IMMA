package com.example.imma;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Name
    private static final String DATABASE_NAME = "magazine";
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // User table name
    private static final String TABLE_NAME = "user";

    // User Table Columns names
    public static class MagCols {
        public static final String COL_FIRST_NAME = "first_name";
        public static final String COL_LAST_NAME = "last_name";
        public static final String COL_EMAIL_ADDRESS = "email";
        public static final String COL_USER_NAME = "username";
        public static final String COL_PASSWORD = "password";
    }

    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            MagCols.COL_FIRST_NAME + " TEXT," +
            MagCols.COL_LAST_NAME + " TEXT," +
            MagCols.COL_EMAIL_ADDRESS + " TEXT," +
            MagCols.COL_USER_NAME + " TEXT," +
            MagCols.COL_PASSWORD + " TEXT" +
            ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("database operation", "Database created successfully");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
        Log.d("database operation", "Table created successfully");
    }

    // drop table sql query
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS user");
        onCreate(sqLiteDatabase);
    }
    // Assign values for each row.
    public boolean RegisterUser(String first_name, String last_name, String email, String username, String password) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MagCols.COL_FIRST_NAME, first_name);
        values.put(MagCols.COL_LAST_NAME, last_name);
        values.put(MagCols.COL_EMAIL_ADDRESS, email);
        values.put(MagCols.COL_USER_NAME, username);
        values.put(MagCols.COL_PASSWORD, password);
        return sqLiteDatabase.insert(TABLE_NAME, null, values) != -1;
    }

    public Cursor readData() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String[] columns = {MagCols.COL_FIRST_NAME, MagCols.COL_LAST_NAME, MagCols.COL_EMAIL_ADDRESS, MagCols.COL_USER_NAME, MagCols.COL_PASSWORD};
        return sqLiteDatabase.query(false, TABLE_NAME, columns, null, null,
                null, null, null, null);
    }
    public boolean isEmailExists(String email) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String[] cols = new String[]{MagCols.COL_EMAIL_ADDRESS};
        String[] selArgs = {email};
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, cols, MagCols.COL_EMAIL_ADDRESS + "=?", selArgs, null, null, null);
        if (cursor.moveToFirst()) {
            int count = 0;
            do {
                count++;
            } while (cursor.moveToNext());
            return count >= 1;
        }
        cursor.close();
        return false;
    }
    public Cursor loginCheck(String username, String password) {
        SQLiteDatabase database = getReadableDatabase();
        return database.query(TABLE_NAME,
                new String[]{MagCols.COL_USER_NAME, MagCols.COL_PASSWORD},
                MagCols.COL_USER_NAME + "=? AND " + MagCols.COL_PASSWORD + "=?",
                new String[]{username, password},
                null, null, null);
    }
    public  Cursor getUsername(String username, SQLiteDatabase sqLiteDatabase){
        String[]projections={MagCols.COL_USER_NAME,MagCols.COL_PASSWORD};
        String selection=MagCols.COL_EMAIL_ADDRESS+" LIKE ?";
        String selection_args[]={username};
        Cursor cursor=sqLiteDatabase.query(TABLE_NAME,projections,selection,selection_args,null,null,null);
        return  cursor;
    }
    public int updatePassword(String old_password, String new_password, SQLiteDatabase database) {

        ContentValues values = new ContentValues();
        values.put(MagCols.COL_PASSWORD, new_password);
        String Selection=MagCols.COL_EMAIL_ADDRESS + " LIKE ?";
        String [] selection_arg={old_password};
      int count=  database.update(TABLE_NAME, values,Selection,selection_arg);
        return count;
    }
    public  Cursor getEmail(String username, SQLiteDatabase sqLiteDatabase){
        String[]projections={MagCols.COL_EMAIL_ADDRESS};
        String selection=MagCols.COL_EMAIL_ADDRESS+" LIKE ?";
        String selection_args[]={username};
        Cursor cursor=sqLiteDatabase.query(TABLE_NAME,projections,selection,selection_args,null,null,null);
        return  cursor;
    }
}

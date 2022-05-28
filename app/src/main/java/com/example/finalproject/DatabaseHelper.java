package com.example.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by Mitch on 2016-05-13.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "students.db";
    public static final String TABLE_NAME = "students_data";
    public static final String COL1 = "ID";
    public static final String COL2 = "NAME";
    public static final String COL3 = "FNAME";
    public static final String COL4 = "SURNAME";
    public static final String COL5 = "NATIONALID";
    public static final String COL6 = "DOB";
    public static final String COL7 = "GENDER";

    /* Constructor */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    /* Code runs automatically when the dB is created */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME +
                " (ID TEXT PRIMARY KEY, " +
                " NAME TEXT, FNAME TEXT, SURNAME TEXT, NATIONALID TEXT, DOB TEXT, GENDER TEXT)";
        db.execSQL(createTable);
    }

    /* Every time the dB is updated (or upgraded) */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /* Basic function to add data. REMEMBER: The fields
       here, must be in accordance with those in
       the onCreate method above.
    */
    public boolean addData(String stID, String stName, String stFather, String stSurname, String stNationalID, String stDOB, String stGender) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, stID);
        contentValues.put(COL2, stName);
        contentValues.put(COL3, stFather);
        contentValues.put(COL4, stSurname);
        contentValues.put(COL5, stNationalID);
        contentValues.put(COL6, stDOB);
        contentValues.put(COL7, stGender);


        long result = db.insert(TABLE_NAME, null, contentValues);

        //if data are inserted incorrectly, it will return -1
        if(result == -1) {return false;} else {return true;}
    }

    /* Returns only one result */
    public Cursor structuredQuery(String ID) {
        SQLiteDatabase db = this.getReadableDatabase(); // No need to write
        Cursor cursor = db.query(TABLE_NAME, new String[]{COL1,
                        COL2, COL3, COL4, COL5, COL6, COL7}, COL1 + "=?",
                new String[]{ID}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        return cursor;
    }

    public boolean updateData(String stID, String stName, String stFather, String stSurname, String stNationalID, String stDOB, String stGender)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, stID);
        contentValues.put(COL2, stName);
        contentValues.put(COL3, stFather);
        contentValues.put(COL4, stSurname);
        contentValues.put(COL5, stNationalID);
        contentValues.put(COL6, stDOB);
        contentValues.put(COL7, stGender);

        int result = db.update(TABLE_NAME, contentValues, "ID=?", new String[]{stID});
        if (result == 1)
            return true;
        else
            return false;
    }


    public Cursor getSpecificProduct(String ID){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME+" where ID=\""+ID+"\"",null);
        return data;
    }

    // Return everything inside the dB
    public Cursor getListContents() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }

    // delete a specific row by id
    public boolean deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COL1 + "=" + id, null) > 0;
    }
}

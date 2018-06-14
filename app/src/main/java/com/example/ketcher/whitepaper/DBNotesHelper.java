package com.example.ketcher.whitepaper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBNotesHelper extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "Notes";
    // Contacts table name
    private static final String TABLE_Notes = "Notes";
    // Contacts Table Columns names
    private static final String DATE = "date";
    private static final String NOTE_TEXT = "note_text";


    public DBNotesHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_Notes + "("
                + NOTE_TEXT + " TEXT," + DATE + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Notes);
        // Create tables again
        onCreate(db);
    }

    // Adding new contact
    public void addNote(MyNote myNote) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DATE, myNote.getDate()); // Contact Name
        values.put(NOTE_TEXT, myNote.getNoteText()); // Contact Phone
        // Inserting Row
        db.insert(TABLE_Notes, null, values);

    }

    // Getting All Contacts

    public ArrayList<MyNote> getAllNotes() {
        ArrayList<MyNote> myNotesList = new ArrayList<>();
        // Select All Query
        SQLiteDatabase db = this.getWritableDatabase();
        String selectDateQuery = "SELECT date FROM " + TABLE_Notes;
        Cursor cursorDate = db.rawQuery(selectDateQuery, null);
        String selectNoteTextQuery = "SELECT note_text FROM " + TABLE_Notes;
        Cursor cursorNoteText = db.rawQuery(selectNoteTextQuery, null);

        // looping through all rows and adding to list
        if (cursorDate.moveToFirst() && cursorNoteText.moveToFirst()) {
            do {
                MyNote myNote = new MyNote(null, null);
                myNote.setNoteText(cursorNoteText.getString(0));
                myNote.setDate(cursorDate.getString(0));
                // Adding contact to list
                myNotesList.add(0, myNote);
            } while (cursorDate.moveToNext() && cursorNoteText.moveToNext());
        }
        // return contact list
        cursorDate.close();
        cursorNoteText.close();
        return myNotesList;
    }

    // Deleting single contact
    public void removeNote(String date, String noteText) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = "date=? AND note_text=?";
        String[] whereArgs = new String[] { date, noteText};
        db.delete(TABLE_Notes, whereClause, whereArgs);
    }

    public void Clear(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Notes, null, null);
    }
}

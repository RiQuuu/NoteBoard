package com.example.talvinen.noteboard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NoteRepo {
    private DBHelper dbHelper;

    public NoteRepo(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(Note note) {

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Note.KEY_text, note.text);
        values.put(Note.KEY_text2, note.text2);
        values.put(Note.KEY_text3, note.text3);
        values.put(Note.KEY_text4, note.text4);

        // Inserting Row
        long note_Id = db.insert(Note.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) note_Id;
    }

    public void delete(int note_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Note.TABLE, Note.KEY_ID + "= ?", new String[] { String.valueOf(note_Id) });
        db.close(); // Closing database connection
    }

    public void update(Note note) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Note.KEY_text, note.text);
        values.put(Note.KEY_text2, note.text2);
        values.put(Note.KEY_text3, note.text3);
        values.put(Note.KEY_text4, note.text4);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Note.TABLE, values, Note.KEY_ID + "= ?", new String[] { String.valueOf(note.note_ID) });
        db.close(); // Closing database connection
    }

    /*public ArrayList<HashMap<String, String>>  getStudentList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Student.KEY_ID + "," +
                Student.KEY_name + "," +
                Student.KEY_email + "," +
                Student.KEY_age +
                " FROM " + Student.TABLE;

        //Student student = new Student();
        ArrayList<HashMap<String, String>> studentList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> student = new HashMap<String, String>();
                student.put("id", cursor.getString(cursor.getColumnIndex(Student.KEY_ID)));
                student.put("name", cursor.getString(cursor.getColumnIndex(Student.KEY_name)));
                studentList.add(student);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return studentList;

    }*/

    public Note getNoteById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Note.KEY_ID + "," +
                Note.KEY_text + "," +
                Note.KEY_text2 + "," +
                Note.KEY_text3 + "," +
                Note.KEY_text4 +
                " FROM " + Note.TABLE
                + " WHERE " +
                Note.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        //int iCount =0;
        Note note = new Note();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                note.note_ID =cursor.getInt(cursor.getColumnIndex(Note.KEY_ID));
                note.text =cursor.getString(cursor.getColumnIndex(Note.KEY_text));
                note.text2 =cursor.getString(cursor.getColumnIndex(Note.KEY_text2));
                note.text3 =cursor.getString(cursor.getColumnIndex(Note.KEY_text3));
                note.text4 =cursor.getString(cursor.getColumnIndex(Note.KEY_text4));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return note;
    }

}

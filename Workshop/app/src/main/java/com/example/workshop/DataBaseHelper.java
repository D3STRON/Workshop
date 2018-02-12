package com.example.workshop;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Anurag on 12-02-2018.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String user_table="USER_INFO";
    private static final String note_table="NOTE_LIST";
    private static final String db="NOTE";


    public DataBaseHelper(Context context) {
        super(context,db,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_USER_TABLE="CREATE TABLE "+user_table+" ( NAME TEXT, EMAIL TEXT);";
        String CREATE_TODO_TABLE="CREATE TABLE "+note_table+" ( ID TEXT , NOTE TEXT);";
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+user_table);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+note_table);
        onCreate(sqLiteDatabase);
    }

    public void AddNote(int id, String note)
    {
        String Id= Integer.toString(id);
        SQLiteDatabase db =this.getWritableDatabase();
        String INSERT_NOTE="INSERT INTO "+note_table+" VALUES ( '"+Id+"','"+note+"' );";
        db.execSQL(INSERT_NOTE);
    }

    public void AddUser(String name, String email)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        String INSERT_USER="INSERT INTO "+user_table+" VALUES ( '"+name+"','"+email+"' );";
        db.execSQL(INSERT_USER);
    }

    public HashMap<String, String> getUserInfo()
    {
        HashMap<String, String > values= new HashMap<>();
        SQLiteDatabase db= this.getReadableDatabase();
        String query= "SELECT * FROM "+user_table+";";
        Cursor cr= db.rawQuery(query,null);
        cr.moveToFirst();
        values.put("name",cr.getString(0));
        values.put("email",cr.getString(1));
        cr.close();
        return values;
    }

    public int getNoteCount()
    {
        SQLiteDatabase db= this.getReadableDatabase();
        String query= "SELECT * FROM "+note_table+";";
        Cursor cr= db.rawQuery(query,null);
        return cr.getCount();
    }

    public ArrayList<String> getAll(){

        SQLiteDatabase db = this.getReadableDatabase();
//        ContentValues cv = new ContentValues();
        String query = "SELECT * FROM "+note_table+";";
        Cursor cr= db.rawQuery(query,null);
        ArrayList<String> notes= new ArrayList<>();
        cr.moveToFirst();
       if(cr.getCount()>0) {
           notes.add(cr.getString(1));

           while (cr.moveToNext()) {
               notes.add(cr.getString(1));

           }
       }
        return notes;
    }

    public void UpDateNote(String oldNote, String newNote)
    {
       SQLiteDatabase database= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("NOTE",newNote);

        database.update(note_table,contentValues,"NOTE=?",new String[] {oldNote});
    }

    public void deleteNote( String Note)
    {
         SQLiteDatabase database= this.getWritableDatabase();
         database.delete(note_table, "NOTE=?",new String[] {Note});
    }
}

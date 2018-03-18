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

//public class DataBaseHelper extends SQLiteOpenHelper{
//
//   private static final String DB= "NOTES";
//   private static final String user_info="USER_INFO";
//   private static final String notes_list="NOTES_LIST";
//
//
//    public DataBaseHelper(Context context) {
//        super(context, DB, null, 1);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        String CREATE_USER_INFO="CREATE TABLE "+user_info+" ( NAME TEXT, EMAIL TEXT);";
//        String CREATE_NOTES_LIST="CREATE TABLE "+notes_list+" ( NAME NOTE);";
//        sqLiteDatabase.execSQL(CREATE_USER_INFO);
//        sqLiteDatabase.execSQL(CREATE_NOTES_LIST);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//          sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+user_info);
//          sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+notes_list);
//          onCreate(sqLiteDatabase);
//    }
//
//    public void AddUser(String name, String email){
//        String Add_user= " INSERT INTO "+user_info+" VALUES ( '"+name+"','"+email+"');";
//        SQLiteDatabase db= this.getWritableDatabase();
//        db.execSQL(Add_user);
//    }
//
//    public Cursor getUserInfo(){
//     HashMap<String, String > user= new HashMap<>();
//      String query= "SELECT * FROM "+user_info;
//      SQLiteDatabase db = this.getReadableDatabase();
//      Cursor cr = db.rawQuery(query,null);
//      return cr;
//    }
//}

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
        String CREATE_TODO_TABLE="CREATE TABLE "+note_table+" ( NOTE TEXT);";
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+user_table);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+note_table);
        onCreate(sqLiteDatabase);
    }

    public void AddNote( String note)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        String INSERT_NOTE="INSERT INTO "+note_table+" VALUES ( '"+note+"' );";
        db.execSQL(INSERT_NOTE);
    }

    public void AddUser(String name, String email)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        String INSERT_USER="INSERT INTO "+user_table+" VALUES ( '"+name+"','"+email+"' );";
        db.execSQL(INSERT_USER);
    }

    public Cursor getUserInfo(){
      String query= "SELECT * FROM "+user_table;
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor cr = db.rawQuery(query,null);
      return cr;
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
           notes.add(cr.getString(0));

           while (cr.moveToNext()) {
               notes.add(cr.getString(0));
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

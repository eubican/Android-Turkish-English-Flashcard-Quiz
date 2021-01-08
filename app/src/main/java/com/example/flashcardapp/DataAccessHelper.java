package com.example.flashcardapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DataAccessHelper extends SQLiteOpenHelper {
    private static String dbName = "flashcards.db";
    private static int dbVersion = 1;
    private SQLiteDatabase db;

    public DataAccessHelper(Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        String create_table_query = "CREATE TABLE WORDS (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ENGLISH TEXT NOT NULL," +
                "TURKISH TEXT NOT NULL);";
        db.execSQL(create_table_query);
        fillDatabase();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS WORDS");
        onCreate(db);
    }

    private void fillDatabase(){
        Flashcard fc1 = new Flashcard("durum", "situation");
        addFlashcard(fc1);
        Flashcard fc2 = new Flashcard("keşfetmek", "explore");
        addFlashcard(fc2);
        Flashcard fc3 = new Flashcard("kaynak", "source");
        addFlashcard(fc3);
        Flashcard fc4 = new Flashcard("varsaymak", "assume");
        addFlashcard(fc4);
        Flashcard fc5 = new Flashcard("ölçmek", "measure");
        addFlashcard(fc5);
        Flashcard fc6 = new Flashcard("kişisel", "personal");
        addFlashcard(fc6);
        Flashcard fc7 = new Flashcard("çalışan", "personnel");
        addFlashcard(fc7);
        Flashcard fc8 = new Flashcard("ülke", "country");
        addFlashcard(fc8);
        Flashcard fc9 = new Flashcard("ilçe", "county");
        addFlashcard(fc9);
        Flashcard fc10 = new Flashcard("ücret", "fare");
        addFlashcard(fc10);
        Flashcard fc11 = new Flashcard("adil", "fair");
        addFlashcard(fc11);
        Flashcard fc12 = new Flashcard("genişlik", "width");
        addFlashcard(fc12);
        Flashcard fc13 = new Flashcard("yükseklik", "height");
        addFlashcard(fc13);
        Flashcard fc14 = new Flashcard("gece", "night");
        addFlashcard(fc14);
        Flashcard fc15 = new Flashcard("şövalye", "knight");
        addFlashcard(fc15);
    }

    private void addFlashcard(Flashcard fc){
        ContentValues cv = new ContentValues();
        cv.put(DataAccess.ENG_column, fc.getFlashcardEN());
        cv.put(DataAccess.TUR_column, fc.getFlashcardTR());
        db.insert(DataAccess.TABLE_NAME, null, cv);
    }

    public List<Flashcard> getAllWords(){
        List<Flashcard> wordList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM WORDS", null);

        if(mCursor.moveToFirst()){
            do{
                Flashcard tmp = new Flashcard();
                tmp.setFlashcardEN(mCursor.getString(mCursor.getColumnIndex(DataAccess.ENG_column)));
                tmp.setFlashcardTR(mCursor.getString(mCursor.getColumnIndex(DataAccess.TUR_column)));
                tmp.setFlashcardID(mCursor.getInt(mCursor.getColumnIndex(DataAccess.ID_column)));
                wordList.add(tmp);
            }while(mCursor.moveToNext());
        }

        mCursor.close();
        return wordList;
    }
}
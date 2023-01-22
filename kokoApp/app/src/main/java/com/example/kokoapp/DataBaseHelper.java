package com.example.kokoapp;

import static androidx.constraintlayout.widget.Constraints.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {

    private final ArrayList<String> datalist = new ArrayList<>();

    private static final String DATABASE_NAME = "koko.db";

    private static final int DATABASE_VERSION = 1;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public final static String TABLE_NAME = "words";

    // word id
    public final static String W_ID ="id";

    //eng word
    public final static String COLUMN_ENG = "eng";

    //eng word
    public final static String COLUMN_BG = "bg";

    //eng word
    public final static String COLUMN_CAT = "category";

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQl_CREATE_ITEMS_TABLE = "CREATE TABLE "
                    + TABLE_NAME + " ("
                    + W_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_ENG + " TEXT NOT NULL, "
                    + COLUMN_BG + " TEXT NOT NULL, "
                    + COLUMN_CAT + " TEXT NOT NULL);";

        db.execSQL(SQl_CREATE_ITEMS_TABLE);

    }

    public boolean checkIsEmpty (){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + "words", null);

        boolean isEmpty = cursor.getCount() == 0;
        cursor.close();
        return isEmpty;

    }

    public void printTableData(String table_name, ArrayList<String> eng, ArrayList<String> bg, ArrayList<String> cat){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name, null);
        int count = 0;

        if(cursor.getCount() != 0){
            cursor.moveToFirst();

            do{
                String row_values = "";

                for(int i = 0 ; i < cursor.getColumnCount(); i++){
                    if (count == 4) count = 0;
                    row_values = row_values + " || " + cursor.getString(i);
                    if(count == 1) eng.add(cursor.getString(i));
                    else if (count == 2)bg.add(cursor.getString(i));
                    else if (count == 3)cat.add(cursor.getString(i));
                    count ++;

                }

            }while (cursor.moveToNext());

            for (String word : eng) {
                Log.d("LOG_eng_HERE", " " + word);
            }
            for (String word : bg) {
                Log.d("LOG_bg_HERE", " " + word);
            }
            for (String word : cat) {
                Log.d("LOG_cat_HERE", " " + word);
            }
        }
        cursor.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);
    }

}

package com.android.marco.tellme.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.marco.tellme.adapters.LanguageModel;

import java.util.ArrayList;
import java.util.Collections;

public class DatabaseOpenHandler extends SQLiteOpenHelper {

    public static final String _ID = "_id";
    public static final String TESTED = "tested";
    public static final String WON = "won";
    public static final String DATE = "date";
    public final static String SQL_GET_ALL_TABLES = "SELECT name FROM " +
            "sqlite_master WHERE type='table' ORDER BY name";
    private static final String TAG = DatabaseOpenHandler.class.getSimpleName();
    private static final String DATABASE_NAME = "mydicts.db";
    private static final int DATABASE_VERSION = 1;
    private static String currentTableName;
    public int updateThisID;

//		private static final String TABLE_WORDS_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME_WORDS;	
//		public String CREATE_TABLE = "CREATE TABLE  IF NOT EXISTS " + TABLE_NAME // + "es_fr" 
//				+ " ( " + _ID
//				+ " INTEGER PRIMARY KEY AUTOINCREMENT, %s ) ";

    public DatabaseOpenHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreateSQL(): id=" + db + " -> " + "fgdfg");
//			db.execSQL(TABLE_WORDS_DROP);
//			db.execSQL("DROP TABLE IF EXISTS de_en");
//			db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrade der Datenbank von Version " + oldVersion + " zu "
                + newVersion + "; alle Daten werden gelöscht");
//			db.execSQL(TABLE_WORDS_DROP);
        onCreate(db);
    }

    public void deleteRow() {
        SQLiteDatabase db = getWritableDatabase();
        int numDeleted = db.delete(currentTableName, _ID + " = ?",
                new String[]{Integer.toString(updateThisID)});
        Log.d(TAG, "delete(): id=" + updateThisID + " -> " + numDeleted);


    }

    public void dontAskAgain() {
        SQLiteDatabase db = getWritableDatabase();
        String s = "UPDATE " + currentTableName +
                " SET " + WON + " = NULL " +
                "  WHERE " + _ID + "=" + updateThisID;

        Log.d(TAG, " dontAskAgain (): id=" + updateThisID + " -> " + s);
        db.execSQL(s);
    }

    public String createTableName(ArrayList<LanguageModel> l) {
        ArrayList<String> tableTokkens = new ArrayList<String>();
        tableTokkens.add(l.get(0).getLanguageCode());
        tableTokkens.add(l.get(1).getLanguageCode());
        Collections.sort(tableTokkens);
        return tableTokkens.get(0) + "_" + tableTokkens.get(1);
    }

    public void createTableAndOrInsert(ArrayList<LanguageModel> l, String mother_lang, String foreing_lang, int date) {
        long rowId = -1;
        String C_T = "CREATE TABLE IF NOT EXISTS " + createTableName(l)
                + " ( " + _ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + l.get(0).getLanguageCode()
                + " text, " + l.get(1).getLanguageCode() + " text, " + "tested int, " + "won int, " + "date int " + " ) ";

        try {
            SQLiteDatabase db = getWritableDatabase();
            Log.d(TAG + "DDDBBB", C_T);
            db.execSQL(C_T);
            ContentValues values = new ContentValues();
            values.put(l.get(0).getLanguageCode(), mother_lang);
            values.put(l.get(1).getLanguageCode(), foreing_lang);
            values.put(DATE, date);
            values.put(WON, 0);
            values.put(TESTED, 0);


            rowId = db.insert(createTableName(l), null, values);
        } catch (SQLiteException e) {
            Log.e(TAG, "insert()", e);
        } finally {
            Log.d(TAG, "insert(): rowId=" + rowId);
        }
    }

    public boolean getTableNames(String tableName) {  // TODO obsolite and probably broken    )) its main man
        SQLiteDatabase db = getWritableDatabase();
        String fql = "SELECT name FROM sqlite_master WHERE type = 'table'";
//			return db.rawQuery("SELECT * FROM items ORDER BY id ASC LIMIT 4", null);
//			Cursor c = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = 'table' AND name = ? ", 
//					new String[] {tableName});
        boolean result = false;
        Cursor c = db.rawQuery(fql, null);
        while (c.moveToNext()) {  //				Log.d("DDBB", c.getString(0));  result == false or c.moveTo
            if (c.getString(0).equals(tableName)) {
                result = true;
            }
        }
        return result;
    }

    public boolean isTableExists(String tableName) {
        SQLiteDatabase db = getWritableDatabase();
        if (tableName == null || db == null)  //  || !db.isOpen()
        {
            return false;
        }
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ? ;",
                new String[]{"table", tableName});
        if (!cursor.moveToFirst()) {
            cursor.close();
            return false;
        }
        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }


    public String[][] getQuizVolume(ArrayList<LanguageModel> l) {
        SQLiteDatabase db = getWritableDatabase();
        currentTableName = createTableName(l);

//			Cursor cursor = db.query(currentTableName, null, null, null, null, null, WON + " ASC");		
        Cursor cursor = db.rawQuery("SELECT * FROM " + currentTableName + " WHERE won IS NOT NULL ORDER BY won ASC ;",
                null); // WON + " ASC");
        int cursorLength = cursor.getCount();
//			int[] rands = myThreeRandoms(cursorLength);

        if (cursor.getCount() > 3 && cursor != null) {
            String[][] str = new String[cursor.getCount()][2];
            int i = 0;

            while (cursor.moveToNext()) {   // && i < 4
                if (i == 0) {
                    updateThisID = cursor.getInt(cursor.getColumnIndex(_ID));
                }
                str[i][0] = cursor.getString(cursor.getColumnIndex(l.get(1).getLanguageCode()));
                str[i][1] = cursor.getString(cursor.getColumnIndex(l.get(0).getLanguageCode()));
                i++;
            }
            return str;
        } else {
            return new String[][]{};
        }
    }

    public void updateQuestedRow(int score) {
        SQLiteDatabase db = getWritableDatabase();
        String s = "UPDATE " + currentTableName +
                " SET " + TESTED + "=" + TESTED + " + 1," +
                WON + "=" + WON + " + " + score +
                "  WHERE " + _ID + "=" + updateThisID;
        db.execSQL(s);
    }

//		public void delete(long id) {			
//			SQLiteDatabase db = getWritableDatabase();
//			int numDeleted = db.delete(TABLE_NAME_WORDS, _ID + " = ?",
//					new String[] { Long.toString(id) });
//			Log.d(TAG, "delete(): id=" + id + " -> " + numDeleted);
//		}
}
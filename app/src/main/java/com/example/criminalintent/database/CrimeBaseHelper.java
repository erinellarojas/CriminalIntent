package com.example.criminalintent.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.criminalintent.database.CrimeDbSchema.CrimeTable;

import java.util.ArrayList;
import java.util.List;

public class CrimeBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 4;
    private static final String DATABASE_NAME = "crimeBase.db";

    public CrimeBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + CrimeTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                CrimeTable.Cols.UUID + ", " +
                CrimeTable.Cols.TITLE + ", " +
                CrimeTable.Cols.DATE + ", " +
                CrimeTable.Cols.SOLVED + ", " +
                CrimeTable.Cols.SUSPECT + ", " +
                CrimeTable.Cols.SUSPECT_PHONE + ", " +
                CrimeTable.Cols.REQUIRES_POLICE +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Use a robust migration strategy that checks for column existence.
        // This fixes previous migration bugs where columns like 'suspect' might have been skipped.
        List<String> columns = getColumnNames(db, CrimeTable.NAME);
        
        if (!columns.contains(CrimeTable.Cols.SUSPECT)) {
            db.execSQL("alter table " + CrimeTable.NAME + " add column " + CrimeTable.Cols.SUSPECT);
        }
        if (!columns.contains(CrimeTable.Cols.SUSPECT_PHONE)) {
            db.execSQL("alter table " + CrimeTable.NAME + " add column " + CrimeTable.Cols.SUSPECT_PHONE);
        }
        if (!columns.contains(CrimeTable.Cols.REQUIRES_POLICE)) {
            db.execSQL("alter table " + CrimeTable.NAME + " add column " + CrimeTable.Cols.REQUIRES_POLICE);
        }
    }

    private List<String> getColumnNames(SQLiteDatabase db, String tableName) {
        List<String> names = new ArrayList<>();
        Cursor cursor = db.rawQuery("PRAGMA table_info(" + tableName + ")", null);
        try {
            int nameIndex = cursor.getColumnIndexOrThrow("name");
            while (cursor.moveToNext()) {
                names.add(cursor.getString(nameIndex));
            }
        } finally {
            cursor.close();
        }
        return names;
    }
}

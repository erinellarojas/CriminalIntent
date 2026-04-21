package com.example.criminalintent.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.criminalintent.Crime;
import com.example.criminalintent.database.CrimeDbSchema.CrimeTable;

import java.util.Date;
import java.util.UUID;

public class CrimeCursorWrapper extends CursorWrapper {
    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Crime getCrime() {
        String uuidString = getString(getColumnIndexOrThrow(CrimeTable.Cols.UUID));
        String title = getString(getColumnIndexOrThrow(CrimeTable.Cols.TITLE));
        long date = getLong(getColumnIndexOrThrow(CrimeTable.Cols.DATE));
        int isSolved = getInt(getColumnIndexOrThrow(CrimeTable.Cols.SOLVED));
        String suspect = getString(getColumnIndexOrThrow(CrimeTable.Cols.SUSPECT));
        String suspectPhone = getString(getColumnIndexOrThrow(CrimeTable.Cols.SUSPECT_PHONE));
        int requiresPolice = getInt(getColumnIndexOrThrow(CrimeTable.Cols.REQUIRES_POLICE));

        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setDate(new Date(date));
        crime.setSolved(isSolved != 0);
        crime.setSuspect(suspect);
        crime.setSuspectPhone(suspectPhone);
        crime.setRequiresPolice(requiresPolice != 0);

        return crime;
    }
}

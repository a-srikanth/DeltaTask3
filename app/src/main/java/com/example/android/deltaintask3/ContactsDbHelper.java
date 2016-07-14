package com.example.android.deltaintask3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Srikanth on 7/9/2016.
 */
public class ContactsDbHelper extends SQLiteOpenHelper {

    //Stores database version, which is to be incremented
    //whenever there is a schema change in any table
    private static final int DATABASE_VERSION = 5;

    static final String DATABASE_NAME = "contacts.db";

    public ContactsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_CONTACTS_TABLE = "CREATE TABLE "+ ContactsDbUtilities.TABLE_NAME + " ("+
                ContactsDbUtilities._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                ContactsDbUtilities.COLUMN_NAME + " TEXT NOT NULL, "+
                ContactsDbUtilities.COLUMN_PHONE_NUMBER + " TEXT NOT NULL, "+
                ContactsDbUtilities.COLUMN_EMAIL + " TEXT NOT NULL, "+
                ContactsDbUtilities.COLUMN_PICTURE_URI + " TEXT);";

        sqLiteDatabase.execSQL(SQL_CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int initial_version, int final_version) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ContactsDbUtilities.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}

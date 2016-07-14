package com.example.android.deltaintask3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.widget.Toast;

/**
 * Created by Srikanth on 7/9/2016.
 */
public class ContactsDbUtilities implements BaseColumns{

    public static final String TABLE_NAME = "contacts_table";

    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PHONE_NUMBER = "phone_number";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PICTURE_URI = "picture_uri";


    public long insert(Context context, String tableName, ContentValues values){
        ContactsDbHelper mOpenHelper = new ContactsDbHelper(context);
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        long insertedRowId = db.insert(tableName, null, values);

        if(insertedRowId==-1){
            Toast.makeText(context, "Failed to add contact", Toast.LENGTH_SHORT).show();
        }
        db.close();
        return insertedRowId;
    }

    public Cursor query(Context context, String tableName, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder){

        Cursor retCursor;
        SQLiteDatabase db = new ContactsDbHelper(context).getReadableDatabase();
        retCursor = db.query(
                tableName,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
        return retCursor;
    }


}

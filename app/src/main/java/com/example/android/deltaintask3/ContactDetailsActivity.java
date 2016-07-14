package com.example.android.deltaintask3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class ContactDetailsActivity extends AppCompatActivity {

    String name="", number="", email="";
    String rowId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        rowId = ""+intent.getIntExtra("row",0);
        Cursor c = new ContactsDbUtilities().query(this, ContactsDbUtilities.TABLE_NAME, new String[]{
                ContactsDbUtilities.COLUMN_PHONE_NUMBER,ContactsDbUtilities.COLUMN_EMAIL}, ContactsDbUtilities._ID+"=?",
                new String[]{""+rowId}, null);

        if(c.moveToFirst()){
            number = c.getString(c.getColumnIndex(ContactsDbUtilities.COLUMN_PHONE_NUMBER));
            email = c.getString(c.getColumnIndex(ContactsDbUtilities.COLUMN_EMAIL));
        }

        TextView t = (TextView) findViewById(R.id.contact_details_name);
        t.setText(name);

        t= (TextView) findViewById(R.id.contact_details_number);
        t.setText(number);

        t = (TextView) findViewById(R.id.contact_details_email);
        t.setText(email);
    }

    public void edit(View view){
        Intent intent = new Intent(this, EditContactActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("number", number);
        intent.putExtra("email", email);
        intent.putExtra("row", rowId);
        startActivity(intent);
    }

    public void delete(View view){
        SQLiteDatabase db = new ContactsDbHelper(this).getWritableDatabase();
        db.execSQL("DELETE FROM "+ContactsDbUtilities.TABLE_NAME+" WHERE "+ContactsDbUtilities._ID+"="+rowId);
        db.close();
        startActivity(new Intent(this, MainActivity.class));
    }


}

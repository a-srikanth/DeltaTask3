package com.example.android.deltaintask3;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class EditContactActivity extends AppCompatActivity {

    String oldName;
    String rowId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        Intent intent = getIntent();
        EditText editText = (EditText) findViewById(R.id.name_edit);

        oldName = intent.getStringExtra("name");
        editText.setText(oldName);

        editText = (EditText) findViewById(R.id.phone_edit);
        editText.setText(intent.getStringExtra("number"));

        editText = (EditText) findViewById(R.id.email_edit);
        editText.setText(intent.getStringExtra("email"));

        rowId = ""+intent.getIntExtra("row",0);

    }
    public void update(View view){
        EditText editText = (EditText) findViewById(R.id.name_edit);
        String newName = editText.getText().toString();
        Log.i("This is the new name: ", newName);

        editText = (EditText) findViewById(R.id.phone_edit);
        String newNumber = editText.getText().toString();

        editText = (EditText) findViewById(R.id.email_edit);
        String newEmail = editText.getText().toString();

        ContentValues newValues = new ContentValues();
        newValues.put(ContactsDbUtilities.COLUMN_NAME, newName);
        newValues.put(ContactsDbUtilities.COLUMN_PHONE_NUMBER, newNumber);
        newValues.put(ContactsDbUtilities.COLUMN_EMAIL, newEmail);

        SQLiteDatabase db = new ContactsDbHelper(this).getWritableDatabase();

        String Sql = "UPDATE "+ContactsDbUtilities.TABLE_NAME+" SET name='"+newName+"', phone_number="+newNumber+", email='"+newEmail+
                "' WHERE _ID="+rowId;
        Log.i("edit: ",Sql);
        db.execSQL(Sql);
           startActivity(new Intent(this, MainActivity.class));
    }
}

package com.example.android.deltaintask3;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<String> mContactAdapter;
    SearchView searchView;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(" oncreate", "called");
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        searchView = (SearchView) findViewById(R.id.contact_searchview);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        String sortOrder = ContactsDbUtilities.COLUMN_NAME + " ASC";
        final Cursor cursor = new ContactsDbUtilities().query(this,
                ContactsDbUtilities.TABLE_NAME,
                new String[]{ContactsDbUtilities.COLUMN_NAME, ContactsDbUtilities.COLUMN_PICTURE_URI},
                null,
                null,
                sortOrder
        );

        mContactAdapter = new ArrayAdapter<String>(this,R.layout.list_item, R.id.contact_name_textview, new ArrayList<String>());
        listView = (ListView) findViewById(R.id.contacts_listview);
        listView.setAdapter(mContactAdapter);
        if(mContactAdapter!=null)
            mContactAdapter.clear();

        if(cursor.moveToFirst()){
            do{
                String name = cursor.getString(cursor.getColumnIndex(ContactsDbUtilities.COLUMN_NAME));
                mContactAdapter.add(name);
            }while(cursor.moveToNext());
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String clickedName = mContactAdapter.getItem(position);
                Cursor idCursor = new ContactsDbUtilities().query(getApplicationContext(), ContactsDbUtilities.TABLE_NAME,
                        new String[]{ContactsDbUtilities._ID}, ContactsDbUtilities.COLUMN_NAME+"=?",new String[]{clickedName},null);
                int i=0;
                if(idCursor.moveToFirst()){
                    i = idCursor.getInt(idCursor.getColumnIndex(ContactsDbUtilities._ID));
                }
                Intent intent = new Intent(getApplicationContext(), ContactDetailsActivity.class);
                intent.putExtra("name", clickedName);
                intent.putExtra("row", i);
                startActivity(intent);
            }
        });

        FloatingActionButton myFab = (FloatingActionButton)findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addContact();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id==R.id.action_search){

            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addContact(){
        Intent intent = new Intent(this, AddContactActivity.class);
        Log.i("Main Activity", "addContact: intent created");
        startActivity(intent);
    }
}

package com.example.android.deltaintask3;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private ArrayAdapter<String> mContactAdapter;
    SearchView searchView;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchView = (SearchView) findViewById(R.id.contact_searchview);
        mContactAdapter = new ArrayAdapter<String>(this,R.layout.list_item, R.id.contact_name_textview, new ArrayList<String>());
        listView = (ListView) findViewById(R.id.contacts_listview);
        listView.setAdapter(mContactAdapter);
        if(mContactAdapter!=null)
            mContactAdapter.clear();


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

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String newQueryText = s+'%';
                String sortOrder = ContactsDbUtilities.COLUMN_NAME + " ASC";
                final Cursor cursor = new ContactsDbUtilities().query(getBaseContext(),
                        ContactsDbUtilities.TABLE_NAME,
                        new String[]{ContactsDbUtilities.COLUMN_NAME},
                        "lower("+ContactsDbUtilities.COLUMN_NAME+") LIKE ? ",
                        new String[]{newQueryText},
                        sortOrder
                );

                if(mContactAdapter!=null)
                    mContactAdapter.clear();

                if(cursor.moveToFirst()){
                    do{
                        String name = cursor.getString(cursor.getColumnIndex(ContactsDbUtilities.COLUMN_NAME));
                        mContactAdapter.add(name);
                    }while(cursor.moveToNext());
                }

                return false;
            }
        });
    }
}

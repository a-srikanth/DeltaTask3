package com.example.android.deltaintask3;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddContactActivity extends AppCompatActivity {

    EditText name,phone,email;
    ImageView imageView;
    Uri uploadedImageUri=null;
    private static final int SELECT_PICTURE = 100;
    private static final String TAG = "AddContactActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        imageView = (ImageView) findViewById(R.id.new_contact_image);
    }

    public void insert(View view){
        name = (EditText) findViewById(R.id.name_edittext);
        phone = (EditText) findViewById(R.id.phone_edittext);
        email = (EditText) findViewById(R.id.email_edittext);

        if(name.length()>0&&phone.length()>0) {

            ContentValues values = new ContentValues();
            values.put(ContactsDbUtilities.COLUMN_NAME, name.getText().toString());
            values.put(ContactsDbUtilities.COLUMN_PHONE_NUMBER, phone.getText().toString());
            values.put(ContactsDbUtilities.COLUMN_EMAIL, email.getText().toString());
            if(uploadedImageUri!= null)
                values.put(ContactsDbUtilities.COLUMN_PICTURE_URI, uploadedImageUri.toString());

            new ContactsDbUtilities().insert(this, ContactsDbUtilities.TABLE_NAME, values);

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(this, "Enter both name and number", Toast.LENGTH_SHORT);
        }
    }

    /* Choose an image from Gallery */
    void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                // Get the url from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Get the path from the Uri
                    String path = getPathFromURI(selectedImageUri);
                    Log.i(TAG, "Image Path : " + path);
                    uploadedImageUri = selectedImageUri;
                    // Set the image in ImageView
                    imageView.setImageURI(selectedImageUri);
                }
            }
        }
    }

    /* Get the real path from the URI */
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    public void upload(View v) {
        openImageChooser();
    }


}

package com.example.felipe.parseandroid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseFile;
import com.parse.ParseObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static android.util.Log.*;



public class firstActivity extends ActionBarActivity {
    private static final int SELECT_PICTURE = 1;
    private String selectedImagePath;
    private String selectedImagePath1;
    private ImageView img;
    private String selectedImageType;
    private Bitmap bitmap;
    byte[] images;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        img = (ImageView)findViewById(R.id.imgphoto);

        ((Button) findViewById(R.id.btnUpload))
                .setOnClickListener(new OnClickListener() {
                    public void onClick(View arg0) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);

                    }
                });

        ((Button) findViewById(R.id.btnSave))
                .setOnClickListener(new OnClickListener() {
                    public void onClick(View arg0) {
                    ParseObject addData=new ParseObject("Names");
                        EditText namephoto = (EditText) findViewById(R.id.editTextNamephoto);

                       EditText nombre=(EditText)findViewById(R.id.editText1);

                       ///addData.put("imagen",img.getIm);
                       addData.put("Name",nombre.getText().toString());
                       e("Nombre de Datos:", nombre.getText().toString());
                       EditText Descripcion=(EditText)findViewById(R.id.editText2);
                       addData.put("Descripcion",Descripcion.getText().toString());
                        //testObject.saveInBackground();
                      //  addData.saveInBackground();

                    }
                });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_first, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        switch (item.getItemId()) {
            case R.id.add:
                return true;
            case R.id.Back:
                finish();
                return true;

            ///  case addFragment.
            default:
                return super.onOptionsItemSelected(item);
        }



    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ///super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
               // Uri selectedImage = data.getData();
                //selectedImageType =data.getType();
                selectedImagePath = getPath(selectedImageUri);
                System.out.println("Image Path : " + selectedImagePath);
                img.setImageURI(selectedImageUri);

            }
        }
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        ///Cursor cursor = managedQuery(uri, projection, null, null, null);
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}

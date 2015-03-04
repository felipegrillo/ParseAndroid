package com.example.felipe.parseandroid;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import com.parse.*;
import java.util.*;
import com.squareup.picasso.Picasso;





public class MainActivity extends Activity {

   ImageView imag;
    ParseQuery<ParseObject> query ;
    List<ParseObject> listas = null;

    private List<chavodelocho> mylista = new ArrayList<chavodelocho>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);
        //inicializa el parse
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "UVAqgvxweF8LgZ49YgQlViRMGFsDQcU46xqr0ZJo", "DOyAEGx5PDaxCR8iGsbVueppQWa7aMrnY7zmTRNY");

        addList();
        addListView();

        //Inicializa el boton y accion del eventp
        Button btnActua = (Button) findViewById(R.id.btnActualiza);
        btnActua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addListView();

            }
        });



        Button btnadd = (Button) findViewById(R.id.add);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData(v);

            }
            public void addData(View v) {
                Intent intent;
             //
               intent = new Intent(MainActivity.this,firstActivity.class);
               startActivity(intent);//

            }
        });


    }

    //   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);


        return true;
    }

    // @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;

            case R.id.action_apagar:
                finish();
                return true;


          ///  case addFragment.
            default:
                return super.onOptionsItemSelected(item);
        }



    }


    private void addList() {

       query = ParseQuery.getQuery("Names");
       listas = null;

        try {
            listas = query.find();

            for (ParseObject post : listas) {
                ParseFile imag = post.getParseFile("imagen");
                mylista.add(new chavodelocho(post.getString("Name"), post.getString("Descripcion"), imag.getUrl(),imag));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void addListView() {
        ArrayAdapter<chavodelocho> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.ListaView);
        list.setAdapter(adapter);
    }



    private class MyListAdapter extends ArrayAdapter<chavodelocho> {
        public MyListAdapter() {
            super(MainActivity.this, R.layout.item_view, mylista);
        }

        public View getView(int posicion, View ConvertView, ViewGroup parent) {

            View itemView = ConvertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.item_view, parent, false);
            }


            //Encontrar actual posicion en la lista
            chavodelocho currentChavo = mylista.get(posicion);
               Log.i("Imagen URL:::", currentChavo.getImagenURL());
           imag = (ImageView) itemView.findViewById(R.id.imgView);
            //cargar imagenes
            Picasso.with(this.getContext())
                    .load(currentChavo.getImagenURL())
                    .into(imag);
            TextView nombres = (TextView) itemView.findViewById(R.id.txtView);
            nombres.setText(currentChavo.getName());

            TextView descrip = (TextView) itemView.findViewById(R.id.txtVIew2);
            descrip.setText((CharSequence) currentChavo.getDescription());

            return itemView;

        }




    }


}

package com.example.felipe.parseandroid;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
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

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {


    private List<chavodelocho> mylista = new ArrayList<chavodelocho>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "UVAqgvxweF8LgZ49YgQlViRMGFsDQcU46xqr0ZJo", "DOyAEGx5PDaxCR8iGsbVueppQWa7aMrnY7zmTRNY");

        addList();

      //  addListView();
        Button btn1 = (Button) findViewById(R.id.btnActualiza);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //  addList();
                addListView();
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
            default:
                return super.onOptionsItemSelected(item);
        }
        //noinspection SimplifiableIfStatement


    }


    private void addList() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Names");
        List<ParseObject> listas = null;

        try {
            listas = query.find();

            for (ParseObject post : listas) {
                ParseFile imag = post.getParseFile("imagen");

                //Log.i("size", String.valueOf(imag.getData().length));
                Bitmap img = BitmapFactory.decodeByteArray(imag.getData(), 0, imag.getData().length);
                mylista.add(new chavodelocho(1, post.getString("Name"), post.getString("Descripcion"), imag.getUrl(), img));


            }
        } catch (ParseException e) {
            e.printStackTrace();
        }




  /*  query.findInBackground(new FindCallback<ParseObject>() {
        @Override
        public void done(List<ParseObject> postList, ParseException e) {
            if (e == null) {
                // If there are results, update the list of posts
                // and notify the adapter
                mylista.clear();
                for (ParseObject post : postList) {

                    ParseFile imag=post.getParseFile("imagen");
                    mylista.add(new chavodelocho(1,post.getString("Name"), post.getString("Descripcion"),imag.getUrl()));

                }
            }else{
                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    });*/



  /*   mylista.add(new chavodelocho(R.drawable.chavo,"Chavo","program"));
     mylista.add(new chavodelocho(R.drawable.chilindrina,"Chilindrina","program"));
     mylista.add(new chavodelocho(R.drawable.nono,"Noño","program"));
     mylista.add(new chavodelocho(R.drawable.popis,"Popis","program"));
     mylista.add(new chavodelocho(R.drawable.profesor,"Profesor","program"));
     */
    }

    private void addListView() {
        ArrayAdapter<chavodelocho> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.ListaView);
        list.setAdapter(adapter);



    }

    public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ImageView imageView;

        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(url);
                   HttpURLConnection connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            imageView.setImageBitmap(result);
        }

    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src", src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap", "returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
            return null;
        }
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
            Log.i("Imagen URL", currentChavo.getImagenURL());
            ImageView imag = (ImageView) findViewById(R.id.imgView);
            loadImageFromURL(currentChavo.getImagenURL(),imag);

            TextView nombres = (TextView) itemView.findViewById(R.id.txtView);
            nombres.setText(currentChavo.getName());

            TextView descrip = (TextView) itemView.findViewById(R.id.txtVIew2);
            descrip.setText((CharSequence) currentChavo.getDescription());


            return itemView;

        }


        public boolean loadImageFromURL(String fileUrl,
                                        ImageView iv){
            URL imageUrl = null;
            HttpURLConnection conn = null;
            try {

                imageUrl = new URL("http://files.parsetfss.com/593b397d-df97-4039-a776-b9456460abc6/tfss-28958ab7-c155-4c36-b9be-7d1cca717c10-chilindrina.jpeg");
                conn = (HttpURLConnection) imageUrl.openConnection();
                conn.connect();

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2; // el factor de escala a minimizar la imagen, siempre es potencia de 2

                Bitmap imagen = BitmapFactory.decodeStream(conn.getInputStream(), new Rect(0, 0, 0, 0), options);
                iv.setImageBitmap(imagen);

                return true;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }


    }

}

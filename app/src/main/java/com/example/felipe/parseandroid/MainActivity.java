package com.example.felipe.parseandroid;

import android.content.DialogInterface;
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

import com.parse.*;

import java.util.*;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    private List<chavodelocho> mylista= new ArrayList<chavodelocho>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            Parse.enableLocalDatastore(this);
            Parse.initialize(this,"UVAqgvxweF8LgZ49YgQlViRMGFsDQcU46xqr0ZJo", "DOyAEGx5PDaxCR8iGsbVueppQWa7aMrnY7zmTRNY");

        addList();

        addListView();
        Button btn1 = (Button) findViewById(R.id.btnActualiza);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addList();
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


private void addList(){

    ParseQuery<ParseObject> query = ParseQuery.getQuery("Names");
    query.findInBackground(new FindCallback<ParseObject>() {
        @Override
        public void done(List<ParseObject> postList, ParseException e) {
            if (e == null) {
                // If there are results, update the list of posts
                // and notify the adapter
                mylista.clear();
                for (ParseObject post : postList) {
                    mylista.add(new chavodelocho(R.drawable.chavo,post.getString("Name"), post.getString("Descripcion")));

                }
            }else{
                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    });



  /*   mylista.add(new chavodelocho(R.drawable.chavo,"Chavo","program"));
     mylista.add(new chavodelocho(R.drawable.chilindrina,"Chilindrina","program"));
     mylista.add(new chavodelocho(R.drawable.nono,"Noño","program"));
     mylista.add(new chavodelocho(R.drawable.popis,"Popis","program"));
     mylista.add(new chavodelocho(R.drawable.profesor,"Profesor","program"));
     */
}

private  void addListView(){
    ArrayAdapter<chavodelocho> adapter=new MyListAdapter();
    ListView list=(ListView) findViewById(R.id.ListaView);
    list.setAdapter(adapter);


}



   private class MyListAdapter extends ArrayAdapter<chavodelocho>{
        public MyListAdapter(){
            super(MainActivity.this,R.layout.item_view,mylista);

        }


    public View getView(int posicion, View ConvertView, ViewGroup parent){
        View itemView=ConvertView;
        if(itemView==null){
            itemView=getLayoutInflater().inflate(R.layout.item_view,parent,false);
        }

        //Encontrar actual posicion en la lista
        chavodelocho currentChavo=mylista.get(posicion);

        //Llenar el imagen
 //       ImageView imag=(ImageView) findViewById(R.id.imgView);
//        imag.setImageResource(currentChavo.getImage());

        TextView nombres=(TextView) itemView.findViewById(R.id.txtView);
        nombres.setText(currentChavo.getName());

        TextView descrip=(TextView) itemView.findViewById(R.id.txtVIew2);
        descrip.setText((CharSequence) currentChavo.getDescription());


        return itemView;

    }




    }
}

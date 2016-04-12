package com.example.casamovil.firebase;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ListView nameList;
    Firebase rootRef = null;
    String firebaseDB = "andresvera";
    String firebaseDefault = "andresvera";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nameList = (ListView) findViewById(R.id.namesLv);
        rootRef = new Firebase("https://" + firebaseDB + ".firebaseio.com/");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create custom dialog object
                final Dialog dialog = new Dialog(MainActivity.this);
                // Include dialog.xml file
                dialog.setContentView(R.layout.custom_dialog);
                // Set dialog title
                dialog.setTitle("Add Name");

                dialog.show();

                final EditText nameTv = (EditText) dialog.findViewById(R.id.nameTv);

                Button addBtm = (Button) dialog.findViewById(R.id.addBtn);
                addBtm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = nameTv.getText().toString();
                        Map<String, String> post1 = new HashMap<String, String>();
                        post1.put("name", name);
                        rootRef.push().setValue(post1);
                        dialog.dismiss();

                    }
                });
                Button declineButton = (Button) dialog.findViewById(R.id.cancelBtn);
                // if decline button is clicked, close the custom dialog
                declineButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });

        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ArrayList<String> names = new ArrayList<String>();
                ArrayList<String> idsArray = new ArrayList<String>();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    System.out.println("Name: " + postSnapshot.child("name").getValue());
                    names.add(postSnapshot.child("name").getValue().toString());
                    idsArray.add(postSnapshot.getRef().getKey().toString());
                }
                String[] namesArray = new String[names.size()];
                namesArray = names.toArray(namesArray);
                String[] ids = new String[idsArray.size()];
                ids = idsArray.toArray(ids);
                CustomAdapter adapter = new CustomAdapter(MainActivity.this, namesArray,ids, rootRef);
                nameList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
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
            final Dialog dialog = new Dialog(MainActivity.this);
            // Include dialog.xml file
            dialog.setContentView(R.layout.custom_dialog);
            // Set dialog title
            dialog.setTitle("Change DB");

            // set values for custom dialog components - text, image and button


            dialog.show();

            final EditText nameTv = (EditText) dialog.findViewById(R.id.nameTv);

            Button addBtm = (Button) dialog.findViewById(R.id.addBtn);
            addBtm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nameDb = nameTv.getText().toString();
                    firebaseDB = nameDb;
                    rootRef = new Firebase("https://" + firebaseDB + ".firebaseio.com/");
                    connectDB();
                    dialog.dismiss();


                }
            });
            Button declineButton = (Button) dialog.findViewById(R.id.cancelBtn);
            // if decline button is clicked, close the custom dialog
            declineButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void connectDB(){
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                try {
                    ArrayList<String> names = new ArrayList<String>();
                    ArrayList<String> idsArray = new ArrayList<String>();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        System.out.println("Name: " + postSnapshot.child("name").getValue());
                        names.add(postSnapshot.child("name").getValue().toString());
                        idsArray.add(postSnapshot.getRef().getKey().toString());
                    }
                    String[] namesArray = new String[names.size()];
                    namesArray = names.toArray(namesArray);
                    String[] ids = new String[idsArray.size()];
                    ids = idsArray.toArray(ids);
                    CustomAdapter adapter = new CustomAdapter(MainActivity.this, namesArray, ids, rootRef);
                    nameList.setAdapter(adapter);
                }catch (Exception ex){
                    Toast.makeText(MainActivity.this, "Estructura de base de datos incompatible, se cambiara a la original", Toast.LENGTH_LONG).show();
                    firebaseDB = firebaseDefault;
                    rootRef = new Firebase("https://" + firebaseDB + ".firebaseio.com/");
                    connectDB();
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

}

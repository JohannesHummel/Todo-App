package com.example.johanneshummel.checklist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {
    private ArrayList<Element> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvItems;
    private Button openAddItem;
    private Button AddItem;
    private String Title = "";
    private String Description= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        lvItems = (ListView) findViewById(R.id.lvItems);


        //Speicherabrufung
        items = (ArrayList<Element>) getIntent().getExtras().getSerializable("exerciseData");
        if(items == null) {
            items = new ArrayList<Element>();
        }
        //Ende Speicherabrufung
        filterArraylist();
        /*items.add("Second Item");*/
        setupListViewListener();
        setupOpenButtonListener();


    }

    private void filterArraylist() {
        ArrayList title = new ArrayList<String>();
        for(int i = 0;i < items.size(); i++) {
            title.add(items.get(i).getTitle());
        }
        itemsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, title);
        lvItems.setAdapter(itemsAdapter);
    }
    private void setupOpenButtonListener() {
        openAddItem = (Button) findViewById(R.id.btnAddItem);
        openAddItem.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                               LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                                               View alertView = inflater.inflate(R.layout.add_alertdialog, null);
                                               final EditText descriptionEdittext = (EditText) alertView.findViewById(R.id.description);
                                               final EditText titleEdittext = (EditText) alertView.findViewById(R.id.title);
                                               builder.setView(alertView);

                                               builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                                   @Override
                                                   public void onClick(DialogInterface dialog, int which) {
                                                       Element addElement = new Element();
                                                       Title = titleEdittext.getText().toString();
                                                       Description = descriptionEdittext.getText().toString();

                                                       addElement.setTitle(Title);
                                                       addElement.setDescription(Description);
                                                       items.add(addElement);
                                                       filterArraylist();
                                                   }
                                               });
                                               builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                                   @Override
                                                   public void onClick(DialogInterface dialog, int which) {
                                                       dialog.cancel();
                                                   }
                                               });

                                               AlertDialog alertDialog = builder.create();
                                               alertDialog.show();

                                           }
                                       });
    }



    // Attaches a long click listener to the listview
    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        items.remove(pos);
                        filterArraylist();
                        itemsAdapter.notifyDataSetChanged();
                        return true;
                    }

                });
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter,
                                            View item, int pos, long id) {
                        changeToCloseUp(item,pos);

                    }

                });

    }

    public void changeToCloseUp(View v, int pos ) {
        Intent intent = new Intent(this, CloseUpExercise.class);
        intent.putExtra("Element",items.get(pos));
        startActivity(intent);
    }
    @Override
    protected void onStop() {
        super.onStop();
        try {
            FileOutputStream fileOutputStream = openFileOutput("saveFile", Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(items);
            fileOutputStream.close();
            objectOutputStream.close();
        } catch (IOException e) {
            Log.d("Error",e.getMessage());
        }
    }


}
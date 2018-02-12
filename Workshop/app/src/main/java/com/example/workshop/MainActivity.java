package com.example.workshop;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    DataBaseHelper dataBaseHelper;
    ListView notelist;
    Button addnote;
    ArrayList<String> notes;
    ArrayAdapter<String> noteAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notelist=(ListView)findViewById(R.id.notelist);
        addnote=(Button)findViewById(R.id.addnote);
        dataBaseHelper= new DataBaseHelper(this);
        notes=new ArrayList<>();
        notes=dataBaseHelper.getAll();
        noteAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,notes);
        notelist.setAdapter(noteAdapter);

        addnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 noteCreator();
            }
        });

        notelist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                dataBaseHelper.deleteNote(notes.get(i));
                notes=dataBaseHelper.getAll();
                noteAdapter= new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,notes);
                notelist.setAdapter(noteAdapter);
                return true;
            }
        });

        notelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                   noteEditor(i);
            }
        });

        HashMap<String, String > user=dataBaseHelper.getUserInfo();

        Toast.makeText(MainActivity.this, user.get("name"),Toast.LENGTH_LONG).show();
    }


    public void noteCreator() {
        final AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = getLayoutInflater();
        final View addnoteview = layoutInflater.inflate(R.layout.notedialg, null);
        dialogbuilder.setView(addnoteview);
        dialogbuilder.setTitle("CREATE NOTE");
        Button add= (Button)addnoteview.findViewById(R.id.add);
        final EditText addnote= (EditText)addnoteview.findViewById(R.id.addnote);
        final AlertDialog addnotedialog = dialogbuilder.create();
        addnotedialog.show();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(addnote.getText().toString().isEmpty())
                {
                    Toast.makeText(MainActivity.this,"Empty Note",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    dataBaseHelper.AddNote(dataBaseHelper.getNoteCount()+1,addnote.getText().toString());
                    notes=dataBaseHelper.getAll();
                    noteAdapter= new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,notes);
                    notelist.setAdapter(noteAdapter);
                    addnotedialog.dismiss();
                }
            }
        });
        }

        public void noteEditor(int i){
            final AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
            LayoutInflater layoutInflater = getLayoutInflater();
            final View addnoteview = layoutInflater.inflate(R.layout.notedialg, null);
            dialogbuilder.setView(addnoteview);
            dialogbuilder.setTitle("UPDATE NOTE");
            final String oldNote=notes.get(i);
            Button add= (Button)addnoteview.findViewById(R.id.add);
            add.setText("UPDATE");
            final EditText addnote= (EditText)addnoteview.findViewById(R.id.addnote);
            addnote.setText(notes.get(i));
            final AlertDialog addnotedialog = dialogbuilder.create();
            addnotedialog.show();
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dataBaseHelper.UpDateNote(oldNote,addnote.getText().toString());
                    notes=dataBaseHelper.getAll();
                    noteAdapter= new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,notes);
                    notelist.setAdapter(noteAdapter);
                    addnotedialog.dismiss();
                }
            });
        }

    @Override
      public void onBackPressed() {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
      }

    }


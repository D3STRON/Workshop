package com.example.workshop;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    DataBaseHelper dataBaseHelper;
    RecyclerView notelist;
    Button addnote;
    ArrayList<String> notes;
    ArrayAdapter<String> noteAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notelist=(RecyclerView) findViewById(R.id.notelist);
        addnote=(Button)findViewById(R.id.addnote);
        addnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noteCreator();
            }
        });

        dataBaseHelper= new DataBaseHelper(this);
        notes=new ArrayList<>();
        notes=dataBaseHelper.getAll();
        notelist.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        notelist.setLayoutManager(mLayoutManager);
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(notes);
        Toast.makeText(this, Integer.toString(recyclerAdapter.getItemCount()), Toast.LENGTH_SHORT).show();
        notelist.setAdapter(recyclerAdapter);
    }


    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
    {
       ArrayList<String> notes= new ArrayList<>();

        RecyclerAdapter(ArrayList<String> notes)
        {
            this.notes=notes;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextView;
            public Button mEditButton,mDeleteButton;
            public ViewHolder(View v) {
                super(v);
                mTextView =v.findViewById(R.id.text);
                mEditButton=v.findViewById(R.id.edit);
                mDeleteButton= v.findViewById(R.id.remove);
            }
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.my_text_view, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            ((ViewHolder)holder).mTextView.setText(notes.get(position));
            ((ViewHolder)holder).mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dataBaseHelper.deleteNote(notes.get(position));
                    notes=dataBaseHelper.getAll();
                    RecyclerAdapter recyclerAdapter= new RecyclerAdapter(notes);
                    notelist.setAdapter(recyclerAdapter);
                }
            });
            ((ViewHolder)holder).mEditButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    noteEditor(position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return notes.size();
        }
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
                    dataBaseHelper.AddNote(addnote.getText().toString());
                    notes=dataBaseHelper.getAll();
                    RecyclerAdapter recyclerAdapter = new RecyclerAdapter(notes);
                    notelist.setAdapter(recyclerAdapter);
                    addnotedialog.dismiss();
                }
            }
        });
    }

    public void noteEditor(int i) {
        final AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = getLayoutInflater();
        final View addnoteview = layoutInflater.inflate(R.layout.notedialg, null);
        dialogbuilder.setView(addnoteview);
        dialogbuilder.setTitle("UPDATE NOTE");
        final String oldNote = notes.get(i);
        Button add = (Button) addnoteview.findViewById(R.id.add);
        add.setText("UPDATE");
        final EditText addnote = (EditText) addnoteview.findViewById(R.id.addnote);
        addnote.setText(notes.get(i));
        final AlertDialog addnotedialog = dialogbuilder.create();
        addnotedialog.show();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataBaseHelper.UpDateNote(oldNote, addnote.getText().toString());
                notes = dataBaseHelper.getAll();
                RecyclerAdapter recyclerAdapter = new RecyclerAdapter(notes);
                notelist.setAdapter(recyclerAdapter);
                addnotedialog.dismiss();
            }
        });
    }
}

/*
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

        Cursor cr= dataBaseHelper.getUserInfo();
        cr.moveToFirst();
        Toast.makeText(MainActivity.this,cr.getString(0),Toast.LENGTH_LONG).show();
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
                    dataBaseHelper.AddNote(addnote.getText().toString());
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
 */
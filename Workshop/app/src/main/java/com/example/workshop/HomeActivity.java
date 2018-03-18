package com.example.workshop;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {
    EditText firstname, lastname, email;
    Button register;
    TextView lname,fname;
    DataBaseHelper dataBaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        register=(Button)findViewById(R.id.register);
        firstname=(EditText) findViewById(R.id.firstname);
        lastname=(EditText)findViewById(R.id.lastname);
        email=(EditText)findViewById(R.id.email);
        lname=(TextView)findViewById(R.id.lname);
        fname=(TextView)findViewById(R.id.fname);
        dataBaseHelper= new DataBaseHelper(this);

        Cursor cr=dataBaseHelper.getUserInfo();
         if(cr.getCount()>0)
         {
             Intent intent = new Intent(HomeActivity.this, MainActivity.class);
             startActivity(intent);
             finish();
         }

        firstname.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                fname.setVisibility(View.VISIBLE);
                lname.setVisibility(View.GONE);
                return false;
            }
        });

        lastname.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                fname.setVisibility(View.GONE);
                lname.setVisibility(View.VISIBLE);
                return false;
            }
        });

        email.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                fname.setVisibility(View.GONE);
                lname.setVisibility(View.GONE);
                if(email.getText().toString().isEmpty()) {
                    email.setText(".com");
                }
                return false;
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().isEmpty() || firstname.getText().toString().isEmpty() || lastname.getText().toString().isEmpty())
                {
                    Toast.makeText(HomeActivity.this,"Koi tho field empty choda hae!", Toast.LENGTH_SHORT).show();
                }else if(!email.getText().toString().contains("@") || !email.getText().toString().substring(email.getText().toString().length()-4,email.getText().toString().length()).matches(".com")){
                    Toast.makeText(HomeActivity.this,"email valid nai hae!", Toast.LENGTH_SHORT).show();
                }
                else{
                    dataBaseHelper.AddUser(firstname.getText().toString()+" "+lastname.getText().toString(),email.getText().toString());
                    Intent intent = new Intent(HomeActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}

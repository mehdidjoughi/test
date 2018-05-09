package com.example.mehdidjo.myapplication2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.mehdidjo.myapplication2.model.Author;
import com.example.mehdidjo.myapplication2.model.Dialog;
import com.example.mehdidjo.myapplication2.model.Message;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddContactActivity extends AppCompatActivity {

    String IMAGE_URL="https://wcc723.gitbooks.io/google_design_translate/content/images/usability/usability_bidirectionality_guidelines_when12.png";
    android.support.v7.widget.Toolbar toolbar;
    private FirebaseDatabase database;
    private DatabaseReference myRefUsers;
    private DatabaseReference myRefDialoge;
    private ChildEventListener mchildEventListener;
    private List<String> userNamelist;
    private ArrayList<Author> userlist;
    EditText editText;
    Spinner spinner;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Add Dialoge");


        userNamelist  = new ArrayList<String>();
        userlist= new ArrayList<>();
        ListView listView = findViewById(R.id.userList);

        editText = findViewById(R.id.group_name);
        spinner = findViewById(R.id.spinner);

        database = FirebaseDatabase.getInstance();
        myRefUsers = database.getReference().child("Users");
        attachDatabaseReadListener();


        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, userNamelist);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);


        button = findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Author author = new Author("sqdqd" , "mehdi");
                Message message = new Message(" " , "abcd" , author);
                myRefDialoge = database.getReference().child("Dialoge");
                String id=  myRefDialoge.push().getKey();
                Dialog dialog= new Dialog(id, editText.getText().toString(),IMAGE_URL,userlist,message,0);
                myRefDialoge.child(id).setValue(dialog);
                finish();
            }
        });

        UserAdapter userAdapter = new UserAdapter(this , userlist);
        listView.setAdapter(userAdapter);
    }


    private void attachDatabaseReadListener() {

        if (mchildEventListener == null){
            mchildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    Author author = dataSnapshot.getValue(Author.class);
                    userlist.add(author);
                    userNamelist.add(author.getName());
                }
                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {}
                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
                @Override
                public void onCancelled(DatabaseError databaseError) {}
            };
            myRefUsers.addChildEventListener(mchildEventListener);
        }
    }
}

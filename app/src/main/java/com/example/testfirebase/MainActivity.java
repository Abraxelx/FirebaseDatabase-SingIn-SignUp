package com.example.testfirebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText txtMail;
    EditText txtPass;
    EditText txtLocation;
    Button btnSubmit;
    Button btnSignIn;


    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtMail = findViewById(R.id.mail);
        txtPass = findViewById(R.id.password);
        txtLocation = findViewById(R.id.city);
        btnSubmit = findViewById(R.id.submit);
        btnSignIn = findViewById(R.id.btnSignIn);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Datas");


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postComment();

            }
        });


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                        {



                            PostDB users = dataSnapshot.child("id").getValue(PostDB.class);
                            Toast.makeText(getApplicationContext(),"Data Bulundu: " + users.email + " " + dataSnapshot.toString(), Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });







    }

    private void postComment() {
      //  id = databaseReference.push().getKey();
        String eMail = txtMail.getText().toString();
        String pass = txtPass.getText().toString();
        String loc = txtLocation.getText().toString();

        PostDB post = new PostDB(eMail,pass,loc);
        databaseReference.push()
                .setValue(post);
    }

}

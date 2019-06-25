package com.example.testfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class LoginActivity extends AppCompatActivity {
    EditText txtMail;
    EditText txtPass;
    Button btnSubmit;
    Button btnRegister;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    List<String> userIdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtMail = findViewById(R.id.txtMail);
        txtPass = findViewById(R.id.txtPassword);
        btnSubmit= findViewById(R.id.btnSubmit);
        btnRegister = findViewById(R.id.btnRegister);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Datas");
        userIdList = new ArrayList();




        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists())
                        {
                            for (DataSnapshot ignored : dataSnapshot.getChildren()) {
                                userIdList.add(ignored.getKey());
                            }
                           // String id =  databaseReference.push().getKey();
                           // String what = dataSnapshot.getKey();
                            for(int i = 0; i<userIdList.size();i++)
                            {
                                PostDB db = dataSnapshot.child(userIdList.get(i)).getValue(PostDB.class);
                                Toast.makeText(getApplicationContext(),"DATA : " +db.email, Toast.LENGTH_SHORT).show();
                            }

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"DATA YOK", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(),"HATA :" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });



    }
}

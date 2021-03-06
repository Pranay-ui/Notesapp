package com.example.firenotes;

import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class AddNotes extends AppCompatActivity {
    FirebaseFirestore fStore;
    EditText noteTitle, noteContent;
    ProgressBar progressBarSave;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


         fStore = FirebaseFirestore.getInstance();
         noteContent = findViewById(R.id.addNoteContent );
         noteTitle = findViewById(R.id.addNotetitle);
         progressBarSave = findViewById(R.id.progressBar);




        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
          String nTitle = noteTitle.getText().toString();
          String nContent  = noteContent.getText().toString();
          if(nTitle.isEmpty() || nContent.isEmpty()){
              Toast.makeText(AddNotes.this,"Can not save Note with empty field",Toast.LENGTH_SHORT).show();
              return;
          }

          progressBarSave.setVisibility(View.VISIBLE);




                DocumentReference docref = fStore.collection("notes").document();
                Map<String,Object> note = new HashMap<>();
                note.put("title",nTitle);
                note.put("content",nContent);

                docref.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddNotes.this,"Note Added",Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddNotes.this,"Error! Try again...",Toast.LENGTH_SHORT).show();
                        progressBarSave.setVisibility(View.VISIBLE);

                    }
                });

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}

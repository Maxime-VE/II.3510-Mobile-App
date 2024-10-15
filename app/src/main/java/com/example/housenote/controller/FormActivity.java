package com.example.housenote.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.housenote.R;
import com.example.housenote.model.Notes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;


public class FormActivity extends AppCompatActivity {

    // Init note list
    public static List<String> noteList = new ArrayList<>();

    // Init XML elements
    TextView mGreetingTextView;
    EditText mNameEditText;
    EditText mAuthor;
    Button mPlayButton;


    /** Function that manage the form activity view
     * (Display note form, Save and redirect to main view) */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        mGreetingTextView = findViewById(R.id.main_textview_greeting);
        mNameEditText = findViewById(R.id.main_edittext_name);
        mPlayButton = findViewById(R.id.main_button_play);
        mAuthor = findViewById(R.id.main_edittext_author);

        // Button is unavailable when any text if entered
        mPlayButton.setEnabled(false);

        // Change button accessibility depending on text
        mNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                mPlayButton.setEnabled(!s.toString().isEmpty());
            }
        });

        //Init Realm
        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        // Get data and save it as a Notes Entity
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String author = mAuthor.getText().toString();
                String description = mNameEditText.getText().toString();
                long createdTime = System.currentTimeMillis();

                realm.beginTransaction();
                Notes new_Notes = realm.createObject(Notes.class);
                new_Notes.setUser(author);
                new_Notes.setContenu(description);
                new_Notes.setDate(createdTime);
                realm.commitTransaction();

                Toast.makeText(getApplicationContext(),"Note ajoutée",Toast.LENGTH_SHORT).show();

                finish();
            }
        });

    }
}
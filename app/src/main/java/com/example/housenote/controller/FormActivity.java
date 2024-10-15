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

    // Initialiser les variables de communication

    private static final String SHARED_PREF_NOTES = "SHARED_PREF_NOTES";
    private static final String SHARED_PREF_NOTES_ITEM = "SHARED_PREF_NOTES_ITEM";
    public static final String BUNDLE_EXTRA_NOTES = "BUNDLE_EXTRA_NOTES";
    public static List<String> noteList = new ArrayList<>();

    // Initialiser les éléments XML
    TextView mGreetingTextView;
    EditText mNameEditText;
    EditText mAuthor;
    Button mPlayButton;

    // Initialiser la fonction lors de la création de l'activité

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        mGreetingTextView = findViewById(R.id.main_textview_greeting);
        mNameEditText = findViewById(R.id.main_edittext_name);
        mPlayButton = findViewById(R.id.main_button_play);
        mAuthor = findViewById(R.id.main_edittext_author);

        // Ne pas rendre le bouton valide si pas de texte

        mPlayButton.setEnabled(false);

        mNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mPlayButton.setEnabled(!s.toString().isEmpty());
            }
        });

        //Initialiser le Realm

        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

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
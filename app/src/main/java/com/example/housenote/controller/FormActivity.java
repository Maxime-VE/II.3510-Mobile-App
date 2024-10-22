package com.example.housenote.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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
import java.util.UUID;

import io.realm.Realm;


public class FormActivity extends AppCompatActivity {

    // Init note list
    public static List<String> noteList = new ArrayList<>();

    // Init XML elements
    TextView mGreetingTextView;
    EditText mNameEditText;
    EditText mAuthor;
    Button mPlayButton;

    private Button buttonRed, buttonBlue, buttonGreen, buttonYellow, buttonOrange, buttonPurple, buttonPink, buttonBlack, buttonWhite, buttonGray;
    private int noteColor;

    /** Function that manage the form activity view
     * (Display note form, Save and redirect to main view) */
    private void setButtonClickListener(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setAlpha(0.5f);
                resetOtherButtons(button);
                //System.out.println(v.getId());
                //System.out.println(R.id.button_red);
                if(v.getId() == R.id.button_red)
                {
                    noteColor = ContextCompat.getColor(getApplicationContext(), R.color.red_b);
                }else if(v.getId() == R.id.button_blue){
                    noteColor = ContextCompat.getColor(getApplicationContext(), R.color.blue_b);
                }else if(v.getId() == R.id.button_green){
                    noteColor = ContextCompat.getColor(getApplicationContext(), R.color.green_b);
                }else if(v.getId() == R.id.button_yellow){
                    noteColor = ContextCompat.getColor(getApplicationContext(), R.color.yellow_b);
                }else if(v.getId() == R.id.button_orange){
                    noteColor = ContextCompat.getColor(getApplicationContext(), R.color.orange_b);
                }else if(v.getId() == R.id.button_purple){
                    noteColor = ContextCompat.getColor(getApplicationContext(), R.color.purple_b);
                }else if(v.getId() == R.id.button_pink){
                    noteColor = ContextCompat.getColor(getApplicationContext(), R.color.pink_b);
                }else if(v.getId() == R.id.button_black){
                    noteColor = ContextCompat.getColor(getApplicationContext(), R.color.black_b);
                }else if(v.getId() == R.id.button_white){
                    noteColor = ContextCompat.getColor(getApplicationContext(), R.color.white_b);
                }else if(v.getId() == R.id.button_gray){
                    noteColor = ContextCompat.getColor(getApplicationContext(), R.color.gray_b);
                }else{
                    noteColor = ContextCompat.getColor(getApplicationContext(), R.color.white_b);
                }
            }
        });
    }
    private void resetOtherButtons(Button clickedButton) {
        Button[] buttons = {
                buttonRed, buttonBlue, buttonGreen, buttonYellow, buttonOrange,
                buttonPurple, buttonPink, buttonBlack, buttonWhite, buttonGray
        };

        for (Button button : buttons) {
            if (button != clickedButton) {
                button.setAlpha(1.0f);
            }
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        String noteId = getIntent().getStringExtra("noteId");

        mGreetingTextView = findViewById(R.id.main_textview_greeting);
        mNameEditText = findViewById(R.id.main_edittext_name);
        mPlayButton = findViewById(R.id.main_button_play);

        buttonRed = findViewById(R.id.button_red);
        buttonBlue = findViewById(R.id.button_blue);
        buttonGreen = findViewById(R.id.button_green);
        buttonYellow = findViewById(R.id.button_yellow);
        buttonOrange = findViewById(R.id.button_orange);
        buttonPurple = findViewById(R.id.button_purple);
        buttonPink = findViewById(R.id.button_pink);
        buttonBlack = findViewById(R.id.button_black);
        buttonWhite = findViewById(R.id.button_white);
        buttonGray = findViewById(R.id.button_gray);

        mAuthor = findViewById(R.id.main_edittext_author);


        setButtonClickListener(buttonRed);
        setButtonClickListener(buttonBlue);
        setButtonClickListener(buttonGreen);
        setButtonClickListener(buttonYellow);
        setButtonClickListener(buttonOrange);
        setButtonClickListener(buttonPurple);
        setButtonClickListener(buttonPink);
        setButtonClickListener(buttonBlack);
        setButtonClickListener(buttonWhite);
        setButtonClickListener(buttonGray);
        // Init Realm
        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        Notes definedNote = null;


        // Enable button if any text is given
        mPlayButton.setEnabled(false);

        if (noteId != null) {
            // Get note from Realm using the id
            definedNote = realm.where(Notes.class).equalTo("id", noteId).findFirst();

            if (definedNote != null) {
                // If a note is found then fulfil the text field
                mNameEditText.setText(definedNote.getContenu());
                mAuthor.setText(definedNote.getUser());
                mPlayButton.setEnabled(true);
            }
        }


        // Change the button accessibility if text is given
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

        Notes finalExistingNote = definedNote;
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String author = mAuthor.getText().toString();
                String description = mNameEditText.getText().toString();
                long createdTime = System.currentTimeMillis();

                realm.beginTransaction();

                if (finalExistingNote != null) {
                    // if we edit an existing note, update it
                    finalExistingNote.setUser(author);
                    finalExistingNote.setContenu(description);
                    finalExistingNote.setDate(createdTime);
                    finalExistingNote.setNoteColor(noteColor);
                } else {
                    // else create a new note
                    Notes new_Notes = realm.createObject(Notes.class, UUID.randomUUID().toString());
                    new_Notes.setUser(author);
                    new_Notes.setContenu(description);
                    new_Notes.setDate(createdTime);
                    new_Notes.setDate(createdTime);
                    new_Notes.setIsFinished(false);
                    new_Notes.setNoteColor(noteColor);
                }

                realm.commitTransaction();

                Toast.makeText(getApplicationContext(), "Note enregistrée", Toast.LENGTH_SHORT).show();

                finish();
            }
        });
    }

}
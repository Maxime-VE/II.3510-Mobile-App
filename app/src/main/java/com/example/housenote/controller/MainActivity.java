package com.example.housenote.controller;

import static com.example.housenote.controller.FormActivity.noteList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.housenote.R;
import com.example.housenote.model.Notes;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;


public class MainActivity extends AppCompatActivity {

    Button AddButton;
    private final int requestCode = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            // Traitez les données de retour de l'activité appelée ici
            String result = data.getStringExtra("resultKey");

            // Rafraîchissez votre interface graphique ou effectuez d'autres actions nécessaires
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AddButton = findViewById(R.id.add_button);

        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameActivityIntent = new Intent(MainActivity.this, FormActivity.class);
                startActivity(gameActivityIntent);
            }
        });

        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        RealmResults<Notes> notesList = realm.where(Notes.class).findAll();

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter myAdapter = new MyAdapter(getApplicationContext(),notesList);
        recyclerView.setAdapter(myAdapter);

        notesList.addChangeListener(new RealmChangeListener<RealmResults<Notes>>() {
            @Override
            public void onChange(RealmResults<Notes> notes) {
                myAdapter.notifyDataSetChanged();
            }
        });

    }





}
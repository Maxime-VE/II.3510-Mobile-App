package com.example.housenote.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Notes extends RealmObject{
    @PrimaryKey
    private String id;

    String mContenu;
    String mUser;
    long mDate;
    private int noteColor;

    public String getId() {return id;}

    public String getContenu() {
        return mContenu;
    }

    public String getUser() {
        return mUser;
    }

    public long getDate() {
        return mDate;
    }

    public int getNoteColor() { return noteColor; }

    public void setId(String id) {this.id = id;}

    public void setContenu(String contenu) {
        mContenu = contenu;
    }

    public void setUser(String user) {
        mUser = user;
    }

    public void setDate(long date) { mDate = date; }
    public void setNoteColor(int noteColor) { this.noteColor = noteColor; }
}


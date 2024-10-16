package com.example.housenote.controller;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.housenote.R;
import com.example.housenote.model.Notes;

import io.realm.Realm;
import io.realm.RealmResults;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    RealmResults<Notes> notesList;

    public MyAdapter(Context context, RealmResults<Notes> notesList) {
        this.context = context;
        this.notesList = notesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        Notes note = notesList.get(position);
        holder.titleOutput.setText(note.getContenu());
        String Author = note.getUser();
        Log.d("FormActivity", "Author: " + Author);
        // User name or "Unknown"
        if(!Objects.equals(Author, "")){
            holder.descriptionOutput.setText("par " + note.getUser());
        } else {
            holder.descriptionOutput.setText("Unknown");
        }


        String formatedTime = DateFormat.getDateTimeInstance().format(note.getDate());
        holder.timeOutput.setText(formatedTime);

        // Trigger Long click on a note
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                PopupMenu menu = new PopupMenu(context,v);
                menu.getMenu().add("Edit");
                menu.getMenu().add("Delete");
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().equals("Edit")){
                            //prepare the note to be edited
                            String noteId = note.getId();

                            Intent formActivityIntent = new Intent(holder.itemView.getContext(), FormActivity.class); //Use this context instead of activity to run from the adapter
                            formActivityIntent.putExtra("noteId", noteId); // Add the noteId as an arg
                            formActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Allow to start Activity from Adapter
                            holder.itemView.getContext().startActivity(formActivityIntent);
                        }
                        if(item.getTitle().equals("Delete")){
                            //delete the note
                            Realm realm = Realm.getDefaultInstance();
                            realm.beginTransaction();
                            note.deleteFromRealm();
                            realm.commitTransaction();
                            Toast.makeText(context,"Note deleted",Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });
                menu.show();

                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titleOutput;
        TextView descriptionOutput;
        TextView timeOutput;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleOutput = itemView.findViewById(R.id.titleoutput);
            descriptionOutput = itemView.findViewById(R.id.descriptionoutput);
            timeOutput = itemView.findViewById(R.id.timeoutput);
        }
    }
}

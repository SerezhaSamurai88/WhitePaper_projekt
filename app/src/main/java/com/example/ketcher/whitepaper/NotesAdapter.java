package com.example.ketcher.whitepaper;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter {
    ArrayList<MyNote>myNotesList;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView noteDateText;
        TextView noteText;
        ImageView removeNoteImg;

        public MyViewHolder(final View itemView) {
            super(itemView);

            noteDateText = (TextView)itemView.findViewById(R.id.noteDate);
            noteText = (TextView)itemView.findViewById(R.id.noteText);
            removeNoteImg = (ImageView)itemView.findViewById(R.id.removeNoteImg);

            removeNoteImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DBNotesHelper dbNotesHelper = new DBNotesHelper(itemView.getContext());
                    dbNotesHelper.removeNote(noteDateText.getText().toString(), noteText.getText().toString());

                    for(int i = 0; i<myNotesList.size(); i++) {
                        if(MainActivity.notesArray.get(i).getDate().equals(noteDateText.getText().toString())
                                && MainActivity.notesArray.get(i).getNoteText().equals(noteText.getText().toString())){

                            myNotesList.remove(i);
                            ObservableObject.getInstance().updateValue();
                        }
                    }
                }
            });


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent openFullNote = new Intent(itemView.getContext(), FullNoteActivity.class);
                    openFullNote.putExtra("noteText", noteText.getText().toString());
                    itemView.getContext().startActivity(openFullNote);
                }
            });

        }
    }
    NotesAdapter(ArrayList<MyNote>myNotesList){
        this.myNotesList = myNotesList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_note, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyNote myNote = myNotesList.get(position);
        if (myNote != null) {
            ((MyViewHolder) holder).noteDateText.setText(String.valueOf(myNote.getDate()));
            ((MyViewHolder) holder).noteText.setText(myNote.getNoteText());
        }

    }

    @Override
    public int getItemCount() {
        return myNotesList.size();
    }
}

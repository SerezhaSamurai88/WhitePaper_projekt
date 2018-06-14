package com.example.ketcher.whitepaper;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import static com.example.ketcher.whitepaper.NoteActivity.getCurrentDate;

public class MainActivity extends AppCompatActivity implements Observer{

    RecyclerView notesRecyclerView;
    Button addNewButton;
    static ArrayList<MyNote>notesArray = new ArrayList<>();
    NotesAdapter notesAdapter;
    DBNotesHelper dbNotesHelper;
    TextView noNotesTxt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_layout);
        ObservableObject.getInstance().addObserver(this);

        notesRecyclerView = (RecyclerView)findViewById(R.id.notesRecyclerView);
        addNewButton = (Button)findViewById(R.id.addNewNoteButton);
        noNotesTxt = (TextView)findViewById(R.id.noNotesTxt);

        addNewButton.setOnTouchListener(NoteActivity.getOnTouchListener(R.drawable.border_button_down));
        addNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent noteActivity = new Intent(getApplicationContext(), NoteActivity.class);
                startActivity(noteActivity);
            }
        });

        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        notesRecyclerView.setLayoutManager(mLayoutManager);
        notesAdapter = new NotesAdapter(notesArray);
        notesRecyclerView.setAdapter(notesAdapter);

        dbNotesHelper = new DBNotesHelper(getApplicationContext());
        if(notesArray.isEmpty()) {
            notesArray.addAll(dbNotesHelper.getAllNotes());
            if(notesArray.size() > 0){
                noNotesTxt.setVisibility(View.GONE);
            }
        }
        notesAdapter.notifyDataSetChanged();


    }

    @Override
    protected void onResume() {
        super.onResume();
        notesAdapter.notifyDataSetChanged();
        if(notesArray.size() != 0){
            noNotesTxt.setVisibility(View.GONE);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        notesAdapter.notifyDataSetChanged();
        if(notesArray.size() == 0){
            noNotesTxt.setVisibility(View.VISIBLE);
        }
    }
}

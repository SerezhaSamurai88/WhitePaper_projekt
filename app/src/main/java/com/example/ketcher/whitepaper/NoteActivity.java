package com.example.ketcher.whitepaper;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NoteActivity extends AppCompatActivity {

    Button addNoteButton;
    Button backButton;
    EditText newNoteText;
    DBNotesHelper dbNotesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_note_layout);

        addNoteButton = (Button) findViewById(R.id.addNoteButton);
        backButton = (Button) findViewById(R.id.backButton);
        newNoteText = (EditText) findViewById(R.id.newNoteText);

        addNoteButton.setOnTouchListener(getOnTouchListener(R.drawable.border_button_down));
        backButton.setOnTouchListener(getOnTouchListener(R.drawable.border_button_down_red));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newNoteText = NoteActivity.this.newNoteText.getText().toString().replaceAll("\\s+", " ");
                if (newNoteText.trim().equals("")) {
                    NoteActivity.this.newNoteText.setError("Can't be empty");
                } else {
                    if (newNoteText.charAt(newNoteText.length() - 1) == ' ') {
                        newNoteText = newNoteText.substring(0, newNoteText.length() - 1);
                    }

                    dbNotesHelper = new DBNotesHelper(getApplicationContext());
                    MainActivity.notesArray.add(0, new MyNote(getCurrentDate(), newNoteText));
                    dbNotesHelper.addNote(new MyNote(getCurrentDate(), newNoteText));

                    finish();
                }
            }
        });
    }

    public static View.OnTouchListener getOnTouchListener(final int drawable){
        View.OnTouchListener onTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        view.setBackgroundResource(drawable);
                        view.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        view.setBackgroundResource(R.drawable.border_button);
                        view.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_CANCEL: {
                        view.setBackgroundResource(R.drawable.border_button);
                        view.invalidate();
                        break;
                    }
                }
                return false;
            }
        };
        return onTouchListener;
    }

    static public String getCurrentDate(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);

        return formattedDate;
    }
}

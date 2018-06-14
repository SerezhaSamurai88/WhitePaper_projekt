package com.example.ketcher.whitepaper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class FullNoteActivity extends AppCompatActivity {

    TextView fullNoteText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_note_layout);

        fullNoteText = (TextView)findViewById(R.id.fullNoteText);
        fullNoteText.setText(getIntent().getStringExtra("noteText"));
    }
}

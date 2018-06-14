package com.example.ketcher.whitepaper;

import java.util.Date;

public class MyNote {
    String date;
    String noteText;

    public MyNote(String date, String noteText) {
        this.date = date;
        this.noteText = noteText;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }
}

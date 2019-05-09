package my.test.mynotes.Room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity
public class Note {

    @PrimaryKey
    public int id;
    public String textNote;
    //public Date dateNote;
    public String day;
    public String time;

    public Note(int id, String textNote, String day, String time) {
        this.id = id;
        this.textNote = textNote;
        this.day = day;
        this.time = time;
    }

    public Note(int id, String textNote, Date dateNote) {
        this.id = id;
        this.textNote = textNote;
        //this.dateNote = dateNote;
        updateDayAndTime(dateNote);
    }

    public void updateDayAndTime(Date dateNote) {

        String DAY_FORMAT = "dd.MM.yy";
        String TIME_FORMAT = "HH:mm";
        SimpleDateFormat dayFormat = new SimpleDateFormat(DAY_FORMAT, Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT, Locale.getDefault());
        this.day = dayFormat.format(dateNote);
        this.time = timeFormat.format(dateNote);

    }
}

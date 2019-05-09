package my.test.mynotes.Room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Note.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    public abstract NoteDao noteDao();

}

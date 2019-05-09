package my.test.mynotes.Room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("Select * from note")
    List<Note> getAll();

    @Query("SELECT * FROM note WHERE id = (SELECT MAX(id) from note)")
    Note getMaxId();

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);
}

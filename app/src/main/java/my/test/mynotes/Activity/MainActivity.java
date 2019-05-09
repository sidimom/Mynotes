package my.test.mynotes.Activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import my.test.mynotes.Adapter.NoteAdapter;
import my.test.mynotes.App;
import my.test.mynotes.R;
import my.test.mynotes.Room.AppDataBase;
import my.test.mynotes.Room.NoteDao;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_addNote;
    RecyclerView rv_notes;
    NoteAdapter noteAdapter;
    AppDataBase db;
    NoteDao noteDao;
    final int ID_ACTIVITY_NOTE_DETAILS = 1;
    final int RESULT_OK = 1;
    final int RESULT_DELETE = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initRoom();
    }

    private void initRoom() {
        db = App.getInstance().getDatabase();
        noteDao = db.noteDao();
        noteAdapter.setItems(noteDao);
    }

    private void initViews() {

        btn_addNote = findViewById(R.id.btn_openDetails);
        btn_addNote.setOnClickListener(this);
        rv_notes = findViewById(R.id.my_recycler_view);
        rv_notes.setLayoutManager(new LinearLayoutManager(this));
        NoteAdapter.OnNoteClickListener onNoteClickListener = new NoteAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(int position, String textNote) {
                openActivityNoteDetails(position, textNote);
            }
        };
        noteAdapter = new NoteAdapter(onNoteClickListener);
        rv_notes.setAdapter(noteAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_openDetails:
                openActivityNoteDetails(-1, "");
                break;
            default:
                break;
        }
    }

    public void openActivityNoteDetails(int position, String noteText) {

        Intent intent = new Intent(MainActivity.this, NoteDetailsActivity.class);
        intent.putExtra("notePosition", position);
        intent.putExtra("noteText", noteText);
        startActivityForResult(intent, ID_ACTIVITY_NOTE_DETAILS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ID_ACTIVITY_NOTE_DETAILS){
            if (data == null){
                Toast.makeText(this, "Data of note is not received!", Toast.LENGTH_SHORT).show();
                return;
            }
            int textPosition = data.getIntExtra("responsePosition", noteAdapter.getItemCount());
            if (resultCode == RESULT_OK) {
                String textNote = data.getStringExtra("responseText");

                if (textNote == null) {
                    Toast.makeText(this, "Data of note is not received!", Toast.LENGTH_SHORT).show();
                    return;
                }

                noteAdapter.setItem(noteDao, textPosition, textNote);
            }else if (resultCode == RESULT_DELETE){
                noteAdapter.deleteItem(noteDao, textPosition);
            }
        }
    }


}

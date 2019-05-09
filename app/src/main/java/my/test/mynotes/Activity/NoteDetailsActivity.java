package my.test.mynotes.Activity;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import my.test.mynotes.R;

public class NoteDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et_note;
    Button btn_deleteNote;
    final int RESULT_OK = 1;
    final int RESULT_DELETE = -1;
    int notePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        initViews();
    }

    private void initViews() {
        btn_deleteNote = findViewById(R.id.btn_delete);
        btn_deleteNote.setOnClickListener(this);

        et_note = findViewById(R.id.et_note);
        Intent intent = getIntent();
        String noteText = intent.getStringExtra("noteText");
        notePosition = intent.getIntExtra("notePosition", 0);
        if (noteText != null){
            et_note.setText(noteText);
        }

        if (notePosition == -1){
            //don't show button, if we add note
            btn_deleteNote.setVisibility(View.GONE);
        }

    }

    @Override
    public void onBackPressed() {
        String noteText = et_note.getText().toString();
        if (noteText.isEmpty()){
            Toast.makeText(this, "Note text is empty!", Toast.LENGTH_SHORT).show();
        }else{
            setIntentResult(RESULT_OK, noteText);
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_delete:
                setIntentResult(RESULT_DELETE, "");
                finish();
                break;
            default:
                break;
        }
    }

    private void setIntentResult(int result, String noteText) {
        Intent intentResult = new Intent();
        intentResult.putExtra("responseText", noteText);
        intentResult.putExtra("responsePosition", notePosition);
        setResult(result, intentResult);
    }
}

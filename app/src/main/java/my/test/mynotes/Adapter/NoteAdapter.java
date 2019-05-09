package my.test.mynotes.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import my.test.mynotes.Room.Note;
import my.test.mynotes.R;
import my.test.mynotes.Room.NoteDao;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    private List<Note> listNotes = new ArrayList<>();
    private OnNoteClickListener onNoteClickListener;

    public NoteAdapter(OnNoteClickListener _onNoteClickListener){
        this.onNoteClickListener = _onNoteClickListener;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_layout, parent, false);
        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        holder.bind(listNotes.get(position));
    }

    @Override
    public int getItemCount() {
        return listNotes.size();
    }

    public void setItem(NoteDao noteDao, int position, String noteText) {

        Date currentTime = Calendar.getInstance().getTime();
        int max_id = 1;
        Note lastNote = noteDao.getMaxId();
        if (lastNote != null){
            max_id = lastNote.id + 1;
        };

        if (position == -1) {
            //listNotes.add(new Note(noteText, currentTime));
            noteDao.insert(new Note(max_id, noteText, currentTime));
        }else{
            Note note = listNotes.get(position);
            note.textNote = noteText;
            note.updateDayAndTime(currentTime);
            noteDao.update(note);
        }
        refreshRecyclerView(noteDao);
    }

    public void deleteItem(NoteDao noteDao, int position) {
        Note note = listNotes.get(position);
        //listNotes.remove(note);
        noteDao.delete(note);
        refreshRecyclerView(noteDao);
    }

    public void setItems(NoteDao noteDao) {
        refreshRecyclerView(noteDao);
    }

    private void refreshRecyclerView(NoteDao noteDao) {
        listNotes = noteDao.getAll();
        notifyDataSetChanged();
    }

    public class NoteHolder extends RecyclerView.ViewHolder {

        private TextView tv_note, tv_day, tv_time;
        public NoteHolder(View itemView) {
            super(itemView);
            tv_note = itemView.findViewById(R.id.tv_note);
            tv_day = itemView.findViewById(R.id.tv_day);
            tv_time = itemView.findViewById(R.id.tv_time);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    onNoteClickListener.onNoteClick(position, listNotes.get(position).textNote);
                }
            });
        }

        public void bind(Note note) {
            tv_note.setText(note.textNote);
            tv_day.setText(note.day);
            tv_time.setText(note.time);
        }
    }

    public interface OnNoteClickListener{
        void onNoteClick(int position, String textNote);
    }


}

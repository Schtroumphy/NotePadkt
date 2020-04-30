package fr.perso.project.android.kotlin.notepad

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import fr.perso.project.android.kotlin.notepad.NoteDetailActivity.Companion.ACTION_DELETE_NOTE
import fr.perso.project.android.kotlin.notepad.NoteDetailActivity.Companion.ACTION_SAVE_NOTE
import fr.perso.project.android.kotlin.notepad.NoteDetailActivity.Companion.EXTRA_NOTE
import fr.perso.project.android.kotlin.notepad.NoteDetailActivity.Companion.EXTRA_NOTE_INDEX
import fr.perso.project.android.kotlin.notepad.NoteDetailActivity.Companion.REQUEST_CODE_EDIT_NOTE
import fr.perso.project.android.kotlin.notepad.model.Note
import fr.perso.project.android.kotlin.notepad.utils.deleteNoteInFile
import fr.perso.project.android.kotlin.notepad.utils.loadNotes
import fr.perso.project.android.kotlin.notepad.utils.persistNote

class NoteListActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var noteList : MutableList<Note>
    lateinit var noteAdapter : NoteAdapter
    lateinit var coordinatorLayout : CoordinatorLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        findViewById<FloatingActionButton>(R.id.fab_create_note).setOnClickListener(this)

        noteList = loadNotes(this)
        noteAdapter = NoteAdapter(noteList, this)

        val recyclerView = findViewById<RecyclerView>(R.id.rv_note_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = noteAdapter

        coordinatorLayout = findViewById(R.id.coordinatorLayout) as CoordinatorLayout
    }
    override fun onClick(v: View) {
        if(v.tag != null){
            println("Click sur une note de la liste")
            showNoteDetail(v.tag as Int)
        } else {
            when(v.id){
                R.id.fab_create_note -> createNewNote()
            }
        }
    }

    private fun createNewNote() {
        showNoteDetail(-1)
    }

    fun showNoteDetail(noteIndex : Int){
        val note = if (noteIndex < 0) Note() else noteList[noteIndex]
        val intent= Intent(this, NoteDetailActivity::class.java)
        intent.putExtra(EXTRA_NOTE, note as Parcelable)
        intent.putExtra(EXTRA_NOTE_INDEX, noteIndex)
        startActivityForResult(intent, REQUEST_CODE_EDIT_NOTE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode != Activity.RESULT_OK || data == null){
            return
        }
        when(requestCode){
            REQUEST_CODE_EDIT_NOTE -> processEditNoteResult(data)
        }

    }

    private fun processEditNoteResult(data: Intent) {
        val noteIndex = data.getIntExtra(EXTRA_NOTE_INDEX, -1)

        when(data.action) {
            ACTION_SAVE_NOTE -> {
                val note = data.getParcelableExtra<Note>(EXTRA_NOTE)
                saveNote(note, noteIndex)
            }
            ACTION_DELETE_NOTE -> deleteNote(noteIndex)
        }

    }

    private fun deleteNote(noteIndex: Int) {
        if(noteIndex < 0){
            return
        }
        val note = noteList.removeAt(noteIndex)
        deleteNoteInFile(this, note)
        noteAdapter.notifyDataSetChanged()

        Snackbar.make(coordinatorLayout, "${note.title} supprimé", Snackbar.LENGTH_SHORT).show()
    }

    fun saveNote(note : Note, noteIndex : Int){
        persistNote(this, note)
        if(noteIndex <0){
            noteList.add(0, note)
        } else {
            noteList[noteIndex] = note
        }
        noteAdapter.notifyDataSetChanged()
    }
}

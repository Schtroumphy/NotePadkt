package fr.perso.project.android.kotlin.notepad

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import fr.perso.project.android.kotlin.notepad.model.Note

class NoteDetailActivity : AppCompatActivity() {

    companion object{
        val EXTRA_NOTE = "note"
        val EXTRA_NOTE_INDEX = "index"

        val REQUEST_CODE_EDIT_NOTE = 1
        val ACTION_SAVE_NOTE = "fr.perso.project.android.kotlin.notepad.actions.ACTION_SAVE"
        val ACTION_DELETE_NOTE = "fr.perso.project.android.kotlin.notepad.actions.ACTION_DELETE"
    }

    lateinit var note : Note
    var index : Int = -1

    lateinit var titleView : TextView
    lateinit var textView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)//display back arrow button

        note = intent.getParcelableExtra(EXTRA_NOTE)
        index = intent.getIntExtra(EXTRA_NOTE_INDEX, -1)

        titleView = findViewById(R.id.title)
        textView = findViewById(R.id.text)

        titleView.text=note.title
        textView.text=note.content
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_note_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_save -> {
                saveNote()
                return true
            }
            R.id.action_delete -> {
                showConfirmDeleteNoteDialog()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showConfirmDeleteNoteDialog() {
        val confirmFragment = ConfirDeleteNoteDialogFragment(note.title)
        confirmFragment.listener = object : ConfirDeleteNoteDialogFragment.ConfirmDeleteDialogListener{
            override fun onDialogPositiveClick() {
                deleteNote()
            }

            override fun onDialogNegativeClick() {

            }
        }
        confirmFragment.show(supportFragmentManager, "confirm")
    }

    fun deleteNote(){
        val intent= Intent(ACTION_DELETE_NOTE)
        intent.putExtra(EXTRA_NOTE_INDEX, index)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    fun saveNote(){
        note.title = titleView.text.toString()
        note.content = textView.text.toString()
        val intent = Intent(ACTION_SAVE_NOTE)
        intent.putExtra(EXTRA_NOTE, note as Parcelable)
        intent.putExtra(EXTRA_NOTE_INDEX, index)
        setResult(Activity.RESULT_OK, intent) //Answer to the call "startActivityForResult"
        finish()
    }
}

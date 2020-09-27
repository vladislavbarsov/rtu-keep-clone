package com.example.rtukeepclone

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.rtukeepclone.MainActivity.Companion.ACTIVITY_ID
import com.example.rtukeepclone.MainActivity.Companion.EXTRA_ID
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private val notesDatabase get() = Database.getInstance(this)
    private lateinit var noteToEdit: NoteItem
    private var noteId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val requestFrom = intent.getStringExtra(ACTIVITY_ID) ?: "DetailActivity"
        Log.e("mode is: ", requestFrom)
        noteId = intent.getLongExtra(EXTRA_ID, 0)
        noteToEdit = notesDatabase.noteItemDao().getNoteById(noteId)

        if (requestFrom == MAIN_ACTIVITY){
            saveBtn.setOnClickListener { saveNewNote() }
        } else {
            noteEditSubject.setText(noteToEdit.noteSubject)
            noteEditBodyText.setText(noteToEdit.noteText)
            saveBtn.setOnClickListener{ updateNote() }
        }

    }

    private fun saveNewNote(){
        notesDatabase.noteItemDao().updateNote(
            noteToEdit.copy(
                noteSubject = noteEditSubject.text.toString(),
                noteText = noteEditBodyText.text.toString()
            )
        )
        val intent = Intent().putExtra(EXTRA_ID, noteId)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun updateNote(){

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val MAIN_ACTIVITY = "MainActivity"
    }
}
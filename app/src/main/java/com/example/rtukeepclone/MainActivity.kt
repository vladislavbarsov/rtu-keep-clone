package com.example.rtukeepclone

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AdapterClickListener {

    private val notesDatabase get() = Database.getInstance(this)

    private val notes = mutableListOf<NoteItem>()

    private lateinit var adapter: NoteItemRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notes.addAll(notesDatabase.noteItemDao().getAllNotes())

        adapter = NoteItemRecyclerAdapter(notes, this)
        noteList.adapter = adapter

        newNoteBtn.setOnClickListener { addNewNote() }
    }

    private fun addNewNote(){
        val blankNote = NoteItem("", "")
        blankNote.uid = notesDatabase.noteItemDao().insertAllNotes(blankNote).first()
        Log.e("blnak note ID is: ", "${blankNote.uid}")
        val intent = Intent(this, DetailActivity::class.java)
            .putExtra(ACTIVITY_ID, "MainActivity")
            .putExtra(EXTRA_ID, blankNote.uid)
        notes.add(blankNote)
        startActivityForResult(intent, REQUEST_CODE_DETAILS)
    }

    override fun deleteNote(note: NoteItem) {
        notesDatabase.noteItemDao().deleteNote(note)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_DETAILS && resultCode == Activity.RESULT_OK && data != null) {
            val noteId = data.getLongExtra(EXTRA_ID, 0)
            Log.e("Updated note UID is: ", "$noteId")
            val note = notesDatabase.noteItemDao().getNoteById(noteId)
            val position = notes.indexOfFirst { it.uid == note.uid } // is -1
            notes[position] = note
            Log.e("the item is: ", "$position")
            adapter.notifyItemChanged(position)
        }
    }

    companion object {
        const val EXTRA_ID = "com.example.rtuKeepClone_note_id"
        const val ACTIVITY_ID = "MainActivity"
        const val REQUEST_CODE_DETAILS = 1234
    }
}

interface AdapterClickListener {
    fun deleteNote(note: NoteItem)
}

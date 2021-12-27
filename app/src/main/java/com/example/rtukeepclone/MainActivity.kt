package com.example.rtukeepclone

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AdapterClickListener {

    private val notesDatabase get() = Database.getInstance(this)

    private val notes = mutableListOf<NoteItem>()

    private lateinit var adapter: NoteItemRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.setBackgroundColor(ContextCompat.getColor(this, R.color.activityBackgroundColor))

        notes.addAll(notesDatabase.noteItemDao().getAllNotes())
        notes.sortByDescending { it.uid }
        adapter = NoteItemRecyclerAdapter(notes, this)
        noteList.adapter = adapter

        newNoteBtn.setOnClickListener { addNewNote() }
    }

    private fun addNewNote(){
        val defaultColor = ContextCompat.getColor(this, R.color.colorCardCoral)
        val blankNote = NoteItem("", "", defaultColor)
        blankNote.uid = notesDatabase.noteItemDao().insertAllNotes(blankNote).first()
        notes.add(blankNote)
        val intent = Intent(this, DetailActivity::class.java)
            .putExtra(ACTIVITY_ID, "MainActivity")
            .putExtra(EXTRA_ID, blankNote.uid)
        startActivityForResult(intent, REQUEST_CODE_DETAILS)
    }

    override fun deleteNote(note: NoteItem) {
        val position = notes.indexOfFirst { it.uid == note.uid }
        notes.removeAt(position)
        notesDatabase.noteItemDao().deleteNote(note)
        adapter.notifyItemRemoved(position)
    }

    override fun clickedNote(note: NoteItem) {
        val intent = Intent(this, DetailActivity::class.java)
            .putExtra(EXTRA_ID, note.uid)
        startActivityForResult(intent, REQUEST_CODE_DETAILS)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (
            requestCode == REQUEST_CODE_DETAILS &&
            resultCode == Activity.RESULT_OK &&
            data != null
        ) {
            val noteId = data.getLongExtra(EXTRA_ID, 0)
            val note = notesDatabase.noteItemDao().getNoteById(noteId)
            val position = notes.indexOfFirst{ it.uid == note.uid }
            notes[position] = note
            adapter.notifyItemChanged(position)
        }
        checkForEmptyNote()
    }

    private fun checkForEmptyNote(){
        if (notes.isNotEmpty()){
            val lastNote = notes[0]
            if (lastNote.noteTitle == "" && lastNote.noteText == ""){
                notes.remove(lastNote)
                notesDatabase.noteItemDao().deleteNote(lastNote)
            }
        }
    }

    companion object {
        const val EXTRA_ID = "com.example.rtuKeepClone_note_id"
        const val ACTIVITY_ID = "MainActivity"
        const val REQUEST_CODE_DETAILS = 1234
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
        notes.sortByDescending { it.uid }
        checkForEmptyNote()
    }
}

interface AdapterClickListener {
    fun deleteNote(note: NoteItem)
    fun clickedNote(note: NoteItem)
}

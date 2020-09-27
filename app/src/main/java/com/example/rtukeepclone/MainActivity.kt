package com.example.rtukeepclone

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val notes = mutableListOf(
        NoteItem("What are dreams made of?", "This"),
        NoteItem("Android or IOS?", "Why not Both ?"),
        NoteItem("To buy", "IceCream")
    )

    private lateinit var adapter: NoteItemRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = NoteItemRecyclerAdapter(notes)
        noteList.adapter = adapter

        newNoteBtn.setOnClickListener { addNewNote() }
    }

    private fun addNewNote(){
        val intent = Intent(this, DetailActivity::class.java)
            .putExtra(EXTRA_ID, REQUEST_FROM)
        startActivity(intent)
    }

    companion object {
        const val EXTRA_ID = "com.example.rtuKeepClone_note_id"
        const val REQUEST_FROM = "MainActivity"
    }
}
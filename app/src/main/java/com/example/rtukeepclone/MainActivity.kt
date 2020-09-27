package com.example.rtukeepclone

import android.os.Bundle
import android.widget.Toast
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
        Toast.makeText(this, "Will do later", Toast.LENGTH_SHORT).show()
    }
}
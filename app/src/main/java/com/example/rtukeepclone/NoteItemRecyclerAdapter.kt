package com.example.rtukeepclone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.note_item.view.*

class NoteItemRecyclerAdapter (
        private val notes: MutableList<NoteItem>,
        private val listener: AdapterClickListener) :
        RecyclerView.Adapter<NoteItemRecyclerAdapter.NoteViewHolder> (){

        class NoteViewHolder(view: View): RecyclerView.ViewHolder(view)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteItemRecyclerAdapter.NoteViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
                return NoteViewHolder(view)
        }

        override fun getItemCount() = notes.size

        override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
                val note = notes[position]
                val noteView = holder.itemView

                noteView.cardSubjectText.text = note.noteSubject
                noteView.cardBodyText.text = note.noteText
                noteView.noteCard.setCardBackgroundColor(note.noteColor)

                noteView.cardDeleteBtn.setOnClickListener{
                        listener.deleteNote(note)
                }
                noteView.setOnClickListener{
                        listener.clickedNote(notes[position])
                }
        }
}
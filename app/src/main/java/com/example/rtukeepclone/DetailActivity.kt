package com.example.rtukeepclone

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.rtukeepclone.MainActivity.Companion.ACTIVITY_ID
import com.example.rtukeepclone.MainActivity.Companion.EXTRA_ID
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity() {

    private val notesDatabase get() = Database.getInstance(this)
    private lateinit var noteToEdit: NoteItem
    private var noteId: Long = 0
    private var editNoteColor: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        window.decorView.setBackgroundColor(ContextCompat.getColor(this, R.color.activityBackgroundColor))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val requestFrom = intent.getStringExtra(ACTIVITY_ID) ?: "DetailActivity"
        noteId = intent.getLongExtra(EXTRA_ID, 0)
        noteToEdit = notesDatabase.noteItemDao().getNoteById(noteId)
        editNoteColor = noteToEdit.noteColor

        if (requestFrom != MAIN_ACTIVITY){
            noteEditSubject.setText(noteToEdit.noteSubject)
            noteEditBodyText.setText(noteToEdit.noteText)
            detailNoteCard.setCardBackgroundColor(editNoteColor)
            colorBtn.setTextColor(editNoteColor)
        }
        saveBtn.setOnClickListener { updateNote() }
        shareBtn.setOnClickListener { shareNote() }
        colorBtn.setOnClickListener { changeNoteColor() }
    }

    private fun updateNote(){
        notesDatabase.noteItemDao().updateNote(
            noteToEdit.copy(
                noteSubject = noteEditSubject.text.toString(),
                noteText = noteEditBodyText.text.toString(),
                noteColor = editNoteColor
            )
        )
        val intent = Intent().putExtra(EXTRA_ID, noteId)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun shareNote(){
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT,
            "${noteEditSubject.text}\n${noteEditBodyText.text}")
            type = "text/plain"
        }
        startActivity(sendIntent)
    }

    private fun changeNoteColor(){
        val builder = AlertDialog.Builder(this)
        val colors = arrayOf("Coral", "Teal", "Green")
        builder.setTitle("Choose Color")
            .setItems(colors) {dialog, which ->
                updateColor(which)
            }
        val dialog = builder.create()
        dialog.show()
    }

    private fun updateColor(colorPosition: Int){
        val color = when (colorPosition){
            0 -> ContextCompat.getColor(this, R.color.colorCardCoral)
            1 -> ContextCompat.getColor(this, R.color.colorCardTeal)
            2 -> ContextCompat.getColor(this, R.color.colorCardGreen)
            else -> ContextCompat.getColor(this, R.color.colorCardGreen)
        }
        Log.e("color is:", color.toString())
        colorBtn.setTextColor(color)
        detailNoteCard.setCardBackgroundColor(color)
        editNoteColor = color
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
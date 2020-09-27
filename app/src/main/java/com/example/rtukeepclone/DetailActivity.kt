package com.example.rtukeepclone

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.rtukeepclone.MainActivity.Companion.EXTRA_ID
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val requestFrom = intent.getStringExtra(EXTRA_ID) ?: "DetailActivity"

        if (requestFrom == MAIN_ACTIVITY){
            saveBtn.setOnClickListener { saveNewNote() }
        } else {
            saveBtn.setOnClickListener{ updateNote() }
        }

    }

    private fun saveNewNote(){
        val newNote = NoteItem(noteEditSubject.text.toString(), noteEditBodyText.text.toString())

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
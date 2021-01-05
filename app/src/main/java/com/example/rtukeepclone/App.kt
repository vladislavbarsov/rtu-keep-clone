package com.example.rtukeepclone

import android.app.Application
import androidx.room.Room

class App : Application() {
    val database: NoteDatabase by lazy {
        Room.databaseBuilder(this, NoteDatabase::class.java, "notes-db")
            .allowMainThreadQueries()
            .build()
    }
}
package com.example.rtukeepclone

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(version = 1, entities = [NoteItem::class])
abstract class NoteDatabase : RoomDatabase(){
    abstract fun noteItemDao(): NoteItemDao
}

object Database {
    private var instance: NoteDatabase? = null

    fun getInstance(context: Context) = instance ?: Room.databaseBuilder(
        context.applicationContext, NoteDatabase::class.java, "notes-db"
    )
        .allowMainThreadQueries()
        .build()
        .also { instance = it }
}
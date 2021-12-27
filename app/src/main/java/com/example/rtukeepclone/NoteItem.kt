package com.example.rtukeepclone

import androidx.room.*


@Entity(tableName = "note_item")
data class NoteItem (
    val noteTitle: String,
    val noteText: String,
    val noteColor: Int,
    @PrimaryKey(autoGenerate = true) var uid: Long = 0
)

@Dao
interface NoteItemDao {
    @Query("SELECT * FROM note_item")
    fun getAllNotes(): List<NoteItem>

    @Query("SELECT * FROM note_item WHERE uid = :noteId")
    fun getNoteById(noteId: Long): NoteItem

    @Insert
    fun insertAllNotes(vararg notes: NoteItem): List<Long>

    @Update
    fun updateNote(note: NoteItem)

    @Delete
    fun deleteNote(note: NoteItem)
}
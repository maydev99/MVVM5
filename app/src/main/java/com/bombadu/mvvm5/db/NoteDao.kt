package com.bombadu.mvvm5.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NoteDao {

    @Insert
    fun insert(note: Note)

    @Query("DELETE FROM notes_table")
    fun deleteAllNotes()

    @Delete
    fun delete(note: Note)


    @Query("SELECT * FROM notes_table ORDER BY priority DESC")
    fun getAllNotes(): LiveData<List<Note>>
}
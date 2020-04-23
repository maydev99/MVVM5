package com.bombadu.mvvm5.repository

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.bombadu.mvvm5.db.Note
import com.bombadu.mvvm5.db.NoteDao


class NoteRepository(private val noteDao: NoteDao) {

    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()


    fun insert(note: Note){
        InsertNoteAsyncTask(
            noteDao
        ).execute(note)
    }

    fun deleteAllNotes() {
        DeleteAllNotesAsyncTask(
            noteDao
        ).execute()
    }

    fun delete(note: Note) {
        DeleteNoteAsyncTask(
            noteDao
        ).execute(note)
    }


    private class InsertNoteAsyncTask(val noteDao: NoteDao) : AsyncTask<Note, Unit, Unit>() {

        override fun doInBackground(vararg note: Note?) {
            noteDao.insert(note[0]!!)

        }
    }


    private class DeleteAllNotesAsyncTask(val noteDao: NoteDao) : AsyncTask<Unit, Unit, Unit>() {

        override fun doInBackground(vararg p0: Unit?) {
            noteDao.deleteAllNotes()
        }
    }

    private class DeleteNoteAsyncTask(val noteDao: NoteDao) : AsyncTask<Note, Unit, Unit>() {

        override fun doInBackground(vararg note: Note?) {
            noteDao.delete(note[0]!!)
        }
    }





}
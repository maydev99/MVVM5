package com.bombadu.mvvm5.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.bombadu.mvvm5.db.Note
import com.bombadu.mvvm5.db.NoteDatabase
import com.bombadu.mvvm5.repository.NoteRepository
import kotlinx.coroutines.launch


class NoteViewModel (application: Application) : AndroidViewModel(application) {

    private val repository: NoteRepository
    private var allNotes: LiveData<List<Note>>

    init {
        val noteDao = NoteDatabase.getDatabase(application, viewModelScope).noteDao()
        repository = NoteRepository(noteDao)
        allNotes = repository.allNotes
    }




    fun insert(note: Note) = viewModelScope.launch  {
        repository.insert(note)
    }

    fun deleteAllNotes(){
        repository.deleteAllNotes()
    }

    fun deleteNote(note: Note){
        repository.delete(note)
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return allNotes
    }


}
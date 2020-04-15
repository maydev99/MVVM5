package com.bombadu.mvvm5.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bombadu.mvvm5.db.Note
import com.bombadu.mvvm5.repository.NoteRepository


open class NoteViewModel(private var repository: NoteRepository) : ViewModel() {

    private var allNotes: LiveData<List<Note>> = repository.getNotes

    fun insert(note: Note) {
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
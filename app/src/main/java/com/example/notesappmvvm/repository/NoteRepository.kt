package com.example.notesappmvvm.repository

import androidx.lifecycle.LiveData
import com.example.notesappmvvm.database.NotesDao
import com.example.notesappmvvm.model.Note

class NoteRepository(private val noteDao : NotesDao) {

    val allNotes : LiveData<List<Note>> = noteDao.getAllNotes()

    suspend fun insert(note: Note){
        noteDao.insert(note)
    }

    suspend fun update(note: Note){
        noteDao.update(note)
    }

    suspend fun delete(note: Note){
        noteDao.delete(note)
    }
}
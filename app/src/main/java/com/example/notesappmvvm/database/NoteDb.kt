package com.example.notesappmvvm.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notesappmvvm.model.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDb : RoomDatabase(){

    abstract fun noteDao() : NotesDao

    companion object{
        private var INSTANCE : NoteDb? = null

        fun getDatabase(context: Context) : NoteDb {

            if (INSTANCE == null){

                synchronized(this){
                    INSTANCE = Room.databaseBuilder(context, NoteDb::class.java, "noteDB").build()
                }
            }
            return INSTANCE!!
        }
    }
}
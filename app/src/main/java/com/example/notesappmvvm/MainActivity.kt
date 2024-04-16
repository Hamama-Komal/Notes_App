package com.example.notesappmvvm

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesappmvvm.adapter.NoteAdapter
import com.example.notesappmvvm.adapter.NoteClickDeleteInterface
import com.example.notesappmvvm.adapter.NoteClickInterface
import com.example.notesappmvvm.databinding.ActivityMainBinding
import com.example.notesappmvvm.model.Note
import com.example.notesappmvvm.viewModel.NoteViewModel

class MainActivity : AppCompatActivity(), NoteClickInterface, NoteClickDeleteInterface
    {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.noteRv.layoutManager = LinearLayoutManager(this)

        val noteRVAdapter = NoteAdapter(this, this, this)
        binding.noteRv.adapter = noteRVAdapter

        // initializing our view modal.
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteViewModel::class.java]

        viewModel.allNotes.observe(this, Observer { list ->
            list?.let {
                // updating our list.
                noteRVAdapter.updateList(it)
            }
        })


        binding.addNoteBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
            startActivity(intent)

        }


    }

        override fun onDeleteIconClick(note: Note) {

            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("Confirm Deletion")
            alertDialogBuilder.setMessage("Are you sure you want to delete ${note.noteTitle}?")
            alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
                // User clicked Yes, delete the note
                viewModel.deleteNote(note)
                Toast.makeText(this, "${note.noteTitle} Deleted", Toast.LENGTH_LONG).show()
            }
            alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
                // User clicked No, do nothing or handle accordingly
                dialog.dismiss()
            }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()

        }

        override fun onNoteClick(note: Note) {
            val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
            intent.putExtra("noteType", "Edit")
            intent.putExtra("noteTitle", note.noteTitle)
            intent.putExtra("noteDescription", note.noteDescription)
            intent.putExtra("noteId", note.id)
            startActivity(intent)

        }

    }

package com.bombadu.mvvm5

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bombadu.mvvm5.db.Note
import com.bombadu.mvvm5.model.NoteViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var noteViewModel: NoteViewModel
    private val adapter = NoteAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()



        noteViewModel.getAllNotes().observe(this,
            Observer { list ->
                list?.let {
                    adapter.setNotes(it)
                }
            })


        button_add_note.setOnClickListener {
            startActivityForResult(
                Intent(this, AddEditNoteActivity::class.java),
                ADD_NOTE_REQUEST
            )

        }
    }

    private fun setupRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
        recycler_view.adapter = adapter
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        noteViewModel.getAllNotes().observe(this, Observer { allNotes ->
            allNotes?.let { adapter.setNotes(it) }
        })

        //adapter.setItemClickCallback(this@MainActivity)

        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.getNoteAt(viewHolder.adapterPosition)?.let { noteViewModel.deleteNote(it) }
                Toast.makeText(this@MainActivity, "Note Deleted", Toast.LENGTH_SHORT).show()
            }
        }).attachToRecyclerView(recycler_view)


        //adapter.setItemClickCallback(this@MainActivity)
        adapter.onItemClick = { pos, _ ->
            //Log.i("POSITION", "Position: $pos")
            val myNote = adapter.getNoteAt(pos)

            intent = Intent(this, AddEditNoteActivity::class.java)
            intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, myNote!!.title)
            intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION, myNote.description)
            intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY, myNote.priority)
            intent.putExtra(AddEditNoteActivity.EXTRA_ID, myNote.id)
            startActivityForResult(intent, EDIT_NOTE_REQUEST)

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_all_notes -> {
                noteViewModel.deleteAllNotes()

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_NOTE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val newNote = Note(
                data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE),
                data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION),
                data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1)

            )

            noteViewModel.insert(newNote)

            Toast.makeText(this, "Note saved!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Note not saved!", Toast.LENGTH_SHORT).show()
        }

        if (requestCode == EDIT_NOTE_REQUEST && resultCode == Activity.RESULT_OK) {
            val id  = data?.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1)
            if (id == -1) {
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show()
                return
            }

            val updatedTitle = data?.getStringExtra(AddEditNoteActivity.EXTRA_TITLE)
            val updatedDescription = data?.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION)
            val updatedPriority = data?.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1)
            val updatedNote = Note(updatedTitle!!, updatedDescription!!, updatedPriority!!)
            if (id != null) {
                updatedNote.id = id
            }
            noteViewModel.update(updatedNote)
            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val ADD_NOTE_REQUEST = 1
        private const val EDIT_NOTE_REQUEST = 2
    }

}

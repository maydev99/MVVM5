package com.bombadu.mvvm5

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_note.*

class AddEditNoteActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TITLE ="com.bombadu.mvvm4.EXTRA_TITLE"
        const val EXTRA_DESCRIPTION ="com.bombadu.mvvm4.EXTRA_DESCRIPTION"
        const val EXTRA_PRIORITY ="com.bombadu.mvvm4.EXTRA_PRIORITY"
        const val EXTRA_ID ="com.bombadu.mvvm4.EXTRA_ID"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        title = "Add Note"
        number_picker_priority.minValue = 1
        number_picker_priority.maxValue = 10

        val intent = intent
        if (intent.hasExtra(EXTRA_ID)) {
            title = "Edit Note"

        }




    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_note_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save_note -> {
                saveNote()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun saveNote() {
        if (edit_text_title.text.toString().trim().isBlank() || edit_text_description.text.toString().trim().isBlank()) {
            Toast.makeText(this, "Can not insert empty note", Toast.LENGTH_SHORT).show()
            return
        }

        val data = Intent().apply {
            val priority = number_picker_priority.value
            putExtra(EXTRA_TITLE, edit_text_title.text.toString())
            putExtra(EXTRA_DESCRIPTION, edit_text_description.text.toString())
            putExtra(EXTRA_PRIORITY, priority)

        }

        setResult(Activity.RESULT_OK, data)
        finish()
    }
}
package com.bombadu.mvvm5

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bombadu.mvvm5.db.Note
import kotlinx.coroutines.withContext


class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteHolder>() {

    private var notes: List<Note> = ArrayList()
    private var itemClickCallback: ItemClickCallback? = null
    //private var listener: AdapterView.OnItemClickListener? = null
    var onItemClick: ((pos: Int, view: View) -> Unit)? = null


    internal interface ItemClickCallback {
        fun onItemClick(p: Int)
    }

    internal fun setItemClickCallback(inItemClickCallback: ItemClickCallback) {
        this.itemClickCallback = inItemClickCallback
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.NoteHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)
        return NoteHolder(itemView)
    }

    override fun getItemCount(): Int {
        return  notes.size
    }

    override fun onBindViewHolder(holder: NoteAdapter.NoteHolder, position: Int) {
        val currentNote = notes[position]
        holder.textViewTitle.text = currentNote.title
        holder.textViewDescription.text = currentNote.description
        holder.textViewPriority.text = ("${currentNote.priority}")



    }

    fun getNoteAt(position: Int): Note? {
        return notes[position]
    }



    fun setNotes(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }



    inner class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        override fun onClick(v: View) {
            onItemClick?.invoke(adapterPosition, v)
        }
        //val tvTitle = itemView.findViewById<TextView>(R.id.tv_cat_title)

        init {
            itemView.setOnClickListener(this)
        }

        var textViewTitle: TextView = itemView.findViewById(R.id.text_view_title)
        var textViewDescription: TextView = itemView.findViewById(R.id.text_view_description)
        var textViewPriority: TextView = itemView.findViewById(R.id.text_view_priority)






        /*init {
            itemView.setOnClickListener {
                val noteItem: View = itemView.findViewById(R.id.noteItem)
                noteItem.setOnClickListener(this)
            }
        }*/

        /*override fun onClick(view: View?) {
            if(view!!.id == R.id.noteItem) {
                itemClickCallback!!.onItemClick(adapterPosition)
            }

        }*/


    }

}






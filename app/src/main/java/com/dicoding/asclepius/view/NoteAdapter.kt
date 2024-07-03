package com.dicoding.asclepius.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.asclepius.database.Note
import com.dicoding.asclepius.databinding.ItemRowHistoryBinding
import com.dicoding.asclepius.helper.NoteDiffCallback

class NoteAdapter(private val onItemClick: (Note) -> Unit) :
    RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    private val listNotes = ArrayList<Note>()

    fun setListNotes(listNotes: List<Note>) {
        val diffCallback = NoteDiffCallback(this.listNotes, listNotes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listNotes.clear()
        this.listNotes.addAll(listNotes)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding =
            ItemRowHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(listNotes[position])
        val ivDetele = holder.binding.btnDelete

        ivDetele.setOnClickListener {
            onItemClick(listNotes[position])
        }


    }

    override fun getItemCount(): Int {
        return listNotes.size
    }

    inner class NoteViewHolder(val binding: ItemRowHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            with(binding) {
                tvDate.text = note.date
                tvResult.text = note.textResult
                Glide.with(binding.root)
                    .load(note.imageUri)
                    .into(binding.tvImg)
            }
        }
    }
}
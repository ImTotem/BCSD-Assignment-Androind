package io.github.imtotem.bcsd_assignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.imtotem.bcsd_assignment.databinding.ItemWordBinding
import io.github.imtotem.bcsd_assignment.db.Word

class WordAdapter(private val context: ItemClickListener) :
    ListAdapter<Word, WordAdapter.WordViewHolder>(object : DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean = oldItem === newItem

        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean = oldItem == newItem

    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder =
        WordViewHolder(ItemWordBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word = currentList[position]
        holder.bind(word)
        holder.itemView.setOnClickListener { context.onClick(word) }
    }

    inner class WordViewHolder(private val binding: ItemWordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Word) {
            with(binding) {
                textTextView.text = item.text
                meanTextView.text = item.mean
            }
        }
    }

    interface ItemClickListener {
        fun onClick(word: Word)
    }
}
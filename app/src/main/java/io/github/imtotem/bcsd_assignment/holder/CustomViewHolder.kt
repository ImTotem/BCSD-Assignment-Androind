package io.github.imtotem.bcsd_assignment.holder

import androidx.recyclerview.widget.RecyclerView
import io.github.imtotem.bcsd_assignment.databinding.ItemListViewBinding
import io.github.imtotem.bcsd_assignment.fragment.BaseFragment
import io.github.imtotem.bcsd_assignment.item.AlphabetItem
import io.github.imtotem.bcsd_assignment.item.ColorItem
import io.github.imtotem.bcsd_assignment.item.NumberItem
import io.github.imtotem.bcsd_assignment.item.ViewItems

class CustomViewHolder(private val binding: ItemListViewBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ViewItems) {
        with (binding) {
            when ( item ) {
                is ColorItem -> itemTextView.setBackgroundResource(item.color)
                is NumberItem -> itemTextView.text = item.number.toString()
                is AlphabetItem -> itemTextView.text = item.alphabet.toString()
            }
        }
    }
}
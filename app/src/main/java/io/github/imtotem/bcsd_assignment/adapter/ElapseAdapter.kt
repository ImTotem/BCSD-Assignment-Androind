package io.github.imtotem.bcsd_assignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.imtotem.bcsd_assignment.databinding.ItemElapsedListViewBinding
import io.github.imtotem.bcsd_assignment.item.ElapseData

class ElapseAdapter(private val list: List<ElapseData>) :
    RecyclerView.Adapter<ElapseAdapter.ElapseViewHolder>() {

    inner class ElapseViewHolder(private val binding: ItemElapsedListViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(elapseData: ElapseData) {
            with(binding) {
                section.text = elapseData.section.toString().padStart(2, '0')
                interval.text = elapseData.interval
                elapse.text = elapseData.elapse
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElapseViewHolder =
        ElapseViewHolder(
            ItemElapsedListViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ElapseViewHolder, position: Int) =
        holder.bind(list[position])
}
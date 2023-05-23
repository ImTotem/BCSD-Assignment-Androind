package io.github.imtotem.bcsd_assignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.imtotem.bcsd_assignment.databinding.ItemListViewBinding
import io.github.imtotem.bcsd_assignment.holder.CustomViewHolder
import io.github.imtotem.bcsd_assignment.item.ViewItems

class CustomAdapter(private val list: List<ViewItems>): RecyclerView.Adapter<CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(ItemListViewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}
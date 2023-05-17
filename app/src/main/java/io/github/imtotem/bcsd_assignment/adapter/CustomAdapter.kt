package io.github.imtotem.bcsd_assignment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.github.imtotem.bcsd_assignment.R

class CustomAdapter(private val itemList: MutableList<String>, private val listener: Context) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.item_text_view_name)
    }

    interface OnClickListener {
        fun onClick(position: Int)
        fun onLongClick(position: Int)
    }

    private lateinit var setOnClickListener: OnClickListener
    private lateinit var setOnLongClickListener: OnClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_name_list_view, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = itemList[position]
        holder.itemView.setOnClickListener {
            setOnClickListener = listener as OnClickListener
            setOnClickListener.onClick(position)
        }

        holder.itemView.setOnLongClickListener {
            setOnLongClickListener = listener as OnClickListener
            setOnLongClickListener.onLongClick(position)
            false
        }
    }

    override fun getItemCount(): Int = itemList.size
}
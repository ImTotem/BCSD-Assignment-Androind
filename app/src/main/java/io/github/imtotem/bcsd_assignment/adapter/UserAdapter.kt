package io.github.imtotem.bcsd_assignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.imtotem.bcsd_assignment.databinding.ItemUserBinding
import io.github.imtotem.bcsd_assignment.model.User

class UserAdapter(val onClick: (User) -> Unit): ListAdapter<User, UserAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ViewHolder {
        return ViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ViewHolder(private val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            with (binding) {
                userNameTextView.text = user.username
                root.setOnClickListener {
                    onClick(user)
                }
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean = oldItem == newItem

        }
    }
}
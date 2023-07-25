package io.github.imtotem.bcsd_assignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.imtotem.bcsd_assignment.databinding.ItemRepoBinding
import io.github.imtotem.bcsd_assignment.model.Repo


class RepoAdapter(val onClick: (Repo) -> Unit): ListAdapter<Repo, RepoAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoAdapter.ViewHolder {
        return ViewHolder(ItemRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RepoAdapter.ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ViewHolder(private val binding: ItemRepoBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(repo: Repo) {
            with (binding) {
                nameTextView.text = repo.name
                descriptionTextView.text = repo.description
                starTextView.text = repo.starCount.toString()
                forkTextView.text = repo.forkCount.toString()

                root.setOnClickListener {
                    onClick(repo)
                }
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean = oldItem == newItem

        }
    }
}
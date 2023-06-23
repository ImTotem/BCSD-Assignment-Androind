package io.github.imtotem.bcsd_assignment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.github.imtotem.bcsd_assignment.R
import io.github.imtotem.bcsd_assignment.databinding.ItemMusicBinding
import io.github.imtotem.bcsd_assignment.item.Music
import io.github.imtotem.bcsd_assignment.utils.setDurationFormat

class MusicAdapter(private val list: List<Music>, val context: Context) :
    RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        return MusicViewHolder(
            ItemMusicBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class MusicViewHolder(private val binding: ItemMusicBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(music: Music) {
            with(binding) {
                titleTextView.text = music.title
                durationTextView.text = setDurationFormat(music.duration)

                Glide.with(context)
                    .load(R.drawable.ic_launcher_foreground)
//                    .load(getAlbumUri(music.albumId))
//                    .placeholder(R.drawable.ic_launcher_foreground)
//                    .error(R.drawable.ic_launcher_foreground)
//                    .fallback(R.drawable.ic_launcher_foreground)
                    .into(albumImageView)
            }
        }
    }
}
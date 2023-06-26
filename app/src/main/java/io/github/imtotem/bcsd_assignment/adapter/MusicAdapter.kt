package io.github.imtotem.bcsd_assignment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.github.imtotem.bcsd_assignment.R
import io.github.imtotem.bcsd_assignment.databinding.ItemMusicBinding
import io.github.imtotem.bcsd_assignment.item.MusicItem
import io.github.imtotem.bcsd_assignment.utils.pixelsEqualTo

class MusicAdapter(private val list: List<MusicItem>, val context: Context) :
    RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {

    private lateinit var itemResumeClickListener: ItemOnClickListener
    private lateinit var itemStopClickListener: ItemOnClickListener

    interface ItemOnClickListener {
        fun onResumeMusic(musicItem: MusicItem)
        fun onStopMusic(musicItem: MusicItem)
    }

    fun setResumeClickListener(listener: ItemOnClickListener) {
        itemResumeClickListener = listener
    }

    fun setStopClickListener(listener: ItemOnClickListener) {
        itemStopClickListener = listener
    }

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
        fun bind(musicItem: MusicItem) {
            with(binding) {
                titleTextView.text = musicItem.title
                durationTextView.text = musicItem.duration

                Glide.with(context)
                    .load(R.drawable.ic_launcher_foreground)
                    .into(albumImageView)

                resumeButton.setOnClickListener {

                    setResumeClickListener(context as ItemOnClickListener)
                    itemResumeClickListener.onResumeMusic(musicItem)

                    if (resumeButton.drawable.pixelsEqualTo(
                            ContextCompat.getDrawable(
                                context,
                                R.drawable.baseline_pause_24
                            )
                        )
                    ) {
                        resumeButton.setImageResource(R.drawable.baseline_play_arrow_24)
                    } else {
                        resumeButton.setImageResource(R.drawable.baseline_pause_24)
                    }
                }

                stopButton.setOnClickListener {
                    setStopClickListener(context as ItemOnClickListener)
                    itemStopClickListener.onStopMusic(musicItem)
                }
            }
        }
    }
}
package io.github.imtotem.bcsd_assignment

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore.Audio.Media
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.imtotem.bcsd_assignment.adapter.MusicAdapter
import io.github.imtotem.bcsd_assignment.base.BaseActivity
import io.github.imtotem.bcsd_assignment.databinding.ActivityMainBinding
import io.github.imtotem.bcsd_assignment.item.MusicItem
import io.github.imtotem.bcsd_assignment.permission.Permission
import io.github.imtotem.bcsd_assignment.utils.Constants
import io.github.imtotem.bcsd_assignment.utils.setDurationFormat

class MainActivity : BaseActivity<ActivityMainBinding>(), MusicAdapter.ItemOnClickListener {

    override val layoutId: Int
        get() = R.layout.activity_main

    companion object {
        lateinit var permission: Permission
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onStart() {
        super.onStart()
        permission = Permission(this)

        if (permission.checkPermission(Manifest.permission.READ_MEDIA_AUDIO)) adaptRecyclerView()
    }

    override fun initView() {
    }

    override fun initEvent() {
        with(binding) {
            requestPermissionTextView.setOnClickListener {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", packageName, null)
                }
                startActivity(intent)
            }
        }
    }

    private fun adaptRecyclerView() {
        val adapter = MusicAdapter(getMusicList(), this)
        with(binding.recyclerViewContainer) {
            this.adapter = adapter
            this.layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun getMusicList(): MutableList<MusicItem> {
        val listUrl = Media.EXTERNAL_CONTENT_URI

        val proj = arrayOf(
            Media._ID,
            Media.TITLE,
            Media.ARTIST,
            Media.ALBUM_ID,
            Media.DURATION
        )

        val cursor = contentResolver.query(listUrl, proj, null, null, null)
        val musicItemList = mutableListOf<MusicItem>()
        while (cursor?.moveToNext() == true) {
            with(cursor) {
                val id = getLong(0)
                val title = getString(1)
                val artist = getString(2)
                val albumId = getLong(3)
                val duration = getLong(4)

                val musicItem = MusicItem(id, title, artist, albumId, setDurationFormat(duration))
                musicItemList.add(musicItem)
            }
        }

        return musicItemList
    }

    override fun onResumeMusic(musicItem: MusicItem) {
        startService(createServiceIntent(Constants.MUSIC_RESUME, musicItem))
    }

    override fun onStopMusic(musicItem: MusicItem) {
        startService(createServiceIntent(Constants.MUSIC_STOP, null))
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(serviceIntent())
    }

}
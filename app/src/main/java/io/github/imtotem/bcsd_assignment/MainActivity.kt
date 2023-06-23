package io.github.imtotem.bcsd_assignment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Media
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.imtotem.bcsd_assignment.adapter.MusicAdapter
import io.github.imtotem.bcsd_assignment.base.BaseActivity
import io.github.imtotem.bcsd_assignment.databinding.ActivityMainBinding
import io.github.imtotem.bcsd_assignment.item.Music

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val layoutId: Int
        get() = R.layout.activity_main

    private val requestReadPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            adaptRecyclerView()
            binding.requestPermissionTextView.visibility = View.INVISIBLE
        } else {
            binding.requestPermissionTextView.visibility = View.VISIBLE
        }
    }

    override fun onStart() {
        super.onStart()
        checkPermission()
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

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    this@MainActivity, Manifest.permission.READ_MEDIA_AUDIO
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestReadPermission.launch(Manifest.permission.READ_MEDIA_AUDIO)
            } else {
                adaptRecyclerView()
                binding.requestPermissionTextView.visibility = View.INVISIBLE
            }
        }
    }

    private fun adaptRecyclerView() {
        val adapter = MusicAdapter(getMusicList(), this)
        binding.recyclerViewContainer.adapter = adapter
        binding.recyclerViewContainer.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun getMusicList(): MutableList<Music> {
        val listUrl = Media.EXTERNAL_CONTENT_URI

        val proj = arrayOf(Media._ID, Media.TITLE, Media.ARTIST, Media.ALBUM_ID, Media.DURATION)

        val cursor = contentResolver.query(listUrl, proj, null, null, null)
        val musicList = mutableListOf<Music>()
        while (cursor?.moveToNext() == true) {
            with(cursor) {
                val id = getLong(0)
                val title = getString(1)
                val artist = getString(2)
                val albumId = getLong(3)
                val duration = getLong(4)

                val music = Music(id, title, artist, albumId, duration)
                Log.d("music", music.toString())
                musicList.add(music)
            }
        }

        return musicList
    }
}
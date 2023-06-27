package io.github.imtotem.bcsd_assignment.base

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import io.github.imtotem.bcsd_assignment.item.MusicItem
import io.github.imtotem.bcsd_assignment.service.MainService

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {
    @get:LayoutRes
    abstract val layoutId: Int
    private lateinit var _binding: T

    val binding: T
        get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, layoutId)
        setContentView(binding.root)
        initState()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }

    abstract fun initView()
    abstract fun initEvent()

    open fun initState() {
        initView()
        initEvent()
    }

    fun serviceIntent(): Intent = Intent(this, MainService::class.java)

    fun createServiceIntent(action: String, musicItem: MusicItem?): Intent =
        serviceIntent().apply {
            this.action = action
            if (musicItem != null) putExtra("musicItem", musicItem)
        }

    fun isPermissionGranted(perm: String): Boolean =
        ActivityCompat.checkSelfPermission(this, perm) == PackageManager.PERMISSION_GRANTED
}
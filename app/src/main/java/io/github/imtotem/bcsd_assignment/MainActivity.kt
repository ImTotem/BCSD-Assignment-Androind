package io.github.imtotem.bcsd_assignment

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.imtotem.bcsd_assignment.adapter.ElapseAdapter
import io.github.imtotem.bcsd_assignment.base.BaseActivity
import io.github.imtotem.bcsd_assignment.viewmodel.StopWatchViewModel
import io.github.imtotem.bcsd_assignment.databinding.ActivityMainBinding
import io.github.imtotem.bcsd_assignment.enums.State
import io.github.imtotem.bcsd_assignment.ext.format
import io.github.imtotem.bcsd_assignment.ext.setColor
import io.github.imtotem.bcsd_assignment.item.ElapseData

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private lateinit var elapseAdapter: ElapseAdapter
    private val elapseList = mutableListOf<ElapseData>()

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun initView() {
        binding.stopWatch = ViewModelProvider(this)[StopWatchViewModel::class.java]

        binding.lifecycleOwner = this

        adaptRecyclerView()
    }

    override fun initEvent() {
        initLeftClickListener()
        initRightClickListener()
    }

    private fun adaptRecyclerView() {
        elapseAdapter = ElapseAdapter(elapseList)
        with(binding.recyclerViewContainer) {
            adapter = elapseAdapter
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            itemAnimator?.apply {
                addDuration = 100
                changeDuration = 100
                moveDuration = 100
            }
        }
    }

    private fun initLeftClickListener() = with(binding) {
        leftButton.setOnClickListener {
            when (stopWatch!!.state) {
                State.RUN -> {
                    elapseList.add(0, ElapseData(
                            elapseList.size+1,
                            stopWatch!!.interval.format(),
                            stopWatch!!.time.format()
                        )
                    )
                    elapseAdapter.notifyItemInserted(0)
                    recyclerViewContainer.scrollToPosition(0)
                }

                State.STOP -> {
                    stopWatch!!.reset()
                    val size = elapseList.size
                    elapseAdapter.notifyItemRangeRemoved(0, size)
                    elapseList.clear()
                    leftButton.isEnabled = false
                    leftButton.text = getString(R.string.elapse)
                }
                else -> {}
            }
        }
    }

    private fun initRightClickListener() = with(binding) {
        rightButton.setOnClickListener {

            rightButton.setColor(
                when (stopWatch!!.state) {
                    State.RUN -> {
                        stopWatch!!.stop()
                        R.color.seed
                    }

                    else -> {
                        stopWatch!!.start()
                        R.color.md_theme_light_error
                    }
                }
            )

            getString().run {
                leftButton.text = this.first
                rightButton.text = this.second
            }
        }
    }

    private fun getString(): Pair<String, String> = when (binding.stopWatch!!.state) {
        State.RUN -> {
            binding.leftButton.isEnabled = true
            listOf(R.string.elapse, R.string.stop)
        }

        State.STOP -> {
            listOf(R.string.reset, R.string.cont)
        }
        State.RESET -> listOf(R.string.elapse, R.string.start)

    }.map { getString(it) }.let { (a, b) -> a to b }
}
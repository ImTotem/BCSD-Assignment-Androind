package io.github.imtotem.bcsd_assignment

import io.github.imtotem.bcsd_assignment.base.BaseActivity
import io.github.imtotem.bcsd_assignment.databinding.ActivityRandomBinding

class RandomActivity : BaseActivity<ActivityRandomBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_random

    override fun initView() {
        with(binding) {
            val range = intent.getIntExtra("count", 0)
            val randomNumber = (0..range).random()

            rangeTextview.text = getString(R.string.range, range)
            randomTextview.text = randomNumber.toString()
        }
    }

    override fun initEvent() {}
}
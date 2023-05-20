package io.github.imtotem.bcsd_assignment.fragment

import io.github.imtotem.bcsd_assignment.R
import io.github.imtotem.bcsd_assignment.item.ColorItem
import io.github.imtotem.bcsd_assignment.item.ViewItems

class RainbowFragment : BaseFragment() {

    override fun setItemList(){
        itemList = mutableListOf<ViewItems>().apply {
            add(ColorItem(R.color.red))
            add(ColorItem(R.color.orange))
            add(ColorItem(R.color.yellow))
            add(ColorItem(R.color.green))
            add(ColorItem(R.color.blue))
            add(ColorItem(R.color.indigo))
            add(ColorItem(R.color.violet))
        }
    }
}
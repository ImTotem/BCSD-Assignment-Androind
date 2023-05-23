package io.github.imtotem.bcsd_assignment.fragment

import io.github.imtotem.bcsd_assignment.item.NumberItem
import io.github.imtotem.bcsd_assignment.item.ViewItems

class NumberFragment : BaseFragment() {

    override fun setItemList() {
        itemList = mutableListOf<ViewItems>().apply {
            for ( i in 1..10 ) {
                add(NumberItem(i))
            }
        }
    }
}
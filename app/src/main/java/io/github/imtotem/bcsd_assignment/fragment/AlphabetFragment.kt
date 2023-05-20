package io.github.imtotem.bcsd_assignment.fragment

import io.github.imtotem.bcsd_assignment.item.AlphabetItem
import io.github.imtotem.bcsd_assignment.item.ViewItems

class AlphabetFragment : BaseFragment() {

    override fun setItemList(){
        itemList = mutableListOf<ViewItems>().apply {
            for (i in 97 .. 122) {
                add(AlphabetItem(i.toChar()))
            }
        }
    }
}
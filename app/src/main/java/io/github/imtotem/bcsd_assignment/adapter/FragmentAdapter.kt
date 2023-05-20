package io.github.imtotem.bcsd_assignment.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import io.github.imtotem.bcsd_assignment.enums.Fragments
import io.github.imtotem.bcsd_assignment.fragment.AlphabetFragment
import io.github.imtotem.bcsd_assignment.fragment.NumberFragment
import io.github.imtotem.bcsd_assignment.fragment.RainbowFragment

class FragmentAdapter(fragment: FragmentActivity): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = PAGES

    override fun createFragment(position: Int): Fragment =
        when(position) {
            Fragments.RAINBOW.id -> RainbowFragment()
            Fragments.NUMBER.id -> NumberFragment()
            Fragments.ALPHABET.id -> AlphabetFragment()
            else ->  throw Exception()
        }

    companion object {
        const val PAGES = 3
    }

}
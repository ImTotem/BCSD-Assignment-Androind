package io.github.imtotem.bcsd_assignment.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory

class FragmentFactoryImpl(private val index: Int): FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className) {
            RandomFragment::class.java.name -> RandomFragment(index)
            AlertDialogFragment::class.java.name -> AlertDialogFragment()
            else -> return super.instantiate(classLoader, className)
        }
    }
}
package io.github.imtotem.bcsd_assignment.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.github.imtotem.bcsd_assignment.adapter.CustomAdapter
import io.github.imtotem.bcsd_assignment.databinding.FragmentBaseBinding
import io.github.imtotem.bcsd_assignment.item.ViewItems

abstract class BaseFragment: Fragment() {

    private lateinit var binding: FragmentBaseBinding
    private lateinit var customAdapter: CustomAdapter
    lateinit var itemList: MutableList<ViewItems>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setItemList()

        customAdapter = CustomAdapter(itemList)
        binding.viewPager2.adapter = customAdapter
    }

    abstract fun setItemList()
}
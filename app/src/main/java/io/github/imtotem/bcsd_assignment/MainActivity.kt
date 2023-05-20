package io.github.imtotem.bcsd_assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import io.github.imtotem.bcsd_assignment.adapter.FragmentAdapter
import io.github.imtotem.bcsd_assignment.databinding.ActivityMainBinding
import io.github.imtotem.bcsd_assignment.enums.Fragments

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val fragmentAdapter by lazy {
        FragmentAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            viewPager2.adapter = fragmentAdapter
//            viewPager2.isUserInputEnabled = false

            bottomNavigationView.setOnItemSelectedListener {
                when(it.itemId) {
                    R.id.item_rainbow -> viewPager2.currentItem = Fragments.RAINBOW.id
                    R.id.item_number -> viewPager2.currentItem = Fragments.NUMBER.id
                    R.id.item_alphabet -> viewPager2.currentItem = Fragments.ALPHABET.id
                }
                true
            }

            viewPager2.registerOnPageChangeCallback(
                object:OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        bottomNavigationView.menu.getItem(position).isChecked = true
                    }
                }
            )
        }
    }

}
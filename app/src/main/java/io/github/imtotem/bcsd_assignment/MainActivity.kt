package io.github.imtotem.bcsd_assignment

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import io.github.imtotem.bcsd_assignment.databinding.ActivityMainBinding
import io.github.imtotem.bcsd_assignment.fragment.AlertDialogFragment
import io.github.imtotem.bcsd_assignment.fragment.FragmentFactoryImpl
import io.github.imtotem.bcsd_assignment.fragment.RandomFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var count = 0

    private lateinit var fragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        with(binding) {
            setCount(count)

            buttonCount.setOnClickListener {
                setCount(++count)
            }
        }

        initFactory()
    }

    private fun initFactory() {
        supportFragmentManager.fragmentFactory = FragmentFactoryImpl(count)

        with(binding) {
            buttonRandom.setOnClickListener {
                fragment = supportFragmentManager.fragmentFactory.instantiate(classLoader, RandomFragment::class.java.name)

                fragment.arguments = Bundle().apply {
                    putBundle(RandomFragment.TAG, bundleOf("range" to count))
                }

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_view_factory, fragment, RandomFragment.TAG)
                    .commit()
            }

            buttonToast.setOnClickListener {
                fragment = supportFragmentManager.fragmentFactory.instantiate(classLoader, AlertDialogFragment::class.java.name)

                (fragment as DialogFragment).show(supportFragmentManager, AlertDialogFragment.TAG)
            }
        }
    }

    fun onRandomClick(count: Int) {
        setCount(count)
    }

    fun onPositiveClick() {
        setCount(0)
    }

    private fun setCount(count: Int) {
        this.count = count
        binding.countTextview.text = count.toString()
    }

    fun onNeutralClick() {
        Toast.makeText(this@MainActivity, "Toast", Toast.LENGTH_SHORT).show()
    }
}
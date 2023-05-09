package io.github.imtotem.bcsd_assignment.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import io.github.imtotem.bcsd_assignment.MainActivity
import io.github.imtotem.bcsd_assignment.R
import io.github.imtotem.bcsd_assignment.databinding.FragmentRandomBinding

class RandomFragment(private val default: Int) : Fragment() {
    private var _binding: FragmentRandomBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRandomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val range = requireArguments().getBundle(TAG)?.getInt("range") ?: default

        Log.d("Range", "RandomFragment - $range")

        val randomNumber = (0..range).random()

        with (binding) {
            rangeTitle.text = getString(R.string.range, range)
            randomTextview.text = randomNumber.toString()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                (requireActivity() as MainActivity).onRandomClick(randomNumber)
                val fragment = parentFragmentManager.findFragmentByTag(TAG)
                if ( fragment != null ) {
                    parentFragmentManager.beginTransaction()
                        .remove(fragment)
                        .commit()
                }
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "random"
    }
}
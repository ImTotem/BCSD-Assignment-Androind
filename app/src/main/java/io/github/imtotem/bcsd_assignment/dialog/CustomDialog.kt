package io.github.imtotem.bcsd_assignment.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import io.github.imtotem.bcsd_assignment.databinding.DialogFragmentCustomDialogBinding
import io.github.imtotem.bcsd_assignment.ext.setWidthPercent

class CustomDialog : DialogFragment() {
    private lateinit var binding: DialogFragmentCustomDialogBinding

    interface OnEditTextNameListener {
        fun onChangedName(name: String)
    }

    lateinit var listener: OnEditTextNameListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFragmentCustomDialogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setWidthPercent(80)
        with(binding) {
            cancelButton.setOnClickListener {
                dismiss()
            }
            confirmButton.setOnClickListener {
                if (editTextEditName.text.isNotEmpty()) {
                    listener = context as OnEditTextNameListener
                    listener.onChangedName(editTextEditName.text.toString())
                    dismiss()
                } else {
                    Toast.makeText(it.context, "수정할 이름을 입력해주세요", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        const val TAG = "Custom Dialog"

        var position: Int? = null
        fun newInstance(pos: Int): CustomDialog {
            val args = Bundle()
            position = pos
            val fragment = CustomDialog()
            fragment.arguments = args
            return fragment
        }
    }
}
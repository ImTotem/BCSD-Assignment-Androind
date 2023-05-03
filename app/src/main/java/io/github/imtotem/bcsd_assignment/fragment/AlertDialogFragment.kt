package io.github.imtotem.bcsd_assignment.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import io.github.imtotem.bcsd_assignment.MainActivity
import io.github.imtotem.bcsd_assignment.R

class AlertDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return requireActivity().let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                it as MainActivity

                isCancelable = false
                setTitle(R.string.alert_dialog_title)

                setPositiveButton(R.string.positive) { _, _ ->
                    it.onPositiveClick()
                    dismiss()
                }

                setNeutralButton(R.string.neutral) { _, _ ->
                    it.onNeutralClick()
                    dismiss()
                }

                setNegativeButton(R.string.negative) {_, _ ->
                    dismiss()
                }
            }

            builder.create()
        }
    }

    companion object {
        const val TAG = "dialog"
    }
}
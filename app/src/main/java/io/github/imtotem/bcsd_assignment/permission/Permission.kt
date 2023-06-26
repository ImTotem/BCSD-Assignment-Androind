package io.github.imtotem.bcsd_assignment.permission

import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import io.github.imtotem.bcsd_assignment.MainActivity

class Permission(private val activity: MainActivity) {
    private var result: Boolean = false

    private val requestReadPermission = with(activity) {
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                binding.requestPermissionTextView.visibility = View.INVISIBLE
                result = true
            } else {
                binding.requestPermissionTextView.visibility = View.VISIBLE
                result = false
            }
        }
    }

    fun checkPermission(permission: String): Boolean {
        result = false
        with(activity) {
            if ( isPermissionGranted(permission) ) {
                binding.requestPermissionTextView.visibility = View.INVISIBLE
                result = true
            } else {
                requestReadPermission.launch(permission)
            }
        }

        return result
    }
}
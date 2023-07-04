package io.github.imtotem.bcsd_assignment.ext

import android.widget.Button
import java.text.SimpleDateFormat
import java.util.*

fun Button.setColor(id: Int) = setBackgroundColor(resources.getColor(id, this.context.theme))

fun Long.format(): String = SimpleDateFormat("mm:ss.SS", Locale.getDefault()).format(this)
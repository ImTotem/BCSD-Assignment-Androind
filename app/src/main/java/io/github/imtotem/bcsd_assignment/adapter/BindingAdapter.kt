package io.github.imtotem.bcsd_assignment.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import io.github.imtotem.bcsd_assignment.db.Word

@BindingAdapter("text")
fun TextView.setText(word: Word?) {
    if (word == null) return
    text = word.text
}

@BindingAdapter("mean")
fun TextView.setMean(word: Word?) {
    if (word == null) return
    text = word.mean
}
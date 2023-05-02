package io.github.imtotem.bcsd_assignment

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.OnBackPressedCallback

class RandomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random)

        val rangeTextView: TextView = findViewById(R.id.range_textview)
        val randomTextview: TextView = findViewById(R.id.random_textview)

        val range = intent.getIntExtra("range", 0)
        val randomNumber = (0..range).random()

        rangeTextView.text = getString(R.string.range, range)
        randomTextview.text = randomNumber.toString()

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent().apply {
                    putExtra("num", randomNumber)
                }
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }

        onBackPressedDispatcher.addCallback( this, callback )
    }
}
package io.github.imtotem.bcsd_assignment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult

class MainActivity : AppCompatActivity() {
    private var count = 0
    private lateinit var countTextView: TextView

    private val randomActivity = registerForActivityResult( StartActivityForResult() ) {
        countTextView.text = it.data?.getIntExtra("num", 0).toString()
        count = countTextView.text.toString().toInt()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toastButton: Button = findViewById(R.id.button_toast)

        countTextView = findViewById(R.id.count_textview)
        val countButton: Button = findViewById(R.id.button_count)

        val randomButton: Button = findViewById(R.id.button_random)

        toastButton.setOnClickListener {
            Toast.makeText(this@MainActivity, "Toast", Toast.LENGTH_SHORT).show()
        }

        countButton.setOnClickListener {
            countTextView.text = (++count).toString()
        }

        randomButton.setOnClickListener {
            val intent = Intent(this, RandomActivity::class.java)
            intent.putExtra("range", count)

            randomActivity.launch(intent)
        }
    }
}
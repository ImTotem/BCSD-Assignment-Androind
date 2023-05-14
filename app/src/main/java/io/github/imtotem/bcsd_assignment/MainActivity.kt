package io.github.imtotem.bcsd_assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.imtotem.bcsd_assignment.adapter.CustomAdapter
import io.github.imtotem.bcsd_assignment.databinding.ActivityMainBinding
import io.github.imtotem.bcsd_assignment.dialog.CustomDialog

class MainActivity : AppCompatActivity(), CustomAdapter.OnClickListener, CustomDialog.OnEditTextNameListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var customAdapter: CustomAdapter
    private val nameList = mutableListOf<String>()
    private var rememberPosition = 0

    private lateinit var customDialog: CustomDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        customAdapter = CustomAdapter(nameList, this)

        with(binding) {
            with(recyclerViewContainer) {
                adapter = customAdapter
                layoutManager =
                    LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            }

            floatingAddActionButton.setOnClickListener {
                if (editTextInputName.text.isNotEmpty()) {
                    nameList.add(editTextInputName.text.toString())
                    customAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@MainActivity, "이름을 입력해주세요", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onChangedName(name: String) {
        nameList[rememberPosition] = name
        customAdapter.notifyItemChanged(rememberPosition)
        binding.editTextInputName.text = null
    }

    override fun onClick(position: Int) {
        AlertDialog.Builder(this)
            .setTitle("이름 목록 삭제하기")
            .setMessage("이름 목록을 삭제해보자.")
            .setPositiveButton("삭제") { _, _ ->
                nameList.removeAt(position)
                customAdapter.notifyItemRemoved(position)
            }
            .setNegativeButton("취소", null)
            .create()
            .show()
    }

    override fun onLongClick(position: Int) {
        customDialog = CustomDialog.newInstance(position)
        customDialog.show(supportFragmentManager, CustomDialog.TAG)
        rememberPosition = position
    }
}
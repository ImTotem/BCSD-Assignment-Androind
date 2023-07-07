package io.github.imtotem.bcsd_assignment

import android.content.Intent
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.imtotem.bcsd_assignment.adapter.WordAdapter
import io.github.imtotem.bcsd_assignment.base.BaseActivity
import io.github.imtotem.bcsd_assignment.databinding.ActivityMainBinding
import io.github.imtotem.bcsd_assignment.db.Word
import io.github.imtotem.bcsd_assignment.repository.WordRepositoryImpl
import io.github.imtotem.bcsd_assignment.util.Flag
import io.github.imtotem.bcsd_assignment.util.FlagResult
import io.github.imtotem.bcsd_assignment.viewmodel.WordViewModel

class MainActivity : BaseActivity<ActivityMainBinding>(), WordAdapter.ItemClickListener {
    override val layoutId: Int
        get() = R.layout.activity_main

    private val viewModel: WordViewModel by viewModels {
        WordViewModel.WordViewModelFactory(WordRepositoryImpl(application))
    }

    private lateinit var adapter: WordAdapter
    private lateinit var selectWord: Word

    private val addWordResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val isAddWord = result.data?.getBooleanExtra(Flag.ADD_WORD, false) ?: false
            if (result.resultCode == RESULT_OK && isAddWord) {
                with(viewModel) {
                    getLatestWord()
                    getAll()
                }
            }
        }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val updateWordResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val updateWord = result.data?.getParcelableExtra(Flag.UPDATE_WORD, Word::class.java)
            if (result.resultCode == RESULT_OK && updateWord != null) {
                with (viewModel) {
                    update(updateWord, adapter.currentList)
                    setWord(updateWord)
                }
            }
        }

    override fun initView() {
        binding.also {
            it.activity = this
            it.lifecycleOwner = this
            it.viewModel = viewModel
        }
        initRecyclerView()
    }

    override fun initEvent() {
        observeLiveData()
    }

    private fun initRecyclerView() {
        viewModel.getAll()
        adapter = WordAdapter(this)
        binding.wordRecyclerView.also {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(this)
            it.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        }
    }

    private fun observeLiveData() {
        viewModel.word.observe(this) {
            selectWord = it
        }
        viewModel.wordList.observe(this) {
            adapter.submitList(it.toList())
        }
    }

    fun addWord() {
        addWordResult.launch(Intent(this, AddActivity::class.java).also {
            it.putExtra(Flag.FLAG, FlagResult.ADD)
        })
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun updateWord() {
        updateWordResult.launch(Intent(this, AddActivity::class.java).also {
            it.putExtra(Flag.WORD, viewModel.word.value)
            it.putExtra(Flag.FLAG, FlagResult.UPDATE)
        })
    }

    fun deleteWord() {
        viewModel.delete(selectWord)
        with(binding) {
            textTextView.text = ""
            meanTextView.text = ""
        }
    }

    override fun onClick(word: Word) {
        viewModel.setWord(word)
    }
}
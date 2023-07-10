package io.github.imtotem.bcsd_assignment

import android.content.Intent
import android.os.Build
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import io.github.imtotem.bcsd_assignment.base.BaseActivity
import io.github.imtotem.bcsd_assignment.databinding.ActivityAddBinding
import io.github.imtotem.bcsd_assignment.db.Word
import io.github.imtotem.bcsd_assignment.repository.WordRepositoryImpl
import io.github.imtotem.bcsd_assignment.util.Flag
import io.github.imtotem.bcsd_assignment.util.FlagResult
import io.github.imtotem.bcsd_assignment.viewmodel.WordViewModel

class AddActivity : BaseActivity<ActivityAddBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_add

    private val viewModel: WordViewModel by viewModels {
        WordViewModel.WordViewModelFactory(WordRepositoryImpl(application))
    }

    private var previousWord: Word? = null


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun initView() {
        binding.also {
            it.activity = this
            it.lifecycleOwner = this
        }

        previousWord = intent?.getParcelableExtra(Flag.WORD, Word::class.java)
        previousWord?.let { word ->
            with(binding) {
                textTextInputEditText.setText(word.text)
                meanTextInputEditText.setText(word.mean)
            }
        }
    }

    override fun initEvent() {
    }

    fun addWord() {
        when (intent.getStringExtra(Flag.FLAG)) {
            FlagResult.ADD -> {
                with(binding) {
                    viewModel.insert(
                        Word(
                            textTextInputEditText.text.toString(),
                            meanTextInputEditText.text.toString()
                        )
                    )
                }
                setResult(RESULT_OK, Intent().putExtra(Flag.ADD_WORD, true))
            }

            FlagResult.UPDATE -> {
                with(binding) {
                    previousWord?.copy(
                        text = textTextInputEditText.text.toString(),
                        mean = meanTextInputEditText.text.toString()
                    ).apply {
                        setResult(RESULT_OK, Intent().putExtra(Flag.UPDATE_WORD, this))
                    }
                }
            }
        }

        finish()
    }
}
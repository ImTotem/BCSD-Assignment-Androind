package io.github.imtotem.bcsd_assignment.view

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.imtotem.bcsd_assignment.R
import io.github.imtotem.bcsd_assignment.adapter.UserAdapter
import io.github.imtotem.bcsd_assignment.base.BaseActivity
import io.github.imtotem.bcsd_assignment.databinding.ActivityMainBinding
import io.github.imtotem.bcsd_assignment.model.UserDTO
import io.github.imtotem.bcsd_assignment.network.ApiClient
import io.github.imtotem.bcsd_assignment.network.GithubService
import io.github.imtotem.bcsd_assignment.viewmodel.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_main

    private val viewModel: MainViewModel by viewModels()

    private lateinit var userAdapter: UserAdapter

    private val handler = Handler(Looper.getMainLooper())

    override fun initView() {
        binding.viewModel = viewModel

        initAdapter()
        initRecyclerView()
        setEditText()
    }

    override fun initEvent() {
    }

    private fun initAdapter() {
        userAdapter = UserAdapter { user ->
            val intent = Intent(this, RepoActivity::class.java)
            intent.putExtra(USER, user)
            startActivity(intent)
        }
    }

    private fun initRecyclerView() {
        binding.repoRecyclerView.also {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = userAdapter
        }
    }

    private fun setEditText() {
        val runnable = Runnable {
            searchUser()
        }

        with(binding) {
            searchEditText.addTextChangedListener {
                viewModel!!.searchText.value = it.toString()

                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 300)
            }
        }

    }

    private fun searchUser() {
        val githubService = ApiClient.retrofit.create(GithubService::class.java)

        githubService.searchUsers(viewModel.searchText.value.toString())
            .enqueue(object : Callback<UserDTO> {
                override fun onResponse(call: Call<UserDTO>, response: Response<UserDTO>) {
                    userAdapter.submitList(response.body()?.items)
                }

                override fun onFailure(call: Call<UserDTO>, t: Throwable) {
                    t.printStackTrace()
                    Toast.makeText(this@MainActivity, "ERROR", Toast.LENGTH_SHORT).show()
                }
            })
    }

    companion object {
        const val USER = "user"
    }
}
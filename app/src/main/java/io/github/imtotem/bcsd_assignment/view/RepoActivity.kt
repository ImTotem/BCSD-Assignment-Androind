package io.github.imtotem.bcsd_assignment.view

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.imtotem.bcsd_assignment.R
import io.github.imtotem.bcsd_assignment.adapter.RepoAdapter
import io.github.imtotem.bcsd_assignment.base.BaseActivity
import io.github.imtotem.bcsd_assignment.databinding.ActivityRepoBinding
import io.github.imtotem.bcsd_assignment.model.Repo
import io.github.imtotem.bcsd_assignment.model.User
import io.github.imtotem.bcsd_assignment.network.ApiClient
import io.github.imtotem.bcsd_assignment.network.GithubService
import io.github.imtotem.bcsd_assignment.viewmodel.RepoViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepoActivity : BaseActivity<ActivityRepoBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_repo

    private val viewModel: RepoViewModel by viewModels()
    private lateinit var repoAdapter: RepoAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    private var page = 1
    private var hasMore = true

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun initView() {
        binding.viewModel = viewModel

        getUser()
        initAdapter()
        initRecyclerView()
        scrollRecyclerView()
        getRepos(viewModel.username.value.toString(), 0)
    }

    override fun initEvent() {
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun getUser() {
        val user = intent.getParcelableExtra(USER, User::class.java)
        viewModel.setUsername(user?.username ?: return)
    }

    private fun initAdapter() {
        repoAdapter = RepoAdapter { repo ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(repo.htmlUrl))
            startActivity(intent)
        }
    }

    private fun initRecyclerView() {
        linearLayoutManager = LinearLayoutManager(this)
        binding.repoRecyclerView.also {
            it.layoutManager = linearLayoutManager
            it.adapter = repoAdapter
        }
    }

    private fun scrollRecyclerView() {
        binding.repoRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalCount = linearLayoutManager.itemCount
                val lastVisiblePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition()

                if ( lastVisiblePosition >= (totalCount - 1) && hasMore ) {
                    getRepos(viewModel.username.value.toString(), ++page)
                }
            }
        })
    }

    private fun getRepos(path: String, page: Int) {
        val githubService = ApiClient.retrofit.create(GithubService::class.java)
        binding.progressBar.isVisible = true

        githubService.getRepos(path, page).enqueue(object : Callback<List<Repo>> {
            override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
                binding.progressBar.isVisible = false
                hasMore = response.body()?.count() == 30
                repoAdapter.submitList(repoAdapter.currentList + response.body().orEmpty())
            }

            override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@RepoActivity, "ERROR", Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {
        const val USER = "user"
    }
}
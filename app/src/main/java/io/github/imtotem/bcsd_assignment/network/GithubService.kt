package io.github.imtotem.bcsd_assignment.network

import io.github.imtotem.bcsd_assignment.model.Repo
import io.github.imtotem.bcsd_assignment.model.UserDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {
    @GET("users/{username}/repos")
    fun getRepos(
        @Path("username") username: String,
        @Query("page") page: Int
    ): Call<List<Repo>>

    @GET("search/users")
    fun searchUsers(
        @Query("q") query: String
    ): Call<UserDTO>
}
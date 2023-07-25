package io.github.imtotem.bcsd_assignment.model

import com.google.gson.annotations.SerializedName

data class UserDTO(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("items")
    val items: List<User>
)
package com.flutlin.apiConfig.response

import com.flutlin.apiConfig.model.User
import com.google.gson.annotations.SerializedName

open class UserResponse {
    @SerializedName("results")
    var users: List<User>? = null
}
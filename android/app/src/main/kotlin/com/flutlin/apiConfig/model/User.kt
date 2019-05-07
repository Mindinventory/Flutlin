package com.flutlin.apiConfig.model

import com.google.gson.annotations.SerializedName

class User {
    @SerializedName("picture")
    var picture: Picture? = null
}
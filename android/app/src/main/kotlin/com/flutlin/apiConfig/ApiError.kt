package com.flutlin.apiConfig

data class ApiError(val message: String, val status: Int, val otp: Long, val token: String) {
    constructor() : this("", 0, 0, "")
}
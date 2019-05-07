package com.flutlin.apiConfig

import com.flutlin.apiConfig.response.UserResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

interface WebApi {
    /* Random user */
    @GET("/api/?inc=picture&noinfo&results=50")
    fun randomUserApi(): Observable<Response<UserResponse>>
}
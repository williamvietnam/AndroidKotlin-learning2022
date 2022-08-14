package com.nbgsoftware.myinstagram.core.data.network

import com.nbgsoftware.myinstagram.core.data.network.models.ApiUser
import retrofit2.http.GET

/**
 * Author: William Giang Nguyen | 8/7/2022
 * */
interface ApiInterface {
    @GET("users")
     fun getUsers(): List<ApiUser>

    @GET("more-users")
    suspend fun getMoreUsers(): List<ApiUser>

    @GET("error")
    suspend fun getUsersWithError(): List<ApiUser>
}

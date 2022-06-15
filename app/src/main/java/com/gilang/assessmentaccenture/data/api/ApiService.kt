package com.gilang.assessmentaccenture.data.api

import com.gilang.assessmentaccenture.data.model.Obj_User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET

interface ApiService {

    @GET("/users/")
    fun getUser(@Body params: Obj_User?): Call<Obj_User?>?

}
package net.rmitsolutions.eskool.fcm.php

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by RMIT on 26/10/2017.
 */
interface ApiService {

    @FormUrlEncoded
    @POST("RegisterDevice.php")
    fun insertToken(
            @Field("user_name") mobileNumber: String,
            @Field("fcm_token_id") tokenId: String): Call<User>
}
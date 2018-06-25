package net.rmitsolutions.eskool.fcm.php

import com.google.gson.annotations.SerializedName

/**
 * Created by RMIT on 26/10/2017.
 */
class User {

    @SerializedName("user_name")
    var userName: String? = null

    @SerializedName("fcm_token_id")
    var fcmTokenId: String? = null
}
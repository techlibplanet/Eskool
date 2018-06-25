package net.rmitsolutions.eskool.sample

import com.google.gson.annotations.SerializedName

/**
 * Created by RMIT on 17/10/2017.
 */
class Question(
        @SerializedName("ques_code") val quesCode: String,
        @SerializedName("question") val question: String,
        @SerializedName("a") val a: String,
        @SerializedName("b") val b: String,
        @SerializedName("c") val c: String,
        @SerializedName("d") val d: String,
        @SerializedName("answer") val answer: String)

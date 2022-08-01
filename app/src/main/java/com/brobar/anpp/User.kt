package com.brobar.anpp

import com.google.gson.annotations.SerializedName

open class User(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("email")
    val email: String = "",
    @SerializedName("password")
    val emailPass: String = "",
    @SerializedName("email_password")
    val emailPassword: String = "",
    @SerializedName("type")
    val type: Int = 0
)
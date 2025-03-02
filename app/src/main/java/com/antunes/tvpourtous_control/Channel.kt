package com.antunes.tvpourtous_control

import com.google.gson.annotations.SerializedName

data class Channel(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)

data class ChangeChannelRequest(
    val ChannelName: String
)

data class ApiResponse(
    val Message: String
)

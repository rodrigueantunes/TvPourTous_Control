package com.antunes.tvpourtous_control

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TvPourTousApi {
    @GET("api/channels")
    suspend fun getChannels(): List<Channel>

    @POST("api/channelcontrol/change")
    suspend fun changeChannel(@Body request: ChangeChannelRequest): ApiResponse
}

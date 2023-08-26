package com.example.lichhop.data.model

import com.example.lichhop.data.model.CacularEvent
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class EventResponse (

    @SerializedName("room")
    @Expose
    var room: String? = null,

    @SerializedName("timeRange")
    @Expose
    var timeRange: String? = null,

    @SerializedName("data")
    @Expose
    var cacularEventList: List<CacularEvent>? = null,

    )

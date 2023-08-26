package com.example.lichhop.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CacularEvent(
    @SerializedName("dayName")
    @Expose
    var dayName: String? = null,

    @SerializedName("event")
    @Expose
    var event: List<Event>? = null
)

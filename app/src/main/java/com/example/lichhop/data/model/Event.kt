package com.example.lichhop.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Event(
    @SerializedName("user")
    @Expose
    var userName: String? = null,

    @SerializedName("startTime")
    @Expose
    var startTime: String? = null,

    @SerializedName("endTime")
    @Expose
    var endTime: String? = null,

    @SerializedName("content")
    @Expose
    var content: String? = null,
)

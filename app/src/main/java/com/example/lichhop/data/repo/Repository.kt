package com.example.lichhop.data.repo

import com.example.lichhop.data.model.EventResponse
import com.example.lichhop.presentation.getApplication
import com.google.gson.Gson

object Repository {

    private const val FILENAME = "lichhop.json"

    private fun readFileText(fileName: String): String {
        return getApplication().assets.open(fileName).bufferedReader().use { it.readText() }
    }

    fun getEventRespone(): EventResponse {
        val jsonData = readFileText(FILENAME)
        val gson = Gson()
        val eventResponse = gson.fromJson(jsonData, EventResponse::class.java)
        return eventResponse
    }
}

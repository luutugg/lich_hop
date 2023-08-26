package com.example.lichhop.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lichhop.data.model.EventResponse
import com.example.lichhop.data.repo.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private var _eventState = MutableStateFlow<EventResponse?>(null)
    val eventState = _eventState.asStateFlow()

    init {
        getEventResponse()
    }

    private fun getEventResponse(){
        viewModelScope.launch {
            _eventState.value = Repository.getEventRespone()
        }
    }
}

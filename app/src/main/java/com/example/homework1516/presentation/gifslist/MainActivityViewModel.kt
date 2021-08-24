package com.example.homework1516.presentation.gifslist

import android.icu.util.TimeUnit
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework1516.data.remote.Data
import com.example.homework1516.presentation.Repository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainActivityViewModel : ViewModel(), KoinComponent {

    private val repository: Repository by inject()

    val searchResult = MutableLiveData<List<Data>>()
    var addToOwnerResult = MutableLiveData<List<Data>>()


    fun performSearch(query: String, offset: String) {
        viewModelScope.launch {
            searchResult.value = repository.performSearch(query, offset)
        }
    }

    fun performSearchNext(query: String, offset: String) {
        viewModelScope.launch {
            addToOwnerResult.value = repository.performSearch(query, offset)
            searchResult.value = searchResult.value?.plus(addToOwnerResult.value!!)

        }
    }

}
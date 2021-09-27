package com.example.takeofflabstinder.profiles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.takeofflabstinder.data.datasource.local.LocalDataSource
import com.example.takeofflabstinder.data.datasource.local.ProfileEntity
import com.example.takeofflabstinder.data.repository.ProfilesRepository
import kotlinx.coroutines.launch

class ProfilesViewModel : ViewModel() {
    // refactor to real repository using DI such as Hilt or Koin
    private val repository = ProfilesRepository(LocalDataSource())

    private val _profiles = MutableLiveData<List<ProfileEntity>>()
    val profiles: LiveData<List<ProfileEntity>>
        get() = _profiles

    private val _matchCount = MutableLiveData<Int>()
    val matchCount: LiveData<Int>
        get() = _matchCount

    private var index = 0

    fun getProfiles() {
        viewModelScope.launch {
            val resource = repository.getProfiles()
            _profiles.postValue(resource)
            _matchCount.postValue(resource.count { it.isMatch })
        }
    }

    fun like() {
        _profiles.value?.let {
            it[index].isMatch = true
            _matchCount.postValue(it.count { profile -> profile.isMatch })
            index++
        }
    }

    fun dislike() {
        _profiles.value?.let {
            it[index].isMatch = false
            _matchCount.postValue(it.count { profile -> profile.isMatch })
            index++
        }
    }
}
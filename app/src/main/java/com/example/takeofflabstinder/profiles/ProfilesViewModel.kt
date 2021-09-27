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

    fun getProfiles() {
        viewModelScope.launch {
            val resource = repository.getProfiles()
            _profiles.postValue(resource)
        }
    }
}
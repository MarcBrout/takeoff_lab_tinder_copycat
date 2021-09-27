package com.example.takeofflabstinder.data.repository

import com.example.takeofflabstinder.data.datasource.local.LocalDataSource

class ProfilesRepository(private val dataSource: LocalDataSource) {
    // Supposed to be asynchronous operations
    suspend fun getProfiles() = dataSource.getProfiles()
}
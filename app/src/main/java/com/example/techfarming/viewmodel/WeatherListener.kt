package com.example.techfarming.viewmodel

import androidx.lifecycle.LiveData

interface WeatherListener {
    fun onSuccess(authRepo: LiveData<String>)
}
package com.example.techfarming.viewmodel

import androidx.lifecycle.LiveData

interface AuthListener {

    fun onStarted()
    fun onSuccess(authRepo: LiveData<String>)
    fun onFailure(message: String)
}
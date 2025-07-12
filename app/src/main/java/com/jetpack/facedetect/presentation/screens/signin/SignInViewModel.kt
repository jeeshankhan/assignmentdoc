package com.jetpack.facedetect.presentation.screens.signin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jetpack.facedetect.domain.model.User
import com.jetpack.facedetect.domain.repository.UserRepository
import kotlinx.coroutines.launch

class SignInViewModel(private val repository: UserRepository) : ViewModel() {

    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isLoggedIn by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    fun onSignInClick() = viewModelScope.launch {
        val user = repository.getUserByEmail(email)

        if (user == null) {
            repository.logoutAllUsers()
            repository.insertUser(User(email = email, password = password))
            repository.setLoggedInUser(email)
            isLoggedIn = true
        } else if (user.password == password) {
            repository.logoutAllUsers()
            repository.setLoggedInUser(email)
            isLoggedIn = true
        } else {
            errorMessage = "Invalid password."
        }
    }

    fun checkIfUserAlreadyLoggedIn() = viewModelScope.launch {
        val user = repository.getLoggedInUser()
        isLoggedIn = user != null
    }
}
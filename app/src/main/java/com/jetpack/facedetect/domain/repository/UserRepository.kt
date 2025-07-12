package com.jetpack.facedetect.domain.repository

import com.jetpack.facedetect.domain.model.User

interface UserRepository {
    suspend fun getUserByEmail(email: String): User?
    suspend fun insertUser(user: User): Long
    suspend fun getLoggedInUser(): User?
    suspend fun logoutAllUsers()
    suspend fun setLoggedInUser(email: String)
}
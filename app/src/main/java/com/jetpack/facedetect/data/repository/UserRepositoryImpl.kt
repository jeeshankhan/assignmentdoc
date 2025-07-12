package com.jetpack.facedetect.data.repository

import com.jetpack.facedetect.data.local.UserDao
import com.jetpack.facedetect.data.local.UserEntity
import com.jetpack.facedetect.domain.model.User
import com.jetpack.facedetect.domain.repository.UserRepository

class UserRepositoryImpl(private val dao: UserDao) : UserRepository {
    override suspend fun getUserByEmail(email: String): User? {
        return dao.getUserByEmail(email)?.toDomain()
    }

    override suspend fun insertUser(user: User): Long {
        return dao.insertUser(user.toEntity())
    }

    override suspend fun getLoggedInUser(): User? {
        return dao.getLoggedInUser()?.toDomain()
    }

    override suspend fun logoutAllUsers() {
        dao.logoutAllUsers()
    }

    override suspend fun setLoggedInUser(email: String) {
        dao.setLoggedInUser(email)
    }

    private fun UserEntity.toDomain() = User(id, email, password)
    private fun User.toEntity() = UserEntity(id, email, password)
}
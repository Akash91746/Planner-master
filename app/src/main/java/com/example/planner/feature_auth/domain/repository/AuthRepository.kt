package com.example.planner.feature_auth.domain.repository

import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {

    suspend fun signIn(email: String,password: String): Result<FirebaseUser>

    suspend fun signUp(email: String,password: String): Result<FirebaseUser>

    fun signOut(): Result<Unit>

    suspend fun sendPasswordResetMail(email:String): Result<Unit>

    suspend fun sendCurrentUserEmailVerificationLink(): Result<Unit>

    suspend fun updatePassword(currentPassword: String,newPassword: String): Result<Unit>

    fun addAuthChangeListener(listener: AuthStateListener)

    fun removeAuthChangeListener(listener: AuthStateListener)

    fun getCurrentUser(): FirebaseUser?
}
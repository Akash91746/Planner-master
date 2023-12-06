package com.example.planner.feature_auth.data.repository

import com.example.planner.feature_auth.domain.repository.AuthRepository
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.EmailAuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import timber.log.Timber

class AuthRepositoryImpl(
    firebaseApp: FirebaseApp,
) : AuthRepository {

    private val firebaseAuth = FirebaseAuth.getInstance(firebaseApp)

    override suspend fun signIn(email: String, password: String): Result<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val user = result.user ?: return Result.failure(Throwable("Error Signing in user"))

            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signUp(email: String, password: String): Result<FirebaseUser> {
        return try {

            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user ?: return Result.failure(Throwable("Error Signing up"))

            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun signOut(): Result<Unit> {
        return try {
            firebaseAuth.signOut()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun sendPasswordResetMail(email: String): Result<Unit> {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun sendCurrentUserEmailVerificationLink(): Result<Unit> {
        return try {
            if(firebaseAuth.currentUser == null) {
                throw Error("Sign in to continue!")
            }
            firebaseAuth.currentUser?.sendEmailVerification()

            Result.success(Unit)
        }catch (e: Error) {
            Result.failure(e)
        }
    }

    override suspend fun updatePassword(
        currentPassword: String,
        newPassword: String,
    ): Result<Unit> {
        val currentUser = getCurrentUser() ?: return Result.failure(Throwable("No user found"))
        val credentials = EmailAuthProvider.getCredential(currentUser.email!!, currentPassword)

        return try {
            currentUser.reauthenticate(credentials).await()

            currentUser.updatePassword(newPassword).await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun addAuthChangeListener(listener: AuthStateListener){
        firebaseAuth.addAuthStateListener(listener)
    }

    override fun removeAuthChangeListener(listener: AuthStateListener){
        firebaseAuth.removeAuthStateListener(listener)
    }

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }
}
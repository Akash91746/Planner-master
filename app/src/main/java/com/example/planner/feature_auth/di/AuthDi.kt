package com.example.planner.feature_auth.di

import com.example.planner.feature_auth.data.repository.AuthRepositoryImpl
import com.example.planner.feature_auth.domain.repository.AuthRepository
import com.example.planner.feature_auth.domain.use_cases.validation_useCases.EmailValidation
import com.example.planner.feature_auth.domain.use_cases.validation_useCases.PasswordValidation
import com.example.planner.feature_auth.domain.use_cases.validation_useCases.RepeatPasswordValidation
import com.example.planner.feature_auth.domain.use_cases.validation_useCases.ValidationUseCases
import com.google.firebase.FirebaseApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object AuthDi {

    @Provides
    fun providesAuthRepository(): AuthRepository{
        val firebaseApp = FirebaseApp.getInstance()
        return AuthRepositoryImpl(firebaseApp)
    }

    @Provides
    fun providesValidationUseCases(): ValidationUseCases {
        return ValidationUseCases(
            emailValidation = EmailValidation(),
            passwordValidation = PasswordValidation(),
            repeatPasswordValidation = RepeatPasswordValidation()
        )
    }
}
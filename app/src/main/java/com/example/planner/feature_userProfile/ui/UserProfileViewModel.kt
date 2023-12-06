package com.example.planner.feature_userProfile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planner.R
import com.example.planner.common.utils.UiText
import com.example.planner.feature_auth.domain.repository.AuthRepository
import com.example.planner.feature_auth.domain.use_cases.validation_useCases.ValidationUseCases
import com.example.planner.feature_userProfile.utils.UiEvents
import com.example.planner.feature_userProfile.utils.UserProfileEvents
import com.example.planner.feature_userProfile.utils.UserProfileState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val validationUseCases: ValidationUseCases,
) : ViewModel() {

    private val _state = MutableStateFlow(UserProfileState())
    val state = _state.asStateFlow()

    private val _uiEvents = Channel<UiEvents>()
    val uiEvents = _uiEvents.receiveAsFlow()

    private var job: Job? = null
    private var forgotPasswordJob: Job? = null

    private val authListener = FirebaseAuth.AuthStateListener { auth ->
        _state.value = _state.value.copy(
            firebaseUser = auth.currentUser
        )
    }

    init {
        authRepository.addAuthChangeListener(authListener)
    }

    fun onEvent(event: UserProfileEvents) {

        when (event) {
            is UserProfileEvents.OnClickVerifyEmail -> {
                verifyEmailJob()
            }
            is UserProfileEvents.ToggleUpdatePassword -> {
                val expanded = _state.value.changePasswordExpanded
                _state.value = _state.value.copy(
                    changePasswordExpanded = !expanded
                )
            }
            is UserProfileEvents.OnChangeCurrentPassword -> {
                _state.value = _state.value.copy(
                    currentPassword = event.value,
                    currentPasswordError = null
                )
            }
            is UserProfileEvents.OnChangeNewPassword -> {
                _state.value = _state.value.copy(
                    newPassword = event.value,
                    newPasswordError = null
                )
            }
            is UserProfileEvents.OnChangeConfirmNewPassword -> {
                _state.value = _state.value.copy(
                    newConfirmPassword = event.value,
                    newConfirmPasswordError = null
                )
            }
            is UserProfileEvents.OnSubmitChangePassword -> {
                submitChangePassword()
            }
            is UserProfileEvents.OnClickScaffold -> {
                sendUiEvent(UiEvents.RemoveFocus)
            }
            is UserProfileEvents.OnClickResetCurrentPassword -> {
                sendResetPasswordJob()
            }
        }
    }

    private fun sendUiEvent(event: UiEvents) = viewModelScope.launch {
        _uiEvents.send(event)
    }

    private fun sendResetPasswordJob() {
        forgotPasswordJob?.cancel()

        val currentUser = _state.value.firebaseUser
        val email = currentUser?.email

        if(currentUser != null && email != null) {
            setLoading(true)
            forgotPasswordJob = viewModelScope.launch(Dispatchers.IO) {
                val result = authRepository.sendPasswordResetMail(email)

                withContext(Dispatchers.Main) {
                    setLoading(false)

                    val message = if (result.isSuccess) {
                        UiText.StringResource(R.string.password_reset_link_send_message)
                    }else {
                        UiText.StringResource(R.string.generic_error)
                    }

                    sendUiEvent(UiEvents.ShowSnackBar(message))
                }
            }
        }
    }

    private fun verifyEmailJob() {
        job?.cancel()

        val currentUser = _state.value.firebaseUser

        if (currentUser != null) {
            setLoading(true)
            job = viewModelScope.launch(Dispatchers.IO) {
                val result = authRepository.sendCurrentUserEmailVerificationLink()

                withContext(Dispatchers.Main) {
                    setLoading(false)

                    val message = if (result.isSuccess) {
                        UiText.StringResource(R.string.email_verification_successful_message)
                    } else {
                        UiText.StringResource(R.string.generic_error)
                    }

                    sendUiEvent(UiEvents.ShowSnackBar(message))
                }
            }
        }
    }

    private fun submitChangePassword() {
        sendUiEvent(UiEvents.RemoveFocus)

        val state = _state.value
        val currentPasswordValidation = validationUseCases.passwordValidation(state.currentPassword)
        val newPasswordValidation = validationUseCases.passwordValidation(state.newPassword)
        val confirmPasswordValidation =
            validationUseCases.repeatPasswordValidation(state.newPassword, state.newConfirmPassword)

        val hasError =
            listOf(newPasswordValidation, confirmPasswordValidation).any { !it.successful }

        if (hasError) {
            _state.value = _state.value.copy(
                currentPasswordError = currentPasswordValidation.errorMessage,
                newPasswordError = newPasswordValidation.errorMessage,
                newConfirmPasswordError = confirmPasswordValidation.errorMessage
            )
            return
        }
        setLoading(true)

        viewModelScope.launch(Dispatchers.IO) {

            val result = authRepository.updatePassword(
                currentPassword = state.currentPassword,
                newPassword = state.newPassword
            )

            withContext(Dispatchers.Main) {
                setLoading(false)

                val message = when (result.exceptionOrNull()) {
                    is FirebaseAuthInvalidCredentialsException -> {
                        UiText.StringResource(R.string.invalid_credentials_error)
                    }
                    null -> {
                        _state.value = _state.value.copy(
                            currentPassword = "",
                            currentPasswordError = null,
                            newPassword = "",
                            newConfirmPasswordError = null,
                            newConfirmPassword = "",
                            newPasswordError = null,
                            changePasswordExpanded = false
                        )
                        UiText.StringResource(R.string.password_reset_successful_message)
                    }
                    else -> {
                        UiText.StringResource(R.string.generic_error)
                    }
                }

                sendUiEvent(UiEvents.ShowSnackBar(message))
            }
        }
    }

    private fun setLoading(loading: Boolean) {
        _state.value = _state.value.copy(
            isLoading = loading
        )
    }

    override fun onCleared() {
        super.onCleared()
        authRepository.removeAuthChangeListener(authListener)
    }
}
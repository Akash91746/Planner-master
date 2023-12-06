package com.example.planner.feature_auth.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planner.R
import com.example.planner.common.utils.UiText
import com.example.planner.feature_auth.domain.repository.AuthRepository
import com.example.planner.feature_auth.domain.use_cases.validation_useCases.ValidationUseCases
import com.example.planner.feature_auth.domain.utils.AuthMode
import com.example.planner.feature_auth.domain.utils.AuthScreenEvents
import com.example.planner.feature_auth.domain.utils.AuthScreenState
import com.example.planner.feature_auth.domain.utils.UiEvents
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val validationUseCases: ValidationUseCases,
) : ViewModel() {

    private val _state = MutableStateFlow(AuthScreenState())
    val state: StateFlow<AuthScreenState> = _state.asStateFlow()

    private val _uiEvents = Channel<UiEvents>()
    val uiEvents = _uiEvents.receiveAsFlow()

    private val authListener = FirebaseAuth.AuthStateListener { auth ->
        if (auth.currentUser != null) {
            sendUiEvent(UiEvents.Navigate(null))
        }
    }

    init {
        authRepository.addAuthChangeListener(authListener)
    }

    private var job: Job? = null

    fun onEvent(event: AuthScreenEvents) {

        when (event) {
            is AuthScreenEvents.OnChangeEmail -> {
                _state.value = _state.value.copy(
                    email = event.value
                )
            }
            is AuthScreenEvents.OnChangePassword -> {
                _state.value = _state.value.copy(
                    password = event.value
                )
            }
            is AuthScreenEvents.OnChangeConfirmPassword -> {
                _state.value = _state.value.copy(
                    confirmPassword = event.value
                )
            }
            is AuthScreenEvents.OnChangeForgotPasswordEmail -> {
                _state.value = _state.value.copy(
                    forgotPasswordEmail = event.value,
                    forgotPasswordEmailErrorMessage = null
                )
            }
            is AuthScreenEvents.OnChangeAuthMode -> {
                handleOnChangeAuthMode(event.authMode)
            }
            is AuthScreenEvents.OnClickForgetPassword -> {
                showForgotPasswordDialog()
            }
            is AuthScreenEvents.OnClickBackdropScaffold -> {
                sendUiEvent(UiEvents.RemoveFocus)
            }
            is AuthScreenEvents.OnSubmitSignIn -> {
                job?.cancel()
                job = handleOnSubmitSignIn()
            }
            is AuthScreenEvents.OnSubmitSignUp -> {
                job?.cancel()
                job = handleOnSubmitSignUp()
            }
            is AuthScreenEvents.OnSubmitForgotPassword -> {
                job?.cancel()
                job = handleOnSubmitForgotPassword()
            }
        }
    }

    private fun sendUiEvent(uiEvent: UiEvents) {
        viewModelScope.launch {
            _uiEvents.send(uiEvent)
        }
    }

    private fun showForgotPasswordDialog() {
        clearUiFocus()
        _state.value = _state.value.copy(
            forgotPasswordEmailErrorMessage = null,
            forgotPasswordEmail = ""
        )
        sendUiEvent(UiEvents.ShowForgotPasswordDialog)
    }

    private fun handleOnChangeAuthMode(mode: AuthMode) {
        clearUiFocus()
        _state.value = _state.value.copy(
            authMode = mode,
            email = "",
            password = "",
            confirmPassword = "",
            emailErrorMessage = null,
            passwordErrorMessage = null,
            confirmPasswordErrorMessage = null
        )
    }

    private fun clearUiFocus() {
        sendUiEvent(UiEvents.RemoveFocus)
    }

    private fun setLoading(loading: Boolean) {
        _state.value = _state.value.copy(
            isLoading = loading
        )
    }

    private fun handleOnSubmitSignIn() = viewModelScope.launch {
        clearUiFocus()

        val state = _state.value
        val email = state.email
        val password = state.password

        _state.value = _state.value.copy(
            emailErrorMessage = null,
            passwordErrorMessage = null,
        )

        val emailResult = validationUseCases.emailValidation(email)

        if (!emailResult.successful) {
            _state.value = _state.value.copy(
                emailErrorMessage = emailResult.errorMessage
            )
            return@launch
        }

        val passwordResult = password.trim().isNotEmpty()

        if (!passwordResult) {
            _state.value = _state.value.copy(
                passwordErrorMessage = UiText.StringResource(R.string.required_error)
            )
            return@launch
        }

        setLoading(true)

        val result = authRepository.signIn(email, password)

        val uiText = when (result.exceptionOrNull()) {
            is FirebaseAuthInvalidCredentialsException, is FirebaseAuthInvalidUserException -> {
                UiText.StringResource(R.string.invalid_credentials_error)
            }
            null -> {
                UiText.StringResource(R.string.on_success_sign_in_greeting)
            }
            else -> {
                UiText.StringResource(R.string.generic_error)
            }
        }

        setLoading(false)

        sendUiEvent(UiEvents.ShowSnackBar(uiText))
    }

    private fun handleOnSubmitSignUp() = viewModelScope.launch {
        clearUiFocus()
        val state = _state.value
        val email = state.email
        val password = state.password
        val confirmPassword = state.confirmPassword

        _state.value = _state.value.copy(
            emailErrorMessage = null,
            passwordErrorMessage = null,
            confirmPasswordErrorMessage = null
        )

        val emailResult = validationUseCases.emailValidation(email)

        if (!emailResult.successful) {
            _state.value = _state.value.copy(
                emailErrorMessage = emailResult.errorMessage
            )
            return@launch
        }

        val passwordResult = validationUseCases.passwordValidation(password)

        if (!passwordResult.successful) {
            _state.value = _state.value.copy(
                passwordErrorMessage = passwordResult.errorMessage
            )
            return@launch
        }

        val repeatPasswordValidation = validationUseCases.repeatPasswordValidation(password,confirmPassword)

        if (!repeatPasswordValidation.successful) {
            _state.value = _state.value.copy(
                confirmPasswordErrorMessage = repeatPasswordValidation.errorMessage
            )
            return@launch
        }

        setLoading(true)

        val result = authRepository.signUp(email, password)

        val exception = result.exceptionOrNull()

        setLoading(false)

        if (exception is FirebaseAuthWeakPasswordException){
            _state.value = _state.value.copy(
                passwordErrorMessage = UiText.StringResource(R.string.week_password_error)
            )
            return@launch
        }

        val uiText = when (exception) {
            null -> {
                UiText.StringResource(R.string.on_success_sign_in_greeting)
            }
            else -> {
                UiText.StringResource(R.string.generic_error)
            }
        }

        sendUiEvent(UiEvents.ShowSnackBar(uiText))
    }

    private fun handleOnSubmitForgotPassword() = viewModelScope.launch {
        clearUiFocus()
        val email = _state.value.forgotPasswordEmail

        val emailResult = validationUseCases.emailValidation(email)

        if (!emailResult.successful) {
            _state.value = _state.value.copy(
                forgotPasswordEmailErrorMessage = emailResult.errorMessage
            )
            return@launch
        }
        setLoading(true)

        val result = authRepository.sendPasswordResetMail(email)

        setLoading(false)

        val uiText = when(result.exceptionOrNull()){
            null -> {
                UiText.StringResource(R.string.password_reset_link_send_message)
            }
            else -> {
                UiText.StringResource(R.string.generic_error)
            }
        }

        Timber.d("Password result is $result")

        sendUiEvent(UiEvents.ShowSnackBar(uiText))
    }

    override fun onCleared() {
        super.onCleared()
        authRepository.removeAuthChangeListener(authListener)
    }

}
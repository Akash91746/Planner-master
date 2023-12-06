package com.example.planner.feature_settings.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planner.common.utils.AppScreens
import com.example.planner.feature_auth.domain.repository.AuthRepository
import com.example.planner.feature_settings.utils.SettingScreenEvents
import com.example.planner.feature_settings.utils.SettingScreenState
import com.example.planner.feature_settings.utils.UiEvents
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(SettingScreenState())
    val state = _state.asStateFlow()

    private val _uiEvents = Channel<UiEvents>()
    val uiEvents = _uiEvents.receiveAsFlow()

    private val authListener = FirebaseAuth.AuthStateListener { auth ->
        _state.value = _state.value.copy(
            firebaseUser = auth.currentUser
        )
    }

    init {
        initUser()
    }

    private fun initUser() {
        authRepository.addAuthChangeListener(authListener)
    }

    fun onEvent(event: SettingScreenEvents) {
        when (event) {
            is SettingScreenEvents.OnClickSignOut -> {
                signOutUser()
            }
            is SettingScreenEvents.OnClickAccount -> {
                navigate(AppScreens.UserProfileScreen)
            }
        }
    }

    private fun navigate(screen: AppScreens){
        sendUiEvent(UiEvents.Navigate(screen.navPath))
    }

    private fun sendUiEvent(event: UiEvents) = viewModelScope.launch {
        _uiEvents.send(event)
    }

    private fun signOutUser() = viewModelScope.launch {
        authRepository.signOut()
    }

    override fun onCleared() {
        super.onCleared()
        authRepository.removeAuthChangeListener(authListener)
    }
}
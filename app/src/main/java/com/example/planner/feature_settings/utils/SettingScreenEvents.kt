package com.example.planner.feature_settings.utils

sealed class SettingScreenEvents {

    object OnClickSignOut: SettingScreenEvents()

    object OnClickAccount: SettingScreenEvents()
}

package com.example.planner.feature_settings.utils

sealed class UiEvents {
    data class Navigate(val path: String?): UiEvents()
}
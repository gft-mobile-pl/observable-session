package com.gft.example.observablesession.ui.enterpassword

data class EnterPasswordViewState(
    val isLoadingIndicatorVisible: Boolean,
    val loginButtonEnabled: Boolean,
    val inputErrorMessage: String?
)

sealed interface EnterPasswordViewEvent {
    object OnBackButtonClicked : EnterPasswordViewEvent
    object OnLoginButtonClicked : EnterPasswordViewEvent
    object OnScreenLoaded : EnterPasswordViewEvent
    data class OnPasswordEntered(val password: String) : EnterPasswordViewEvent
}

sealed interface EnterPasswordViewEffect {
    data class FillWithSensitiveData(val password: String) : EnterPasswordViewEffect
    object ShowInvalidCredentialsToast : EnterPasswordViewEffect
}

sealed interface EnterPasswordNavigationEffect {
    object NavigateBack : EnterPasswordNavigationEffect
    object NavigateToLoggedInScreen : EnterPasswordNavigationEffect
}
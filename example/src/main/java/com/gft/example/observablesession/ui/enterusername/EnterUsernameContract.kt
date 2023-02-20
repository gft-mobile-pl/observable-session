package com.gft.example.observablesession.ui.enterusername

data class EnterUsernameViewState(
    val nextButtonEnabled: Boolean,
    val inputErrorMessage: String?
)

sealed interface EnterUsernameViewEvent {
    object OnBackButtonClicked : EnterUsernameViewEvent
    object OnNextButtonClicked : EnterUsernameViewEvent
    object OnScreenLoaded : EnterUsernameViewEvent
    data class OnUsernameEntered(val username: String) : EnterUsernameViewEvent
}

sealed interface EnterUsernameViewEffect {
    data class FillWithSensitiveData(val username: String) : EnterUsernameViewEffect
}

sealed interface EnterUsernameNavigationEffect {
    object NavigateBack : EnterUsernameNavigationEffect
    object NavigateToEnterPassword : EnterUsernameNavigationEffect
}
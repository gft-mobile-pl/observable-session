package com.gft.example.observablesession.ui.loggedin

data class LoggedInViewState(
    val accessToken: String
)

sealed interface LoggedInViewEvent {
    object OnNavigateBackClicked : LoggedInViewEvent
}

sealed interface LoggedInNavigationEffect {
    object NavigateOut : LoggedInNavigationEffect
}
package com.gft.example.observablesession.ui.loggedin

import com.gft.example.observablesession.domain.loggedin.GetAccessTokenUseCase
import com.gft.example.observablesession.domain.loggedin.LogoutUseCase
import com.gft.example.observablesession.ui.base.BaseViewModel
import com.gft.example.observablesession.ui.loggedin.LoggedInNavigationEffect.NavigateOut
import com.gft.example.observablesession.ui.loggedin.LoggedInViewEvent.OnNavigateBackClicked

class LoggedInViewModel(
    getAccessToken: GetAccessTokenUseCase,
    private val logout: LogoutUseCase
) : BaseViewModel<LoggedInViewState, LoggedInViewEvent, Unit, LoggedInNavigationEffect>() {
    init {
        viewState = LoggedInViewState(
            accessToken = getAccessToken()
        )
    }

    override fun onEvent(viewEvent: LoggedInViewEvent) {
        when (viewEvent) {
            OnNavigateBackClicked -> {
                logout()
                dispatchNavigationEffect(NavigateOut)
            }
        }
    }
}
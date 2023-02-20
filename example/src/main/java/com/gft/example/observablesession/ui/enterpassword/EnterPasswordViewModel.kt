package com.gft.example.observablesession.ui.enterpassword

import androidx.lifecycle.viewModelScope
import com.gft.example.observablesession.domain.login.GetCollectedPasswordUseCase
import com.gft.example.observablesession.domain.login.IsCollectedPasswordFormatValidUseCase
import com.gft.example.observablesession.domain.login.IsUserLoggedInUseCase
import com.gft.example.observablesession.domain.login.LoginUseCase
import com.gft.example.observablesession.domain.login.PasswordValidationResult
import com.gft.example.observablesession.domain.login.UpdateCollectedPasswordUseCase
import com.gft.example.observablesession.ui.base.BaseViewModel
import com.gft.example.observablesession.ui.enterpassword.EnterPasswordNavigationEffect.NavigateBack
import com.gft.example.observablesession.ui.enterpassword.EnterPasswordNavigationEffect.NavigateToLoggedInScreen
import com.gft.example.observablesession.ui.enterpassword.EnterPasswordViewEffect.FillWithSensitiveData
import com.gft.example.observablesession.ui.enterpassword.EnterPasswordViewEffect.ShowInvalidCredentialsToast
import com.gft.example.observablesession.ui.enterpassword.EnterPasswordViewEvent.OnPasswordEntered
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EnterPasswordViewModel internal constructor(
    private val updateCollectedPassword: UpdateCollectedPasswordUseCase,
    private val isCollectedPasswordFormatValid: IsCollectedPasswordFormatValidUseCase,
    private val isUserLoggedIn: IsUserLoggedInUseCase,
    private val getCollectedPassword: GetCollectedPasswordUseCase,
    private val login: LoginUseCase
) : BaseViewModel<EnterPasswordViewState, EnterPasswordViewEvent, EnterPasswordViewEffect, EnterPasswordNavigationEffect>() {
    init {
        startViewStateUpdates()
        startNavigationEffectsUpdates()
    }

    private fun startViewStateUpdates() {
        viewState = EnterPasswordViewState(
            loginButtonEnabled = false,
            isLoadingIndicatorVisible = false,
            inputErrorMessage = null
        )

        viewModelScope.launch(start = CoroutineStart.UNDISPATCHED) {
            isCollectedPasswordFormatValid()
                .collect { passwordFormatValidationResult ->
                    viewState = viewState.copy(
                        loginButtonEnabled = passwordFormatValidationResult == PasswordValidationResult.VALID,
                        inputErrorMessage = when(passwordFormatValidationResult) {
                            PasswordValidationResult.NOT_AVAILABLE,
                            PasswordValidationResult.VALID -> null
                            PasswordValidationResult.TOO_SHORT -> "Password is too short"
                        }
                    )
                }
        }
    }

    private fun startNavigationEffectsUpdates() {
        viewModelScope.launch(start = CoroutineStart.UNDISPATCHED) {
            isUserLoggedIn()
                .collect { userLoggedIn ->
                    if (userLoggedIn) {
                        dispatchNavigationEffect(NavigateToLoggedInScreen)
                    } else {
                        clearNavigationEffect()
                    }
                }
        }
    }

    override fun onEvent(viewEvent: EnterPasswordViewEvent) {
        when (viewEvent) {
            EnterPasswordViewEvent.OnBackButtonClicked -> {
                dispatchNavigationEffect(NavigateBack)
            }
            EnterPasswordViewEvent.OnScreenLoaded -> viewModelScope.launch(start = CoroutineStart.UNDISPATCHED) {
                dispatchViewEffect(FillWithSensitiveData(getCollectedPassword().first()))
            }
            is OnPasswordEntered -> {
                updateCollectedPassword(viewEvent.password)
            }
            EnterPasswordViewEvent.OnLoginButtonClicked -> {
                viewModelScope.launch(start = CoroutineStart.UNDISPATCHED) {
                    viewState = viewState.copy(
                        isLoadingIndicatorVisible = true
                    )
                    try {
                        login()
                    } catch (e: Throwable) {
                        viewState = viewState.copy(
                            isLoadingIndicatorVisible = false
                        )
                        dispatchViewEffect(ShowInvalidCredentialsToast)
                    }
                }
            }
        }
    }
}